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
import model.NhanVien;

public class NhanVienDAO {

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVien obj = new NhanVien();
                obj.setMaSo(rs.getString("MaSo"));
                obj.setCmnd(rs.getString("CMND"));
                obj.setGioiTinh(rs.getString("GioiTinh"));
                obj.setNgaySinh(rs.getDate("NgaySinh"));
                obj.setDiaChi(rs.getString("DiaChi"));
                obj.setNgayTuyenDung(rs.getDate("NgayTuyenDung"));
                obj.setChucDanh(rs.getString("ChucDanh"));
                obj.setMaKhoa(rs.getString("MaKhoa"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(NhanVien obj) {
        String sql = "INSERT INTO NhanVien(MaSo, CMND, GioiTinh, NgaySinh, DiaChi, NgayTuyenDung, ChucDanh, MaKhoa) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaSo());
            ps.setString(2, obj.getCmnd());
            ps.setString(3, obj.getGioiTinh());
            ps.setDate(4, obj.getNgaySinh() != null ? new Date(obj.getNgaySinh().getTime()) : null);
            ps.setString(5, obj.getDiaChi());
            ps.setDate(6, obj.getNgayTuyenDung() != null ? new Date(obj.getNgayTuyenDung().getTime()) : null);
            ps.setString(7, obj.getChucDanh());
            ps.setString(8, obj.getMaKhoa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maSo) {
        String sql = "DELETE FROM NhanVien WHERE MaSo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(NhanVien obj) {
        String sql = "UPDATE NhanVien SET CMND=?, GioiTinh=?, NgaySinh=?, DiaChi=?, NgayTuyenDung=?, ChucDanh=?, MaKhoa=? WHERE MaSo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getCmnd());
            ps.setString(2, obj.getGioiTinh());
            ps.setDate(3, obj.getNgaySinh() != null ? new Date(obj.getNgaySinh().getTime()) : null);
            ps.setString(4, obj.getDiaChi());
            ps.setDate(5, obj.getNgayTuyenDung() != null ? new Date(obj.getNgayTuyenDung().getTime()) : null);
            ps.setString(6, obj.getChucDanh());
            ps.setString(7, obj.getMaKhoa());
            ps.setString(8, obj.getMaSo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
