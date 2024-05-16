package lk.ijse.restaurantManagement.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ReservationCartTm {
    private String ReservationId;
    private String date;
    private  String time;
    private String tableId;
    private int tablesQty;
    private JFXButton btnRemove;
}
