package model;

public class BenhNhan {
    private String maBN;
    private String cmnd;
    private String hoTen;
    private String gioiTinh;
    private java.util.Date ngaySinh;
    private String diaChi;
    private java.util.Date ngayDangKy;

    public BenhNhan() {}

    public BenhNhan(String maBN, String cmnd, String hoTen, String gioiTinh, java.util.Date ngaySinh, String diaChi, java.util.Date ngayDangKy) {
        this.maBN = maBN;
        this.cmnd = cmnd;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.ngayDangKy = ngayDangKy;
    }

    public String getMaBN() {
        return maBN;
    }

    public void setMaBN(String maBN) {
        this.maBN = maBN;
    }
    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    public java.util.Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(java.util.Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public java.util.Date getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(java.util.Date ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }
}
