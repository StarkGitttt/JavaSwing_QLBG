/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.NhaCCDAO;
import modal.NhaCC;
import utils.MsgBox;
import utils.Validator;

/**
 *
 * @author DELL
 */
public class ChiTietNhaCC extends javax.swing.JDialog {

    NhaCCDAO nhaCCDAO;    
    public ChiTietNhaCC(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initErr();
        setTitle("Cập nhập");
        setLocationRelativeTo(null);
    }
    public void initErr() {
        txtId.setEditable(false);
        lblErrName.setText(null);
        lblErrPhone.setText(null);
        lblErrEmail.setText(null);
        lblErrAddress.setText(null);
        txtName.setBorder(new javax.swing.JTextField().getBorder());
        txtPhone.setBorder(new javax.swing.JTextField().getBorder());
        txtEmail.setBorder(new javax.swing.JTextField().getBorder());
        txtAddress.setBorder(new javax.swing.JTextField().getBorder());
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(new javax.swing.JTextField().getBorder());
        label.setText(null);
    } 
    public void showMessage(String message) {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage(message);
            tbForm.setVisible(true);
    }
    /* Validate */
    public boolean validateSupplier() {
        return Validator.isName(txtName, lblErrName);
    }
    public boolean validatePhone() {
        return Validator.isPhone(txtPhone, lblErrPhone);
    }
    public boolean validateEmail() {
        return Validator.isEmail(txtEmail, lblErrEmail);
    }
    public boolean validateAddress() {
        return Validator.isAddress(txtAddress, lblErrAddress);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(validateSupplier()) {
            isFormValid = false;
        }
        if(validatePhone()) {
            isFormValid = false;
        }
        if(validateEmail()) {
            isFormValid = false;
        }
        if(validateAddress()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public NhaCC getForm() {
        NhaCC nhaCC = new NhaCC();
        nhaCC.setMaNhaCC(Integer.parseInt(txtId.getText()));
        nhaCC.setTenNhaCC(txtName.getText());
        nhaCC.setDienThoai(txtPhone.getText());
        nhaCC.setEmail(txtEmail.getText());
        nhaCC.setDiaChi(txtAddress.getText());
        nhaCC.setGhiChu(txtNote.getText());
        return nhaCC;
    }
    public void setForm(NhaCC nhaCC) {
        txtId.setText(nhaCC.getMaNhaCC()+"");
        txtName.setText(nhaCC.getTenNhaCC());
        txtPhone.setText(nhaCC.getDienThoai());
        txtEmail.setText(nhaCC.getEmail());
        txtAddress.setText(nhaCC.getDiaChi());
        txtNote.setText(nhaCC.getGhiChu());
    }
    public void add() {
        nhaCCDAO = new NhaCCDAO();
        int rowEffect = nhaCCDAO.update(this.getForm());
        if(rowEffect > 0) {
            showMessage("Cập nhập thành công");
            initErr();
        } else {
            showMessage("Cập nhập thất bại");         
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

        jPanel1 = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblNote = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtNote = new javax.swing.JTextField();
        lblErrName = new javax.swing.JLabel();
        lblErrPhone = new javax.swing.JLabel();
        lblErrEmail = new javax.swing.JLabel();
        lblErrAddress = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblId.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblId.setText("Mã nhà cung cấp");
        jPanel1.add(lblId, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 110, -1));

        lblName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblName.setText("Tên nhà cung cấp");
        jPanel1.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 110, -1));

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblEmail.setText("Email");
        jPanel1.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 40, -1));

        lblPhone.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPhone.setText("Số điện thoại");
        jPanel1.add(lblPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, 90, -1));

        lblAddress.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblAddress.setText("Địa chỉ");
        jPanel1.add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 50, -1));

        lblNote.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNote.setText("Ghi chú");
        jPanel1.add(lblNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, 50, -1));

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });
        jPanel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 170, 30));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 170, 30));

        txtId.setForeground(new java.awt.Color(204, 51, 0));
        jPanel1.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 170, 30));

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
        jPanel1.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, 170, 30));

        txtAddress.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAddressFocusLost(evt);
            }
        });
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAddressKeyPressed(evt);
            }
        });
        jPanel1.add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 170, 30));

        txtNote.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanel1.add(txtNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, 170, 30));

        lblErrName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrName.setText("Error Message");
        jPanel1.add(lblErrName, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 170, -1));

        lblErrPhone.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPhone.setText("Error Message");
        jPanel1.add(lblErrPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 270, 170, -1));

        lblErrEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrEmail.setText("Error Message");
        jPanel1.add(lblErrEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 170, -1));

        lblErrAddress.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrAddress.setText("Error Message");
        jPanel1.add(lblErrAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 170, -1));

        btnUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-updateSup.png"))); // NOI18N
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 130, 40));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-detailSupplier2.jpg"))); // NOI18N
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        if(validateAll()) {
            if(MsgBox.confirm(this, "Bạn đã chắc chắn với thông tin đã nhập?" )) {
                add();
            }           
        }
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        validateSupplier();
    }//GEN-LAST:event_txtNameFocusLost

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        validateEmail();
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusLost
        validatePhone();
    }//GEN-LAST:event_txtPhoneFocusLost

    private void txtAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAddressFocusLost
        validateAddress();
    }//GEN-LAST:event_txtAddressFocusLost

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        setNullErr(txtName, lblErrName);
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        setNullErr(txtEmail, lblErrEmail);
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        setNullErr(txtPhone, lblErrPhone);
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void txtAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyPressed
        setNullErr(txtAddress, lblErrAddress);
    }//GEN-LAST:event_txtAddressKeyPressed

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
            java.util.logging.Logger.getLogger(ChiTietNhaCC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChiTietNhaCC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChiTietNhaCC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChiTietNhaCC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChiTietNhaCC dialog = new ChiTietNhaCC(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel bg;
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblErrAddress;
    private javax.swing.JLabel lblErrEmail;
    private javax.swing.JLabel lblErrName;
    private javax.swing.JLabel lblErrPhone;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
