/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.NhaCCDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import java.awt.Color;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import modal.NhaCC;
import modal.NhanVien;

/**
 *
 * @author DELL
 */
public class Validator {
    public static EtchedBorder getBorders() {
        EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
        return eb;
    }
    // Validate Form Đăng nhập
    public static boolean isEmptyUser(JTextField txtUser, JPasswordField txtPw,
                                        JLabel errUser, JLabel errPw) {
        NhanVien nv = new NhanVien();
        NhanVienDAO nvDAO = new NhanVienDAO();
        boolean isValid = false;
        if(txtUser.getText().equals("")) {
            errUser.setText("Vui lòng nhập trường này");
            errUser.setForeground(new java.awt.Color(255, 51, 51));
            txtUser.setBackground(new java.awt.Color(255, 237, 94));
            isValid = true;           
        }
        if(new String(txtPw.getPassword()).equals("")) {
            errPw.setText("Vui lòng nhập trường này");
            errPw.setForeground(new java.awt.Color(255, 51, 51));
            txtPw.setBackground(new java.awt.Color(255, 237, 94));
            isValid = true;
            return isValid;
        }
        nv = nvDAO.selectByUser(txtUser.getText());
        Auth.user = nv;
        if(Auth.user == null) {
            errUser.setText("Tài khoản không tồn tại");
            errUser.setForeground(new java.awt.Color(255, 51, 51));
            txtUser.setBackground(new java.awt.Color(255, 237, 94));
            isValid = true;
        } else if(!Auth.user.getMatKhau().equals(new String(txtPw.getPassword()))) {
            errPw.setText("Mật khẩu không chính xác");
            errPw.setForeground(new java.awt.Color(255, 51, 51));
            txtPw.setBackground(new java.awt.Color(255, 237, 94));
            isValid = true;
        }
        return isValid;
    }
    // Validate Form ChangePW
    public static boolean isEmptyOldPw(JPasswordField txtOldPw, JLabel errOldpw) {
        boolean isValid = false;
        if(new String(txtOldPw.getPassword()).equals("")) {
            EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
            txtOldPw.setBorder(eb);
            errOldpw.setText("Vui lòng nhập trường này");
            errOldpw.setForeground(new java.awt.Color(204,51,0));
            isValid = true;
            return isValid;
        }
         if(!Auth.user.getMatKhau().equals(new String(txtOldPw.getPassword()))) {
            EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
            txtOldPw.setBorder(eb);
            errOldpw.setText("Mật khẩu cũ không chính xác");
            errOldpw.setForeground(new java.awt.Color(204,51,0));
            isValid = true;
       }        
        return isValid;
    }
    public static boolean isEmptyPassword(JPasswordField Pw, JLabel errPw) {
        boolean isValid = false;
        if(new String(Pw.getPassword()).equals("")) {
            EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
            Pw.setBorder(eb);
            errPw.setText("Vui lòng nhập trường này");
            errPw.setForeground(new java.awt.Color(204,51,0));
            isValid = true;
            return isValid;
        }
        if(new String(Pw.getPassword()).length() < 6 ) {
            EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
            Pw.setBorder(eb);
            errPw.setText("Vui lòng nhập tối thiếu 6 kí tự");
            errPw.setForeground(new java.awt.Color(204,51,0));
            isValid = true;
            return isValid;
        }
        return isValid;
    }
    public static boolean isEmptyConfirmPw(JPasswordField Pw, JPasswordField ConfirmPw, JLabel errConfirmPw) {
        boolean isValid = false;
        if(new String(ConfirmPw.getPassword()).equals("")) {
            EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
            ConfirmPw.setBorder(eb);
            errConfirmPw.setText("Vui lòng nhập trường này");
            errConfirmPw.setForeground(new java.awt.Color(204,51,0));
            isValid = true;
            return isValid;
        }
        if(!(new String(Pw.getPassword()).length() == 0)) {
            if(!new String(Pw.getPassword()).equals(new String(ConfirmPw.getPassword()))) {
                EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
                ConfirmPw.setBorder(eb);
                errConfirmPw.setText("Vui lòng nhập mật khẩu chính xác");
                errConfirmPw.setForeground(new java.awt.Color(204,51,0));
                isValid = true;
            }           
        }
        return isValid;
    }
    /* VALIDATE FORM SẢN PHẨM*/
    public static boolean isEmptyProduct(JTextField txtNameProduct, JLabel errNameProduct) {
        boolean isValid = false;
        // Check Empty
        if(txtNameProduct.getText().equals("")) {
            errNameProduct.setText("Vui lòng nhập trường này");
            errNameProduct.setForeground(new java.awt.Color(233,38,38));
            txtNameProduct.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        // Check Exist
        SanPhamDAO spDAO = new SanPhamDAO();
        if(!spDAO.getExistProduct(txtNameProduct.getText()).isEmpty()) {
            errNameProduct.setText("Tên sản phẩm đã tồn tại");
            errNameProduct.setForeground(new java.awt.Color(233,38,38));
            txtNameProduct.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    // Check Form Update Product
    public static boolean isEmptyProductUpdate(JTextField txtNameProduct, JLabel errNameProduct) {
        boolean isValid = false;
        // Check Empty
        if(txtNameProduct.getText().equals("")) {
            errNameProduct.setText("Vui lòng nhập trường này");
            errNameProduct.setForeground(new java.awt.Color(233,38,38));
            txtNameProduct.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean checkPriceUpdate(JTextField txtPrice, JLabel errPrice) {
        boolean isValid = false;
         // Check Empty
        if(txtPrice.getText().equals("")) {
            errPrice.setText("Vui lòng nhập trường này");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        // Check Price
         String pattern = "[.|\\d]+";
        if(!txtPrice.getText().matches(pattern)) {
            errPrice.setText("Vui lòng nhập số");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        float getPrice = 0;
        try {
            getPrice = Float.parseFloat(txtPrice.getText());
            if(getPrice < 25) {
                errPrice.setText("Vui lòng nhập giá lớn hơn 0");
                errPrice.setForeground(new java.awt.Color(233,38,38));
                txtPrice.setBorder(getBorders());
                isValid = true;
            }          
        } catch (Exception e) {
            errPrice.setText("Vui lòng nhập số");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean checkPrice(JTextField txtPrice, JLabel errPrice) {
        boolean isValid = false;
         // Check Empty
        if(txtPrice.getText().equals("")) {
            errPrice.setText("Vui lòng nhập trường này");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        // Check Price
         String pattern = "[-|\\d]+";
        if(!txtPrice.getText().matches(pattern)) {
            errPrice.setText("Vui lòng nhập số");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        float getPrice = 0;
        try {
            getPrice = Float.parseFloat(txtPrice.getText());
            if(getPrice < 25) {
                errPrice.setText("Vui lòng nhập giá lớn hơn 0");
                errPrice.setForeground(new java.awt.Color(233,38,38));
                txtPrice.setBorder(getBorders());
                isValid = true;
            }          
        } catch (Exception e) {
             errPrice.setText("Vui lòng nhập số");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean checkSize(JTextField txtSize, JLabel errSize) {
        boolean isValid = false;
         // Check Empty
        if(txtSize.getText().equals("")) {
            errSize.setText("Vui lòng nhập trường này");
            errSize.setForeground(new java.awt.Color(233,38,38));
            txtSize.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        // Check Size
        String pattern = "[-|\\d]+";
        if(!txtSize.getText().matches(pattern)) {
            errSize.setText("Vui lòng nhập số");
            errSize.setForeground(new java.awt.Color(233,38,38));
            txtSize.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        float getSize = 0;
        try {
            getSize = Float.parseFloat(txtSize.getText());
            if(getSize < 25) {
                errSize.setText("Vui lòng nhập size lớn hơn 25");
                errSize.setForeground(new java.awt.Color(233,38,38));
                txtSize.setBorder(getBorders());
                isValid = true;
            }          
        } catch (Exception e) {
             errSize.setText("Vui lòng nhập số");
            errSize.setForeground(new java.awt.Color(233,38,38));
            txtSize.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean checkExpiry(JTextField txtExpiry, JLabel lblErrExpiry) {
        boolean isValid = false;
        // Check Empty
        if(txtExpiry.getText().equals("")) {           
            isValid = false;
            return isValid;
        }
        // Check Expiry
        String pattern = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
        if(!txtExpiry.getText().matches(pattern)){
            lblErrExpiry.setText("Ngày hợp lệ (dd-mm-yyyy)");
            lblErrExpiry.setForeground(new java.awt.Color(255, 51, 51));
            txtExpiry.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    /* Validate Form update Infor*/
    public static boolean isName(JTextField txtFullName, JLabel lblErrFullName) {
         boolean isValid = false;
         // Check Empty
        if(txtFullName.getText().equals("")) {
            lblErrFullName.setText("Vui lòng nhập trường này");
            lblErrFullName.setForeground(new java.awt.Color(255, 51, 51));
            txtFullName.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        return isValid;
    }
    public static boolean isPhone(JTextField txtPhone, JLabel lblErrPhone) {
        boolean isValid = false;
         // Check Empty
        if(txtPhone.getText().equals("")) {
            lblErrPhone.setText("Vui lòng nhập trường này");
            lblErrPhone.setForeground(new java.awt.Color(255, 51, 51));
            txtPhone.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        String pattern = "\\d{10}";
         if(!txtPhone.getText().matches(pattern)) {
            lblErrPhone.setText("Vui lòng nhập SĐT chính xác!");
            lblErrPhone.setForeground(new java.awt.Color(255, 51, 51));
            txtPhone.setBorder(getBorders());
            isValid = true;
        }
        return isValid; 
    }
    public static boolean isBirth(JTextField birth, JLabel errBirth) {
        boolean isValid = false;
        if(birth.getText().equals("")) {
            errBirth.setText("Vui lòng nhập trường này");
            errBirth.setForeground(new java.awt.Color(255, 51, 51));
            birth.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        String pattern = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
        if(!birth.getText().matches(pattern)){
            errBirth.setText("Ngày sinh hợp lệ (dd-mm-yyyy)");
            errBirth.setForeground(new java.awt.Color(255, 51, 51));
            birth.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean isEmail(JTextField txlEmail, JLabel errEmail) {
        boolean isValid = false;
        if(txlEmail.getText().equals("")) {
            errEmail.setText("Vui lòng nhập trường này");
            errEmail.setForeground(new java.awt.Color(255, 51, 51));
            txlEmail.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        String pattern = "^[a-zA-Z]+[a-z0-9]*@{1}[a-zA-Z]+mail.com$";
        if(!txlEmail.getText().toLowerCase().matches(pattern)){
            errEmail.setText("Vui lòng nhập Email chính xác!");
            errEmail.setForeground(new java.awt.Color(255, 51, 51));
            txlEmail.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean isAddress(JTextField txtAddress, JLabel errAddress) {
        boolean isValid = false;
        if(txtAddress.getText().equals("")) {
            errAddress.setText("Vui lòng nhập trường này");
            errAddress.setForeground(new java.awt.Color(255, 51, 51));
            txtAddress.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        return isValid;
    }
    /* Validate Nhà Cung Cấp*/
    public static boolean isEmptySupplier(JTextField txtName, JLabel errName) {
        boolean isValid = false;
        if(txtName.getText().equals("")) {
            errName.setText("Vui lòng nhập trường này");
            errName.setForeground(new java.awt.Color(255, 51, 51));
            txtName.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        NhaCCDAO ccDAO = new NhaCCDAO();
        List<NhaCC> lstNhaCC = ccDAO.checkEmptySupplier(txtName.getText());
        if(!lstNhaCC.isEmpty()) {
            errName.setText("Đã tồn tại nhà cung cấp này");
            errName.setForeground(new java.awt.Color(255, 51, 51));
            txtName.setBorder(getBorders());
            isValid = true;
        } 
        return isValid;
    }
    /* Validate Form Nhan Vien */
    public static boolean isEmptyUser(JTextField txtUser, JLabel errUser){
        boolean isValid = false;
        if(txtUser.getText().equals("")) {
            errUser.setText("Vui lòng nhập trường này");
            errUser.setForeground(new java.awt.Color(255, 51, 51));
            txtUser.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        // Check exist User Name
        NhanVienDAO nvDAO = new NhanVienDAO();
        if(nvDAO.checkEmptyUser(txtUser.getText()) != null) {
            errUser.setText("Tài khoản đã tồn tại");
            errUser.setForeground(new java.awt.Color(255, 51, 51));
            txtUser.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean isEmptyPw(JPasswordField Pw, JLabel errPw) {
        boolean isValid = false;
        if(new String(Pw.getPassword()).equals("")) {
            errPw.setText("Vui lòng nhập trường này");
            errPw.setForeground(new java.awt.Color(255, 51, 51));
            Pw.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        if(new String(Pw.getPassword()).length() < 6) {
            errPw.setText("Vui lòng nhập tối thiệu 6 ký tự");
            errPw.setForeground(new java.awt.Color(255, 51, 51));
            Pw.setBorder(getBorders());
            isValid = true;
        }  
        return isValid;
    }
    public static boolean isEmptyRole(JRadioButton rdoEmp, JRadioButton rdoManager, JLabel errRole) {
        boolean isValid = false;
        if(!rdoEmp.isSelected() && !rdoManager.isSelected()) {
            errRole.setText("Vui lòng chọn vai trò");
            errRole.setForeground(new java.awt.Color(255, 51, 51));
            isValid = true;
        }
        return isValid;
    }
    public static boolean isEmptyGenger(JRadioButton male, JRadioButton female, JLabel errGenger) {
        boolean isValid = false;
        if(!male.isSelected() && !female.isSelected()) {
            errGenger.setText("Vui lòng chọn giới tính");
            errGenger.setForeground(new java.awt.Color(255, 51, 51));
            isValid = true;
        }
        return isValid;
    }
    /* Validate Form thanh toán */
    public static boolean isNameInvoice(JTextField txtFullName, JLabel lblErrFullName) {
         boolean isValid = false;
         // Check Empty
        if(!txtFullName.getText().equals("")) {
            String pattern = "\\d+";
            if(txtFullName.getText().matches(pattern)) {
                lblErrFullName.setText("Vui lòng không nhập số!");
                lblErrFullName.setForeground(new java.awt.Color(255,102,102));
                txtFullName.setBorder(getBorders());
                isValid = true;
            }       
        }
        return isValid;
    }
    public static boolean isPhoneInvoice(JTextField txtPhone, JLabel lblErrPhone) {
        boolean isValid = false;
         // Check Empty
        if(!txtPhone.getText().equals("")) {
            String pattern = "\\d{10}";
            if(!txtPhone.getText().matches(pattern)) {
                lblErrPhone.setText("Vui lòng nhập SĐT chính xác!");
                lblErrPhone.setForeground(new java.awt.Color(255,102,102));
                txtPhone.setBorder(getBorders());
                isValid = true;
            }         
        }
        return isValid; 
    }
    public static boolean isBirthInvoice(JTextField birth, JLabel errBirth) {
        boolean isValid = false;
        if(!birth.getText().equals("")) {
            String pattern = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
            if(!birth.getText().matches(pattern)){
                errBirth.setText("Ngày sinh hợp lệ (dd-mm-yyyy)");
                errBirth.setForeground(new java.awt.Color(255,102,102));
                birth.setBorder(getBorders());
                isValid = true;
            }        
        }
        return isValid;
    }
    public static boolean isEmailInvoice(JTextField txlEmail, JLabel errEmail) {
        boolean isValid = false;
        if(!txlEmail.getText().equals("")) {
            String pattern = "^[a-zA-Z]+[a-z0-9]*@{1}[a-zA-Z]+mail.com$";
            if(!txlEmail.getText().toLowerCase().matches(pattern)){
                errEmail.setText("Vui lòng nhập Email chính xác!");
                errEmail.setForeground(new java.awt.Color(255,102,102));
                txlEmail.setBorder(getBorders());
                isValid = true;
            }       
        }
        return isValid;
    }
    /* Validate Form Supplier */
    public static boolean checkPriceSupplier(JTextField txtPrice, JLabel errPrice) {
        boolean isValid = false;
         // Check Empty
        if(txtPrice.getText().equals("")) {
            errPrice.setText("Vui lòng nhập trường này");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        // Check Price
         String pattern = "[.|\\d|-]+";
        if(!txtPrice.getText().matches(pattern)) {
            errPrice.setText("Vui lòng nhập số");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        float getPrice = 0;
        try {
            getPrice = Float.parseFloat(txtPrice.getText());
            if(getPrice <= 0) {
                errPrice.setText("Vui lòng nhập giá lớn hơn 0");
                errPrice.setForeground(new java.awt.Color(233,38,38));
                txtPrice.setBorder(getBorders());
                isValid = true;
            }          
        } catch (Exception e) {
            errPrice.setText("Vui lòng nhập số");
            errPrice.setForeground(new java.awt.Color(233,38,38));
            txtPrice.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean checkMinQuantity(JTextField txtMinQuantity, JLabel lblErrMinQuantity) {
        boolean isValid = false;
         // Check Empty
        if(txtMinQuantity.getText().equals("")) {
            lblErrMinQuantity.setText("Vui lòng nhập trường này");
            lblErrMinQuantity.setForeground(new java.awt.Color(233,38,38));
            txtMinQuantity.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        // Check Size
        String pattern = "[-|\\d]+";
        if(!txtMinQuantity.getText().matches(pattern)) {
            lblErrMinQuantity.setText("Vui lòng nhập số");
            lblErrMinQuantity.setForeground(new java.awt.Color(233,38,38));
            txtMinQuantity.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        float getSize = 0;
        try {
            getSize = Float.parseFloat(txtMinQuantity.getText());
            if(getSize < 10) {
                lblErrMinQuantity.setText("Vui lòng nhập lớn hơn hoặc bằng 10");
                lblErrMinQuantity.setForeground(new java.awt.Color(233,38,38));
                txtMinQuantity.setBorder(getBorders());
                isValid = true;
            }          
        } catch (Exception e) {
            lblErrMinQuantity.setText("Vui lòng nhập số");
            lblErrMinQuantity.setForeground(new java.awt.Color(233,38,38));
            txtMinQuantity.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
    public static boolean checkImport(JTextField txtImport, JLabel lblErrImport) {
        boolean isValid = false;
         // Check Empty
        if(txtImport.getText().equals("")) {
            lblErrImport.setText("Vui lòng nhập trường này");
            lblErrImport.setForeground(new java.awt.Color(233,38,38));
            txtImport.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        // Check Size
        String pattern = "[-|\\d]+";
        if(!txtImport.getText().matches(pattern)) {
            lblErrImport.setText("Vui lòng nhập số");
            lblErrImport.setForeground(new java.awt.Color(233,38,38));
            txtImport.setBorder(getBorders());
            isValid = true;
            return isValid;
        }
        float getSize = 0;
        try {
            getSize = Float.parseFloat(txtImport.getText());
            if(getSize <= 0) {
                lblErrImport.setText("Số lượng cần lớn hơn 0");
                lblErrImport.setForeground(new java.awt.Color(233,38,38));
                txtImport.setBorder(getBorders());
                isValid = true;
            }          
        } catch (Exception e) {
            lblErrImport.setText("Vui lòng nhập số");
            lblErrImport.setForeground(new java.awt.Color(233,38,38));
            txtImport.setBorder(getBorders());
            isValid = true;
        }
        return isValid;
    }
}
