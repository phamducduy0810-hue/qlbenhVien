package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import dao.BacSyDAO;
import model.BacSy;

public class FrmQuanLyBacSy extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private BacSyDAO dao = new BacSyDAO();

    public FrmQuanLyBacSy() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));

        // Tiêu đề form
        JLabel lblTitle = new JLabel("QUẢN LÝ THÔNG TIN BÁC SỸ", SwingConstants.CENTER);
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
        tableModel = new DefaultTableModel(new Object[]{"Mã Số", "Điện Thoại", "Chuyên Ngành", "Kỹ Năng", "Lịch Làm Việc"}, 0);
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
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bác sỹ cần sửa!");
                return;
            }
            String maSo = tableModel.getValueAt(row, 0).toString();
            BacSy selected = null;
            for(BacSy bs : dao.getAll()) {
                if(bs.getMaSo().equals(maSo)) {
                    selected = bs; break;
                }
            }
            if (selected != null) showFormDialog(selected);
        });
        
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bác sỹ cần xóa!");
                return;
            }
            String maSo = tableModel.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa bác sỹ " + maSo + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
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
            for (BacSy bs : dao.getAll()) {
                tableModel.addRow(new Object[]{
                    bs.getMaSo(), bs.getDienThoai(), bs.getChuyenNganh(), bs.getKyNang(), bs.getLichLamViec()
                });
            }
        } catch (Exception e) {}
    }
    
    private void showFormDialog(BacSy bs) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), bs == null ? "Thêm Bác Sỹ" : "Sửa Bác Sỹ", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlInput = new JPanel(new GridLayout(5, 2, 10, 15));
        pnlInput.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField txtMaSo = new JTextField();
        JTextField txtDienThoai = new JTextField();
        JTextField txtChuyenNganh = new JTextField();
        JTextField txtKyNang = new JTextField();
        JTextField txtLich = new JTextField();
        
        if (bs != null) {
            txtMaSo.setText(bs.getMaSo());
            txtMaSo.setEditable(false);
            txtDienThoai.setText(bs.getDienThoai());
            txtChuyenNganh.setText(bs.getChuyenNganh());
            txtKyNang.setText(bs.getKyNang());
            txtLich.setText(bs.getLichLamViec());
        }
        
        pnlInput.add(new JLabel("Mã Bác Sỹ:")); pnlInput.add(txtMaSo);
        pnlInput.add(new JLabel("Điện Thoại:")); pnlInput.add(txtDienThoai);
        pnlInput.add(new JLabel("Chuyên Ngành:")); pnlInput.add(txtChuyenNganh);
        pnlInput.add(new JLabel("Kỹ Năng:")); pnlInput.add(txtKyNang);
        pnlInput.add(new JLabel("Lịch Làm Việc:")); pnlInput.add(txtLich);
        
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
            BacSy newBs = new BacSy(txtMaSo.getText(), txtDienThoai.getText(), txtChuyenNganh.getText(), txtKyNang.getText(), txtLich.getText());
            if (bs == null) {
                if (dao.insert(newBs)) {
                    JOptionPane.showMessageDialog(dialog, "Thêm thành công");
                    loadData();
                    dialog.dispose();
                }
            } else {
                if (dao.update(newBs)) {
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
