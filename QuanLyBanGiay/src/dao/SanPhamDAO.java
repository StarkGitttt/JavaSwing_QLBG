/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modal.SanPham;
import xjdbc.JDBCHelper;

/**
 *
 * @author DELL
 */
public class SanPhamDAO extends GeneralDAO<SanPham, String> {
    String insert = "{call sp_InsertSP(?, ?, ?, ?, ?, ?, ?, ?)}";
    String update = "{call sp_UpdateSP(?, ?, ?, ?, ?, ?, ?)}";
    String updateStatus = "{call sp_UpdateStatusSP(?)}";
    String updateSelling = "{call sp_UpdateSelling(?)}";
    String delete = "{call sp_DeleteSP(?)}";
    String selectAll = "SELECT * FROM SanPham WHERE TrangThai LIKE N'Còn bán'";
    String selectBy_ID = "{call sp_SelectIdSP(?)}";
    String selectBy_LSP = "{call sp_SelectByLSP(?, ?, ?)}";
    String seletctProductStopTrade = "{call sp_SelectProductStopTrade(?)}";
    String countProductSold = "{call sp_CountProductSellToday}";
    String countProductOOS = "{call sp_CountProductOOS}";
    String countProductBusiness = "{call sp_CountProductBusiness}";
    String checkExistProduct = "{call sp_CheckExistProduct(?)}";
    @Override
    public int insert(SanPham entity) {
        return JDBCHelper.update(insert, entity.getMaLoaiSP(),
                entity.getTenSP(), entity.getGia(), entity.getSize(),
                entity.getHinh(),entity.getNgayBan(), entity.getMoTa(), entity.getHanSuDung());
    }
    
    @Override
    public int update(SanPham entity) {
        return JDBCHelper.update(update, entity.getMaSP(), entity.getTenSP(),
                entity.getGia(), entity.getSize(),
                entity.getHinh(), entity.getMoTa(),
                entity.getHanSuDung());
    }

    @Override
    public int delete(String idSP) {
        return JDBCHelper.update(delete, idSP);
    }

    @Override
    public List<SanPham> selectAll() {
        List<SanPham> lstSP = this.selectBySQL(selectAll);
        if(lstSP.isEmpty()) {
            lstSP = null;
        }
        return lstSP;
    }

    @Override
    public SanPham selectByID(String idSP) {
        SanPham sp = new SanPham();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectBy_ID, idSP);
            if(rs.next()) {
                sp.setMaSP(rs.getString(1));
                sp.setMaLoaiSP(rs.getString(2));
                sp.setTenSP(rs.getString(3));
                sp.setGia(rs.getFloat(4));
                sp.setSize(rs.getInt(5));
                sp.setHinh(rs.getString(6));
                sp.setNgayBan(rs.getDate(7));
                sp.setMoTa(rs.getString(8));
                sp.setHanSuDung(rs.getDate(9));
                sp.setTrangThai(rs.getString(10));
            } else {
                sp = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sp;
    }

    @Override
    protected List<SanPham> selectBySQL(String sql, Object... obj) {
        List<SanPham> lstSP = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(sql, obj);
            while(rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString(1));
                sp.setMaLoaiSP(rs.getString(2));
                sp.setTenSP(rs.getString(3));
                sp.setGia(rs.getFloat(4));
                sp.setSize(rs.getInt(5));
                sp.setHinh(rs.getString(6));
                sp.setNgayBan(rs.getDate(7));
                sp.setMoTa(rs.getString(8));
                sp.setHanSuDung(rs.getDate(9));
                sp.setTrangThai(rs.getString(10));
                lstSP.add(sp);               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstSP;
    }
    public List<SanPham> selectByLSP(String idLSP, float firstPrice, float secondPrice) {
        List<SanPham> lstSP = new ArrayList<>();
        lstSP = this.selectBySQL(selectBy_LSP, idLSP, firstPrice, secondPrice);
        return lstSP; 
    }
    public int updateStatus(String idSP) {
        return JDBCHelper.update(updateStatus, idSP);
    }
    public int updateSelling(String idSP) {
        return JDBCHelper.update(updateSelling, idSP);
    }
    public List<SanPham> getProductStopTrade(String idLSP) {
        List<SanPham> lstProductStopTrade = new ArrayList<>();
        lstProductStopTrade = this.selectBySQL(seletctProductStopTrade, idLSP);
        return lstProductStopTrade;
    }
    public int getCountProductSold() {
        int getCount = (int) JDBCHelper.getValue(countProductSold);
        return getCount;
    }
    public int getCountProductOOS() {
        int getCount = (int) JDBCHelper.getValue(countProductOOS);
        return getCount;
    }
    public int getCountProductBusiness() {
        int getCount = (int) JDBCHelper.getValue(countProductBusiness);
        return getCount;
    }
    public List<SanPham> getExistProduct(String nameProduct) {
        List<SanPham> lstSP = new ArrayList<>();
        lstSP = this.selectBySQL(checkExistProduct, nameProduct);
        return lstSP;
    }
    public static void main(String[] args) {
//        SanPhamDAO spDAO = new SanPhamDAO();
//        List<SanPham> lstSP = new ArrayList<>();
//        SanPham sp = new SanPham();
//        sp = spDAO.selectByID("SP001");
//        System.out.println("" + sp.getTenSP());   
        String str = "500.000 VNĐ - 1.000.000 VNĐ";
        String firstValue = str.substring(0, str.indexOf(" ")+1).trim();
        String secondValud = str.substring(str.indexOf("- ")+ 1, str.lastIndexOf(" ")).trim();
        System.out.println(""+firstValue);
        System.out.println(""+secondValud);
    }
   
}
