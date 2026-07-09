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
import model.LanKham;

public class LanKhamDAO {

    public List<LanKham> getAll() {
        List<LanKham> list = new ArrayList<>();
        String sql = "SELECT * FROM LanKham";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LanKham obj = new LanKham();
                obj.setMaLanKham(rs.getInt("MaLanKham"));
                obj.setMaBenhNhan(rs.getString("MaBenhNhan"));
                obj.setMaBacSy(rs.getString("MaBacSy"));
                obj.setThoiDiemKham(rs.getTimestamp("ThoiDiemKham"));
                obj.setKetLuan(rs.getString("KetLuan"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(LanKham obj) {
        String sql = "INSERT INTO LanKham(MaBenhNhan, MaBacSy, ThoiDiemKham, KetLuan) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaBenhNhan());
            ps.setString(2, obj.getMaBacSy());
            ps.setTimestamp(3, obj.getThoiDiemKham() != null ? new Timestamp(obj.getThoiDiemKham().getTime()) : null);
            ps.setString(4, obj.getKetLuan());
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
