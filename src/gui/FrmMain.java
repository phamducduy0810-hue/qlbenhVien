package gui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmMain extends JFrame {
    public FrmMain() {
        setTitle("Hệ Thống Quản Lý Bệnh Viện Đa Khoa Thu Cúc");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Use native macOS Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        // Tạo Menu (Không chỉnh sửa Font toàn hệ thống nữa để tránh lỗi macOS)
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuQuanLy = new JMenu("Quản Lý Danh Mục");
        
        JMenuItem mnuBenhNhan = new JMenuItem("Quản Lý Bệnh Nhân");
        mnuBenhNhan.addActionListener(e -> new FrmQuanLyBenhNhan().setVisible(true));
        
        JMenuItem mnuBacSy = new JMenuItem("Quản Lý Bác Sỹ");
        mnuBacSy.addActionListener(e -> new FrmQuanLyBacSy().setVisible(true));
        
        JMenuItem mnuKhoaPhong = new JMenuItem("Quản Lý Khoa Phòng");
        mnuKhoaPhong.addActionListener(e -> new FrmQuanLyKhoaPhong().setVisible(true));
        
        JMenuItem mnuGiuong = new JMenuItem("Quản Lý Giường Bệnh");
        mnuGiuong.addActionListener(e -> new FrmQuanLyGiuong().setVisible(true));
        
        menuQuanLy.add(mnuBenhNhan);
        menuQuanLy.addSeparator();
        menuQuanLy.add(mnuBacSy);
        menuQuanLy.addSeparator();
        menuQuanLy.add(mnuKhoaPhong);
        menuQuanLy.addSeparator();
        menuQuanLy.add(mnuGiuong);
        
        menuBar.add(menuQuanLy);
        setJMenuBar(menuBar);
        
        // Thân trang
        JPanel pnlBody = new JPanel(new BorderLayout());
        pnlBody.setBackground(new Color(240, 248, 255)); // Màu xanh nhạt dễ chịu
        
        JLabel lblWelcome = new JLabel("HỆ THỐNG QUẢN LÝ BỆNH VIỆN THU CÚC", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblWelcome.setForeground(new Color(0, 102, 204));
        lblWelcome.setBorder(new EmptyBorder(50, 0, 20, 0));
        
        JLabel lblSub = new JLabel("Chuyên nghiệp - Tận tâm - Hiện đại", SwingConstants.CENTER);
        lblSub.setFont(new Font("SansSerif", Font.ITALIC, 18));
        lblSub.setForeground(new Color(100, 100, 100));
        
        JPanel pnlTitles = new JPanel(new GridLayout(2, 1));
        pnlTitles.setOpaque(false);
        pnlTitles.add(lblWelcome);
        pnlTitles.add(lblSub);
        
        pnlBody.add(pnlTitles, BorderLayout.NORTH);
        add(pnlBody, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmMain().setVisible(true);
        });
    }
}
