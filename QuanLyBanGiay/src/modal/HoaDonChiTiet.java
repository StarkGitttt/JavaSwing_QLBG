/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

/**
 *
 * @author DELL
 */
public class HoaDonChiTiet {
    private int maHD, maHDCT, soLuong;
    private String maSP;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int maHD, int maHDCT, int soLuong, String maSP) {
        this.maHD = maHD;
        this.maHDCT = maHDCT;
        this.soLuong = soLuong;
        this.maSP = maSP;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }
    
}
