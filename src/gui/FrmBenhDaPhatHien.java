package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import dao.BenhDaPhatHienDAO;
import model.BenhDaPhatHien;

public class FrmBenhDaPhatHien extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private BenhDaPhatHienDAO dao = new BenhDaPhatHienDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FrmBenhDaPhatHien() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));

        JLabel lblTitle = new JLabel("QUẢN LÝ BỆNH ĐÃ PHÁT HIỆN", SwingConstants.CENTER);
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
        pnlSearch.add(new JLabel("Tìm mã bệnh:"));
        JTextField txtSearch = new JTextField(15);
        JButton btnSearch = createToolButton("Tìm kiếm");
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);

        pnlToolbar.add(pnlActions, BorderLayout.WEST);
        pnlToolbar.add(pnlSearch, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new Object[]{"Mã Bệnh", "Mã Bệnh Nhân", "Tên Bệnh", "Ngày Phát Hiện"}, 0);
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
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
                return;
            }
            int maBenh = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            BenhDaPhatHien selected = null;
            for(BenhDaPhatHien b : dao.getAll()) {
                if(b.getMaBenh() == maBenh) {
                    selected = b; break;
                }
            }
            if (selected != null) showFormDialog(selected);
        });
        
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
                return;
            }
            int maBenh = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            if (JOptionPane.showConfirmDialog(this, "Xóa bệnh mã " + maBenh + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (dao.delete(maBenh)) {
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
            for (BenhDaPhatHien b : dao.getAll()) {
                tableModel.addRow(new Object[]{
                    b.getMaBenh(), b.getMaBenhNhan(), b.getTenBenh(), 
                    b.getNgayPhatHien() != null ? sdf.format(b.getNgayPhatHien()) : ""
                });
            }
        } catch (Exception e) {}
    }
    
    private void showFormDialog(BenhDaPhatHien b) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), b == null ? "Thêm Bệnh" : "Sửa Bệnh", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 10, 15));
        pnlInput.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField txtMaBenh = new JTextField();
        JTextField txtMaBenhNhan = new JTextField();
        JTextField txtTenBenh = new JTextField();
        JTextField txtNgay = new JTextField();
        
        if (b != null) {
            txtMaBenh.setText(String.valueOf(b.getMaBenh())); txtMaBenh.setEditable(false);
            txtMaBenhNhan.setText(b.getMaBenhNhan());
            txtTenBenh.setText(b.getTenBenh());
            txtNgay.setText(b.getNgayPhatHien() != null ? sdf.format(b.getNgayPhatHien()) : "");
        }
        
        pnlInput.add(new JLabel("Mã Bệnh (ID):")); pnlInput.add(txtMaBenh);
        pnlInput.add(new JLabel("Mã Bệnh Nhân:")); pnlInput.add(txtMaBenhNhan);
        pnlInput.add(new JLabel("Tên Bệnh:")); pnlInput.add(txtTenBenh);
        pnlInput.add(new JLabel("Ngày Phát Hiện:")); pnlInput.add(txtNgay);
        
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
                BenhDaPhatHien newB = new BenhDaPhatHien(Integer.parseInt(txtMaBenh.getText()), txtMaBenhNhan.getText(), 
                    txtTenBenh.getText(), sdf.parse(txtNgay.getText()));
                if (b == null) {
                    if (dao.insert(newB)) { JOptionPane.showMessageDialog(dialog, "Thêm thành công"); loadData(); dialog.dispose(); }
                } else {
                    if (dao.update(newB)) { JOptionPane.showMessageDialog(dialog, "Sửa thành công"); loadData(); dialog.dispose(); }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi dữ liệu (Mã phải là số, Ngày yyyy-MM-dd)!");
            }
        });
        
        pnlButtons.add(btnSave); pnlButtons.add(btnCancel);
        dialog.setLayout(new BorderLayout());
        dialog.add(pnlInput, BorderLayout.CENTER);
        dialog.add(pnlButtons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
