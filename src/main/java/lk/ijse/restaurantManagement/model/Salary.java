package lk.ijse.restaurantManagement.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Salary {
    private String salaryId;
    private String employeeId;
    private double amount;
    private String date;
}
