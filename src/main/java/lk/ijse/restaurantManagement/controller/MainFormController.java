package lk.ijse.restaurantManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane root;

    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));

        Pane registerPane = fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

        // setButtonActive(btnHome);
    }
    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        //  setButtonActive(btnHome);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        root.getChildren().clear();
        root.getChildren().add(registerPane);
    }

    public void btnIReservationOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/reservation_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        root.getChildren().clear();
        root.getChildren().add(registerPane);
    }

    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/placeOrder_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        root.getChildren().clear();
        root.getChildren().add(registerPane);
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/salary_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        root.getChildren().clear();
        root.getChildren().add(registerPane);
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        root.getChildren().clear();
        root.getChildren().add(registerPane);
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/item_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        root.getChildren().clear();
        root.getChildren().add(registerPane);
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customer_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        root.getChildren().clear();
        root.getChildren().add(registerPane);
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        Pane registerPane = fxmlLoader.load();
        root.getChildren().clear();
        root.getChildren().add(registerPane);
    }
}