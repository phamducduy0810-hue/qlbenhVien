package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import model.BacSy;

public class BacSyDAO {

    public List<BacSy> getAll() {
        List<BacSy> list = new ArrayList<>();
        String sql = "SELECT * FROM BacSy";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BacSy obj = new BacSy();
                obj.setMaSo(rs.getString("MaSo"));
                obj.setDienThoai(rs.getString("DienThoai"));
                obj.setChuyenNganh(rs.getString("ChuyenNganh"));
                obj.setKyNang(rs.getString("KyNang"));
                obj.setLichLamViec(rs.getString("LichLamViec"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(BacSy obj) {
        String sql = "INSERT INTO BacSy(MaSo, DienThoai, ChuyenNganh, KyNang, LichLamViec) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getMaSo());
            ps.setString(2, obj.getDienThoai());
            ps.setString(3, obj.getChuyenNganh());
            ps.setString(4, obj.getKyNang());
            ps.setString(5, obj.getLichLamViec());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maSo) {
        String sql = "DELETE FROM BacSy WHERE MaSo=?";
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
