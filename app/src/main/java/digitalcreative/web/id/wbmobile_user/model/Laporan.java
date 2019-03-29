package digitalcreative.web.id.wbmobile_user.model;

/**
 * Created by User on 26/03/2019.
 */

public class Laporan {
    String masalah, deskripsi;

    public Laporan() {
    }

    public Laporan(String masalah, String deskripsi) {
        this.masalah = masalah;
        this.deskripsi = deskripsi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getMasalah() {
        return masalah;
    }

    public void setMasalah(String masalah) {
        this.masalah = masalah;
    }
}
