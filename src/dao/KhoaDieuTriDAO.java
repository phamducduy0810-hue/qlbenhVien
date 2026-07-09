package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhoaDieuTri;

public class KhoaDieuTriDAO {

    public List<KhoaDieuTri> getAll() {
        List<KhoaDieuTri> list = new ArrayList<>();
        String sql = "SELECT * FROM KhoaDieuTri";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhoaDieuTri khoa = new KhoaDieuTri();
                khoa.setMaKhoa(rs.getString("MaKhoa"));
                khoa.setTenKhoa(rs.getString("TenKhoa"));
                list.add(khoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(KhoaDieuTri khoa) {
        String sql = "INSERT INTO KhoaDieuTri(MaKhoa, TenKhoa) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khoa.getMaKhoa());
            ps.setString(2, khoa.getTenKhoa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(KhoaDieuTri khoa) {
        String sql = "UPDATE KhoaDieuTri SET TenKhoa=? WHERE MaKhoa=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, khoa.getTenKhoa());
            ps.setString(2, khoa.getMaKhoa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maKhoa) {
        String sql = "DELETE FROM KhoaDieuTri WHERE MaKhoa=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKhoa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
