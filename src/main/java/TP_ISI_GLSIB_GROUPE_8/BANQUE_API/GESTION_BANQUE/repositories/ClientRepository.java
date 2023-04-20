package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.repositories;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
