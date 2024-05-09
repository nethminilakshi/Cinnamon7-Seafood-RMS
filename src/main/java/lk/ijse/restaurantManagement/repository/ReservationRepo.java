package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRepo {
    public String autoGenarateSalaryId() throws SQLException {
        String sql = "SELECT reserveId from Reservation order by reserveId desc limit 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String reserveId = resultSet.getString("reserveId");
            String numericPart = reserveId.replaceAll("\\D+", "");
            int newReservationId = Integer.parseInt(numericPart) + 1;
            return String.format("R%03d", newReservationId);

        } else {
            return "R001";
        }
    }
}
