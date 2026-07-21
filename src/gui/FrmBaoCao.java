package gui;

import dao.ThongKeDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FrmBaoCao extends JPanel {
    private JSpinner spnFrom;
    private JSpinner spnTo;
    private DefaultTableModel model;
    private ThongKeDAO dao;
    private Map<String, Integer> top5Benh;

    public FrmBaoCao() {
        dao = new ThongKeDAO();
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        JLabel lblTitle = new JLabel("THỐNG KÊ & BÁO CÁO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 168, 107));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlTop.setOpaque(false);
        pnlTop.add(new JLabel("Từ ngày: "));
        spnFrom = new JSpinner(new SpinnerDateModel());
        spnFrom.setEditor(new JSpinner.DateEditor(spnFrom, "yyyy-MM-dd"));
        pnlTop.add(spnFrom);
        
        pnlTop.add(new JLabel("Đến ngày: "));
        spnTo = new JSpinner(new SpinnerDateModel());
        spnTo.setEditor(new JSpinner.DateEditor(spnTo, "yyyy-MM-dd"));
        pnlTop.add(spnTo);
        
        JButton btnReport = new JButton("Lập Báo Cáo");
        btnReport.setBackground(new Color(0, 168, 107));
        btnReport.setForeground(Color.WHITE);
        btnReport.setOpaque(true);
        btnReport.setBorderPainted(false);
        pnlTop.add(btnReport);

        JButton btnExport = new JButton("Xuất CSV");
        btnExport.setBackground(new Color(41, 128, 185));
        btnExport.setForeground(Color.WHITE);
        btnExport.setOpaque(true);
        btnExport.setBorderPainted(false);
        pnlTop.add(btnExport);
        
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setOpaque(false);
        pnlNorth.add(lblTitle, BorderLayout.NORTH);
        pnlNorth.add(pnlTop, BorderLayout.CENTER);
        
        String[] cols = {"Mục Thống Kê", "Số Lượng", "Ghi Chú"};
        model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Panel biểu đồ
        JPanel pnlChart = new ChartPanel();
        pnlChart.setPreferredSize(new Dimension(0, 300));
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll, pnlChart);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);
        
        add(pnlNorth, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        
        btnReport.addActionListener(e -> loadData());
        btnExport.addActionListener(e -> exportToCSV());
        
        // Load default (last 30 days to today)
        java.util.Calendar cal = java.util.Calendar.getInstance();
        spnTo.setValue(cal.getTime());
        cal.add(java.util.Calendar.DAY_OF_MONTH, -30);
        spnFrom.setValue(cal.getTime());
        
        loadData();
    }
    
    private Map<String, Integer> trendThang;

    private String getTrendText(int current, int previous) {
        if (previous == 0) return current > 0 ? "+100% so với kỳ trước" : "Không đổi";
        double pct = (current - previous) * 100.0 / previous;
        if (pct > 0) return String.format("+%.1f%% so với kỳ trước", pct);
        if (pct < 0) return String.format("%.1f%% so với kỳ trước", pct);
        return "Không đổi";
    }

    private void loadData() {
        model.setRowCount(0);
        Date fromDate = (Date) spnFrom.getValue();
        Date toDate = (Date) spnTo.getValue();
        
        if (fromDate.after(toDate)) {
            JOptionPane.showMessageDialog(this, "Từ ngày không được lớn hơn Đến ngày!");
            return;
        }

        try {
            // Tính số ngày trong kỳ hiện tại
            long diffMillis = toDate.getTime() - fromDate.getTime();
            
            // Kỳ trước
            Date prevTo = new Date(fromDate.getTime() - 24L * 60 * 60 * 1000);
            Date prevFrom = new Date(prevTo.getTime() - diffMillis);

            int countBN = dao.getSoBenhNhanMoi(fromDate, toDate);
            int prevBN = dao.getSoBenhNhanMoi(prevFrom, prevTo);

            int countLanKham = dao.getSoLanKham(fromDate, toDate);
            int prevLanKham = dao.getSoLanKham(prevFrom, prevTo);

            int countNhapVien = dao.getSoCaNhapVien(fromDate, toDate);
            int prevNhapVien = dao.getSoCaNhapVien(prevFrom, prevTo);

            String tyLeGiuong = dao.getTyLeSuDungGiuong();
            
            int tongBacSy = dao.getTongBacSy();
            int tongNhanVien = dao.getTongNhanVien();
            
            model.addRow(new Object[]{"Số bệnh nhân mới", countBN, getTrendText(countBN, prevBN)});
            model.addRow(new Object[]{"Số lần khám", countLanKham, getTrendText(countLanKham, prevLanKham)});
            model.addRow(new Object[]{"Số ca nhập viện", countNhapVien, getTrendText(countNhapVien, prevNhapVien)});
            model.addRow(new Object[]{"Tỷ lệ sử dụng giường", tyLeGiuong, "Công suất giường hiện tại"});
            model.addRow(new Object[]{"Tổng số Bác Sỹ", tongBacSy, "Snapshot hiện tại (tĩnh)"});
            model.addRow(new Object[]{"Tổng số Nhân Viên", tongNhanVien, "Snapshot hiện tại (tĩnh)"});
            
            trendThang = dao.getLuotKhamTheoThang(fromDate, toDate);
            repaint();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu thống kê!");
        }
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu Báo Cáo CSV");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().endsWith(".csv")) {
                file = new File(file.getAbsolutePath() + ".csv");
            }
            try (PrintWriter pw = new PrintWriter(file, "UTF-8")) {
                // BOM for UTF-8 Excel
                pw.write('\ufeff');
                pw.println("Mục Thống Kê,Số Lượng,Ghi Chú");
                for (int i = 0; i < model.getRowCount(); i++) {
                    pw.println(model.getValueAt(i, 0) + "," + 
                               model.getValueAt(i, 1) + "," + 
                               model.getValueAt(i, 2));
                }
                pw.println();
                pw.println("Xu Hướng Lượt Khám Theo Tháng:");
                pw.println("Tháng,Số Lượt");
                if (trendThang != null) {
                    for (Map.Entry<String, Integer> entry : trendThang.entrySet()) {
                        pw.println(entry.getKey() + "," + entry.getValue());
                    }
                }
                JOptionPane.showMessageDialog(this, "Xuất báo cáo thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file!");
            }
        }
    }

    // Lớp nội bộ để vẽ biểu đồ đường/cột Xu hướng lượt khám
    class ChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            g2.setColor(new Color(50, 50, 50));
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2.drawString("Biểu Đồ Xu Hướng Lượt Khám Theo Tháng", 20, 30);
            
            if (trendThang == null || trendThang.isEmpty()) {
                g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
                g2.drawString("Không có dữ liệu trong khoảng thời gian này.", 20, 60);
                return;
            }
            
            int max = trendThang.values().stream().max(Integer::compareTo).orElse(1);
            if (max == 0) max = 1;
            
            int startX = 60;
            int startY = getHeight() - 40;
            int maxBarHeight = getHeight() - 100;
            int gap = 20;
            int barWidth = Math.max(30, (getWidth() - 100) / trendThang.size() - gap);
            
            int x = startX;
            for (Map.Entry<String, Integer> entry : trendThang.entrySet()) {
                String thang = entry.getKey();
                int val = entry.getValue();
                int height = (int) ((double) val / max * maxBarHeight);
                if (height < 5 && val > 0) height = 5;
                
                // Vẽ cột
                g2.setColor(new Color(41, 128, 185)); // Xanh dương
                g2.fillRect(x, startY - height, barWidth, height);
                
                // Vẽ số lượng
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(String.valueOf(val));
                g2.drawString(String.valueOf(val), x + (barWidth - textWidth) / 2, startY - height - 5);
                
                // Vẽ tên tháng
                g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                textWidth = fm.stringWidth(thang);
                g2.drawString(thang, x + (barWidth - textWidth) / 2, startY + 15);
                
                x += barWidth + gap;
            }
        }
    }
}
