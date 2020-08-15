package com.example.bismillahyukbisayuk.Model;

public class Histori {
    private String historiid;
    private String publisher;
    private String tanggalrekam;
    private String berat;
    private String tinggi;
    private String statusgizi;
    private String imunisasi;
    private String vitamin;

    public Histori(String historiid, String publisher, String tanggalrekam, String berat, String tinggi, String statusgizi, String imunisasi, String vitamin) {
        this.historiid = historiid;
        this.publisher = publisher;
        this.tanggalrekam = tanggalrekam;
        this.berat = berat;
        this.tinggi = tinggi;
        this.statusgizi = statusgizi;
        this.imunisasi = imunisasi;
        this.vitamin = vitamin;
    }

    public Histori() {
    }

    public String getHistoriid() {
        return historiid;
    }

    public void setHistoriid(String historiid) {
        this.historiid = historiid;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTanggalrekam() {
        return tanggalrekam;
    }

    public void setTanggalrekam(String tanggalrekam) {
        this.tanggalrekam = tanggalrekam;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }

    public String getStatusgizi() {
        return statusgizi;
    }

    public void setStatusgizi(String statusgizi) {
        this.statusgizi = statusgizi;
    }

    public String getImunisasi() {
        return imunisasi;
    }

    public void setImunisasi(String imunisasi) {
        this.imunisasi = imunisasi;
    }

    public String getVitamin() {
        return vitamin;
    }

    public void setVitamin(String vitamin) {
        this.vitamin = vitamin;
    }
}
