package lk.ijse.restaurantManagement.repository;

import lk.ijse.restaurantManagement.db.DbConnection;
import lk.ijse.restaurantManagement.model.PlaceReservation;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceReservationRepo {

    public static boolean placeReservation(PlaceReservation ps) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isReservationSaved = ReservationRepo.add(ps.getReservation());
            if (isReservationSaved) {
                boolean isReservationDetailSaved = ReservationDetailRepo.save(ps.getReservationDetailsList());
                if (isReservationDetailSaved) {
                    boolean isTableQtyUpdate = TablesRepo.updateQty(ps.getReservationDetailsList());
                    if (isTableQtyUpdate) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
    }

