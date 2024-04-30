package lk.ijse.restaurantManagement.model.tm;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class ItemTm {
    private String Id;
    private String description;
    private String qtyOnHand;
    private  String unitPrice;
    private String status;
}
