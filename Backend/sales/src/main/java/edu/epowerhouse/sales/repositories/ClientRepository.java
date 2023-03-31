package edu.epowerhouse.sales.repositories;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.epowerhouse.sales.daos.ClientDAO;
import edu.epowerhouse.common.models.records.Client;

@Repository
public class ClientRepository {
    private final ClientDAO clientDAO;

    public ClientRepository(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public void createClient(Client client) throws SQLException {
        clientDAO.createClient(client);
    }

    public Client findClient(String nit) throws SQLException {
        return clientDAO.findClient(nit);
    }

    public void updateClient(Client client) throws SQLException {
        clientDAO.updateClient(client);
    }
}
