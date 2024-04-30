package lk.ijse.restaurantManagement.model;

import lombok.*;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceOrder {
    private Order order;
    private List<OrderDetail> odList;


}
