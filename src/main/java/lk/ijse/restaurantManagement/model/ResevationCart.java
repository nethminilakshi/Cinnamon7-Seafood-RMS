package lk.ijse.restaurantManagement.model;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResevationCart {
    private String ReservationId;
    private String date;
    private  String time;
    private String tableId;
    private int tablesQty;
    private JFXButton btnRemove;
}
