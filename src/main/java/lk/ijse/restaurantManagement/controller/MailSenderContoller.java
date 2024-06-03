package lk.ijse.restaurantManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.restaurantManagement.EmailSender;


public class MailSenderContoller {
    @FXML
    private TextArea Message;

    @FXML
    private Hyperlink hypGmail;

    @FXML
    private TextField txtGmail;

    @FXML
    private TextField txtTitle;




    public void btnSendOnAction(MouseEvent mouseEvent) {
        if (txtTitle.getText().equals("") || txtGmail.getText().equals("") || Message.getText().equals("")) {
            new Alert(Alert.AlertType.WARNING, "please insert detail !").show();
        } else {
            EmailSender emailSender = new EmailSender();
            boolean b = emailSender.sendEmail(txtGmail.getText(), txtTitle.getText(),Message.getText());
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

}
