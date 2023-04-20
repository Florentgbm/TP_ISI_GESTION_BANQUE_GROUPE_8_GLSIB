package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.enumeration.Sexe;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom_du_client",nullable = false)
    private String lastname;
    @Column(name = "prenom_du_client",nullable = false)
    private String firstname;
    @Column(name = "date_de_naissance",nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate dateOfBirth;
    @Column(name = "sexe_du_client",nullable = false)
    private Sexe sexe;
    @Column(name = "adresse_du_client")
    private String address;
    @Column(name = "telephone_du_client", unique = true)
    private String telephone;
    @Column(name = "email_du_client", unique = true)
    private String email;
    @Column(name = "nationalite_du_client",nullable = false)
    private String nationality;
}
