package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.Item;
import lk.ijse.restaurantManagement.model.ReservationNew;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class newRepo {


    public static ReservationNew searchByDate(String date) throws SQLException {

        String sql = "SELECT t.tableId, t.noOfTables FROM Tables t LEFT JOIN Reservation_Details rd ON t.tableId = rd.tableId LEFT JOIN\n" +
                " Reservation r ON rd.reservationId = r.reservationId WHERE MONTH(r.date) != ? OR r.reservationId IS NULL";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,date);
        ResultSet resultSet = pstm.executeQuery();

        ReservationNew reservationNew = null;

        if (resultSet.next()) {
            String reservationId = resultSet.getString(1);
            String tableId = resultSet.getString(2);
            int noOfTables = Integer.parseInt(resultSet.getString(3));
            String Date = resultSet.getString(4);


            reservationNew = new ReservationNew(reservationId, tableId, noOfTables, date);
        }
        return reservationNew;
    }
}
