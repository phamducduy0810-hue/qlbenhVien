package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.NhanVien;
import java.sql.Date;

public class NhanVienDAO {

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setCmnd(rs.getString("CMND"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setNgayTuyenDung(rs.getDate("NgayTuyenDung"));
                nv.setChucDanh(rs.getString("ChucDanh"));
                nv.setMaKhoa(rs.getString("MaKhoa"));
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(NhanVien nv) {
        String sql = "INSERT INTO NhanVien(MaNV, CMND, HoTen, GioiTinh, NgaySinh, DiaChi, NgayTuyenDung, ChucDanh, MaKhoa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getCmnd());
            ps.setString(3, nv.getHoTen());
            ps.setString(4, nv.getGioiTinh());
            ps.setDate(5, nv.getNgaySinh() != null ? new Date(nv.getNgaySinh().getTime()) : null);
            ps.setString(6, nv.getDiaChi());
            ps.setDate(7, nv.getNgayTuyenDung() != null ? new Date(nv.getNgayTuyenDung().getTime()) : null);
            ps.setString(8, nv.getChucDanh());
            ps.setString(9, nv.getMaKhoa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(NhanVien nv) {
        String sql = "UPDATE NhanVien SET CMND=?, HoTen=?, GioiTinh=?, NgaySinh=?, DiaChi=?, NgayTuyenDung=?, ChucDanh=?, MaKhoa=? WHERE MaNV=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getCmnd());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, nv.getNgaySinh() != null ? new Date(nv.getNgaySinh().getTime()) : null);
            ps.setString(5, nv.getDiaChi());
            ps.setDate(6, nv.getNgayTuyenDung() != null ? new Date(nv.getNgayTuyenDung().getTime()) : null);
            ps.setString(7, nv.getChucDanh());
            ps.setString(8, nv.getMaKhoa());
            ps.setString(9, nv.getMaNV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
