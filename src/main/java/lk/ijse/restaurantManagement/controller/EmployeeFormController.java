package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Employee;
import lk.ijse.restaurantManagement.model.tm.EmployeeTm;
import lk.ijse.restaurantManagement.repository.CustomerRepo;
import lk.ijse.restaurantManagement.repository.EmployeeRepo;
import lk.ijse.restaurantManagement.repository.ItemRepo;
import lk.ijse.restaurantManagement.repository.SalaryRepo;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFormController {
    @FXML
    private ComboBox<String> cmbPosition;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableColumn<?, ?> colBasicSalary;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;
    private List<Employee> employeeList = new ArrayList<>();

    public void initialize() {
        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        this.employeeList = getAllEmployees();
        getPositionList();
        setCellValueFactory();
        loadEmployeeTable();
    }
    private String[] positionList={"Chef","Kitchen Helper", "Server"};

    public void getPositionList(){
        List<String> positionlist = new ArrayList<>();
        for(String data: positionList){
            positionlist.add(data);
        }
        ObservableList<String> obList= FXCollections.observableArrayList(positionlist);
        cmbPosition.setItems(obList);

    }

    private void loadEmployeeTable() {
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

        for (Employee employee : employeeList) {
            EmployeeTm employeeTm = new EmployeeTm(
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getContact(),
                    employee.getPositon(),
                    employee.getBasicSalary()
            );

            tmList.add(employeeTm);
        }
        tblEmployee.setItems(tmList);
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Employee> getAllEmployees() {
        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/main_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if(isValidate()){
        String employeeId= txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String position = String.valueOf(cmbPosition.getValue());
        String basicSalary = txtSalary.getText();

        Employee employee = new Employee(employeeId, name, address, contact, position, basicSalary);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        initialize();
//        now we should persist our customer model
    }}

    public void txtSearchOnAction(ActionEvent actionEvent) {

            String contact = txtContact.getText();

            try {
                Employee employee = EmployeeRepo.searchByContact(contact);

                if (employee != null) {
                    txtId.setText(employee.getEmployeeId());
                    txtName.setText(employee.getName());
                    txtAddress.setText(employee.getAddress());
                    txtContact.setText(employee.getContact());
                    cmbPosition.setValue(employee.getPositon());
                    txtSalary.setText(employee.getBasicSalary());

                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Not Found Customer").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
            initialize();
        }


    public void btnUpdateOnAction(ActionEvent event) {
        if (isValidate()){
        String employeeId=txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String position = String.valueOf(cmbPosition.getValue());
        String basicSalary = txtSalary.getText();

        Employee employee = new Employee(employeeId, name, address,contact, position, basicSalary);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        initialize();
    }
}

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        cmbPosition.setValue("");
        txtSalary.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        if (isValidate()) {
            clearFields();
        }
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (isValidate()){
        String contact= txtContact.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(contact);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
        initialize();
        }
    }

    public void tblClickOnAction(MouseEvent mouseEvent) {
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        txtId.setText(selectedItem.getEmployeeId());
        txtName.setText(selectedItem.getName());
        txtAddress.setText(selectedItem.getAddress());
        txtContact.setText(selectedItem.getContact());
        cmbPosition.setValue(selectedItem.getPosition());
        txtSalary.setText(selectedItem.getBasicSalary());
    }
    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtId.setText(new EmployeeRepo().autoGenarateEmployeeId());
    }


    public void txtEmployeeContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.CONTACT,txtContact);
    }

    public void txtEmployeeAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.ADDRESS,txtAddress);
    }

    public void txtEmployeeNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtName);
    }

    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.NAME,txtName))return false;
        if(!Regex.setTextColor(TextField.CONTACT,txtContact))return false;
        if(!Regex.setTextColor(TextField.ADDRESS,txtAddress))return false;

        return true;
    }
}
