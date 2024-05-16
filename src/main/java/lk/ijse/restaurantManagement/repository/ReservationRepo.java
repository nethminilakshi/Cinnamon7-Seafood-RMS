package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Customer;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepo {
    public static boolean add(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO Reservation VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, reservation.getReservationId());
        pstm.setObject(2, reservation.getDescription());
        pstm.setObject(3, reservation.getCusId());
        pstm.setObject(4, reservation.getDate());
        pstm.setObject(5, reservation.getTime());
        pstm.setObject(6,reservation.getStatus());


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
            String reservationId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String cusId = resultSet.getString(3);
            String date = resultSet.getString(4);
            String time = resultSet.getString(5);
            String status = resultSet.getString(6);


            reservation = new Reservation(reservationId, description, cusId,date,time,status);
        }
        return reservation;
    }

    public static List<Reservation> getAll() throws SQLException {
        String sql = "SELECT * FROM Reservation";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Reservation> reservationList = new ArrayList<>();
        while (resultSet.next()) {
            String reservationId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String cusId = resultSet.getString(3);
            String date = resultSet.getString(4);
            String time = resultSet.getString(5);
            String status = resultSet.getString(6);

            Reservation reservation = new Reservation(reservationId, description, cusId, date,time, status);
            reservationList.add(reservation);
        }
        return reservationList;
    }





    public String autoGenarateReservationId() throws SQLException {
        String sql = "SELECT reservationId from Reservation order by reservationId desc limit 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String reserveId = resultSet.getString("reservationId");
            String numericPart = reserveId.replaceAll("\\D+", "");
            int newReservationId = Integer.parseInt(numericPart) + 1;
            return String.format("R%03d", newReservationId);

        } else {
            return "R001";
        }
    }
}
