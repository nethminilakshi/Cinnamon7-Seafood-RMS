package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXBadge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.restaurantManagement.db.DbConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUserId;



    public void btnRegisterOnAction(ActionEvent actionEvent) {

        String userId = txtUserId.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        saveUser(userId, userName, password);
        
        
    }

    private void saveUser(String userId, String userName, String password) {

        try {
            String sql = "INSERT INTO users VALUES(?, ?, ?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1, userId);
            pstm.setObject(2, userName);
            pstm.setObject(3, password);

            if(pstm.executeUpdate() > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happend!").show();
        }
    }
}
