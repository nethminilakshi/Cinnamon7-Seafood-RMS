package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRepo {
    public static boolean add(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO Reservation VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, reservation.getReserveId());
        pstm.setObject(2, reservation.getDescription());
        pstm.setObject(3, reservation.getCusId());
        pstm.setObject(4, reservation.getTableId());
        pstm.setObject(5,reservation.getReqTablesQty());
        pstm.setObject(6, reservation.getDate());
        pstm.setObject(7, reservation.getTime());
        pstm.setObject(8,reservation.getStatus());

        return pstm.executeUpdate()>0;
    }

    public static Reservation searchById(String Id) throws SQLException {
        String sql = "SELECT * FROM Reservation WHERE cusId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, Id);
        ResultSet resultSet = pstm.executeQuery();

        Reservation reservation = null;

        if (resultSet.next()) {
            String reserveId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String cusId = resultSet.getString(3);
            String tableId = resultSet.getString(4);
            int reqTablesQty = Integer.parseInt(resultSet.getString(5));
            String date = resultSet.getString(6);
            String time = resultSet.getString(7);
            String status = resultSet.getString(8);

            reservation = new Reservation(reserveId, description, cusId,tableId,reqTablesQty,date,time,status);
        }
        return reservation;
    }


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
