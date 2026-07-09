package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.KhoaDieuTriDAO;
import model.KhoaDieuTri;

public class FrmQuanLyKhoaPhong extends JFrame {
    private JTextField txtMaKhoa, txtTenKhoa;
    private JTable table;
    private DefaultTableModel tableModel;
    private KhoaDieuTriDAO dao = new KhoaDieuTriDAO();

    public FrmQuanLyKhoaPhong() {
        setTitle("Quản Lý Khoa Điều Trị");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlInput = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlInput.add(new JLabel("Mã Khoa:"));
        txtMaKhoa = new JTextField();
        pnlInput.add(txtMaKhoa);
        pnlInput.add(new JLabel("Tên Khoa:"));
        txtTenKhoa = new JTextField();
        pnlInput.add(txtTenKhoa);
        add(pnlInput, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Mã Khoa", "Tên Khoa"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaKhoa.setText(tableModel.getValueAt(row, 0).toString());
                txtTenKhoa.setText(tableModel.getValueAt(row, 1).toString());
            }
        });

        JPanel pnlButtons = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xoá");
        JButton btnLoad = new JButton("Tải lại");

        btnAdd.addActionListener(e -> {
            KhoaDieuTri k = new KhoaDieuTri(txtMaKhoa.getText(), txtTenKhoa.getText());
            if (dao.insert(k)) { JOptionPane.showMessageDialog(this, "Thêm thành công"); loadData(); }
            else JOptionPane.showMessageDialog(this, "Lỗi thêm");
        });

        btnUpdate.addActionListener(e -> {
            KhoaDieuTri k = new KhoaDieuTri(txtMaKhoa.getText(), txtTenKhoa.getText());
            if (dao.update(k)) { JOptionPane.showMessageDialog(this, "Sửa thành công"); loadData(); }
            else JOptionPane.showMessageDialog(this, "Lỗi sửa");
        });

        btnDelete.addActionListener(e -> {
            if (dao.delete(txtMaKhoa.getText())) { JOptionPane.showMessageDialog(this, "Xoá thành công"); loadData(); }
            else JOptionPane.showMessageDialog(this, "Lỗi xoá");
        });

        btnLoad.addActionListener(e -> loadData());

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnLoad);
        add(pnlButtons, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (KhoaDieuTri k : dao.getAll()) {
            tableModel.addRow(new Object[]{k.getMaKhoa(), k.getTenKhoa()});
        }
    }
}
