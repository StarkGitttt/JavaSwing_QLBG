/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.KhachHangDAO;
import dao.ThongKeDAO;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modal.KhachHang;
import utils.XDate;

/**
 *
 * @author DELL
 */
public class KhachHangForm extends javax.swing.JDialog {
    DefaultTableModel tblModelList, tblModelTop, tblModelBirth;
    DefaultComboBoxModel cbxModel, cbxModelBirth;
    List<KhachHang> lstKH, lstTop, lstBirth;
    KhachHangDAO khDAO;
    ThongKeDAO tkDAO;
    /**
     * Creates new form KhangHangForm
     */
    public KhachHangForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Khách hàng");
        designTable();
        initColumnIdent();
        fillTableList();
        fillCbxTopRevenue();
        fillCbxBirth();
        fillTableTop();
        fillTableBirth();
    }
    public void designTable() {
         // Table List
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblList.getTableHeader().setForeground(new java.awt.Color(11,89,214));
        // Table Top
        tblTop.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblTop.getTableHeader().setForeground(new java.awt.Color(11,89,214));
        // Table Birhth
        tblBirth.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblBirth.getTableHeader().setForeground(new java.awt.Color(11,89,214));
    }
    public void fillCbxTopRevenue() {
        cbxModel = (DefaultComboBoxModel) cbxFilterPercent.getModel();
        cbxModel.removeAllElements();
        String percent[] = {"TOP 10", "TOP 50", "TOP 100", "TOP 150", "TOP 200", "Top 300"};
        for(String getPercent : percent) {
            cbxModel.addElement(getPercent);
        }
    }
    public void fillCbxBirth() {
        cbxModelBirth = (DefaultComboBoxModel) cbxFilterBirth.getModel();
        cbxModelBirth.removeAllElements();
        String day[] = {"Hôm nay", "7 Ngày", "14 Ngày", "30 Ngày"};
        for(String getDay : day) {
            cbxModelBirth.addElement(getDay);
        }
    }
    public void initColumnIdent() {
        // Table List
        tblModelList = (DefaultTableModel) tblList.getModel();
        tblModelList.setColumnIdentifiers(new Object[]{"STT", "Mã KH", "Họ tên",
        "Ngày sinh", "Giới tính", "Email", "SĐT"});
        // Table Top
        tblModelTop = (DefaultTableModel) tblTop.getModel();
        tblModelTop.setColumnIdentifiers(new Object[]{"STT", "Mã KH", "Họ tên",
        "Số sản phẩm", "Doanh thu"});
        // Table Birth
        tblModelBirth = (DefaultTableModel) tblBirth.getModel();
        tblModelBirth.setColumnIdentifiers(new Object[]{"STT", "Mã KH", "Họ tên",
        "Ngày sinh", "Còn"});
    }
    public void navShowList() {
            layered.removeAll();
            layered.add(pnlContentList);
            layered.repaint();
            layered.revalidate();
    }
    public void navShowTop() {
            layered.removeAll();
            layered.add(pnlContentTop);
            layered.repaint();
            layered.revalidate();
    }
    public void navShowBirth() {
            layered.removeAll();
            layered.add(pnlContentBirth);
            layered.repaint();
            layered.revalidate();
    }
    public KhachHang updateInfor() {
        KhachHang kh = new KhachHang();
        int count = tblList.getRowCount();
        if(count > 0 ) {
            int index = tblList.getSelectedRow();
            if(index >= 0) {
                String getID = (String) tblList.getValueAt(index, 1);
                khDAO = new KhachHangDAO();
                kh = khDAO.selectByID(getID);
                CapNhapKhachHang updateKH =  new CapNhapKhachHang(null, true);
                updateKH.setForm(kh);
                updateKH.setVisible(true);
                fillTableList();
                fillTableBirth();
            }
        }
        return kh;
    }
    public void fillTableList() {
        tblModelList = (DefaultTableModel) tblList.getModel();
        tblModelList.setRowCount(0);
        khDAO = new KhachHangDAO();
        lstKH = new ArrayList<>();
        lstKH = khDAO.selectByName(txtSearch.getText());
        if(lstKH != null) {
            for(int i = 0; i < lstKH.size(); i++) {
                KhachHang kh = lstKH.get(i);
                tblModelList.addRow(new Object[]{i+1, kh.getMaKH(), kh.getHoTen(),
                XDate.toString(kh.getNgaySinh(), "dd-MM-yyyy"),
                kh.isGioiTinh() ? "Nam" : " Nữ", kh.getEmail(), kh.getDienThoai()});
            }
        } else {
            // TODO CODE
        }
    }
    public void fillTableTop() {
        tkDAO = new ThongKeDAO();
        tblModelTop = (DefaultTableModel) tblTop.getModel();
        tblModelTop.setRowCount(0);
        String percent = (String) cbxFilterPercent.getSelectedItem();
        String cutPercent = percent.substring(percent.lastIndexOf(" ")).trim();
        int parsePercent = Integer.parseInt(cutPercent);
        List<Object[]> lstObj = tkDAO.getRevenueCustomer(txtSearchTop.getText(), parsePercent);
        if(lstObj.size() > 0) {
            DecimalFormat formatter;
            formatter = new DecimalFormat("###,###,###");
            for(int i = 0; i < lstObj.size(); i++) {
                 Object[] row = lstObj.get(i);
                    tblModelTop.addRow(new Object[]{i+1, row[0], row[1],
                     row[6], formatter.format(row[7]) + " VNĐ"});
            }           
        } else {
            // TODO CODE
        }
    }
    public void fillTableBirth() {
        tkDAO = new ThongKeDAO();
        tblModelBirth = (DefaultTableModel) tblBirth.getModel();
        tblModelBirth.setRowCount(0);
        String getValue = (String) cbxFilterBirth.getSelectedItem();
        if(getValue.equalsIgnoreCase("Hôm nay")) {
            List<Object[]> lstObj = tkDAO.getStatisticsBirth(txtSearchBirth.getText(), 0);
            if(lstObj.size() > 0) {
                for(int i = 0; i < lstObj.size(); i++) {
                   Object[]row = lstObj.get(i);
                   tblModelBirth.addRow(new Object[]{i+1, row[0], row[1],
                   row[2], row[3]});
                }
            }
        } else {
            String cutValue = getValue.substring(0, getValue.lastIndexOf(" ")).trim();
            int parseValue = Integer.parseInt(cutValue);
            List<Object[]> lstObj = tkDAO.getStatisticsBirth(txtSearchBirth.getText(), parseValue);
            if(lstObj.size() > 0) {
                for(int i = 0; i < lstObj.size(); i++) {
                   Object[]row = lstObj.get(i);
                   tblModelBirth.addRow(new Object[]{i+1, row[0], row[1],
                   row[2], row[3]});
                }
            }
        }
    }
    public void viewDetail() {
        int count = tblTop.getRowCount();
        if(count > 0 ) {
            int index = tblTop.getSelectedRow();
            if(index >= 0) {
                String getID = (String) tblTop.getValueAt(index, 1);
               ChiTietKhachHang ctKH =  new ChiTietKhachHang(null, true);
               ctKH.fillTable(getID);
               ctKH.setVisible(true);                
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnlList = new javax.swing.JPanel();
        lblTitleList = new javax.swing.JLabel();
        lblIconList = new javax.swing.JLabel();
        lblList = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlTop = new javax.swing.JPanel();
        lblFamiliar = new javax.swing.JLabel();
        lblTitleFamiliar = new javax.swing.JLabel();
        lblTop = new javax.swing.JLabel();
        pnlStatistics = new javax.swing.JPanel();
        lblIconBirth = new javax.swing.JLabel();
        lblBirth = new javax.swing.JLabel();
        lblStatictisc = new javax.swing.JLabel();
        nav = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        layered = new javax.swing.JLayeredPane();
        pnlContentList = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        lblSearch = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnUpdate = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlContentTop = new javax.swing.JPanel();
        lblSearchTop = new javax.swing.JLabel();
        txtSearchTop = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTop = new javax.swing.JTable();
        cbxFilterPercent = new javax.swing.JComboBox();
        btnDetails = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlContentBirth = new javax.swing.JPanel();
        lblSearchBirth = new javax.swing.JLabel();
        txtSearchBirth = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBirth = new javax.swing.JTable();
        cbxFilterBirth = new javax.swing.JComboBox<String>();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("KHÁCH HÀNG");
        jPanel1.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 120, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 140, 10));

        pnlList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleList.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitleList.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleList.setText("DANH SÁCH");
        lblTitleList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblListMouseClicked(evt);
            }
        });
        pnlList.add(lblTitleList, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 80, 30));

        lblIconList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list-customer.png"))); // NOI18N
        lblIconList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblListMouseClicked(evt);
            }
        });
        pnlList.add(lblIconList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblListMouseClicked(evt);
            }
        });
        pnlList.add(lblList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jLabel3.setText("jLabel3");
        pnlList.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.add(pnlList, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 160, 50));

        pnlTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFamiliar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/top-customer.png"))); // NOI18N
        lblFamiliar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopMouseClicked(evt);
            }
        });
        pnlTop.add(lblFamiliar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblTitleFamiliar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitleFamiliar.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleFamiliar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleFamiliar.setText("KHÁCH QUEN");
        lblTitleFamiliar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopMouseClicked(evt);
            }
        });
        pnlTop.add(lblTitleFamiliar, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 20, 90, -1));

        lblTop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTopMouseClicked(evt);
            }
        });
        pnlTop.add(lblTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 160, 50));

        pnlStatistics.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconBirth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/birth-customer.png"))); // NOI18N
        lblIconBirth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStatictiscMouseClicked(evt);
            }
        });
        pnlStatistics.add(lblIconBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblBirth.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblBirth.setForeground(new java.awt.Color(255, 255, 255));
        lblBirth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBirth.setText("SINH NHẬT");
        lblBirth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStatictiscMouseClicked(evt);
            }
        });
        pnlStatistics.add(lblBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 14, 80, 20));

        lblStatictisc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblStatictisc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStatictiscMouseClicked(evt);
            }
        });
        pnlStatistics.add(lblStatictisc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlStatistics, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 160, 50));

        nav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/left-nav.png"))); // NOI18N
        jPanel1.add(nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 440));

        layered.setLayout(new java.awt.CardLayout());

        pnlContentList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });
        pnlContentList.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 250, 40));

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

        pnlContentList.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 580, 280));

        lblSearch.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(204, 0, 0));
        lblSearch.setText("Tìm kiếm");
        pnlContentList.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));
        pnlContentList.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 70, 10));

        btnUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-updateCustomer.png"))); // NOI18N
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });
        pnlContentList.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, 160, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-product.jpg"))); // NOI18N
        pnlContentList.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 440));

        layered.add(pnlContentList, "card2");

        pnlContentTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSearchTop.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSearchTop.setForeground(new java.awt.Color(204, 51, 0));
        lblSearchTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchTop.setText("Tìm kiếm");
        pnlContentTop.add(lblSearchTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 80, -1));

        txtSearchTop.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSearchTop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchTopKeyTyped(evt);
            }
        });
        pnlContentTop.add(txtSearchTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 190, 40));
        pnlContentTop.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 80, 10));

        tblTop.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblTop.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTop.setRowHeight(30);
        tblTop.setSelectionBackground(new java.awt.Color(1, 157, 176));
        jScrollPane2.setViewportView(tblTop);

        pnlContentTop.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 580, 270));

        cbxFilterPercent.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbxFilterPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFilterPercentActionPerformed(evt);
            }
        });
        pnlContentTop.add(cbxFilterPercent, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 180, 40));

        btnDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-detail.png"))); // NOI18N
        btnDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDetailsMouseClicked(evt);
            }
        });
        pnlContentTop.add(btnDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 371, 190, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-product.jpg"))); // NOI18N
        pnlContentTop.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 440));

        layered.add(pnlContentTop, "card3");

        pnlContentBirth.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSearchBirth.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSearchBirth.setForeground(new java.awt.Color(204, 51, 0));
        lblSearchBirth.setText("Tìm kiếm");
        pnlContentBirth.add(lblSearchBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 80, -1));

        txtSearchBirth.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSearchBirth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchBirthKeyTyped(evt);
            }
        });
        pnlContentBirth.add(txtSearchBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 200, 40));
        pnlContentBirth.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 90, 20));

        tblBirth.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblBirth.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBirth.setRowHeight(30);
        jScrollPane3.setViewportView(tblBirth);

        pnlContentBirth.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 580, 280));

        cbxFilterBirth.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbxFilterBirth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFilterBirthActionPerformed(evt);
            }
        });
        pnlContentBirth.add(cbxFilterBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 190, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-product.jpg"))); // NOI18N
        pnlContentBirth.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 440));

        layered.add(pnlContentBirth, "card4");

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

        jPanel1.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 600, 440));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblListMouseClicked
        navShowList();
    }//GEN-LAST:event_lblListMouseClicked

    private void lblTopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTopMouseClicked
        navShowTop();
    }//GEN-LAST:event_lblTopMouseClicked

    private void lblStatictiscMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStatictiscMouseClicked
        navShowBirth();
    }//GEN-LAST:event_lblStatictiscMouseClicked

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        fillTableList();
    }//GEN-LAST:event_txtSearchKeyTyped

    private void cbxFilterPercentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterPercentActionPerformed
        fillTableTop();
    }//GEN-LAST:event_cbxFilterPercentActionPerformed

    private void txtSearchTopKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTopKeyTyped
        fillTableTop();
    }//GEN-LAST:event_txtSearchTopKeyTyped

    private void txtSearchBirthKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchBirthKeyTyped
        fillTableBirth();
    }//GEN-LAST:event_txtSearchBirthKeyTyped

    private void cbxFilterBirthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterBirthActionPerformed
        fillTableBirth();
    }//GEN-LAST:event_cbxFilterBirthActionPerformed

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
       updateInfor();
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDetailsMouseClicked
       viewDetail();
    }//GEN-LAST:event_btnDetailsMouseClicked

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
            java.util.logging.Logger.getLogger(KhachHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhachHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhachHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhachHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KhachHangForm dialog = new KhachHangForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnDetails;
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JComboBox<String> cbxFilterBirth;
    private javax.swing.JComboBox cbxFilterPercent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLayeredPane layered;
    private javax.swing.JLabel lblBirth;
    private javax.swing.JLabel lblFamiliar;
    private javax.swing.JLabel lblIconBirth;
    private javax.swing.JLabel lblIconList;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblSearchBirth;
    private javax.swing.JLabel lblSearchTop;
    private javax.swing.JLabel lblStatictisc;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleFamiliar;
    private javax.swing.JLabel lblTitleList;
    private javax.swing.JLabel lblTop;
    private javax.swing.JLabel nav;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlContentBirth;
    private javax.swing.JPanel pnlContentList;
    private javax.swing.JPanel pnlContentTop;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlStatistics;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JTable tblBirth;
    private javax.swing.JTable tblList;
    private javax.swing.JTable tblTop;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchBirth;
    private javax.swing.JTextField txtSearchTop;
    // End of variables declaration//GEN-END:variables
}
