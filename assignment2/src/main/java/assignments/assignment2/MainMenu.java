package assignments.assignment2;
//library dan package yang dibutuhkan
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    //memiliki scanner, date formatter, calendar, arraylist member, arraylist nota
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList=new ArrayList<Nota>();
    private static ArrayList<Member> memberList=new ArrayList<Member>();

    public static void main(String[] args) {
        //program akan terus berjalan hingga diberi input 0
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    //case 1
    private static void handleGenerateUser() {
        //handle generate user
        System.out.print("Masukkan nama Anda: \n");
        String nama=inputNama();
        String nomorHP=inputHP();
        String tempId=generateId(nama, nomorHP);
        // akan mengconstruct ID customer
        
        boolean memberAda=false;
        //jika size arraylist member 0 akan langsung menambahkan ke arraylist
        if (memberList.size()==0){
            Member member = new Member(nama, nomorHP);
            memberList.add(member);
        }
        //jika tidak akan mengecek di array list id tersebut sudah ada atau belum
        else{
            for (Member element : memberList){
                if(element.getid().equals(tempId)){
                    System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n",element.getNama(),element.getnoHp());
                    memberAda=true;
                    break;
                }
                else{
                    memberAda=false;
                }
            }
            //jika tidak ada yang sama maka akan membuat object member baru dan memasukkannya kedalam list member
            if(memberAda==false){
                Member member = new Member(nama, nomorHP);
                memberList.add(member);
            }
        }
    }

    //case 2
    private static void handleGenerateNota() {
        //handle ambil cucian
        System.out.println("Masukan ID member: ");
        String idMember = input.nextLine();
        boolean memberAda=false;
        Member memberCuci = null;
        String tanggal = fmt.format(cal.getTime());

        //mengecek apakah id yang dimasukkan ada didalam list member
        for (Member element : memberList){
            if(element.getid().equals(idMember)){
                memberAda = true;
                memberCuci = element;
                break;
            }
            else{
                continue;
            }
            
        }

        /*  jima member ada maka akan melanjutkan meminta input paket, berat, dan membuat nota baru kemudian memasukkannya
            ke arraylist nota*/
        if(memberAda==true){
            String paket = inputPaket();
            int berat = validasiBerat();
            Nota nota = new Nota(memberCuci, paket, berat, tanggal);
            notaList.add(nota);
            memberCuci.addOrder();
        }else{
            System.out.printf("Member dengan ID %s tidak ditemukan!\n",idMember);   //jika tidak akan mengeprint member tidak ditemukan
        }
    }

    //case 3
    private static void handleListNota() {
        // handle list semua nota pada sistem
        // mengeprint banyak nota yang ada
        System.out.println("Terdapat "+notaList.size()+ " nota dalam sistem.");
        //menggunakan loop untuk mengeprint semua id nota beserta status nota
        for (Nota nota : notaList){
            System.out.printf("- [%d] Status            : %s \n",nota.getID(),nota.cekReady(nota.cekStatus(nota.getsisaHariPengerjaan())));
        }
    }

    //case 4
    private static void handleListUser() {
        //handle list semua user pada sistem
        //mengeprint banyak users dan ada beserta id dan nama lengkapnya
        System.out.println("Terdapat "+memberList.size()+ " member dalam sistem.");
        for (Member element : memberList){
            System.out.println("- "+ element.getid() +" : "+ element.getNama());
        }
    }

    //case 5
    private static void handleAmbilCucian() {
        //handle ambil cucian
        System.out.println("Masukan ID nota yang akan diambil: ");
        int idNota = inputInt();                        //jika string akan mengeprint id nota harus angka
        Nota nota =cariNota(idNota);                    //id nota yang masuk sudah integer
        //jika nota ada maka akan mengeprint 
        if(nota!=null){
            //jika status ready akan mengeprint ready
            if(nota.cekStatus(nota.getsisaHariPengerjaan())==true){
                System.out.printf("Nota dengan ID %d berhasil diambil!\n",nota.getID());
                notaList.remove(nota);     
            }
            //jika belum ready maka gagal mengambil cucian
            else{
                System.out.printf("Nota dengan ID %d gagal diambil!\n",nota.getID());
            }
        }
    }

    //case 6
    private static void handleNextDay() {
        //handle ganti hari
        System.out.println("Dek Depe tidur hari ini... zzz...");
        //mengeprint semua nota yang sudah ready jika ada
        for(Nota nota : notaList){
            //sisa hari pengerjaan di setiap nota yang ada akan berkurang 1
            nota.nextDay();
            if(nota.cekStatus(nota.getsisaHariPengerjaan()) == true){
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n",nota.getID());
            }
        }
        System.out.println("Selamat pagi dunia!\n" +
                           "Dek Depe: It's CuciCuci Time.");
        //hari akan bertambah 1                   
        cal.add(Calendar.DATE,1);
    }

    //mengeprint menu
    private static void printMenu() {
        System.out.println("\n\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    //jika id nota tidak berbentuk angka
    public static int inputInt() {
        int num = 0;
        boolean validInput = false;
        while (!validInput) {
            String angka = input.nextLine();
            try {
                num = Integer.parseInt(angka);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("ID nota berbentuk angka!");
            }
        }
        return num;
    }

    //mencari nota berdasarkan id nota 
    public static Nota cariNota(int idNota){
        boolean notaAda = false;

        for (Nota nota : notaList){
            if(nota.getID()==(idNota)){
                notaAda = true;
                return nota;
            }
            else{
                notaAda = false;
            }
        }
        //jika tidak ada maka nota tidak ditemukan
        if (notaAda==false){
            System.out.printf("Nota dengan ID %d tidak ditemukan!",idNota);
        }
        return null;
    }
}
