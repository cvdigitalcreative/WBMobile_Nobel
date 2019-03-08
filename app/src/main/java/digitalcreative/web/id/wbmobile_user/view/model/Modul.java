package digitalcreative.web.id.wbmobile_user.view.model;

/**
 * Created by User on 07/03/2019.
 */

public class Modul {
    String nama_modul, status, tema_materi, url_modul;

    public Modul(){}

    public Modul(String nama_modul, String status, String tema_materi, String url_modul) {
        this.nama_modul = nama_modul;
        this.status = status;
        this.tema_materi = tema_materi;
        this.url_modul = url_modul;
    }

    public String getNama_modul() {
        return nama_modul;
    }

    public void setNama_modul(String nama_modul) {
        this.nama_modul = nama_modul;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTema_materi() {
        return tema_materi;
    }

    public void setTema_materi(String tema_materi) {
        this.tema_materi = tema_materi;
    }

    public String getUrl_modul() {
        return url_modul;
    }

    public void setUrl_modul(String url_modul) {
        this.url_modul = url_modul;
    }
}
