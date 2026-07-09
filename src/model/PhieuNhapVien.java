package model;

public class PhieuNhapVien {
    private int maPhieu;
    private String maBenhNhan;
    private String soGiuong;
    private java.util.Date ngayNhan;
    private java.util.Date ngayTra;

    public PhieuNhapVien() {}

    public PhieuNhapVien(int maPhieu, String maBenhNhan, String soGiuong, java.util.Date ngayNhan, java.util.Date ngayTra) {
        this.maPhieu = maPhieu;
        this.maBenhNhan = maBenhNhan;
        this.soGiuong = soGiuong;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
    }

    public int getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }
    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }
    public String getSoGiuong() {
        return soGiuong;
    }

    public void setSoGiuong(String soGiuong) {
        this.soGiuong = soGiuong;
    }
    public java.util.Date getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(java.util.Date ngayNhan) {
        this.ngayNhan = ngayNhan;
    }
    public java.util.Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(java.util.Date ngayTra) {
        this.ngayTra = ngayTra;
    }
}
