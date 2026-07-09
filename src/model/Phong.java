package model;

public class Phong {
    private String soPhong;
    private String tenPhong;
    private String maKhoa;

    public Phong() {}

    public Phong(String soPhong, String tenPhong, String maKhoa) {
        this.soPhong = soPhong;
        this.tenPhong = tenPhong;
        this.maKhoa = maKhoa;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
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
