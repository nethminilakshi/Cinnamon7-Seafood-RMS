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
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Salary;
import lk.ijse.restaurantManagement.model.tm.SalaryTm;
import lk.ijse.restaurantManagement.repository.EmployeeRepo;
import lk.ijse.restaurantManagement.repository.SalaryRepo;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.management.JMRuntimeException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private ComboBox<String> cmbEmployeeId;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private DatePicker txtDate;

    @FXML
    private JFXTextField txtSalaryId;


    @FXML
    private AnchorPane root;


    private List<Salary> salaryList = new ArrayList<>();

    public void initialize() {
        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        this.salaryList = getAllSalaries();
        setCellValueFactory();
        loadCustomerTable();  
        getEmployeeIdList();
        
    }

    private void getEmployeeIdList() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = EmployeeRepo.getIds();
            for (String id : idList) {
                obList.add(id);
            }

            cmbEmployeeId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Salary> getAllSalaries() {
        List<Salary> salaryList = null;
        try {
            salaryList = SalaryRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salaryList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadCustomerTable() {
        ObservableList<SalaryTm> tmList = FXCollections.observableArrayList();

        for (Salary salary : salaryList) {
            SalaryTm salaryTm = new SalaryTm(
                    salary.getSalaryId(),
                    salary.getEmployeeId(),
                    salary.getAmount(),
                    salary.getDate()
            );
            tmList.add(salaryTm);
        }
        tblSalary.setItems(tmList);
        System.out.println(tmList.toString());

    }

    public void tblOnClickAction(MouseEvent mouseEvent) {
        SalaryTm selectedItem = tblSalary.getSelectionModel().getSelectedItem();
        txtSalaryId.setText(selectedItem.getSalaryId());
        cmbEmployeeId.setValue(selectedItem.getEmployeeId());
        txtAmount.setText(String.valueOf(selectedItem.getAmount()));
        txtDate.setValue(LocalDate.parse(selectedItem.getDate()));
        
    }
    @FXML
    public void btnClearOnAction(ActionEvent actionEvent) {
        if (isValidate()) {
            clearFields();
        }
    }
    @FXML
    private void clearFields() {
        txtSalaryId.setText("");
        cmbEmployeeId.setValue("");
        txtAmount.setText("");
        txtDate.setValue(LocalDate.parse(""));
    }

     @FXML
     void btnSaveOnAction(ActionEvent actionEvent) {
         if (isValidate()) {
             String salaryId = txtSalaryId.getText();
             String employeeId = cmbEmployeeId.getValue();
             double amount = Double.parseDouble(txtAmount.getText());
             String date = String.valueOf(txtDate.getValue());


             Salary salary = new Salary(salaryId, employeeId, amount, date);

             try {

                 boolean isSaved = SalaryRepo.save(salary);
                 if (isSaved) {
                     new Alert(Alert.AlertType.CONFIRMATION, "Salary paid!").show();
                     clearFields();
                 }
             } catch (SQLException e) {
                 new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
             }
             clearFields();
             initialize();
         }
     }
    @FXML
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/main_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }
    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtSalaryId.setText(new SalaryRepo().autoGenarateSalaryId());
    }

    public void cmbIdOnAction(ActionEvent actionEvent) {
        String employeeId  = cmbEmployeeId.getValue();
        try {
            Salary salary = SalaryRepo.searchById(employeeId);
            if (salary != null) {
                txtSalaryId.setText(salary.getSalaryId());
                txtAmount.setText(String.valueOf(salary.getAmount()));
                txtDate.setValue(LocalDate.parse(salary.getDate()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnReceiptOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        if (isValidate()){
            JasperDesign jasperDesign =
                    JRXmlLoader.load("src/main/resources/reports/salaryPayments.jrxml");
            JasperReport jasperReport =
                    JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> data = new HashMap<>();
            data.put("salaryId",txtSalaryId.getText());
            data.put("employeeId",cmbEmployeeId.getValue());

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            data,
                            DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint,false);
    }
    }
    public void txtSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.SALARY,txtAmount);
    }
    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.AMOUNT,txtAmount))return false;
        return true;
    }
}
