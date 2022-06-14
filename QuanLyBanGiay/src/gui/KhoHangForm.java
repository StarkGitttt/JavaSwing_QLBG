/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.KhachHangDAO;
import dao.KhoHangDAO;
import dao.NhaCCDAO;
import dao.SanPhamDAO;
import dao.ThongKeDAO;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modal.KhachHang;
import modal.KhoHang;
import modal.NhaCC;
import modal.SanPham;
import utils.Auth;
import utils.Validator;
import utils.XDate;

/**
 *
 * @author DELL
 */
public class KhoHangForm extends javax.swing.JDialog {
    
    DefaultComboBoxModel cbxModelSupplier, cbxModelProduct, cbxModelUnit;
    DefaultTableModel tblModelList;
    NhaCCDAO nhaCCDAO;
    SanPhamDAO spDAO;
    KhoHangDAO khDAO;
    NhaCC nhaCC;
    SanPham sp;
    KhoHang kh;
    List<SanPham> lstSP;
    List<NhaCC> lstNhaCC;
    List<KhoHang> lstKhoHang;
    List<Object[]> lstObj;
    public KhoHangForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Kho hàng");
        initErr();
        fillCbxSupplier();
        fillCbxProduct();
        fillCbxUnit();
        initColumn();
        fillTable();
        designTable();
    }
    public void designTable() {
         // Table List
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblList.getTableHeader().setForeground(new java.awt.Color(11,89,214));       
    }
    public void showMessage(String message) {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage(message);
            tbForm.setVisible(true);
    }
    public void initErr() {
        lblErrImport.setText(null);
        lblErrMinQuantity.setText(null);
        lblErrPrice.setText(null);
    }
    public void initTextFields() {
        txtImport.setBorder(new javax.swing.JTextField().getBorder());
        txtMinQuantity.setBorder(new javax.swing.JTextField().getBorder());
        txtPrice.setBorder(new javax.swing.JTextField().getBorder());
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(new javax.swing.JTextField().getBorder());
        label.setText(null);
    } 
    public void fillCbxSupplier() {
        cbxModelSupplier = (DefaultComboBoxModel) cbxSupplier.getModel();
        cbxModelSupplier.removeAllElements();
        lstNhaCC = new ArrayList<>();
        nhaCCDAO = new NhaCCDAO();
        lstNhaCC = nhaCCDAO.selectAll();
        if(!lstNhaCC.isEmpty()) {
            for(NhaCC nhaCC : lstNhaCC) {
                cbxModelSupplier.addElement(nhaCC);
            }
        }
    }
    public void fillCbxProduct() {
        cbxModelProduct = (DefaultComboBoxModel) cbxProduct.getModel();
        cbxModelProduct.removeAllElements();
        lstSP = new ArrayList<>();
        spDAO = new SanPhamDAO();
        lstSP = spDAO.selectAll();
        if(!lstSP.isEmpty()) {
            for(SanPham sp : lstSP) {
                cbxModelProduct.addElement(sp);
            }
        }
    }
    public void fillCbxUnit() {
        cbxModelUnit = (DefaultComboBoxModel) cbxUnit.getModel();
        cbxModelUnit.removeAllElements();
        cbxModelUnit.addElement("Đôi");
        cbxModelUnit.addElement("Chiếc");
        cbxModelUnit.addElement("Cái");
        cbxModelUnit.addElement("Cặp");
        cbxModelUnit.addElement("Đôi");
    }
    public void navShowAddGoods() {
            layered.removeAll();
            layered.add(pnlContentAdd);
            layered.repaint();
            layered.revalidate();
    }
    public void navShowList() {
            layered.removeAll();
            layered.add(pnlContentList);
            layered.repaint();
            layered.revalidate();
    }
    /* Validate */
    public boolean isImport() {
        return Validator.checkImport(txtImport, lblErrImport);
    }
    public boolean isPrice() {
        return Validator.checkPriceSupplier(txtPrice, lblErrPrice);
    }
    public boolean isMinQuantity() {
        return Validator.checkMinQuantity(txtMinQuantity, lblErrMinQuantity);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(isImport()) {
            isFormValid = false;
        }
        if(isPrice()) {
            isFormValid = false;
        }
        if(isMinQuantity()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public KhoHang getForm() {
        KhoHang kh = new KhoHang();
        // Lấy mã sản phẩm
        SanPham sp = (SanPham) cbxProduct.getSelectedItem();
        NhaCC nhaCC = (NhaCC) cbxSupplier.getSelectedItem();
        String dvt = (String) cbxUnit.getSelectedItem();
        // Lấy giá
        float getQuantity = Float.parseFloat(txtImport.getText());
        float getPrice = Float.parseFloat(txtPrice.getText());
        float getTotal = getQuantity * getPrice;
        if(sp != null && nhaCC != null && dvt != null) {
            kh.setMaSP(sp.getMaSP());
            kh.setMaNV(Auth.user.getMaNV());
            kh.setDVT(dvt);
            kh.setThucNhap((int) getQuantity);
            kh.setDonGia(getPrice);
            kh.setThanhTien(getTotal);
            kh.setNgayNhap(XDate.convertDate(XDate.toString(XDate.now(), "dd-MM-yyyy"), "dd-MM-yyyy"));
            kh.setMaNhaCC(nhaCC.getMaNhaCC());
            kh.setSlToiThieu(Integer.parseInt(txtMinQuantity.getText()));            
        }
        return kh;
    }
    public void setForm(KhoHang kh) {
        fillCbxSupplier();
        fillCbxUnit();
        fillCbxUnit();
        txtImport.setText(kh.getThucNhap()+"");
        txtPrice.setText(kh.getDonGia()+"");
        txtMinQuantity.setText(kh.getSlToiThieu()+"");
        initErr();
        initTextFields();
    }
    public void initColumn() {
        tblModelList = (DefaultTableModel) tblList.getModel();
        tblModelList.setColumnIdentifiers(new Object[]{"TT", "TenSP","ThucNhap",
            "DonGia","ThanhTien",
            "NgayNhap", "MaNhaCC"});
    }
    public void fillTable() {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        tblModelList = (DefaultTableModel) tblList.getModel();
        tblModelList.setRowCount(0);
        lstObj = new ArrayList<>();
        khDAO = new KhoHangDAO();
        lstObj = khDAO.getWareHouse(txtSearch.getText());
        if(!lstObj.isEmpty()) {
            for(Object[] row : lstObj) {
                tblModelList.addRow(new Object[]{row[0], row[1], row[2],
                formatter.format(row[3]) + " VNĐ", formatter.format(row[4]) + " VNĐ",
                row[5], row[6]});
            }
        }
    }
    public void insert() {
        khDAO = new KhoHangDAO();
        int rowEffect = khDAO.insert(this.getForm());
        if(rowEffect > 0) {
            showMessage("Thêm thành công");
            KhoHang kh = new KhoHang();
            this.setForm(kh);
        }
    }
    public void viewDetail() {
        int count = tblList.getRowCount();
        if(count > 0 ) {
            int index = tblList.getSelectedRow();
            if(index >= 0) {
               int id = (int) tblList.getValueAt(index, 0);
               KhoHang kh = new KhoHang();
               khDAO = new KhoHangDAO();
               kh = khDAO.selectByID(id);
               CapNhapKhoHang cnkh = new CapNhapKhoHang(null, true);
               cnkh.setForm(kh);
               cnkh.setVisible(true);
               fillTable();
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
        lbltitleAdd = new javax.swing.JLabel();
        lblIconAdd = new javax.swing.JLabel();
        lblAdd = new javax.swing.JLabel();
        pnlTop = new javax.swing.JPanel();
        lblIconList = new javax.swing.JLabel();
        lblTitleList = new javax.swing.JLabel();
        lblList = new javax.swing.JLabel();
        nav = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        layered = new javax.swing.JLayeredPane();
        pnlContentAdd = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbxSupplier = new javax.swing.JComboBox<String>();
        cbxProduct = new javax.swing.JComboBox<String>();
        txtImport = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtMinQuantity = new javax.swing.JTextField();
        lblErrImport = new javax.swing.JLabel();
        lblErrPrice = new javax.swing.JLabel();
        lblErrMinQuantity = new javax.swing.JLabel();
        cbxUnit = new javax.swing.JComboBox<String>();
        lblNew = new javax.swing.JLabel();
        lblInsert = new javax.swing.JLabel();
        pnlContentList = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        lblSearch = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnUpdate = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("KHO HÀNG");
        jPanel1.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 120, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 140, 10));

        pnlAdd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbltitleAdd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbltitleAdd.setForeground(new java.awt.Color(255, 255, 255));
        lbltitleAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltitleAdd.setText("CẬP NHẬP");
        lbltitleAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lbltitleAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 80, 30));

        lblIconAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ware-house.png"))); // NOI18N
        lblIconAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lblIconAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 49));

        lblAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lblAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 160, 50));

        pnlTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/wareHouse-List.png"))); // NOI18N
        lblIconList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblListMouseClicked(evt);
            }
        });
        pnlTop.add(lblIconList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblTitleList.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitleList.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleList.setText("DANH SÁCH");
        lblTitleList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblListMouseClicked(evt);
            }
        });
        pnlTop.add(lblTitleList, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 20, 90, -1));

        lblList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblListMouseClicked(evt);
            }
        });
        pnlTop.add(lblList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 160, 50));

        nav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/left-nav.png"))); // NOI18N
        jPanel1.add(nav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 440));

        layered.setLayout(new java.awt.CardLayout());

        pnlContentAdd.setBackground(new java.awt.Color(255, 255, 255));
        pnlContentAdd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Số lượng tối thiểu");
        pnlContentAdd.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Thực nhập");
        pnlContentAdd.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Đơn vị tính");
        pnlContentAdd.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Tên sản phẩm");
        pnlContentAdd.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("Tên nhà cung cấp");
        pnlContentAdd.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setText("Đơn giá");
        pnlContentAdd.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, -1, 20));

        cbxSupplier.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlContentAdd.add(cbxSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 510, 40));

        cbxProduct.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlContentAdd.add(cbxProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 510, 40));

        txtImport.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtImport.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtImportFocusLost(evt);
            }
        });
        txtImport.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtImportKeyPressed(evt);
            }
        });
        pnlContentAdd.add(txtImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 230, 40));

        txtPrice.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
        pnlContentAdd.add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 240, 40));

        txtMinQuantity.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
        pnlContentAdd.add(txtMinQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 230, 40));

        lblErrImport.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrImport.setText("Error Message");
        pnlContentAdd.add(lblErrImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 230, -1));

        lblErrPrice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPrice.setText("Error Message");
        pnlContentAdd.add(lblErrPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 240, -1));

        lblErrMinQuantity.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrMinQuantity.setText("Error Message");
        pnlContentAdd.add(lblErrMinQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 230, -1));

        cbxUnit.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlContentAdd.add(cbxUnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 240, 40));

        lblNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-new3.png"))); // NOI18N
        lblNew.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNewMouseClicked(evt);
            }
        });
        pnlContentAdd.add(lblNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 378, 120, 50));

        lblInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-add3.png"))); // NOI18N
        lblInsert.setToolTipText("");
        lblInsert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInsertMouseClicked(evt);
            }
        });
        pnlContentAdd.add(lblInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, -1, -1));

        layered.add(pnlContentAdd, "card3");

        pnlContentList.setBackground(new java.awt.Color(255, 255, 255));
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

    private void lblAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddMouseClicked
        navShowAddGoods();
    }//GEN-LAST:event_lblAddMouseClicked

    private void lblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblListMouseClicked
       navShowList();
    }//GEN-LAST:event_lblListMouseClicked

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        fillTable();
    }//GEN-LAST:event_txtSearchKeyTyped

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        viewDetail();
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void lblInsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInsertMouseClicked
        if(validateAll()) {
            insert();
        }
    }//GEN-LAST:event_lblInsertMouseClicked

    private void lblNewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNewMouseClicked
        KhoHang kh = new KhoHang();
        this.setForm(kh);
    }//GEN-LAST:event_lblNewMouseClicked

    private void txtImportFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtImportFocusLost
        isImport();
    }//GEN-LAST:event_txtImportFocusLost

    private void txtPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusLost
        isPrice();
    }//GEN-LAST:event_txtPriceFocusLost

    private void txtMinQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinQuantityFocusLost
        isMinQuantity();
    }//GEN-LAST:event_txtMinQuantityFocusLost

    private void txtImportKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImportKeyPressed
        setNullErr(txtImport, lblErrImport);
    }//GEN-LAST:event_txtImportKeyPressed

    private void txtPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyPressed
        setNullErr(txtPrice, lblErrPrice);
    }//GEN-LAST:event_txtPriceKeyPressed

    private void txtMinQuantityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMinQuantityKeyPressed
        setNullErr(txtMinQuantity, lblErrMinQuantity);
    }//GEN-LAST:event_txtMinQuantityKeyPressed

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
            java.util.logging.Logger.getLogger(KhoHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhoHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhoHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhoHangForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KhoHangForm dialog = new KhoHangForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JComboBox<String> cbxProduct;
    private javax.swing.JComboBox<String> cbxSupplier;
    private javax.swing.JComboBox<String> cbxUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLayeredPane layered;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblErrImport;
    private javax.swing.JLabel lblErrMinQuantity;
    private javax.swing.JLabel lblErrPrice;
    private javax.swing.JLabel lblIconAdd;
    private javax.swing.JLabel lblIconList;
    private javax.swing.JLabel lblInsert;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblNew;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleList;
    private javax.swing.JLabel lbltitleAdd;
    private javax.swing.JLabel nav;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlContentAdd;
    private javax.swing.JPanel pnlContentList;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtImport;
    private javax.swing.JTextField txtMinQuantity;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
