/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import java.awt.Color;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import modal.NhanVien;
import utils.Auth;
import utils.DADUpdateEmp;
import utils.MsgBox;
import utils.Validator;
import utils.XDate;
import utils.XImg;

/**
 *
 * @author DELL
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    NhanVien nv;
    NhanVienDAO nvDAO;
    HoaDonDAO hdDAO;
    SanPhamDAO spDAO;
    KhachHangDAO khDAO;
    JFileChooser fileChooser;
    public MainForm() {
        initComponents();
        this.setBackground(new java.awt.Color(0,0,0,0));
        pnlBg.setBackground(new java.awt.Color(0,0,0,0));
        setLocationRelativeTo(null);
        titleForm.setText("TRANG CHỦ");
        initInfor();
        initErr();
        getDayMonthYear(XDate.toString(XDate.now(), "yyyy-MM-dd"));
        dragAndDropImg();
        txtID.setEditable(false);
        txtUserName.setEditable(false);
        setIconImage(XImg.logoApp());
    }
    public void dragAndDropImg() {
        DADUpdateEmp dad = new DADUpdateEmp(lblInforImg);
        new DropTarget(this, dad);      
    }
    public void initInfor() {
        lblAvatar.setIcon(XImg.read(Auth.user.getAvatar()));
        lblFullName.setText(Auth.user.getHoTen());
    }
    public void initErr() {
        lblErrLocation.setText(null);
        lblErrBirth.setText(null);
        lblErrEmail.setText(null);
        lblErrPhone.setText(null);
    }
    public void initTextFields() {
        txtLocation.setBorder(txtTemplate.getBorder());
        txtBirth.setBorder(txtTemplate.getBorder());
        txtEmail.setBorder(txtTemplate.getBorder());
        txtPhone.setBorder(txtTemplate.getBorder());
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
    /* Validate*/ 
    public boolean validatePhone() {
        return Validator.isPhone(txtPhone, lblErrPhone);
    }
    public boolean validateEmail() {
        return Validator.isEmail(txtEmail, lblErrEmail);
    }
    public boolean validateBirth() {
        return Validator.isBirth(txtBirth, lblErrBirth);
    }
    public boolean validateAddress() {
        return Validator.isAddress(txtLocation, lblErrLocation);
    }
    public boolean validateAll() {
        boolean isFormValid = true;
        if(validatePhone()) {
            isFormValid = false;
        }
        if(validateEmail()) {
            isFormValid = false;
        }
        if(validateBirth()) {
            isFormValid = false;
        }
        if(validateAddress()) {
            isFormValid = false;
        }
        return isFormValid;
    }
    public void setNullErr(javax.swing.JTextField txt, javax.swing.JLabel label) {
        txt.setBorder(txtTemplate.getBorder());
        label.setText(null);
    }
    
    public void navShowHome() {
        layoutStacking.removeAll();
        layoutStacking.add(pnlContentHome);
        layoutStacking.repaint();
        layoutStacking.revalidate();
        titleForm.setText("TRANG CHỦ");
    }
    public void navShowGeneral() {
        layoutStacking.removeAll();
        layoutStacking.add(pnlContentGeneral);
        layoutStacking.repaint();
        layoutStacking.revalidate();
        titleForm.setText("TỔNG QUAN");
        countInvoiceToday();
        sumInvoiceToday();
        countProductSoldToday();
        countProductOOSToday();
        countBirthCustomerToday();
        countProductBusiness();
    }
    public void navShowInvoice() {
        
    }
    public void navShowInfor() {
        layoutStacking.removeAll();
        layoutStacking.add(pnlInfor);
        layoutStacking.repaint();
        layoutStacking.revalidate();
        titleForm.setText("Thông tin cá nhân");
        setForm(Auth.user);
    }
    public void updateGeneralAll() {
        countInvoiceToday();
        sumInvoiceToday();
        countProductSoldToday();
        countProductOOSToday();
        countBirthCustomerToday();
        countProductBusiness();
    }
    public void sumInvoiceToday() {
        hdDAO = new HoaDonDAO();
        double count = hdDAO.getSumInvoiceToday();
        DecimalFormat formatter;
        formatter = new DecimalFormat("###,###,###");
        lblResultSell.setText(formatter.format(count) + " VNĐ");
    }
    public void countProductBusiness() {
        spDAO = new SanPhamDAO();
        int count = spDAO.getCountProductBusiness();
        lblResultBusiness.setText(count+" sản phẩm");
    }
    public void countInvoiceToday() {
        hdDAO = new HoaDonDAO();
        int count = hdDAO.getCountInvoiceToday();
        lblResultInvoice.setText(count+" hóa đơn");
    }
    public void countProductSoldToday() {
        spDAO = new SanPhamDAO();
        int count = spDAO.getCountProductSold();
        lblResultSold.setText(count+" sản phẩm");
    }
    public void countProductOOSToday() {
        spDAO = new SanPhamDAO();
        int count = spDAO.getCountProductOOS();
        lblResultOOS.setText(count+" sản phẩm");
    }
    public void countBirthCustomerToday() {
        khDAO = new KhachHangDAO();
        int count = khDAO.getCountBirthCustomer();
        lblResultBirth.setText(count+" khách hàng");
    }
    /* INFORMATION EMPLOYEE*/
    public NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtID.getText());
        nv.setHoTen(txtFullName.getText());
        nv.setEmail(txtEmail.getText());
        nv.setDienThoai(txtPhone.getText());
        nv.setDiaChi(txtLocation.getText());
        nv.setNgaySinh(XDate.convertDate(txtBirth.getText(), "dd-MM-yyyy"));
        nv.setAvatar(lblInforImg.getToolTipText());
        return nv;
    }
    public void setForm(NhanVien nv) {
        txtID.setText(nv.getMaNV());
        txtFullName.setText(nv.getHoTen());
        txtUserName.setText(nv.getTaiKhoan());
        txtEmail.setText(nv.getEmail());
        txtPhone.setText(nv.getDienThoai());
        txtLocation.setText(nv.getDiaChi());
        txtBirth.setText(XDate.toString(nv.getNgaySinh(), "dd-MM-yyyy"));
        if(nv.getAvatar() != null) {
            lblInforImg.setToolTipText(nv.getAvatar());
            ImageIcon iconCore = XImg.read(lblInforImg.getToolTipText());
            Image img = iconCore.getImage();
            ImageIcon iconParse = new ImageIcon(img.getScaledInstance(lblAvatar.getWidth(),
                    lblInforImg.getHeight(), Image.SCALE_SMOOTH));
            lblInforImg.setIcon(iconParse);            
        } else {
            lblInforImg.setIcon(null);
        }
    }
    public void loadAvatar() {
        fileChooser = new JFileChooser();
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImg.save(file);
            ImageIcon iconCore = XImg.read(file.getName());
            Image img = iconCore.getImage();
            ImageIcon iconParsed = new ImageIcon(img.getScaledInstance(lblInforImg.getWidth(),
                    lblInforImg.getHeight(),
                    Image.SCALE_SMOOTH));
            lblInforImg.setIcon(iconParsed);
            lblInforImg.setToolTipText(file.getName());
        }
    }
    public void updateInforEmp() {
        nvDAO = new NhanVienDAO();
        if(MsgBox.confirm(this, "Xác nhận cập nhập thông tin")) {
            int rowEffect = nvDAO.updateInfor(this.getForm());
            if(rowEffect > 0) {
                NhanVien nv = nvDAO.selectByID(txtID.getText());
                Auth.user = nv;
                lblAvatar.setIcon(XImg.read(Auth.user.getAvatar()));
                this.setForm(nv);
                initErr();
                initTextFields();
                ThongBaoForm tb = new ThongBaoForm(this,true);
                tb.setMessage("Cập nhập thông tin thành công");
                tb.setVisible(true);
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

        jPanel6 = new javax.swing.JPanel();
        pnlBg = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblAvatar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblTitleHome = new javax.swing.JLabel();
        lblIconHome = new javax.swing.JLabel();
        lblHome = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTitleOverview = new javax.swing.JLabel();
        lblIconOverview = new javax.swing.JLabel();
        lblOverview = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblTitlePrice = new javax.swing.JLabel();
        lblIconPrice = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTitleInfor = new javax.swing.JLabel();
        lblIconInfor = new javax.swing.JLabel();
        lblInfor = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblTitleLogout = new javax.swing.JLabel();
        lblIconLogout = new javax.swing.JLabel();
        lblLogout = new javax.swing.JLabel();
        lblFullName = new javax.swing.JLabel();
        lblLeftNavigation = new javax.swing.JLabel();
        layoutStacking = new javax.swing.JLayeredPane();
        pnlContentHome = new javax.swing.JPanel();
        pnlProduct = new javax.swing.JPanel();
        lblTitleProduct = new javax.swing.JLabel();
        lblIconProduct = new javax.swing.JLabel();
        pnlSupplier = new javax.swing.JPanel();
        lblTitleSupplier = new javax.swing.JLabel();
        lblIconSupplier = new javax.swing.JLabel();
        pnlEmp = new javax.swing.JPanel();
        lblTitleEmp = new javax.swing.JLabel();
        lblIconEmp = new javax.swing.JLabel();
        pnlStatistics = new javax.swing.JPanel();
        lblTitleStatistics = new javax.swing.JLabel();
        lblIconStatistics = new javax.swing.JLabel();
        pnlCustomer = new javax.swing.JPanel();
        lblTitleCustomer = new javax.swing.JLabel();
        lblIconCustomer = new javax.swing.JLabel();
        pnlInvoice = new javax.swing.JPanel();
        lblTitleInvoice = new javax.swing.JLabel();
        lblIconInvoice = new javax.swing.JLabel();
        lblDay = new javax.swing.JLabel();
        lblMonth = new javax.swing.JLabel();
        lblDayOfW = new javax.swing.JLabel();
        lblFirst = new javax.swing.JLabel();
        pnlContentGeneral = new javax.swing.JPanel();
        lblActiveToday = new javax.swing.JLabel();
        pnlSell = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        iconSell = new javax.swing.JLabel();
        lblResultSell = new javax.swing.JLabel();
        lblContentSell = new javax.swing.JLabel();
        pnlContentInvoice = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        iconInvoice = new javax.swing.JLabel();
        lblResultInvoice = new javax.swing.JLabel();
        lblContentInvoice = new javax.swing.JLabel();
        pnlOOS = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblResultOOS = new javax.swing.JLabel();
        iconOOS = new javax.swing.JLabel();
        lblContentOOS = new javax.swing.JLabel();
        pnlProductSold = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblResultSold = new javax.swing.JLabel();
        iconSold = new javax.swing.JLabel();
        lblContentSold = new javax.swing.JLabel();
        pnlBirth = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblResultBirth = new javax.swing.JLabel();
        iconBirth = new javax.swing.JLabel();
        lblContentBirth = new javax.swing.JLabel();
        pnlBusiness = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblResultBusiness = new javax.swing.JLabel();
        iconBusiness = new javax.swing.JLabel();
        lblContentBusiness = new javax.swing.JLabel();
        iconActivitiesToday = new javax.swing.JLabel();
        lblBgGeneral = new javax.swing.JLabel();
        pnlInfor = new javax.swing.JPanel();
        lblInforImg = new javax.swing.JLabel();
        txtTemplate = new javax.swing.JTextField();
        sepName = new javax.swing.JSeparator();
        txtFullName = new javax.swing.JTextField();
        inforIconID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        txtBirth = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        InforIconMail = new javax.swing.JLabel();
        inforIconBirth = new javax.swing.JLabel();
        inforIconUserName = new javax.swing.JLabel();
        inforIconLocation = new javax.swing.JLabel();
        inforIconPhone = new javax.swing.JLabel();
        lblErrPhone = new javax.swing.JLabel();
        lblErrLocation = new javax.swing.JLabel();
        lblErrBirth = new javax.swing.JLabel();
        lblErrEmail = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        sepPhone = new javax.swing.JSeparator();
        sepUserName = new javax.swing.JSeparator();
        sepBirth = new javax.swing.JSeparator();
        sepMail = new javax.swing.JSeparator();
        sepLocation = new javax.swing.JSeparator();
        sepID = new javax.swing.JSeparator();
        line2 = new javax.swing.JLabel();
        line1 = new javax.swing.JLabel();
        line4 = new javax.swing.JLabel();
        line3 = new javax.swing.JLabel();
        lblChangePw = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblUpdate = new javax.swing.JLabel();
        bgInfor = new javax.swing.JLabel();
        pnlHeaderClose = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        titleForm = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        pnlBg.setForeground(new java.awt.Color(255, 255, 255));
        pnlBg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/avatar/trandan.png"))); // NOI18N
        jPanel2.add(lblAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 140));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleHome.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleHome.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleHome.setText("TRANG CHỦ");
        lblTitleHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHomeMouseClicked(evt);
            }
        });
        jPanel1.add(lblTitleHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 100, 30));

        lblIconHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home-page.png"))); // NOI18N
        lblIconHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHomeMouseClicked(evt);
            }
        });
        jPanel1.add(lblIconHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, 50));

        lblHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHomeMouseClicked(evt);
            }
        });
        jPanel1.add(lblHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 50));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 220, 50));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleOverview.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleOverview.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleOverview.setText("TỔNG QUAN");
        lblTitleOverview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOverviewMouseClicked(evt);
            }
        });
        jPanel3.add(lblTitleOverview, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, 30));

        lblIconOverview.setForeground(new java.awt.Color(255, 255, 255));
        lblIconOverview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconOverview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/charts.png"))); // NOI18N
        lblIconOverview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOverviewMouseClicked(evt);
            }
        });
        jPanel3.add(lblIconOverview, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, 50));

        lblOverview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblOverview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblOverviewMouseClicked(evt);
            }
        });
        jPanel3.add(lblOverview, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 50));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 220, 50));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitlePrice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitlePrice.setForeground(new java.awt.Color(255, 255, 255));
        lblTitlePrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitlePrice.setText("THANH TOÁN");
        lblTitlePrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPriceMouseClicked(evt);
            }
        });
        jPanel5.add(lblTitlePrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 110, 30));

        lblIconPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconPrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save-money.png"))); // NOI18N
        lblIconPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPriceMouseClicked(evt);
            }
        });
        jPanel5.add(lblIconPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, 50));

        lblPrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPriceMouseClicked(evt);
            }
        });
        jPanel5.add(lblPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 50));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 220, 50));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleInfor.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleInfor.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleInfor.setText("THÔNG TIN");
        lblTitleInfor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInforMouseClicked(evt);
            }
        });
        jPanel4.add(lblTitleInfor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 100, 30));

        lblIconInfor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconInfor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/infor.png"))); // NOI18N
        lblIconInfor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInforMouseClicked(evt);
            }
        });
        jPanel4.add(lblIconInfor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, 50));

        lblInfor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblInfor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInforMouseClicked(evt);
            }
        });
        jPanel4.add(lblInfor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 50));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 220, 50));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleLogout.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleLogout.setForeground(new java.awt.Color(255, 255, 255));
        lblTitleLogout.setText("ĐĂNG XUẤT");
        lblTitleLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInforMouseClicked(evt);
            }
        });
        jPanel7.add(lblTitleLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 100, 30));

        lblIconLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        lblIconLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInforMouseClicked(evt);
            }
        });
        jPanel7.add(lblIconLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 70, 50));

        lblLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/label-horizontal.png"))); // NOI18N
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });
        jPanel7.add(lblLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 50));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 220, 50));

        lblFullName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFullName.setForeground(new java.awt.Color(255, 255, 255));
        lblFullName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFullName.setText("Phan Tấn Lộc");
        jPanel2.add(lblFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 220, -1));

        lblLeftNavigation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLeftNavigation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/left-nav.png"))); // NOI18N
        jPanel2.add(lblLeftNavigation, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 570));

        pnlBg.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 570));

        layoutStacking.setLayout(new java.awt.CardLayout());

        pnlContentHome.setBackground(new java.awt.Color(245, 245, 245));
        pnlContentHome.setForeground(new java.awt.Color(232, 232, 232));
        pnlContentHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlProduct.setBackground(new java.awt.Color(255, 255, 255));
        pnlProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlProductMouseClicked(evt);
            }
        });
        pnlProduct.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleProduct.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleProduct.setText("Sản phẩm");
        lblTitleProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlProductMouseClicked(evt);
            }
        });
        pnlProduct.add(lblTitleProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 100, -1));

        lblIconProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sneakers-3.png"))); // NOI18N
        lblIconProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlProductMouseClicked(evt);
            }
        });
        pnlProduct.add(lblIconProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 120, 60));

        pnlContentHome.add(pnlProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 160, 170));

        pnlSupplier.setBackground(new java.awt.Color(255, 255, 255));
        pnlSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlSupplierMouseClicked(evt);
            }
        });
        pnlSupplier.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleSupplier.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleSupplier.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleSupplier.setText("Nhà cung cấp");
        lblTitleSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlSupplierMouseClicked(evt);
            }
        });
        pnlSupplier.add(lblTitleSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        lblIconSupplier.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/supplier.png"))); // NOI18N
        lblIconSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlSupplierMouseClicked(evt);
            }
        });
        pnlSupplier.add(lblIconSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 60));

        pnlContentHome.add(pnlSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 160, 170));

        pnlEmp.setBackground(new java.awt.Color(255, 255, 255));
        pnlEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlEmpMouseClicked(evt);
            }
        });
        pnlEmp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleEmp.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleEmp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleEmp.setText("Nhân viên");
        lblTitleEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlEmpMouseClicked(evt);
            }
        });
        pnlEmp.add(lblTitleEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 120, -1));

        lblIconEmp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconEmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/employee.png"))); // NOI18N
        lblIconEmp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlEmpMouseClicked(evt);
            }
        });
        pnlEmp.add(lblIconEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 60));

        pnlContentHome.add(pnlEmp, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, 160, 170));

        pnlStatistics.setBackground(new java.awt.Color(255, 255, 255));
        pnlStatistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlStatisticsMouseClicked(evt);
            }
        });
        pnlStatistics.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleStatistics.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleStatistics.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleStatistics.setText("Thống kê");
        lblTitleStatistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlStatisticsMouseClicked(evt);
            }
        });
        pnlStatistics.add(lblTitleStatistics, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 100, -1));

        lblIconStatistics.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconStatistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/statistics.png"))); // NOI18N
        lblIconStatistics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlStatisticsMouseClicked(evt);
            }
        });
        pnlStatistics.add(lblIconStatistics, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 60));

        pnlContentHome.add(pnlStatistics, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 160, 170));

        pnlCustomer.setBackground(new java.awt.Color(255, 255, 255));
        pnlCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlCustomerMouseClicked(evt);
            }
        });
        pnlCustomer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleCustomer.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleCustomer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleCustomer.setText("Khách hàng");
        lblTitleCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlCustomerMouseClicked(evt);
            }
        });
        pnlCustomer.add(lblTitleCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 100, -1));

        lblIconCustomer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/customer2.png"))); // NOI18N
        lblIconCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlCustomerMouseClicked(evt);
            }
        });
        pnlCustomer.add(lblIconCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 60));

        pnlContentHome.add(pnlCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 160, 170));

        pnlInvoice.setBackground(new java.awt.Color(255, 255, 255));
        pnlInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlInvoiceMouseClicked(evt);
            }
        });
        pnlInvoice.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitleInvoice.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblTitleInvoice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitleInvoice.setText("Hóa đơn");
        lblTitleInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlInvoiceMouseClicked(evt);
            }
        });
        pnlInvoice.add(lblTitleInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 100, -1));

        lblIconInvoice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIconInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/inventory.png"))); // NOI18N
        lblIconInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlInvoiceMouseClicked(evt);
            }
        });
        pnlInvoice.add(lblIconInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 60));

        pnlContentHome.add(pnlInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 160, 170));

        lblDay.setFont(new java.awt.Font("Tahoma", 0, 45)); // NOI18N
        lblDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDay.setText("30");
        pnlContentHome.add(lblDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 80, 40));

        lblMonth.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMonth.setText("DECEMBER");
        pnlContentHome.add(lblMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 120, -1));

        lblDayOfW.setFont(new java.awt.Font("Segoe UI Semilight", 0, 16)); // NOI18N
        lblDayOfW.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDayOfW.setText("MONDAY");
        pnlContentHome.add(lblDayOfW, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 120, -1));

        lblFirst.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFirst.setText("TH");
        pnlContentHome.add(lblFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, -1, -1));

        layoutStacking.add(pnlContentHome, "card2");

        pnlContentGeneral.setBackground(new java.awt.Color(245, 245, 245));
        pnlContentGeneral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblActiveToday.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblActiveToday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActiveToday.setText("HOẠT ĐỘNG HÔM NAY");
        pnlContentGeneral.add(lblActiveToday, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 160, 30));

        pnlSell.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TIỀN BÁN HÀNG");
        pnlSell.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 130, -1));

        iconSell.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconSell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sell.png"))); // NOI18N
        pnlSell.add(iconSell, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 50));

        lblResultSell.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblResultSell.setForeground(new java.awt.Color(255, 255, 255));
        lblResultSell.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultSell.setText("value");
        pnlSell.add(lblResultSell, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 240, -1));

        lblContentSell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-general.jpg"))); // NOI18N
        lblContentSell.setText("ss");
        pnlSell.add(lblContentSell, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 100));

        pnlContentGeneral.add(pnlSell, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 240, -1));

        pnlContentInvoice.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SỐ ĐƠN HÀNG");
        pnlContentInvoice.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 140, -1));

        iconInvoice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/invoice-2.png"))); // NOI18N
        pnlContentInvoice.add(iconInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 50));

        lblResultInvoice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblResultInvoice.setForeground(new java.awt.Color(255, 255, 255));
        lblResultInvoice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultInvoice.setText("value");
        pnlContentInvoice.add(lblResultInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 200, -1));

        lblContentInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-general.jpg"))); // NOI18N
        pnlContentInvoice.add(lblContentInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 100));

        pnlContentGeneral.add(pnlContentInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 240, 100));

        pnlOOS.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SẢN PHẨM SẮP HẾT");
        pnlOOS.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 160, 40));

        lblResultOOS.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblResultOOS.setForeground(new java.awt.Color(255, 255, 255));
        lblResultOOS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultOOS.setText("value");
        pnlOOS.add(lblResultOOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 240, -1));

        iconOOS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconOOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/oos-product.png"))); // NOI18N
        pnlOOS.add(iconOOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 60, 60));

        lblContentOOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-general.jpg"))); // NOI18N
        pnlOOS.add(lblContentOOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlContentGeneral.add(pnlOOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 240, 105));

        pnlProductSold.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SẢN PHẨM ĐÃ BÁN");
        pnlProductSold.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 170, 40));

        lblResultSold.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblResultSold.setForeground(new java.awt.Color(255, 255, 255));
        lblResultSold.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultSold.setText("value");
        pnlProductSold.add(lblResultSold, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 240, -1));

        iconSold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/product-sold.png"))); // NOI18N
        pnlProductSold.add(iconSold, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 50));

        lblContentSold.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-general.jpg"))); // NOI18N
        pnlProductSold.add(lblContentSold, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlContentGeneral.add(pnlProductSold, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 240, -1));

        pnlBirth.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("SINH NHẬT KHÁCH HÀNG");
        pnlBirth.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 180, 40));

        lblResultBirth.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblResultBirth.setForeground(new java.awt.Color(255, 255, 255));
        lblResultBirth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultBirth.setText("value");
        pnlBirth.add(lblResultBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 240, -1));

        iconBirth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconBirth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/birth.png"))); // NOI18N
        pnlBirth.add(iconBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 60, 60));

        lblContentBirth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-general.jpg"))); // NOI18N
        pnlBirth.add(lblContentBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlContentGeneral.add(pnlBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 390, 240, -1));

        pnlBusiness.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("SẢN PHẨM KINH DOANH");
        pnlBusiness.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 180, 40));

        lblResultBusiness.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblResultBusiness.setForeground(new java.awt.Color(255, 255, 255));
        lblResultBusiness.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultBusiness.setText("value");
        pnlBusiness.add(lblResultBusiness, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 240, -1));

        iconBusiness.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconBusiness.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/product-business.png"))); // NOI18N
        pnlBusiness.add(iconBusiness, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 60, 60));

        lblContentBusiness.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-general.jpg"))); // NOI18N
        pnlBusiness.add(lblContentBusiness, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlContentGeneral.add(pnlBusiness, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 240, -1));

        iconActivitiesToday.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/activities-today.png"))); // NOI18N
        pnlContentGeneral.add(iconActivitiesToday, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 50));
        pnlContentGeneral.add(lblBgGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 540));

        layoutStacking.add(pnlContentGeneral, "card3");

        pnlInfor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInforImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInforImg.setText("IMG");
        lblInforImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInforImgMouseClicked(evt);
            }
        });
        pnlInfor.add(lblInforImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 120, 110));

        txtTemplate.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTemplate.setBorder(null);
        pnlInfor.add(txtTemplate, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 0, 0));
        pnlInfor.add(sepName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 130, 20));

        txtFullName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtFullName.setText("Name");
        txtFullName.setBorder(null);
        pnlInfor.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 130, 30));

        inforIconID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inforIconID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/infor-idEmp.png"))); // NOI18N
        pnlInfor.add(inforIconID, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 55, 45));

        txtID.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtID.setText("Mã nhân viên");
        txtID.setBorder(null);
        pnlInfor.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 180, 30));

        txtUserName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtUserName.setText("Tài khoản");
        txtUserName.setBorder(null);
        pnlInfor.add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, 180, 30));

        txtBirth.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtBirth.setText("Ngày sinh");
        txtBirth.setBorder(null);
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
        pnlInfor.add(txtBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 350, 180, 30));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtEmail.setText("Email");
        txtEmail.setBorder(null);
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
        pnlInfor.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 430, 180, 30));

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPhone.setText("Số điện thoại");
        txtPhone.setBorder(null);
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
        pnlInfor.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 270, 180, 30));

        InforIconMail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        InforIconMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/infor-email.png"))); // NOI18N
        pnlInfor.add(InforIconMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, 45));

        inforIconBirth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inforIconBirth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/infor-birth.png"))); // NOI18N
        pnlInfor.add(inforIconBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 55, 45));

        inforIconUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inforIconUserName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/infor-userName.png"))); // NOI18N
        pnlInfor.add(inforIconUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, 45));

        inforIconLocation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inforIconLocation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/infor-location.png"))); // NOI18N
        pnlInfor.add(inforIconLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 54, 45));

        inforIconPhone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inforIconPhone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/infor-phone.png"))); // NOI18N
        pnlInfor.add(inforIconPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, 55, 45));

        lblErrPhone.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblErrPhone.setText("Error Message");
        pnlInfor.add(lblErrPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 180, -1));

        lblErrLocation.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblErrLocation.setText("Error Message");
        pnlInfor.add(lblErrLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 460, 180, -1));

        lblErrBirth.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblErrBirth.setText("Error Message");
        pnlInfor.add(lblErrBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, 190, -1));

        lblErrEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblErrEmail.setText("Error Message");
        pnlInfor.add(lblErrEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 180, -1));

        txtLocation.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtLocation.setText("Địa chỉ");
        txtLocation.setBorder(null);
        txtLocation.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLocationFocusLost(evt);
            }
        });
        txtLocation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLocationKeyPressed(evt);
            }
        });
        pnlInfor.add(txtLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 430, 180, 30));
        pnlInfor.add(sepPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 180, 10));
        pnlInfor.add(sepUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, 180, 10));
        pnlInfor.add(sepBirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, 180, 10));
        pnlInfor.add(sepMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 180, 10));
        pnlInfor.add(sepLocation, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 460, 180, 10));
        pnlInfor.add(sepID, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 180, 10));

        line2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/line2.png"))); // NOI18N
        pnlInfor.add(line2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 4, 70));

        line1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/line2.png"))); // NOI18N
        pnlInfor.add(line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 4, 70));

        line4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/line2.png"))); // NOI18N
        pnlInfor.add(line4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 380, 4, 70));

        line3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/line2.png"))); // NOI18N
        pnlInfor.add(line3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 300, 4, 70));

        lblChangePw.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblChangePw.setForeground(new java.awt.Color(204, 51, 0));
        lblChangePw.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChangePw.setText("Đổi mật khẩu?");
        lblChangePw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblChangePwMouseClicked(evt);
            }
        });
        pnlInfor.add(lblChangePw, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 470, 90, 40));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/human.png"))); // NOI18N
        pnlInfor.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 80, 90));

        lblUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn-updateSup2.png"))); // NOI18N
        lblUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUpdateMouseClicked(evt);
            }
        });
        pnlInfor.add(lblUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 490, 130, 40));

        bgInfor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bg-inforUser.jpg"))); // NOI18N
        bgInfor.setText("jLabel2");
        pnlInfor.add(bgInfor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 540));

        layoutStacking.add(pnlInfor, "card4");

        pnlBg.add(layoutStacking, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 570, 540));

        pnlHeaderClose.setBackground(new java.awt.Color(255, 255, 255));
        pnlHeaderClose.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/close.png"))); // NOI18N
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });
        pnlHeaderClose.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(506, 0, 40, 29));

        titleForm.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titleForm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleForm.setText("Title Form");
        pnlHeaderClose.add(titleForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 0, 407, 29));

        pnlBg.add(pnlHeaderClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 570, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBg, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBg, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
       if(MsgBox.confirm(this, "Bạn muốn đăng xuất?")) {
           this.dispose();
           new Login().setVisible(true);
       }
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void lblHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHomeMouseClicked
        navShowHome();
    }//GEN-LAST:event_lblHomeMouseClicked

    private void lblOverviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblOverviewMouseClicked
        navShowGeneral();
    }//GEN-LAST:event_lblOverviewMouseClicked

    private void lblPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPriceMouseClicked
        new ThanhToanForm(this, true).setVisible(true);
    }//GEN-LAST:event_lblPriceMouseClicked

    private void lblInforMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInforMouseClicked
        navShowInfor();
    }//GEN-LAST:event_lblInforMouseClicked

    private void pnlProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlProductMouseClicked
        new SanPhamForm(this, true).setVisible(true);
    }//GEN-LAST:event_pnlProductMouseClicked

    private void pnlSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSupplierMouseClicked
        new NhaCungCap(this, true).setVisible(true);
    }//GEN-LAST:event_pnlSupplierMouseClicked

    private void pnlInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlInvoiceMouseClicked
        if(!Auth.user.isVaiTro()) {
            ThongBaoForm tb = new ThongBaoForm(this,true);
            tb.setMessage("Bạn không có quyền sử dụng chức năng này");
            tb.setVisible(true);
            return;
        }
        new HoaDonForm(this, true).setVisible(true);
    }//GEN-LAST:event_pnlInvoiceMouseClicked

    private void pnlStatisticsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlStatisticsMouseClicked
        if(!Auth.user.isVaiTro()) {
            ThongBaoForm tb = new ThongBaoForm(this,true);
            tb.setMessage("Bạn không có quyền sử dụng chức năng này");
            tb.setVisible(true);
            return;
        }
        new ThongKeForm(this, true).setVisible(true);
    }//GEN-LAST:event_pnlStatisticsMouseClicked

    private void pnlCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCustomerMouseClicked
        new KhachHangForm(this, true).setVisible(true);
    }//GEN-LAST:event_pnlCustomerMouseClicked

    private void pnlEmpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEmpMouseClicked
        if(!Auth.user.isVaiTro()) {
            ThongBaoForm tb = new ThongBaoForm(this,true);
            tb.setMessage("Bạn không có quyền sử dụng chức năng này");
            tb.setVisible(true);
            return;
        }
        new NhanVienForm(this, true).setVisible(true);
    }//GEN-LAST:event_pnlEmpMouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked

       if(MsgBox.confirm(this, "Bạn muốn kết thúc phiên làm việc?")) {
           System.exit(0);

       }
    }//GEN-LAST:event_jLabel26MouseClicked

    private void lblInforImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInforImgMouseClicked
       if(evt.getClickCount() == 2){
              loadAvatar();     
        }
    }//GEN-LAST:event_lblInforImgMouseClicked

    private void lblUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUpdateMouseClicked
        if(validateAll()) {
            updateInforEmp();  
        }
    }//GEN-LAST:event_lblUpdateMouseClicked

    private void lblChangePwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblChangePwMouseClicked
        new DoiMatKhau(null, true).setVisible(true);
    }//GEN-LAST:event_lblChangePwMouseClicked

    private void txtPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusLost
        validatePhone();
    }//GEN-LAST:event_txtPhoneFocusLost

    private void txtBirthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBirthFocusLost
        validateBirth();
    }//GEN-LAST:event_txtBirthFocusLost

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        validateEmail();
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtLocationFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLocationFocusLost
        validateAddress();
    }//GEN-LAST:event_txtLocationFocusLost

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
       setNullErr(txtPhone, lblErrPhone);
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void txtBirthKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBirthKeyPressed
        setNullErr(txtBirth, lblErrBirth);
    }//GEN-LAST:event_txtBirthKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        setNullErr(txtEmail, lblErrEmail);
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtLocationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocationKeyPressed
        setNullErr(txtLocation, lblErrLocation);
    }//GEN-LAST:event_txtLocationKeyPressed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel InforIconMail;
    private javax.swing.JLabel bgInfor;
    private javax.swing.JLabel iconActivitiesToday;
    private javax.swing.JLabel iconBirth;
    private javax.swing.JLabel iconBusiness;
    private javax.swing.JLabel iconInvoice;
    private javax.swing.JLabel iconOOS;
    private javax.swing.JLabel iconSell;
    private javax.swing.JLabel iconSold;
    private javax.swing.JLabel inforIconBirth;
    private javax.swing.JLabel inforIconID;
    private javax.swing.JLabel inforIconLocation;
    private javax.swing.JLabel inforIconPhone;
    private javax.swing.JLabel inforIconUserName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLayeredPane layoutStacking;
    private javax.swing.JLabel lblActiveToday;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblBgGeneral;
    private javax.swing.JLabel lblChangePw;
    private javax.swing.JLabel lblContentBirth;
    private javax.swing.JLabel lblContentBusiness;
    private javax.swing.JLabel lblContentInvoice;
    private javax.swing.JLabel lblContentOOS;
    private javax.swing.JLabel lblContentSell;
    private javax.swing.JLabel lblContentSold;
    private javax.swing.JLabel lblDay;
    private javax.swing.JLabel lblDayOfW;
    private javax.swing.JLabel lblErrBirth;
    private javax.swing.JLabel lblErrEmail;
    private javax.swing.JLabel lblErrLocation;
    private javax.swing.JLabel lblErrPhone;
    private javax.swing.JLabel lblFirst;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblIconCustomer;
    private javax.swing.JLabel lblIconEmp;
    private javax.swing.JLabel lblIconHome;
    private javax.swing.JLabel lblIconInfor;
    private javax.swing.JLabel lblIconInvoice;
    private javax.swing.JLabel lblIconLogout;
    private javax.swing.JLabel lblIconOverview;
    private javax.swing.JLabel lblIconPrice;
    private javax.swing.JLabel lblIconProduct;
    private javax.swing.JLabel lblIconStatistics;
    private javax.swing.JLabel lblIconSupplier;
    private javax.swing.JLabel lblInfor;
    private javax.swing.JLabel lblInforImg;
    private javax.swing.JLabel lblLeftNavigation;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblMonth;
    private javax.swing.JLabel lblOverview;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblResultBirth;
    private javax.swing.JLabel lblResultBusiness;
    private javax.swing.JLabel lblResultInvoice;
    private javax.swing.JLabel lblResultOOS;
    private javax.swing.JLabel lblResultSell;
    private javax.swing.JLabel lblResultSold;
    private javax.swing.JLabel lblTitleCustomer;
    private javax.swing.JLabel lblTitleEmp;
    private javax.swing.JLabel lblTitleHome;
    private javax.swing.JLabel lblTitleInfor;
    private javax.swing.JLabel lblTitleInvoice;
    private javax.swing.JLabel lblTitleLogout;
    private javax.swing.JLabel lblTitleOverview;
    private javax.swing.JLabel lblTitlePrice;
    private javax.swing.JLabel lblTitleProduct;
    private javax.swing.JLabel lblTitleStatistics;
    private javax.swing.JLabel lblTitleSupplier;
    private javax.swing.JLabel lblUpdate;
    private javax.swing.JLabel line1;
    private javax.swing.JLabel line2;
    private javax.swing.JLabel line3;
    private javax.swing.JLabel line4;
    private javax.swing.JPanel pnlBg;
    private javax.swing.JPanel pnlBirth;
    private javax.swing.JPanel pnlBusiness;
    private javax.swing.JPanel pnlContentGeneral;
    private javax.swing.JPanel pnlContentHome;
    private javax.swing.JPanel pnlContentInvoice;
    private javax.swing.JPanel pnlCustomer;
    private javax.swing.JPanel pnlEmp;
    private javax.swing.JPanel pnlHeaderClose;
    private javax.swing.JPanel pnlInfor;
    private javax.swing.JPanel pnlInvoice;
    private javax.swing.JPanel pnlOOS;
    private javax.swing.JPanel pnlProduct;
    private javax.swing.JPanel pnlProductSold;
    private javax.swing.JPanel pnlSell;
    private javax.swing.JPanel pnlStatistics;
    private javax.swing.JPanel pnlSupplier;
    private javax.swing.JSeparator sepBirth;
    private javax.swing.JSeparator sepID;
    private javax.swing.JSeparator sepLocation;
    private javax.swing.JSeparator sepMail;
    private javax.swing.JSeparator sepName;
    private javax.swing.JSeparator sepPhone;
    private javax.swing.JSeparator sepUserName;
    private javax.swing.JLabel titleForm;
    private javax.swing.JTextField txtBirth;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtTemplate;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
