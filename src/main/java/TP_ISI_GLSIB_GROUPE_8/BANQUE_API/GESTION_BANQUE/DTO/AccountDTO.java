package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.DTO;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.enumeration.TypeCompte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String accountNumber;
    private TypeCompte typeCompte;
    private LocalDate creationDate;
    private Double accountBalance;
    private Long idClient;
}
