package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import dao.GiuongDAO;
import model.Giuong;

public class FrmQuanLyGiuong extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private GiuongDAO dao = new GiuongDAO();

    public FrmQuanLyGiuong() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));

        // Tiêu đề form
        JLabel lblTitle = new JLabel("QUẢN LÝ THÔNG TIN GIƯỜNG BỆNH", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Thanh công cụ (Toolbar)
        JPanel pnlToolbar = new JPanel(new BorderLayout());
        pnlToolbar.setOpaque(false);
        pnlToolbar.setBorder(new EmptyBorder(0, 20, 10, 20));

        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlActions.setOpaque(false);
        JButton btnAdd = createToolButton("➕ Thêm");
        JButton btnUpdate = createToolButton("✏️ Sửa");
        JButton btnDelete = createToolButton("❌ Xóa");
        JButton btnLoad = createToolButton("🔄 Làm mới");
        
        pnlActions.add(btnAdd);
        pnlActions.add(btnUpdate);
        pnlActions.add(btnDelete);
        pnlActions.add(btnLoad);

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Tìm theo mã:"));
        JTextField txtSearch = new JTextField(15);
        pnlSearch.add(txtSearch);

        pnlToolbar.add(pnlActions, BorderLayout.WEST);
        pnlToolbar.add(pnlSearch, BorderLayout.EAST);

        // Bảng dữ liệu
        tableModel = new DefaultTableModel(new Object[]{"Số Giường", "Số Phòng"}, 0);
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

        // Sự kiện các nút
        btnAdd.addActionListener(e -> showFormDialog(null));
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn giường cần sửa!");
                return;
            }
            String maSo = tableModel.getValueAt(row, 0).toString();
            Giuong selected = null;
            for(Giuong g : dao.getAll()) {
                if(g.getSoGiuong().equals(maSo)) {
                    selected = g; break;
                }
            }
            if (selected != null) showFormDialog(selected);
        });
        
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn giường cần xóa!");
                return;
            }
            String maSo = tableModel.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa giường " + maSo + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.delete(maSo)) {
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
            for (Giuong g : dao.getAll()) {
                tableModel.addRow(new Object[]{
                    g.getSoGiuong(), g.getSoPhong()
                });
            }
        } catch (Exception e) {}
    }
    
    private void showFormDialog(Giuong g) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), g == null ? "Thêm Giường" : "Sửa Giường", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlInput = new JPanel(new GridLayout(2, 2, 10, 15));
        pnlInput.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField txtSoGiuong = new JTextField();
        JTextField txtSoPhong = new JTextField();
        
        if (g != null) {
            txtSoGiuong.setText(g.getSoGiuong());
            txtSoGiuong.setEditable(false);
            txtSoPhong.setText(g.getSoPhong());
        }
        
        pnlInput.add(new JLabel("Số Giường:")); pnlInput.add(txtSoGiuong);
        pnlInput.add(new JLabel("Số Phòng:")); pnlInput.add(txtSoPhong);
        
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnSave = new JButton("💾 Lưu thông tin");
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);
        
        JButton btnCancel = new JButton("Đóng");
        
        btnCancel.addActionListener(e -> dialog.dispose());
        btnSave.addActionListener(e -> {
            Giuong newG = new Giuong(txtSoGiuong.getText(), txtSoPhong.getText());
            if (g == null) {
                if (dao.insert(newG)) {
                    JOptionPane.showMessageDialog(dialog, "Thêm thành công");
                    loadData();
                    dialog.dispose();
                }
            } else {
                if (dao.update(newG)) {
                    JOptionPane.showMessageDialog(dialog, "Sửa thành công");
                    loadData();
                    dialog.dispose();
                }
            }
        });
        
        pnlButtons.add(btnSave);
        pnlButtons.add(btnCancel);
        
        dialog.setLayout(new BorderLayout());
        dialog.add(pnlInput, BorderLayout.CENTER);
        dialog.add(pnlButtons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
