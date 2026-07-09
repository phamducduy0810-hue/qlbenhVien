CREATE DATABASE IF NOT EXISTS qlbv_thucuc DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE qlbv_thucuc;

-- Bảng Khoa Điều Trị
CREATE TABLE IF NOT EXISTS KhoaDieuTri (
    MaKhoa VARCHAR(50) PRIMARY KEY,
    TenKhoa VARCHAR(255) NOT NULL
);

-- Bảng Nhân Viên (bao gồm cả thông tin Cá Nhân)
CREATE TABLE IF NOT EXISTS NhanVien (
    MaNV VARCHAR(50) PRIMARY KEY,
    CMND VARCHAR(20) NOT NULL UNIQUE,
    HoTen VARCHAR(255) NOT NULL,
    GioiTinh VARCHAR(10),
    NgaySinh DATE,
    DiaChi TEXT,
    NgayTuyenDung DATE,
    ChucDanh VARCHAR(100),
    MaKhoa VARCHAR(50),
    FOREIGN KEY (MaKhoa) REFERENCES KhoaDieuTri(MaKhoa)
);

-- Bảng Bác Sỹ (kế thừa logic từ Nhân Viên)
CREATE TABLE IF NOT EXISTS BacSy (
    MaBS VARCHAR(50) PRIMARY KEY,
    ChuyenNganh VARCHAR(255),
    KyNang TEXT,
    LichLamViec TEXT,
    FOREIGN KEY (MaBS) REFERENCES NhanVien(MaNV)
);

-- Bảng Bệnh Nhân
CREATE TABLE IF NOT EXISTS BenhNhan (
    MaBN VARCHAR(50) PRIMARY KEY,
    CMND VARCHAR(20) NOT NULL UNIQUE,
    HoTen VARCHAR(255) NOT NULL,
    GioiTinh VARCHAR(10),
    NgaySinh DATE,
    DiaChi TEXT,
    NgayDangKy DATE
);

-- Bảng Người Liên Hệ (người nhà bệnh nhân)
CREATE TABLE IF NOT EXISTS NguoiLienHe (
    MaNLH INT AUTO_INCREMENT PRIMARY KEY,
    MaBN VARCHAR(50) NOT NULL,
    HoTen VARCHAR(255),
    SoDienThoai VARCHAR(20),
    MoiQuanHe VARCHAR(50),
    FOREIGN KEY (MaBN) REFERENCES BenhNhan(MaBN)
);

-- Bảng Bệnh Đã Phát Hiện (của bệnh nhân)
CREATE TABLE IF NOT EXISTS BenhDaPhatHien (
    MaBenh INT AUTO_INCREMENT PRIMARY KEY,
    MaBN VARCHAR(50) NOT NULL,
    TenBenh VARCHAR(255) NOT NULL,
    NgayPhatHien DATE,
    GhiChu TEXT,
    FOREIGN KEY (MaBN) REFERENCES BenhNhan(MaBN)
);

-- Bảng Phòng (phòng thuộc khoa)
CREATE TABLE IF NOT EXISTS Phong (
    MaPhong VARCHAR(50) PRIMARY KEY,
    TenPhong VARCHAR(255) NOT NULL,
    MaKhoa VARCHAR(50),
    FOREIGN KEY (MaKhoa) REFERENCES KhoaDieuTri(MaKhoa)
);

-- Bảng Giường (thuộc phòng)
CREATE TABLE IF NOT EXISTS Giuong (
    MaGiuong VARCHAR(50) PRIMARY KEY,
    SoGiuong INT NOT NULL,
    MaPhong VARCHAR(50) NOT NULL,
    TrangThai VARCHAR(50) DEFAULT 'Trong', -- Trong, DaCoNguoi
    FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong)
);

-- Bảng Lần Khám (Bác sỹ khám cho Bệnh nhân)
CREATE TABLE IF NOT EXISTS LanKham (
    MaLanKham INT AUTO_INCREMENT PRIMARY KEY,
    MaBN VARCHAR(50) NOT NULL,
    MaBS VARCHAR(50) NOT NULL,
    NgayGioKham DATETIME NOT NULL,
    KetLuan TEXT,
    FOREIGN KEY (MaBN) REFERENCES BenhNhan(MaBN),
    FOREIGN KEY (MaBS) REFERENCES BacSy(MaBS)
);

-- Bảng Phiếu Nhập Viện (quản lý nằm giường)
CREATE TABLE IF NOT EXISTS PhieuNhapVien (
    MaPhieu INT AUTO_INCREMENT PRIMARY KEY,
    MaBN VARCHAR(50) NOT NULL,
    MaGiuong VARCHAR(50) NOT NULL,
    NgayNhan DATETIME NOT NULL,
    NgayTra DATETIME,
    GhiChu TEXT,
    FOREIGN KEY (MaBN) REFERENCES BenhNhan(MaBN),
    FOREIGN KEY (MaGiuong) REFERENCES Giuong(MaGiuong)
);
