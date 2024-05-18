package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Reservation;
import lk.ijse.restaurantManagement.model.reservationDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDetailRepo {
    public static boolean save(List<reservationDetails> rdList) throws SQLException {
        for (reservationDetails rd : rdList) {
            if(!save(rd)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(reservationDetails rd) throws SQLException {
        String sql = "INSERT INTO Reservation_Details VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, rd.getReservationId());
        pstm.setString(2, rd.getTableId());
        pstm.setInt(3, rd.getReqTablesQty());


        return pstm.executeUpdate() > 0;
    }


    public static List<reservationDetails> getAll() throws SQLException {
        String sql = "SELECT * FROM Reservation_details";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<reservationDetails> reservationDetailsList = new ArrayList<>();
        while (resultSet.next()) {
            String reservationId = resultSet.getString(1);
            String tableId = resultSet.getString(2);
            int reqTablesQty = Integer.parseInt(resultSet.getString(3));


            reservationDetails reservationDetails = new reservationDetails(reservationId, tableId, reqTablesQty);
            reservationDetailsList.add(reservationDetails);
        }
        return reservationDetailsList;
    }
}
