package digitalcreative.web.id.wbmobile_user.model;

public class Pemesanan {
    String konfirmasi, harga, tanggal_batch, status;

    public Pemesanan(){}

    public Pemesanan(String konfirmasi, String harga, String tanggal_batch, String status) {
        this.konfirmasi = konfirmasi;
        this.harga = harga;
        this.tanggal_batch = tanggal_batch;
        this.status = status;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTanggal_batch() {
        return tanggal_batch;
    }

    public void setTanggal_batch(String tanggal_batch) {
        this.tanggal_batch = tanggal_batch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
