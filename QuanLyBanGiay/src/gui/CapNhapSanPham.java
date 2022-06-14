/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.LoaiSanPhamDAO;
import dao.SanPhamDAO;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import modal.SanPham;
import utils.DragAndDropProduct;
import utils.MsgBox;
import utils.Validator;
import utils.XDate;
import utils.XImg;

/**
 *
 * @author DELL
 */
public class CapNhapSanPham extends javax.swing.JDialog {

    /**
     * Creates new form CapNhapSanPham
     */
    SanPham sp;
    SanPhamDAO spDAO;
    JFileChooser fileChooser;
    public CapNhapSanPham(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Cập nhập sản phẩm");
        initErr();
        dragAndDropImg();
    }
    public void dragAndDropImg() {
        DragAndDropProduct dad = new DragAndDropProduct(lblImgProduct, lblDragAndDrop);
        new DropTarget(this, dad);      
    }
    public void initErr() {
        lblErrNameProduct.setText(null);
        lblErrPrice.setText(null);
        lblErrSize.setText(null);
        lblErrExpiry.setText(null);
    }
    public void initTextFields() {
        txtNameProduct.setBorder(new javax.swing.JTextField().getBorder());
        txtPrice.setBorder(new javax.swing.JTextField().getBorder());
        txtSize.setBorder(new javax.swing.JTextField().getBorder());
        txtExpiry.setBorder(new javax.swing.JTextField().getBorder());
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(new javax.swing.JTextField().getBorder());
        label.setText(null);
    } 
    /* Validate*/
    public boolean validateNameProduct() {
        return Validator.isEmptyProductUpdate(txtNameProduct, lblErrNameProduct);
    }
    public boolean validatePrice() {
        return Validator.checkPriceUpdate(txtPrice, lblErrPrice);
    }
    public boolean validateSize() {
        return Validator.checkSize(txtSize, lblErrSize);
    }
    public boolean validateExpirt() {
        return Validator.checkExpiry(txtExpiry, lblErrExpiry);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(validateNameProduct()) {
            isFormValid = false;
        }
        if(validatePrice()) {
            isFormValid = false;
        }
        if(validateSize()) {
            isFormValid = false;
        }
        if(validateExpirt()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public void setForm(SanPham sp) {
        // Đỗ dữ liệu loại sản phẩm của sản phẩm đó thuộc về lên combo box 
        txtIdProduct.setText(sp.getMaSP());
        txtNameProduct.setText(sp.getTenSP());
        if(sp.getGia() == 0) {
            txtPrice.setText("");
        } else {
            txtPrice.setText(sp.getGia()+"");
        }
        if(sp.getSize() == 0) {
            txtSize.setText("");
        } else {
            txtSize.setText(sp.getSize()+"");
        }
        if(sp.getHanSuDung()== null) {
            txtExpiry.setText("");
        } else {
            txtExpiry.setText(XDate.toString(sp.getHanSuDung(),"dd-MM-yyyy"));            
        }
        txtDescription.setText(sp.getMoTa());
        if(sp.getHinh()!= null) {
            lblImgProduct.setToolTipText(sp.getHinh());
            ImageIcon iconCore = XImg.readImgProduct(lblImgProduct.getToolTipText());
            Image img = iconCore.getImage();
            ImageIcon iconParse = new ImageIcon(img.getScaledInstance(lblImgProduct.getWidth(),
                    lblImgProduct.getHeight(), Image.SCALE_SMOOTH));
            lblImgProduct.setIcon(iconParse);
            lblDragAndDrop.setVisible(false);
        } else {
            lblImgProduct.setIcon(null);
            lblDragAndDrop.setVisible(true);
        }
    }
    public SanPham getForm() {
        SanPham sp = new SanPham();
        sp.setMaSP(txtIdProduct.getText());
        sp.setTenSP(txtNameProduct.getText());
//        sp.setMaNV(Auth.user.getMaNV());
        sp.setGia(Float.parseFloat(txtPrice.getText()));
        sp.setSize(Integer.parseInt(txtSize.getText()));
        sp.setHinh(lblImgProduct.getToolTipText());
        if(!txtDescription.getText().equals("")) {
            sp.setMoTa(txtDescription.getText());
        }
        if(txtExpiry.getText().equals("")) {
            sp.setHanSuDung(null);
        } else {
            sp.setHanSuDung(XDate.convertDate(txtExpiry.getText(), "dd-MM-yyyy"));
        }      
        return sp;
    }
    public void update() {
        spDAO = new SanPhamDAO();
        int effect = spDAO.update(this.getForm());
        if(effect > 0) {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage("Cập nhập sản phẩm thành công");
            tbForm.setVisible(true);
            initErr();
            initTextFields();
        }
    }
    public void loadImg() {
        fileChooser = new JFileChooser();
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImg.saveImgProduct(file);
            ImageIcon iconCore = XImg.readImgProduct(file.getName());
            Image img = iconCore.getImage();
            ImageIcon iconParsed = new ImageIcon(img.getScaledInstance(lblImgProduct.getWidth(),
                    lblImgProduct.getHeight(),
                    Image.SCALE_SMOOTH));
            lblImgProduct.setIcon(iconParsed);
            lblImgProduct.setToolTipText(file.getName());
            lblDragAndDrop.setVisible(false);
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
        lblIdProduct = new javax.swing.JLabel();
        lblNameProduct = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblSize = new javax.swing.JLabel();
        lblExpiry = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        txtIdProduct = new javax.swing.JTextField();
        txtNameProduct = new javax.swing.JTextField();
        txtExpiry = new javax.swing.JTextField();
        txtSize = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        lblErrNameProduct = new javax.swing.JLabel();
        lblErrPrice = new javax.swing.JLabel();
        lblErrSize = new javax.swing.JLabel();
        lblErrExpiry = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        pnlImgProduct = new javax.swing.JPanel();
        lblDragAndDrop = new javax.swing.JLabel();
        lblImgProduct = new javax.swing.JLabel();
        lblUpdate = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblContent = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIdProduct.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIdProduct.setText("Mã sản phẩm");
        jPanel1.add(lblIdProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 100, -1));

        lblNameProduct.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNameProduct.setText("Tên sản phẩm");
        jPanel1.add(lblNameProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 100, -1));

        lblPrice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPrice.setText("Giá");
        jPanel1.add(lblPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 145, 60, 20));

        lblSize.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSize.setText("Size");
        jPanel1.add(lblSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 145, 40, 20));

        lblExpiry.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblExpiry.setText("Hạn sử dụng");
        jPanel1.add(lblExpiry, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 100, -1));

        lblDescription.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDescription.setText("Mô tả");
        jPanel1.add(lblDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 50, -1));

        txtIdProduct.setEditable(false);
        txtIdProduct.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtIdProduct.setForeground(new java.awt.Color(204, 51, 0));
        jPanel1.add(txtIdProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 320, 30));

        txtNameProduct.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtNameProduct.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameProductFocusLost(evt);
            }
        });
        txtNameProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameProductKeyPressed(evt);
            }
        });
        jPanel1.add(txtNameProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 320, 30));

        txtExpiry.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtExpiry.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtExpiryFocusLost(evt);
            }
        });
        txtExpiry.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtExpiryKeyPressed(evt);
            }
        });
        jPanel1.add(txtExpiry, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 320, 30));

        txtSize.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtSize.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSizeFocusLost(evt);
            }
        });
        txtSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSizeKeyPressed(evt);
            }
        });
        jPanel1.add(txtSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 165, 150, 30));

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
        jPanel1.add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 165, 140, 30));

        lblErrNameProduct.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrNameProduct.setText("Error Message");
        jPanel1.add(lblErrNameProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 310, -1));

        lblErrPrice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPrice.setText("Error Message");
        jPanel1.add(lblErrPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 195, 150, -1));

        lblErrSize.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrSize.setText("Error Message");
        jPanel1.add(lblErrSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 195, 150, -1));

        lblErrExpiry.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrExpiry.setText("Error Message");
        jPanel1.add(lblErrExpiry, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, 320, -1));

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtDescription.setRows(5);
        jScrollPane1.setViewportView(txtDescription);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 330, 320, 50));

        pnlImgProduct.setBackground(new java.awt.Color(255, 255, 255));
        pnlImgProduct.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hình", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(255, 51, 0))); // NOI18N
        pnlImgProduct.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDragAndDrop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDragAndDrop.setForeground(new java.awt.Color(4, 165, 201));
        lblDragAndDrop.setText("Thả hình vào đây");
        pnlImgProduct.add(lblDragAndDrop, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 110, -1));

        lblImgProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblImgProductMousePressed(evt);
            }
        });
        pnlImgProduct.add(lblImgProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 23, 128, 110));

        jPanel1.add(pnlImgProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 160, 150));

        lblUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/button-update .png"))); // NOI18N
        lblUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUpdateMouseClicked(evt);
            }
        });
        jPanel1.add(lblUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 400, 130, 40));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 10, 310));

        lblContent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-updateproduct.jpg"))); // NOI18N
        jPanel1.add(lblContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 470));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUpdateMouseClicked
        if(validateAll()) {
            update();          
        } else {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage("Vui lòng kiểm tra lại dữ liệu");
            tbForm.setVisible(true);
        }
    }//GEN-LAST:event_lblUpdateMouseClicked

    private void lblImgProductMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgProductMousePressed
        if (evt.getClickCount() == 2) {
             loadImg();
        }
    }//GEN-LAST:event_lblImgProductMousePressed

    private void txtNameProductFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameProductFocusLost
        validateNameProduct();
    }//GEN-LAST:event_txtNameProductFocusLost

    private void txtPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusLost
        validatePrice();
    }//GEN-LAST:event_txtPriceFocusLost

    private void txtSizeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSizeFocusLost
        validateSize();
    }//GEN-LAST:event_txtSizeFocusLost

    private void txtExpiryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtExpiryFocusLost
        validateExpirt();
    }//GEN-LAST:event_txtExpiryFocusLost

    private void txtNameProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameProductKeyPressed
        setNullErr(txtNameProduct, lblErrNameProduct);
    }//GEN-LAST:event_txtNameProductKeyPressed

    private void txtPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyPressed
       setNullErr(txtPrice, lblErrPrice);
    }//GEN-LAST:event_txtPriceKeyPressed

    private void txtSizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSizeKeyPressed
       setNullErr(txtSize, lblErrSize);
    }//GEN-LAST:event_txtSizeKeyPressed

    private void txtExpiryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExpiryKeyPressed
        setNullErr(txtExpiry, lblErrExpiry);
    }//GEN-LAST:event_txtExpiryKeyPressed

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
            java.util.logging.Logger.getLogger(CapNhapSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapNhapSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapNhapSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapNhapSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CapNhapSanPham dialog = new CapNhapSanPham(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblContent;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblDragAndDrop;
    private javax.swing.JLabel lblErrExpiry;
    private javax.swing.JLabel lblErrNameProduct;
    private javax.swing.JLabel lblErrPrice;
    private javax.swing.JLabel lblErrSize;
    private javax.swing.JLabel lblExpiry;
    private javax.swing.JLabel lblIdProduct;
    private javax.swing.JLabel lblImgProduct;
    private javax.swing.JLabel lblNameProduct;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblUpdate;
    private javax.swing.JPanel pnlImgProduct;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtExpiry;
    private javax.swing.JTextField txtIdProduct;
    private javax.swing.JTextField txtNameProduct;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtSize;
    // End of variables declaration//GEN-END:variables
}
