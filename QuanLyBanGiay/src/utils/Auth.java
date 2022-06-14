/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import modal.KhachHang;
import modal.NhanVien;

/**
 *
 * @author DELL
 */
public class Auth {
    public static NhanVien user = null;
    public static KhachHang kh = null;
    public static int soLuong = 0;
    public static String note = "";
    public static void clear() {
        Auth.user = null;
    }
    public static boolean isLogin() {
        return Auth.user != null;
    }
    public static boolean role() {
        return Auth.isLogin() && user.isVaiTro();
    }
    public static void main(String[] args) {
        String str = "10%";
        System.out.println("" + str.substring(0, str.indexOf("%")).trim());
    }
}
