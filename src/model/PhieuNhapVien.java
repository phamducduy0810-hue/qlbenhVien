package model;

public class PhieuNhapVien {
    private int maPhieu;
    private String maBN;
    private String maGiuong;
    private java.util.Date ngayNhan;
    private java.util.Date ngayTra;
    private String ghiChu;

    public PhieuNhapVien() {}

    public PhieuNhapVien(int maPhieu, String maBN, String maGiuong, java.util.Date ngayNhan, java.util.Date ngayTra, String ghiChu) {
        this.maPhieu = maPhieu;
        this.maBN = maBN;
        this.maGiuong = maGiuong;
        this.ngayNhan = ngayNhan;
        this.ngayTra = ngayTra;
        this.ghiChu = ghiChu;
    }

    public int getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }
    public String getMaBN() {
        return maBN;
    }

    public void setMaBN(String maBN) {
        this.maBN = maBN;
    }
    public String getMaGiuong() {
        return maGiuong;
    }

    public void setMaGiuong(String maGiuong) {
        this.maGiuong = maGiuong;
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
    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
