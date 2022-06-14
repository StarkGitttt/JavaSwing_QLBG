/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author DELL
 */
public class JDBCHelper {
    static String dbURL = "jdbc:sqlserver://localhost;databaseName=QLBG_Group9;user=sa;password=sa123";
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static Connection cnt;

    static{
        try {            
            Class.forName(driver);
            cnt = DriverManager.getConnection(dbURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public static PreparedStatement getPstm(String sql, Object...args) {
        PreparedStatement pstm = null;
        try {
            if(sql.trim().startsWith("{")) {
                pstm = cnt.prepareCall(sql);             
            } else {
                pstm = cnt.prepareStatement(sql);
            }
            for(int i = 0; i < args.length; i++) {
                pstm.setObject(i + 1, args[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pstm;
    }
    // Thao tác
    public static int update(String sql, Object...args) {
        int rowEffect = 0;
        try {         
            try(PreparedStatement pstm = JDBCHelper.getPstm(sql, args);) {
                rowEffect = pstm.executeUpdate();
            }       
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rowEffect;       
    }
    // Truy vấn
    public static ResultSet query(String sql, Object...args) {
        ResultSet rs = null;
        PreparedStatement pstm = null;
        try {
            pstm = JDBCHelper.getPstm(sql, args);
            rs = pstm.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    // Nhân 1 giá trị trả về
    public static Object getValue(String sql, Object...args) {
        Object obj = null;
        ResultSet rs = null;
        try{
            rs = JDBCHelper.query(sql, args);
            if(rs.next()) {
                obj = rs.getObject(1);
            }
//            rs.getStatement().getConnection().close();
         } catch (Exception e) {
             e.printStackTrace();
        }
        return obj;
    }
}
