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
public class NhanVien {
    private String maNV, hoTen, dienThoai,
            diaChi, email, avatar,
            taiKhoan, matKhau, trangThai;
    private Date ngaySinh;
    private boolean gioiTinh, vaiTro;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTen, String dienThoai, String diaChi, String email,
            String avatar, String taiKhoan, String matKhau,
            Date ngaySinh, boolean gioiTinh, boolean vaiTro, String trangThai) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
        this.email = email;
        this.avatar = avatar;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
