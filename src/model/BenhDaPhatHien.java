package model;

public class BenhDaPhatHien {
    private int maBenh;
    private String maBenhNhan;
    private String tenBenh;
    private java.util.Date ngayPhatHien;

    public BenhDaPhatHien() {}

    public BenhDaPhatHien(int maBenh, String maBenhNhan, String tenBenh, java.util.Date ngayPhatHien) {
        this.maBenh = maBenh;
        this.maBenhNhan = maBenhNhan;
        this.tenBenh = tenBenh;
        this.ngayPhatHien = ngayPhatHien;
    }

    public int getMaBenh() {
        return maBenh;
    }

    public void setMaBenh(int maBenh) {
        this.maBenh = maBenh;
    }
    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
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
}
