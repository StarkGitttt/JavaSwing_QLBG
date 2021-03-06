/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.HoaDonCTDAO;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class HoaDonCTForm extends javax.swing.JDialog {
    DefaultTableModel tblModel;
    HoaDonCTDAO hdCTDAO ;
    List<Object[]> lstObj, lstInfor;
    public HoaDonCTForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Chi tiết hóa đơn");
        initColumn();
        designTable();
        
    }
    public void initColumn() {
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setColumnIdentifiers(new Object[]{"Mã HĐCT", "Mã HĐ", "Mã SP",
        "Tên SP", "Số lượng"});
    }
    public void designTable() {
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12)); 
    }
    public void fillTable(int idInvoice) {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setRowCount(0);
        lstObj = new ArrayList<>();
        lstInfor = new ArrayList<>();
        hdCTDAO = new HoaDonCTDAO();
        // Set Infor Emp And Customer
        lstInfor = hdCTDAO.getInforCAE(idInvoice);
        if(lstInfor.size() > 0) {
            Object[] infor = lstInfor.get(0);
            lblCreater.setText("Người lập: " + infor[0]);
            lblDateCreate.setText("Ngày lập: " + infor[1]);
            lblPrice.setText(formatter.format(infor[2]) + " VNĐ");
        } 
        lstObj = hdCTDAO.getInvoiceDetails(idInvoice);
        if(lstObj.size() > 0) {
            for(Object[] row : lstObj) {
                tblModel.addRow(new Object[]{row[0], row[1], row[2],
                row[3], row[4]});
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
        lblTitle = new javax.swing.JLabel();
        lblCreater = new javax.swing.JLabel();
        lblDateCreate = new javax.swing.JLabel();
        iconCreater = new javax.swing.JLabel();
        iconCalendar = new javax.swing.JLabel();
        sepLine = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        lblNote = new javax.swing.JLabel();
        lblTitleTotal = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblTextNote = new javax.swing.JLabel();
        sepNote = new javax.swing.JSeparator();
        iconInvoiceDetail = new javax.swing.JLabel();
        sepInvoiceDetail = new javax.swing.JSeparator();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("CHI TIẾT HÓA ĐƠN");
        jPanel1.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        lblCreater.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblCreater.setForeground(new java.awt.Color(255, 255, 255));
        lblCreater.setText("Người lập: ");
        jPanel1.add(lblCreater, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 240, -1));

        lblDateCreate.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDateCreate.setForeground(new java.awt.Color(255, 255, 255));
        lblDateCreate.setText("Ngày lập: ");
        jPanel1.add(lblDateCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 240, -1));

        iconCreater.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconCreater.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/person.png"))); // NOI18N
        jPanel1.add(iconCreater, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 50, 50));

        iconCalendar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconCalendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/date-invoiceDetail.png"))); // NOI18N
        jPanel1.add(iconCalendar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 50, 50));
        jPanel1.add(sepLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 172, 400, 10));

        tblList.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblList.setModel(new javax.swing.table.DefaultTableModel(
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
        tblList.setFocusable(false);
        tblList.setRowHeight(30);
        tblList.setSelectionBackground(new java.awt.Color(1, 157, 176));
        tblList.setShowHorizontalLines(false);
        tblList.setShowVerticalLines(false);
        tblList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblList);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 650, 280));

        lblNote.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblNote.setText("Ghi chú: ");
        jPanel1.add(lblNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, -1, -1));

        lblTitleTotal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblTitleTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleTotal.setText("TỔNG TIỀN");
        jPanel1.add(lblTitleTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 550, -1, -1));

        lblPrice.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPrice.setText("jLabel9");
        jPanel1.add(lblPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 600, 190, -1));

        lblTextNote.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblTextNote.setText("<html><body><span>Hóa đơn chi tiết chỉ dùng để xem. Nếu có chênh lệch giá tức có giảm giá.\n</span></body></html>");
        jPanel1.add(lblTextNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 549, 220, 80));
        jPanel1.add(sepNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 60, 10));

        iconInvoiceDetail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconInvoiceDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/invoice-detail.png"))); // NOI18N
        jPanel1.add(iconInvoiceDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, 80));
        jPanel1.add(sepInvoiceDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 122, 158, 10));

        bg.setForeground(new java.awt.Color(255, 255, 255));
        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-invoice2.jpg"))); // NOI18N
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
            java.util.logging.Logger.getLogger(HoaDonCTForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonCTForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonCTForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonCTForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HoaDonCTForm dialog = new HoaDonCTForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel iconCalendar;
    private javax.swing.JLabel iconCreater;
    private javax.swing.JLabel iconInvoiceDetail;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCreater;
    private javax.swing.JLabel lblDateCreate;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblTextNote;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleTotal;
    private javax.swing.JSeparator sepInvoiceDetail;
    private javax.swing.JSeparator sepLine;
    private javax.swing.JSeparator sepNote;
    private javax.swing.JTable tblList;
    // End of variables declaration//GEN-END:variables
}
