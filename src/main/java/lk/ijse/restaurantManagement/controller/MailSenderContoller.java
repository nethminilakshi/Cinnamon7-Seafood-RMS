package lk.ijse.restaurantManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.restaurantManagement.sendEmail;

import javax.mail.MessagingException;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class MailSenderContoller {
    @FXML
    private TextArea Message;

    @FXML
    private Hyperlink hypGmail;

    @FXML
    private TextField txtGmail;

    @FXML
    private TextField txtTitle;

    @FXML
    void initialize() {
        hypGmail.setText(CustomerFormController.gmail);
    }

    public void btnSendOnAction(MouseEvent mouseEvent) {
        if (txtTitle.getText().equals("") || txtGmail.getText().equals("") || Message.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "please insert detail !").show();
        } else {
            boolean b = sendMail(txtTitle.getText(), Message.getText(), txtGmail.getText());
            if (b) {
                new Alert(Alert.AlertType.CONFIRMATION, "send !").show();
            }
        }
    }

    public void mouseEnter(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof javafx.scene.image.ImageView) {
            javafx.scene.image.ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
//            glow.setColor(Color.valueOf("#EF233C"));
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(15);
            glow.setHeight(15);
            glow.setRadius(15);
            icon.setEffect(glow);
        }

    }

    public void mouseExit(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

    private boolean sendMail(String title, String message, String gmail) {
        try {
            new sendEmail().sendMail(title, message, gmail);
            return true;
        } catch (IOException | MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        }
    }
}
