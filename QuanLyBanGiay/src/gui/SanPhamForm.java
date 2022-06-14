/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.LoaiSanPhamDAO;
import dao.SanPhamDAO;
import dao.ThongKeDAO;
import java.awt.Color;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import modal.LoaiSanPham;
import modal.SanPham;
import utils.Auth;
import utils.DragAndDrop;
import utils.DragAndDropProduct;
import utils.MsgBox;
import utils.Validator;
import utils.XDate;
import utils.XImg;

/**
 *
 * @author DELL
 */
public class SanPhamForm extends javax.swing.JDialog {

    /**
     * Creates new form SanPhamForm
     */
    SanPham sp;
    LoaiSanPham lsp;
    SanPhamDAO spDAO;
    LoaiSanPhamDAO lspDAO;
    ThongKeDAO tkDAO;
    DefaultTableModel tblModelSP, tblModelStopTrade, tblModelSeller, tblModelOOS;
    DefaultComboBoxModel cbxModelLSP, cbxModelFilter;
    List<SanPham> lstSP, lstStopTrade;
    JFileChooser fileChooser;
    boolean hiddenText = true;
    public SanPhamForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Quản lý sản phẩm");  
        designTable();
        initColumnIdentity();
        initErr();
        fillCbx();
        fillCbxFilter();
        fillCbxFilterPrice();
        fillCbxOOS();
        fillCbxStopTrade();
        fillCbxSeller();
        dragAndDropImg();
        txtDateEnter.setText(XDate.toString(XDate.now(), "dd-MM-yyyy"));
        txtDateEnter.setEditable(false);
    }
    public void dragAndDropImg() {
        DragAndDropProduct dad = new DragAndDropProduct(lblImgProduct, lblDragAndDrop);
        new DropTarget(this, dad);      
    }
    public void designTable() {
        // Table list
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblList.getTableHeader().setForeground(new java.awt.Color(32,136,203));
        // Table List Seller
        tblListSeller.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblListSeller.getTableHeader().setForeground(new java.awt.Color(32,136,203));
        // Table Stop Trade
        tblStopTrade.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblStopTrade.getTableHeader().setForeground(new java.awt.Color(32,136,203));
        // Table OOS
        tblListOOS.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblListOOS.getTableHeader().setForeground(new java.awt.Color(32,136,203));
//        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
//        tblList.getTableHeader().setForeground(new java.awt.Color(32,136,203));
    }
    public void initErr() {
        lblErrExpiry.setText(null);
        lblErrNameProduct.setText(null);
        lblErrPrice.setText(null);
        lblErrSize.setText(null);
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
    public EtchedBorder getBorders() {
        EtchedBorder eb = new EtchedBorder(Color.red, Color.white);
        return eb;
    }
    public void showMessage(String message) {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage(message);
            tbForm.setVisible(true);
    }
    public void navShowProduct() {
            layeredPane.removeAll();
            layeredPane.add(pnlProduct);
            layeredPane.repaint();
            layeredPane.revalidate();
    }
    public void navShowList() {
            layeredPane.removeAll();
            layeredPane.add(pnlList);
            layeredPane.repaint();
            layeredPane.revalidate();
    }
    public void navShowBestSeller() {
            layeredPane.removeAll();
            layeredPane.add(pnlSeller);
            layeredPane.repaint();
            layeredPane.revalidate();
    }
    public void navShowOutOfStock() {
            layeredPane.removeAll();
            layeredPane.add(pnlOOS);
            layeredPane.repaint();
            layeredPane.revalidate();
    }
    public void navShowStopTrade() {
            layeredPane.removeAll();
            layeredPane.add(pnlST);
            layeredPane.repaint();
            layeredPane.revalidate();
    }
    /* Validate */
    public boolean validateNameProduct() {
        return Validator.isEmptyProduct(txtNameProduct, lblErrNameProduct);
    }
    public boolean validatePrice() {
        return Validator.checkPrice(txtPrice, lblErrPrice);
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
    public void initColumnIdentity() {
        tblModelSP = (DefaultTableModel) tblList.getModel();
        tblModelSP.setColumnIdentifiers(new Object[]{"Mã SP", "Tên SP", "Giá", "Ngày bán",
        "Size", "Mô Tả", "HSD"});
        // Column cho bảng Sản phẩm ngừng kinh doanh
        tblModelStopTrade = (DefaultTableModel) tblStopTrade.getModel();
        tblModelStopTrade.setColumnIdentifiers(new Object[]{"Mã SP", "Tên SP", "Giá",
        "Size"});
        // Column cho bảng ToP 3 Sản phẩm bán chạy
        tblModelSeller = (DefaultTableModel) tblListSeller.getModel();
        tblModelSeller.setColumnIdentifiers(new Object[]{"Mã SP", "Tên SP", "Giá",
        "Size", "Số lượng"});
        // Column cho bảng các sản phẩm sắp hết hàng
        tblModelOOS = (DefaultTableModel) tblListOOS.getModel();
        tblModelOOS.setColumnIdentifiers(new Object[]{"Mã SP","Tên SP","Size","Số lượng"});
    }
    public void fillCbx() {
        cbxModelLSP = (DefaultComboBoxModel) cbxKindProduct.getModel();
        cbxModelLSP.removeAllElements();
        lspDAO = new LoaiSanPhamDAO();
        List<LoaiSanPham> lstSP = lspDAO.selectAll();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelLSP.addElement(lsp);
        }
    }
    public void fillCbxAll() {
        cbxModelFilter = (DefaultComboBoxModel) cbxFilter.getModel();
        cbxModelFilter.removeAllElements();
        lspDAO = new LoaiSanPhamDAO();
        List<LoaiSanPham> lstSP = lspDAO.selectAll();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelFilter.addElement(lsp);
        }
        //
        cbxModelFilter = (DefaultComboBoxModel) cbxFilterOOS.getModel();
        cbxModelFilter.removeAllElements();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelFilter.addElement(lsp);
        }
        //
        cbxModelFilter = (DefaultComboBoxModel) cbxStopTrade.getModel();
        cbxModelFilter.removeAllElements();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelFilter.addElement(lsp);
        }
        //
        cbxModelFilter = (DefaultComboBoxModel) cbxSeller.getModel();
        cbxModelFilter.removeAllElements();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelFilter.addElement(lsp);
        }
    }
    public void fillCbxFilter() {
        cbxModelFilter = (DefaultComboBoxModel) cbxFilter.getModel();
        cbxModelFilter.removeAllElements();
        lspDAO = new LoaiSanPhamDAO();
        List<LoaiSanPham> lstSP = lspDAO.selectAll();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelFilter.addElement(lsp);
        }
        fillTable();
    }
    public void fillCbxFilterPrice() {
        cbxModelFilter = (DefaultComboBoxModel) cbxFilterPrice.getModel();
        cbxModelFilter.removeAllElements();
        cbxModelFilter.addElement("Tất cả");
        cbxModelFilter.addElement("5.000 VNĐ - 500.000 VNĐ");
        cbxModelFilter.addElement("500.000 VNĐ - 1.000.000 VNĐ");
        cbxModelFilter.addElement("1.000.000 VNĐ - 2.000.000 VNĐ");
        cbxModelFilter.addElement("2.000.000 VNĐ - 3.000.000 VNĐ");
    }
    public void fillCbxOOS() {
        cbxModelFilter = (DefaultComboBoxModel) cbxFilterOOS.getModel();
        cbxModelFilter.removeAllElements();
        lspDAO = new LoaiSanPhamDAO();
        List<LoaiSanPham> lstSP = lspDAO.selectAll();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelFilter.addElement(lsp);
        }
    }
    public void fillCbxStopTrade() {
        cbxModelFilter = (DefaultComboBoxModel) cbxStopTrade.getModel();
        cbxModelFilter.removeAllElements();
        lspDAO = new LoaiSanPhamDAO();
        List<LoaiSanPham> lstSP = lspDAO.selectAll();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelFilter.addElement(lsp);
        }
    }
    public void fillCbxSeller() {
        cbxModelFilter = (DefaultComboBoxModel) cbxSeller.getModel();
        cbxModelFilter.removeAllElements();
        lspDAO = new LoaiSanPhamDAO();
        List<LoaiSanPham> lstSP = lspDAO.selectAll();
        for(LoaiSanPham lsp : lstSP) {
            cbxModelFilter.addElement(lsp);
        }
    }
    public void fillTable() {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        tblModelSP = (DefaultTableModel) tblList.getModel();
        while(tblModelSP.getRowCount() > 0) {
            tblModelSP.removeRow(0);
        }
        // Lọc theo mã loại SP
        LoaiSanPham lsp = (LoaiSanPham) cbxFilter.getSelectedItem();
        if(lsp != null) {          
            String getValue = (String) cbxFilterPrice.getSelectedItem();
            if(getValue != null) {
                if(getValue.equalsIgnoreCase("Tất cả")) {
                     lstSP = new ArrayList<>();
                spDAO = new SanPhamDAO();
                lstSP = spDAO.selectByLSP(lsp.getMaLoaiSP(), 0, 999999999);
                if (!lstSP.isEmpty()) {
                    for (SanPham sp : lstSP) {
                        tblModelSP.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(), formatter.format(sp.getGia()) + " VNĐ",
                            XDate.toString(sp.getNgayBan(), "dd-MM-yyyy"), sp.getSize(),
                            sp.getMoTa(), sp.getHanSuDung()});
                    }
                } else {
                    // TO DO CODE
                }
                } else {
                    String firstPrice = getValue.substring(0, getValue.indexOf(" ")+1).trim();
                    String secondPrice = getValue.substring(getValue.indexOf("- ")+ 1,
                        getValue.lastIndexOf(" ")).trim();
                    String replaceFirst = firstPrice.replace(".", "");
                    String replaceSecond = secondPrice.replace(".", "");
                    float firstParse = Float.parseFloat(replaceFirst);
                    float secondParse = Float.parseFloat(replaceSecond);

                    lstSP = new ArrayList<>();
                    spDAO = new SanPhamDAO();
                    lstSP = spDAO.selectByLSP(lsp.getMaLoaiSP(), firstParse, secondParse);
                    if (!lstSP.isEmpty()) {
                        for (SanPham sp : lstSP) {
                            tblModelSP.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(), formatter.format(sp.getGia()) + " VNĐ",
                                XDate.toString(sp.getNgayBan(), "dd-MM-yyyy"), sp.getSize(),
                                sp.getMoTa(), sp.getHanSuDung()});
                        }
                    } else {
        //                MsgBox.alert(this, "Chưa kinh doanh loại sản phẩm này");
                    }                
                }
            }                  
        }
    }
    public void fillTableOOS() {
        tkDAO = new ThongKeDAO();
        tblModelOOS = (DefaultTableModel) tblListOOS.getModel();
        tblModelOOS.setRowCount(0);
        // Lọc theo mã loại SP
        LoaiSanPham lsp = (LoaiSanPham) cbxFilterOOS.getSelectedItem();
        if(lsp != null) {
            List<Object[]> obj = tkDAO.getProductOOS(lsp.getMaLoaiSP());
            if(obj.size() > 0) {
                for(Object[] row : obj) {
                    tblModelOOS.addRow(row);
                }            
            }           
        }
    }
    public void fillTableStopTrade() {
        // Định dạng tiền
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        // Lọc theo mã loại SP
        LoaiSanPham lsp = (LoaiSanPham) cbxStopTrade.getSelectedItem();
        if(lsp != null) {
            tblModelStopTrade = (DefaultTableModel) tblStopTrade.getModel();
            while(tblModelStopTrade.getRowCount() > 0) {
                tblModelStopTrade.removeRow(0);
            }
            lstStopTrade = new ArrayList<>();
            spDAO = new SanPhamDAO();
            lstSP = spDAO.getProductStopTrade(lsp.getMaLoaiSP());  
            if(!lstSP.isEmpty()) {
                for(SanPham sp : lstSP) {
                    tblModelStopTrade.addRow(new Object[]{sp.getMaSP(), sp.getTenSP(),
                        formatter.format(sp.getGia()) + " VNĐ", sp.getSize()});                                   
                }
            } else {
                showMessage("Không có sản phẩm ngừng kinh doanh");
//                MsgBox.alert(this, "Chưa có sản phẩm nào ngừng kinh doanh thuộc loại giày " + lsp.getTenLoaiSP());
            }          
        }
        
    }
    public void fillTableSeller() {
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        tkDAO = new ThongKeDAO();
        tblModelSeller = (DefaultTableModel) tblListSeller.getModel();
        tblModelSeller.setRowCount(0);
        // Lọc theo mã loại SP
        LoaiSanPham lsp = (LoaiSanPham) cbxSeller.getSelectedItem();
        if(lsp != null) {           
            List<Object[]> obj = tkDAO.getProductSeller(lsp.getMaLoaiSP());
            if(obj.size() > 0) {
                for(Object[] row : obj) {
                    tblModelSeller.addRow(new Object[]{row[0], row[1],
                        formatter.format(row[2]) + " VNĐ", row[3], row[4]});
                }            
            }
        }
        
    }
    public SanPham getForm() {
        SanPham sp = new SanPham();
        LoaiSanPham lsp = (LoaiSanPham) cbxKindProduct.getSelectedItem();
        sp.setTenSP(txtNameProduct.getText());
        sp.setMaLoaiSP(lsp.getMaLoaiSP());
//        sp.setMaNV(Auth.user.getMaNV());
        sp.setGia(Float.parseFloat(txtPrice.getText()));
        sp.setNgayBan(XDate.convertDate(txtDateEnter.getText(), "dd-MM-yyyy"));
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
    public void setForm(SanPham sp) {
        // Đỗ dữ liệu loại sản phẩm của sản phẩm đó thuộc về lên combo box 
        lspDAO = new LoaiSanPhamDAO();
        cbxModelLSP = (DefaultComboBoxModel) cbxKindProduct.getModel();
        cbxModelLSP.removeAllElements();
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
        if(sp.getNgayBan()== null) {
            txtDateEnter.setText(XDate.toString(XDate.now(), "dd-MM-yyyy"));         
        } else {
//            txtDateEnter.setText("");
        }
        txtDescription.setText(sp.getMoTa());
        cbxModelLSP.addElement(lspDAO.selectByID(sp.getMaLoaiSP()));
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
    public void clearForm() {
        SanPham sp = new SanPham();
        this.setForm(sp);
        fillCbx();
        initErr();
        initTextFields();
        lblImgProduct.setToolTipText(null);
    }
    public void edit() {
        int count = tblList.getRowCount();
        if(count > 0 ) {
                spDAO = new SanPhamDAO();
                int index = tblList.getSelectedRow();
                if(index >= 0) {
                    String getMaSP = (String)tblList.getValueAt(index, 0);
                    SanPham sp = spDAO.selectByID(getMaSP);
                    CapNhapSanPham update = new CapNhapSanPham(null, true);
                    update.setForm(sp);
                    update.setVisible(true);
                    fillTable();                  
                }
//                this.setForm(sp);
//                lblDragAndDrop.setVisible(false);
//                navShowProduct();
        } else {
            // TODO CODE
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
    public void insert() {
        int rowEffect = 0;
        spDAO = new SanPhamDAO();
        rowEffect = spDAO.insert(this.getForm());
        if(rowEffect > 0) {
            fillTable();
            clearForm();
            showMessage("Thêm sản phẩm thành công");
        } else {
            showMessage("Thêm sản phẩm thất bại");
        }
    }
    public void delete() {
        int count = tblList.getRowCount();
        if(count > 0 ) {
                spDAO = new SanPhamDAO();
                int []rows = tblList.getSelectedRows();  
                if(rows.length > 0 && MsgBox.confirm(this, "Bạn có muốn ngừng kinh doanh những sản phẩm này?")){
                for(int row : rows) {
                    String getMaSP = (String) tblList.getValueAt(row, 0);
                    spDAO.updateStatus(getMaSP);
                }
                this.fillTable();
                this.fillTableStopTrade();
                showMessage("Thành công");
            }
        } else {
            // TODO CODE
        }
    }
    public void continuteToSell() {
        int count = tblStopTrade.getRowCount();
        if(count > 0 ) {
                spDAO = new SanPhamDAO();
                int []rows = tblStopTrade.getSelectedRows();  
                if(rows.length > 0 && MsgBox.confirm(this, "Bạn có muốn tiếp tục kinh doanh những sản phẩm này?")){
                for(int row : rows) {
                    String getMaSP = (String) tblStopTrade.getValueAt(row, 0);
                    spDAO.updateSelling(getMaSP);
                }
                this.fillTableStopTrade();
                this.fillTable();
                showMessage("Thành công");
            }
        } else {
            // TODO CODE
        }
    }
    public void addKindsProduct() {
        new LoaiSanPhamForm(null,true).setVisible(true);
        fillCbx();
        fillCbxAll();       
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlNav = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        navList = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblIconList = new javax.swing.JLabel();
        lblNavList = new javax.swing.JLabel();
        navProduct = new javax.swing.JPanel();
        lblProduct = new javax.swing.JLabel();
        lblIconProduct = new javax.swing.JLabel();
        lblNavProduct = new javax.swing.JLabel();
        navSeller = new javax.swing.JPanel();
        lblBestSeller = new javax.swing.JLabel();
        lblIconBestSeller = new javax.swing.JLabel();
        pnlBestSeller = new javax.swing.JLabel();
        navOutOfStock = new javax.swing.JPanel();
        lblOutOfStock = new javax.swing.JLabel();
        lblIconOutOfStock = new javax.swing.JLabel();
        pnlOutOfStock = new javax.swing.JLabel();
        navStopTrade = new javax.swing.JPanel();
        lblStopTrade = new javax.swing.JLabel();
        lblIconTrade = new javax.swing.JLabel();
        pnlStopTrade = new javax.swing.JLabel();
        bgNavLeft = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        layeredPane = new javax.swing.JLayeredPane();
        pnlProduct = new javax.swing.JPanel();
        lblNameProduct = new javax.swing.JLabel();
        lblKindProduct = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblSize = new javax.swing.JLabel();
        lblExpiry = new javax.swing.JLabel();
        lblDateEnter = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        txtSize = new javax.swing.JTextField();
        txtExpiry = new javax.swing.JTextField();
        txtDateEnter = new javax.swing.JTextField();
        txtNameProduct = new javax.swing.JTextField();
        lblErrNameProduct = new javax.swing.JLabel();
        lblErrSize = new javax.swing.JLabel();
        cbxKindProduct = new javax.swing.JComboBox<String>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        lblErrPrice = new javax.swing.JLabel();
        lblErrExpiry = new javax.swing.JLabel();
        pnlImgProduct = new javax.swing.JPanel();
        lblDragAndDrop = new javax.swing.JLabel();
        lblImgProduct = new javax.swing.JLabel();
        lblClear = new javax.swing.JLabel();
        lblAdd = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblAddKindProduct = new javax.swing.JLabel();
        lblBgProduct = new javax.swing.JLabel();
        pnlList = new javax.swing.JPanel();
        lblFilter = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cbxFilter = new javax.swing.JComboBox<String>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        cbxFilterPrice = new javax.swing.JComboBox<String>();
        lblStopBussiness = new javax.swing.JLabel();
        lblEdit = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlSeller = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblListSeller = new javax.swing.JTable();
        cbxSeller = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        pnlOOS = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        cbxFilterOOS = new javax.swing.JComboBox<String>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblListOOS = new javax.swing.JTable();
        lblWareHouse = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        pnlST = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblStopTrade = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        cbxStopTrade = new javax.swing.JComboBox();
        jSeparator4 = new javax.swing.JSeparator();
        lblContinuteSell = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlNav.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("SẢN PHẨM");
        pnlNav.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));
        pnlNav.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 160, 10));

        navList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("DANH SÁCH");
        navList.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(69, 10, 80, 30));

        lblIconList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list-product.png"))); // NOI18N
        navList.add(lblIconList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        lblNavList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblNavList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNavListMouseClicked(evt);
            }
        });
        navList.add(lblNavList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        pnlNav.add(navList, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 160, 50));

        navProduct.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProduct.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblProduct.setForeground(new java.awt.Color(255, 255, 255));
        lblProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProduct.setText("SẢN PHẨM");
        navProduct.add(lblProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 20, 70, -1));

        lblIconProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sneaker-4.png"))); // NOI18N
        navProduct.add(lblIconProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        lblNavProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblNavProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNavProductMouseClicked(evt);
            }
        });
        navProduct.add(lblNavProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        pnlNav.add(navProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 160, 50));

        navSeller.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBestSeller.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblBestSeller.setForeground(new java.awt.Color(255, 255, 255));
        lblBestSeller.setText("BÁN CHẠY");
        navSeller.add(lblBestSeller, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 70, -1));

        lblIconBestSeller.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconBestSeller.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/best-seller.png"))); // NOI18N
        navSeller.add(lblIconBestSeller, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        pnlBestSeller.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        pnlBestSeller.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBestSellerMouseClicked(evt);
            }
        });
        navSeller.add(pnlBestSeller, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        pnlNav.add(navSeller, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 160, 50));

        navOutOfStock.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblOutOfStock.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblOutOfStock.setForeground(new java.awt.Color(255, 255, 255));
        lblOutOfStock.setText("SẮP HẾT HÀNG");
        navOutOfStock.add(lblOutOfStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 14, -1, 20));

        lblIconOutOfStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconOutOfStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/out-of-stock.png"))); // NOI18N
        navOutOfStock.add(lblIconOutOfStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        pnlOutOfStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        pnlOutOfStock.setToolTipText("");
        pnlOutOfStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlOutOfStockMouseClicked(evt);
            }
        });
        navOutOfStock.add(pnlOutOfStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        pnlNav.add(navOutOfStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 160, 50));

        navStopTrade.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblStopTrade.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStopTrade.setForeground(new java.awt.Color(255, 255, 255));
        lblStopTrade.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStopTrade.setText("NGỪNG BÁN");
        navStopTrade.add(lblStopTrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 80, 30));

        lblIconTrade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconTrade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stop-trade.png"))); // NOI18N
        navStopTrade.add(lblIconTrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 50));

        pnlStopTrade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        pnlStopTrade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlStopTradeMouseClicked(evt);
            }
        });
        navStopTrade.add(pnlStopTrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 50));

        pnlNav.add(navStopTrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 160, 50));

        bgNavLeft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/left-nav.png"))); // NOI18N
        pnlNav.add(bgNavLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 470));

        getContentPane().add(pnlNav, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 470));

        layeredPane.setLayout(new java.awt.CardLayout());

        pnlProduct.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNameProduct.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblNameProduct.setText("Tên sản phẩm");
        pnlProduct.add(lblNameProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, 20));

        lblKindProduct.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblKindProduct.setText("Loại sản phẩm");
        pnlProduct.add(lblKindProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, -1, 20));

        lblPrice.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPrice.setText("Giá bán");
        pnlProduct.add(lblPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, -1));

        lblSize.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblSize.setText("Size");
        pnlProduct.add(lblSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, -1, -1));

        lblExpiry.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblExpiry.setText("Hạn sử dụng");
        pnlProduct.add(lblExpiry, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, -1, 20));

        lblDateEnter.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDateEnter.setText("Ngày bán");
        pnlProduct.add(lblDateEnter, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, -1, -1));

        lblDescription.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDescription.setText("Mô tả");
        pnlProduct.add(lblDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, -1, -1));

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
        pnlProduct.add(txtPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 170, 30));

        txtSize.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
        pnlProduct.add(txtSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 180, 30));

        txtExpiry.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
        pnlProduct.add(txtExpiry, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 180, 30));

        txtDateEnter.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlProduct.add(txtDateEnter, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 170, 30));

        txtNameProduct.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
        pnlProduct.add(txtNameProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 170, 30));

        lblErrNameProduct.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrNameProduct.setText("Error Message");
        pnlProduct.add(lblErrNameProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 170, 20));

        lblErrSize.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrSize.setText("Error Message");
        pnlProduct.add(lblErrSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 170, 20));

        cbxKindProduct.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        pnlProduct.add(cbxKindProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 180, 30));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtDescription.setRows(5);
        jScrollPane2.setViewportView(txtDescription);

        pnlProduct.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 370, 70));

        lblErrPrice.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPrice.setText("Error Message");
        pnlProduct.add(lblErrPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 170, 20));

        lblErrExpiry.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrExpiry.setText("Error Message");
        pnlProduct.add(lblErrExpiry, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, 170, -1));

        pnlImgProduct.setBackground(new java.awt.Color(255, 255, 255));
        pnlImgProduct.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hình", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 51, 51))); // NOI18N
        pnlImgProduct.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDragAndDrop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDragAndDrop.setForeground(new java.awt.Color(4, 165, 201));
        lblDragAndDrop.setText("THẢ HÌNH VÀO ĐÂY");
        pnlImgProduct.add(lblDragAndDrop, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));

        lblImgProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImgProductMouseClicked(evt);
            }
        });
        pnlImgProduct.add(lblImgProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 17, 158, 140));

        pnlProduct.add(pnlImgProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 200, 180));

        lblClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-new.png"))); // NOI18N
        lblClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClearMouseClicked(evt);
            }
        });
        pnlProduct.add(lblClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 390, 110, 40));

        lblAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-add2.png"))); // NOI18N
        lblAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddMouseClicked(evt);
            }
        });
        pnlProduct.add(lblAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 390, 110, 40));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnlProduct.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 10, 320));

        lblAddKindProduct.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblAddKindProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAddKindProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus.png"))); // NOI18N
        lblAddKindProduct.setToolTipText("");
        lblAddKindProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAddKindProductMouseClicked(evt);
            }
        });
        pnlProduct.add(lblAddKindProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 29, 40, 30));

        lblBgProduct.setBackground(new java.awt.Color(255, 255, 255));
        lblBgProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-product2.jpg"))); // NOI18N
        pnlProduct.add(lblBgProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 470));

        layeredPane.add(pnlProduct, "card3");

        pnlList.setPreferredSize(new java.awt.Dimension(650, 470));
        pnlList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFilter.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblFilter.setForeground(new java.awt.Color(204, 0, 0));
        lblFilter.setText("Lọc: ");
        pnlList.add(lblFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 40, 40));
        pnlList.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 50, 10));

        cbxFilter.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbxFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFilterActionPerformed(evt);
            }
        });
        pnlList.add(cbxFilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 230, 40));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tblList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
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
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblListMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblList);

        pnlList.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 630, 290));

        cbxFilterPrice.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbxFilterPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFilterPriceActionPerformed(evt);
            }
        });
        pnlList.add(cbxFilterPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 230, 40));

        lblStopBussiness.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-stopBussiness.png"))); // NOI18N
        lblStopBussiness.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblStopBussinessMouseClicked(evt);
            }
        });
        pnlList.add(lblStopBussiness, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 400, -1, -1));

        lblEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-updateProduct.png"))); // NOI18N
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditMouseClicked(evt);
            }
        });
        pnlList.add(lblEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-listproduct.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        pnlList.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 470));

        layeredPane.add(pnlList, "card2");

        pnlSeller.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("Lọc:");
        pnlSeller.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, 30));
        pnlSeller.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 50, 10));

        tblListSeller.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblListSeller.setModel(new javax.swing.table.DefaultTableModel(
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
        tblListSeller.setRowHeight(30);
        tblListSeller.setSelectionBackground(new java.awt.Color(1, 157, 176));
        jScrollPane5.setViewportView(tblListSeller);

        pnlSeller.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 610, 340));

        cbxSeller.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbxSeller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSellerActionPerformed(evt);
            }
        });
        pnlSeller.add(cbxSeller, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 250, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-listproduct.jpg"))); // NOI18N
        pnlSeller.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 470));

        layeredPane.add(pnlSeller, "card4");

        pnlOOS.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Lọc:");
        pnlOOS.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 80, 30));
        pnlOOS.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 60, 10));

        cbxFilterOOS.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbxFilterOOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxFilterOOSActionPerformed(evt);
            }
        });
        pnlOOS.add(cbxFilterOOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 240, 40));

        tblListOOS.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblListOOS.setModel(new javax.swing.table.DefaultTableModel(
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
        tblListOOS.setRowHeight(30);
        tblListOOS.setSelectionBackground(new java.awt.Color(1, 157, 176));
        jScrollPane3.setViewportView(tblListOOS);

        pnlOOS.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 610, 300));

        lblWareHouse.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblWareHouse.setForeground(new java.awt.Color(255, 51, 51));
        lblWareHouse.setText("NHẬP THÊM SẢN PHẨM?");
        lblWareHouse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblWareHouseMouseClicked(evt);
            }
        });
        pnlOOS.add(lblWareHouse, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 420, -1, -1));
        pnlOOS.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 440, 170, 10));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-listproduct.jpg"))); // NOI18N
        pnlOOS.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 470));

        layeredPane.add(pnlOOS, "card5");

        pnlST.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblStopTrade.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tblStopTrade.setModel(new javax.swing.table.DefaultTableModel(
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
        tblStopTrade.setRowHeight(30);
        tblStopTrade.setSelectionBackground(new java.awt.Color(1, 157, 176));
        jScrollPane4.setViewportView(tblStopTrade);

        pnlST.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 630, 270));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setText("Lọc:");
        pnlST.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, -1, -1));

        cbxStopTrade.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbxStopTrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxStopTradeActionPerformed(evt);
            }
        });
        pnlST.add(cbxStopTrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 280, 40));
        pnlST.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 70, 20));

        lblContinuteSell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-continueBussiness.png"))); // NOI18N
        lblContinuteSell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblContinuteSellMouseClicked(evt);
            }
        });
        pnlST.add(lblContinuteSell, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-listproduct.jpg"))); // NOI18N
        pnlST.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 470));

        layeredPane.add(pnlST, "card6");

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, -1, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblNavProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavProductMouseClicked
           navShowProduct();
    }//GEN-LAST:event_lblNavProductMouseClicked

    private void lblNavListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNavListMouseClicked
         navShowList(); 
    }//GEN-LAST:event_lblNavListMouseClicked

    private void cbxFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterActionPerformed
        fillTable();
    }//GEN-LAST:event_cbxFilterActionPerformed

    private void lblAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddMouseClicked
        if(validateAll()) {
            insert();           
        }
    }//GEN-LAST:event_lblAddMouseClicked

    private void tblListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMousePressed
//        if (evt.getClickCount() == 2) {
//             edit();
//        }
    }//GEN-LAST:event_tblListMousePressed

    private void pnlStopTradeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlStopTradeMouseClicked
        navShowStopTrade();
    }//GEN-LAST:event_pnlStopTradeMouseClicked

    private void cbxStopTradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxStopTradeActionPerformed
        fillTableStopTrade();
    }//GEN-LAST:event_cbxStopTradeActionPerformed

    private void lblClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClearMouseClicked
       clearForm();
    }//GEN-LAST:event_lblClearMouseClicked

    private void lblImgProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgProductMouseClicked
        loadImg();
    }//GEN-LAST:event_lblImgProductMouseClicked

    private void pnlBestSellerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBestSellerMouseClicked
       navShowBestSeller();
    }//GEN-LAST:event_pnlBestSellerMouseClicked

    private void cbxSellerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSellerActionPerformed
        fillTableSeller();
    }//GEN-LAST:event_cbxSellerActionPerformed

    private void cbxFilterPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterPriceActionPerformed
        fillTable();
    }//GEN-LAST:event_cbxFilterPriceActionPerformed

    private void pnlOutOfStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlOutOfStockMouseClicked
       navShowOutOfStock();
    }//GEN-LAST:event_pnlOutOfStockMouseClicked

    private void cbxFilterOOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxFilterOOSActionPerformed
        fillTableOOS();
    }//GEN-LAST:event_cbxFilterOOSActionPerformed

    private void lblAddKindProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAddKindProductMouseClicked
        addKindsProduct();
    }//GEN-LAST:event_lblAddKindProductMouseClicked

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

    private void lblEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseClicked
        edit();
    }//GEN-LAST:event_lblEditMouseClicked

    private void lblStopBussinessMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblStopBussinessMouseClicked
        delete();
    }//GEN-LAST:event_lblStopBussinessMouseClicked

    private void lblContinuteSellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblContinuteSellMouseClicked
       continuteToSell();
    }//GEN-LAST:event_lblContinuteSellMouseClicked

    private void lblWareHouseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblWareHouseMouseClicked
        new KhoHangForm(null, true).setVisible(true);
        fillTableOOS();
    }//GEN-LAST:event_lblWareHouseMouseClicked

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
            java.util.logging.Logger.getLogger(SanPhamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SanPhamForm dialog = new SanPhamForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel bgNavLeft;
    private javax.swing.JComboBox<String> cbxFilter;
    private javax.swing.JComboBox<String> cbxFilterOOS;
    private javax.swing.JComboBox<String> cbxFilterPrice;
    private javax.swing.JComboBox<String> cbxKindProduct;
    private javax.swing.JComboBox<String> cbxSeller;
    private javax.swing.JComboBox cbxStopTrade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLayeredPane layeredPane;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblAddKindProduct;
    private javax.swing.JLabel lblBestSeller;
    private javax.swing.JLabel lblBgProduct;
    private javax.swing.JLabel lblClear;
    private javax.swing.JLabel lblContinuteSell;
    private javax.swing.JLabel lblDateEnter;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblDragAndDrop;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblErrExpiry;
    private javax.swing.JLabel lblErrNameProduct;
    private javax.swing.JLabel lblErrPrice;
    private javax.swing.JLabel lblErrSize;
    private javax.swing.JLabel lblExpiry;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JLabel lblIconBestSeller;
    private javax.swing.JLabel lblIconList;
    private javax.swing.JLabel lblIconOutOfStock;
    private javax.swing.JLabel lblIconProduct;
    private javax.swing.JLabel lblIconTrade;
    private javax.swing.JLabel lblImgProduct;
    private javax.swing.JLabel lblKindProduct;
    private javax.swing.JLabel lblNameProduct;
    private javax.swing.JLabel lblNavList;
    private javax.swing.JLabel lblNavProduct;
    private javax.swing.JLabel lblOutOfStock;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblProduct;
    private javax.swing.JLabel lblSize;
    private javax.swing.JLabel lblStopBussiness;
    private javax.swing.JLabel lblStopTrade;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblWareHouse;
    private javax.swing.JPanel navList;
    private javax.swing.JPanel navOutOfStock;
    private javax.swing.JPanel navProduct;
    private javax.swing.JPanel navSeller;
    private javax.swing.JPanel navStopTrade;
    private javax.swing.JLabel pnlBestSeller;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlImgProduct;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlNav;
    private javax.swing.JPanel pnlOOS;
    private javax.swing.JLabel pnlOutOfStock;
    private javax.swing.JPanel pnlProduct;
    private javax.swing.JPanel pnlST;
    private javax.swing.JPanel pnlSeller;
    private javax.swing.JLabel pnlStopTrade;
    private javax.swing.JTable tblList;
    private javax.swing.JTable tblListOOS;
    private javax.swing.JTable tblListSeller;
    private javax.swing.JTable tblStopTrade;
    private javax.swing.JTextField txtDateEnter;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtExpiry;
    private javax.swing.JTextField txtNameProduct;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtSize;
    // End of variables declaration//GEN-END:variables
}
