package gui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class FrmDangNhap extends JFrame {
    
    // Màu sắc chủ đạo (60-30-10)
    private final Color COLOR_BG = new Color(255, 255, 255); // 60% Nền trắng
    private final Color COLOR_TEXT_PRIMARY = new Color(51, 51, 51); // Chữ chính
    private final Color COLOR_TEXT_SECONDARY = new Color(119, 119, 119); // 30% Chữ phụ/Gợi ý
    private final Color COLOR_PRIMARY = new Color(0, 168, 107); // 10% Xanh ngọc (CTA)
    private final Color COLOR_PRIMARY_HOVER = new Color(0, 145, 90);

    public FrmDangNhap() {
        setTitle("Đăng Nhập - Hệ Thống Quản Lý TCI");
        setSize(420, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_BG);

        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBackground(COLOR_BG);
        pnlMain.setBorder(new EmptyBorder(40, 40, 40, 40));

        // --- LOGO ---
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon("src/images/logo.png");
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("TCI");
            lblLogo.setFont(new Font("SansSerif", Font.BOLD, 32));
            lblLogo.setForeground(COLOR_PRIMARY);
        }
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- TITLE ---
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(COLOR_TEXT_PRIMARY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubTitle = new JLabel("Bệnh Viện Đa Khoa Quốc Tế Thu Cúc", SwingConstants.CENTER);
        lblSubTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubTitle.setForeground(COLOR_TEXT_SECONDARY);
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- FORM PANEL ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(COLOR_BG);
        pnlForm.setMaximumSize(new Dimension(340, 150));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0); 
        gbc.weightx = 1.0;

        JTextField txtUser = createCustomTextField("Tài khoản");
        JPasswordField txtPass = createCustomPasswordField("Mật khẩu");

        gbc.gridy = 0; pnlForm.add(txtUser, gbc);
        gbc.gridy = 1; pnlForm.add(txtPass, gbc);

        // --- LOGIN BUTTON ---
        JButton btnLogin = createCustomButton("ĐĂNG NHẬP");
        
        java.awt.event.ActionListener loginAction = (ActionEvent e) -> {
            String u = txtUser.getText();
            String p = new String(txtPass.getPassword());
            if (u.isEmpty() || p.isEmpty() || u.equals("Tài khoản") || p.equals("Mật khẩu")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dao.TaiKhoanDAO tkDao = new dao.TaiKhoanDAO();
            model.TaiKhoan tk = tkDao.authenticate(u, p);
            if (tk != null) {
                utils.SessionManager.setCurrentUser(tk);
                this.dispose();
                new FrmMain().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        };
        
        btnLogin.addActionListener(loginAction);
        txtPass.addActionListener(loginAction); // Bấm Enter ở pass để login
        txtUser.addActionListener(e -> txtPass.requestFocus()); // Bấm Enter ở user để sang pass

        // --- HINT ---
        JLabel lblHint = new JLabel("Tài khoản mặc định: admin | admin");
        lblHint.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblHint.setForeground(new Color(180, 180, 180));
        lblHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- GẮN VÀO MAIN PANEL ---
        pnlMain.add(lblLogo);
        pnlMain.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlMain.add(lblTitle);
        pnlMain.add(Box.createRigidArea(new Dimension(0, 5)));
        pnlMain.add(lblSubTitle);
        pnlMain.add(Box.createRigidArea(new Dimension(0, 30)));
        pnlMain.add(pnlForm);
        pnlMain.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlMain.add(btnLogin);
        pnlMain.add(Box.createRigidArea(new Dimension(0, 15)));
        pnlMain.add(lblHint);

        add(pnlMain);
    }

    private JTextField createCustomTextField(String placeholder) {
        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(340, 45));
        txt.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txt.setForeground(COLOR_TEXT_PRIMARY);
        txt.setCaretColor(COLOR_PRIMARY);
        txt.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, new Color(220, 220, 220)),
            new EmptyBorder(5, 15, 5, 15)
        ));
        
        // Placeholder text implementation
        txt.setText(placeholder);
        txt.setForeground(Color.LIGHT_GRAY);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txt.getText().equals(placeholder)) {
                    txt.setText("");
                    txt.setForeground(COLOR_TEXT_PRIMARY);
                    txt.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(8, COLOR_PRIMARY),
                        new EmptyBorder(5, 15, 5, 15)
                    ));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txt.getText().isEmpty()) {
                    txt.setForeground(Color.LIGHT_GRAY);
                    txt.setText(placeholder);
                }
                txt.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, new Color(220, 220, 220)),
                    new EmptyBorder(5, 15, 5, 15)
                ));
            }
        });
        return txt;
    }

    private JPasswordField createCustomPasswordField(String placeholder) {
        JPasswordField txt = new JPasswordField();
        txt.setPreferredSize(new Dimension(340, 45));
        txt.setFont(new Font("SansSerif", Font.PLAIN, 15));
        txt.setForeground(COLOR_TEXT_PRIMARY);
        txt.setCaretColor(COLOR_PRIMARY);
        txt.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, new Color(220, 220, 220)),
            new EmptyBorder(5, 15, 5, 15)
        ));
        
        txt.setEchoChar((char)0);
        txt.setText(placeholder);
        txt.setForeground(Color.LIGHT_GRAY);
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                String pass = new String(txt.getPassword());
                if (pass.equals(placeholder)) {
                    txt.setText("");
                    txt.setForeground(COLOR_TEXT_PRIMARY);
                    txt.setEchoChar('•');
                    txt.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(8, COLOR_PRIMARY),
                        new EmptyBorder(5, 15, 5, 15)
                    ));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                String pass = new String(txt.getPassword());
                if (pass.isEmpty()) {
                    txt.setForeground(Color.LIGHT_GRAY);
                    txt.setEchoChar((char)0);
                    txt.setText(placeholder);
                }
                txt.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, new Color(220, 220, 220)),
                    new EmptyBorder(5, 15, 5, 15)
                ));
            }
        });
        return txt;
    }

    private JButton createCustomButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(COLOR_PRIMARY_HOVER);
                else if (getModel().isRollover()) g2.setColor(new Color(0, 180, 115));
                else g2.setColor(COLOR_PRIMARY);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setPreferredSize(new Dimension(340, 45));
        btn.setMaximumSize(new Dimension(340, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Lớp nội bộ để tạo viền bo góc cho Text Field
    class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;
        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }
    }
}
