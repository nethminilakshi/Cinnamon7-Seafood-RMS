package lk.ijse.restaurantManagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.restaurantManagement.repository.ReservationRepo;
import lk.ijse.restaurantManagement.repository.SalaryRepo;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ReservationFormController {
    @FXML
    private TextField txtCustomerId;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtTableId;

    @FXML
    private TextField txtTime;
    @FXML
    private AnchorPane root;

    public void initialize(){

        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void searchOnAction(ActionEvent event) {

    }

    public void btnDeleteOnAction(javafx.event.ActionEvent actionEvent) {
    }

    public void btnAddOnAction(javafx.event.ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(javafx.event.ActionEvent actionEvent) {
    }

    public void btnBackOnAction(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtReservationId.setText(new ReservationRepo().autoGenarateSalaryId());
    }

    public void searchOnAction() {
    }
}
