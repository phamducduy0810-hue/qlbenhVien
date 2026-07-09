package model;

public class NguoiLienHe {
    private int maNLH;
    private String maBN;
    private String hoTen;
    private String soDienThoai;
    private String moiQuanHe;

    public NguoiLienHe() {}

    public NguoiLienHe(int maNLH, String maBN, String hoTen, String soDienThoai, String moiQuanHe) {
        this.maNLH = maNLH;
        this.maBN = maBN;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.moiQuanHe = moiQuanHe;
    }

    public int getMaNLH() {
        return maNLH;
    }

    public void setMaNLH(int maNLH) {
        this.maNLH = maNLH;
    }
    public String getMaBN() {
        return maBN;
    }

    public void setMaBN(String maBN) {
        this.maBN = maBN;
    }
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getMoiQuanHe() {
        return moiQuanHe;
    }

    public void setMoiQuanHe(String moiQuanHe) {
        this.moiQuanHe = moiQuanHe;
    }
}
