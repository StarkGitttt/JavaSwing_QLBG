/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.HoaDonDAO;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modal.HoaDon;
import utils.Auth;
import utils.MsgBox;
import utils.XDate;

/**
 *
 * @author DELL
 */
public class HoaDonForm extends javax.swing.JDialog {
    HoaDon hd;
    HoaDonDAO hdDAO;
    List<HoaDon> lstHD;
    DefaultTableModel tblModel;
    DecimalFormat formatter;
    ThongBaoForm tbForm;
    public HoaDonForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Hóa đơn");
        designCalendar();
        initColumn();
        designTable();
        fillTable();  
    }
    public void designCalendar() {
        dateFrom.setDateFormatString("dd-MM-yyyy");
        dateEnd.setDateFormatString("dd-MM-yyyy");
        dateFrom.setIcon(new javax.swing.ImageIcon("src\\icons\\calendar.png"));
        dateEnd.setIcon(new javax.swing.ImageIcon("src\\icons\\calendar.png"));       
    }
    public void initColumn() {
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setColumnIdentifiers(new Object[]{"Mã HD", "Mã KH", "Mã NV",
            "Ngày lập HD", "Đơn giá"});
        
    }
    public void designTable() {
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12)); 
    }
    public boolean validateDiffDay() {
        boolean isValid = true;
        long getDiff = XDate.differentDays(XDate.toString(dateFrom.getDate(), "dd-MM-yyyy"),
                XDate.toString(dateEnd.getDate(), "dd-MM-yyyy"));
        if(getDiff < 0) {
            isValid = false;
        }
        return isValid;
    }
    public void fillTable() {
        formatter = new DecimalFormat("###,###,###");
        hdDAO = new HoaDonDAO();
        lstHD = new ArrayList<>();
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setRowCount(0);
        if(dateFrom.getDate() == null
                || dateEnd.getDate() == null) {
            lstHD = hdDAO.getStatisticsInvoice(null, null);
            if(!lstHD.isEmpty()) {
                for(HoaDon hd : lstHD ) {
                    tblModel.addRow(new Object[]{hd.getMaHD(), hd.getMaKH(),
                    hd.getMaNV(), hd.getNgayLapHD(),
                    formatter.format(hd.getDonGia()) + " VNĐ"});
                }               
            }
        } else if (dateFrom.getDate() != null && dateEnd.getDate() != null){
            if(validateDiffDay()) {
                lstHD = hdDAO.getStatisticsInvoice(
                XDate.convertDate(XDate.toString(dateFrom.getDate(),"yyyy-MM-dd"), "yyyy-MM-dd"),
                XDate.convertDate(XDate.toString(dateEnd.getDate(),"yyyy-MM-dd"), "yyyy-MM-dd"));          
                if(!lstHD.isEmpty()) {
                    for(HoaDon hd : lstHD ) {
                        tblModel.addRow(new Object[]{hd.getMaHD(), hd.getMaKH(),
                        hd.getMaNV(), hd.getNgayLapHD(),
                        formatter.format(hd.getDonGia()) + " VNĐ"});
                    }                
                } else {
                    tbForm = new ThongBaoForm(null, true);
                    tbForm.setMessage("Không tồn tại hóa đơn thuộc thời gian này");
                    tbForm.setVisible(true);
                }
            } else {
                    tbForm = new ThongBaoForm(null, true);
                    tbForm.setMessage("Vui lòng chọn ngày kết thúc lớn hơn ngày bắt đầu");
                    tbForm.setVisible(true);
            }
            
        }
    }
    public void viewInvoiceDetails() {
        int getRowCount = tblList.getRowCount();
        if(getRowCount > 0) {
            int index = tblList.getSelectedRow();
            if(index >= 0) {
                int getId = (int) tblList.getValueAt(index, 0);
                HoaDonCTForm view = new HoaDonCTForm(null, true);
                view.fillTable(getId);
                view.setVisible(true);
            }
        }
    }
    public void deleteInvoice() {
        int getRowCount = tblList.getRowCount();
        boolean success = true;
        if(getRowCount > 0) {
            int index[] = tblList.getSelectedRows();
            if(index.length > 0) {
                GhiChuForm note = new GhiChuForm(null, true);
                note.setVisible(true);
                String getNote = Auth.note;
                if(!getNote.equals("")) {
                    hdDAO = new HoaDonDAO();
                    for(int getIndex : index) {
                        int getId = (int) tblList.getValueAt(getIndex, 0);
                        int rowEffect = hdDAO.deleteInvoice(getId, getNote);  
                        if(rowEffect < 0) {
                            success = false;
                        }
                    }
                    if(success) {
                        ThongBaoForm tb = new ThongBaoForm(null, true);
                        tb.setMessage("Hủy hóa đơn thành công");
                        tb.setVisible(true);
                        fillTable();
                        Auth.note = "";
                    }
                }
               
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

        pnlGeneral = new javax.swing.JPanel();
        lblTitleInvoice = new javax.swing.JLabel();
        iconInvoice = new javax.swing.JLabel();
        titileShop = new javax.swing.JLabel();
        titlePlace = new javax.swing.JLabel();
        titleGmail = new javax.swing.JLabel();
        iconGmail = new javax.swing.JLabel();
        iconLocation = new javax.swing.JLabel();
        iconShop = new javax.swing.JLabel();
        sepInvoice = new javax.swing.JSeparator();
        dateFrom = new com.toedter.calendar.JDateChooser();
        lblDateFrom = new javax.swing.JLabel();
        lblDateEnd = new javax.swing.JLabel();
        dateEnd = new com.toedter.calendar.JDateChooser();
        sepDateFrom = new javax.swing.JSeparator();
        sepDateEnd = new javax.swing.JSeparator();
        lblSearch = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        lblDetail = new javax.swing.JLabel();
        lblCancelInvoice = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlGeneral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleInvoice.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblTitleInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleInvoice.setText("HÓA ĐƠN");
        pnlGeneral.add(lblTitleInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        iconInvoice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/invoice-title.png"))); // NOI18N
        pnlGeneral.add(iconInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 100, 70));

        titileShop.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titileShop.setForeground(new java.awt.Color(255, 255, 255));
        titileShop.setText("Money Heist Shoes Store");
        pnlGeneral.add(titileShop, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, 30));

        titlePlace.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titlePlace.setForeground(new java.awt.Color(255, 255, 255));
        titlePlace.setText("132 Lê Duẫn, TP. Đà Nẵng");
        pnlGeneral.add(titlePlace, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 53, 190, 40));

        titleGmail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titleGmail.setForeground(new java.awt.Color(255, 255, 255));
        titleGmail.setText("moneyheiststore@gmail.com");
        pnlGeneral.add(titleGmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, -1, -1));

        iconGmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/gmail.png"))); // NOI18N
        pnlGeneral.add(iconGmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 40, 40));

        iconLocation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/location.png"))); // NOI18N
        pnlGeneral.add(iconLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 47, 40, 50));

        iconShop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/shoes-shop.png"))); // NOI18N
        pnlGeneral.add(iconShop, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 40, 50));
        pnlGeneral.add(sepInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 140, 10));

        dateFrom.setBackground(new java.awt.Color(255, 255, 255));
        dateFrom.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlGeneral.add(dateFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 205, 40));

        lblDateFrom.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDateFrom.setForeground(new java.awt.Color(204, 51, 0));
        lblDateFrom.setText("Từ ngày: ");
        pnlGeneral.add(lblDateFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, 20));

        lblDateEnd.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDateEnd.setForeground(new java.awt.Color(204, 51, 0));
        lblDateEnd.setText("Đến ngày:");
        pnlGeneral.add(lblDateEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, -1, -1));

        dateEnd.setBackground(new java.awt.Color(255, 255, 255));
        dateEnd.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlGeneral.add(dateEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 170, 205, 40));
        pnlGeneral.add(sepDateFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 62, 10));
        pnlGeneral.add(sepDateEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 70, 10));

        lblSearch.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search-invoice.png"))); // NOI18N
        lblSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSearchMouseClicked(evt);
            }
        });
        pnlGeneral.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, 60, 40));
        pnlGeneral.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 540, 10));

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
        tblList.setRowHeight(30);
        tblList.setSelectionBackground(new java.awt.Color(1, 157, 176));
        jScrollPane1.setViewportView(tblList);

        pnlGeneral.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 670, 310));

        lblDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-detailInvoice.png"))); // NOI18N
        lblDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDetailMouseClicked(evt);
            }
        });
        pnlGeneral.add(lblDetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 560, -1, 60));

        lblCancelInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-deleteInvoice.png"))); // NOI18N
        lblCancelInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCancelInvoiceMouseClicked(evt);
            }
        });
        pnlGeneral.add(lblCancelInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 570, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 51, 0));
        jLabel1.setText("Thống kê hóa đơn?");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        pnlGeneral.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 580, -1, -1));
        pnlGeneral.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 600, 115, 10));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-invoice.jpg"))); // NOI18N
        pnlGeneral.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 630));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSearchMouseClicked
        fillTable();
    }//GEN-LAST:event_lblSearchMouseClicked

    private void lblDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDetailMouseClicked
        viewInvoiceDetails();
    }//GEN-LAST:event_lblDetailMouseClicked

    private void lblCancelInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelInvoiceMouseClicked
        deleteInvoice();
    }//GEN-LAST:event_lblCancelInvoiceMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        new ThongKeHDForm(null, true).setVisible(true);
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(HoaDonForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HoaDonForm dialog = new HoaDonForm(new javax.swing.JFrame(), true);
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
    private com.toedter.calendar.JDateChooser dateEnd;
    private com.toedter.calendar.JDateChooser dateFrom;
    private javax.swing.JLabel iconGmail;
    private javax.swing.JLabel iconInvoice;
    private javax.swing.JLabel iconLocation;
    private javax.swing.JLabel iconShop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblCancelInvoice;
    private javax.swing.JLabel lblDateEnd;
    private javax.swing.JLabel lblDateFrom;
    private javax.swing.JLabel lblDetail;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitleInvoice;
    private javax.swing.JPanel pnlGeneral;
    private javax.swing.JSeparator sepDateEnd;
    private javax.swing.JSeparator sepDateFrom;
    private javax.swing.JSeparator sepInvoice;
    private javax.swing.JTable tblList;
    private javax.swing.JLabel titileShop;
    private javax.swing.JLabel titleGmail;
    private javax.swing.JLabel titlePlace;
    // End of variables declaration//GEN-END:variables
}
