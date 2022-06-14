/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.sql.Timestamp;

/**
 *
 * @author DELL
 */
public class HoaDon {
    private int maHD;
    private String maKH, maNV, trangThai, ghiChu;
    private float donGia;
    private Timestamp ngayLapHD;

    public HoaDon() {
    }

    public HoaDon(int maHD, String maKH, String maNV, String trangThai,
            float donGia, Timestamp ngayLapHD, String ghiChu) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.trangThai = trangThai;
        this.donGia = donGia;
        this.ngayLapHD = ngayLapHD;
        this.ghiChu = ghiChu;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public Timestamp getNgayLapHD() {
        return ngayLapHD;
    }

    public void setNgayLapHD(Timestamp ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    
}
