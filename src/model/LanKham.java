package model;

public class LanKham {
    private int maLanKham;
    private String maBN;
    private String maBS;
    private java.util.Date ngayGioKham;
    private String ketLuan;

    public LanKham() {}

    public LanKham(int maLanKham, String maBN, String maBS, java.util.Date ngayGioKham, String ketLuan) {
        this.maLanKham = maLanKham;
        this.maBN = maBN;
        this.maBS = maBS;
        this.ngayGioKham = ngayGioKham;
        this.ketLuan = ketLuan;
    }

    public int getMaLanKham() {
        return maLanKham;
    }

    public void setMaLanKham(int maLanKham) {
        this.maLanKham = maLanKham;
    }
    public String getMaBN() {
        return maBN;
    }

    public void setMaBN(String maBN) {
        this.maBN = maBN;
    }
    public String getMaBS() {
        return maBS;
    }

    public void setMaBS(String maBS) {
        this.maBS = maBS;
    }
    public java.util.Date getNgayGioKham() {
        return ngayGioKham;
    }

    public void setNgayGioKham(java.util.Date ngayGioKham) {
        this.ngayGioKham = ngayGioKham;
    }
    public String getKetLuan() {
        return ketLuan;
    }

    public void setKetLuan(String ketLuan) {
        this.ketLuan = ketLuan;
    }
}
