package com.example.bai_asm_api;


public class Car {
    private String _id;
    private String ten;
    private int namSX;
    private String hang;
    private int gia;

    public Car(String ten, int namSX, String hang, int gia) {
        this.ten = ten;
        this.namSX = namSX;
        this.hang = hang;
        this.gia = gia;
    }

    public String get_id() {
        return _id;
    }

    public String getTen() {
        return ten;
    }

    public int getNamSX() {
        return namSX;
    }

    public String getHang() {
        return hang;
    }

    public int getGia() {
        return gia;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setNamSX(int namSX) {
        this.namSX = namSX;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}

