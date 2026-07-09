package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;
import model.PhieuNhapVien;

public class PhieuNhapVienDAO {

    public List<PhieuNhapVien> getAll() {
        List<PhieuNhapVien> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhapVien";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhieuNhapVien obj = new PhieuNhapVien();
                obj.setMaPhieu(rs.getInt("MaPhieu"));
                obj.setMaBenhNhan(rs.getString("MaBenhNhan"));
                obj.setSoGiuong(rs.getString("SoGiuong"));
                obj.setNgayNhan(rs.getTimestamp("NgayNhan"));
                obj.setNgayTra(rs.getTimestamp("NgayTra"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(PhieuNhapVien obj) {
        String sql = "INSERT INTO PhieuNhapVien(MaBenhNhan, SoGiuong, NgayNhan, NgayTra) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaBenhNhan());
            ps.setString(2, obj.getSoGiuong());
            ps.setTimestamp(3, obj.getNgayNhan() != null ? new Timestamp(obj.getNgayNhan().getTime()) : null);
            ps.setTimestamp(4, obj.getNgayTra() != null ? new Timestamp(obj.getNgayTra().getTime()) : null);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int maPhieu) {
        String sql = "DELETE FROM PhieuNhapVien WHERE MaPhieu=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPhieu);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
