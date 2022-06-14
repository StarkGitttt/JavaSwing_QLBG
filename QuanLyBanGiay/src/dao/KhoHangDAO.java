/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modal.KhoHang;
import xjdbc.JDBCHelper;

/**
 *
 * @author DELL
 */
public class KhoHangDAO extends GeneralDAO<KhoHang, Integer> {
    String insert = "{call sp_InsertKhoHang(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    String update = "{call sp_UpdateKhoHang(?, ?, ?, ?, ?)}";
    String delete = "";
    String selectAll = "SELECT * FROM KhoHang";
    String selectByID = "SELECT * FROM KhoHang WHERE STT = ?";
    String updateMinProduct = "";
    String selectByNameSP = "{call sp_selectKhoHang(?)}";
    String getNameProduct = "{call sp_selectNameKhoHang(?)}";
    @Override
    public int insert(KhoHang entity) {
        return JDBCHelper.update(insert, entity.getMaSP(), entity.getMaNV(),
                entity.getDVT(), entity.getThucNhap(), entity.getDonGia(),
                entity.getThanhTien(), entity.getNgayNhap(),
                entity.getMaNhaCC(), entity.getSlToiThieu());
    }

    @Override
    public int update(KhoHang entity) {
        return JDBCHelper.update(update, entity.getStt(),entity.getThucNhap(),
                entity.getSlToiThieu(),entity.getDonGia(),
                entity.getThanhTien()
                );
    }

    @Override
    public int delete(Integer key) {
        return 0;
    }

    @Override
    public List<KhoHang> selectAll() {
        return this.selectBySQL(selectAll);
    }

    @Override
    public KhoHang selectByID(Integer id) {
        KhoHang kh = new KhoHang();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectByID, id);
            if(rs.next()) {
                kh.setStt(rs.getInt(1));
                kh.setMaSP(rs.getString(2));
                kh.setMaNV(rs.getString(3));
                kh.setDVT(rs.getString(4));
                kh.setThucNhap(rs.getInt(5));
                kh.setDonGia(rs.getFloat(6));
                kh.setThanhTien(rs.getFloat(7));
                kh.setNgayNhap(rs.getDate(8));
                kh.setMaNhaCC(rs.getInt(9));
                kh.setSlToiThieu(rs.getInt(10));
            } else {
                kh = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }

    @Override
    protected List<KhoHang> selectBySQL(String sql, Object... obj) {
        List<KhoHang> lstKhoHang = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(sql, obj);
            while(rs.next()) {
                KhoHang kh = new KhoHang();
                kh.setStt(rs.getInt(1));
                kh.setMaSP(rs.getString(2));
                kh.setMaNV(rs.getString(3));
                kh.setDVT(rs.getString(4));
                kh.setThucNhap(rs.getInt(5));
                kh.setDonGia(rs.getFloat(6));
                kh.setThanhTien(rs.getFloat(7));
                kh.setNgayNhap(rs.getDate(8));
                kh.setMaNhaCC(rs.getInt(9));
                kh.setSlToiThieu(rs.getInt(10));
            }     
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstKhoHang;
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
    public Object getNameProduct(int id ) {
        return JDBCHelper.getValue(getNameProduct, id);
    }
    public List<Object[]> getWareHouse(String nameSP) {
        String [] cols = {"STT","TenSP","ThucNhap","DonGia","ThanhTien",
        "NgayNhap", "MaNhaCC"};
        return getListOfArray(selectByNameSP, cols, "%"+nameSP+"%");
    }
    public static void main(String[] args) {
        String v = "100 VNĐ";
        if(v.contains("VNĐ")) {
            System.out.println("Success");
        } else {
            System.out.println("Err");
        }
    }
    
}
