package lk.ijse.restaurantManagement.model.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentTm {
   private String paymentId;
    private String  cusId;
    private String orderId;
   private String payMethod;
   private Double amount;
  // private Button payButton;


  //  {
    //    payButton = new Button("PaymentAction");
  //      payButton.setCursor(javafx.scene.Cursor.HAND);
  //  }
}
