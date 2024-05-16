package lk.ijse.restaurantManagement.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Reservation {
    private String reserveId;
    private String description;
    private String cusId;
    private String tableId;
    private int reqTablesQty;
    private String date;
    private String time;
    private String status;
}
