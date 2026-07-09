package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.LanKham;
import java.sql.Timestamp;

public class LanKhamDAO {

    public List<LanKham> getAll() {
        List<LanKham> list = new ArrayList<>();
        String sql = "SELECT * FROM LanKham";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LanKham lk = new LanKham();
                lk.setMaLanKham(rs.getInt("MaLanKham"));
                lk.setMaBN(rs.getString("MaBN"));
                lk.setMaBS(rs.getString("MaBS"));
                lk.setNgayGioKham(rs.getTimestamp("NgayGioKham"));
                lk.setKetLuan(rs.getString("KetLuan"));
                list.add(lk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(LanKham lk) {
        String sql = "INSERT INTO LanKham(MaBN, MaBS, NgayGioKham, KetLuan) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lk.getMaBN());
            ps.setString(2, lk.getMaBS());
            ps.setTimestamp(3, lk.getNgayGioKham() != null ? new Timestamp(lk.getNgayGioKham().getTime()) : null);
            ps.setString(4, lk.getKetLuan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(LanKham lk) {
        String sql = "UPDATE LanKham SET MaBN=?, MaBS=?, NgayGioKham=?, KetLuan=? WHERE MaLanKham=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lk.getMaBN());
            ps.setString(2, lk.getMaBS());
            ps.setTimestamp(3, lk.getNgayGioKham() != null ? new Timestamp(lk.getNgayGioKham().getTime()) : null);
            ps.setString(4, lk.getKetLuan());
            ps.setInt(5, lk.getMaLanKham());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int maLanKham) {
        String sql = "DELETE FROM LanKham WHERE MaLanKham=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maLanKham);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
