package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.repositories;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Compte,String> {
    @Query("SELECT a FROM Compte a WHERE a.owner.id=:idClient")
    List<Compte> findAllByClientId(@Param("idClient") Long idClient);
}
