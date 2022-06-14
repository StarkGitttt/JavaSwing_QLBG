/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import xjdbc.JDBCHelper;

/**
 *
 * @author DELL
 */
public class ThongKeDAO {
    String productSeller = "{call sp_StatisticProductSeller(?)}";
    String productOOS = "{call sp_StatisticsOOF(?)}";
    // KhachHang
    String topRevenueCustomer = "{call sp_StatisticsRevenueKH(?, ?)}";
    String viewDetailProduct = "{call sp_ViewDetailProduct(?)}";
    String statisticsBirthKH = "{call sp_StatisticsBirthKH(?, ?)}";
    // Nhà cung cấp
    String statisticsFamiliar = "{call sp_StatisticsFamiliar}";
    // Doanh thu 
    String statisticsRevenue = "{call sp_StatisticsRevenue(?, ?)}";
    String statisicLineChart = "{call sp_StatisticsChartLine(?, ?)}";
    List<Object[]> getListOfArray(String sql, String[] cols, Object...args) {
         try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {                
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Object[]> getProductSeller(String idLSP) {
        String [] cols = {"MaSP","TenSP","GiaBan","Size","SL"};
        return getListOfArray(productSeller, cols, idLSP);
    }
    public List<Object[]> getProductOOS(String idLSP) {
        String [] cols = {"MaSP","TenSP","Size","SLSanPhamConLai"};
        return getListOfArray(productOOS, cols, idLSP);
    }
    public List<Object[]> getRevenueCustomer(String fullName, int percent) {
        String [] cols = {"MaKH","HoTen","NgaySinh","GioiTinh",
        "Email", "SDT", "SoLuong", "DoanhThu"};
        return getListOfArray(topRevenueCustomer, cols, "%"+fullName+"%", percent);
    }
    public List<Object[]> getDetailProduct(String maKH) {
        String [] cols = {"MaSP","TenSP","Size","NgayLapHD"};
        return getListOfArray(viewDetailProduct, cols, maKH);
    }
    public List<Object[]> getStatisticsBirth(String fullName, int day) {
        String [] cols = {"MaKH","HoTen","NgaySinh","SinhNhat"};
        return getListOfArray(statisticsBirthKH, cols, "%"+fullName+"%", day);
    }
    public List<Object[]> getStatisticsFamiliar() {
        String [] cols = {"TenNhaCC", "SoLanOrder"};
        return getListOfArray(statisticsFamiliar, cols);
    }
    public List<Object[]> getStatisticsRevenue(Date from, Date end) {
        String [] cols = {"SoLuongHD", "SoLuongSP", "DoanhThu"};
        return getListOfArray(statisticsRevenue, cols, from, end);
    }
    public List<Object[]> getStatisticsLineChart(Date from, Date end) {
        String [] cols = {"Ngay", "Thang", "Nam", "DonGia"};
        return getListOfArray(statisicLineChart, cols, from, end);
    }
    public static void main(String[] args) {
        ThongKeDAO tkDAO = new ThongKeDAO();
        List<Object[]> obj = tkDAO.getStatisticsBirth("", 30);
        for(Object[] row : obj) {
            System.out.println("" + row[1]);
        }
//        String str = "14 Ngày";
//        String getStr = str.substring(0, str.lastIndexOf(" "));
//        System.out.println("" + getStr.trim());

    }
}
