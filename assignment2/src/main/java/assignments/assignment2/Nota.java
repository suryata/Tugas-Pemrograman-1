package assignments.assignment2;

//library yang dibutuhkan
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Nota {
    //attributes yang diperlukan untuk class ini
    //idNota, paket, member, berat, tanggalMasuk, sisaHariPengerjaan, isReady
    //counter untuk id nota
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
        this.sisaHariPengerjaan = lamaHari(paket);                              //lama hari berdasarkan paket
        this.isReady = cekStatus(sisaHariPengerjaan);                           //status sudah ready atau belum
        this.idNota = counter;
        String idMember = member.getid();                                       //mendapatkan id dari class member
        System.out.print(generateNota(idMember, paket, berat, tanggalMasuk, idNota,isReady));   //memanggil fungsi generate nota
        counter++;                                                              //counter bertambah 1
    }

    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");   //format tanggal

    //getter id member
    public String getIDMember(){
        return member.getid();
    }

    //getter id nota
    public int getID(){
        return this.idNota;
    }

    //getter berat
    public int getBerat(){
        return this.berat;
    }

    //getter sisa hari pengerjaan
    public int getsisaHariPengerjaan(){
        return this.sisaHariPengerjaan;
    }

    //getter paket yang dipilih
    public String getPaket(){
        return this.paket;
    }

    //getter tanggal masuk
    public String getTanggalMasuk(){
        return this.tanggalMasuk;
    }

    //apakah nota sudah ready
    public boolean getIsready(){
        return this.isReady;
    }

    //menambahkan tanggal sesuai paket yang diinginkan
    public String tambahTanggal(String tanggalMasuk, int hari){
        //format tanggal yang digunakan
        String tanggalAkhir="";

        try{
            Calendar calendar = Calendar.getInstance();
            //diubah menjadi calendar raw kemudian ditambahkan
            calendar.setTime(fmt.parse(tanggalMasuk));
            calendar.add(Calendar.DATE,hari);
            //lalu diformat lagi 
            tanggalAkhir=fmt.format(calendar.getTime());
        }catch(ParseException e){
            e.printStackTrace();
        }

        //akan mereturn tanggal yang sudah ditambahkan sesuai ketentuan
        return tanggalAkhir;
    }

    //menentukan lama hari dan harga sesuai paket yang dipilih
    public int lamaHari(String paketDipilih){
        //menentukan lama hari berdasarkan paket yang dipilih
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

    //menentukan harga perKG berdasarkan paket yang dipilih
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

    // untuk mengecek status, jika sisa hari 0 maka akan true (status ready)
    public boolean cekStatus(int lamaHari){
        if (lamaHari==0){
            return true;
        }
        else{
            return false;
        }
    }

    //jika ready akan mereturn string sesuai ketentuan
    public String cekReady(boolean isReady){
        if (isReady){
            return "Sudah dapat diambil!";
        }
        else{
            return "Belum bisa diambil :(";
        }
    }

    //jika nextday hari pengerjaan akan dikurangi dengan 1
    public void nextDay(){
        if(sisaHariPengerjaan>0){
            sisaHariPengerjaan--;
        }
    }

    //jika dapat diskon harga akan dikurangi 50% dan mereturn string sesuai ketentuan
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

    //tambahkan methods yang diperlukan untuk class ini
    //generate nota sama seperti tp 1 tetapi ada sedikit perubahan
    public String generateNota(String id, String paketDipilih, int berat, String tanggalTerima, int idNota, boolean isReady){
        int harga = hargaPerKG(paketDipilih);
        int hari = lamaHari(paketDipilih);
        String tanggalSelesai= "";

        //status akan diprint di nota 
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
        //String jika mendapat diskon akan muncul pada nota
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
                "Status          : "+status+"\n";

        return solution;

    }
}
