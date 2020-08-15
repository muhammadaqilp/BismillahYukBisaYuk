package com.example.bismillahyukbisayuk.Model;

public class Berita {
    private String beritaid;
    private String judulberita;
    private String isiberita;
    private String jenisberita;

    public Berita(String beritaid, String judulberita, String isiberita, String jenisberita) {
        this.beritaid = beritaid;
        this.judulberita = judulberita;
        this.isiberita = isiberita;
        this.jenisberita = jenisberita;
    }

    public Berita() {
    }

    public String getBeritaid() {
        return beritaid;
    }

    public void setBeritaid(String beritaid) {
        this.beritaid = beritaid;
    }

    public String getJudulberita() {
        return judulberita;
    }

    public void setJudulberita(String judulberita) {
        this.judulberita = judulberita;
    }

    public String getIsiberita() {
        return isiberita;
    }

    public void setIsiberita(String isiberita) {
        this.isiberita = isiberita;
    }

    public String getJenisberita() {
        return jenisberita;
    }

    public void setJenisberita(String jenisberita) {
        this.jenisberita = jenisberita;
    }
}
