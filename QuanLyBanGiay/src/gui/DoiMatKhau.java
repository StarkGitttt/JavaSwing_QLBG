/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.NhanVienDAO;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import utils.Auth;
import utils.MsgBox;
import utils.Validator;

/**
 *
 * @author DELL
 */
public class DoiMatKhau extends javax.swing.JDialog {

    /**
     * Creates new form DoiMatKhau
     */
    NhanVienDAO nvDAO;
    public DoiMatKhau(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Đổi mật khẩu");
        initErr();
        
    }
    public void initErr() {
        lblErrConfirmPw.setText(null);
        lblErrOldPw.setText(null);
        lblErrNewPw.setText(null);
        txtOldPw.setBorder(border.getBorder());
        txtNewPw.setBorder(border.getBorder());
        txtConfirmPw.setBorder(border.getBorder());
    }
    public void resetForm() {
        initErr();
        txtOldPw.setText(null);
        txtNewPw.setText(null);
        txtConfirmPw.setText(null);
    }
    public boolean validateOldPw() {
        return Validator.isEmptyOldPw(txtOldPw, lblErrOldPw);
    }
    public boolean validateNewPw() {
        return Validator.isEmptyPassword(txtNewPw, lblErrNewPw);
    }
    public boolean validateConfirmPw() {
        return Validator.isEmptyConfirmPw(txtNewPw, txtConfirmPw, lblErrConfirmPw);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(validateOldPw()) {
            isFormValid = false;
        }
        if(validateNewPw()) {
            isFormValid = false;
        }
        if(validateConfirmPw()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public void update() {
        if(validateAll()) {
            nvDAO = new NhanVienDAO();
            int rowEffect = nvDAO.updatePw(Auth.user.getMaNV(), new String(txtNewPw.getPassword()));
            if(rowEffect > 0 ) {
                MsgBox.alert(this, "Đổi mật khẩu thành công");
                resetForm();
            } else {
                MsgBox.alert(this, "Đổi mật khẩu thất bại");
            }
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblErrConfirmPw = new javax.swing.JLabel();
        lblErrOldPw = new javax.swing.JLabel();
        lblErrNewPw = new javax.swing.JLabel();
        txtNewPw = new javax.swing.JPasswordField();
        txtConfirmPw = new javax.swing.JPasswordField();
        txtOldPw = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblChangePw = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        border = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Mật khẩu cũ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Mật khẩu mới");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 195, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Nhập lại mập khẩu ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        lblErrConfirmPw.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrConfirmPw.setText("Error Message");
        jPanel1.add(lblErrConfirmPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 240, -1));

        lblErrOldPw.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrOldPw.setText("Error Message");
        jPanel1.add(lblErrOldPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 240, -1));

        lblErrNewPw.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrNewPw.setText("Error Message");
        jPanel1.add(lblErrNewPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 255, 240, -1));

        txtNewPw.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtNewPw.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNewPwFocusLost(evt);
            }
        });
        txtNewPw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNewPwKeyPressed(evt);
            }
        });
        jPanel1.add(txtNewPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 215, 240, 37));

        txtConfirmPw.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtConfirmPw.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtConfirmPwFocusLost(evt);
            }
        });
        txtConfirmPw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConfirmPwKeyPressed(evt);
            }
        });
        jPanel1.add(txtConfirmPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 240, 37));

        txtOldPw.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtOldPw.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOldPwFocusLost(evt);
            }
        });
        txtOldPw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOldPwKeyPressed(evt);
            }
        });
        jPanel1.add(txtOldPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 240, 37));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 51, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("*");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 111, 30, 12));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 51, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("*");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 195, 20, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 51, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("*");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 20, -1));

        lblChangePw.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChangePw.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-changePw2.png"))); // NOI18N
        lblChangePw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblChangePwMouseClicked(evt);
            }
        });
        jPanel1.add(lblChangePw, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 365, 150, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-changePw3.jpg"))); // NOI18N
        jLabel1.setToolTipText("");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 355, 415));

        border.setText("jTextField1");
        jPanel1.add(border, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 20, 30));

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

    private void lblChangePwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChangePwMouseClicked
        update();
    }//GEN-LAST:event_lblChangePwMouseClicked

    private void txtOldPwFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOldPwFocusLost
        validateOldPw();
    }//GEN-LAST:event_txtOldPwFocusLost

    private void txtNewPwFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNewPwFocusLost
       validateNewPw();
    }//GEN-LAST:event_txtNewPwFocusLost

    private void txtConfirmPwFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConfirmPwFocusLost
        validateConfirmPw();
    }//GEN-LAST:event_txtConfirmPwFocusLost

    private void txtOldPwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOldPwKeyPressed
        txtOldPw.setBorder(border.getBorder());
        lblErrOldPw.setText(null);
    }//GEN-LAST:event_txtOldPwKeyPressed

    private void txtNewPwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewPwKeyPressed
        txtNewPw.setBorder(border.getBorder());
        lblErrNewPw.setText(null);
    }//GEN-LAST:event_txtNewPwKeyPressed

    private void txtConfirmPwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConfirmPwKeyPressed
        txtConfirmPw.setBorder(border.getBorder());
        lblErrConfirmPw.setText(null);
    }//GEN-LAST:event_txtConfirmPwKeyPressed

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
            java.util.logging.Logger.getLogger(DoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DoiMatKhau dialog = new DoiMatKhau(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField border;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblChangePw;
    private javax.swing.JLabel lblErrConfirmPw;
    private javax.swing.JLabel lblErrNewPw;
    private javax.swing.JLabel lblErrOldPw;
    private javax.swing.JPasswordField txtConfirmPw;
    private javax.swing.JPasswordField txtNewPw;
    private javax.swing.JPasswordField txtOldPw;
    // End of variables declaration//GEN-END:variables
}
