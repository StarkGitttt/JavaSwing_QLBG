/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.KhachHangDAO;
import java.awt.Color;
import java.awt.Font;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import modal.KhachHang;
import sun.security.x509.PKIXExtensions;
import utils.Auth;
import utils.XDate;

/**
 *
 * @author DELL
 */
public class Template extends javax.swing.JDialog {

    /**
     * Creates new form Template
     */
    DefaultTableModel tblModel;
    public Template(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Danh sách khách hàng");
        setLocationRelativeTo(null);
        initColum();
    }
    public void initColum() {
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setColumnIdentifiers(new Object[]{"Mã KH", "Họ tên", "Email", "SĐT", "Ngày sinh"});
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 13));
        getDayMonthYear(XDate.toString(XDate.now(), "yyyy-MM-dd"));
    }
    public void getDayMonthYear(String date) {
        // Get an instance of LocalTime
        // from date
        LocalDate currentDate = LocalDate.parse(date);
 
        // Get day from date
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        int day = currentDate.getDayOfMonth();
        // Get month from date
        Month month = currentDate.getMonth();
 
        // Get year from date
        int year = currentDate.getYear();
        // Set Label
        if(day == 1) {
            lblFirst.setText("ST");
        } else if(day == 2) {
            lblFirst.setText("ND");
        } else if(day == 3) {
            lblFirst.setText("RD");
        } else {
            lblFirst.setText("TH");
        }
        lblMonth.setText((""+month).toUpperCase());
        lblDayOfW.setText((""+dayOfWeek).toUpperCase());
        lblDay.setText(""+day);
    }
    public void fillTable(List<KhachHang> lstKH) {
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setRowCount(0);
        for(KhachHang kh : lstKH) {
            tblModel.addRow(new Object[]{kh.getMaKH(), kh.getHoTen(),
            kh.getEmail(), kh.getDienThoai(),
            XDate.toString(kh.getNgaySinh(), "dd-MM-yyyy")});
        }
    }
    public KhachHang getKhachHang() {
        KhachHang kh = new KhachHang();
        KhachHangDAO khDAO = new KhachHangDAO();
        int getRowCount = tblList.getRowCount();
        if(getRowCount > 0) {
            int index = tblList.getSelectedRow();
            if(index >= 0) {
                kh = khDAO.selectByID((String)tblList.getValueAt(index, 0));
                if(kh != null) {
                    Auth.kh = kh;
                }
                this.dispose();
            } else {
                kh = null;
            }
        }
        return kh;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        btnConfirm = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDayOfW = new javax.swing.JLabel();
        lblMonth = new javax.swing.JLabel();
        lblDay = new javax.swing.JLabel();
        lblFirst = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(null);

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
        tblList.setShowVerticalLines(false);
        tblList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblList);

        pnlContent.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 590, 200));

        btnConfirm.setBackground(new java.awt.Color(42, 42, 42));
        btnConfirm.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnConfirm.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirm.setText("XÁC NHẬN");
        btnConfirm.setBorder(null);
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });
        pnlContent.add(btnConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 400, 120, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Shoes Stores");
        pnlContent.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("DANH SÁCH KHÁCH HÀNG");
        pnlContent.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, 30));

        lblDayOfW.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblDayOfW.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDayOfW.setText("SUN DAY");
        pnlContent.add(lblDayOfW, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 110, -1));

        lblMonth.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        lblMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMonth.setText("DECEMBER");
        pnlContent.add(lblMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 110, -1));

        lblDay.setFont(new java.awt.Font("Tahoma", 0, 50)); // NOI18N
        lblDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDay.setText("30");
        pnlContent.add(lblDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 20, 70, -1));

        lblFirst.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFirst.setText("TH");
        pnlContent.add(lblFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 30, -1));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-inforCus.jpg"))); // NOI18N
        pnlContent.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        getKhachHang();
    }//GEN-LAST:event_btnConfirmActionPerformed

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
            java.util.logging.Logger.getLogger(Template.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Template.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Template.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Template.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Template dialog = new Template(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnConfirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDay;
    private javax.swing.JLabel lblDayOfW;
    private javax.swing.JLabel lblFirst;
    private javax.swing.JLabel lblMonth;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JTable tblList;
    // End of variables declaration//GEN-END:variables
}
