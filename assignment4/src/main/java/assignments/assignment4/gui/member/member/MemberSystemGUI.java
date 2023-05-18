package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//Panel jika yang dilogin adalah member
public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        Font font3 = new Font("Garamond",Font.PLAIN,15);
        Color hijau = new Color(57, 81, 68);
        JButton laundryButton = new JButton("Saya ingin laundry");
        JButton showNota = new JButton("Lihat detail nota saya");
        laundryButton.setForeground(hijau);
        laundryButton.setFont(font3);
        showNota.setForeground(hijau);
        showNota.setFont(font3);

        return new JButton[]{
            laundryButton,showNota
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
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Nota nota : loggedInMember.getNotaList()) {
            stringBuilder.append(nota.toString()).append("\n");
        }
        String result = stringBuilder.toString();
        String notaKosong = "Belum pernah laundry di CuciCuci, hiks :'(";

        JTextArea textArea = new JTextArea(19,30);
        textArea.setEditable(false);

        //menampilkan nota yang dimiliki oleh member yang sedang login jika kosng akan menampilkan nota kosong
        if(result.isEmpty()){
            textArea.setText(notaKosong);
            JOptionPane.showMessageDialog(MainFrame.getInstance(),new JScrollPane(textArea),"Detail Nota",JOptionPane.INFORMATION_MESSAGE);
        }else{
            textArea.setText(result);
            JOptionPane.showMessageDialog(MainFrame.getInstance(),new JScrollPane(textArea),"Detail Nota",JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        MainFrame.getInstance().navigateTo(CreateNotaGUI.KEY);
    }

}
