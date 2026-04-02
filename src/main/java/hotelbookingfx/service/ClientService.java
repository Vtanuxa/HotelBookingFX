package hotelbookingfx.service;

import hotelbookingfx.model.Client;
import hotelbookingfx.repository.ClientRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService() {
        this.clientRepository = new ClientRepository();
    }

    public Client addClient(String fullName, String phone, String email, String passport ){
        if (clientRepository.findByPhone(phone).isPresent()){
            System.out.println("Ошибка: Клиент с таким телефоном уже существует!");
            return null;
        }
        Client client  = new Client(fullName, phone, email, passport);
        return clientRepository.save(client);
    }

    public Optional<Client> findClientById(int id){
        return clientRepository.findById(id);
    }

    public Optional<Client> findClientByPhoneNumber(String phone){
        return clientRepository.findByPhone(phone);
    }


    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }


    public boolean updateClient(int id, String fullName, String phone, String email, String passport){
        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isPresent()){
            Client client = clientOpt.get();
            if (!client.getPhone().equals(phone)){
                if(clientRepository.findByPhone(phone).isPresent()){
                    System.out.println("Ошибка: Клиент с таким номером уже существует!");
                    return false;
                }
            }
            client.setFullName(fullName);
            client.setPhone(phone);
            client.setEmail(email);
            client.setPassport(passport);
            return true;
        }
        System.out.println("Ошибка: Клиент не найден!");
        return false;
    }


    public List<Client> searchClients(String fullName, String phone, String email, String passport){
        List<Client> results = clientRepository.findAll();
        if(fullName != null && !fullName.isEmpty()) {
            results.retainAll(clientRepository.findByName(fullName));
        }
        if (phone != null && !phone.isEmpty()) {
            results.retainAll(Collections.singleton(clientRepository.findByPhone(phone)));
        }

        if (email != null && !email.isEmpty()) {}

        return results;
    }

    public boolean deleteClient(int id){
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()){
            return clientRepository.delete(id);
        }
        return false;
    }

}
