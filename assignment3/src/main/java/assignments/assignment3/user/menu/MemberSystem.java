package assignments.assignment3.user.menu;
import java.text.SimpleDateFormat;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice) {           //saya menambah method laundry dan nota
            case 1 -> laundry();
            case 2 -> nota();
            case 3 -> logout = true;
            default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        Member[] memberListNew = new Member[memberList.length+1];
        for(int i= 0; i<memberList.length;i++){
            memberListNew[i] = memberList[i];
        }
        memberListNew[memberList.length] = member;
        memberList = memberListNew;
    }

    //method ini berfungsi jika member ingin laundry dan meminta paket berat dan service yang diinginkan
    private void laundry() {
        // Logic for handling laundry choice
        String paket = inputPaket();
        int berat = validasiBerat();
        String pembulatan;
        if (berat<2){
            berat=2;
            pembulatan="Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg\n";
        }
        else{
            pembulatan="";
        }

        //berikut ini meminta servis
        System.out.print(pembulatan);
        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?\n"+"Hanya tambah 1000 / kg :0");
        System.out.print("[Ketik x untuk tidak mau]: ");
        boolean setrika = validasiService();
        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!\n"+"Cuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        boolean antar = validasiService();

        //membuat nota dan menambah servis ke nota
        String tanggal = fmt.format(NotaManager.cal.getTime());
        Nota nota = new Nota(loginMember, berat, paket, tanggal);
        if (setrika){
            nota.addService(new SetrikaService());
        }
        if(antar){
            nota.addService(new AntarService());
        }

        //menambah nota ke member tersebut dan ke notaManager
        loginMember.addNota(nota);
        NotaManager.addNota(nota);
        System.out.print("Nota berhasil dibuat!\n");

    }
    
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");   //format tanggal

    //jika dipilih 2 akan mengeprint seluruh nota yang dimiliki member tersebut
    private void nota() {
        // Logic for handling nota choice
        for (Nota nota : loginMember.getNotaList()) {
            System.out.println(nota.toString());
        }
    }

    //jika x maka member tidak menggunakan service tersebut
    private boolean validasiService(){
        String pilihan = in.nextLine();
        if(pilihan.equalsIgnoreCase("x")){
            return false;
        }
        return true;
    }

    //showpaket
    private void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    //validasi input paket dari tp 1
    private String inputPaket(){
        boolean ada=false;
        String paketLengkap="";
        String paketAsli="";
        String paketDipilih="";
        //selama paket belum valid program akan tetap meminta input
        while(!ada){
            System.out.println("Masukkan paket laundry: ");
            showPaket();
            //saya melowercase nya karena di discord disebutkan case insensitive
            //agar jika memasukkan input kosong meminta ulang input (handle)
            paketLengkap= in.nextLine();
            String[] paket = paketLengkap.split(" ");
            // cek apakah kata lebih dari 1
            if (paket.length > 1) {
                ada = false;
                System.out.printf("Paket %s tidak diketahui",paketLengkap);
            } else {
                paketAsli = paket[0];
                paketDipilih=paketAsli.toLowerCase();
    
                if(paketDipilih.equals("express")){
                    ada=true;
                }
                else if(paketDipilih.equals("fast")){
                    ada=true;
                }
                else if(paketDipilih.equals("reguler")){
                    ada=true;
                }
                //saye menghandle jika input kosong
                else if(paketDipilih.isEmpty()){
                    System.out.printf("Anda belum memasukkan jenis paket");
                }
                //jika tidak ada maka mengeprint paket tidak diketahui
                else{
                    System.out.printf("Paket %s tidak diketahui",paketAsli);
                }
            }
        }
        //akan mereturn paket asli sesuai yang ditulis oleh pengguna
        return paketAsli;
    }

    //validasi berat dari tp1
    public int validasiBerat(){
        int berat=0;
        boolean valid=false;
        System.out.println("Masukkan berat cucian Anda [Kg]: ");
        while(!valid){
            //dari discord dikatakan jika input seperti "1 2" akan meminta input ulang maka saya menghandle nya
            try{
                String beratString = in.nextLine();
                String[] arrayCheck = beratString.split("\\s+");

                if(arrayCheck.length >1){
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                    continue;
                }

                berat=Integer.parseInt(arrayCheck[0]);
                //jika berat kurang dari 1 akan meminta input ulang
                if(berat<1){
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                }
                else{
                    valid=true;
                }
            //jika string atau float akan meminta input ulang
            }catch(NumberFormatException e){
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
            }
        }
        //mereturn berat yang sudah valid
        return berat;
    }
}