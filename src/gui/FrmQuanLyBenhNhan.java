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

public class FrmQuanLyBenhNhan extends JFrame {
    private JTextField txtMaSo, txtCMND, txtGioiTinh, txtNgaySinh, txtDiaChi, txtNgayDangKy, txtMaBacSy;
    private JTable table;
    private DefaultTableModel tableModel;
    private BenhNhanDAO dao = new BenhNhanDAO();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public FrmQuanLyBenhNhan() {
        setTitle("Quản Lý Bệnh Nhân");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 250));

        // Tiêu đề form
        JLabel lblTitle = new JLabel("THÔNG TIN BỆNH NHÂN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(new EmptyBorder(15, 0, 5, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Vùng nhập liệu
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 15, 15));
        pnlInput.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 20, 10, 20),
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Điền thông tin", TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 14), Color.DARK_GRAY)
        ));
        pnlInput.setBackground(Color.WHITE);
        
        pnlInput.add(new JLabel("Mã Số BN:")); txtMaSo = new JTextField(); pnlInput.add(txtMaSo);
        pnlInput.add(new JLabel("CMND/CCCD:")); txtCMND = new JTextField(); pnlInput.add(txtCMND);
        pnlInput.add(new JLabel("Giới Tính:")); txtGioiTinh = new JTextField(); pnlInput.add(txtGioiTinh);
        pnlInput.add(new JLabel("Ngày Sinh (N-T-N):")); txtNgaySinh = new JTextField(); pnlInput.add(txtNgaySinh);
        pnlInput.add(new JLabel("Địa Chỉ:")); txtDiaChi = new JTextField(); pnlInput.add(txtDiaChi);
        pnlInput.add(new JLabel("Ngày Đăng Ký:")); txtNgayDangKy = new JTextField(); pnlInput.add(txtNgayDangKy);
        pnlInput.add(new JLabel("Mã Bác Sỹ:")); txtMaBacSy = new JTextField(); pnlInput.add(txtMaBacSy);
        
        // Nút chức năng
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButtons.setBackground(new Color(245, 245, 250));
        
        JButton btnAdd = createButton("Thêm Mới", new Color(46, 204, 113));
        JButton btnUpdate = createButton("Cập Nhật", new Color(52, 152, 219));
        JButton btnDelete = createButton("Xóa", new Color(231, 76, 60));
        JButton btnLoad = createButton("Tải Lại", new Color(155, 89, 182));

        btnAdd.addActionListener(e -> {
            try {
                BenhNhan bn = new BenhNhan(txtMaSo.getText(), txtCMND.getText(), txtGioiTinh.getText(),
                    sdf.parse(txtNgaySinh.getText()), txtDiaChi.getText(), sdf.parse(txtNgayDangKy.getText()), txtMaBacSy.getText());
                if (dao.insert(bn)) { JOptionPane.showMessageDialog(this, "Thêm thành công"); loadData(); }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày tháng (yyyy-MM-dd)!"); }
        });
        btnUpdate.addActionListener(e -> {
            try {
                BenhNhan bn = new BenhNhan(txtMaSo.getText(), txtCMND.getText(), txtGioiTinh.getText(),
                    sdf.parse(txtNgaySinh.getText()), txtDiaChi.getText(), sdf.parse(txtNgayDangKy.getText()), txtMaBacSy.getText());
                if (dao.update(bn)) { JOptionPane.showMessageDialog(this, "Sửa thành công"); loadData(); }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi định dạng ngày tháng (yyyy-MM-dd)!"); }
        });
        btnDelete.addActionListener(e -> {
            if (dao.delete(txtMaSo.getText())) { JOptionPane.showMessageDialog(this, "Xoá thành công"); loadData(); }
        });
        btnLoad.addActionListener(e -> loadData());

        pnlButtons.add(btnAdd); pnlButtons.add(btnUpdate); pnlButtons.add(btnDelete); pnlButtons.add(btnLoad);
        
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);
        pnlTop.add(pnlInput, BorderLayout.CENTER);
        pnlTop.add(pnlButtons, BorderLayout.SOUTH);
        add(pnlTop, BorderLayout.CENTER);

        // Bảng dữ liệu
        tableModel = new DefaultTableModel(new Object[]{"Mã Số", "CMND", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Đăng Ký", "Mã BS"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(173, 216, 230));
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(200, 200, 200));
        
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(new EmptyBorder(10, 20, 20, 20));
        pnlTable.setOpaque(false);
        pnlTable.add(new JScrollPane(table), BorderLayout.CENTER);
        add(pnlTable, BorderLayout.SOUTH);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaSo.setText(tableModel.getValueAt(row, 0) != null ? tableModel.getValueAt(row, 0).toString() : "");
                txtCMND.setText(tableModel.getValueAt(row, 1) != null ? tableModel.getValueAt(row, 1).toString() : "");
                txtGioiTinh.setText(tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "");
                txtNgaySinh.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
                txtDiaChi.setText(tableModel.getValueAt(row, 4) != null ? tableModel.getValueAt(row, 4).toString() : "");
                txtNgayDangKy.setText(tableModel.getValueAt(row, 5) != null ? tableModel.getValueAt(row, 5).toString() : "");
                txtMaBacSy.setText(tableModel.getValueAt(row, 6) != null ? tableModel.getValueAt(row, 6).toString() : "");
            }
        });

        loadData();
    }
    
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            new EmptyBorder(8, 20, 8, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (BenhNhan bn : dao.getAll()) {
            tableModel.addRow(new Object[]{
                bn.getMaSo(), bn.getCmnd(), bn.getGioiTinh(),
                bn.getNgaySinh() != null ? sdf.format(bn.getNgaySinh()) : "",
                bn.getDiaChi(),
                bn.getNgayDangKy() != null ? sdf.format(bn.getNgayDangKy()) : "",
                bn.getMaBacSy()
            });
        }
    }
}
