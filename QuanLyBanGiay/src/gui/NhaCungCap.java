/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.NhaCCDAO;
import dao.ThongKeDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modal.NhaCC;
import utils.MsgBox;
import utils.Validator;

/**
 *
 * @author DELL
 */
public class NhaCungCap extends javax.swing.JDialog {
    DefaultTableModel tblModelList, tblModelFamiliar;
    /**
     * Creates new form KhangHangForm
     */
    NhaCungCap nhaCC;
    NhaCCDAO nhaCCDAO;
    ThongKeDAO tkDAO;
    List<NhaCC> lstNhaCC; 
    public NhaCungCap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Nhà cung cấp");
        designTable();
        initColumn();
        fillTalble();
        fillTableFimiliar();
        initErr();
    }
    public void initErr() {
        lblErrName.setText(null);
        lblErrPhone.setText(null);
        lblErrEmail.setText(null);
        lblErrAddress.setText(null);
        txtName.setBorder(new JTextField().getBorder());
        txtPhone.setBorder(new JTextField().getBorder());
        txtEmail.setBorder(new JTextField().getBorder());
        txtAddress.setBorder(new JTextField().getBorder());
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(new javax.swing.JTextField().getBorder());
        label.setText(null);
    } 
    public void showMessage(String message) {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage(message);
            tbForm.setVisible(true);
    }
    /* Validate */
    public boolean validateSupplier() {
        return Validator.isEmptySupplier(txtName, lblErrName);
    }
    public boolean validatePhone() {
        return Validator.isPhone(txtPhone, lblErrPhone);
    }
    public boolean validateEmail() {
        return Validator.isEmail(txtEmail, lblErrEmail);
    }
    public boolean validateAddress() {
        return Validator.isAddress(txtAddress, lblErrAddress);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(validateSupplier()) {
            isFormValid = false;
        }
        if(validatePhone()) {
            isFormValid = false;
        }
        if(validateEmail()) {
            isFormValid = false;
        }
        if(validateAddress()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public void navShowAdd() {
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
    public void navShowFamiliar() {
        layered.removeAll();
        layered.add(pnlContentFamiliar);
        layered.repaint();
        layered.revalidate();
    }
    public void designTable() {
         // Table List
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblList.getTableHeader().setForeground(new java.awt.Color(11,89,214));
        // Table Familiar Supplier
        tblSimilarSup.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblSimilarSup.getTableHeader().setForeground(new java.awt.Color(11,89,214));
    }
    public void initColumn() {
        tblModelList = (DefaultTableModel) tblList.getModel();
        tblModelList.setColumnIdentifiers(new Object[]{"Mã nhà CC", "Tên Nhà CC",
            "SĐT", "Email","Địa chỉ", "Ghi chú"});
        // Table Familiar Supplier
        tblModelFamiliar = (DefaultTableModel) tblSimilarSup.getModel();
        tblModelFamiliar.setColumnIdentifiers(new Object[]{"Tên nhà cung cấp", "Số lần đặt hàng"});
    }
    public void fillTalble() {
        tblModelList = (DefaultTableModel) tblList.getModel();
        tblModelList.setRowCount(0);
        lstNhaCC = new ArrayList<>();
        nhaCCDAO = new NhaCCDAO();
        lstNhaCC = nhaCCDAO.selectByName(txtSearch.getText());
        if(lstNhaCC != null) {
            for(NhaCC nhaCC : lstNhaCC) {
                 tblModelList.addRow(new Object[]{nhaCC.getMaNhaCC(), nhaCC.getTenNhaCC(),
                 nhaCC.getDienThoai(), nhaCC.getEmail(), nhaCC.getDiaChi(), nhaCC.getGhiChu()});
            }          
        } else {
//            TODO CODE
        }
    }
    public void fillTableFimiliar() {
        tkDAO = new ThongKeDAO();
        List<Object[]> lstFamiliar = tkDAO.getStatisticsFamiliar();
        if(lstFamiliar != null) {
            tblModelFamiliar = (DefaultTableModel) tblSimilarSup.getModel();
            tblModelFamiliar.setRowCount(0);
            for(Object[] row : lstFamiliar) {
                tblModelFamiliar.addRow(row);
            }
        }
    }
    public NhaCC getForm() {
        NhaCC nhaCC = new NhaCC();
        nhaCC.setTenNhaCC(txtName.getText());
        nhaCC.setDienThoai(txtPhone.getText());
        nhaCC.setEmail(txtEmail.getText());
        nhaCC.setDiaChi(txtAddress.getText());
        nhaCC.setGhiChu(txtNote.getText());
        return nhaCC;
    }
    public void setForm(NhaCC nhaCC) {
        txtName.setText(nhaCC.getTenNhaCC());
        txtPhone.setText(nhaCC.getDienThoai());
        txtEmail.setText(nhaCC.getEmail());
        txtAddress.setText(nhaCC.getDiaChi());
        txtNote.setText(nhaCC.getGhiChu());
    }
    public void clear() {
        NhaCC nhaCC = new NhaCC();
        this.setForm(nhaCC);
        initErr();
    }
    public void add() {
        nhaCCDAO = new NhaCCDAO();
        int rowEffect = nhaCCDAO.insert(this.getForm());
        if(rowEffect > 0) {
            fillTalble();
            showMessage("Thêm nhà cung cấp thành công");
            clear();
        } else {
            showMessage("Thêm nhà cung cấp thất bại");
        }
    }
    public void update() {
        nhaCCDAO = new NhaCCDAO();
        int rowCount = tblList.getRowCount();
        if(rowCount > 0) {
            int index = tblList.getSelectedRow();
            if(index >= 0) {
                int getId = (int) tblList.getValueAt(index, 0);
                NhaCC nhaCC = nhaCCDAO.selectByID(getId);
                ChiTietNhaCC ctNhaCC = new ChiTietNhaCC(null, true);
                ctNhaCC.setForm(nhaCC);
                ctNhaCC.setVisible(true);   
                this.fillTalble();
                  
            }
        }
    }
    public void delete() {
        if(MsgBox.confirm(this, "Bạn có muốn xóa những nhà cung cấp này")) {
            int rowCount = tblList.getRowCount();
            int rowEffect = 0;
            if(rowCount > 0) {
                int[] arrIndex = tblList.getSelectedRows();
                if(arrIndex.length > 0) {
                    nhaCCDAO = new NhaCCDAO();
                    for(int index : arrIndex) {
                        int getIndex = (int) tblList.getValueAt(index, 0);
                        int effect = nhaCCDAO.delete(getIndex);
                        if(effect > 0) {
                            ++rowEffect;
                        }
                    }
                    if(arrIndex.length == rowEffect) {
                        showMessage("Xóa thành công");
                    }
                }
                this.fillTalble();
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
        lblTitleAdd = new javax.swing.JLabel();
        lblIconAdd = new javax.swing.JLabel();
        lblAdd = new javax.swing.JLabel();
        pnlList = new javax.swing.JPanel();
        lblIconList = new javax.swing.JLabel();
        lblTitleList = new javax.swing.JLabel();
        lblList = new javax.swing.JLabel();
        pnlFamiliarSup = new javax.swing.JPanel();
        lblIconFamiliarSup = new javax.swing.JLabel();
        lblTitleFamiliarSup = new javax.swing.JLabel();
        lblFamiliarSup = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        layered = new javax.swing.JLayeredPane();
        pnlContentAdd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        lblErrPhone = new javax.swing.JLabel();
        lblErrName = new javax.swing.JLabel();
        lblErrEmail = new javax.swing.JLabel();
        lblErrAddress = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        btnAdd = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        pnlContentList = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        lblSearch = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnUpdate = new javax.swing.JLabel();
        btnDelete = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlContentFamiliar = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSimilarSup = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nav = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("NHÀ CUNG CẤP");
        jPanel1.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 120, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 140, 10));

        pnlAdd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleAdd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitleAdd.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleAdd.setText("CẬP NHẬP");
        lblTitleAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lblTitleAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 70, 30));

        lblIconAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list-customer.png"))); // NOI18N
        lblIconAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lblIconAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lblAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 160, 50));

        pnlList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/top-customer.png"))); // NOI18N
        lblIconList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleListMouseClicked(evt);
            }
        });
        pnlList.add(lblIconList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblTitleList.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTitleList.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleList.setText("DANH SÁCH");
        lblTitleList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleListMouseClicked(evt);
            }
        });
        pnlList.add(lblTitleList, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 20, 80, -1));

        lblList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleListMouseClicked(evt);
            }
        });
        pnlList.add(lblList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlList, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 160, 50));

        pnlFamiliarSup.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIconFamiliarSup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/top-customer.png"))); // NOI18N
        lblIconFamiliarSup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleFamiliarSupMouseClicked(evt);
            }
        });
        pnlFamiliarSup.add(lblIconFamiliarSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        lblTitleFamiliarSup.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTitleFamiliarSup.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleFamiliarSup.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleFamiliarSup.setText("TIN DÙNG");
        lblTitleFamiliarSup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleFamiliarSupMouseClicked(evt);
            }
        });
        pnlFamiliarSup.add(lblTitleFamiliarSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 20, 80, -1));

        lblFamiliarSup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblFamiliarSup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTitleFamiliarSupMouseClicked(evt);
            }
        });
        pnlFamiliarSup.add(lblFamiliarSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        jPanel1.add(pnlFamiliarSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 160, 50));

        layered.setLayout(new java.awt.CardLayout());

        pnlContentAdd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tên nhà cung cấp");
        pnlContentAdd.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Số điện thoại");
        pnlContentAdd.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 100, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Email");
        pnlContentAdd.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 40, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Địa chỉ");
        pnlContentAdd.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 50, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Ghi chú");
        pnlContentAdd.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, 60, -1));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });
        pnlContentAdd.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 200, 220, 30));

        txtAddress.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAddressFocusLost(evt);
            }
        });
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAddressKeyPressed(evt);
            }
        });
        pnlContentAdd.add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 280, 220, 30));

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });
        pnlContentAdd.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 220, 30));

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPhoneFocusLost(evt);
            }
        });
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneKeyPressed(evt);
            }
        });
        pnlContentAdd.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, 220, 30));

        lblErrPhone.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPhone.setText("Error message");
        pnlContentAdd.add(lblErrPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, 200, -1));

        lblErrName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrName.setText("Error message");
        pnlContentAdd.add(lblErrName, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 200, -1));

        lblErrEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrEmail.setText("Error message");
        pnlContentAdd.add(lblErrEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 230, 200, -1));

        lblErrAddress.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrAddress.setText("Error message");
        pnlContentAdd.add(lblErrAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, 200, -1));

        txtNote.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlContentAdd.add(txtNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 220, 30));

        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-add2.png"))); // NOI18N
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });
        pnlContentAdd.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 405, 140, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-supplier.jpg"))); // NOI18N
        pnlContentAdd.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 460));

        layered.add(pnlContentAdd, "card3");

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

        pnlContentList.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 560, 280));

        lblSearch.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(204, 0, 0));
        lblSearch.setText("Tìm kiếm");
        pnlContentList.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));
        pnlContentList.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 70, 10));

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-update2.png"))); // NOI18N
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });
        pnlContentList.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, -1, -1));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-delete2.png"))); // NOI18N
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });
        pnlContentList.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 130, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-ListSupplier.jpg"))); // NOI18N
        pnlContentList.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 460));

        layered.add(pnlContentList, "card2");

        pnlContentFamiliar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSimilarSup.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblSimilarSup.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSimilarSup.setRowHeight(30);
        tblSimilarSup.setSelectionBackground(new java.awt.Color(1, 157, 176));
        jScrollPane2.setViewportView(tblSimilarSup);

        pnlContentFamiliar.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 560, 310));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(234, 60, 60));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("NHỮNG NHÀ CUNG CẤP TIN DÙNG");
        pnlContentFamiliar.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 560, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-ListSupplier.jpg"))); // NOI18N
        pnlContentFamiliar.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 460));

        layered.add(pnlContentFamiliar, "card4");

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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTitleAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleAddMouseClicked
        navShowAdd();
    }//GEN-LAST:event_lblTitleAddMouseClicked

    private void lblTitleListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleListMouseClicked
       navShowList();
    }//GEN-LAST:event_lblTitleListMouseClicked

    private void lblTitleFamiliarSupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTitleFamiliarSupMouseClicked
        navShowFamiliar();
    }//GEN-LAST:event_lblTitleFamiliarSupMouseClicked

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        if(validateAll()) {
            add();           
        }
    }//GEN-LAST:event_btnAddMouseClicked

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
       fillTalble();
    }//GEN-LAST:event_txtSearchKeyTyped

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
       update();
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        delete();
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        validateSupplier();
    }//GEN-LAST:event_txtNameFocusLost

    private void txtPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusLost
        validatePhone();
    }//GEN-LAST:event_txtPhoneFocusLost

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        validateEmail();
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAddressFocusLost
        validateAddress();
    }//GEN-LAST:event_txtAddressFocusLost

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        setNullErr(txtName, lblErrName);
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        setNullErr(txtPhone, lblErrPhone);
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        setNullErr(txtEmail, lblErrEmail);
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddressKeyPressed
        setNullErr(txtAddress, lblErrAddress);
    }//GEN-LAST:event_txtAddressKeyPressed

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
            java.util.logging.Logger.getLogger(NhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhaCungCap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NhaCungCap dialog = new NhaCungCap(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnAdd;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLayeredPane layered;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblErrAddress;
    private javax.swing.JLabel lblErrEmail;
    private javax.swing.JLabel lblErrName;
    private javax.swing.JLabel lblErrPhone;
    private javax.swing.JLabel lblFamiliarSup;
    private javax.swing.JLabel lblIconAdd;
    private javax.swing.JLabel lblIconFamiliarSup;
    private javax.swing.JLabel lblIconList;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleAdd;
    private javax.swing.JLabel lblTitleFamiliarSup;
    private javax.swing.JLabel lblTitleList;
    private javax.swing.JLabel nav;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlContentAdd;
    private javax.swing.JPanel pnlContentFamiliar;
    private javax.swing.JPanel pnlContentList;
    private javax.swing.JPanel pnlFamiliarSup;
    private javax.swing.JPanel pnlList;
    private javax.swing.JTable tblList;
    private javax.swing.JTable tblSimilarSup;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
