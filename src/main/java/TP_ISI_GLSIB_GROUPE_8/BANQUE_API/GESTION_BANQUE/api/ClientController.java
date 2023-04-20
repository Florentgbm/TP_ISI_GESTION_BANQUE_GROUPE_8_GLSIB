package TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.api;

import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model.Client;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.model.Compte;
import TP_ISI_GLSIB_GROUPE_8.BANQUE_API.GESTION_BANQUE.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public List<Client> listClients() {
        return clientService.getAllClient();
    }

    @GetMapping("/clients/{id}")
    public Client showClientById(@PathVariable("id") long id) throws Exception {
        return clientService.findOneClient(id);
    }
    @GetMapping("/clients/{id}/accounts")
    public List<Compte> getAllAccountOfTheClient(@PathVariable("id") Long id){
        return clientService.getAllClientAccounts(id);
    }
    @PostMapping("/clients")
    public Client saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @PatchMapping("/clients/{id}")
    public Client updateClient(@PathVariable("id") Long id, @RequestBody Map<String,Object> fields) throws Exception {
        return clientService.updateClient(id,fields);
    }
    @DeleteMapping("/clients/{id}")
    public void removeClient(@PathVariable long id) {
        clientService.removeClient(id);
    }

}
