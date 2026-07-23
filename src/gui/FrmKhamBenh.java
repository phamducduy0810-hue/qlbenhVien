package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import dao.LanKhamDAO;
import model.LanKham;

public class FrmKhamBenh extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private LanKhamDAO dao = new LanKhamDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FrmKhamBenh() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));

        JLabel lblTitle = new JLabel("QUẢN LÝ LẦN KHÁM", SwingConstants.CENTER);
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
        pnlSearch.add(new JLabel("Tìm mã lần khám:"));
        JTextField txtSearch = new JTextField(15);
        JButton btnSearch = createToolButton("Tìm kiếm");
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);

        pnlToolbar.add(pnlActions, BorderLayout.WEST);
        pnlToolbar.add(pnlSearch, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new Object[]{"Mã Khám", "Mã Bệnh Nhân", "Mã Bác Sỹ", "Thời Điểm Khám", "Kết Luận"}, 0);
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
            int maKham = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            LanKham selected = null;
            for(LanKham k : dao.getAll()) {
                if(k.getMaLanKham() == maKham) {
                    selected = k; break;
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
            int maKham = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            if (JOptionPane.showConfirmDialog(this, "Xóa lần khám " + maKham + "?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (dao.delete(maKham)) {
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
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            for (LanKham l : dao.getAll()) {
                tableModel.addRow(new Object[]{
                    l.getMaLanKham(), l.getMaBenhNhan(), l.getMaBacSy(), 
                    l.getThoiDiemKham() != null ? sdf.format(l.getThoiDiemKham()) : "",
                    l.getKetLuan()
                });
            }
        } catch (Exception e) {}
    }
    
    private void showFormDialog(LanKham k) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), k == null ? "Thêm Lần Khám" : "Sửa Lần Khám", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlInput = new JPanel(new GridLayout(5, 2, 10, 15));
        pnlInput.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextField txtMaKham = new JTextField();
        JTextField txtMaBenhNhan = new JTextField();
        JTextField txtMaBacSy = new JTextField();
        JTextField txtThoiDiem = new JTextField();
        JTextField txtKetLuan = new JTextField();
        
        if (k != null) {
            txtMaKham.setText(String.valueOf(k.getMaLanKham())); txtMaKham.setEditable(false);
            txtMaBenhNhan.setText(k.getMaBenhNhan());
            txtMaBacSy.setText(k.getMaBacSy());
            txtThoiDiem.setText(k.getThoiDiemKham() != null ? sdf.format(k.getThoiDiemKham()) : "");
            txtKetLuan.setText(k.getKetLuan());
        }
        
        pnlInput.add(new JLabel("Mã Khám (ID):")); pnlInput.add(txtMaKham);
        pnlInput.add(new JLabel("Mã Bệnh Nhân:")); pnlInput.add(txtMaBenhNhan);
        pnlInput.add(new JLabel("Mã Bác Sỹ:")); pnlInput.add(txtMaBacSy);
        pnlInput.add(new JLabel("Thời Điểm (Y-M-D H:m:s):")); pnlInput.add(txtThoiDiem);
        pnlInput.add(new JLabel("Kết Luận:")); pnlInput.add(txtKetLuan);
        
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
                LanKham newK = new LanKham(Integer.parseInt(txtMaKham.getText()), txtMaBenhNhan.getText(), 
                    txtMaBacSy.getText(), sdf.parse(txtThoiDiem.getText()), txtKetLuan.getText());
                if (k == null) {
                    if (dao.insert(newK)) { JOptionPane.showMessageDialog(dialog, "Thêm thành công"); loadData(); dialog.dispose(); }
                } else {
                    // if (dao.update(newK)) { JOptionPane.showMessageDialog(dialog, "Sửa thành công"); loadData(); dialog.dispose(); }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi định dạng dữ liệu!");
            }
        });
        
        pnlButtons.add(btnSave); pnlButtons.add(btnCancel);
        dialog.setLayout(new BorderLayout());
        dialog.add(pnlInput, BorderLayout.CENTER);
        dialog.add(pnlButtons, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
