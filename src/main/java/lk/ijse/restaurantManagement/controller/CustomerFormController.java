package lk.ijse.restaurantManagement.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.tm.CustomerTm;
import lk.ijse.restaurantManagement.model.tm.EmployeeTm;
import lk.ijse.restaurantManagement.model.tm.ItemTm;
import lk.ijse.restaurantManagement.repository.CustomerRepo;
import lk.ijse.restaurantManagement.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerFormController {
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CustomerTm> tblCustomers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;


    private List<Customer> customerList = new ArrayList<>();

    public void initialize() {

        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        this.customerList = getAllCustomers();
        setCellValueFactory();
        loadCustomerTable();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private List<Customer> getAllCustomers() {
        List<Customer> customerList = null;
        try {
            customerList = CustomerRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        for (Customer customer : customerList) {
            CustomerTm customerTm = new CustomerTm(
                    customer.getCusId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact()
            );
            tmList.add(customerTm);
        }
        tblCustomers.setItems(tmList);
        System.out.println(tmList.toString());
        /*CustomerTm selectedItem = tblCustomers.getSelectionModel().getSelectedItem();*/
        /*System.out.println(selectedItem.toString());*/
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String contact = txtContact.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(contact);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        initialize();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String cusId = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        Customer customer = new Customer(cusId, name, address,contact);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        initialize();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String cusId=txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        Customer customer = new Customer(cusId, name, address,contact);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }
    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtId.setText(new CustomerRepo().autoGenarateCustomerId());
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String contact = txtContact.getText();

        try {
            Customer customer = CustomerRepo.searchByContact(contact);

            if (customer != null) {
                txtId.setText(customer.getCusId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtContact.setText(customer.getContact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void tblOnClickAction(MouseEvent mouseEvent) {
        CustomerTm selectedItem = tblCustomers.getSelectionModel().getSelectedItem();
        txtId.setText(selectedItem.getCusId());
        txtName.setText(selectedItem.getName());
        txtAddress.setText(selectedItem.getAddress());
        txtContact.setText(selectedItem.getContact());

    }
}
