package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Login panel
public class LoginGUI extends JPanel {
    //berikut atribut yang dimiliki
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    //menginisiasi loginGUI menggunakan borderlayout
    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(255, 248, 214));
        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        //saya menggunakan gridbagconstraint
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,0, 10, 0);
        Font font3 = new Font("Garamond",Font.PLAIN,15);
        Color hijau = new Color(57, 81, 68);

        idLabel = new JLabel("Masukkan ID Anda:");
        idLabel.setFont(font3);
        idLabel.setForeground(hijau);

        idTextField = new JTextField();

        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordLabel.setFont(font3);
        passwordLabel.setForeground(hijau);

        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');

        loginButton = new JButton("Login");
        loginButton.setFont(font3);
        loginButton.setForeground(hijau);

        backButton = new JButton("Kembali");
        backButton.setFont(font3);
        backButton.setForeground(hijau);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(idLabel,gbc);

        gbc.gridy=1;
        mainPanel.add(idTextField,gbc);

        gbc.gridy=2;
        mainPanel.add(passwordLabel,gbc);

        gbc.gridy=3;
        mainPanel.add(passwordField,gbc);

        gbc.gridy=4;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(loginButton,gbc);

        gbc.gridy=5;
        mainPanel.add(backButton,gbc);

        //actionlistener login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        //action listener backbutton
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
        idTextField.setText("");
        passwordField.setText("");
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        String pass = new String(passwordField.getPassword());
        String id = idTextField.getText();

        //jika idnya tidak ada di sistem maka invalid dan mengset fieldnya menjadi kosong
        if(loginManager.getSystem(id)==null){
            JOptionPane.showMessageDialog(MainFrame.getInstance(),"ID atau Password Invalid.", "Invalid ID or Password", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            idTextField.setText("");
            return;
        }
        else {
            MainFrame.getInstance().login(id, pass);
            passwordField.setText("");
            idTextField.setText("");
        }
    }
       

 }

