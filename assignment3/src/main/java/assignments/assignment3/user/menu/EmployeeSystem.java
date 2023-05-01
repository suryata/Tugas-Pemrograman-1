package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.notaList;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        memberList = new Member[]{
                new Employee("Dek Depe", "akuDDP"),
                new Employee("Depram", "musiktualembut"),
                new Employee("Lita Duo", "gitCommitPush"),
                new Employee("Ivan Hoshimachi", "SuamiSahSuisei"),
        };
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        while (!logout) {
            switch (choice) {
                case 1 -> nyuci();
                case 2 -> nota();
                case 3 -> logout = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
            choice = in.nextInt();
            in.nextLine();
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }

    public void nyuci(){
        System.out.printf("Stand back! %s beginning to nyuci!",loginMember.getNama());
        for(Nota nota : notaList){
            nota.kerjakan();
        }
    }

    public void nota(){
        for(Nota nota : notaList){
            System.out.println("Nota "+nota.getId()+": "+nota.getNotaStatus());
        }
    }
}