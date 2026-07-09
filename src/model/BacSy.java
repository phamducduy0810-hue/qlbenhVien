package model;

public class BacSy {
    private String maBS;
    private String chuyenNganh;
    private String kyNang;
    private String lichLamViec;

    public BacSy() {}

    public BacSy(String maBS, String chuyenNganh, String kyNang, String lichLamViec) {
        this.maBS = maBS;
        this.chuyenNganh = chuyenNganh;
        this.kyNang = kyNang;
        this.lichLamViec = lichLamViec;
    }

    public String getMaBS() {
        return maBS;
    }

    public void setMaBS(String maBS) {
        this.maBS = maBS;
    }
    public String getChuyenNganh() {
        return chuyenNganh;
    }

    public void setChuyenNganh(String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
    }
    public String getKyNang() {
        return kyNang;
    }

    public void setKyNang(String kyNang) {
        this.kyNang = kyNang;
    }
    public String getLichLamViec() {
        return lichLamViec;
    }

    public void setLichLamViec(String lichLamViec) {
        this.lichLamViec = lichLamViec;
    }
}
