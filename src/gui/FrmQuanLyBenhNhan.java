package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import dao.BenhNhanDAO;
import model.BenhNhan;
import java.text.SimpleDateFormat;

public class FrmQuanLyBenhNhan extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private BenhNhanDAO dao = new BenhNhanDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FrmQuanLyBenhNhan() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));

        // Tiêu đề form
        JLabel lblTitle = new JLabel("THÔNG TIN BỆNH NHÂN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Thanh công cụ (Toolbar)
        JPanel pnlToolbar = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlToolbar.setOpaque(false);
        pnlToolbar.setBorder(new EmptyBorder(0, 20, 10, 20));

        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlActions.setOpaque(false);
        JButton btnAdd = createToolButton("➕ Thêm");
        JButton btnUpdate = createToolButton("✏️ Sửa");
        JButton btnDelete = createToolButton("❌ Xóa");
        JButton btnView = createToolButton("🔍 Xem chi tiết");
        JButton btnExcel = createToolButton("📊 Xuất Excel");
        JButton btnLoad = createToolButton("🔄 Làm mới");
        
        pnlActions.add(btnUpdate);
        pnlActions.add(btnView);
        pnlActions.add(btnExcel);
        pnlActions.add(btnLoad);
        
        String role = "Admin";
        if (utils.SessionManager.isLoggedIn()) {
            role = utils.SessionManager.getCurrentUser().getVaiTro();
        }
        
        if ("Admin".equalsIgnoreCase(role)) {
            pnlActions.add(btnAdd, 0); // Thêm vào đầu
            pnlActions.add(btnDelete, 2);
        } else if ("LeTan".equalsIgnoreCase(role)) {
            pnlActions.add(btnAdd, 0);
        } else if ("BacSy".equalsIgnoreCase(role)) {
            // Chỉ cập nhật (Sửa), không thêm không xóa
        }

        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlSearch.setOpaque(false);
        pnlSearch.add(new JLabel("Tìm theo mã:"));
        JTextField txtSearch = new JTextField(15);
        JButton btnSearch = createToolButton("Tìm kiếm");
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);

        pnlToolbar.add(pnlActions);
        pnlToolbar.add(pnlSearch);

        // Bảng dữ liệu
        tableModel = new DefaultTableModel(new Object[]{"Mã Số", "CMND", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Đăng Ký", "Mã BS"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(173, 216, 230));
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
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
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bệnh nhân cần sửa!");
                return;
            }
            String maSo = tableModel.getValueAt(row, 0).toString();
            // Lấy bệnh nhân từ CSDL (tạm thời lặp tìm)
            BenhNhan selected = null;
            for(BenhNhan bn : dao.getAll()) {
                if(bn.getMaSo().equals(maSo)) {
                    selected = bn; break;
                }
            }
            if (selected != null) showFormDialog(selected);
        });
        
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn bệnh nhân cần xóa!");
                return;
            }
            String maSo = tableModel.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa bệnh nhân " + maSo + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.delete(maSo)) {
                    JOptionPane.showMessageDialog(this, "Xoá thành công!");
                    loadData();
                }
            }
        });
        
        btnLoad.addActionListener(e -> {
            txtSearch.setText("");
            if (table.getRowSorter() != null) {
                ((javax.swing.table.TableRowSorter)table.getRowSorter()).setRowFilter(null);
            }
            loadData();
        });
        
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim().toLowerCase();
            javax.swing.table.TableRowSorter<DefaultTableModel> sorter = (javax.swing.table.TableRowSorter<DefaultTableModel>) table.getRowSorter();
            if (sorter == null) {
                sorter = new javax.swing.table.TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);
            }
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                sorter.setRowFilter(null);
                return;
            }
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + keyword));
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        loadData();
    }
    
    private JButton createToolButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        String currentUserRole = "";
        String currentBacSyId = "";
        if (utils.SessionManager.isLoggedIn()) {
            currentUserRole = utils.SessionManager.getCurrentUser().getVaiTro();
            currentBacSyId = utils.SessionManager.getCurrentUser().getMaBacSy();
        }

        for (BenhNhan bn : dao.getAll()) {
            // Hiển thị tất cả bệnh nhân cho mọi vai trò (kể cả Bác sỹ)

            
            tableModel.addRow(new Object[]{
                bn.getMaSo(), bn.getCmnd(), bn.getGioiTinh(),
                bn.getNgaySinh() != null ? sdf.format(bn.getNgaySinh()) : "",
                bn.getDiaChi(),
                bn.getNgayDangKy() != null ? sdf.format(bn.getNgayDangKy()) : "",
                bn.getMaBacSy()
            });
        }
    }
    
    // Hộp thoại form nhập liệu
    private void showFormDialog(BenhNhan bn) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), bn == null ? "Thêm Bệnh Nhân" : "Sửa Bệnh Nhân", true);
        dialog.setSize(600, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 15));
        pnlInput.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField txtMaSo = new JTextField();
        JTextField txtCMND = new JTextField();
        
        JRadioButton radNam = new JRadioButton("Nam");
        JRadioButton radNu = new JRadioButton("Nữ");
        radNam.setSelected(true);
        ButtonGroup bgGioiTinh = new ButtonGroup();
        bgGioiTinh.add(radNam);
        bgGioiTinh.add(radNu);
        JPanel pnlGioiTinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlGioiTinh.add(radNam);
        pnlGioiTinh.add(radNu);
        
        JSpinner spnNgaySinh = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorNgaySinh = new JSpinner.DateEditor(spnNgaySinh, "yyyy-MM-dd");
        spnNgaySinh.setEditor(editorNgaySinh);
        
        JTextField txtDiaChi = new JTextField();
        
        JSpinner spnNgayDangKy = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorNgayDangKy = new JSpinner.DateEditor(spnNgayDangKy, "yyyy-MM-dd");
        spnNgayDangKy.setEditor(editorNgayDangKy);
        
        JTextField txtMaBacSy = new JTextField();
        
        if (bn != null) {
            txtMaSo.setText(bn.getMaSo());
            txtMaSo.setEditable(false);
            txtCMND.setText(bn.getCmnd());
            if ("Nữ".equalsIgnoreCase(bn.getGioiTinh())) radNu.setSelected(true);
            else radNam.setSelected(true);
            if (bn.getNgaySinh() != null) spnNgaySinh.setValue(bn.getNgaySinh());
            txtDiaChi.setText(bn.getDiaChi());
            if (bn.getNgayDangKy() != null) spnNgayDangKy.setValue(bn.getNgayDangKy());
            txtMaBacSy.setText(bn.getMaBacSy());
        }
        
        pnlInput.add(new JLabel("Mã Số BN:")); pnlInput.add(txtMaSo);
        pnlInput.add(new JLabel("CMND/CCCD:")); pnlInput.add(txtCMND);
        pnlInput.add(new JLabel("Giới Tính:")); pnlInput.add(pnlGioiTinh);
        pnlInput.add(new JLabel("Ngày Sinh (Y-M-D):")); pnlInput.add(spnNgaySinh);
        pnlInput.add(new JLabel("Địa Chỉ:")); pnlInput.add(txtDiaChi);
        pnlInput.add(new JLabel("Ngày Đăng Ký (Y-M-D):")); pnlInput.add(spnNgayDangKy);
        pnlInput.add(new JLabel("Mã Bác Sỹ:")); pnlInput.add(txtMaBacSy);
        
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
                String gioiTinh = radNam.isSelected() ? "Nam" : "Nữ";
                java.util.Date ngaySinh = (java.util.Date) spnNgaySinh.getValue();
                java.util.Date ngayDangKy = (java.util.Date) spnNgayDangKy.getValue();
                if (txtMaSo.getText().trim().isEmpty() || txtCMND.getText().trim().isEmpty() || 
                    txtDiaChi.getText().trim().isEmpty() || txtMaBacSy.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                BenhNhan newBn = new BenhNhan(txtMaSo.getText().trim(), txtCMND.getText().trim(), gioiTinh,
                    ngaySinh, txtDiaChi.getText().trim(), ngayDangKy, txtMaBacSy.getText().trim());
                
                if (bn == null) {
                    if (dao.insert(newBn)) {
                        JOptionPane.showMessageDialog(dialog, "Thêm thành công");
                        loadData();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Lưu thất bại! Vui lòng kiểm tra lại dữ liệu (Mã BN trùng, Mã Bác Sỹ không tồn tại,...)", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (dao.update(newBn)) {
                        JOptionPane.showMessageDialog(dialog, "Sửa thành công");
                        loadData();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại! Vui lòng kiểm tra lại dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi định dạng ngày tháng (yyyy-MM-dd)!");
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
