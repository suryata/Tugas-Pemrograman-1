package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//registerGUI merupakan panel yang akan ditambahkan pada mainFrame jika button register ditekan
public class RegisterGUI extends JPanel {
    //berikut atribut yang dimiliki
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

    //inisiai register gui dengan borderlayout
    public RegisterGUI(LoginManager loginManager) {
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
        // Set up komponen yang terdapat pada registerGUI
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,0, 10, 0);
        
        Font font3 = new Font("Garamond",Font.PLAIN,15);
        Color hijau = new Color(57, 81, 68);
        
        nameLabel = new JLabel("Masukkan nama Anda:");
        nameTextField = new JTextField();
        nameLabel.setFont(font3);
        nameLabel.setForeground(hijau);

        phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
        phoneTextField = new JTextField();
        phoneLabel.setFont(font3);
        phoneLabel.setForeground(hijau);

        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        passwordLabel.setFont(font3);
        passwordLabel.setForeground(hijau);

        registerButton = new JButton("Register");
        registerButton.setFont(font3);
        registerButton.setForeground(hijau);

        backButton = new JButton("Kembali");
        backButton.setFont(font3);
        backButton.setForeground(hijau);


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
        //semua field akan dikosongkan
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        String nama = nameTextField.getText();
        String phone = phoneTextField.getText();
        String password = new String(passwordField.getPassword());

        //jika ada field yang kosong akan menampilkan warning message
        if (nama.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(),"Data tidak boleh kosong!","Empty Field",JOptionPane.WARNING_MESSAGE );
            return;
        }

        //jika pada nomor telepon terdapat bukan angka menampilkan warning message
        try{
            Integer.parseInt(phone);
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(MainFrame.getInstance(),"Nomor handphone harus berisi angka!", "Invalid Phone Number", JOptionPane.WARNING_MESSAGE);
            phoneTextField.setText("");
            return;
        }

        //akan membuat member jika semuanya sesuai dan menggunakan register yang ada pada loginManager dan menambahkannya ke membersystem
        Member registeredMember = loginManager.register(nama, phone, password);

        //jika member sudah ada maka akan menampilkan warning message (registered membernya null)
        if(registeredMember == null){
            JOptionPane.showMessageDialog(MainFrame.getInstance(),"User dengan nama "+nama+" dan nomor hp "+phone+" sudah ada!", "Registration Failed", JOptionPane.WARNING_MESSAGE);
            handleBack();
            return;
        }

        //jika berhasil akan kembali ke home panel
        JOptionPane.showMessageDialog(MainFrame.getInstance(),"Berhasil membuat user dengan ID "+registeredMember.getId()+"!","Registration Succesful",JOptionPane.INFORMATION_MESSAGE);
        handleBack();
    }
}
