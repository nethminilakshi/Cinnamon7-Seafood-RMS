package lk.ijse.restaurantManagement.model;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {
    private String id;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private String date;
    private JFXButton btnRemove;
}
