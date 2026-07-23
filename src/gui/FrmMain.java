package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class FrmMain extends JFrame {
    private JPanel pnlCard;
    private CardLayout cardLayout;

    // Bảng màu rực rỡ và chuyên nghiệp hơn
    private final Color COLOR_BG = new Color(240, 244, 248); // Nền xám nhạt hơi xanh (Background)
    private final Color COLOR_SIDEBAR = new Color(26, 35, 45); // Nền Sidebar (Xanh đen đậm)
    private final Color COLOR_PRIMARY = new Color(0, 168, 107); // Xanh ngọc CTA
    private final Color COLOR_SIDEBAR_TEXT = new Color(220, 225, 230); // Chữ Sidebar trắng nhạt
    private final Color COLOR_HOVER = new Color(40, 53, 67); // Hover trên Sidebar
    
    // Màu cho khu vực nội dung
    private final Color COLOR_CONTENT_TEXT_DARK = new Color(44, 62, 80);
    private final Color COLOR_CONTENT_TEXT_MUTED = new Color(127, 140, 141);

    private SidebarItem activeItem = null;

    public FrmMain() {
        setTitle("Hệ Thống Quản Lý Bệnh Viện Đa Khoa Thu Cúc");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        // --- SIDEBAR ---
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setBackground(COLOR_SIDEBAR);
        pnlSidebar.setPreferredSize(new Dimension(280, 0));
        pnlSidebar.setLayout(new BorderLayout());
        
        // Header Sidebar
        JPanel pnlSidebarHeader = new JPanel(new BorderLayout());
        pnlSidebarHeader.setBackground(COLOR_SIDEBAR);
        pnlSidebarHeader.setBorder(new EmptyBorder(30, 0, 20, 0));
        
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon("src/images/logo.png");
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("TCI");
            lblLogo.setFont(new Font("SansSerif", Font.BOLD, 28));
            lblLogo.setForeground(COLOR_PRIMARY);
        }
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel pnlTitle = new JPanel(new GridLayout(2, 1));
        pnlTitle.setOpaque(false);
        JLabel lblTitle = new JLabel("BỆNH VIỆN THU CÚC", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        JLabel lblAdmin = new JLabel("Vai trò: Admin", SwingConstants.CENTER);
        lblAdmin.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblAdmin.setForeground(COLOR_PRIMARY);
        pnlTitle.add(lblTitle);
        pnlTitle.add(lblAdmin);
        pnlTitle.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        pnlSidebarHeader.add(lblLogo, BorderLayout.CENTER);
        pnlSidebarHeader.add(pnlTitle, BorderLayout.SOUTH);
        
        pnlSidebar.add(pnlSidebarHeader, BorderLayout.NORTH);
        
        // Menu
        JPanel pnlMenu = new JPanel();
        pnlMenu.setLayout(new BoxLayout(pnlMenu, BoxLayout.Y_AXIS));
        pnlMenu.setBackground(COLOR_SIDEBAR);
        pnlMenu.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        String role = "Admin"; // Default if not logged in through proper channel
        if (utils.SessionManager.isLoggedIn()) {
            role = utils.SessionManager.getCurrentUser().getVaiTro();
        }
        lblAdmin.setText("Vai trò: " + role);

        SidebarItem menuTrangChu = createSidebarItem("Trang Chủ", "TrangChu");
        pnlMenu.add(menuTrangChu);
        pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
        
        if ("Admin".equalsIgnoreCase(role)) {
            pnlMenu.add(createSidebarItem("Quản Lý Nhân Viên", "QuanLyNhanVien"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Quản Lý Bác Sỹ", "QuanLyBacSy"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Quản Lý Bệnh Nhân", "QuanLyBenhNhan"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Quản Lý Khoa Phòng", "QuanLyKhoaPhong"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Quản Lý Giường Bệnh", "QuanLyGiuong"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Quản Lý Lần Khám", "KhamBenh"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Phiếu Nhập Viện", "NhapVien"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Bệnh Đã Phát Hiện", "BenhDaPhatHien"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Tra Cứu Nâng Cao", "TraCuu"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Báo Cáo Thống Kê", "BaoCao"));
        } else if ("BacSy".equalsIgnoreCase(role)) {
            pnlMenu.add(createSidebarItem("Quản Lý Bệnh Nhân", "QuanLyBenhNhan"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Quản Lý Lần Khám", "KhamBenh"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Bệnh Đã Phát Hiện", "BenhDaPhatHien"));
        } else if ("LeTan".equalsIgnoreCase(role)) {
            pnlMenu.add(createSidebarItem("Quản Lý Bệnh Nhân", "QuanLyBenhNhan"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Quản Lý Giường Bệnh", "QuanLyGiuong"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Quản Lý Lần Khám", "KhamBenh"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Phiếu Nhập Viện", "NhapVien"));
            pnlMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlMenu.add(createSidebarItem("Tra Cứu Nâng Cao", "TraCuu"));
        }
        
        JScrollPane scrollMenu = new JScrollPane(pnlMenu);
        scrollMenu.setBorder(null);
        scrollMenu.setOpaque(false);
        scrollMenu.getViewport().setOpaque(false);
        pnlSidebar.add(scrollMenu, BorderLayout.CENTER);
        
        // Logout
        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBackground(COLOR_SIDEBAR);
        pnlBottom.setBorder(new EmptyBorder(15, 15, 25, 15));
        
        SidebarItem btnLogout = new SidebarItem("Đăng Xuất", "");
        btnLogout.setForeground(new Color(255, 99, 71)); // Màu cam đỏ (Tomato) rực rỡ
        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new FrmDangNhap().setVisible(true);
            }
        });
        pnlBottom.add(btnLogout, BorderLayout.CENTER);
        pnlSidebar.add(pnlBottom, BorderLayout.SOUTH);
        
        add(pnlSidebar, BorderLayout.WEST);
        
        // --- CARD LAYOUT ---
        cardLayout = new CardLayout();
        pnlCard = new JPanel(cardLayout);
        pnlCard.setBackground(COLOR_BG);
        
        // --- TRANG CHỦ ---
        JPanel pnlTrangChu = new JPanel(new BorderLayout());
        pnlTrangChu.setBackground(COLOR_BG);
        
        JPanel pnlCenterBg = new JPanel() {
            Image bgImg;
            {
                try {
                    java.net.URL url = getClass().getResource("/images/hospital_cover.jpg");
                    if (url != null) bgImg = new ImageIcon(url).getImage();
                } catch(Exception e){}
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImg != null) {
                    // Vẽ ảnh full màn hình (ảnh mới đã có sẵn bầu trời trống phía trên)
                    g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        pnlCenterBg.setLayout(new BorderLayout());
        pnlCenterBg.setOpaque(true);
        
        // Bảng chứa chữ (KHÔNG nền mờ)
        JPanel pnlTextWrapper = new JPanel();
        pnlTextWrapper.setOpaque(false);
        pnlTextWrapper.setLayout(new BoxLayout(pnlTextWrapper, BoxLayout.Y_AXIS));

        JLabel lblWelcome = new JLabel("HỆ THỐNG QUẢN LÝ BỆNH VIỆN", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 42));
        lblWelcome.setForeground(new Color(26, 35, 45)); // Màu đen xanh navy đậm (tương phản tốt với bầu trời)
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblWelcome2 = new JLabel("ĐA KHOA QUỐC TẾ THU CÚC", SwingConstants.CENTER);
        lblWelcome2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 50)); // Font chữ sang trọng
        lblWelcome2.setForeground(new Color(0, 150, 60)); // Màu xanh lục nổi bật
        lblWelcome2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pnlTextWrapper.add(lblWelcome);
        pnlTextWrapper.add(Box.createVerticalStrut(15));
        pnlTextWrapper.add(lblWelcome2);

        // Đẩy toàn bộ chữ lên ĐỈNH màn hình (phần không gian bầu trời của ảnh mới)
        pnlCenterBg.add(Box.createVerticalStrut(60), BorderLayout.NORTH); // Cách mép trên 60px
        pnlCenterBg.add(pnlTextWrapper, BorderLayout.CENTER); // Phần text chiếm không gian giữa
        
        // Tạo một khoảng trống cực lớn ở dưới để ép cụm text lên trên cùng
        JPanel pnlBottomSpacer = new JPanel();
        pnlBottomSpacer.setOpaque(false);
        pnlBottomSpacer.setPreferredSize(new Dimension(10, 450)); // Chiều cao 450px đẩy text lên bầu trời
        pnlCenterBg.add(pnlBottomSpacer, BorderLayout.SOUTH);
        
        pnlTrangChu.add(pnlCenterBg, BorderLayout.CENTER);
        
        // Add Panels
        pnlCard.add(pnlTrangChu, "TrangChu");
        pnlCard.add(new FrmQuanLyBenhNhan(), "QuanLyBenhNhan");
        pnlCard.add(new FrmQuanLyBacSy(), "QuanLyBacSy");
        pnlCard.add(new FrmQuanLyKhoaPhong(), "QuanLyKhoaPhong");
        pnlCard.add(new FrmQuanLyGiuong(), "QuanLyGiuong");
        pnlCard.add(new FrmQuanLyNhanVien(), "QuanLyNhanVien");
        pnlCard.add(new FrmKhamBenh(), "KhamBenh");
        pnlCard.add(new FrmNhapVien(), "NhapVien");
        pnlCard.add(new FrmBenhDaPhatHien(), "BenhDaPhatHien");
        pnlCard.add(new FrmTraCuu(), "TraCuu");
        pnlCard.add(new FrmBaoCao(), "BaoCao");
        
        add(pnlCard, BorderLayout.CENTER);
        
        // Set Default Active
        setActiveItem(menuTrangChu);
    }
    
    private SidebarItem createSidebarItem(String text, String panelName) {
        SidebarItem item = new SidebarItem(text, panelName);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!panelName.isEmpty()) {
                    cardLayout.show(pnlCard, panelName);
                    setActiveItem(item);
                }
            }
        });
        return item;
    }
    
    private void setActiveItem(SidebarItem item) {
        if (activeItem != null) {
            activeItem.setActive(false);
        }
        activeItem = item;
        activeItem.setActive(true);
    }
    
    public void switchPanel(String panelName) {
        cardLayout.show(pnlCard, panelName);
        // Highlight menu tương ứng nếu cần
        for (Component c : ((JPanel)((BorderLayout)((JPanel)getContentPane().getComponent(0)).getLayout()).getLayoutComponent(BorderLayout.WEST)).getComponents()) {
            if (c instanceof JPanel) {
                for (Component menuC : ((JPanel)c).getComponents()) {
                    if (menuC instanceof SidebarItem) {
                        SidebarItem item = (SidebarItem) menuC;
                        if (item.panelName.equals(panelName)) {
                            if (activeItem != null) activeItem.setActive(false);
                            activeItem = item;
                            item.setActive(true);
                        }
                    }
                }
            }
        }
    }

    private JPanel createStatCard(String title, String value, Color colorAccent) {
        JPanel pnl = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Đổ bóng (shadow)
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fill(new RoundRectangle2D.Float(2, 5, getWidth() - 4, getHeight() - 5, 15, 15));
                
                // Nền chính
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 4, getHeight() - 5, 15, 15));
                
                // Viền màu accent nhỏ trên cùng (top border)
                g2.setColor(colorAccent);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 4, 8, 15, 15)); // bo góc trên
                g2.fillRect(0, 4, getWidth() - 4, 4); // lấp đi viền bo góc dưới của thanh màu này
                
                g2.dispose();
            }
        };
        pnl.setLayout(new BorderLayout());
        pnl.setOpaque(false);
        pnl.setPreferredSize(new Dimension(210, 130));
        pnl.setBorder(new EmptyBorder(30, 20, 20, 20));
        
        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblValue.setForeground(colorAccent);
        
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblTitle.setForeground(COLOR_CONTENT_TEXT_DARK);
        
        pnl.add(lblValue, BorderLayout.CENTER);
        pnl.add(lblTitle, BorderLayout.SOUTH);
        return pnl;
    }
    
    // Custom Label làm nút bấm Sidebar
    class SidebarItem extends JLabel {
        private boolean isActive = false;
        private boolean isHover = false;
        private String panelName;
        
        public SidebarItem(String text, String panelName) {
            super("  " + text);
            this.panelName = panelName;
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setForeground(COLOR_SIDEBAR_TEXT);
            setPreferredSize(new Dimension(250, 45));
            setMaximumSize(new Dimension(250, 45));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    isHover = true;
                    repaint();
                }
                public void mouseExited(MouseEvent e) {
                    isHover = false;
                    repaint();
                }
            });
        }
        
        public void setActive(boolean active) {
            this.isActive = active;
            if (active) {
                setForeground(Color.WHITE);
            } else {
                setForeground(COLOR_SIDEBAR_TEXT);
            }
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Fix: Sử dụng biến isActive và isHover để quyết định vẽ background
            if (isActive) {
                g2.setColor(COLOR_PRIMARY);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
            } else if (isHover) {
                g2.setColor(COLOR_HOVER);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
            }
            
            g2.dispose();
            super.paintComponent(g);
        }
        
        @Override
        public boolean isOpaque() {
            return false; // Phải return false để Swing không vẽ đè background mặc định hình chữ nhật
        }
    }
}
