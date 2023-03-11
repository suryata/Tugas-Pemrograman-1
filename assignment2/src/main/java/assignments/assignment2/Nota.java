package assignments.assignment2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// import assignments.assignment1.NotaGenerator;

public class Nota {
    //attributes yang diperlukan untuk class ini
    private int idNota;
    private String paket;
    private Member member;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    
    private static int counter = 0;
    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        //constructor untuk class ini
        this.member=member;
        this.paket=paket;
        this.berat=berat;
        this.tanggalMasuk=tanggalMasuk;
        this.sisaHariPengerjaan = lamaHari(paket);
        this.isReady = cekStatus(sisaHariPengerjaan);
        this.idNota = counter;
        String idMember = member.getid();
        System.out.print(generateNota(idMember, paket, berat, tanggalMasuk, idNota,isReady));
        counter++;
    }

    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

    public String getIDMember(){
        return member.getid();
    }

    public int getID(){
        return this.idNota;
    }

    public int getBerat(){
        return this.berat;
    }

    public int getsisaHariPengerjaan(){
        return this.sisaHariPengerjaan;
    }

    public String getPaket(){
        return this.paket;
    }

    public String getTanggalMasuk(){
        return this.tanggalMasuk;
    }

    public boolean getIsready(){
        return this.isReady;
    }

    public String tambahTanggal(String tanggalMasuk, int hari){
        //format tanggal yang digunakan
        String tanggalAkhir="";

        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fmt.parse(tanggalMasuk));
            calendar.add(Calendar.DATE,hari);
            tanggalAkhir=fmt.format(calendar.getTime());
        }catch(ParseException e){
            e.printStackTrace();
        }

        //calendar udah diubah jadi calendar raw
        //akan mereturn tanggal yang sudah ditambahkan sesuai ketentuan
        return tanggalAkhir;
    }

    //menentukan lama hari dan harga sesuai paket yang dipilih
    public int lamaHari(String paketDipilih){
        String paket =paketDipilih.toLowerCase();
        int lamaHari;
        if (paket.equals("express")){
            lamaHari=1;
        }
        else if (paket.equals("fast")){
            lamaHari=2;
        }
        else{
            lamaHari=3;
        }
        return lamaHari;
    }

    public int hargaPerKG(String paketDipilih){
        String paket =paketDipilih.toLowerCase();
        int harga;
        if (paket.equals("express")){
            harga=12000;
        }
        else if (paket.equals("fast")){
            harga=10000;
        }
        else{
            harga=7000;
        }

        return harga;
    }

    public boolean cekStatus(int lamaHari){
        if (lamaHari==0){
            return true;
        }
        else{
            return false;
        }
    }

    public String cekReady(boolean isReady){
        if (isReady){
            return "Sudah dapat diambil!";
        }
        else{
            return "Belum bisa diambil :(";
        }
    }

    public void nextDay(){
        if(sisaHariPengerjaan>0){
            sisaHariPengerjaan--;
        }
    }

    public String dapatDiskon(int hargaTotal){
        if (member.isEligible()) {
            hargaTotal = (int)(hargaTotal*0.5);
            member.setEligibleForDiscount(false);
            member.resetbonusCounter();
            return " = "+hargaTotal+" (Discount member 50%!!!)";
        }else{
            return "";
        }
    }

    public String generateNota(String id, String paketDipilih, int berat, String tanggalTerima, int idNota, boolean isReady){
        int harga = hargaPerKG(paketDipilih);
        int hari = lamaHari(paketDipilih);
        String tanggalSelesai= "";
        String status=cekReady(isReady);
        
        //jika berat kurang dari 2 akan dianggap menjadi 2kg
        String pembulatan;
        if (berat<2){
            berat=2;
            pembulatan="Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg\n";
        }
        else{
            pembulatan="";
        }
        
        //memformat tanggal terima dan tanggal selesai
        tanggalTerima=tambahTanggal(tanggalTerima, 0);
        
        //tanggal terima dijumlahkan dengan lama hari sesuai paket yang dipilih
        tanggalSelesai=tambahTanggal(tanggalTerima,hari);
        
        //menghitung harga total
        int hargaTotal=berat*harga;
        String diskon = dapatDiskon(hargaTotal); 
        
        //mmereturn string sesuai dengan testcase yang ada
        String solution = 
                pembulatan+
                "Berhasil menambahkan nota!\n"+
                "[ID Nota = "+ idNota +"]"+"\n"+
                "ID    : "+id+"\n" +
                "Paket : "+paketDipilih+"\n" +
                "Harga :\n" +
                berat+" kg x "+harga+" = "+hargaTotal+diskon+ "\n" +
                "Tanggal Terima  : "+tanggalTerima+"\n" +
                "Tanggal Selesai : "+tanggalSelesai+"\n"+
                "Status          : "+status;

        return solution;

    }

    //tambahkan methods yang diperlukan untuk class ini
}
