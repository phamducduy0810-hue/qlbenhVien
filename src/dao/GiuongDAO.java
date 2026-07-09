package dao;

import connectdb.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Giuong;

public class GiuongDAO {

    public List<Giuong> getAll() {
        List<Giuong> list = new ArrayList<>();
        String sql = "SELECT * FROM Giuong";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Giuong g = new Giuong();
                g.setSoGiuong(rs.getString("SoGiuong"));
                g.setSoPhong(rs.getString("SoPhong"));
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Giuong g) {
        String sql = "INSERT INTO Giuong(SoGiuong, SoPhong) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, g.getSoGiuong());
            ps.setString(2, g.getSoPhong());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Giuong g) {
        String sql = "UPDATE Giuong SET SoPhong=? WHERE SoGiuong=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, g.getSoPhong());
            ps.setString(2, g.getSoGiuong());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String soGiuong) {
        String sql = "DELETE FROM Giuong WHERE SoGiuong=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, soGiuong);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
