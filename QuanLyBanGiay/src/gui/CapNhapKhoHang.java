/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.KhoHangDAO;
import java.text.DecimalFormat;
import modal.KhoHang;
import utils.Validator;



/**
 *
 * @author DELL
 */
public class CapNhapKhoHang extends javax.swing.JDialog {
    KhoHang kh;
    KhoHangDAO khDAO;
    /**
     * Creates new form CapNhapKhachHang
     */
    public CapNhapKhoHang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Chỉnh sủa thông tin kho hàng");
        setLocationRelativeTo(null);
        txtName.setEditable(false);
        txtTotal.setEditable(false);
        initErr();
    }
    public void initErr() {
        lblErrQuantity.setText(null);
        lblErrMinQuantity.setText(null);
        lblErrPrice.setText(null);
        lblErrTotal.setText(null);
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(new javax.swing.JTextField().getBorder());
        label.setText(null);
    }
    public float convertMoney(String value) {
        String cut = value.substring(0, value.lastIndexOf(" ")).trim();
        String replaceStr = cut.replace(",","");
        float convert = Float.parseFloat(replaceStr);
        return convert;
    } 
    public void showMessage(String message) {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage(message);
            tbForm.setVisible(true);
    }
    /* Validate */
    public boolean isImport() {
        return Validator.checkImport(txtQuantity, lblErrQuantity);
    }
    public boolean isMinQuantity() {
        return Validator.checkMinQuantity(txtMinQuantity, lblErrMinQuantity);
    }
    public boolean isPrice() {
        return Validator.checkPriceSupplier(txtPrice, lblErrPrice);
    }
    public boolean isTotal() {
        return Validator.checkPriceSupplier(txtTotal, lblErrTotal);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(isImport()) {
            isFormValid = false;
        }
        if(isMinQuantity()) {
            isFormValid = false;
        }
        if(isPrice()) {
            isFormValid = false;
        }
        if(isTotal()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public KhoHang getForm() {
        KhoHang kHang = new KhoHang();
        float getPrice = 0;
        kHang.setStt(kh.getStt());
        kHang.setThucNhap(Integer.parseInt(txtQuantity.getText()));
        kHang.setSlToiThieu(Integer.parseInt(txtMinQuantity.getText()));
        // Xử lý trường hợp khi có VNĐ
        kHang.setDonGia(Float.parseFloat(txtPrice.getText()));
        kHang.setThanhTien(Float.parseFloat(txtTotal.getText()));
        return kHang;
    }
    public void setForm(KhoHang kHang) {
        khDAO = new KhoHangDAO();
        kh = new KhoHang();
        kh = kHang;
        Object getName = new Object();
        getName = khDAO.getNameProduct(kHang.getStt());
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        txtName.setText(getName+"");
        txtQuantity.setText(kHang.getThucNhap()+"");
        txtMinQuantity.setText(kHang.getSlToiThieu()+"");
        txtPrice.setText(kHang.getDonGia()+"");
        txtTotal.setText(kHang.getThanhTien()+"");
    }
    public void updateTotal() {
        boolean isFormValid = true;
        if(isImport()) {
            isFormValid = false;
        }
        if(isPrice()) {
            isFormValid = false;
        }
        if(isFormValid) {
            float getQuantity = Float.parseFloat(txtQuantity.getText());
            float getPrice = Float.parseFloat(txtPrice.getText());
            txtTotal.setText(String.format("%.2f", getQuantity * getPrice));
        }
    }
    public void updateSupplier() {
        try {
            khDAO = new KhoHangDAO();
            int rowEffect = khDAO.update(this.getForm());           
            if(rowEffect > 0) {
                showMessage("Cập nhập thành công");
            }
        } catch (Exception e) {
                showMessage("Cập nhập thành công");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblErrQuantity = new javax.swing.JLabel();
        lblErrMinQuantity = new javax.swing.JLabel();
        lblErrPrice = new javax.swing.JLabel();
        lblErrTotal = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtMinQuantity = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        shape = new javax.swing.JLabel();
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tên sản phẩm");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, -1));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotal.setText("Thành tiền");
        jPanel2.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 90, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Thực nhập");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 80, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Số lượng tối thiểu");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 150, -1));

        lblPrice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPrice.setText("Đơn giá");
        jPanel2.add(lblPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 90, -1));

        lblErrQuantity.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrQuantity.setText("Error Message");
        jPanel2.add(lblErrQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 170, -1));

        lblErrMinQuantity.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrMinQuantity.setText("Error Message");
        jPanel2.add(lblErrMinQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 170, -1));

        lblErrPrice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPrice.setText("Error Message");
        jPanel2.add(lblErrPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 170, -1));

        lblErrTotal.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrTotal.setText("Error Message");
        jPanel2.add(lblErrTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 170, -1));

        txtName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 360, 38));

        txtQuantity.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQuantityFocusLost(evt);
            }
        });
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQuantityKeyPressed(evt);
            }
        });
        jPanel2.add(txtQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 170, 38));

        txtMinQuantity.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMinQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMinQuantityFocusLost(evt);
            }
        });
        txtMinQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMinQuantityKeyPressed(evt);
            }
        });
        jPanel2.add(txtMinQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 170, 38));

        txtPrice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPriceFocusLost(evt);
            }
        });
        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPriceKeyPressed(evt);
            }
        });
        jPanel2.add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 170, 38));

        txtTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(204, 51, 0));
        txtTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTotalFocusLost(evt);
            }
        });
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalKeyPressed(evt);
            }
        });
        jPanel2.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 170, 38));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 81, 400, 340));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("CẬP NHẬP KHO HÀNG");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 22, -1, -1));

        shape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/shape.png"))); // NOI18N
        jPanel1.add(shape, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 220, 60));

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
            updateSupplier();       
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void txtQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantityFocusLost
        isImport();
    }//GEN-LAST:event_txtQuantityFocusLost

    private void txtMinQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinQuantityFocusLost
        isMinQuantity();
    }//GEN-LAST:event_txtMinQuantityFocusLost

    private void txtPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusLost
        isPrice();
    }//GEN-LAST:event_txtPriceFocusLost

    private void txtTotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalFocusLost
        isTotal();
    }//GEN-LAST:event_txtTotalFocusLost

    private void txtQuantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyPressed
       setNullErr(txtQuantity, lblErrQuantity);
       updateTotal();
    }//GEN-LAST:event_txtQuantityKeyPressed

    private void txtMinQuantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMinQuantityKeyPressed
       setNullErr(txtMinQuantity, lblErrMinQuantity);       
    }//GEN-LAST:event_txtMinQuantityKeyPressed

    private void txtPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyPressed
       setNullErr(txtPrice, lblErrPrice);
       updateTotal();
    }//GEN-LAST:event_txtPriceKeyPressed

    private void txtTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyPressed
        
    }//GEN-LAST:event_txtTotalKeyPressed

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
            java.util.logging.Logger.getLogger(CapNhapKhoHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapNhapKhoHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapNhapKhoHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapNhapKhoHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CapNhapKhoHang dialog = new CapNhapKhoHang(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblErrMinQuantity;
    private javax.swing.JLabel lblErrPrice;
    private javax.swing.JLabel lblErrQuantity;
    private javax.swing.JLabel lblErrTotal;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel shape;
    private javax.swing.JTextField txtMinQuantity;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
