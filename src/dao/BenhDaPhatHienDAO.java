package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import model.BenhDaPhatHien;

public class BenhDaPhatHienDAO {

    public List<BenhDaPhatHien> getAll() {
        List<BenhDaPhatHien> list = new ArrayList<>();
        String sql = "SELECT * FROM BenhDaPhatHien";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BenhDaPhatHien obj = new BenhDaPhatHien();
                obj.setMaBenh(rs.getInt("MaBenh"));
                obj.setMaBenhNhan(rs.getString("MaBenhNhan"));
                obj.setTenBenh(rs.getString("TenBenh"));
                obj.setNgayPhatHien(rs.getDate("NgayPhatHien"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(BenhDaPhatHien obj) {
        String sql = "INSERT INTO BenhDaPhatHien(MaBenh, MaBenhNhan, TenBenh, NgayPhatHien) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, obj.getMaBenh());
            ps.setString(2, obj.getMaBenhNhan());
            ps.setString(3, obj.getTenBenh());
            ps.setDate(4, new Date(obj.getNgayPhatHien().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int maBenh) {
        String sql = "DELETE FROM BenhDaPhatHien WHERE MaBenh=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maBenh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(BenhDaPhatHien obj) {
        String sql = "UPDATE BenhDaPhatHien SET MaBenhNhan=?, TenBenh=?, NgayPhatHien=? WHERE MaBenh=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaBenhNhan());
            ps.setString(2, obj.getTenBenh());
            ps.setDate(3, new Date(obj.getNgayPhatHien().getTime()));
            ps.setInt(4, obj.getMaBenh());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
