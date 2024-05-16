package lk.ijse.restaurantManagement.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ReservationTm {
    private String ReservationId;
    private String description;
    private String cusId;
    private String date;
    private  String time;
    private String status;

}
