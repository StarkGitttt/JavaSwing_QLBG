/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modal.NhanVien;
import utils.XDate;
import xjdbc.JDBCHelper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.swing.text.DateFormatter;

/**
 *
 * @author DELL
 */
public class NhanVienDAO extends GeneralDAO<NhanVien, String> {
    String insert = "{call sp_InsertNV(?, ?, ?, ?, ? ,? ,? ,? ,? ,?)}";
    String update = "{call sp_UpdateNV(?, ?, ?, ?, ? ,? ,? ,? ,?, ?)}";
    String updateInforEmp = "{call sp_UpdateInforNV(?, ?, ?, ?, ?, ?, ?)}";
    String updatePw = "{call sp_UpdatePassword(?, ?)}";
    String delete = "{call sp_DeleteNV(?)}";
    String selectAll = "SELECT * FROM NhanVien WHERE TrangThai LIKE N'Hoạt động'";
    String selectBy_ID = "{call sp_SelectIdNV(?)}";
    String selectBy_User = "{call sp_SelectUser(?)}";
    String selectBy_Name = "SELECT * FROM NhanVien WHERE TrangThai LIKE N'Hoạt động'"
            + " AND HoTen LIKE ? And VaiTro = 0";
    String test = "INSERT INTO TEST3(Ngay) VALUES (?)";
    @Override
    public int insert(NhanVien entity) {
        int rowEffect = JDBCHelper.update(insert, entity.getHoTen(), entity.getNgaySinh(),
                entity.getDienThoai(), entity.getEmail(), entity.isGioiTinh(),
                entity.getDiaChi(), entity.isVaiTro(), entity.getTaiKhoan(),
                entity.getMatKhau(), entity.getAvatar());
        return rowEffect;
    }

    @Override
    public int update(NhanVien entity) {
        int rowEffect = JDBCHelper.update(update, entity.getMaNV(), entity.getHoTen(), entity.getNgaySinh(),
                entity.getDienThoai(), entity.getEmail(), entity.isGioiTinh(),
                entity.getDiaChi(), entity.isVaiTro(), entity.getMatKhau(),
                entity.getAvatar());
        return rowEffect;
    }

    @Override
    public int delete(String idNV) {
        int rowEffect = JDBCHelper.update(delete, idNV);
        return rowEffect;
    }

    @Override
    public List<NhanVien> selectAll() {
        List<NhanVien> lstNV = this.selectBySQL(selectAll);
        if(lstNV.isEmpty()) {
            lstNV = null;
        }
        return lstNV;
    }
    @Override
    public NhanVien selectByID(String idNV) {
        NhanVien nv = new NhanVien();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectBy_ID, idNV);
            if(rs.next()) {
                    nv.setMaNV(rs.getString(1));
                    nv.setHoTen(rs.getString(2));
                    nv.setNgaySinh(rs.getDate(3));
                    nv.setDienThoai(rs.getString(4));
                    nv.setEmail(rs.getString(5));
                    nv.setGioiTinh(rs.getBoolean(6));
                    nv.setDiaChi(rs.getString(7)); 
                    nv.setVaiTro(rs.getBoolean(8));
                    nv.setTaiKhoan(rs.getString(9));
                    nv.setMatKhau(rs.getString(10));
                    nv.setAvatar(rs.getString(11));
                    nv.setTrangThai(rs.getString(12));
            } else {
                nv = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... obj) {
        List<NhanVien> lstNV = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(sql, obj);
                while(rs.next()) {
                    NhanVien nv = new NhanVien();
                    nv.setMaNV(rs.getString(1));
                    nv.setHoTen(rs.getString(2));
                    nv.setNgaySinh(rs.getDate(3));
                    nv.setDienThoai(rs.getString(4));
                    nv.setEmail(rs.getString(5));
                    nv.setGioiTinh(rs.getBoolean(6));
                    nv.setDiaChi(rs.getString(7)); 
                    nv.setVaiTro(rs.getBoolean(8));
                    nv.setTaiKhoan(rs.getString(9));
                    nv.setMatKhau(rs.getString(10));
                    nv.setAvatar(rs.getString(11));
                    nv.setTrangThai(rs.getString(12));
                    lstNV.add(nv);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstNV;
    }
    public NhanVien selectByUser(String userName) {
        NhanVien nv = new NhanVien();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectBy_User, userName);
            if(rs.next()) {
                    nv.setMaNV(rs.getString(1));
                    nv.setHoTen(rs.getString(2));
                    nv.setNgaySinh(rs.getDate(3));
                    nv.setDienThoai(rs.getString(4));
                    nv.setEmail(rs.getString(5));
                    nv.setGioiTinh(rs.getBoolean(6));
                    nv.setDiaChi(rs.getString(7)); 
                    nv.setVaiTro(rs.getBoolean(8));
                    nv.setTaiKhoan(rs.getString(9));
                    nv.setMatKhau(rs.getString(10));
                    nv.setAvatar(rs.getString(11));
                    nv.setTrangThai(rs.getString(12));
            } else {
                nv = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }
     public List<NhanVien> selectByName(String fullName) {
        List<NhanVien> lstNV = this.selectBySQL(selectBy_Name, "%"+fullName+"%");
        if(lstNV.isEmpty()) {
            lstNV = null;
        }
        return lstNV;
    }
     public int testDay(java.sql.Timestamp date) {
         return JDBCHelper.update(test, date);
     } 
     public java.sql.Timestamp getDate() {
         java.sql.Timestamp getDay = null;
         ResultSet rs = null;
         String sql = "SELECT * FROM TEST3";
         try {
             rs = JDBCHelper.query(sql);
             if(rs.next()) {
                 getDay = rs.getTimestamp(1);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return getDay;
     }
    public int updateInfor(NhanVien nv) {
        int rowEffect = JDBCHelper.update(updateInforEmp, nv.getMaNV(), nv.getHoTen(),
                nv.getNgaySinh(), nv.getDienThoai(), nv.getEmail(), nv.getDiaChi(),
                nv.getAvatar());
        return rowEffect;
    }
    public int updatePw(String id, String pw) {
        return JDBCHelper.update(updatePw, id, pw);
    }
    public NhanVien checkEmptyUser(String userName) {
        NhanVien nv = new NhanVien();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectBy_User, userName);
            if(rs.next()) {
                    nv.setMaNV(rs.getString(1));
                    nv.setHoTen(rs.getString(2));
                    nv.setNgaySinh(rs.getDate(3));
                    nv.setDienThoai(rs.getString(4));
                    nv.setEmail(rs.getString(5));
                    nv.setGioiTinh(rs.getBoolean(6));
                    nv.setDiaChi(rs.getString(7)); 
                    nv.setVaiTro(rs.getBoolean(8));
                    nv.setTaiKhoan(rs.getString(9));
                    nv.setMatKhau(rs.getString(10));
                    nv.setAvatar(rs.getString(11));
                    nv.setTrangThai(rs.getString(12));
            } else {
                nv = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }
    public static void main(String[] args) {
            NhanVienDAO nvDAO = new NhanVienDAO();
//            java.util.Date date=new java.util.Date();
//            java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
//            int rowEffect = nvDAO.testDay(XDate.convertTimeStamp());
//            if(rowEffect > 0) {
//                System.out.println("Succes");
//            } else {
//                System.out.println("Err");
//            }
                java.sql.Timestamp date = nvDAO.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                String str = sdf.format(date);
              System.out.println("" + str);
                
    }
}
