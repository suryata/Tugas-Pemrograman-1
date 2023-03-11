package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
// import java.util.InputMismatchException;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList=new ArrayList<Nota>();
    private static ArrayList<Member> memberList=new ArrayList<Member>();

    public static void main(String[] args) {
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

    private static void handleGenerateUser() {
        //handle generate user
        System.out.print("Masukkan nama Anda: \n");
        String nama=inputNama();
        String nomorHP=inputHP();
        String tempId=generateId(nama, nomorHP);
        // akan mengconstruct ID customer
        
        boolean memberAda=false;
        if (memberList.size()==0){
            Member member = new Member(nama, nomorHP);
            memberList.add(member);
        }
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
            if(memberAda==false){
                Member member = new Member(nama, nomorHP);
                memberList.add(member);
            } else{
                
            }
        }
    }

    private static void handleGenerateNota() {
        //handle ambil cucian
        System.out.println("Masukan ID member: ");
        String idMember = input.nextLine();
        boolean memberAda=false;
        Member memberCuci = null;
        String tanggal = fmt.format(cal.getTime());

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

        if(memberAda==true){
            String paket = inputPaket();
            int berat = validasiBerat();
            Nota nota = new Nota(memberCuci, paket, berat, tanggal);
            notaList.add(nota);
            memberCuci.addOrder();
        }else{
            System.out.printf("Member dengan ID %s tidak ditemukan!\n",idMember);
        }
    }

    private static void handleListNota() {
        //handle list semua nota pada sistem
        System.out.println("Terdapat "+notaList.size()+ " nota dalam sistem.");
        for (Nota nota : notaList){
            System.out.printf("- [%d] Status            : %s \n",nota.getID(),nota.cekReady(nota.cekStatus(nota.getsisaHariPengerjaan())));
        }
    }

    private static void handleListUser() {
        //handle list semua user pada sistem
        System.out.println("Terdapat "+memberList.size()+ " member dalam sistem.");
        for (Member element : memberList){
            System.out.println("- "+ element.getid() +" : "+ element.getNama());
        }
    }

    private static void handleAmbilCucian() {
        //handle ambil cucian
        System.out.println("Masukan ID nota yang akan diambil: ");
        int idNota = inputInt();
        Nota nota =cariNota(idNota);
        if(nota!=null){
            if(nota.cekStatus(nota.getsisaHariPengerjaan())==true){
                System.out.printf("Nota dengan ID %d berhasil diambil!\n",nota.getID());
                notaList.remove(nota);     
            }else{
                System.out.printf("Nota dengan ID %d gagal diambil!\n",nota.getID());
            }
        }
    }

    private static void handleNextDay() {
        //handle ganti hari
        System.out.println("Dek Depe tidur hari ini... zzz...");
        for(Nota nota : notaList){
            nota.nextDay();
            if(nota.cekStatus(nota.getsisaHariPengerjaan()) == true){
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n",nota.getID());
            }
        }
        System.out.println("Selamat pagi dunia!\n" +
                           "Dek Depe: It's CuciCuci Time.");
        cal.add(Calendar.DATE,1);
    }

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
        if (notaAda==false){
            System.out.printf("Nota dengan ID %d tidak ditemukan!",idNota);
        }
        return null;
    }


}
