package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferDTO {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Long senderIdClient;
    private Long receiverIdClient;
    private Double amount;
}
