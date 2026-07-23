package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.TaiKhoan;

public class TaiKhoanDAO {

    public TaiKhoan authenticate(String username, String password) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan();
                    tk.setTenDangNhap(rs.getString("TenDangNhap"));
                    tk.setMatKhau(rs.getString("MatKhau"));
                    tk.setVaiTro(rs.getString("VaiTro"));
                    tk.setMaNhanVien(rs.getString("MaNhanVien"));
                    tk.setMaBacSy(rs.getString("MaBacSy"));
                    return tk;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
