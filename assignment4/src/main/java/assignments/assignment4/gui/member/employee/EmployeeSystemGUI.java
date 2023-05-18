package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//inisiasi EmployeeSystemGUI
public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        Font font3 = new Font("Garamond",Font.PLAIN,15);
        Color hijau = new Color(57, 81, 68);
        JButton nyuciButton = new JButton("It's nyuci time");
        JButton displayNota = new JButton("Display List Nota");
        nyuciButton.setForeground(hijau);
        nyuciButton.setFont(font3);
        displayNota.setForeground(hijau);
        displayNota.setFont(font3);
        return new JButton[]{
            nyuciButton, displayNota
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        String notaKosong = "Belum ada nota";
        StringBuilder stringBuilder = new StringBuilder();

        //membuat stringbuilder untuk menampilkan semua nota yang ada
        for (Nota nota : NotaManager.notaList) {
            stringBuilder.append(nota.getNotaStatus()).append("\n");
        }
        String result = stringBuilder.toString();

        JTextArea textArea = new JTextArea(10,15);
        textArea.setEditable(false);

        //jika nota belum ada maka menampilkan notaKosong jika ada maka menampilkan stringbuilder
        if(result.isEmpty()){
            JOptionPane.showMessageDialog(this,notaKosong,"List Nota",JOptionPane.WARNING_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,result,"List Nota",JOptionPane.INFORMATION_MESSAGE);
        }


    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        String pencuci = "Stand back! "+loggedInMember.getNama()+" beginning to nyuci!\n";
        StringBuilder stringBuilder = new StringBuilder();
        String notaKosong = "Nothing to cuci here";

        //membuat stringbuilder yang berisi semua nota 
        for (Nota nota : NotaManager.notaList) {
            stringBuilder.append(nota.kerjakan()).append("\n");
        }
        String result = stringBuilder.toString();


        JTextArea textArea = new JTextArea(10,15);
        textArea.setEditable(false);
        textArea.setText(pencuci);
        JOptionPane.showMessageDialog(this,pencuci,"Nyuci Time",JOptionPane.INFORMATION_MESSAGE);

        //jika nota kosong maka akan menampilkan nothingtocuci, jika ada maka menampilkan stringbuilder 
        if(result.isEmpty()){
            JOptionPane.showMessageDialog(this,notaKosong,"Nyuci Results",JOptionPane.WARNING_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this,result,"Nyuci Results",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
