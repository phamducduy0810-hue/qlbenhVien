package model;

public class BenhDaPhatHien {
    private int maBenh;
    private String maBN;
    private String tenBenh;
    private java.util.Date ngayPhatHien;
    private String ghiChu;

    public BenhDaPhatHien() {}

    public BenhDaPhatHien(int maBenh, String maBN, String tenBenh, java.util.Date ngayPhatHien, String ghiChu) {
        this.maBenh = maBenh;
        this.maBN = maBN;
        this.tenBenh = tenBenh;
        this.ngayPhatHien = ngayPhatHien;
        this.ghiChu = ghiChu;
    }

    public int getMaBenh() {
        return maBenh;
    }

    public void setMaBenh(int maBenh) {
        this.maBenh = maBenh;
    }
    public String getMaBN() {
        return maBN;
    }

    public void setMaBN(String maBN) {
        this.maBN = maBN;
    }
    public String getTenBenh() {
        return tenBenh;
    }

    public void setTenBenh(String tenBenh) {
        this.tenBenh = tenBenh;
    }
    public java.util.Date getNgayPhatHien() {
        return ngayPhatHien;
    }

    public void setNgayPhatHien(java.util.Date ngayPhatHien) {
        this.ngayPhatHien = ngayPhatHien;
    }
    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
