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
public class KhoHang {
    private int Stt, thucNhap, slToiThieu, maNhaCC;
    private String maSP, maNV, DVT;
    private float donGia, thanhTien;
    private Date ngayNhap;

    public KhoHang() {
    }

    public KhoHang(int Stt, int thucNhap, int slToiThieu, int maNhaCC, String maSP, String maNV, String DVT, float donGia, float thanhTien, Date ngayNhap) {
        this.Stt = Stt;
        this.thucNhap = thucNhap;
        this.slToiThieu = slToiThieu;
        this.maNhaCC = maNhaCC;
        this.maSP = maSP;
        this.maNV = maNV;
        this.DVT = DVT;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
        this.ngayNhap = ngayNhap;
    }

    public int getStt() {
        return Stt;
    }

    public void setStt(int Stt) {
        this.Stt = Stt;
    }

    public int getThucNhap() {
        return thucNhap;
    }

    public void setThucNhap(int thucNhap) {
        this.thucNhap = thucNhap;
    }

    public int getSlToiThieu() {
        return slToiThieu;
    }

    public void setSlToiThieu(int slToiThieu) {
        this.slToiThieu = slToiThieu;
    }

    public int getMaNhaCC() {
        return maNhaCC;
    }

    public void setMaNhaCC(int maNhaCC) {
        this.maNhaCC = maNhaCC;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getDVT() {
        return DVT;
    }

    public void setDVT(String DVT) {
        this.DVT = DVT;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }
    
}
