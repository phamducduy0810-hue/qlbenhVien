package model;

public class Giuong {
    private String soGiuong;
    private String soPhong;

    public Giuong() {}

    public Giuong(String soGiuong, String soPhong) {
        this.soGiuong = soGiuong;
        this.soPhong = soPhong;
    }

    public String getSoGiuong() {
        return soGiuong;
    }

    public void setSoGiuong(String soGiuong) {
        this.soGiuong = soGiuong;
    }
    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }
}
