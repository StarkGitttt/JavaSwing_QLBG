/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.HoaDonDAO;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modal.HoaDon;



/**
 *
 * @author DELL
 */
public class ThongKeHDForm extends javax.swing.JDialog {
    DefaultTableModel tblModelTop, tblModelCancel;
    HoaDonDAO hdDAO;
    List<HoaDon> lstHDTop, lstHDCancel;
    public ThongKeHDForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Nhà cung cấp");
        designTable();
        initColumn();
        fillTableTop();
    }
   
    public void navShowTop() {
        layered.removeAll();
        layered.add(pnlContentTop);
        layered.repaint();
        layered.revalidate();
    }
    public void navShowCancel() {
        layered.removeAll();
        layered.add(pnlContentCancel);
        layered.repaint();
        layered.revalidate();
        fillTableCancel();
    }
   
    public void designTable() {
//          Table List
        tblListTop.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblListTop.getTableHeader().setForeground(new java.awt.Color(11,89,214));
        // Table Familiar Supplier
        tblListCancel.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblListCancel.getTableHeader().setForeground(new java.awt.Color(11,89,214));
    }
    public void initColumn() {
        // Column List Top
        tblModelTop = (DefaultTableModel) tblListTop.getModel();
        tblModelTop.setColumnIdentifiers(new Object[]{"Mã HĐ", "Mã KH", "Mã NV",
        "Ngày lập HĐ", "Đơn giá"});
        // Column List Cancel
        tblModelCancel = (DefaultTableModel) tblListCancel.getModel();
        tblModelCancel.setColumnIdentifiers(new Object[]{"Mã HĐ", "Mã KH", "Mã NV",
        "Ngày lập HĐ", "Đơn giá", "Lý do"});
    }
    public void fillTableTop() {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        hdDAO = new HoaDonDAO();
        lstHDTop = new ArrayList<>();
        tblModelTop = (DefaultTableModel) tblListTop.getModel();
        tblModelTop.setRowCount(0);
        lstHDTop = hdDAO.getStatisticsTop10Invoice();
        if(!lstHDTop.isEmpty()) {
            for(HoaDon hd : lstHDTop) {
                tblModelTop.addRow(new Object[]{hd.getMaHD(), hd.getMaKH(),
                hd.getMaNV(), hd.getNgayLapHD(),formatter.format(hd.getDonGia())+" VNĐ"});
            }
        }
    }
    public void fillTableCancel() {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        hdDAO = new HoaDonDAO();
        lstHDCancel = new ArrayList<>();
        tblModelCancel = (DefaultTableModel) tblListCancel.getModel();
        tblModelCancel.setRowCount(0);
        lstHDCancel = hdDAO.getStatisticsInvoiceCancel();
        if(!lstHDCancel.isEmpty()) {
            for(HoaDon hd : lstHDCancel) {
                tblModelCancel.addRow(new Object[]{hd.getMaHD(), hd.getMaKH(),
                hd.getMaNV(), hd.getNgayLapHD(),
                formatter.format(hd.getDonGia())+ "VNĐ", hd.getGhiChu()});
            }
        } else {
            ThongBaoForm tb = new ThongBaoForm(null, true);
            tb.setMessage("Không có dữ liệu");
            tb.setVisible(true);
        }
    }
    public void viewDetailTop() {
        int getRowCount = tblListTop.getRowCount();
        if(getRowCount > 0) {
            int index = tblListTop.getSelectedRow();
            if(index >= 0) {
                int getId = (int) tblListTop.getValueAt(index, 0);
                HoaDonCTForm view = new HoaDonCTForm(null, true);
                view.fillTable(getId);
                view.setVisible(true);
            }
        }
    }
    public void viewDetailCancel() {
        int getRowCount = tblListCancel.getRowCount();
        if(getRowCount > 0) {
            int index = tblListCancel.getSelectedRow();
            if(index >= 0) {
                int getId = (int) tblListCancel.getValueAt(index, 0);
                HoaDonCTCancelForm view = new HoaDonCTCancelForm(null, true);
                view.fillTable(getId);
                view.setVisible(true);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnlAdd = new javax.swing.JPanel();
        lblTitleTop = new javax.swing.JLabel();
        lblIconTop = new javax.swing.JLabel();
        lblAdd = new javax.swing.JLabel();
        pnlCancel = new javax.swing.JPanel();
        lblIconCancel = new javax.swing.JLabel();
        lblTitleCancel = new javax.swing.JLabel();
        lblList = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        layered = new javax.swing.JLayeredPane();
        pnlContentTop = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListTop = new javax.swing.JTable();
        lblViewTop = new javax.swing.JLabel();
        lblTop = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlContentCancel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblListCancel = new javax.swing.JTable();
        lblViewCancel = new javax.swing.JLabel();
        lblTop1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nav = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("HÓA ĐƠN");
        jPanel1.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 120, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 140, 10));

        pnlAdd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleTop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitleTop.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleTop.setText("TOP 10");
        lblTitleTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleTopMouseClicked(evt);
            }
        });
        pnlAdd.add(lblTitleTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 70, 30));

        lblIconTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconTop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/top-invoice.png"))); // NOI18N
        lblIconTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleTopMouseClicked(evt);
            }
        });
        pnlAdd.add(lblIconTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleTopMouseClicked(evt);
            }
        });
        pnlAdd.add(lblAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 160, 50));

        pnlCancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconCancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/invoice-cancel.png"))); // NOI18N
        lblIconCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleCancelMouseClicked(evt);
            }
        });
        pnlCancel.add(lblIconCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblTitleCancel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitleCancel.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleCancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleCancel.setText("ĐÃ HỦY");
        lblTitleCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleCancelMouseClicked(evt);
            }
        });
        pnlCancel.add(lblTitleCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 20, 80, -1));

        lblList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleCancelMouseClicked(evt);
            }
        });
        pnlCancel.add(lblList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 160, 50));

        layered.setLayout(new java.awt.CardLayout());

        pnlContentTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblListTop.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblListTop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblListTop.setRowHeight(30);
        tblListTop.setSelectionBackground(new java.awt.Color(1, 157, 176));
        jScrollPane1.setViewportView(tblListTop);

        pnlContentTop.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 530, 260));

        lblViewTop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-detail.png"))); // NOI18N
        lblViewTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblViewTopMouseClicked(evt);
            }
        });
        pnlContentTop.add(lblViewTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 330, 110, 60));

        lblTop.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lblTop.setForeground(new java.awt.Color(228, 96, 64));
        lblTop.setText("TOP 10 HÓA ĐƠN");
        pnlContentTop.add(lblTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-ListSupplier.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        pnlContentTop.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 390));

        layered.add(pnlContentTop, "card3");

        pnlContentCancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        tblListCancel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblListCancel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblListCancel.setRowHeight(30);
        tblListCancel.setSelectionBackground(new java.awt.Color(1, 157, 176));
        jScrollPane2.setViewportView(tblListCancel);

        pnlContentCancel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 530, 260));

        lblViewCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-detail.png"))); // NOI18N
        lblViewCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblViewCancelMouseClicked(evt);
            }
        });
        pnlContentCancel.add(lblViewCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, -1, -1));

        lblTop1.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lblTop1.setForeground(new java.awt.Color(228, 96, 64));
        lblTop1.setText("DANH SÁCH HÓA ĐƠN BỊ HỦY");
        pnlContentCancel.add(lblTop1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-ListSupplier.jpg"))); // NOI18N
        jLabel3.setText("jLabel3");
        pnlContentCancel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 390));

        layered.add(pnlContentCancel, "card2");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layered)
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layered)
        );

        jPanel1.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 600, 460));

        nav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/left-nav.png"))); // NOI18N
        jPanel1.add(nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 753, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTitleTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleTopMouseClicked
        navShowTop();
    }//GEN-LAST:event_lblTitleTopMouseClicked

    private void lblTitleCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleCancelMouseClicked
       navShowCancel();
    }//GEN-LAST:event_lblTitleCancelMouseClicked

    private void lblViewCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblViewCancelMouseClicked
       viewDetailCancel();
    }//GEN-LAST:event_lblViewCancelMouseClicked

    private void lblViewTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblViewTopMouseClicked
         viewDetailTop();
    }//GEN-LAST:event_lblViewTopMouseClicked

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
            java.util.logging.Logger.getLogger(ThongKeHDForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeHDForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeHDForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeHDForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThongKeHDForm dialog = new ThongKeHDForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLayeredPane layered;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblIconCancel;
    private javax.swing.JLabel lblIconTop;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleCancel;
    private javax.swing.JLabel lblTitleTop;
    private javax.swing.JLabel lblTop;
    private javax.swing.JLabel lblTop1;
    private javax.swing.JLabel lblViewCancel;
    private javax.swing.JLabel lblViewTop;
    private javax.swing.JLabel nav;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlCancel;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlContentCancel;
    private javax.swing.JPanel pnlContentTop;
    private javax.swing.JTable tblListCancel;
    private javax.swing.JTable tblListTop;
    // End of variables declaration//GEN-END:variables
}
