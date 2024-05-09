package lk.ijse.restaurantManagement.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TablesTm {
    private String tableId;
    private String description;
    private int noOfSeats;
}
