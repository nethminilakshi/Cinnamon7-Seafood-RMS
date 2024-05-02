package lk.ijse.restaurantManagement.model;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDetail {
    private String orderId;
    private String itemId;
    private int qty;
    private double unitPrice;


}
