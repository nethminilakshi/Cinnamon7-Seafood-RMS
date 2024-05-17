package lk.ijse.restaurantManagement.model;

import lombok.*;

import java.sql.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Order {
    private String orderId;
    private String orderType;
    private String cusId;
    private String date;
}
