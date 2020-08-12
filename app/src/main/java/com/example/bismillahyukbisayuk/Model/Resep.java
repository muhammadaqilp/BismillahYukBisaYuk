package com.example.bismillahyukbisayuk.Model;

public class Resep {

    private String resepid;
    private String resepimage;
    private String namaresep;
    private String publisher;

    public Resep(String resepid, String resepimage, String namaresep, String publisher) {
        this.resepid = resepid;
        this.resepimage = resepimage;
        this.namaresep = namaresep;
        this.publisher = publisher;
    }

    public Resep() {
    }

    public String getResepid() {
        return resepid;
    }

    public void setResepid(String resepid) {
        this.resepid = resepid;
    }

    public String getResepimage() {
        return resepimage;
    }

    public void setResepimage(String resepimage) {
        this.resepimage = resepimage;
    }

    public String getNamaresep() {
        return namaresep;
    }

    public void setNamaresep(String namaresep) {
        this.namaresep = namaresep;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
