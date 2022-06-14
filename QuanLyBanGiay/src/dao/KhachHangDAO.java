/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modal.KhachHang;
import xjdbc.JDBCHelper;

/**
 *
 * @author DELL
 */
public class KhachHangDAO extends GeneralDAO<KhachHang, String> {
    String insert = "{call sp_InsertKH(?, ?, ?, ?, ?, ?)}";
    String insertInvoice = "{call sp_InsertCusToInvoice(?, ?, ?, ?, ?)}";
    String update = "{call sp_UpdateKH(?, ?, ?, ?, ?)}";
    String delete = "{call sp_DeleteKH(?)}";
    String selectAll = "SELECT * FROM KhachHang";
    String selectBy_ID = "{call sp_SelectIdKH(?)}";
    String selectBy_Name = "SELECT * FROM KhachHang WHERE HoTen LIKE ?";
    String countBirthCustomer = "{call sp_CountBirthCustomer}";
    String selectByInfor = "SELECT * FROM KhachHang WHERE SDT = ?"
            + " OR Email = ? OR HoTen = ?";
    String saleCustomerByBuy = "{call sp_saleCustomerBuyProduct(?)}";
    String saleCustomerByBirth = "{call sp_SaleCustomberByBirth(?)}";
    String countLastValue = "SELECT MAX(MaKH) FROM dbo.KhachHang";
    @Override
    public int insert(KhachHang entity) {
        return JDBCHelper.update(insert, entity.getHoTen(),
                entity.getNgaySinh(), entity.isGioiTinh(),
                entity.getEmail(), entity.getDienThoai(),
                entity.getMaNV());
    }

    @Override
    public int update(KhachHang entity) {
        return JDBCHelper.update(update, entity.getMaKH(),
                entity.getHoTen(), entity.getNgaySinh(),
                entity.getEmail(), entity.getDienThoai());
    }

    @Override
    public int delete(String idKH) {
        return JDBCHelper.update(delete, idKH);
    }

    @Override
    public List<KhachHang> selectAll() {
        List<KhachHang> lstKH = this.selectBySQL(selectAll);
        if(lstKH.isEmpty()) {
            lstKH = null;
        }
        return lstKH;
    }

    @Override
    public KhachHang selectByID(String idKH) {
        KhachHang kh = new KhachHang();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectBy_ID, idKH);
            if(rs.next()) {
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setNgaySinh(rs.getDate(3));
                kh.setGioiTinh(rs.getBoolean(4));
                kh.setEmail(rs.getString(5));
                kh.setDienThoai(rs.getString(6));
                kh.setMaNV(rs.getString(7));
            } else {
                kh = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }

    @Override
    protected List<KhachHang> selectBySQL(String sql, Object... obj) {
        List<KhachHang> lstKH = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(sql, obj);
            while(rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setNgaySinh(rs.getDate(3));
                kh.setGioiTinh(rs.getBoolean(4));
                kh.setEmail(rs.getString(5));
                kh.setDienThoai(rs.getString(6));
                kh.setMaNV(rs.getString(7));
                lstKH.add(kh);
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        return lstKH;
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
    public List<KhachHang> selectByName(String hoTen) {
        List<KhachHang> lstKH = this.selectBySQL(selectBy_Name, "%"+hoTen+"%");
        if(lstKH.isEmpty()) {
            lstKH = null;
        }
        return lstKH;
    }
     public int getCountBirthCustomer() {
        int count = 0;
        return count = (int) JDBCHelper.getValue(countBirthCustomer);
    }
    public KhachHang searchByPhone(String infor) {
        KhachHang kh = new KhachHang();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectByInfor, infor, infor, infor);
            if(rs.next()) {
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setNgaySinh(rs.getDate(3));
                kh.setGioiTinh(rs.getBoolean(4));
                kh.setEmail(rs.getString(5));
                kh.setDienThoai(rs.getString(6));
                kh.setMaNV(rs.getString(7));
            } else {
                kh = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
     }
    public List<KhachHang> searchByInfor(String infor) {
       List<KhachHang> lstKH = this.selectBySQL(selectByInfor, infor, infor, infor);
        if(lstKH.isEmpty()) {
            lstKH = null;
        }
        return lstKH;
     }
    public List<Object[]> CustomerBuy10Product(String phone) {
        String [] cols = {"MaKH","SLMua"};
        return getListOfArray(saleCustomerByBuy, cols, phone);
    }
    public KhachHang CustomerByBirth(String phone) {
        KhachHang kh = new KhachHang();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(saleCustomerByBirth, phone);
            if(rs.next()) {
                kh.setMaKH(rs.getString(1));
                kh.setHoTen(rs.getString(2));
                kh.setNgaySinh(rs.getDate(3));
                kh.setGioiTinh(rs.getBoolean(4));
                kh.setEmail(rs.getString(5));
                kh.setDienThoai(rs.getString(6));
                kh.setMaNV(rs.getString(7));
            } else {
                kh = null;
            }
        } catch (Exception e) {
            
        }
        return kh;
    }
    public int insertInvoice(KhachHang entity) {
        return JDBCHelper.update(insertInvoice, entity.getHoTen(),
                entity.getNgaySinh(), entity.getEmail(),
                entity.getDienThoai(),
                entity.getMaNV());
    }
     public Object getValueCustomer() {
        return JDBCHelper.getValue(countLastValue);
    }
    public static void main(String[] args) {
      KhachHangDAO khDAO = new KhachHangDAO();
      KhachHang kh = khDAO.searchByPhone("20905160998");
      if(kh != null) {
          System.out.println(""+kh.getHoTen());
      } else {
          System.out.println("Rong");
      }
    }
}
