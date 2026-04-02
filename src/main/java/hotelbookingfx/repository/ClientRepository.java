package hotelbookingfx.repository;

import hotelbookingfx.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hotelbookingfx.connection.DBConnection.connection;

public class ClientRepository {

    private List<Client> clients = new ArrayList<>();

    public Client save(Client client) {
        clients.add(client);
        return client;
    }

    public Optional<Client> findById(int id) {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findFirst();
    }

    public Optional<Client> findByPhone(String phone) {
        return clients.stream()
                .filter(client -> client.getPhone().equals(phone))
                .findFirst();
    }

    public List<Client> findByName(String namePart) {
        return clients.stream()
                .filter(client -> client.getFullName().toLowerCase().contains(namePart.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Client> findAll() {
        return new ArrayList<>(clients);
    }

    public boolean update(Client updatedClient) {
        Optional<Client> existingClient = findById(updatedClient.getId());
        if (existingClient.isPresent()) {
            Client client = existingClient.get();
            client.setFullName(updatedClient.getFullName());
            client.setPhone(updatedClient.getPhone());
            client.setPassport(updatedClient.getPassport());
            client.setEmail(updatedClient.getEmail());
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        return clients.removeIf(client -> client.getId() == id);
    }

    public void clear() {
        clients.clear();
    }
}


//    public Client save(Client client) {
//        String saveClient = "INSERT INTO clients (fullName, phone, email, passport) VALUES (?, ?, ?, ?)";
//
//        try (Connection connection = connection();
//             PreparedStatement preparedStatement = connection.prepareStatement(saveClient, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, client.getFullName());
//            preparedStatement.setString(2, client.getPhone());
//            preparedStatement.setString(3, client.getEmail());
//            preparedStatement.setString(4, client.getPassport());
//            preparedStatement.execute();
//
//            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    client.setId(generatedKeys.getInt(1));
//                }
//            }
//            System.out.println("Новый клиент успешно добавлен");
//            return  client;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Optional <Client> findById(int id) {
//        String findClientId = "SELECT * FROM clients WHERE id = ?";
//        try (Connection connection = connection();
//             PreparedStatement preparedStatement = connection.prepareStatement(findClientId)) {
//            preparedStatement.setInt(1, id);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    Client client = new Client(
//                            resultSet.getString("fullName"),
//                            resultSet.getString("phone"),
//                            resultSet.getString("email"),
//                            resultSet.getString("passport"));
//                    client.setId(resultSet.getInt(1));
//
//                    return Optional.of(client);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<Client> findByEmail(String email) {
//        String findClientEmail = "SELECT * FROM clients WHERE email = ?";
//        try (Connection connection = connection();
//             PreparedStatement preparedStatement = connection.prepareStatement(findClientEmail)) {
//            preparedStatement.setString(1, email);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    Client client = new Client(
//                            resultSet.getString("fullName"),
//                            resultSet.getString("phone"),
//                            resultSet.getString("email"),
//                            resultSet.getString("passport"));
//                    client.setId(resultSet.getInt("id"));
//                    return Optional.of(client);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<Client> findByPhone(String phone) {
//        String findClientPhone = "SELECT * FROM clients WHERE phone = ?";
//        try (Connection connection = connection();
//             PreparedStatement preparedStatement = connection.prepareStatement(findClientPhone)) {
//
//            preparedStatement.setString(1, phone);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    Client client = new Client(
//                            resultSet.getString("fullName"),
//                            resultSet.getString("phone"),
//                            resultSet.getString("email"),
//                            resultSet.getString("passport"));
//                    client.setId(resultSet.getInt("id"));
//                    return Optional.of(client);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//
//
//    public List<Client> findAll() {
//        List<Client> clients = new ArrayList<>();
//        String findAllClients = "SELECT * FROM clients";
//
//        try (Connection connection = connection();
//             PreparedStatement preparedStatement = connection.prepareStatement(findAllClients);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            while (resultSet.next()) {
//                Client client = new Client(
//                        resultSet.getString("fullName"),
//                        resultSet.getString("phone"),
//                        resultSet.getString("email"),
//                        resultSet.getString("passport"));
//                client.setId(resultSet.getInt("id"));
//                clients.add(client);
//            }
//            return clients;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public boolean update(Client updateClient) {
//        String updateRoomSql = "UPDATE clients SET fullName = ?, phone = ?, email = ?, passport = ? WHERE id = ?";
//        try (Connection connection = connection();
//             PreparedStatement preparedStatement = connection.prepareStatement(updateRoomSql)) {
//
//            preparedStatement.setString(2, updateClient.getFullName());
//            preparedStatement.setString(3, updateClient.getPhone());
//            preparedStatement.setString(4, updateClient.getEmail());
//            preparedStatement.setString(5, updateClient.getPassport());
//            preparedStatement.setInt(1, updateClient.getId());
//            return preparedStatement.executeUpdate() == 1;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public boolean delete(int id) {
//        String deleteClient = "DELETE FROM clients WHERE id = ?";
//        try (Connection connection = connection();
//             PreparedStatement preparedStatement = connection.prepareStatement(deleteClient)) {
//            preparedStatement.setInt(1, id);
//            preparedStatement.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

