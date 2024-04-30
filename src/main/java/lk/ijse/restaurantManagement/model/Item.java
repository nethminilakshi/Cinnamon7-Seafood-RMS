package lk.ijse.restaurantManagement.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Item {
    private String Id;
    private String description;
    private String qtyOnHand;
    private  String unitPrice;
    private String status;

}
