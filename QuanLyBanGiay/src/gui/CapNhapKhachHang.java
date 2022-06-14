/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.KhachHangDAO;
import modal.KhachHang;
import utils.MsgBox;
import utils.Validator;
import utils.XDate;

/**
 *
 * @author DELL
 */
public class CapNhapKhachHang extends javax.swing.JDialog {

    /**
     * Creates new form CapNhapKhachHang
     */
    public CapNhapKhachHang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Cập nhập thông tin khách hàng");
        setLocationRelativeTo(null);
        txtIdKH.setEditable(false);
        initErr();
    }
    public void initErr() {
        lblErrFullName.setText(null);
        lblErrBirth.setText(null);
        lblErrEmail.setText(null);
        lblErrPhone.setText(null);
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(new javax.swing.JTextField().getBorder());
        label.setText(null);
    }
    public boolean validateName() {
        return Validator.isName(txtFullName, lblErrFullName);
    }
    public boolean validateEmail() {
        return Validator.isEmail(txtEmail, lblErrEmail);
    }
    public boolean validatePhone() {
        return Validator.isPhone(txtPhone, lblErrPhone);
    }
    public boolean validateBirth() {
        return Validator.isBirth(txtBirth, lblErrBirth);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(validateName()) {
            isFormValid = false;
        }
        if(validateEmail()) {
            isFormValid = false;
        }
        if(validatePhone()) {
            isFormValid = false;
        }
        if(validateBirth()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public void setForm(KhachHang kh) {
        txtIdKH.setText(kh.getMaKH());
        txtFullName.setText(kh.getHoTen());
        txtEmail.setText(kh.getEmail());
        txtPhone.setText(kh.getDienThoai());
        txtBirth.setText(XDate.toString(kh.getNgaySinh(), "dd-MM-yyyy"));
    }
    public KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setMaKH(txtIdKH.getText());
        kh.setHoTen(txtFullName.getText());
        kh.setEmail(txtEmail.getText());
        kh.setDienThoai(txtPhone.getText());
        kh.setNgaySinh(XDate.convertDate(txtBirth.getText(), "dd-MM-yyyy"));
        return kh;
    }
    public void update() {
        KhachHangDAO khDAO = new KhachHangDAO();
        int rowEffect = khDAO.update(this.getForm());
        if(rowEffect > 0) {
            new KhachHangForm(null,true).fillTableList();
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage("Cập nhập thông tin thành công");
            tbForm.setVisible(true);
        } else {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage("Cập nhập thông tin thất bại");
            tbForm.setVisible(true);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblErrFullName = new javax.swing.JLabel();
        lblErrEmail = new javax.swing.JLabel();
        lblErrPhone = new javax.swing.JLabel();
        lblErrBirth = new javax.swing.JLabel();
        txtIdKH = new javax.swing.JTextField();
        txtFullName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtBirth = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(94, 46, 145));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-updateCustomer.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 140, 60));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/boy-sit.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, -10, 120, 120));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Mã khách hàng");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Ngày sinh");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 90, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Họ tên");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 80, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Email");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 34, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Số điện thoại");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 90, -1));

        lblErrFullName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrFullName.setText("Error Message");
        jPanel2.add(lblErrFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 140, -1));

        lblErrEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrEmail.setText("Error Message");
        jPanel2.add(lblErrEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 150, -1));

        lblErrPhone.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPhone.setText("Error Message");
        jPanel2.add(lblErrPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 140, -1));

        lblErrBirth.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrBirth.setText("Error Message");
        jPanel2.add(lblErrBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 150, -1));

        txtIdKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIdKH.setForeground(new java.awt.Color(204, 0, 0));
        jPanel2.add(txtIdKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 320, 38));

        txtFullName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtFullName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFullNameFocusLost(evt);
            }
        });
        txtFullName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFullNameKeyPressed(evt);
            }
        });
        jPanel2.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 140, 38));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });
        jPanel2.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 150, 38));

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPhoneFocusLost(evt);
            }
        });
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneKeyPressed(evt);
            }
        });
        jPanel2.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 140, 38));

        txtBirth.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtBirth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBirthFocusLost(evt);
            }
        });
        txtBirth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBirthKeyPressed(evt);
            }
        });
        jPanel2.add(txtBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, 150, 38));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 81, 360, 340));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-updateUser.jpg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        if(validateAll()) {
            update();           
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void txtFullNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFullNameFocusLost
        validateName();
    }//GEN-LAST:event_txtFullNameFocusLost

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        validateEmail();
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusLost
        validatePhone();
    }//GEN-LAST:event_txtPhoneFocusLost

    private void txtBirthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBirthFocusLost
        validateBirth();
    }//GEN-LAST:event_txtBirthFocusLost

    private void txtFullNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFullNameKeyPressed
        setNullErr(txtFullName, lblErrFullName);
    }//GEN-LAST:event_txtFullNameKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        setNullErr(txtEmail, lblErrEmail);
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        setNullErr(txtPhone, lblErrPhone);
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void txtBirthKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBirthKeyPressed
        setNullErr(txtBirth, lblErrBirth);
    }//GEN-LAST:event_txtBirthKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CapNhapKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapNhapKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapNhapKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapNhapKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CapNhapKhachHang dialog = new CapNhapKhachHang(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblErrBirth;
    private javax.swing.JLabel lblErrEmail;
    private javax.swing.JLabel lblErrFullName;
    private javax.swing.JLabel lblErrPhone;
    private javax.swing.JTextField txtBirth;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtIdKH;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
