package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import dao.NhanVienDAO;
import model.NhanVien;

public class FrmQuanLyNhanVien extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private NhanVienDAO dao = new NhanVienDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FrmQuanLyNhanVien() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));

        JLabel lblTitle = new JLabel("QUẢN LÝ THÔNG TIN NHÂN VIÊN", SwingConstants.CENTER);
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
        pnlSearch.add(new JLabel("Tìm theo mã:"));
        JTextField txtSearch = new JTextField(15);
        pnlSearch.add(txtSearch);

        pnlToolbar.add(pnlActions, BorderLayout.WEST);
        pnlToolbar.add(pnlSearch, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new Object[]{"Mã Số", "CMND", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Ngày Tuyển", "Chức Danh", "Mã Khoa"}, 0);
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
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
                return;
            }
            String maSo = tableModel.getValueAt(row, 0).toString();
            NhanVien selected = null;
            for(NhanVien nv : dao.getAll()) {
                if(nv.getMaSo().equals(maSo)) {
                    selected = nv; break;
                }
            }
            if (selected != null) showFormDialog(selected);
        });
        
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
                return;
            }
            String maSo = tableModel.getValueAt(row, 0).toString();
            if (JOptionPane.showConfirmDialog(this, "Xóa nhân viên " + maSo + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
            for (NhanVien nv : dao.getAll()) {
                tableModel.addRow(new Object[]{
                    nv.getMaSo(), nv.getCmnd(), nv.getGioiTinh(),
                    nv.getNgaySinh() != null ? sdf.format(nv.getNgaySinh()) : "",
                    nv.getDiaChi(),
                    nv.getNgayTuyenDung() != null ? sdf.format(nv.getNgayTuyenDung()) : "",
                    nv.getChucDanh(), nv.getMaKhoa()
                });
            }
        } catch (Exception e) {}
    }
    
    private void showFormDialog(NhanVien nv) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), nv == null ? "Thêm Nhân Viên" : "Sửa Nhân Viên", true);
        dialog.setSize(650, 450);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField txtMaSo = new JTextField();
        JTextField txtCMND = new JTextField();
        
        // Radio Button Giới Tính
        JRadioButton radNam = new JRadioButton("Nam");
        JRadioButton radNu = new JRadioButton("Nữ");
        ButtonGroup bgGioiTinh = new ButtonGroup();
        bgGioiTinh.add(radNam); bgGioiTinh.add(radNu);
        JPanel pnlGioiTinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlGioiTinh.add(radNam); pnlGioiTinh.add(new JLabel("    ")); pnlGioiTinh.add(radNu);
        radNam.setSelected(true); // Default
        
        // Spinner Ngày Sinh
        JSpinner spnNgaySinh = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor deNgaySinh = new JSpinner.DateEditor(spnNgaySinh, "yyyy-MM-dd");
        spnNgaySinh.setEditor(deNgaySinh);
        
        JTextField txtDiaChi = new JTextField();
        
        // Spinner Ngày Tuyển
        JSpinner spnNgayTuyen = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor deNgayTuyen = new JSpinner.DateEditor(spnNgayTuyen, "yyyy-MM-dd");
        spnNgayTuyen.setEditor(deNgayTuyen);
        
        JTextField txtChucDanh = new JTextField();
        JTextField txtMaKhoa = new JTextField();
        
        // Load data if edit
        if (nv != null) {
            txtMaSo.setText(nv.getMaSo()); txtMaSo.setEditable(false);
            txtCMND.setText(nv.getCmnd());
            if ("Nữ".equalsIgnoreCase(nv.getGioiTinh())) radNu.setSelected(true);
            else radNam.setSelected(true);
            if (nv.getNgaySinh() != null) spnNgaySinh.setValue(nv.getNgaySinh());
            txtDiaChi.setText(nv.getDiaChi());
            if (nv.getNgayTuyenDung() != null) spnNgayTuyen.setValue(nv.getNgayTuyenDung());
            txtChucDanh.setText(nv.getChucDanh());
            txtMaKhoa.setText(nv.getMaKhoa());
        }
        
        // Add to layout
        gbc.gridx = 0; gbc.gridy = 0; pnlInput.add(new JLabel("Mã Số NV:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; pnlInput.add(txtMaSo, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0; pnlInput.add(new JLabel("CMND/CCCD:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; pnlInput.add(txtCMND, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; pnlInput.add(new JLabel("Giới Tính:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; pnlInput.add(pnlGioiTinh, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0; pnlInput.add(new JLabel("Ngày Sinh:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; pnlInput.add(spnNgaySinh, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; pnlInput.add(new JLabel("Địa Chỉ:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; pnlInput.add(txtDiaChi, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0; pnlInput.add(new JLabel("Ngày Tuyển:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; pnlInput.add(spnNgayTuyen, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; pnlInput.add(new JLabel("Chức Danh:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; pnlInput.add(txtChucDanh, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0; pnlInput.add(new JLabel("Mã Khoa:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; pnlInput.add(txtMaKhoa, gbc);
        
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
                java.util.Date dNgaySinh = (java.util.Date) spnNgaySinh.getValue();
                java.util.Date dNgayTuyen = (java.util.Date) spnNgayTuyen.getValue();
                String gioiTinh = radNam.isSelected() ? "Nam" : "Nữ";
                
                NhanVien newNv = new NhanVien(txtMaSo.getText(), txtCMND.getText(), gioiTinh,
                    dNgaySinh, txtDiaChi.getText(), dNgayTuyen, txtChucDanh.getText(), txtMaKhoa.getText());
                
                if (nv == null) {
                    if (dao.insert(newNv)) { JOptionPane.showMessageDialog(dialog, "Thêm thành công"); loadData(); dialog.dispose(); }
                } else {
                    // Cập nhật sau
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi khi lưu dữ liệu!");
            }
        });
        
        pnlButtons.add(btnSave); pnlButtons.add(btnCancel);
        dialog.setLayout(new BorderLayout());
        dialog.add(pnlInput, BorderLayout.CENTER);
        dialog.add(pnlButtons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
