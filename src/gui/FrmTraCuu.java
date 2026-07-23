package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import dao.BenhNhanDAO;
import dao.BacSyDAO;
import dao.NhanVienDAO;
import model.BenhNhan;
import model.BacSy;
import model.NhanVien;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FrmTraCuu extends JPanel {
    private JComboBox<String> cbxLoai;
    private JTextField txtKeyword;
    private DefaultTableModel model;
    private JTable table;
    private BenhNhanDAO bnDAO = new BenhNhanDAO();
    private BacSyDAO bsDAO = new BacSyDAO();
    private NhanVienDAO nvDAO = new NhanVienDAO();
    private Map<String, NhanVien> nhanVienMap = new HashMap<>();

    public FrmTraCuu() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        
        JLabel lblTitle = new JLabel("TRA CỨU NÂNG CAO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 168, 107));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlTop.setOpaque(false);
        
        pnlTop.add(new JLabel("Loại:"));
        cbxLoai = new JComboBox<>(new String[]{"Tất cả", "Bệnh Nhân", "Bác Sỹ"});
        pnlTop.add(cbxLoai);

        pnlTop.add(new JLabel("Từ khóa: "));
        txtKeyword = new JTextField(20);
        pnlTop.add(txtKeyword);
        
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
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(173, 216, 230));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        add(pnlNorth, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        
        // Cache NhanVien to get NgayTuyenDung for BacSy
        for (NhanVien nv : nvDAO.getAll()) {
            nhanVienMap.put(nv.getMaSo(), nv);
        }
        
        btnSearch.addActionListener(e -> performSearch());
        
        // Double-click to detail
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        String loai = model.getValueAt(row, 2).toString();
                        if (loai.equals("Bệnh Nhân")) {
                            openFeature("QuanLyBenhNhan");
                        } else if (loai.equals("Bác Sỹ")) {
                            openFeature("QuanLyBacSy");
                        } else if (loai.equals("Nhân Viên")) {
                            openFeature("QuanLyNhanVien");
                        }
                    }
                }
            }
        });

        // Load data automatically on start
        performSearch();
    }
    
    private void performSearch() {
        model.setRowCount(0);
        String keyword = txtKeyword.getText().trim().toLowerCase();
        
        
        String loai = cbxLoai.getSelectedItem().toString();
        
        int MAX_ROWS = 500;
        int rowCount = 0;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (loai.equals("Tất cả") || loai.equals("Bệnh Nhân")) {
                List<BenhNhan> bnList = bnDAO.getAll();
                for (BenhNhan bn : bnList) {
                    if (rowCount >= MAX_ROWS) break;
                    if (bn.getMaSo().toLowerCase().contains(keyword) || 
                        bn.getCmnd().toLowerCase().contains(keyword) || 
                        bn.getDiaChi().toLowerCase().contains(keyword)) {
                        
                        String ngay = bn.getNgayDangKy() != null ? sdf.format(bn.getNgayDangKy()) : "";
                        model.addRow(new Object[]{
                            bn.getMaSo(), 
                            "Bệnh nhân (CMND: " + bn.getCmnd() + " - " + bn.getDiaChi() + ")", 
                            "Bệnh Nhân", 
                            ngay
                        });
                        rowCount++;
                    }
                }
            }
            
            if (loai.equals("Tất cả") || loai.equals("Bác Sỹ")) {
                List<BacSy> bsList = bsDAO.getAll();
                for (BacSy bs : bsList) {
                    if (rowCount >= MAX_ROWS) break;
                    if (bs.getMaSo().toLowerCase().contains(keyword) || 
                        bs.getChuyenNganh().toLowerCase().contains(keyword) ||
                        bs.getDienThoai().toLowerCase().contains(keyword)) {
                        
                        NhanVien nv = nhanVienMap.get(bs.getMaSo());
                        String ngay = "";
                        if (nv != null && nv.getNgayTuyenDung() != null) {
                            ngay = sdf.format(nv.getNgayTuyenDung());
                        }
                        
                        model.addRow(new Object[]{
                            bs.getMaSo(), 
                            "Bác sỹ (" + bs.getChuyenNganh() + ") - SĐT: " + bs.getDienThoai(), 
                            "Bác Sỹ", 
                            ngay
                        });
                        rowCount++;
                    }
                }
            }
            
            if (loai.equals("Tất cả")) {
                List<NhanVien> nvList = nvDAO.getAll();
                for (NhanVien nv : nvList) {
                    if (rowCount >= MAX_ROWS) break;
                    // Bỏ qua nhân viên có chức danh là Bác sỹ để tránh trùng lặp
                    if (nv.getChucDanh() != null && nv.getChucDanh().toLowerCase().contains("bác sỹ")) {
                        continue;
                    }
                    if (nv.getMaSo().toLowerCase().contains(keyword) || 
                        (nv.getChucDanh() != null && nv.getChucDanh().toLowerCase().contains(keyword)) ||
                        (nv.getCmnd() != null && nv.getCmnd().toLowerCase().contains(keyword))) {
                        
                        String ngay = nv.getNgayTuyenDung() != null ? sdf.format(nv.getNgayTuyenDung()) : "";
                        
                        model.addRow(new Object[]{
                            nv.getMaSo(), 
                            "Nhân viên (" + (nv.getChucDanh() != null ? nv.getChucDanh() : "") + ") - CMND: " + nv.getCmnd(), 
                            "Nhân Viên", 
                            ngay
                        });
                        rowCount++;
                    }
                }
            }
            
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tra cứu dữ liệu!");
        }
    }
    
    private void openFeature(String featureName) {
        // Find FrmMain parent and switch panel
        Container parent = this.getParent();
        while (parent != null && !(parent instanceof JFrame)) {
            parent = parent.getParent();
        }
        
        if (parent instanceof FrmMain) {
            FrmMain main = (FrmMain) parent;
            main.switchPanel(featureName);
        } else {
            JOptionPane.showMessageDialog(this, "Đã mở tab chi tiết " + featureName + "!");
        }
    }
}
