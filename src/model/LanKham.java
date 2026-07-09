package model;

public class LanKham {
    private int maLanKham;
    private String maBenhNhan;
    private String maBacSy;
    private java.util.Date thoiDiemKham;
    private String ketLuan;

    public LanKham() {}

    public LanKham(int maLanKham, String maBenhNhan, String maBacSy, java.util.Date thoiDiemKham, String ketLuan) {
        this.maLanKham = maLanKham;
        this.maBenhNhan = maBenhNhan;
        this.maBacSy = maBacSy;
        this.thoiDiemKham = thoiDiemKham;
        this.ketLuan = ketLuan;
    }

    public int getMaLanKham() {
        return maLanKham;
    }

    public void setMaLanKham(int maLanKham) {
        this.maLanKham = maLanKham;
    }
    public String getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(String maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }
    public String getMaBacSy() {
        return maBacSy;
    }

    public void setMaBacSy(String maBacSy) {
        this.maBacSy = maBacSy;
    }
    public java.util.Date getThoiDiemKham() {
        return thoiDiemKham;
    }

    public void setThoiDiemKham(java.util.Date thoiDiemKham) {
        this.thoiDiemKham = thoiDiemKham;
    }
    public String getKetLuan() {
        return ketLuan;
    }

    public void setKetLuan(String ketLuan) {
        this.ketLuan = ketLuan;
    }
}
