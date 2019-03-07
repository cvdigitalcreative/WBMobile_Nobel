package digitalcreative.web.id.wbmobile_user.view.kelas;

/**
 * Created by User on 05/03/2019.
 */

public class MateriKursus {
    private String namaPaket;
    private String namaPaketReal;

    public MateriKursus(){}

    public void setNamaPaket(String nama){
        if(nama.equals("kelas_intensif"))
            namaPaket = "Kelas Intesif";
        else if(nama.equals("pemrograman_dekstop"))
            namaPaket = "Pemrograman Desktop";
        else if(nama.equals("pemrograman_mobile"))
            namaPaket = "Pemrograman Mobile";
        else if(nama.equals("pemrograman_website"))
            namaPaket = "Pemrograman Website";
        else
            namaPaket = "Pengenalan Pemrograman";
    }

    public String getNamaPaket(){
        return namaPaket;
    }

    public void setNamaPaketReal(String nama){
        if(nama.equals("Kelas Intesif"))
            namaPaketReal = "kelas_intensif";
        else if(nama.equals("Pemrograman Desktop"))
            namaPaketReal = "pemrograman_dekstop";
        else if(nama.equals("Pemrograman Mobile"))
            namaPaketReal = "pemrograman_mobile";
        else if(nama.equals("Pemrograman Website"))
            namaPaketReal = "pemrograman_website";
        else
            namaPaketReal = "pengenalan_pemrograman";
    }

    public String getNamaPaketReal(){
        return namaPaketReal;
    }
}
