package lk.ijse.restaurantManagement.model.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerTm {

    private String cusId;
    private String name;
    private String address;
    private String contact;
    private String email;
}