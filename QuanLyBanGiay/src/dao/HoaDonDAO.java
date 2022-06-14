/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modal.HoaDon;
import xjdbc.JDBCHelper;

/**
 *`
 * @author DELL
 */
public class HoaDonDAO extends GeneralDAO<HoaDon, Integer> {
    String insert = "{call sp_InsertInvoice(?, ?, ?, ?)}";
    String delete = "{call sp_DeleteInvoice(?, ?)}";
    String selectAll = "SELECT * FROM HoaDon";
    String countInvoice = "{call sp_CountInvoinceToday}";
    String sumInvoice = "{call sp_SumInvoiceToday}";
    String selectProductSell = "{call sp_selectProductSell(?, ?)}";
    String countLastValue = "SELECT MAX(HD.MaHoaDon) FROM dbo.HoaDon AS HD";
    String statisticsInvoice = "{call sp_StatisticInvoice(?, ?)}";
    String statisticsTop10Invoice = "{call sp_StatisticsTop10Invoice}";
    String statisticsInvoiceCancel = "SELECT * FROM dbo.HoaDon AS HD WHERE HD.TrangThai = N'Đã hủy'";
    
    @Override
    public int insert(HoaDon hd) {
        return JDBCHelper.update(insert, hd.getMaKH(), hd.getMaNV(),
                hd.getNgayLapHD(), hd.getDonGia());
    }

    @Override
    public int update(HoaDon entity) {
        return 0;
    }

    @Override
    public int delete(Integer key) {
        return 0;
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySQL(selectAll);
    }

    @Override
    public HoaDon selectByID(Integer key) {
        HoaDon hd = new HoaDon();
        return hd;
    }

    @Override
    protected List<HoaDon> selectBySQL(String sql, Object... obj) {
        List<HoaDon> lstHD = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(sql, obj);
            while(rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt(1));
                hd.setMaKH(rs.getString(2));
                hd.setMaNV(rs.getString(3));
                hd.setNgayLapHD(rs.getTimestamp(4));
                hd.setDonGia(rs.getFloat(5));
                hd.setTrangThai(rs.getString(6));
                hd.setGhiChu(rs.getString(7));
                lstHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstHD;
    }
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
    public int getCountInvoiceToday() {
        int count = 0;
        return count = (int) JDBCHelper.getValue(countInvoice);
    }
    public double getSumInvoiceToday() {
        double sum = 0;
        if(JDBCHelper.getValue(sumInvoice) == null) {
            return sum = 0;
        }
        return sum = (double) JDBCHelper.getValue(sumInvoice);
    }
    public List<Object[]> getProductSell(String idLSP, String nameProduct) {
        String [] cols = {"MaSP","TenSP","Size","SLConLai","GiaBan"};
        return getListOfArray(selectProductSell, cols, idLSP, "%"+nameProduct+"%");
    }
    public Object getValueInvoice() {
        return JDBCHelper.getValue(countLastValue);
    }
    public List<HoaDon> getStatisticsInvoice(Date fromDay, Date endDay) {
        return this.selectBySQL(statisticsInvoice, fromDay, endDay);
    }
    public List<HoaDon> getStatisticsTop10Invoice() {
        return this.selectBySQL(statisticsTop10Invoice);
    }
    public List<HoaDon> getStatisticsInvoiceCancel() {
        return this.selectBySQL(statisticsInvoiceCancel);
    }
    public int deleteInvoice(int idInvoice, String message) {
        return JDBCHelper.update(delete, idInvoice, message);
    }
    public static void main(String[] args) {
        HoaDonDAO hdDAO = new HoaDonDAO();
        List<HoaDon> lstHD = hdDAO.getStatisticsInvoiceCancel();
        if(lstHD.isEmpty()) {
            System.out.println("Error");
        } else {
            System.out.println("Success");
        }
//        for(HoaDon hd : lstHD) {
//            System.out.println(""+hd.getNgayLapHD());
//        }
//        int rowInvoice = (int) hdDAO.getValueInvoice();
//        System.out.println("" + rowInvoice);
    }
  
    
    
}
