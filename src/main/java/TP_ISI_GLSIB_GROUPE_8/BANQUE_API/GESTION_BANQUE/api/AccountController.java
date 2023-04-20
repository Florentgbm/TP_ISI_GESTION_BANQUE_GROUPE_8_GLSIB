package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.api;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.DTO.AccountDTO;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.DTO.AccountTransferDTO;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model.Compte;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/accounts")
    public List<Compte> listAccounts() {
        return accountService.getAllAccount();
    }

    @GetMapping("/accounts/{accountNumber}")
    public Compte showAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) throws Exception {
        return accountService.findAccountInfo(accountNumber);
    }

    @PostMapping("/accounts")
    public Compte saveAccount(@RequestBody AccountDTO account) throws Exception {
        return accountService.saveAccount(account);
    }

    @PostMapping("/accounts/makeADeposit")
    public Compte makeADeposit(@RequestBody AccountDTO accountDTO) throws Exception {
        return accountService.makeADeposit(accountDTO);
    }
    @PostMapping("/accounts/withdrawMoney")
    public Compte withdrawMoney(@RequestBody AccountDTO accountDTO) throws Exception {
        return accountService.withdrawMoney(accountDTO);
    }
    @PostMapping("/accounts/makeATransfer")
    public Compte makeATransfer(@RequestBody AccountTransferDTO accountTransferDTO) throws Exception {
        return accountService.makeATransfer(accountTransferDTO);
    }

    @DeleteMapping("/accounts/{accountNumber}")
    public void removeAccount(@PathVariable String accountNumber) {
        accountService.removeAccount(accountNumber);
    }

}
