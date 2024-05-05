package lk.ijse.restaurantManagement.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeTm {
    private String employeeId;
    private String name;
    private String address;
    private String contact;
    private  String position;
    private  String basicSalary;
}
