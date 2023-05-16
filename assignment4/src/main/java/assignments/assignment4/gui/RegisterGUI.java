package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        // Set up komponen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,0, 10, 0);

        nameLabel = new JLabel("Masukkan nama Anda:");
        nameTextField = new JTextField();
        phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
        phoneTextField = new JTextField();
        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        registerButton = new JButton("Register");
        backButton = new JButton("Kembali");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(nameLabel,gbc);

      
        gbc.gridy = 1;
        mainPanel.add(nameTextField,gbc);

        
        gbc.gridy = 2;
        mainPanel.add(phoneLabel,gbc);

        
        gbc.gridy = 3;
        mainPanel.add(phoneTextField,gbc);

        
        gbc.gridy = 4;
        mainPanel.add(passwordLabel,gbc);

        
        gbc.gridy = 5;
        mainPanel.add(passwordField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(registerButton,gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(backButton,gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
        }});

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
        }});
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // TODO
        String nama = nameTextField.getText();
        String phone = phoneTextField.getText();
        String password = new String(passwordField.getPassword());
        int noHP;

        if (nama.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(),"Data tidak boleh kosong!","Empty Field",JOptionPane.WARNING_MESSAGE );
            return;
        }

        try{
            noHP = Integer.parseInt(phone);
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(MainFrame.getInstance(),"Nomor handphone harus berisi angka!", "Invalid Phone Number", JOptionPane.WARNING_MESSAGE);
            phoneTextField.setText("");
            return;
        }

        String noHpString = Integer.toString(noHP);
        Member registeredMember = loginManager.register(nama, noHpString, password);
        if(registeredMember == null){
            JOptionPane.showMessageDialog(MainFrame.getInstance(),"User dengan nama "+nama+" dan nomor hp "+noHpString+" sudah ada!", "Registration Failed", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(MainFrame.getInstance(),"Berhasil membuat user dengan ID "+registeredMember.getId()+"!","Registration Succesful",JOptionPane.INFORMATION_MESSAGE);
    }
}
