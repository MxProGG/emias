package sql;

import java.sql.*;

public class DataSQL {

    private static String connectionUrl = "jdbc:sqlserver://192.168.7.253:64783";
    private static String databaseName = "test_mo_hlt_Taldom_CRB_20190129";
    private static String userName = "sa";
    private static String password = "HkyeZfiCkO8";


    public  static void updateResultMSE(int idExam) throws ClassNotFoundException {
        String url = connectionUrl +
                ";databaseName=" + databaseName +
                ";user=" + userName +
                ";password=" + password;

        try {

            try (Connection connection = DriverManager.getConnection(url)) {
                String sql = "update dmg_mse_Examination set rf_mse_statusmseid='6' where mse_examinationid='" + idExam + "'";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                }
            } catch (SQLException s) {s.printStackTrace();}
        } catch (Exception e) {
            e.printStackTrace();  }

    }
}