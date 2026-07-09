package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import dao.GiuongDAO;
import model.Giuong;

public class FrmQuanLyGiuong extends JFrame {
    private JTextField txtSoGiuong, txtSoPhong;
    private JTable table;
    private DefaultTableModel tableModel;
    private GiuongDAO dao = new GiuongDAO();

    public FrmQuanLyGiuong() {
        setTitle("Quản Lý Giường");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pnlInput = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlInput.add(new JLabel("Số Giường:")); txtSoGiuong = new JTextField(); pnlInput.add(txtSoGiuong);
        pnlInput.add(new JLabel("Số Phòng:")); txtSoPhong = new JTextField(); pnlInput.add(txtSoPhong);
        add(pnlInput, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Số Giường", "Số Phòng"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtSoGiuong.setText(tableModel.getValueAt(row, 0).toString());
                txtSoPhong.setText(tableModel.getValueAt(row, 1).toString());
            }
        });

        JPanel pnlButtons = new JPanel();
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xoá");
        JButton btnLoad = new JButton("Tải lại");

        btnAdd.addActionListener(e -> {
            Giuong g = new Giuong(); g.setSoGiuong(txtSoGiuong.getText()); g.setSoPhong(txtSoPhong.getText());
            if (dao.insert(g)) { JOptionPane.showMessageDialog(this, "Thêm thành công"); loadData(); }
        });

        btnUpdate.addActionListener(e -> {
            Giuong g = new Giuong(); g.setSoGiuong(txtSoGiuong.getText()); g.setSoPhong(txtSoPhong.getText());
            if (dao.update(g)) { JOptionPane.showMessageDialog(this, "Sửa thành công"); loadData(); }
        });

        btnDelete.addActionListener(e -> {
            if (dao.delete(txtSoGiuong.getText())) { JOptionPane.showMessageDialog(this, "Xoá thành công"); loadData(); }
        });

        btnLoad.addActionListener(e -> loadData());

        pnlButtons.add(btnAdd); pnlButtons.add(btnUpdate); pnlButtons.add(btnDelete); pnlButtons.add(btnLoad);
        add(pnlButtons, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (Giuong g : dao.getAll()) {
            tableModel.addRow(new Object[]{g.getSoGiuong(), g.getSoPhong()});
        }
    }
}
