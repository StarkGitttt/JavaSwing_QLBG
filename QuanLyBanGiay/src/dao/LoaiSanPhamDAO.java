/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modal.LoaiSanPham;
import xjdbc.JDBCHelper;

/**
 *
 * @author DELL
 */
public class LoaiSanPhamDAO extends GeneralDAO<LoaiSanPham, String> {
    String insert = "{call sp_InsertLSP(?)}";
    String update = "{call }";
    String delete = "{call }";
    String selectAll = "SELECT * FROM LoaiSP";
    String selectBy_ID = "{call sp_SelectIdLSP(?)}";
    String checkProduct = "{call sp_CheckNameProduct(?)}";
    @Override
    public int insert(LoaiSanPham entity) {
        return JDBCHelper.update(insert, entity.getTenLoaiSP());
    }

    @Override
    public int update(LoaiSanPham entity) {
        return 0;
    }

    @Override
    public int delete(String key) {
        return 0;
    }

    @Override
    public List<LoaiSanPham> selectAll() {
        List<LoaiSanPham> lstSP = this.selectBySQL(selectAll);
        if(lstSP.isEmpty()) {
            lstSP = null;
        }
        return lstSP;
    }

    @Override
    public LoaiSanPham selectByID(String idLSP) {
        LoaiSanPham lsp = new LoaiSanPham();
        ResultSet rs = null;
        try {
            rs = JDBCHelper.query(selectBy_ID, idLSP);
            if(rs.next()) {
                lsp.setMaLoaiSP(rs.getString(1));
                lsp.setTenLoaiSP(rs.getString(2));
            } else {
                lsp = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsp;
    }

    @Override
    protected List<LoaiSanPham> selectBySQL(String sql, Object... obj) {
         List<LoaiSanPham> lstLSP = new ArrayList<>();
         ResultSet rs = null;
         try {
            rs = JDBCHelper.query(sql, obj);
            while(rs.next()) {
                LoaiSanPham lsp = new LoaiSanPham();
                lsp.setMaLoaiSP(rs.getString(1));
                lsp.setTenLoaiSP(rs.getString(2));
                lstLSP.add(lsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return lstLSP;
    }
    public List<LoaiSanPham> checkProductEmpty(String nameProduct) {
        return this.selectBySQL(checkProduct, "%"+nameProduct+"%");
    }
    
}
