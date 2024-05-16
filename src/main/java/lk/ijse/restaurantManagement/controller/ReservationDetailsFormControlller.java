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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.Reservation;
import lk.ijse.restaurantManagement.model.tm.ItemTm;
import lk.ijse.restaurantManagement.model.tm.ReservationTm;
import lk.ijse.restaurantManagement.repository.ItemRepo;
import lk.ijse.restaurantManagement.repository.ReservationRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDetailsFormControlller {

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableView<ReservationTm> tblReservation;

    @FXML
    private AnchorPane root;
    private List<Reservation> ReservationList=new ArrayList<>();
    public void initialize() {

        this.ReservationList=getAllReservations();
        setCellValueFactory();
        loadItemTable();

    }

    private void loadItemTable() {
        ObservableList<ReservationTm> tmList = FXCollections.observableArrayList();

        for (Reservation reservation : ReservationList) {
            ReservationTm reservationTm = new ReservationTm(
                    reservation.getReservationId(),
                    reservation.getDescription(),
                    reservation.getCusId(),
                    reservation.getDate(),
                    reservation.getTime(),
                    reservation.getStatus()
            );


            tmList.add(reservationTm);
        }
        tblReservation.setItems(tmList);
        ReservationTm selectedItem = tblReservation.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private void setCellValueFactory() {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private List<Reservation> getAllReservations() {
        List<Reservation> reservationList = null;
        try {
             reservationList= ReservationRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }

    @FXML
   public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/reservation_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

}
