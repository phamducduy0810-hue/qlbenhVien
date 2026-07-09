package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.BenhNhan;
import java.sql.Date;

public class BenhNhanDAO {

    public List<BenhNhan> getAll() {
        List<BenhNhan> list = new ArrayList<>();
        String sql = "SELECT * FROM BenhNhan";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BenhNhan bn = new BenhNhan();
                bn.setMaBN(rs.getString("MaBN"));
                bn.setCmnd(rs.getString("CMND"));
                bn.setHoTen(rs.getString("HoTen"));
                bn.setGioiTinh(rs.getString("GioiTinh"));
                bn.setNgaySinh(rs.getDate("NgaySinh"));
                bn.setDiaChi(rs.getString("DiaChi"));
                bn.setNgayDangKy(rs.getDate("NgayDangKy"));
                list.add(bn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(BenhNhan bn) {
        String sql = "INSERT INTO BenhNhan(MaBN, CMND, HoTen, GioiTinh, NgaySinh, DiaChi, NgayDangKy) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bn.getMaBN());
            ps.setString(2, bn.getCmnd());
            ps.setString(3, bn.getHoTen());
            ps.setString(4, bn.getGioiTinh());
            ps.setDate(5, bn.getNgaySinh() != null ? new Date(bn.getNgaySinh().getTime()) : null);
            ps.setString(6, bn.getDiaChi());
            ps.setDate(7, bn.getNgayDangKy() != null ? new Date(bn.getNgayDangKy().getTime()) : null);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(BenhNhan bn) {
        String sql = "UPDATE BenhNhan SET CMND=?, HoTen=?, GioiTinh=?, NgaySinh=?, DiaChi=?, NgayDangKy=? WHERE MaBN=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bn.getCmnd());
            ps.setString(2, bn.getHoTen());
            ps.setString(3, bn.getGioiTinh());
            ps.setDate(4, bn.getNgaySinh() != null ? new Date(bn.getNgaySinh().getTime()) : null);
            ps.setString(5, bn.getDiaChi());
            ps.setDate(6, bn.getNgayDangKy() != null ? new Date(bn.getNgayDangKy().getTime()) : null);
            ps.setString(7, bn.getMaBN());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maBN) {
        String sql = "DELETE FROM BenhNhan WHERE MaBN=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBN);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
