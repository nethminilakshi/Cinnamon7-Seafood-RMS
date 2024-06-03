package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.model.*;
import lk.ijse.restaurantManagement.model.tm.ReservationCartTm;
import lk.ijse.restaurantManagement.repository.*;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ReservationFormController {


    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbTableId;

    @FXML
    private ComboBox<String> cmbTimeSlot;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colRequiredTableQty;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colTableId;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ReservationCartTm> tblReservationCart;

    @FXML
    private JFXTextField txtAvailableQty;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private DatePicker txtDate;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtRequiredQty;

    @FXML
    private JFXTextField txtReservationId;

    private final ObservableList<ReservationCartTm> cartList = FXCollections.observableArrayList();
    public void initialize(){

        setCellValueFactory();
        loadTable();
        getStatus();
        getTimeSlot();
        getTableIds();

        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void setCellValueFactory() {
       colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
       colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
       colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
       colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
       colRequiredTableQty.setCellValueFactory(new PropertyValueFactory<>("tablesQty"));
       colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void loadTable() {
        ObservableList<ReservationCartTm> tmList = FXCollections.observableArrayList();

        for (ReservationCartTm cart : cartList) {
            ReservationCartTm cartTm = new ReservationCartTm(
                    cart.getReservationId(),
                    cart.getDate(),
                    cart.getTime(),
                    cart.getTableId(),
                    cart.getTablesQty(),
                    cart.getBtnRemove()
            );


            tmList.add(cartTm);
        }
        tblReservationCart.setItems(tmList);
        ReservationCartTm selectedItem = tblReservationCart.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private String[] Status={"available","Reserved"};

    public void getStatus(){
        List<String> statusList = new ArrayList<>();
        for(String data: Status){
            statusList.add(data);
        }
        ObservableList<String> obList= FXCollections.observableArrayList(statusList);
        cmbStatus.setItems(obList);
    }

    private String[] timeSlot={"morning","afternoon","evening","night"};

    public void getTimeSlot(){
        List<String> timeList = new ArrayList<>();
        for(String data: timeSlot){
            timeList.add(data);
        }
        ObservableList<String> obList= FXCollections.observableArrayList(timeList);
        cmbTimeSlot.setItems(obList);
    }

    private void getTableIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = TablesRepo.getIds();
            for (String code : idList) {
                obList.add(code);
            }

            cmbTableId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        if (isValidate()){
        String reservationId = txtReservationId.getText();
        String date = String.valueOf(txtDate.getValue());
        String time = String.valueOf(cmbTimeSlot.getValue());
        String tableId = String.valueOf(cmbTableId.getValue());
        int tablesQty = Integer.parseInt(txtRequiredQty.getText());
        JFXButton btnRemove = new JFXButton("cancel");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to cancel?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblReservationCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);

                tblReservationCart.refresh();

            }
        });

        for (int i = 0; i < tblReservationCart.getItems().size(); i++) {
            if (tableId.equals(colTableId.getCellData(i))) {
                tablesQty += cartList.get(i).getTablesQty();

                cartList.get(i).setTablesQty(tablesQty);

                tblReservationCart.refresh();

                txtRequiredQty.setText("");
                return;
            }
        }

        ReservationCartTm cartTm = new ReservationCartTm(reservationId, date, time, tableId, tablesQty, btnRemove);

        cartList.add(cartTm);

        tblReservationCart.setItems(cartList);
        txtRequiredQty.setText("");

    }}

    @FXML
    public void btnAddOnAction(ActionEvent actionEvent) {
        if (isValidate()){
        String reservationId = txtReservationId.getText();
        String description= txtDescription.getText();
        String cusId = txtCustomerId.getText();
        String date = String.valueOf(txtDate.getValue());
        String time = cmbTimeSlot.getValue();
        String status = cmbStatus.getValue();

        var reservation = new Reservation(reservationId,description, cusId, date,time,status);

        List<reservationDetails> reservationDetailsList = new ArrayList<>();
        for (int i = 0; i < tblReservationCart.getItems().size(); i++) {
            ReservationCartTm tm = cartList.get(i);
            reservationDetails od = new reservationDetails(
                    reservationId,
                    tm.getTableId(),
                    tm.getTablesQty()

            );
            reservationDetailsList.add(od);
        }

        PlaceReservation ps = new PlaceReservation(reservation, reservationDetailsList);
        try {
            boolean isPlaced = PlaceReservationRepo.placeReservation(ps);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Reservation Ok!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Reservation not placed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
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
    public void btnClearOnAction(ActionEvent actionEvent) {
        if (isValidate()){
        clearFields();
    }}

    private void clearFields() {
        txtDescription.setText("");
       txtDate.setValue(LocalDate.parse(""));
        txtAvailableQty.setText("");
        cmbTableId.setValue("");
        txtRequiredQty.setText("");
        cmbStatus.setValue("");
        cmbTimeSlot.setValue("");
        txtCustomerId.setText("");
        txtContact.setText("");
    }


    @FXML
    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtReservationId.setText(new ReservationRepo().autoGenarateReservationId());
    }


    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }


    public void searchContactOnAction(ActionEvent actionEvent) {
        String contact  = txtContact.getText();

        try {
            Customer customer = CustomerRepo.searchByContact(contact);

            if (customer != null) {
                txtCustomerId.setText(customer.getCusId());
                txtContact.setText(customer.getContact());
            } else {
                    new Alert(Alert.AlertType.INFORMATION, "Not Found Customer").show();
                }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    public void searchOnIdAction(ActionEvent actionEvent) {
        String cusId  = txtCustomerId.getText();

        try {
            Reservation reservation = ReservationRepo.searchById(cusId);

            if (reservation != null) {
                txtReservationId.setText(reservation.getReservationId());
                txtDescription.setText(reservation.getDescription());
                txtCustomerId.setText(reservation.getCusId());
                txtDate.setValue(LocalDate.parse(reservation.getDate()));
                cmbTimeSlot.setValue(reservation.getTime());



            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        initialize();
    }

    public void cmbTablesOnAction(ActionEvent actionEvent) {
        String tableId = cmbTableId.getValue();
        try {
            Tables tables = TablesRepo.searchById(tableId);
            if (tables != null) {
                txtAvailableQty.setText(String.valueOf(tables.getNoOfTables()));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        txtRequiredQty.requestFocus();
    }

    public void searchOnAction(ActionEvent actionEvent) {
        String date  = String.valueOf(txtDate.getValue());

        try {
            ReservationNew reseve = newRepo.searchByDate(date);

            if (reseve  != null) {
                txtReservationId.setText(reseve.getReservationId());
                cmbTableId.setValue(reseve.getTableId());
                txtAvailableQty.setText(String.valueOf(reseve.getNoOfTables()));
                txtDate.setValue(LocalDate.parse(reseve.getDate()));

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        initialize();
    }
    public void btnNextOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/mailSender_form.fxml"));
        Stage stage = new Stage();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Mail Sender Form");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.centerOnScreen();
        stage.show();
    }
    public void btnReservationDetails(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/resevationDetails_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("ReservationDetails Form");
        stage.centerOnScreen();
    }

    public void txtDescOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.DESC,txtDescription);
    }

    public void txtAContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.CONTACT,txtContact);
    }

    public void txtQtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.QTY,txtRequiredQty);
    }
    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.DESC,txtDescription))return false;
        if(!Regex.setTextColor(TextField.CONTACT,txtContact))return false;
        if(!Regex.setTextColor(TextField.QTY,txtRequiredQty))return false;
        return true;
    }
}
