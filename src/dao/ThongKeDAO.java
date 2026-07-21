package dao;

import connectdb.DatabaseConnection;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ThongKeDAO {
    
    // 1. Số bệnh nhân mới đăng ký trong kỳ
    public int getSoBenhNhanMoi(java.util.Date fromDate, java.util.Date toDate) {
        String sql = "SELECT COUNT(*) FROM BenhNhan WHERE NgayDangKy BETWEEN ? AND ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(fromDate.getTime()));
            ps.setDate(2, new java.sql.Date(toDate.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    
    // 2. Số lần khám trong kỳ
    public int getSoLanKham(java.util.Date fromDate, java.util.Date toDate) {
        String sql = "SELECT COUNT(*) FROM LanKham WHERE ThoiDiemKham BETWEEN ? AND ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(fromDate.getTime()));
            ps.setDate(2, new java.sql.Date(toDate.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    
    // 3. Số ca nhập viện trong kỳ
    public int getSoCaNhapVien(java.util.Date fromDate, java.util.Date toDate) {
        String sql = "SELECT COUNT(*) FROM PhieuNhapVien WHERE NgayNhan BETWEEN ? AND ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(fromDate.getTime()));
            ps.setDate(2, new java.sql.Date(toDate.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    
    // 4. Tỷ lệ sử dụng giường (Số giường đang có bệnh nhân / Tổng số giường)
    public String getTyLeSuDungGiuong() {
        String sqlGiuong = "SELECT COUNT(*) FROM Giuong";
        String sqlDangDung = "SELECT COUNT(DISTINCT SoGiuong) FROM PhieuNhapVien WHERE NgayTra IS NULL";
        try (Connection con = DatabaseConnection.getConnection()) {
            int total = 0, used = 0;
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sqlGiuong)) {
                if (rs.next()) total = rs.getInt(1);
            }
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sqlDangDung)) {
                if (rs.next()) used = rs.getInt(1);
            }
            if (total == 0) return "0% (0/0)";
            double pct = (double)used / total * 100;
            return String.format("%.2f%% (%d/%d)", pct, used, total);
        } catch (Exception e) { e.printStackTrace(); }
        return "0%";
    }
    
    // 5. Top 5 bệnh phổ biến trong kỳ
    public Map<String, Integer> getTop5Benh(java.util.Date fromDate, java.util.Date toDate) {
        Map<String, Integer> top5 = new LinkedHashMap<>();
        String sql = "SELECT TenBenh, COUNT(*) as SoCa FROM BenhDaPhatHien WHERE NgayPhatHien BETWEEN ? AND ? GROUP BY TenBenh ORDER BY SoCa DESC LIMIT 5";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(fromDate.getTime()));
            ps.setDate(2, new java.sql.Date(toDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                top5.put(rs.getString("TenBenh"), rs.getInt("SoCa"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return top5;
    }
    
    // 6. Tổng số bác sỹ
    public int getTongBacSy() {
        String sql = "SELECT COUNT(*) FROM BacSy";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    
    // 7. Tổng số nhân viên
    public int getTongNhanVien() {
        String sql = "SELECT COUNT(*) FROM NhanVien";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    
    // 8. Xu hướng lượt khám theo tháng
    public Map<String, Integer> getLuotKhamTheoThang(java.util.Date fromDate, java.util.Date toDate) {
        Map<String, Integer> trend = new LinkedHashMap<>();
        String sql = "SELECT DATE_FORMAT(ThoiDiemKham, '%Y-%m') as Thang, COUNT(*) as SoLuot FROM LanKham WHERE ThoiDiemKham BETWEEN ? AND ? GROUP BY Thang ORDER BY Thang";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(fromDate.getTime()));
            ps.setDate(2, new java.sql.Date(toDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                trend.put(rs.getString("Thang"), rs.getInt("SoLuot"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return trend;
    }
}
