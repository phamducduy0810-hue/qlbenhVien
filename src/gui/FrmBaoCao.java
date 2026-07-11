package gui;

import javax.swing.*;
import java.awt.*;

public class FrmBaoCao extends JPanel {
    public FrmBaoCao() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        JLabel lblTitle = new JLabel("THỐNG KÊ & BÁO CÁO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 168, 107));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JPanel pnlTop = new JPanel();
        pnlTop.setOpaque(false);
        pnlTop.add(new JLabel("Từ ngày: "));
        JSpinner spnFrom = new JSpinner(new SpinnerDateModel());
        spnFrom.setEditor(new JSpinner.DateEditor(spnFrom, "yyyy-MM-dd"));
        pnlTop.add(spnFrom);
        
        pnlTop.add(new JLabel("Đến ngày: "));
        JSpinner spnTo = new JSpinner(new SpinnerDateModel());
        spnTo.setEditor(new JSpinner.DateEditor(spnTo, "yyyy-MM-dd"));
        pnlTop.add(spnTo);
        
        JButton btnReport = new JButton("Lập Báo Cáo");
        btnReport.setBackground(new Color(0, 168, 107));
        btnReport.setForeground(Color.WHITE);
        btnReport.setOpaque(true);
        btnReport.setBorderPainted(false);
        pnlTop.add(btnReport);
        
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setOpaque(false);
        pnlNorth.add(lblTitle, BorderLayout.NORTH);
        pnlNorth.add(pnlTop, BorderLayout.CENTER);
        
        String[] cols = {"Mục Thống Kê", "Số Lượng", "Ghi Chú"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        add(pnlNorth, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        
        btnReport.addActionListener(e -> {
            model.setRowCount(0);
            try {
                int countBN = new dao.BenhNhanDAO().getAll().size();
                int countBS = new dao.BacSyDAO().getAll().size();
                int countNV = new dao.NhanVienDAO().getAll().size();
                
                model.addRow(new Object[]{"Tổng số Bệnh Nhân", countBN, "Bệnh nhân đã đăng ký"});
                model.addRow(new Object[]{"Tổng số Bác Sỹ", countBS, "Bác sỹ đang làm việc"});
                model.addRow(new Object[]{"Tổng số Nhân Viên", countNV, "Nhân viên các phòng ban"});
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        // Auto load initial data
        btnReport.doClick();
    }
}
