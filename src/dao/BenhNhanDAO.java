package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import model.BenhNhan;

public class BenhNhanDAO {

    public List<BenhNhan> getAll() {
        List<BenhNhan> list = new ArrayList<>();
        String sql = "SELECT * FROM BenhNhan";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BenhNhan obj = new BenhNhan();
                obj.setMaSo(rs.getString("MaSo"));
                obj.setCmnd(rs.getString("CMND"));
                obj.setGioiTinh(rs.getString("GioiTinh"));
                obj.setNgaySinh(rs.getDate("NgaySinh"));
                obj.setDiaChi(rs.getString("DiaChi"));
                obj.setNgayDangKy(rs.getDate("NgayDangKy"));
                obj.setMaBacSy(rs.getString("MaBacSy"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(BenhNhan obj) {
        String sql = "INSERT INTO BenhNhan(MaSo, CMND, GioiTinh, NgaySinh, DiaChi, NgayDangKy, MaBacSy) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaSo());
            ps.setString(2, obj.getCmnd());
            ps.setString(3, obj.getGioiTinh());
            ps.setDate(4, obj.getNgaySinh() != null ? new Date(obj.getNgaySinh().getTime()) : null);
            ps.setString(5, obj.getDiaChi());
            ps.setDate(6, obj.getNgayDangKy() != null ? new Date(obj.getNgayDangKy().getTime()) : null);
            ps.setString(7, obj.getMaBacSy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(BenhNhan obj) {
        String sql = "UPDATE BenhNhan SET CMND=?, GioiTinh=?, NgaySinh=?, DiaChi=?, NgayDangKy=?, MaBacSy=? WHERE MaSo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getCmnd());
            ps.setString(2, obj.getGioiTinh());
            ps.setDate(3, obj.getNgaySinh() != null ? new Date(obj.getNgaySinh().getTime()) : null);
            ps.setString(4, obj.getDiaChi());
            ps.setDate(5, obj.getNgayDangKy() != null ? new Date(obj.getNgayDangKy().getTime()) : null);
            ps.setString(6, obj.getMaBacSy());
            ps.setString(7, obj.getMaSo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maSo) {
        String sql = "DELETE FROM BenhNhan WHERE MaSo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
