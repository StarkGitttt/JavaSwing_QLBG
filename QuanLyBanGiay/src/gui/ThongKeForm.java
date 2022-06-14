
package gui;

import dao.ThongKeDAO;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import utils.XDate;

/**
 *
 * @author DELL
 */
public class ThongKeForm extends javax.swing.JDialog {

    /**
     * Creates new form ThongKeForm
     */
    DefaultTableModel tblModel;
    List<Object[]> lstObj;
    List<Object[]> lstLineChart;
    DecimalFormat formatter;
    ThongKeDAO tkDAO;
    public ThongKeForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Thống kê doanh thu");
        setLocationRelativeTo(null);
        initColumn();
        setIcon();
        fillTable();
    }
    public void setIcon() {
        dateFrom.setDateFormatString("dd-MM-yyyy");
        dateEnd.setDateFormatString("dd-MM-yyyy");
        dateFrom.setIcon(new javax.swing.ImageIcon("src\\icons\\calendar-2.png"));
        dateEnd.setIcon(new javax.swing.ImageIcon("src\\icons\\calendar-2.png"));
    }
    public void initColumn() {
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setColumnIdentifiers(new Object[]{"Tổng hóa đơn", "Tổng sản phẩm bán ra"});
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 13));
        getDayMonthYear(XDate.toString(XDate.now(), "yyyy-MM-dd"));
    }
    public void showMessageForm(String message) {
        ThongBaoForm tb = new ThongBaoForm(null, true);
        tb.setMessage(message);
        tb.setVisible(true);
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
        tkDAO = new ThongKeDAO();
        lstObj = new ArrayList<>();
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setRowCount(0);
        formatter = new DecimalFormat("###,###,###");
        if(dateFrom.getDate() == null
                || dateEnd.getDate() == null) {
            lstObj = tkDAO.getStatisticsRevenue(null, null);
            if(lstObj.size() > 0) {
                for(Object[] row : lstObj ) {
                    tblModel.addRow(new Object[]{row[0], row[1]});
                    lblRevenue.setText(formatter.format(row[2]) + " VNĐ");
                }               
            }
        } else if (dateFrom.getDate() != null && dateEnd.getDate() != null){
            if(validateDiffDay()) {
                lstObj = tkDAO.getStatisticsRevenue(
                XDate.convertDate(XDate.toString(dateFrom.getDate(),"yyyy-MM-dd"), "yyyy-MM-dd"),
                XDate.convertDate(XDate.toString(dateEnd.getDate(),"yyyy-MM-dd"), "yyyy-MM-dd"));          
                if(!lstObj.isEmpty()) {
                    for(Object[] row : lstObj) {
                       tblModel.addRow(new Object[]{row[0], row[1]});                  
                       if(!(row[2] == null)) {
                           lblRevenue.setText(formatter.format(row[2]) + " VNĐ");                           
                       } else {
                           lblRevenue.setText("");
                           showMessageForm("Không có dữ liệu trong khoảng thời gian này");
                       }
                    }                
                } else {
                    lblRevenue.setText("");
                    showMessageForm("Không tồn tại hóa đơn thuộc thời gian này");
                }
            } else {
                    lblRevenue.setText("");
                    showMessageForm("Vui lòng chọn ngày kết thúc lớn hơn ngày bắt đầu");
            }
            
        }
        
    }
    public void showLineChart() {
        lstLineChart = new ArrayList<>();
        tkDAO = new ThongKeDAO();
        lstLineChart = tkDAO.getStatisticsLineChart(
                XDate.convertDate(XDate.toString(dateFrom.getDate(),"yyyy-MM-dd"), "yyyy-MM-dd"),
                XDate.convertDate(XDate.toString(dateEnd.getDate(),"yyyy-MM-dd"), "yyyy-MM-dd"));
        ThongKeBieuDo statisticsLC = new ThongKeBieuDo(null, true);
        statisticsLC.showLineChart(lstLineChart);
        statisticsLC.setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        lblDayOfW = new javax.swing.JLabel();
        lblDay = new javax.swing.JLabel();
        lblMonth = new javax.swing.JLabel();
        lblFirst = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateFrom = new com.toedter.calendar.JDateChooser();
        dateEnd = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblRevenue = new javax.swing.JLabel();
        lblLineChart = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

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
        tblList.setSelectionBackground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(tblList);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 570, 250));

        lblDayOfW.setFont(new java.awt.Font("Segoe UI Semilight", 0, 15)); // NOI18N
        lblDayOfW.setForeground(new java.awt.Color(255, 255, 255));
        lblDayOfW.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDayOfW.setText("SUNDAY");
        jPanel1.add(lblDayOfW, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 7, 90, -1));

        lblDay.setFont(new java.awt.Font("Tahoma", 0, 45)); // NOI18N
        lblDay.setForeground(new java.awt.Color(255, 255, 255));
        lblDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDay.setText("30");
        jPanel1.add(lblDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 25, 80, -1));

        lblMonth.setFont(new java.awt.Font("Segoe UI Semilight", 0, 15)); // NOI18N
        lblMonth.setForeground(new java.awt.Color(255, 255, 255));
        lblMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMonth.setText("DECEMBER");
        jPanel1.add(lblMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 80, -1));

        lblFirst.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFirst.setForeground(new java.awt.Color(255, 255, 255));
        lblFirst.setText("TH");
        jPanel1.add(lblFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 35, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("THỐNG KÊ DOANH THU");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 240, -1));

        dateFrom.setBackground(new java.awt.Color(255, 255, 255));
        dateFrom.setForeground(new java.awt.Color(204, 51, 0));
        dateFrom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dateFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateFromPropertyChange(evt);
            }
        });
        jPanel1.add(dateFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 150, 30));

        dateEnd.setBackground(new java.awt.Color(255, 255, 255));
        dateEnd.setForeground(new java.awt.Color(204, 51, 0));
        dateEnd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dateEnd.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateEndPropertyChange(evt);
            }
        });
        jPanel1.add(dateEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 150, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 51, 0));
        jLabel3.setText("Từ ngày:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 80, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 51, 0));
        jLabel4.setText("Đến ngày: ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 70, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("DOANH THU");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 440, 110, -1));

        lblRevenue.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblRevenue.setForeground(new java.awt.Color(255, 255, 255));
        lblRevenue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRevenue.setText("jLabel6");
        jPanel1.add(lblRevenue, new org.netbeans.lib.awtextra.AbsoluteConstraints(479, 466, 180, 20));

        lblLineChart.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblLineChart.setForeground(new java.awt.Color(204, 51, 0));
        lblLineChart.setText("Thống kê bằng biểu đồ?");
        lblLineChart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLineChartMouseClicked(evt);
            }
        });
        jPanel1.add(lblLineChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 480, 160, 10));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Shoes Store");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 14, 90, 60));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-statistics2.jpg"))); // NOI18N
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dateFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateFromPropertyChange
        fillTable();
    }//GEN-LAST:event_dateFromPropertyChange

    private void dateEndPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateEndPropertyChange
        fillTable();
    }//GEN-LAST:event_dateEndPropertyChange

    private void lblLineChartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLineChartMouseClicked
        if(!lblRevenue.getText().equals("")) {
            showLineChart();
        }
    }//GEN-LAST:event_lblLineChartMouseClicked

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
            java.util.logging.Logger.getLogger(ThongKeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThongKeForm dialog = new ThongKeForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDay;
    private javax.swing.JLabel lblDayOfW;
    private javax.swing.JLabel lblFirst;
    private javax.swing.JLabel lblLineChart;
    private javax.swing.JLabel lblMonth;
    private javax.swing.JLabel lblRevenue;
    private javax.swing.JTable tblList;
    // End of variables declaration//GEN-END:variables
}
