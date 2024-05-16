package lk.ijse.restaurantManagement.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Reservation {
    private String reservationId;
    private String description;
    private String cusId;
    private String date;
    private String time;
    private String status;

}
