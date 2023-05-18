package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import static assignments.assignment3.nota.NotaManager.toNextDay;

//HomeGUI merupakan panel awal saat program dijalankan
public class HomeGUI extends JPanel {
    //berikut atribut yang dimiliki
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    //menginisiasi panel homeGUI
    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBackground(new Color(255, 248, 214));

    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // Set up atribut dari homeGUI menggunakan gridbagconstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(28,0, 28, 0);
        Font font = new Font("Bodoni", Font.BOLD | Font.ITALIC, 21);
        Font font2 = new Font("Bodoni", Font.BOLD | Font.ITALIC, 14);
        Font font3 = new Font("Garamond",Font.PLAIN,15);
        Color hijau = new Color(57, 81, 68);

        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        titleLabel.setFont(font);
        titleLabel.setForeground(hijau);

        dateLabel = new JLabel("Hari ini: "+NotaManager.fmt.format(NotaManager.cal.getTime()));
        dateLabel.setForeground(hijau);
        dateLabel.setFont(font2);

        loginButton = new JButton("Login");
        loginButton.setForeground(hijau);
        loginButton.setFont(font3);
        registerButton = new JButton("Register");
        registerButton.setForeground(hijau);
        registerButton.setFont(font3);
        toNextDayButton = new JButton("Next Day");
        toNextDayButton.setForeground(hijau);
        toNextDayButton.setFont(font3);

        gbc.gridx = 3;
        gbc.gridy = 0;
        mainPanel.add(titleLabel,gbc);

        gbc.gridy = 3;
        mainPanel.add(loginButton,gbc);
    
        gbc.gridy = 6;
        mainPanel.add(registerButton,gbc);
        
        gbc.gridy = 9;
        mainPanel.add(toNextDayButton,gbc);

        gbc.gridy = 12;
        mainPanel.add(dateLabel,gbc);
        
        //actionlistener untuk login button akan memanggil method handleToLogin
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToLogin();
        }});

        //actionlistener untuk register button akan memanggil method handleToRegister
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToRegister();
        }});

        //actionlistener untuk nextday akan memanggil method handleNextDay
        toNextDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextDay();
        }});
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        // Buat panel login
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        //memanggil toNextDay yang ada pada notamanager
        toNextDay();
        NotaManager.cal.add(Calendar.DATE,1);
        dateLabel.setText("Hari ini: "+NotaManager.fmt.format(NotaManager.cal.getTime()));
        //akan mengshow massage
        JOptionPane.showMessageDialog(this,"Kamu tidur hari ini...zzz...", "This is Prince Paul's Bubble Party's ability!", JOptionPane.INFORMATION_MESSAGE);
    }
}
