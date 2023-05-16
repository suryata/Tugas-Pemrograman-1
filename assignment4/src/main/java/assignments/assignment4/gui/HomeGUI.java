package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
        Color pastel= new Color(255, 248, 214);
        mainPanel.setBackground(pastel);

    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // Set up title label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(28,0, 28, 0);
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 21);
        Font font2 = new Font("Arial", Font.BOLD | Font.ITALIC, 14);
        Color hijau = new Color(57, 81, 68);

        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        titleLabel.setFont(font);
        titleLabel.setForeground(hijau);

        dateLabel = new JLabel("Hari ini: "+NotaManager.fmt.format(NotaManager.cal.getTime()));
        dateLabel.setForeground(hijau);
        dateLabel.setFont(font2);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(78, 108, 80));
        loginButton.setForeground(Color.WHITE);
        registerButton = new JButton("Register");
        registerButton.setBackground(hijau);
        registerButton.setForeground(Color.WHITE);
        toNextDayButton = new JButton("Next Day");
        toNextDayButton.setBackground(new Color(78, 108, 80));
        toNextDayButton.setForeground(Color.WHITE);

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
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToLogin();
        }});

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToRegister();
        }});

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
        toNextDay();
        NotaManager.cal.add(Calendar.DATE,1);
        dateLabel.setText("Hari ini: "+NotaManager.fmt.format(NotaManager.cal.getTime()));
    }
}
