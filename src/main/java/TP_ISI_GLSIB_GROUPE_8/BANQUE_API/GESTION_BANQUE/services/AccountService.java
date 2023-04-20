package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.services;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.DTO.AccountDTO;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.DTO.AccountTransferDTO;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model.Client;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model.Compte;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.repositories.AccountRepository;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;

    public List<Compte> getAllAccount() {
        return accountRepository.findAll();
    }

    public Compte findAccountInfo(String accountNumber) throws Exception {
        return accountRepository.findById(accountNumber).orElseThrow(() -> new Exception("No account with this account number"));
    }

    public Compte saveAccount(AccountDTO accountDTO) throws Exception {
        if(accountDTO.getTypeCompte() == null || accountDTO.getIdClient() == null){
            throw new Exception("This/these field/s must not be null");
        }
        Compte account = new Compte();
        account.setAccountNumber(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5).toUpperCase() + Calendar.getInstance().get(Calendar.YEAR));
        account.setCreationDate(LocalDate.now());
        account.setTypeCompte(accountDTO.getTypeCompte());
        account.setOwner(clientRepository.findById(accountDTO.getIdClient()).orElseThrow(() -> new Exception("Client with this id does not exist!")));
        account.setAccountBalance(0D);
        return accountRepository.save(account);
    }

    public Compte makeADeposit(AccountDTO accountDTO) throws Exception {
        if(accountDTO.getAccountBalance() == null || accountDTO.getIdClient() == null || accountDTO.getAccountNumber() == null){
            throw new Exception("This/these field/s must not be null");
        }
        Optional<Client> optionalClient = clientRepository.findById(accountDTO.getIdClient());
        Optional<Compte> optionalAccount = accountRepository.findById(accountDTO.getAccountNumber());
        if (accountDTO.getAccountBalance() < 0) {
            throw new Exception("The amount is negative");
        }
        checkIfClientExist(accountDTO.getIdClient(), accountDTO.getAccountNumber(), optionalClient, optionalAccount);
        Compte account = optionalAccount.get();
        if(account.getAccountBalance()==null){
            account.setAccountBalance(accountDTO.getAccountBalance());
        }else {
            account.setAccountBalance(account.getAccountBalance() + accountDTO.getAccountBalance());
        }
        Compte accountEnd = accountRepository.save(account);
        System.out.println(accountEnd.toString());
        return accountEnd;
    }

    public Compte withdrawMoney(AccountDTO accountDTO) throws Exception {
        if(accountDTO.getAccountBalance() == null || accountDTO.getIdClient() == null || accountDTO.getAccountNumber() == null){
            throw new Exception("This/these field/s must not be null");
        }
        Optional<Client> optionalClient = clientRepository.findById(accountDTO.getIdClient());
        Optional<Compte> optionalAccount = accountRepository.findById(accountDTO.getAccountNumber());
        checkIfClientExist(accountDTO.getIdClient(), accountDTO.getAccountNumber(), optionalClient, optionalAccount);
        Compte account = optionalAccount.orElseThrow(() -> new Exception("Account with this account number does not exist"));
        if (!withdrawMoneyCheck(account, accountDTO.getAccountBalance())) {
            throw new Exception("The account balance is less than the amount to be withdrawn");
        }
        System.out.println(account.toString());
        return accountRepository.save(account);
    }

    public Compte makeATransfer(AccountTransferDTO accountTransferDTO) throws Exception {
        if(accountTransferDTO.getSenderAccountNumber()==null||accountTransferDTO.getReceiverAccountNumber()==null||accountTransferDTO.getSenderIdClient()==null||accountTransferDTO.getReceiverIdClient()==null||accountTransferDTO.getAmount()==null){
            throw new Exception("This/these field/s must not be null");
        }

        Optional<Client> optionalSenderClient = clientRepository.findById(accountTransferDTO.getSenderIdClient());
        Optional<Client> optionalReceiverClient = clientRepository.findById(accountTransferDTO.getReceiverIdClient());
        Optional<Compte> optionalSenderAccount = accountRepository.findById(accountTransferDTO.getSenderAccountNumber());
        Optional<Compte> optionalReceiverAccount = accountRepository.findById(accountTransferDTO.getReceiverAccountNumber());

        checkIfClientExist(accountTransferDTO.getSenderIdClient(), accountTransferDTO.getSenderAccountNumber(), optionalSenderClient, optionalSenderAccount);
        checkIfClientExist(accountTransferDTO.getReceiverIdClient(), accountTransferDTO.getReceiverAccountNumber(), optionalReceiverClient, optionalReceiverAccount);

        Compte senderAccount = optionalSenderAccount.get();
        Compte receiverAccount = optionalReceiverAccount.get();

        AccountDTO senderAccountDTO = new AccountDTO();
        senderAccountDTO.setAccountNumber(senderAccount.getAccountNumber());
        if(accountTransferDTO.getAmount()!=null){
            senderAccountDTO.setAccountBalance(accountTransferDTO.getAmount());
        }else {
            senderAccountDTO.setAccountBalance(0D);
        }
        senderAccountDTO.setIdClient(senderAccount.getOwner().getId());

        System.out.println("FINAL SENDER DTO");
        System.out.println(senderAccountDTO.toString());

        AccountDTO receiverAccountDTO = new AccountDTO();
        receiverAccountDTO.setAccountNumber(receiverAccount.getAccountNumber());
        if(accountTransferDTO.getAmount()!=null){
            receiverAccountDTO.setAccountBalance(accountTransferDTO.getAmount());
        }else {
            receiverAccountDTO.setAccountBalance(0D);
        }
        receiverAccountDTO.setIdClient(receiverAccount.getOwner().getId());

        System.out.println("FINAL RECEIVER DTO");
        System.out.println(receiverAccountDTO.toString());

        withdrawMoney(senderAccountDTO);
        makeADeposit(receiverAccountDTO);

        return senderAccount;
    }

    public void removeAccount(String accountNumber) {
        accountRepository.deleteById(accountNumber);
    }


    private void checkIfClientExist(Long clientId, String accountNumber, Optional<Client> optionalClient, Optional<Compte> optionalAccount) throws Exception {
        if (optionalClient.isEmpty()) {
            throw new Exception("The client with id '" + clientId + "' does not exist");
        }
        if (optionalAccount.isEmpty()) {
            throw new Exception("The account with number '" + accountNumber + "' does not exist");
        }

        if (!(optionalClient.get().getId() == optionalAccount.get().getOwner().getId())) {
            throw new Exception("The user with the id '" + clientId + "' is not the owner of the account with number '" + accountNumber + "'");
        }
    }

    public boolean withdrawMoneyCheck(Compte account, Double amount) {
        if (account.getAccountBalance() >= amount) {
            account.setAccountBalance(account.getAccountBalance() - amount);
            return true;
        }
        return false;
    }
}
