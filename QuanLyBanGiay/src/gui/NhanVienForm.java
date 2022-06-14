/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.NhanVienDAO;
import java.awt.Color;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import modal.NhanVien;
import utils.DragAndDrop;
import utils.MsgBox;
import utils.Validator;
import utils.XDate;
import utils.XImg;


/**
 *
 * @author DELL
 */
public class NhanVienForm extends javax.swing.JDialog {

    NhanVien nv;
    NhanVienDAO nvDAO;
    DefaultTableModel tblModel;
    List<NhanVien> lstNV;
    JFileChooser fileChooser;
    String placeHolder = "";
    int index = 0;
    int row = -1;
    boolean hiddenText = true;
    public NhanVienForm(java.awt.Frame parent, boolean modal) {
         super(parent, modal);
         initComponents();
         setLocationRelativeTo(null);
         setTitle("Quản lý nhân viên");
         designTable();
         initColumnIdentity();
         updateStatus();
         fillTable();
         setPlaceHolder();
         dragAndDropImg();
         initErr();
    }
    public void dragAndDropImg() {
        DragAndDrop dad = new DragAndDrop(lblAvatar, lblDragAndDrop);
        new DropTarget(this, dad);      
    }
    public void setPlaceHolder() {
        txtSearch.setText(placeHolder);
        txtSearch.setForeground(new java.awt.Color(170, 170, 170));
    }
    public void designTable() {
        tblList.getTableHeader().setFont(new java.awt.Font("Tahoma", 1, 12));
        tblList.getTableHeader().setForeground(new java.awt.Color(32,136,203));
    }
    public void initColumnIdentity() {
        tblModel = (DefaultTableModel) tblList.getModel();
        tblModel.setColumnIdentifiers(new Object[]{"Họ tên", "SĐT", "Email", "Giới tính",
        "Địa chỉ", "Tài khoản", "Mật khẩu", "Trạng thái"});
    }
    public void showMessage(String message) {
            ThongBaoForm tbForm = new ThongBaoForm(null, true);
            tbForm.setMessage(message);
            tbForm.setVisible(true);
    }
    public void initErr() {
        lblErrUser.setText(null);
        lblErrFN.setText(null);
        lblErrPhone.setText(null);
        lblErrAddress.setText(null);
        lblErrBirth.setText(null);
        lblErrEmail.setText(null);
        lblErrPw.setText(null);
        lblErrGenger.setText(null);
        lblErrRole.setText(null);
    }
    public void initTextFields() {
        txtUser.setBorder(new javax.swing.JTextField().getBorder());
        txtFullName.setBorder(new javax.swing.JTextField().getBorder());
        txtPhone.setBorder(new javax.swing.JTextField().getBorder());
        txtAddress.setBorder(new javax.swing.JTextField().getBorder());
        txtBirth.setBorder(new javax.swing.JTextField().getBorder());
        txtEmail.setBorder(new javax.swing.JTextField().getBorder());
        txtPw.setBorder(new javax.swing.JTextField().getBorder());
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(new javax.swing.JTextField().getBorder());
        label.setText(null);
    } 
    /* Validate */
    public boolean validateUser() {
        return Validator.isEmptyUser(txtUser, lblErrUser);
    }
    public boolean validateName() {
        return Validator.isName(txtFullName, lblErrFN);
    }
    public boolean validateEmail() {
        return Validator.isEmail(txtEmail, lblErrEmail);
    }
    public boolean validateBirth() {
        return Validator.isBirth(txtBirth, lblErrBirth);
    }
    public boolean validatePw() {
        return Validator.isEmptyPw(txtPw, lblErrPw);
    }
    public boolean validatePhone() {
        return Validator.isPhone(txtPhone, lblErrPhone);
    }
    public boolean validateAddress() {
        return Validator.isAddress(txtAddress, lblErrAddress);
    }
    public boolean validateGenger() {
        return Validator.isEmptyGenger(rdoMale, rdoFemale, lblErrGenger);
    }
    public boolean validateRole() {
        return Validator.isEmptyRole(rdoEmp, rdoManage, lblErrRole);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(validateUser()) {
            isFormValid = false;
        }
        if(validateName()) {
            isFormValid = false;
        }
        if(validateEmail()) {
            isFormValid = false;
        }
        if(validateBirth()) {
            isFormValid = false;
        }
        if(validatePw()) {
            isFormValid = false;
        }
        if(validatePhone()) {
            isFormValid = false;
        }
        if(validateAddress()) {
            isFormValid = false;
        }
        if(validateGenger()) {
            isFormValid = false;
        }
        if(validateRole()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public boolean validateUpdate() {
        boolean isFormValid = true;
        if(validateName()) {
            isFormValid = false;
        }
        if(validateEmail()) {
            isFormValid = false;
        }
        if(validateBirth()) {
            isFormValid = false;
        }
        if(validatePw()) {
            isFormValid = false;
        }
        if(validatePhone()) {
            isFormValid = false;
        }
        if(validateAddress()) {
            isFormValid = false;
        }
        if(validateGenger()) {
            isFormValid = false;
        }
        if(validateRole()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public void fillTable() {
        lstNV = new ArrayList<>();
        nv = new NhanVien();
        nvDAO = new NhanVienDAO();
        tblModel = (DefaultTableModel) tblList.getModel();
        while(tblModel.getRowCount() > 0) {
            tblModel.removeRow(0);
        }
        try {
            lstNV = nvDAO.selectByName(txtSearch.getText());
            if(lstNV != null) {
                for(NhanVien nv : lstNV) {
                    tblModel.addRow(new Object[]{nv.getHoTen(), nv.getDienThoai(),
                    nv.getEmail(), nv.isGioiTinh() ? "Nam" : "Nữ",
                    nv.getDiaChi(), nv.getTaiKhoan(), nv.getMatKhau(), nv.getTrangThai()});
                }               
            } else {
//                MsgBox.alert(this, "Chưa có nhân viên nào hiện đang làm việc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public NhanVien getForm() {
        nv = new NhanVien();
        int count = tblList.getRowCount();
        if(count > 0 ) {
            this.index = tblList.getSelectedRow();
            if(this.index >= 0) {
                NhanVien nVien = lstNV.get(this.index);
                nv.setMaNV(nVien.getMaNV());
            }
        } else {
            // TODO CODE
        }
        nv.setHoTen(txtFullName.getText());
        nv.setTaiKhoan(txtUser.getText());
        nv.setMatKhau(new String(txtPw.getPassword()));
        nv.setDienThoai(txtPhone.getText());
        nv.setEmail(txtEmail.getText());
        nv.setNgaySinh(XDate.convertDate(txtBirth.getText(), "dd-MM-yyyy"));
        nv.setDiaChi(txtAddress.getText());
        nv.setGioiTinh(rdoMale.isSelected());
        nv.setVaiTro(rdoManage.isSelected());
        nv.setAvatar(lblAvatar.getToolTipText());
        return nv;
    }
    public void setForm(NhanVien nv) {
        txtFullName.setText(nv.getHoTen());
        txtUser.setText(nv.getTaiKhoan());
        txtPw.setText(nv.getMatKhau());
        txtPhone.setText(nv.getDienThoai());
        txtEmail.setText(nv.getEmail());
        if(nv.getNgaySinh() == null) {
            txtBirth.setText("");
        } else {
        txtBirth.setText(XDate.toString(nv.getNgaySinh(),"dd-MM-yyyy"));            
        }
        txtAddress.setText(nv.getDiaChi());
        rdoMale.setSelected(nv.isGioiTinh());
        rdoFemale.setSelected(!nv.isGioiTinh());
        rdoManage.setSelected(nv.isVaiTro());
        rdoEmp.setSelected(!nv.isVaiTro());
        if(nv.getAvatar() != null) {
            lblAvatar.setToolTipText(nv.getAvatar());
            ImageIcon iconCore = XImg.read(lblAvatar.getToolTipText());
            Image img = iconCore.getImage();
            ImageIcon iconParse = new ImageIcon(img.getScaledInstance(lblAvatar.getWidth(),
                    lblAvatar.getHeight(), Image.SCALE_SMOOTH));
            lblAvatar.setIcon(iconParse);            
        } else {
            lblAvatar.setIcon(null);
        }
        // Làm sạch lại toolTipText trong lblLogo
        // Tránh trường hợp khi bấm vào 1 khóa học còn lưu trữ dữ liệu trong toolTipText ở lblLogo
//          lblAvatar.setToolTipText(null);     
    }
    public void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
        groupGenger.clearSelection();
        groupRole.clearSelection();
        this.row = -1;
        this.index = 0;
        this.hiddenText = true;
        this.updateStatus();
        lblAvatar.setToolTipText(null);
        initErr();
        initTextFields();
    }
    public void updateStatus() {
        boolean edit = (this.row >= 0);
        txtUser.setEditable(!edit);
        // Add
        lblBgAdd.setEnabled(!edit);
        lblAdd.setEnabled(!edit);
        lblIconAdd.setEnabled(!edit);
        // Update
        lblBgUpdate.setEnabled(edit);
        lblIconUpdate.setEnabled(edit);
        lblUpdate.setEnabled(edit);
        // Delete
        lblDelete.setEnabled(edit);
        lblIconDelete.setEnabled(edit);
        lblBgDelete.setEnabled(edit);
        //
        lblDragAndDrop.setVisible(hiddenText);
    }
    public void edit() {
        int count = tblList.getRowCount();
        if(count > 0 ) {
                this.index = tblList.getSelectedRow();  
                if(index >= 0) {
                    NhanVien nv = lstNV.get(this.index);                
                    this.setForm(nv);
                    tabs.setSelectedIndex(0);
                    this.hiddenText = false;
                    this.updateStatus();       
                    initErr();
                    initTextFields();
                }
        } else {
            // TODO CODE
        }
    }
    public void loadAvatar() {
        fileChooser = new JFileChooser();
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImg.save(file);
            ImageIcon iconCore = XImg.read(file.getName());
            Image img = iconCore.getImage();
            ImageIcon iconParsed = new ImageIcon(img.getScaledInstance(lblAvatar.getWidth(),
                    lblAvatar.getHeight(),
                    Image.SCALE_SMOOTH));
            lblAvatar.setIcon(iconParsed);
            lblAvatar.setToolTipText(file.getName());
            lblDragAndDrop.setVisible(false);
        }
    }
    public void insert() {
        int rowEffect = 0;
        nvDAO = new NhanVienDAO();
        rowEffect = nvDAO.insert(this.getForm());
        if(rowEffect > 0) {
            fillTable();
            showMessage("Thêm nhân viên thành công");
            clearForm();
        } else {
            // TODO CODE
        }
    }
    public void update() {
        int rowEffect = 0;
        nvDAO = new NhanVienDAO();
        rowEffect = nvDAO.update(this.getForm());
        if(rowEffect > 0) {
            fillTable();
            showMessage("Chỉnh sửa thông tin thành công");
            clearForm();
        } else {
            // TODO CODE
        }
    }
    public void delete() {
        if(MsgBox.confirm(this, "Xác nhận xóa nhân viên " + this.getForm().getHoTen())) {
            int rowEffect = 0;
            nvDAO = new NhanVienDAO();
            rowEffect = nvDAO.delete(this.getForm().getMaNV());
            if(rowEffect > 0) {
                fillTable();
                clearForm();
                showMessage("Xóa thành công");
            } else {
                // TODO CODE
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

        groupGenger = new javax.swing.ButtonGroup();
        groupRole = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        tabUpdate = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        pnlContent = new javax.swing.JPanel();
        lblFullName = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblPw = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblGenger = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblBirth = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtBirth = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtPw = new javax.swing.JPasswordField();
        lblErrFN = new javax.swing.JLabel();
        lblErrEmail = new javax.swing.JLabel();
        lblErrUser = new javax.swing.JLabel();
        lblErrBirth = new javax.swing.JLabel();
        lblErrRole = new javax.swing.JLabel();
        lblErrPw = new javax.swing.JLabel();
        lblErrPhone = new javax.swing.JLabel();
        lblErrAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        pnlGenger = new javax.swing.JPanel();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        pnlRole = new javax.swing.JPanel();
        rdoEmp = new javax.swing.JRadioButton();
        rdoManage = new javax.swing.JRadioButton();
        lblErrGenger = new javax.swing.JLabel();
        pnlAvatar = new javax.swing.JPanel();
        lblDragAndDrop = new javax.swing.JLabel();
        lblAvatar = new javax.swing.JLabel();
        pnlFooter = new javax.swing.JPanel();
        pnlAdd = new javax.swing.JPanel();
        lblAdd = new javax.swing.JLabel();
        lblIconAdd = new javax.swing.JLabel();
        lblBgAdd = new javax.swing.JLabel();
        pnlClear = new javax.swing.JPanel();
        lblClear = new javax.swing.JLabel();
        lblIconClear = new javax.swing.JLabel();
        lblBgClear = new javax.swing.JLabel();
        pnlUpdate = new javax.swing.JPanel();
        lblUpdate = new javax.swing.JLabel();
        lblIconUpdate = new javax.swing.JLabel();
        lblBgUpdate = new javax.swing.JLabel();
        pnlDelete = new javax.swing.JPanel();
        lblDelete = new javax.swing.JLabel();
        lblIconDelete = new javax.swing.JLabel();
        lblBgDelete = new javax.swing.JLabel();
        tabList = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabs.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        tabUpdate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tabUpdate.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlContent.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFullName.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblFullName.setText("Họ tên");
        pnlContent.add(lblFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 70, -1));

        lblUser.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblUser.setText("Tài khoản");
        pnlContent.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 77, -1));

        lblPw.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPw.setText("Mật khẩu");
        pnlContent.add(lblPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 180, 70, -1));

        lblAddress.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblAddress.setText("Địa chỉ");
        pnlContent.add(lblAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 70, -1));

        lblGenger.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblGenger.setText("Giới tính");
        pnlContent.add(lblGenger, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, 70, -1));

        lblPhone.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblPhone.setText("SĐT");
        pnlContent.add(lblPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, 70, -1));

        lblBirth.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblBirth.setText("Ngày sinh");
        pnlContent.add(lblBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 70, -1));

        lblRole.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblRole.setText("Vai trò");
        pnlContent.add(lblRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 70, 20));

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblEmail.setText("Email");
        pnlContent.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 20, 70, -1));

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
        pnlContent.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 190, 30));

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
        pnlContent.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 200, 30));

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
        pnlContent.add(txtBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 200, 30));

        txtUser.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUserFocusLost(evt);
            }
        });
        txtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserKeyPressed(evt);
            }
        });
        pnlContent.add(txtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 190, 30));

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
        pnlContent.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, 190, 30));

        txtPw.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPw.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPwFocusLost(evt);
            }
        });
        txtPw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPwKeyPressed(evt);
            }
        });
        pnlContent.add(txtPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 190, 30));

        lblErrFN.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrFN.setText("Error Message");
        pnlContent.add(lblErrFN, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 190, -1));

        lblErrEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrEmail.setText("Error Message");
        pnlContent.add(lblErrEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 190, -1));

        lblErrUser.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrUser.setText("Error Message");
        pnlContent.add(lblErrUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 190, -1));

        lblErrBirth.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrBirth.setText("Error Message");
        pnlContent.add(lblErrBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 190, -1));

        lblErrRole.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrRole.setText("Error Message");
        pnlContent.add(lblErrRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 200, -1));

        lblErrPw.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPw.setText("Error Message");
        pnlContent.add(lblErrPw, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 190, -1));

        lblErrPhone.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrPhone.setText("Error Message");
        pnlContent.add(lblErrPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 190, -1));

        lblErrAddress.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrAddress.setText("Error Message");
        pnlContent.add(lblErrAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 370, -1));

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
        pnlContent.add(txtAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 420, 30));

        pnlGenger.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        groupGenger.add(rdoMale);
        rdoMale.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoMale.setText("Nam");
        rdoMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMaleActionPerformed(evt);
            }
        });

        groupGenger.add(rdoFemale);
        rdoFemale.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoFemale.setText("Nữ");
        rdoFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGengerLayout = new javax.swing.GroupLayout(pnlGenger);
        pnlGenger.setLayout(pnlGengerLayout);
        pnlGengerLayout.setHorizontalGroup(
            pnlGengerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGengerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdoFemale)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGengerLayout.setVerticalGroup(
            pnlGengerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGengerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rdoFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(rdoMale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContent.add(pnlGenger, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, 200, 30));

        pnlRole.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        groupRole.add(rdoEmp);
        rdoEmp.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoEmp.setText("Nhân viên");
        rdoEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoEmpActionPerformed(evt);
            }
        });

        groupRole.add(rdoManage);
        rdoManage.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rdoManage.setText("Quản lý");
        rdoManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoEmpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRoleLayout = new javax.swing.GroupLayout(pnlRole);
        pnlRole.setLayout(pnlRoleLayout);
        pnlRoleLayout.setHorizontalGroup(
            pnlRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoleLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(rdoEmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoManage, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlRoleLayout.setVerticalGroup(
            pnlRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoleLayout.createSequentialGroup()
                .addGroup(pnlRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoManage))
                .addGap(4, 4, 4))
        );

        pnlContent.add(pnlRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 200, 30));

        lblErrGenger.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblErrGenger.setText("Error Message");
        pnlContent.add(lblErrGenger, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 190, 20));

        pnlAvatar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hình", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(204, 51, 0))); // NOI18N
        pnlAvatar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDragAndDrop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDragAndDrop.setForeground(new java.awt.Color(4, 165, 201));
        lblDragAndDrop.setText("  KÉO HÌNH ẢNH VÀO ĐÂY");
        pnlAvatar.add(lblDragAndDrop, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        lblAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAvatarMouseClicked(evt);
            }
        });
        pnlAvatar.add(lblAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 37, 168, 135));

        pnlContent.add(pnlAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, 230));

        pnlHeader.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 430));

        tabUpdate.add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 690, -1));

        pnlFooter.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlFooter.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlAdd.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAdd.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblAdd.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAdd.setText("Thêm");
        lblAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lblAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 60, -1));

        lblIconAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-add.png"))); // NOI18N
        lblIconAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lblIconAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, 120, 40));

        lblBgAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-create.png"))); // NOI18N
        lblBgAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgAddMouseClicked(evt);
            }
        });
        pnlAdd.add(lblBgAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 80));

        pnlFooter.add(pnlAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, 85));

        pnlClear.setPreferredSize(new java.awt.Dimension(119, 67));
        pnlClear.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblClear.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblClear.setForeground(new java.awt.Color(255, 255, 255));
        lblClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClear.setText("Mới");
        lblClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgClearMouseClicked(evt);
            }
        });
        pnlClear.add(lblClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 50, 40, -1));

        lblIconClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-clear.png"))); // NOI18N
        lblIconClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgClearMouseClicked(evt);
            }
        });
        pnlClear.add(lblIconClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 4, 90, 40));

        lblBgClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-new.png"))); // NOI18N
        lblBgClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgClearMouseClicked(evt);
            }
        });
        pnlClear.add(lblBgClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 140, 80));

        pnlFooter.add(pnlClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 80));

        pnlUpdate.setPreferredSize(new java.awt.Dimension(119, 4));
        pnlUpdate.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUpdate.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblUpdate.setForeground(new java.awt.Color(255, 255, 255));
        lblUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUpdate.setText("Cập nhập");
        lblUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgUpdateMouseClicked(evt);
            }
        });
        pnlUpdate.add(lblUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 50, 90, -1));

        lblIconUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-update.png"))); // NOI18N
        lblIconUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgUpdateMouseClicked(evt);
            }
        });
        pnlUpdate.add(lblIconUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 100, 50));

        lblBgUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-update.png"))); // NOI18N
        lblBgUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgUpdateMouseClicked(evt);
            }
        });
        pnlUpdate.add(lblBgUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 80));

        pnlFooter.add(pnlUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 140, 80));

        pnlDelete.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDelete.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblDelete.setForeground(new java.awt.Color(255, 255, 255));
        lblDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDelete.setText("Xóa");
        pnlDelete.add(lblDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 50, -1));

        lblIconDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-delete.png"))); // NOI18N
        pnlDelete.add(lblIconDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 110, 50));

        lblBgDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-delete.png"))); // NOI18N
        lblBgDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgDeleteMouseClicked(evt);
            }
        });
        pnlDelete.add(lblBgDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 80));

        pnlFooter.add(pnlDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 140, 80));

        tabUpdate.add(pnlFooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 690, 100));

        tabs.addTab("Cập nhập", tabUpdate);

        tabList.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm:");

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblListMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabListLayout = new javax.swing.GroupLayout(tabList);
        tabList.setLayout(tabListLayout);
        tabListLayout.setHorizontalGroup(
            tabListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabListLayout.setVerticalGroup(
            tabListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Danh sách", tabList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        fillTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void lblBgClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBgClearMouseClicked
       clearForm();
    }//GEN-LAST:event_lblBgClearMouseClicked

    private void lblBgAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBgAddMouseClicked
        if(validateAll()) {
            insert();         
        }
    }//GEN-LAST:event_lblBgAddMouseClicked

    private void lblBgUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBgUpdateMouseClicked
        if(validateUpdate()) {
            update();            
        }
    }//GEN-LAST:event_lblBgUpdateMouseClicked

    private void lblBgDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBgDeleteMouseClicked
        delete();
    }//GEN-LAST:event_lblBgDeleteMouseClicked

    private void lblAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvatarMouseClicked
        loadAvatar();
    }//GEN-LAST:event_lblAvatarMouseClicked

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped
        if(txtSearch.getText().equalsIgnoreCase(placeHolder)) {
            txtSearch.setText("");
            txtSearch.setForeground(Color.black);
        } else if(txtSearch.getText().equalsIgnoreCase("")) {
            txtSearch.setText(placeHolder);
            txtSearch.setForeground(new java.awt.Color(170, 170, 170));
        }
        
    }//GEN-LAST:event_txtSearchKeyTyped

    private void tblListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMousePressed
            this.row = tblList.rowAtPoint(evt.getPoint());
            edit();
    }//GEN-LAST:event_tblListMousePressed

    private void txtFullNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFullNameFocusLost
       validateName();
    }//GEN-LAST:event_txtFullNameFocusLost

    private void txtUserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserFocusLost
        validateUser();
    }//GEN-LAST:event_txtUserFocusLost

    private void txtPwFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPwFocusLost
        validatePw();
    }//GEN-LAST:event_txtPwFocusLost

    private void txtPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusLost
        validatePhone();
    }//GEN-LAST:event_txtPhoneFocusLost

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        validateEmail();
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtBirthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBirthFocusLost
        validateBirth();
    }//GEN-LAST:event_txtBirthFocusLost

    private void txtAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAddressFocusLost
        validateAddress();
    }//GEN-LAST:event_txtAddressFocusLost

    private void rdoMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMaleActionPerformed
       if(rdoMale.isSelected() || rdoFemale.isSelected()) {
           lblErrGenger.setText(null);
       }
    }//GEN-LAST:event_rdoMaleActionPerformed

    private void rdoEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoEmpActionPerformed
       if(rdoEmp.isSelected() || rdoManage.isSelected()) {
           lblErrRole.setText(null);
       }
    }//GEN-LAST:event_rdoEmpActionPerformed

    private void txtFullNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFullNameKeyPressed
        setNullErr(txtFullName, lblErrFN);
    }//GEN-LAST:event_txtFullNameKeyPressed

    private void txtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserKeyPressed

        setNullErr(txtUser, lblErrUser);
    }//GEN-LAST:event_txtUserKeyPressed

    private void txtPwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPwKeyPressed
        setNullErr(txtPw, lblErrPw);

    }//GEN-LAST:event_txtPwKeyPressed

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        setNullErr(txtPhone, lblErrPhone);

    }//GEN-LAST:event_txtPhoneKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        setNullErr(txtEmail, lblErrEmail);
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtBirthKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBirthKeyPressed
        setNullErr(txtBirth, lblErrBirth);
    }//GEN-LAST:event_txtBirthKeyPressed

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
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new NhanVienForm().setVisible(true);
                NhanVienForm dialog = new NhanVienForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnSearch;
    private javax.swing.ButtonGroup groupGenger;
    private javax.swing.ButtonGroup groupRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblBgAdd;
    private javax.swing.JLabel lblBgClear;
    private javax.swing.JLabel lblBgDelete;
    private javax.swing.JLabel lblBgUpdate;
    private javax.swing.JLabel lblBirth;
    private javax.swing.JLabel lblClear;
    private javax.swing.JLabel lblDelete;
    private javax.swing.JLabel lblDragAndDrop;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblErrAddress;
    private javax.swing.JLabel lblErrBirth;
    private javax.swing.JLabel lblErrEmail;
    private javax.swing.JLabel lblErrFN;
    private javax.swing.JLabel lblErrGenger;
    private javax.swing.JLabel lblErrPhone;
    private javax.swing.JLabel lblErrPw;
    private javax.swing.JLabel lblErrRole;
    private javax.swing.JLabel lblErrUser;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblGenger;
    private javax.swing.JLabel lblIconAdd;
    private javax.swing.JLabel lblIconClear;
    private javax.swing.JLabel lblIconDelete;
    private javax.swing.JLabel lblIconUpdate;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPw;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblUpdate;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlAvatar;
    private javax.swing.JPanel pnlClear;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlDelete;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPanel pnlGenger;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlRole;
    private javax.swing.JPanel pnlUpdate;
    private javax.swing.JRadioButton rdoEmp;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JRadioButton rdoManage;
    private javax.swing.JPanel tabList;
    private javax.swing.JPanel tabUpdate;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtBirth;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JPasswordField txtPw;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
