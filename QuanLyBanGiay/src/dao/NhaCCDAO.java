/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modal.NhaCC;
import xjdbc.JDBCHelper;

/**
 *
 * @author DELL
 */
public class NhaCCDAO extends GeneralDAO<NhaCC, Integer> {
    String insert = "{call sp_InsertNhaCC(?, ?, ?, ?, ?)}";
    String update = "{call sp_UpdateNhaCC(?, ?, ?, ?, ?, ?)}";
    String delete = "{call sp_DeleteNhaCC(?)}";
    String selectAll = "SELECT * FROM NhaCC";
    String selectBy_ID = "{call sp_SelectIDNhaCC(?)}";
    String selectBy_Name = "SELECT * FROM NhaCC WHERE TenNhaCC LIKE ?";
    String checkEmptyProduct = "SELECT * FROM NhaCC WHERE TenNhaCC = ?";
    @Override
    public int insert(NhaCC entity) {
        return JDBCHelper.update(insert, entity.getTenNhaCC(), entity.getDienThoai(),
                entity.getEmail(), entity.getDiaChi(), entity.getGhiChu());
    }

    @Override
    public int update(NhaCC entity) {
         return JDBCHelper.update(update, entity.getMaNhaCC(), entity.getTenNhaCC(), entity.getDienThoai(),
                entity.getEmail(), entity.getDiaChi(), entity.getGhiChu());
    }

    @Override
    public int delete(Integer id) {
        return JDBCHelper.update(delete, id);
    }

    @Override
    public List<NhaCC> selectAll() {
        return this.selectBySQL(selectAll);
    }

    @Override
    public NhaCC selectByID(Integer id) {
        NhaCC nhaCC = new NhaCC();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectBy_ID, id);
            if(rs.next()) {
                nhaCC.setMaNhaCC(rs.getInt(1));
                nhaCC.setTenNhaCC(rs.getString(2));
                nhaCC.setDienThoai(rs.getString(3));
                nhaCC.setEmail(rs.getString(4));
                nhaCC.setDiaChi(rs.getString(5));
                nhaCC.setGhiChu(rs.getString(6));
            } else {
                nhaCC = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nhaCC;
    }

    @Override
    protected List<NhaCC> selectBySQL(String sql, Object... obj) {
        List<NhaCC> lstNhaCC = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(sql, obj);
            while(rs.next()) {
                NhaCC nhaCC = new NhaCC();
                nhaCC.setMaNhaCC(rs.getInt(1));
                nhaCC.setTenNhaCC(rs.getString(2));
                nhaCC.setDienThoai(rs.getString(3));
                nhaCC.setEmail(rs.getString(4));
                nhaCC.setDiaChi(rs.getString(5));
                nhaCC.setGhiChu(rs.getString(6));
                lstNhaCC.add(nhaCC);
                
            }
        } catch (Exception e) {
            e.printStackTrace();               
        }
        return lstNhaCC;
    }
    public List<NhaCC> selectByName(String name) {
        return this.selectBySQL(selectBy_Name, "%"+name+"%");
    }
    public List<NhaCC> checkEmptySupplier(String name) {
        return this.selectBySQL(checkEmptyProduct, name);
    }
    public static void main(String[] args) {
        int index = 0;
        for(int i = 1; i <= 5; i++) {
            index++;
        }
        System.out.println("" + index);
    }
}
