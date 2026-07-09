package model;

public class BacSy {
    private String maSo;
    private String dienThoai;
    private String chuyenNganh;
    private String kyNang;
    private String lichLamViec;

    public BacSy() {}

    public BacSy(String maSo, String dienThoai, String chuyenNganh, String kyNang, String lichLamViec) {
        this.maSo = maSo;
        this.dienThoai = dienThoai;
        this.chuyenNganh = chuyenNganh;
        this.kyNang = kyNang;
        this.lichLamViec = lichLamViec;
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }
    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
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
