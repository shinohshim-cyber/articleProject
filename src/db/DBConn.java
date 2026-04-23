package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static Connection dbConn;

    public static Connection getConnection() {
        if (dbConn == null) {
            try {
                String dbDriver = "com.mysql.cj.jdbc.Driver";
                String dbUrl = "jdbc:mysql://localhost:3306/article_db";
                String dbUser = "root";
                String dbPassword = "1111";
                //  드라이버 클래스를 메모리를 가져온다.
                Class.forName(dbDriver);
                //  연결을 생성
                dbConn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                //  연결 성공
                System.out.println("DB 연결 성공!!!");
            } catch (ClassNotFoundException e) {
                System.out.println("드라이버가 없어요.. 실패");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("DB 연결 실패");
                e.printStackTrace();
            }
        }
        return dbConn;
    }

    //  DB 종료 시 처리
    public static void close() {
        if (dbConn != null) {
            try {
                if (!dbConn.isClosed()) {
                    dbConn.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            dbConn = null;
        }
    }
}