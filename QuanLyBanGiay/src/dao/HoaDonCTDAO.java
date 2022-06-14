/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modal.HoaDonChiTiet;
import xjdbc.JDBCHelper;

/**
 *
 * @author DELL
 */
public class HoaDonCTDAO extends GeneralDAO<HoaDonChiTiet, Integer> {
    String insert = "";
    String insertByNameProduct = "{call insert_InvoiceDetail(?, ?, ?)}";
    String update = "";
    String delete = "";
    String selectBy_ID = "";
    String selectAll = "SELECT * FROM HoaDonCT";
    String viewInvoiceDetails = "{call sp_ViewInvoiceDetails(?)}";
    String viewInforCusAndEmp = "{call sp_ViewInforCusAndEmp(?)}";
    String viewInforCancel = "{call sp_ViewInvoiceCancel(?)}";
    @Override
    public int insert(HoaDonChiTiet entity) {
        return 0;
    }

    @Override
    public int update(HoaDonChiTiet entity) {
        return 0;
    }

    @Override
    public int delete(Integer idHDCT) {
        return 0;
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
        return this.selectBySQL(selectAll);
    }

    @Override
    public HoaDonChiTiet selectByID(Integer idHDCT) {
        HoaDonChiTiet hdCT = new HoaDonChiTiet();
        return hdCT;
    }

    @Override
    protected List<HoaDonChiTiet> selectBySQL(String sql, Object... obj) {
        List<HoaDonChiTiet> lstHDCT = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(sql, obj);
            while(rs.next()) {
                HoaDonChiTiet hdCT = new HoaDonChiTiet();
                hdCT.setMaHDCT(rs.getInt(1));
                hdCT.setMaHD(rs.getInt(2));
                hdCT.setMaSP(rs.getString(3));
                hdCT.setSoLuong(rs.getInt(4));
                lstHDCT.add(hdCT);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstHDCT;
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
    public int insertByNameProduct(int idHD, String nameProduct, int quantity) {
        return JDBCHelper.update(insertByNameProduct, idHD, nameProduct, quantity);
    }
    public List<Object[]> getInvoiceDetails(int idInvoice) {
        String [] cols = {"MaHoaDonCT","MaHoaDon","MaSP","TenSP","SoLuong","TongTien"};
        return getListOfArray(viewInvoiceDetails, cols, idInvoice);
    }
    public List<Object[]> getInvoiceCancel(int idInvoice) {
        String [] cols = {"MaHoaDonCT","MaHoaDon","MaSP","TenSP","SoLuong","TongTien"};
        return getListOfArray(viewInforCancel, cols, idInvoice);
    }
    public List<Object[]> getInforCAE(int idInvoice) {
        String [] cols = {"HoTen","NgayLapHD","DonGia"};
        return getListOfArray(viewInforCusAndEmp, cols, idInvoice);
    }
    public static void main(String[] args) {
        HoaDonCTDAO hd = new HoaDonCTDAO();
        List<Object[]> lstObj = new ArrayList<>();
        lstObj = hd.getInforCAE(1);
        Object[] row = lstObj.get(0);
        System.out.println("" + row[1]);
    }
    
}
