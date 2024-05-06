package lk.ijse.restaurantManagement.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SalaryTm {
    private String salaryId;
    private String employeeId;
    private double amount;
    private String date;

}
