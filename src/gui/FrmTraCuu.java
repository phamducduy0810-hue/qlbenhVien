package gui;

import javax.swing.*;
import java.awt.*;

public class FrmTraCuu extends JPanel {
    public FrmTraCuu() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        JLabel lblTitle = new JLabel("TRA CỨU NÂNG CAO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 168, 107));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JPanel pnlTop = new JPanel();
        pnlTop.setOpaque(false);
        pnlTop.add(new JLabel("Từ khóa: "));
        pnlTop.add(new JTextField(20));
        JButton btnSearch = new JButton("Tìm Kiếm");
        btnSearch.setBackground(new Color(0, 168, 107));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setOpaque(true);
        btnSearch.setBorderPainted(false);
        pnlTop.add(btnSearch);
        
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setOpaque(false);
        pnlNorth.add(lblTitle, BorderLayout.NORTH);
        pnlNorth.add(pnlTop, BorderLayout.CENTER);
        
        String[] cols = {"Mã", "Tên/Nội Dung", "Loại", "Ngày"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        add(pnlNorth, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        
        JTextField txtKeyword = (JTextField) pnlTop.getComponent(1);
        
        btnSearch.addActionListener(e -> {
            model.setRowCount(0);
            String keyword = txtKeyword.getText().toLowerCase();
            try {
                for (model.BenhNhan bn : new dao.BenhNhanDAO().getAll()) {
                    if (bn.getMaSo().toLowerCase().contains(keyword) || bn.getCmnd().toLowerCase().contains(keyword) || bn.getDiaChi().toLowerCase().contains(keyword)) {
                        model.addRow(new Object[]{bn.getMaSo(), "Bệnh nhân (CMND: " + bn.getCmnd() + " - " + bn.getDiaChi() + ")", "Bệnh Nhân", bn.getNgayDangKy()});
                    }
                }
                for (model.BacSy bs : new dao.BacSyDAO().getAll()) {
                    if (bs.getMaSo().toLowerCase().contains(keyword) || bs.getChuyenNganh().toLowerCase().contains(keyword)) {
                        model.addRow(new Object[]{bs.getMaSo(), "Bác sỹ (" + bs.getChuyenNganh() + ")", "Bác Sỹ", ""});
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Auto load initial data
        btnSearch.doClick();
    }
}
