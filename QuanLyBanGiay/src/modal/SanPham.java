/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class SanPham {
    private String maSP, maLoaiSP, tenSP, hinh, moTa, trangThai;
    private float gia;
    private int size;
    Date ngayBan, hanSuDung;

    public SanPham() {
    }

    public SanPham(String maSP, String maLoaiSP, String maNV, String tenSP,
            String hinh, String moTa, float gia,
            int size, Date ngayBan, Date hanSuDung, String trangThai) {
        this.maSP = maSP;
        this.maLoaiSP = maLoaiSP;
        this.tenSP = tenSP;
        this.hinh = hinh;
        this.moTa = moTa;
        this.gia = gia;
        this.size = size;
        this.ngayBan = ngayBan;
        this.hanSuDung = hanSuDung;
        this.trangThai = trangThai;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(String maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }
    
    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return  tenSP;
    }
    
    
}
