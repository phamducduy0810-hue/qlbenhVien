package model;

public class Giuong {
    private String maGiuong;
    private int soGiuong;
    private String maPhong;
    private String trangThai;

    public Giuong() {}

    public Giuong(String maGiuong, int soGiuong, String maPhong, String trangThai) {
        this.maGiuong = maGiuong;
        this.soGiuong = soGiuong;
        this.maPhong = maPhong;
        this.trangThai = trangThai;
    }

    public String getMaGiuong() {
        return maGiuong;
    }

    public void setMaGiuong(String maGiuong) {
        this.maGiuong = maGiuong;
    }
    public int getSoGiuong() {
        return soGiuong;
    }

    public void setSoGiuong(int soGiuong) {
        this.soGiuong = soGiuong;
    }
    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }
    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
