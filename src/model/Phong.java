package model;

public class Phong {
    private String maPhong;
    private String tenPhong;
    private String maKhoa;

    public Phong() {}

    public Phong(String maPhong, String tenPhong, String maKhoa) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.maKhoa = maKhoa;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }
    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }
    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }
}
