package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.DTO.AccountDTO;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.enumeration.Sexe;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.enumeration.TypeCompte;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model.Client;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.repositories.AccountRepository;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.repositories.ClientRepository;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class GestionBanqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionBanqueApplication.class, args);
	}

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	AccountService accountService;
	@Autowired
	ClientRepository clientRepository;
	@Bean
	CommandLineRunner start(){
		return args -> {
			clientRepository.save(
					new Client(
							null,
							"GBEMOU",
							"Florent",
							LocalDate.of(2002,11,25),
							Sexe.MASCULIN,
							"Adidoadin",
							"97268800",
							"mergogbemou@gmail.com",
							"Togolaise")
			);
			clientRepository.save(
					new Client(
							null,
							"AKPAN",
							"Elikem",
							LocalDate.of(2002,1,15),
							Sexe.MASCULIN,
							"Tokoin",
							"00000000",
							"elikem@gmail.com",
							"Togolaise")
			);
			accountService.saveAccount(
					new AccountDTO(
							null,
							TypeCompte.COURANT,
							LocalDate.now(),
							0.0,
							1L
					)
			);
			accountService.saveAccount(
					new AccountDTO(
							null,
							TypeCompte.COURANT,
							LocalDate.now(),
							0.0,
							2L
					)
			);
		};
	}
}
