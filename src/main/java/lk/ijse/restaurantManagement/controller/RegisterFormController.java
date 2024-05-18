package lk.ijse.restaurantManagement.controller;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.util.Regex;
import lk.ijse.restaurantManagement.util.TextField;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;



    public void btnRegisterOnAction(ActionEvent actionEvent) {
if (isValidate()) {
    String userId = txtUserId.getText();
    String userName = txtUserName.getText();
    String password = txtPassword.getText();

    saveUser(userId, userName, password);
}
        
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

    public void txtPasswordOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.PW,txtPassword);
    }

    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.NAME,txtUserName);
    }

    public void txtUserIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.ID,txtUserId);
    }
    public boolean isValidate(){
        if(!Regex.setTextColor(TextField.ID,txtUserId))return false;
        if(!Regex.setTextColor(TextField.NAME,txtUserName))return false;
        if(!Regex.setTextColor(TextField.PW,txtPassword))return false;
        return true;
    }
}
