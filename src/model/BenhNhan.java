package model;

public class BenhNhan {
    private String maSo;
    private String cmnd;
    private String gioiTinh;
    private java.util.Date ngaySinh;
    private String diaChi;
    private java.util.Date ngayDangKy;
    private String maBacSy;

    public BenhNhan() {}

    public BenhNhan(String maSo, String cmnd, String gioiTinh, java.util.Date ngaySinh, String diaChi, java.util.Date ngayDangKy, String maBacSy) {
        this.maSo = maSo;
        this.cmnd = cmnd;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.ngayDangKy = ngayDangKy;
        this.maBacSy = maBacSy;
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }
    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
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
    public String getMaBacSy() {
        return maBacSy;
    }

    public void setMaBacSy(String maBacSy) {
        this.maBacSy = maBacSy;
    }
}
