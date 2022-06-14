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
public class NhaCC {
    private int maNhaCC;
    String tenNhaCC, dienThoai, email, diaChi, ghiChu;

    public NhaCC() {
    }

    public NhaCC(int maNhaCC, String tenNhaCC, String dienThoai, String email, String diaChi, String ghiChu) {
        this.maNhaCC = maNhaCC;
        this.tenNhaCC = tenNhaCC;
        this.dienThoai = dienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.ghiChu = ghiChu;
    }

    public int getMaNhaCC() {
        return maNhaCC;
    }

    public void setMaNhaCC(int maNhaCC) {
        this.maNhaCC = maNhaCC;
    }

    public String getTenNhaCC() {
        return tenNhaCC;
    }

    public void setTenNhaCC(String tenNhaCC) {
        this.tenNhaCC = tenNhaCC;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    @Override
    public String toString() {
        return  tenNhaCC;
    }
    
}
