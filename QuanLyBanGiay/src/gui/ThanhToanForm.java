/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.HoaDonCTDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.LoaiSanPhamDAO;
import dao.SanPhamDAO;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modal.HoaDon;
import modal.KhachHang;
import modal.LoaiSanPham;
import modal.SanPham;
import utils.Auth;
import utils.MsgBox;
import utils.Validator;
import utils.XDate;

/**
 *
 * @author DELL
 */
public class ThanhToanForm extends javax.swing.JDialog {

    DefaultComboBoxModel cbxModelLSP;
    DefaultTableModel tblModelProduct, tblModelProductCus;
    LoaiSanPhamDAO lspDAO;
    HoaDonDAO hdDAO;
    HoaDonCTDAO hdCTDAO;
    KhachHangDAO khDAO;
    SanPham sp;
    SanPhamDAO spDAO;
    LoaiSanPham lsp;
    KhachHang kh, getKH;
    List<Object[]> lstProduct, lstProductCus, lstProductCusTemp;
    ThongBaoForm tbForm;
   
    public ThanhToanForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Thanh toán");
        lstProductCus = new ArrayList<>();
        designTable();
        initErr();
        initColumnProduct();
        initColumnProductCustomer();
        fillCbx();
        fillTableProduct();
        setEditTablePayment();
    }
    public void setEditableFalse() {
        txtFullName.setEditable(false);
        txtEmail.setEditable(false);
        txtBirth.setEditable(false);
        txtPhone.setEditable(false);
        // Phần thanh toán
        txtSale.setEditable(false);
        txtTotalPrice.setEditable(false);
        txtTotalPayment.setEditable(false);
    }
    public void setEditTableTrue() {
        txtFullName.setEditable(true);
        txtEmail.setEditable(true);
        txtBirth.setEditable(true);
        txtPhone.setEditable(true); 
    }
    public void setEditTablePayment() {
        txtSale.setEditable(false);
        txtTotalPrice.setEditable(false);
        txtTotalPayment.setEditable(false);
        txtExchange.setEditable(false);
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(new javax.swing.JTextField().getBorder());
        label.setText(null);
    } 
    public void initErr() {
        lblErrFullName.setText(null);
        lblErrPhone.setText(null);
        lblErrEmail.setText(null);
        lblErrBirth.setText(null);
        txtBirth.setBorder(new javax.swing.JTextField().getBorder());
        txtEmail.setBorder(new javax.swing.JTextField().getBorder());
        txtFullName.setBorder(new javax.swing.JTextField().getBorder());
        txtPhone.setBorder(new javax.swing.JTextField().getBorder());
    }
    /* Validate */
    public boolean validateName() {
        return Validator.isNameInvoice(txtFullName, lblErrFullName);
    }
    public boolean validateEmail() {
        return Validator.isEmailInvoice(txtEmail, lblErrEmail);
    }
    public boolean validatePhone() {
        return Validator.isPhoneInvoice(txtPhone, lblErrPhone);
    }
    public boolean validateBirth() {
        return Validator.isBirthInvoice(txtBirth, lblErrBirth);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(validateName()) {
            isFormValid = false;
        }
        if(validateEmail()) {
            isFormValid = false;
        }
        if(validatePhone()) {
            isFormValid = false;
        }
        if(validateBirth()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public void designTable() {
        // Table list product
        tblProduct.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));     
        tblProduct.getTableHeader().setForeground(new java.awt.Color(0,0,0));
        // Table List product of customer
        tblProductCustomer.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblProductCustomer.getTableHeader().setForeground(new java.awt.Color(0,0,0));        
    }
    public void initColumnProduct() {
        tblModelProduct = (DefaultTableModel) tblProduct.getModel();
        tblModelProduct.setColumnIdentifiers(new Object[]{"Tên SP", "Size",
            "SL", "Giá"});
    }
    public void initColumnProductCustomer() {
        tblModelProductCus = (DefaultTableModel) tblProductCustomer.getModel();
        tblModelProductCus.setColumnIdentifiers(new Object[]{"TT", "Tên sản phẩm",
            "SL", "Đơn giá","Thành tiền"});
    }
    public void fillCbx() {
        cbxModelLSP = (DefaultComboBoxModel) cbxKindProduct.getModel();
        cbxModelLSP.removeAllElements();
        lspDAO = new LoaiSanPhamDAO();
        List<LoaiSanPham> lstSP = lspDAO.selectAll();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelLSP.addElement(lsp);
        }
        fillTableProduct();
    }
    public void setForm(KhachHang kh) {
        txtFullName.setText(kh.getHoTen());
        txtEmail.setText(kh.getEmail());
        if(kh.getNgaySinh() == null) {
            txtBirth.setText(null);
        } else {
            txtBirth.setText(XDate.toString(kh.getNgaySinh(), "dd-MM-yyyy"));           
        }
        txtPhone.setText(kh.getDienThoai());
    }
    public void clearForm() {
        txtSearch.setText(null);
        setEditTableTrue();
        setEditTablePayment();
        DefaultTableModel tbl = (DefaultTableModel) tblProductCustomer.getModel();
        tbl.setRowCount(0);
        // Clear thông tin khách hàng
        KhachHang kh2 = new KhachHang();
        kh = null;
        this.setForm(kh2);
        txtSearch.setText(null);
        // Clear thông tin thanh thoáng
        txtSale.setText(null);
        txtExchange.setText(null);
        txtMoneyFromCus.setText(null);
        txtTotalPayment.setText(null);
        txtTotalPrice.setText(null);
        // Clear thông tin sản phẩm
        txtSearchProduct.setText(null);
        txtSearchProduct.requestFocus();
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        lsp = new LoaiSanPham();
        hdDAO = new HoaDonDAO();
        tblModelProduct = (DefaultTableModel) tblProduct.getModel();
        tblModelProduct.setRowCount(0);
        fillCbx();
    }
    public KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setMaNV(Auth.user.getMaNV());
        kh.setHoTen(txtFullName.getText());
        kh.setEmail(txtEmail.getText());
        kh.setDienThoai(txtPhone.getText());
        kh.setNgaySinh(XDate.convertDate(txtBirth.getText(), "dd-MM-yyyy"));
        return kh;
    }
    public float convertMoney(String value) {
        String cut = value.substring(0, value.lastIndexOf(" ")).trim();
        String replaceStr = cut.replace(",","");
        float convert = Float.parseFloat(replaceStr);
        return convert;
    }  
    public void addProductCustomerBuy() {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        float sum = 0;
        SanPham sp = new SanPham();
        spDAO = new SanPhamDAO();
        tblModelProductCus = (DefaultTableModel) tblProductCustomer.getModel();
        int getRowCount = tblProduct.getRowCount();
        if(getRowCount > 0) {
            int index = tblProduct.getSelectedRow();
            if(index >= 0) {
                new SoLuongSP(null, true).setVisible(true);
                Object[] obj = lstProduct.get(index);
                if(obj.length > 0) {                 
                        String getID = (String) obj[0];
                        sp = spDAO.selectByID(getID);
                        if(sp != null) {
                            Object[] row = new Object[5];
                            row[0] = tblProductCustomer.getRowCount() + 1;
                            row[1] = sp.getTenSP();
                            if(Auth.soLuong == 0) {
        //                        row[2] = 1;
                                
                                tbForm = new ThongBaoForm(null, true);
                                tbForm.setMessage("Vui lòng nhập số lượng sản phẩm");
                                tbForm.setVisible(true);
                                return;
                            } else {
                                int quantity = (int) tblProduct.getValueAt(index, 2);
                                if(Auth.soLuong > quantity && Auth.soLuong > 1) {
                                    tbForm = new ThongBaoForm(null, true);
                                    tbForm.setMessage("Số lượng mua vượt quá số lượng SP có trong Shop"); 
                                    tbForm.setVisible(true);
                                    return;
                                }
                                row[2] = Auth.soLuong;
                            }
                            row[3] = formatter.format(sp.getGia())+ " VNĐ";
                            row[4] = formatter.format(Float.parseFloat(row[2]+"") * convertMoney(row[3]+"")) + " VNĐ";
                            tblModelProductCus.addRow(row);
                            // Tính tổng tiền SP
                            int countTblProductCus = tblModelProductCus.getRowCount();
                            for(int i = 0; i < countTblProductCus; i++) {
                                int rowTable = tblProductCustomer.getRowCount();
                                if(rowTable > 0) {
                                        String strValue = (String) tblProductCustomer.getValueAt(i, 4);
                                        float getValue = convertMoney(strValue);   
                                        sum += getValue;
                                }                   
                            }
                        }
                        setEditableFalse();
                        initErr();
                    }
                    Auth.soLuong = 0;
                    txtTotalPrice.setText(formatter.format(sum)+ " VNĐ");
                    txtTotalPayment.setText(formatter.format(sum)+ " VNĐ");
                    saleMoney();
                }
        }
    }
    public void delete() {
        if(MsgBox.confirm(this, "Bạn muốn xóa sản phẩm này")) {
            DecimalFormat formatter;
            formatter = new DecimalFormat("###,###,###");
            float sum = 0;
            int count = 0;
            int getRowCount = tblProductCustomer.getRowCount();
            if(getRowCount > 0) {
                int index = tblProductCustomer.getSelectedRow();
                if(index >= 0) {
                    DefaultTableModel tbl = (DefaultTableModel) tblProductCustomer.getModel();
                    tbl.removeRow(index);
                }
                getRowCount = tblProductCustomer.getRowCount();
                if(getRowCount > 0) {
                    for(int i = 0; i < getRowCount; i++) {
                        count++;
                        tblProductCustomer.setValueAt(count,i,0);
                        // SET LẠI GIÁ
                        String strValue = (String) tblProductCustomer.getValueAt(i, 4);
                        float getValue = convertMoney(strValue);               
                        sum += getValue;
                    }
                    txtTotalPrice.setText(formatter.format(sum)+ " VNĐ");
                    txtTotalPayment.setText(formatter.format(sum)+ " VNĐ");
                } else {
                     txtTotalPrice.setText("");
                     txtTotalPayment.setText("");

                }
                saleMoney();
            }      
        }
    } 
    public void update() {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        float sum = 0;
        int getRowCount = tblProductCustomer.getRowCount();
        if(getRowCount > 0) {
           int index = tblProductCustomer.getSelectedRow();
           if(index >= 0) {
               float quantity =  Float.parseFloat(tblProductCustomer.getValueAt(index, 2)+"");
               String strPrice = (String) tblProductCustomer.getValueAt(index, 3);
               float getPrice = convertMoney(strPrice);
               float setTotal = quantity * getPrice; 
               tblProductCustomer.setValueAt(formatter.format(setTotal) + " VNĐ", index, 4);
                int countTblProductCus = tblModelProductCus.getRowCount();
                for(int i = 0; i < countTblProductCus; i++) {
                    float getValue = convertMoney((String)tblProductCustomer.getValueAt(index, 4));
                    sum += getValue;
                }
                txtTotalPrice.setText(formatter.format(sum)+ " VNĐ");
                txtTotalPayment.setText(formatter.format(sum)+ " VNĐ");
                saleMoney();
           }
       }
    }
    public void fillTableProduct() {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        lsp = new LoaiSanPham();
        hdDAO = new HoaDonDAO();
        tblModelProduct = (DefaultTableModel) tblProduct.getModel();
        tblModelProduct.setRowCount(0);
        lsp = (LoaiSanPham) cbxKindProduct.getSelectedItem();
        if(lsp != null) {
            List<Object[]> lstObj = hdDAO.getProductSell(lsp.getMaLoaiSP(), txtSearchProduct.getText());
            if(lstObj.size() > 0) {
                lstProduct = new ArrayList<>();
                lstProduct = lstObj;
                for(Object[] row : lstObj) {
                    tblModelProduct.addRow(new Object[]{ row[1], row[2],
                    row[3], formatter.format(row[4]) + " VNĐ"});
                }
            }   
                  
        }
    }
    public void sale(String phone) {
        KhachHang kh = new KhachHang();
            KhachHangDAO khDAO = new KhachHangDAO();
            kh = khDAO.CustomerByBirth(phone);
            if(kh != null) {
                if(txtSale.getText().equals("10%")) {
                    txtSale.setText("20%");
                } else {
                    txtSale.setText("10%");
                }              
            } else {
                // Xử lý trường hợp khi chọn một khách hàng trước đó đc giảm giá,
                // và chọn lại khách hàng không được giảm giá
                if(txtSale.getText().equals("10%") || txtSale.getText().equals("20%")) {
                    txtSale.setText("10%");
                } else {
                    txtSale.setText(null);
                    
                }  
            }
    }
    public void findCustomerByInfor() {
        if(!txtSearch.getText().equals("")) {
            List<KhachHang> lstKH = new ArrayList<>();
            khDAO = new KhachHangDAO();
            kh = new KhachHang();
            this.setForm(kh);
            kh = khDAO.searchByPhone(txtSearch.getText());
            txtSale.setText("");
            // Xử lý trường hợp khi tìm kiếm theo tên có 2 hoặc nhiều tên trùng lập
            lstKH = khDAO.searchByInfor(txtSearch.getText());
            if(lstKH != null) {
                if(lstKH.size() >= 2) {
                    Template temp = new Template(null, true);
                    temp.fillTable(lstKH);
                    temp.setVisible(true);
                    if(Auth.kh != null) {
                        this.setForm(Auth.kh);
                        // Lọc xem khách hàng có mua >= 10 sản phẩm
                        if(khDAO.CustomerBuy10Product(Auth.kh.getDienThoai()).size() > 0) {
                            txtSale.setText("10%");
                        }
                        sale(Auth.kh.getDienThoai());
                        setEditableFalse();
                        saleMoney();
                    }
                    return;
                }
            }
            // Trường hợp khi chỉ tồn tại 1 khách hàng
            if(kh != null) {
                this.setForm(kh);
                if(khDAO.CustomerBuy10Product(kh.getDienThoai()).size() > 0) {
                            txtSale.setText("10%");
                    }
                sale(kh.getDienThoai());
                setEditableFalse();
                saleMoney();
            }        
        } else {
            kh = new KhachHang();
        }
    }
    public void saleMoney() {       
        if(!txtTotalPrice.getText().equals("")) {
            if(!txtSale.getText().equals("")) {
                DecimalFormat formatter;
                formatter = new DecimalFormat("###,###,###");
                float getValue = convertMoney(txtTotalPrice.getText());
                float getSale = Float.parseFloat(
                        txtSale.getText().substring(0,txtSale.getText().indexOf("%")).trim()
                );
                float discount = getValue * ((100 - getSale)/100);
                txtTotalPayment.setText(formatter.format(discount) + " VNĐ");
            }
        }
    }
    public void addInvoice() {
        int getRowCount = tblProductCustomer.getRowCount();
        hdDAO = new HoaDonDAO();
        hdCTDAO = new HoaDonCTDAO();
        HoaDon hd = new HoaDon();
        if(kh != null && !(txtTotalPayment.getText().equals(""))) {
            // Trường hợp khách hàng đã tới shop
            hd.setMaKH(kh.getMaKH());
            hd.setMaNV("NV001");
            hd.setNgayLapHD(XDate.convertTimeStamp());
            hd.setDonGia(convertMoney(txtTotalPayment.getText()));
            int rowEffectInvoice = hdDAO.insert(hd);
            if(rowEffectInvoice <= 0) {
                tbForm = new ThongBaoForm(null, true);
                tbForm.setMessage("Lỗi chèn hóa đơn");
                tbForm.setVisible(true);
                return;
            } 
            // Lấy mã hóa đơn vừa mới insert
            boolean success = true;
            int getIdInvoice = (int) hdDAO.getValueInvoice();
            if(getRowCount > 0) {
                for(int i = 0; i < getRowCount; i++) {
                    String nameProduct = (String) tblProductCustomer.getValueAt(i, 1);
                    int quantityProduct = (int) tblProductCustomer.getValueAt(i, 2);
                    int rowEffectInvoiceDetails = hdCTDAO.insertByNameProduct(getIdInvoice, nameProduct, quantityProduct);
                    if(rowEffectInvoice > 0) {
                        success = true;
                    } else {
                        success = false;
                    }
                }
                if(success) {
                    tbForm = new ThongBaoForm(null, true);
                    tbForm.setMessage("Thêm hóa đơn thành công");
                    tbForm.setVisible(true);
                    clearForm();
                }                 
            } 
    } else if(kh == null && !(txtTotalPayment.getText().equals("")))  {
            KhachHang kh = new KhachHang();
            khDAO = new KhachHangDAO();
            kh.setHoTen(txtFullName.getText());
            kh.setEmail(txtEmail.getText());
            kh.setDienThoai(txtPhone.getText());
            kh.setNgaySinh(XDate.convertDate(txtBirth.getText(), "dd-MM-yyyy"));
            kh.setMaNV("NV001");
            int rowEffectCustomer = khDAO.insertInvoice(kh);
            if(rowEffectCustomer < 0) {
                tbForm = new ThongBaoForm(null, true);
                tbForm.setMessage("Lỗi thêm khách hàng");
                tbForm.setVisible(true);
                return;
            }
            // Lấy dữ liệu khách hàng vừa insert
            String getIdCustomer = (String) khDAO.getValueCustomer();
            // Insert vào bảng hóa đơn
            hd.setMaKH(getIdCustomer);
            hd.setMaNV("NV001");
            hd.setNgayLapHD(XDate.convertTimeStamp());
            hd.setDonGia(convertMoney(txtTotalPayment.getText()));
            int rowEffectCus = hdDAO.insert(hd);
            if(rowEffectCus < 0) {
                tbForm = new ThongBaoForm(null, true);
                tbForm.setMessage("Lỗi thêm hóa đơn");
                tbForm.setVisible(true);
                return;
            }
            // Lấy mã hóa đơn vừa mới insert
            boolean success2 = true;
            int getIdInvoice2 = (int) hdDAO.getValueInvoice();
            if(getRowCount > 0) {
                for(int i = 0; i < getRowCount; i++) {
                    String nameProduct = (String) tblProductCustomer.getValueAt(i, 1);
                    int quantityProduct = (int) tblProductCustomer.getValueAt(i, 2);
                    int rowEffectInvoiceDetails = hdCTDAO.insertByNameProduct(getIdInvoice2, nameProduct, quantityProduct);
                    if(rowEffectInvoiceDetails > 0) {
                        success2 = true;
                    } else {
                        success2 = false;
                    }
                }
                if(success2) {
                    tbForm = new ThongBaoForm(null, true);
                    tbForm.setMessage("Thêm hóa đơn thành công");
                    tbForm.setVisible(true);
                    clearForm();
                }
            }
    }
    }
    public void viewInvoices() {
         new HoaDonForm(null, true).setVisible(true);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenu = new javax.swing.JPopupMenu();
        mnuOption = new javax.swing.JMenu();
        mnuOption_Delete = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        lblInforProduct = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbxKindProduct = new javax.swing.JComboBox();
        txtSearchProduct = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblInforInvoice = new javax.swing.JLabel();
        lblFullName = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblBirth = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        lblErrPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        txtSearch = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtBirth = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProductCustomer = new javax.swing.JTable();
        lblErrFullName = new javax.swing.JLabel();
        lblErrEmail = new javax.swing.JLabel();
        lblErrBirth = new javax.swing.JLabel();
        lblPayment = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        lblMoneyFromCus = new javax.swing.JLabel();
        lblTotalPayment = new javax.swing.JLabel();
        txtTotalPrice = new javax.swing.JTextField();
        txtMoneyFromCus = new javax.swing.JTextField();
        txtTotalPayment = new javax.swing.JTextField();
        txtSale = new javax.swing.JTextField();
        lblSale = new javax.swing.JLabel();
        lblPrintInvoice = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblPrint = new javax.swing.JLabel();
        iconPrint = new javax.swing.JLabel();
        pnlPrint = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblCancelInvoice = new javax.swing.JLabel();
        lblExchange = new javax.swing.JLabel();
        txtExchange = new javax.swing.JTextField();
        viewInvoice = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        mnuOption.setText("Tùy chọn");
        mnuOption.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N

        mnuOption_Delete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        mnuOption_Delete.setText("Xóa");
        mnuOption_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOption_DeleteActionPerformed(evt);
            }
        });
        mnuOption.add(mnuOption_Delete);

        popMenu.add(mnuOption);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInforProduct.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInforProduct.setForeground(new java.awt.Color(255, 255, 255));
        lblInforProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInforProduct.setText("THÔNG TIN SẢN PHẨM");
        jPanel1.add(lblInforProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 170, 20));

        jScrollPane1.setBorder(null);

        tblProduct.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProduct.setRowHeight(30);
        tblProduct.setSelectionBackground(new java.awt.Color(1, 157, 176));
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 310, 150));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tìm kiếm tên sản phẩm");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tìm kiếm loại sản phẩm");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        cbxKindProduct.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbxKindProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxKindProductActionPerformed(evt);
            }
        });
        jPanel1.add(cbxKindProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 310, 40));

        txtSearchProduct.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSearchProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchProductKeyTyped(evt);
            }
        });
        jPanel1.add(txtSearchProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 310, 40));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Danh sách sản phẩm");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        lblInforInvoice.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblInforInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblInforInvoice.setText("THÔNG TIN KHÁCH HÀNG");
        jPanel1.add(lblInforInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(758, 32, 180, 20));

        lblFullName.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblFullName.setForeground(new java.awt.Color(255, 255, 255));
        lblFullName.setText("Họ và tên");
        jPanel1.add(lblFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(383, 80, 80, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Email");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 160, 50, 20));

        lblBirth.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblBirth.setForeground(new java.awt.Color(255, 255, 255));
        lblBirth.setText("Ngày sinh");
        jPanel1.add(lblBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 160, -1, 20));

        lblSearch.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 255, 255));
        lblSearch.setText("Tìm khách hàng");
        jPanel1.add(lblSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 10, 120, -1));

        lblErrPhone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblErrPhone.setForeground(new java.awt.Color(255, 102, 102));
        lblErrPhone.setText("Error Message");
        jPanel1.add(lblErrPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 140, 240, -1));

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
        jPanel1.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, 240, 40));

        lblPhone.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPhone.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone.setText("Điện thoại");
        jPanel1.add(lblPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, -1, -1));

        txtFullName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtFullName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFullNameFocusLost(evt);
            }
        });
        txtFullName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFullNameKeyPressed(evt);
            }
        });
        jPanel1.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 240, 40));

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        jPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 240, 40));

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
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 240, 40));

        txtBirth.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtBirth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBirthFocusLost(evt);
            }
        });
        txtBirth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBirthKeyPressed(evt);
            }
        });
        jPanel1.add(txtBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 180, 240, 40));

        tblProductCustomer.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblProductCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProductCustomer.setComponentPopupMenu(popMenu);
        tblProductCustomer.setRowHeight(30);
        tblProductCustomer.setSelectionBackground(new java.awt.Color(1, 157, 176));
        tblProductCustomer.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblProductCustomerPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(tblProductCustomer);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 510, 160));

        lblErrFullName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblErrFullName.setForeground(new java.awt.Color(255, 102, 102));
        lblErrFullName.setText("Error Message");
        jPanel1.add(lblErrFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 240, -1));

        lblErrEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblErrEmail.setForeground(new java.awt.Color(255, 102, 102));
        lblErrEmail.setText("Error Message");
        jPanel1.add(lblErrEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, 240, 20));

        lblErrBirth.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblErrBirth.setForeground(new java.awt.Color(255, 102, 102));
        lblErrBirth.setText("Error Message");
        jPanel1.add(lblErrBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 220, 250, 20));

        lblPayment.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPayment.setForeground(new java.awt.Color(255, 255, 255));
        lblPayment.setText("THANH TOÁN");
        jPanel1.add(lblPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(805, 439, 90, 20));

        lblTotalPrice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalPrice.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalPrice.setText("Tổng tiền:");
        jPanel1.add(lblTotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, 90, -1));

        lblMoneyFromCus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMoneyFromCus.setForeground(new java.awt.Color(255, 255, 255));
        lblMoneyFromCus.setText("Tiền khách đưa:");
        jPanel1.add(lblMoneyFromCus, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 485, 110, -1));

        lblTotalPayment.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalPayment.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalPayment.setText("Tổng tiền thanh toán:");
        jPanel1.add(lblTotalPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 545, 140, -1));

        txtTotalPrice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel1.add(txtTotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 440, 230, 32));

        txtMoneyFromCus.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMoneyFromCus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMoneyFromCusKeyReleased(evt);
            }
        });
        jPanel1.add(txtMoneyFromCus, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 505, 230, 32));

        txtTotalPayment.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalPayment.setForeground(new java.awt.Color(204, 51, 0));
        jPanel1.add(txtTotalPayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 565, 230, 32));

        txtSale.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel1.add(txtSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 505, 230, 32));

        lblSale.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSale.setForeground(new java.awt.Color(255, 255, 255));
        lblSale.setText("Chiếu khấu (%):");
        jPanel1.add(lblSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 485, 130, -1));

        lblPrintInvoice.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPrintInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblPrintInvoice.setText("IN HÓA ĐƠN");
        jPanel1.add(lblPrintInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 442, -1, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPrint.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPrint.setForeground(new java.awt.Color(255, 255, 255));
        lblPrint.setText("IN");
        lblPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPrintMouseClicked(evt);
            }
        });
        jPanel2.add(lblPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 15, -1, 20));

        iconPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/invoice-price.png"))); // NOI18N
        iconPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPrintMouseClicked(evt);
            }
        });
        jPanel2.add(iconPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 50, 50));

        pnlPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/line-price.jpg"))); // NOI18N
        pnlPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPrintMouseClicked(evt);
            }
        });
        jPanel2.add(pnlPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 120, 50));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("HỦY");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCancelInvoiceMouseClicked(evt);
            }
        });
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 15, 30, 20));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cancel-price.png"))); // NOI18N
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCancelInvoiceMouseClicked(evt);
            }
        });
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 50, 50));

        lblCancelInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/line-price.jpg"))); // NOI18N
        lblCancelInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCancelInvoiceMouseClicked(evt);
            }
        });
        jPanel3.add(lblCancelInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 50));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 500, 120, 50));

        lblExchange.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblExchange.setForeground(new java.awt.Color(255, 255, 255));
        lblExchange.setText("Tiền thối lại");
        jPanel1.add(lblExchange, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 546, 130, -1));

        txtExchange.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jPanel1.add(txtExchange, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 565, 230, 32));

        viewInvoice.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        viewInvoice.setForeground(new java.awt.Color(255, 255, 255));
        viewInvoice.setText("Xem danh sách hóa đơn?");
        viewInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewInvoiceMouseClicked(evt);
            }
        });
        jPanel1.add(viewInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 580, -1, -1));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-price.jpg"))); // NOI18N
        bg.setText("jLabel12");
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxKindProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxKindProductActionPerformed
        fillTableProduct();
    }//GEN-LAST:event_cbxKindProductActionPerformed

    private void txtSearchProductKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProductKeyTyped
        fillTableProduct();
    }//GEN-LAST:event_txtSearchProductKeyTyped

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        if(txtSearch.getText().equals("")) {
            KhachHang kh = new KhachHang();
            this.setForm(kh);
            setEditTableTrue();
            initErr();
        } else {
            findCustomerByInfor();            
        }
    }//GEN-LAST:event_txtSearchKeyReleased
    
    private void tblProductCustomerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblProductCustomerPropertyChange
        update();
    }//GEN-LAST:event_tblProductCustomerPropertyChange

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
         addProductCustomerBuy();
    }//GEN-LAST:event_tblProductMouseClicked

    private void mnuOption_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOption_DeleteActionPerformed
        delete();
    }//GEN-LAST:event_mnuOption_DeleteActionPerformed

    private void txtMoneyFromCusKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMoneyFromCusKeyReleased
        if(!txtTotalPayment.getText().equals("")) {
            String pattern = "\\d+";
            if(txtMoneyFromCus.getText().matches(pattern)) {
                txtMoneyFromCus.setBorder(new javax.swing.JTextField().getBorder());
                DecimalFormat formatter;
                formatter = new DecimalFormat("###,###,###");
                float getPayment = convertMoney(txtTotalPayment.getText());
                if(!txtMoneyFromCus.getText().equals("")) {
                    float getMoneyCus = Float.parseFloat(txtMoneyFromCus.getText());
                    float getExchange = getMoneyCus - getPayment;     
                    txtExchange.setText(formatter.format(getExchange)+" VNĐ");                                
                } else {
                    txtExchange.setText("");
                }              
            } else {
                EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
                txtMoneyFromCus.setBorder(eb);
                
            }
        }
    }//GEN-LAST:event_txtMoneyFromCusKeyReleased

    private void lblCancelInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCancelInvoiceMouseClicked
        if(tblProductCustomer.getRowCount() > 0) {
            if(MsgBox.confirm(this, "Bạn muốn hủy đơn hàng này?")) {
                    clearForm();           
            }            
        } else {
            tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage("Hóa đơn trống");
            tbForm.setVisible(true);
        }
    }//GEN-LAST:event_lblCancelInvoiceMouseClicked

    private void pnlPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPrintMouseClicked
        if(tblProductCustomer.getRowCount() > 0) {
            if(validateAll()) {
                if(MsgBox.confirm(this, "Xác nhận thanh toán")) {
                        if(!(txtExchange.getText().equals(""))) {
                            if(convertMoney(txtExchange.getText()) < 0) {
                                tbForm = new ThongBaoForm(null, true);
                                tbForm.setMessage("Tiền thối lại khách không hợp lệ");
                                tbForm.setVisible(true);
                            } else {
                                addInvoice();              
                            }                                                 
                        } else {
                            tbForm = new ThongBaoForm(null, true);
                            tbForm.setMessage("Vui lòng nhập tiền khách trả");
                            tbForm.setVisible(true);
                        }
                }                            
            }
        } else {
            tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage("Hóa đơn trống");
            tbForm.setVisible(true);
        }
    }//GEN-LAST:event_pnlPrintMouseClicked

    private void viewInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewInvoiceMouseClicked
        viewInvoices();
    }//GEN-LAST:event_viewInvoiceMouseClicked

    private void txtFullNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFullNameFocusLost
        validateName();
    }//GEN-LAST:event_txtFullNameFocusLost

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        validateEmail();
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusLost
        validatePhone();
    }//GEN-LAST:event_txtPhoneFocusLost

    private void txtBirthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBirthFocusLost
        validateBirth();
    }//GEN-LAST:event_txtBirthFocusLost

    private void txtFullNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFullNameKeyPressed
        setNullErr(txtFullName, lblErrFullName);
    }//GEN-LAST:event_txtFullNameKeyPressed

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        setNullErr(txtPhone, lblErrPhone);
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        setNullErr(txtEmail, lblErrEmail);
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtBirthKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBirthKeyPressed
        setNullErr(txtBirth, lblErrBirth);
    }//GEN-LAST:event_txtBirthKeyPressed

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
            java.util.logging.Logger.getLogger(ThanhToanForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThanhToanForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThanhToanForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThanhToanForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThanhToanForm dialog = new ThanhToanForm(new javax.swing.JFrame(), true);
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
//    public List<Object[]> addProductCusBuy() {
//        lstProductCusTemp = new ArrayList<>();
//        DecimalFormat formatter;
//        formatter = new DecimalFormat("###,###,###");
//        int getRowCount = tblProduct.getRowCount();
//        SanPham sp = new SanPham();
//        spDAO = new SanPhamDAO();
//        if(getRowCount > 0) {
//            int index = tblProduct.getSelectedRow();
//            if(index >= 0) {
//                Object[] obj = lstProduct.get(index);
//                String getID = (String) obj[0];
//                sp = spDAO.selectByID(getID);
//                if(sp != null) {
//                    Object[] row = new Object[4];
//                    row[0] = sp.getTenSP();
//                    new SoLuongSP(null, true).setVisible(true);
//                    if(Auth.soLuong == 0) {
//                        row[1] = 1;                       
//                    } else {
//                        row[1] = Auth.soLuong;
//                    }
//                    row[2] = formatter.format(sp.getGia()) + " VNĐ";
//                    row[3] = formatter.format(
//                            Float.parseFloat(row[1]+"") * convertMoney((String)row[2])
//                            ) + " VNĐ";
//                    lstProductCusTemp.add(row);
//                    lstProductCus = lstProductCusTemp;
//                }
//            }
//        }
//        return lstProductCus;
//    }
//        public void getInforInvoice() {
////        int getRowCount = tblProduct.getRowCount();
////        if(getRowCount > 0 ) {
////            int index = tblProduct.getSelectedRow();
////            if(index >= 0) {
////                Object[] obj = lstProductTemp.get(index);
////                String getID = (String) obj[0];
////                System.out.println("" + getID);
////            }
////        }
//        fillTableProductCus(addProductCusBuy());
//    }
//    public void fillTableProductCus(List<Object[]> lstObj) {
//        
//        DecimalFormat formatter;
//        formatter = new DecimalFormat("###,###,###");
//        float sum = 0;
//        tblModelProductCus = (DefaultTableModel) tblProductCustomer.getModel();
//        tblModelProductCus.setRowCount(0);
//        if(lstObj.size() > 0) {
//            for(int i = 0; i < lstObj.size(); i++) {
//                Object[] row = lstObj.get(i);
//                tblModelProductCus.addRow(new Object[]{i+1, row[0], row[1], 
//                row[2], row[3]});
//                float getValue = convertMoney((String)row[3]);
//                sum += getValue;
//            }            
//        }
//        txtTotalPrice.setText(formatter.format(sum)+ " VNĐ");
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JComboBox cbxKindProduct;
    private javax.swing.JLabel iconPrint;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBirth;
    private javax.swing.JLabel lblCancelInvoice;
    private javax.swing.JLabel lblErrBirth;
    private javax.swing.JLabel lblErrEmail;
    private javax.swing.JLabel lblErrFullName;
    private javax.swing.JLabel lblErrPhone;
    private javax.swing.JLabel lblExchange;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblInforInvoice;
    private javax.swing.JLabel lblInforProduct;
    private javax.swing.JLabel lblMoneyFromCus;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPrint;
    private javax.swing.JLabel lblPrintInvoice;
    private javax.swing.JLabel lblSale;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTotalPayment;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JMenu mnuOption;
    private javax.swing.JMenuItem mnuOption_Delete;
    private javax.swing.JLabel pnlPrint;
    private javax.swing.JPopupMenu popMenu;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblProductCustomer;
    private javax.swing.JTextField txtBirth;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtExchange;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtMoneyFromCus;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSale;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchProduct;
    private javax.swing.JTextField txtTotalPayment;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JLabel viewInvoice;
    // End of variables declaration//GEN-END:variables
}
