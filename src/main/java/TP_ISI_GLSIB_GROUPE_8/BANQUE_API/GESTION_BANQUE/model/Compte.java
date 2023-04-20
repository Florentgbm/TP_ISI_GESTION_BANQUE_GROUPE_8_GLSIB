package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.enumeration.TypeCompte;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
    @Id
    @Column(name="numero_compte",nullable = false)
    private String accountNumber;
    @Column(name="type_compte",nullable = false)
    private TypeCompte typeCompte;
    @Column(name = "date_de_creation",nullable = false)
    private LocalDate creationDate;
    @Column(name = "solde_compte")
    private Double accountBalance;

    @ManyToOne
    @JoinColumn(name = "proprietaire",nullable = false)
    private Client owner;

}
