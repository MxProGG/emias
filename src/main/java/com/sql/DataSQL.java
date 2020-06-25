package com.sql;

import java.sql.*;

public class DataSQL {

    private static String connectionUrl = "jdbc:sqlserver://192.168.7.253:64783";
    private static String databaseName = "test_mo_hlt_Taldom_CRB_20190129";
    private static String userName = "sa";
    private static String password = "HkyeZfiCkO8";

    public static int getExamId(int status) {
        String url = connectionUrl +
                ";databaseName=" + databaseName +
                ";user=" + userName +
                ";password=" + password;
        //ResultSet rs = null;
            try (Connection connection = DriverManager.getConnection(url)) {
                String sql = "select mse_ExaminationID from dmg_mse_Examination where rf_MKABID='2662331' and rf_mse_StatusMseID='" + status + "'";
                try (Statement statement = connection.createStatement()) {
                   ResultSet rs= statement.executeQuery(sql);
                    rs.next();
                    return rs.getInt(1);
                }
            } catch (SQLException s) {s.printStackTrace();}
        return 999;
    }

    public  static void setStatusMSE(int idExam,int idStatus) {
      /*01	Сформирован
        02	Подписан
        03	Отправлен
        04	Ошибка при отправке
        05	Зарегистрирован
        06	Ошибка регистрации
        07	Аннулирован*/
        String url = connectionUrl +
                ";databaseName=" + databaseName +
                ";user=" + userName +
                ";password=" + password;

        try {
            try (Connection connection = DriverManager.getConnection(url)) {
                String sql = "update dmg_mse_Examination set rf_mse_statusmseid='" + idStatus + "' where mse_examinationid='" + idExam + "'";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                }
            } catch (SQLException s) {s.printStackTrace();}
        } catch (Exception e) {
            e.printStackTrace();  }

    }

    public  static void deleteDirectionMSE(int idExam) {
        String url = connectionUrl +
                ";databaseName=" + databaseName +
                ";user=" + userName +
                ";password=" + password;

        try {
            try (Connection connection = DriverManager.getConnection(url)) {
                String sql = "-- Идентификатор удаляемого направления (dmg_mse_Examination)\n" +
                        "declare @examId int =\n" + idExam +
                        " \n" +
                        "declare @resultId int = isnull((select rf_mse_ResultMSEID from dmg_mse_Examination where mse_ExaminationID = @examId), 0)\n" +
                        " \n" +
                        "-- таблицы ссылающиеся на направление\n" +
                        "delete from dmg_mse_ExaminationPurposes where rf_mse_ExaminationID = @examId and mse_ExaminationPurposesID > 0\n" +
                        "delete from dmg_mse_ProfAccidentsLossDegree where rf_mse_ExaminationID = @examId and mse_ProfAccidentsLossDegreeID > 0\n" +
                        " \n" +
                        "delete from dmg_mse_LabResearchParam where rf_mse_LabResearchID > 0 and rf_mse_LabResearchID in\n" +
                        "    (select mse_LabResearchID from dmg_mse_LabResearch where rf_mse_ExaminationID = @examId and mse_LabResearchID > 0)\n" +
                        " \n" +
                        "delete from dmg_mse_LabResearch where rf_mse_ExaminationID = @examId and mse_LabResearchID > 0\n" +
                        "delete from dmg_mse_ConsultationExperts where rf_mse_ExaminationID = @examId and mse_ConsultationExpertsID > 0\n" +
                        "delete from dmg_mse_Diagnosis where rf_mse_ExaminationID = @examId and mse_DiagnosisID > 0\n" +
                        "delete from dmg_mse_TemporaryDisability where rf_mse_ExaminationID = @examId and mse_TemporaryDisabilityID > 0\n" +
                        "-- само направление\n" +
                        "delete from dmg_mse_Examination where mse_ExaminationID = @examId and mse_ExaminationID > 0\n" +
                        "-- обратный талон и связанные с ним таблицы\n" +
                        "if (@resultId > 0)\n" +
                        "begin\n" +
                        "    delete from dmg_mse_Diagnosis where rf_mse_ResultMSEID = @resultID and mse_DiagnosisID >0\n" +
                        "    delete from dmg_mse_TypeDysfunctionMSE where rf_mse_ResultMSEID = @resultID and mse_TypeDysfunctionMSEID >0\n" +
                        "    delete from dmg_mse_LifeCategoryMSE where rf_mse_ResultMSEID = @resultID and mse_LifeCategoryMSEID >0\n" +
                        "    delete from dmg_mse_ResultMse where mse_ResultMSEID = @resultID and mse_ResultMseID > 0\n" +
                        "end";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                }
            } catch (SQLException s) {s.printStackTrace();}
        } catch (Exception e) {
            e.printStackTrace();  }

    }


}

