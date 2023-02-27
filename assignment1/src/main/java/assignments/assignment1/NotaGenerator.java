package assignments.assignment1;

//package yang digunakan yaitu untuk time, time formatter, trycatch, dan scanner
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
     public static void main(String[] args) {
        printMenu();
        //selama pilihan tidak 0 program akan terus berjalan (1/2). String dan bilangan negatif akan meminta input ulang
        int pilihan=inputMenu();
        while (pilihan != 0){
            //jika yang dipilih 1 akan masuk ke menu generate ID, program akan meminta nama, nomor HP 
            //input nomor hp akan divalidasi
            if (pilihan==1){
                System.out.println("================================");
                System.out.print("Masukkan nama Anda: \n");
                String nama=input.nextLine();
                String nomorHP=inputHP();

                // akan memanggil generateID dan mereturn ID customer
                String idCustomer=generateId(nama, nomorHP);
                System.out.printf("ID Anda : %s\n", idCustomer);

                //setelah selesai akan meminta input menu kembali
                printMenu();
                pilihan=inputMenu();
            }
            //jika yang dipilih 2 akan masuk ke menu generate nota, program akan meminta nama, nomor HP, tanggal terima, paket, berat cucian
            else if (pilihan==2){
                System.out.println("================================");
                System.out.print("Masukkan nama Anda: \n");
                String nama=input.nextLine();
                String nomorHP=inputHP();

                //akan memanggil generateId seperti menu 1 
                String idCustomer=generateId(nama, nomorHP);

                //karena tanggal dijamin valid saya tidak memvalidasi
                System.out.println("Masukkan tanggal terima: ");
                String tanggalTerima=input.nextLine();

                //paket yang dipilih juga akan divalidasi
                String paketDipilih=inputPaket();

                //berat juga akan divalidasi
                int berat=validasiBerat();

                //jika berat cucian kurang dari 2 akan dibulatkan menjadi 2kg
                String pembulatan;
                if (berat<2){
                    berat=2;
                    pembulatan="Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg\n";
                }
                else{
                    pembulatan="";
                }

                // akan memanggil generateNota dan mereturn string yang sesuai ketentuan
                // setelah itu mengeprint sesuai ketentuan
                String nota=generateNota(idCustomer, paketDipilih, berat, tanggalTerima);
                System.out.print(pembulatan);
                System.out.println("Nota Laundry");
                System.out.println(nota);

                // setelah selesai maka akan meminta input pilihan menu kembali
                printMenu();
                pilihan=inputMenu();
            }
            else{
                // jika bukan 0/1/2 tetapi integer akan meminta input ulang
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                printMenu();
                pilihan=inputMenu();
            }
        }
        // setelah mendapat input 0 akan keluar dari loop dan program berakhir
        System.out.println("================================\nTerima kasih telah menggunakan NotaGenerator!");

        
    }

    public static int inputMenu(){
        int pilihan=-1;
        boolean valid=false;
        // selama menu yang dipilih belum valid maka akan meminta input sampai memasukkan anggka 0/1/2
        while(!valid){
            try{
                System.out.print("pilihan : ");
                String pilihanString =input.nextLine();
                
                //saya menghandle jika input string kosong dianggap perintah tidak diketahui
                if(pilihanString.isEmpty()){
                    System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                    printMenu();
                    continue;
                }

                pilihan=Integer.parseInt(pilihanString);
                valid=true;
                //jika input bukan berupa integer maka akan meminta input ulang
            }catch(NumberFormatException e){
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                printMenu();
            }
        }
        //mereturn pilihan, jika pilihan bukan 0/1/2 akan kembali ke fungsi ini
        return pilihan;
    }

    //memvalidasi berat (hanya integer)
    public static int validasiBerat(){
        int berat=0;
        boolean valid=false;
        System.out.println("Masukkan berat cucian Anda [Kg]: ");
        while(!valid){
            try{
                berat = input.nextInt();
                input.nextLine();
                //jika berat kurang dari 1 akan meminta input ulang
                if(berat<1){
                    System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                }
                else{
                    valid=true;
                }
            //jika string akan meminta input ulang
            }catch(InputMismatchException e){
                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                input.next(); //mengosongkan isi input buffer
            }
        }
        //mereturn berat yang sudah valid
        return berat;
    }

    //memvalidasi nomor handphone agar hanya integer
    public static String inputHP(){
        System.out.print("Masukkan nomor handphone Anda: \n");
        String nomorHP="";
        boolean valid=false;

        while(!valid){
            try{
                nomorHP =input.nextLine();
                //cek apakah digit dan jika angka pertama 0 tidak mengubahnya menjadi octal (hanya menerima digit)
                if(nomorHP.matches("\\d+")){
                    valid=true;
                }
                //jika didalam angka terdapat string juga meminta input ulang
                else{
                    System.out.println("Nomor hp hanya menerima digit");
                }
            //jika string akan meminta input ulang
            }catch(InputMismatchException e){
                System.out.println("Nomor hp hanya menerima digit");
                input.next(); //mengosongkan isi input buffer
            }
        }
        //mereturn nomor HP yang sudah valid
        return nomorHP;
    }

    //akan memvalidasi paket yang dipilih
    public static String inputPaket(){
        boolean ada=false;
        String paketAsli="";
        String paketDipilih="";
        //selama paket belum valid program akan tetap meminta input
        while(!ada){
            System.out.println("Masukkan paket laundry: ");
            //saya melowercase nya karena di discord disebutkan case insensitive
            paketAsli=input.next();
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
            else if(paketDipilih.equals("?")){
                showPaket();
            }
            //jika tidak ada maka mengeprint paket tidak diketahui
            else{
                System.out.printf("Paket %s tidak diketahui",paketAsli);
                System.out.println("\n[ketik ? untuk mencari tahu jenis paket]");
            }
        }
        //akan mereturn paket asli sesuai yang ditulis oleh pengguna
        return paketAsli;
        
    }

    
    
    //Method untuk menampilkan menu di NotaGenerator.
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }
    
    //Method untuk menampilkan paket.
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    
    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */

    public static String generateId(String nama, String nomorHP){
        //nama yang utuh akan displit dan diambil indeks ke 0 nya saja
        String[] namaLengkap = nama.split(" ");
        String namaAwal = namaLengkap[0].toUpperCase();
        int jumlah=0;

        //string yang ingin dicek dengan format (namaawal-nomorhp) menggunakan checkSum
        String cekString=namaAwal+"-"+nomorHP;
        String checkHasil=checkSum(cekString,jumlah);

        //hasil string yaitu ID dengan format sesuai yang diberikan
        String hasil=namaAwal+"-"+nomorHP+"-"+checkHasil;
        return hasil;
    }

    //meminta parameter string yang akan dicek dan jumlah awal 0(jumlah yang akan ditambahkan pada akhir ID)
    public static String checkSum(String cekString,int jumlah){
        //value dari suatu char dalam string yang akan dicek satu persatu dari kanan
        int valueChar;
        //selama string yang ingin dicek memiliki panjang lebih dari 0 maka akan dijumlahkan terus dan 
        //char yang sudah dicek (indeks paling kanan) akan dihapus.
        //disini saya menggunakan rekursi, jumlah akan ditambah dengan value dari char yang dicek tersebut.
        if (cekString.length()>0){
            //jika char di indeks terakhir adalah int maka akan mengambil value dari integer tersebut
            if(Character.isDigit(cekString.charAt(cekString.length()-1))){
                valueChar=Character.getNumericValue(cekString.charAt(cekString.length()-1));
                return checkSum(cekString.substring(0, cekString.length()-1), jumlah+valueChar);
            }
            //jika char di indeks terakhir adalah suatu huruf maka akan mengambil value nya sesuai ketentuan (A=1...Z=26)
            else if(Character.isAlphabetic(cekString.charAt(cekString.length()-1))){
                valueChar=(cekString.charAt(cekString.length()-1)-'A')+1;
                return checkSum(cekString.substring(0, cekString.length()-1), jumlah+valueChar);
            }
            //jika bukan integer dan alphabet (seperti symbol atau lainnya) value dari char tersebut akan bernilai 7
            else{
                valueChar=7;
                return checkSum(cekString.substring(0, cekString.length()-1), jumlah+valueChar);
            }
        }

        //jika panjang string yang ingin dicek sudah 0 maka jumlah yang sudah didapat akan diubah ke string
        else{
            String hasil= Integer.toString(jumlah);

            //jika panjang checksum kurang dari 2 maka akan menambahkan 0 pada awal checksum tersebut
            if(hasil.length()<2){
                return '0'+hasil;
            }

            //selain itu akan mengambil dari indeks kedua terakhir
            else{
                return hasil.substring(hasil.length()-2);
            }
        }
    }

    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */

    public static String generateNota(String id, String paketDipilih, int berat, String tanggalTerima){
        int harga,hari;
        String tanggalSelesai="";
        String paket=paketDipilih.toLowerCase();

        //menentukan lama hari dan harga sesuai paket yang dipilih
        if (paket.equals("express")){
            hari=1;
            harga=12000;
        }
        else if (paket.equals("fast")){
            hari=2;
            harga=10000;
        }
        else{
            hari=3;
            harga=7000;
        }

        //memformat tanggal terima dan tanggal selesai
        tanggalTerima=tanggalFormat(tanggalTerima, 0);

        //tanggal terima dijumlahkan dengan lama hari sesuai paket yang dipilih
        tanggalSelesai=tanggalFormat(tanggalTerima,hari);

        //menghitung harga total
        int hargaTotal=berat*harga;
        
        //mmereturn string sesuai dengan testcase yang ada
        String solution = "ID    : "+id+"\n" +
                "Paket : "+paketDipilih+"\n" +
                "Harga :\n" +
                berat+" kg x "+harga+" = "+hargaTotal+"\n" +
                "Tanggal Terima  : "+tanggalTerima+"\n" +
                "Tanggal Selesai : "+tanggalSelesai;

        return solution;
    }

    //tanggal yang diterima akan diformat, parameter hari untuk ditambahkan
    public static String tanggalFormat(String tanggalTerima, int hari){
        //format tanggal yang digunakan
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        //mengubah menjadi localdate agar dapat ditambahkan
        LocalDate date = LocalDate.parse(tanggalTerima, formatTanggal);

        //tanggal terima akan ditambahkan dengan hari sesuai paket yang dipilih
        date=date.plusDays(hari);

        //akan mereturn tanggal selesai sesuai dengan format
        String tanggalSelesai = date.format(formatTanggal);
        return tanggalSelesai;
    }
}
