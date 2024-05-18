package lk.ijse.restaurantManagement.model.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ReservationDetailsTm {
    private String reservationId;
    private String tableId;
    private int reqTablesQty;
}
