package assignments.assignment3.nota;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.user.Member;

public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private  int berat;
    private int id;
    private String tanggalMasuk;
    private boolean isDone;
    static public int totalNota;
    private String tanggalSelesai;

    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggal;
        this.sisaHariPengerjaan = lamaHari(paket);
        this.id = totalNota;
        this.baseHarga = hargaPerKG(paket);
        this.tanggalSelesai = addDate(tanggal, sisaHariPengerjaan);  
        addService(new CuciService());
        totalNota++;
    }

    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");   //format tanggal

    private static String addDate(String tanggalMasuk, int tambahanHari) {
        try {
            Date date = fmt.parse(tanggalMasuk);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, tambahanHari); // Adding days to the date
            Date newDate = cal.getTime();
            return fmt.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String kompensasi(){
        setIsDone();
        if((getSisaHariPengerjaan()<0)){
            return " Ada kompensasi keterlambatan "+Math.abs(getSisaHariPengerjaan())+" * 2000 hari";
        }
        else{
            return "";
        }
    };

    public void addService(LaundryService service){
        if (services == null) {
            services = new LaundryService[]{service};
        } else {
            LaundryService[] newArray = new LaundryService[services.length + 1];
            for (int i = 0; i < services.length; i++) {
                newArray[i] = services[i];
            }
            newArray[services.length] = service;
            services = newArray;
        }
    }

    public String kerjakan(){
        for (LaundryService laundryService : services) {
            if(!laundryService.isDone()){
                return "Nota "+getId()+" : "+ laundryService.doWork();
            }
        }
        return "Nota "+getId()+" : "+"Sudah selesai.";
    }

    public void toNextDay() {
        if(!isDone){
            sisaHariPengerjaan--;
        }
    }

    public long calculateHarga(){
        long harga = baseHarga*berat;
        if(services.length==0){
            return harga;
        }
        else{
            for(LaundryService service : services){
                harga+=service.getHarga(berat);
            }
            return harga;
        }
    }

    public void setIsDone(){
        boolean allServicesDone = true;
        for (LaundryService laundryService : services) {
            if(!laundryService.isDone()){
                allServicesDone = false;
                break;
            }
        }
        isDone = allServicesDone;
    }

    public String getNotaStatus(){
        setIsDone();
        if(isDone){
            return "Nota "+getId()+" : "+"Sudah selesai.";
        }else{
            return "Nota "+getId()+" : "+"Belum selesai.";
        }
    }

    public String getServiceList(LaundryService[] services) {
        StringBuilder sb = new StringBuilder();
        for (LaundryService service : services) {
            sb.append("-").append(service.getServiceName()).append(" @ Rp.").append(service.getHarga(berat)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString(){
        String service = getServiceList(services);
        long hargaAkhir;
        if(getSisaHariPengerjaan()<0){
            hargaAkhir = calculateHarga()+(2000L*getSisaHariPengerjaan());
        }else{
            hargaAkhir=calculateHarga();
        }
        return "[ID Nota = "+id+"]\n"+
        "ID    : "+member.getId()+"\n"+
        "Paket : "+paket+"\n"+
        "Harga : \n"+
        berat+" kg x "+baseHarga+" = "+berat*baseHarga+"\n"+
        "tanggal terima  : "+tanggalMasuk+"\n"+
        "tanggal selesai : "+tanggalSelesai+"\n"+
        "--- SERVICE LIST ---\n"+
        service+
        "Harga Akhir: "+ hargaAkhir+ kompensasi()+"\n";
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }

    public int getId(){
        return id;
    }

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


    public boolean cekStatus(int lamaHari){
        if (lamaHari==0){
            return true;
        }
        else{
            return false;
        }
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
}
