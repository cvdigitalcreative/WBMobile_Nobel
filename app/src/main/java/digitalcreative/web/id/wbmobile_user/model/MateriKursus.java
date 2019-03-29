package digitalcreative.web.id.wbmobile_user.model;

/**
 * Created by User on 05/03/2019.
 */

public class MateriKursus {
    private String deskripsi;
    private String durasi_pertemuan;
    private String lama_pertemuan;
    private String harga;
    private String judul;
    private Modul modul;

    public MateriKursus(String deskripsi, String durasi_pertemuan, String lama_pertemuan, String harga, String judul, Modul modul) {
        this.deskripsi = deskripsi;
        this.durasi_pertemuan = durasi_pertemuan;
        this.lama_pertemuan = lama_pertemuan;
        this.harga = harga;
        this.judul = judul;
        this.modul = modul;
    }

    public MateriKursus() {}

    public Modul getModul() {
        return modul;
    }

    public void setModul(Modul modul) {
        this.modul = modul;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return this.deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDurasi_pertemuan() {
        return durasi_pertemuan;
    }

    public void setDurasi_pertemuan(String durasi_pertemuan) {
        this.durasi_pertemuan = durasi_pertemuan;
    }

    public String getLama_pertemuan() {
        return this.lama_pertemuan;
    }

    public void setLama_pertemuan(String lama_pertemuan) {
        this.lama_pertemuan = lama_pertemuan;
    }

    public String getHarga() {
        return this.harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
