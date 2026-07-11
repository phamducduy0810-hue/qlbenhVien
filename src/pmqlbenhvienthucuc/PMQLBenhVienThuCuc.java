package pmqlbenhvienthucuc;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import java.awt.Font;

public class PMQLBenhVienThuCuc {
    
    // Hàm chuẩn hóa font chữ toàn ứng dụng
    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setUIFont(new FontUIResource("Arial", Font.PLAIN, 14));
        } catch (Exception e) {}
        
        // Chạy form Đăng Nhập
        java.awt.EventQueue.invokeLater(() -> {
            new gui.FrmDangNhap().setVisible(true);
        });
    }
}
