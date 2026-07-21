package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import dao.PhieuNhapVienDAO;
import model.PhieuNhapVien;

public class FrmNhapVien extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private PhieuNhapVienDAO dao = new PhieuNhapVienDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FrmNhapVien() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));

        JLabel lblTitle = new JLabel("QUẢN LÝ PHIẾU NHẬP VIỆN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlToolbar = new JPanel(new BorderLayout());
        pnlToolbar.setOpaque(false);
        pnlToolbar.setBorder(new EmptyBorder(0, 20, 10, 20));

        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlActions.setOpaque(false);
        JButton btnAdd = createToolButton("Thêm Mới");
        JButton btnUpdate = createToolButton("Cập Nhật");
        JButton btnDelete = createToolButton("Xóa");
        JButton btnLoad = createToolButton("Làm Mới");
        
        pnlActions.add(btnAdd);
        pnlActions.add(btnUpdate);
        pnlActions.add(btnDelete);
        pnlActions.add(btnLoad);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Tìm mã phiếu:"));
        JTextField txtSearch = new JTextField(15);
        JButton btnSearch = createToolButton("Tìm kiếm");
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);

        pnlToolbar.add(pnlActions, BorderLayout.WEST);
        pnlToolbar.add(pnlSearch, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new Object[]{"Mã Phiếu", "Mã Bệnh Nhân", "Số Giường", "Ngày Nhận", "Ngày Trả"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(173, 216, 230));
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(230, 230, 230));
        
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(new EmptyBorder(0, 20, 20, 20));
        pnlTable.setOpaque(false);
        pnlTable.add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setOpaque(false);
        pnlCenter.add(pnlToolbar, BorderLayout.NORTH);
        pnlCenter.add(pnlTable, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> showFormDialog(null));
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu cần sửa!");
                return;
            }
            int maPhieu = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            PhieuNhapVien selected = null;
            for(PhieuNhapVien p : dao.getAll()) {
                if(p.getMaPhieu() == maPhieu) {
                    selected = p; break;
                }
            }
            if (selected != null) showFormDialog(selected);
        });
        
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu cần xóa!");
                return;
            }
            int maPhieu = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            if (JOptionPane.showConfirmDialog(this, "Xóa phiếu " + maPhieu + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (dao.delete(maPhieu)) {
                    JOptionPane.showMessageDialog(this, "Xoá thành công!");
                    loadData();
                }
            }
        });
        
        btnLoad.addActionListener(e -> loadData());
        loadData();
    }
    
    private JButton createToolButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            for (PhieuNhapVien p : dao.getAll()) {
                tableModel.addRow(new Object[]{
                    p.getMaPhieu(), p.getMaBenhNhan(), p.getSoGiuong(), 
                    p.getNgayNhan() != null ? sdf.format(p.getNgayNhan()) : "",
                    p.getNgayTra() != null ? sdf.format(p.getNgayTra()) : ""
                });
            }
        } catch (Exception e) {}
    }
    
    private void showFormDialog(PhieuNhapVien p) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), p == null ? "Thêm Phiếu" : "Sửa Phiếu", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlInput = new JPanel(new GridLayout(5, 2, 10, 15));
        pnlInput.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField txtMaPhieu = new JTextField();
        JTextField txtMaBenhNhan = new JTextField();
        JTextField txtSoGiuong = new JTextField();
        JTextField txtNgayNhan = new JTextField();
        JTextField txtNgayTra = new JTextField();
        
        if (p != null) {
            txtMaPhieu.setText(String.valueOf(p.getMaPhieu())); txtMaPhieu.setEditable(false);
            txtMaBenhNhan.setText(p.getMaBenhNhan());
            txtSoGiuong.setText(p.getSoGiuong());
            txtNgayNhan.setText(p.getNgayNhan() != null ? sdf.format(p.getNgayNhan()) : "");
            txtNgayTra.setText(p.getNgayTra() != null ? sdf.format(p.getNgayTra()) : "");
        }
        
        pnlInput.add(new JLabel("Mã Phiếu (ID):")); pnlInput.add(txtMaPhieu);
        pnlInput.add(new JLabel("Mã Bệnh Nhân:")); pnlInput.add(txtMaBenhNhan);
        pnlInput.add(new JLabel("Số Giường:")); pnlInput.add(txtSoGiuong);
        pnlInput.add(new JLabel("Ngày Nhận (Y-M-D):")); pnlInput.add(txtNgayNhan);
        pnlInput.add(new JLabel("Ngày Trả (Y-M-D):")); pnlInput.add(txtNgayTra);
        
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnSave = new JButton("💾 Lưu thông tin");
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);
        btnSave.setOpaque(true);
        btnSave.setBorderPainted(false);
        btnSave.setFocusPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JButton btnCancel = new JButton("❌ Đóng");
        btnCancel.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCancel.setBackground(new Color(231, 76, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setOpaque(true);
        btnCancel.setBorderPainted(false);
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCancel.addActionListener(e -> dialog.dispose());
        btnSave.addActionListener(e -> {
            try {
                PhieuNhapVien newP = new PhieuNhapVien(Integer.parseInt(txtMaPhieu.getText()), txtMaBenhNhan.getText(), 
                    txtSoGiuong.getText(), sdf.parse(txtNgayNhan.getText()), 
                    txtNgayTra.getText().isEmpty() ? null : sdf.parse(txtNgayTra.getText()));
                if (p == null) {
                    if (dao.insert(newP)) { JOptionPane.showMessageDialog(dialog, "Thêm thành công"); loadData(); dialog.dispose(); }
                } else {
                    // if (dao.update(newP)) { JOptionPane.showMessageDialog(dialog, "Sửa thành công"); loadData(); dialog.dispose(); }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi định dạng dữ liệu (Ngày yyyy-MM-dd)!");
            }
        });
        
        pnlButtons.add(btnSave); pnlButtons.add(btnCancel);
        dialog.setLayout(new BorderLayout());
        dialog.add(pnlInput, BorderLayout.CENTER);
        dialog.add(pnlButtons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
