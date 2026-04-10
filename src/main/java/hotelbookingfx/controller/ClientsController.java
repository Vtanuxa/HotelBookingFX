package hotelbookingfx.controller;

import hotelbookingfx.controller.ClientDialogController;
import hotelbookingfx.model.Client;
import hotelbookingfx.repository.ClientRepository;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {
    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TextField searchField;

    private ClientRepository clientRepository;
    private ObservableList<Client> clientList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientRepository = ClientRepository.getInstance();
        clientList = FXCollections.observableArrayList();
        setupTableColumns();
        loadClientsData();
    }

    private void setupTableColumns() {
        TableColumn<Client, Integer> clientId = (TableColumn<Client, Integer>) clientTable.getColumns().get(0);
        TableColumn<Client, String> fullNameColumn = (TableColumn<Client, String>) clientTable.getColumns().get(1);
        TableColumn<Client, String> phoneColumn = (TableColumn<Client, String>) clientTable.getColumns().get(2);
        TableColumn<Client, String> emailColumn = (TableColumn<Client, String>) clientTable.getColumns().get(3);
        TableColumn<Client, String> passportColumn = (TableColumn<Client, String>) clientTable.getColumns().get(4);

        clientId.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            return client == null ? new SimpleIntegerProperty(0).asObject()
                    : new SimpleIntegerProperty(client.getId()).asObject();
        });

        fullNameColumn.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            return client == null ? new SimpleStringProperty("")
                    : new SimpleStringProperty(client.getFullName());
        });

        phoneColumn.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            return client == null ? new SimpleStringProperty("")
                    : new SimpleStringProperty(client.getPhone());
        });

        emailColumn.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            return client == null ? new SimpleStringProperty("")
                    : new SimpleStringProperty(client.getEmail());
        });


        passportColumn.setCellValueFactory(cellData -> {
            Client client = cellData.getValue();
            return client == null ? new SimpleStringProperty("")
                    : new SimpleStringProperty(client.getPassport());
        });
    }

    private void loadClientsData(){
        List<Client> clients = clientRepository.findAll();
        clientList.clear();
        clientList.addAll(clients);
        clientTable.setItems(clientList);
    }

    @FXML
    private void handleAddClient(){
        showClientDialog(null);
    }

    private void showClientDialog(Client client){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotelbookingfx/client-dialog.fxml"));
            DialogPane dialogPane = loader.load();
            ClientDialogController dialogController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle(client ==  null ? "Добавление комнаты" : "Редактирование комнаты");

            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Client newClient = dialogController.getClientData();
                if (newClient == null){
                    System.out.println("Комната не сохранена");
                }else{
                    clientRepository.save(newClient);
                    loadClientsData();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}