package model;

public class NhanVien {
    private String maSo;
    private String cmnd;
    private String gioiTinh;
    private java.util.Date ngaySinh;
    private String diaChi;
    private java.util.Date ngayTuyenDung;
    private String chucDanh;
    private String maKhoa;

    public NhanVien() {}

    public NhanVien(String maSo, String cmnd, String gioiTinh, java.util.Date ngaySinh, String diaChi, java.util.Date ngayTuyenDung, String chucDanh, String maKhoa) {
        this.maSo = maSo;
        this.cmnd = cmnd;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.ngayTuyenDung = ngayTuyenDung;
        this.chucDanh = chucDanh;
        this.maKhoa = maKhoa;
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
    public java.util.Date getNgayTuyenDung() {
        return ngayTuyenDung;
    }

    public void setNgayTuyenDung(java.util.Date ngayTuyenDung) {
        this.ngayTuyenDung = ngayTuyenDung;
    }
    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }
    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }
}
