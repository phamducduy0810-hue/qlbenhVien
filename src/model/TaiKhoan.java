package model;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;
    private String maNhanVien;
    private String maBacSy;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenDangNhap, String matKhau, String vaiTro, String maNhanVien, String maBacSy) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.maNhanVien = maNhanVien;
        this.maBacSy = maBacSy;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaBacSy() {
        return maBacSy;
    }

    public void setMaBacSy(String maBacSy) {
        this.maBacSy = maBacSy;
    }
}
