package edu.epowerhouse.sales.services;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import edu.epowerhouse.common.models.records.Client;
import edu.epowerhouse.sales.repositories.ClientRepository;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void createClient(Client client) throws SQLException {
        clientRepository.createClient(client);
    }

    public Client findClient(String nit) throws SQLException {
        return clientRepository.findClient(nit);
    }

    public void updateClient(Client client) throws SQLException {
        clientRepository.updateClient(client);
    }
}
