package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//jika pada MemberSystemGUI menekan tombol create nota
public class CreateNotaGUI extends JPanel {
    //berikut atribut yang dimiliki
    public static final String KEY = "CREATE_NOTA";
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;
    public String paket;

    //menginisiasi CreateNotaGUI menggunakan gridbaglayout
    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        super(new GridBagLayout());
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;
        
        // Set up main panel, Feel free to make any changes
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(new Color(255, 248, 214));
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {

        //inisiasi atribut yang ada di panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5, 5, 5);

        Font font3 = new Font("Garamond",Font.PLAIN,15);
        Color hijau = new Color(57, 81, 68);

        paketLabel = new JLabel("Paket Laundry:");
        paketComboBox = new JComboBox<String>(new String[]{"Express", "Fast", "Reguler"});
        showPaketButton = new JButton("Show Paket");
        beratLabel = new JLabel("Berat Cucian (Kg):");
        beratTextField = new JTextField();
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/kg)");
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/ 4kg pertama, kemudian 500/ kg)");
        createNotaButton = new JButton("Buat Nota");
        backButton = new JButton("Kembali");

        //set penampilannya

        paketLabel.setFont(font3);
        paketLabel.setForeground(hijau);
        paketComboBox.setFont(font3);
        paketComboBox.setForeground(hijau);
        showPaketButton.setFont(font3);
        showPaketButton.setForeground(hijau);
        beratLabel.setFont(font3);
        beratLabel.setForeground(hijau);
        beratTextField.setFont(font3);
        beratTextField.setForeground(hijau);
        setrikaCheckBox.setFont(new Font("Garamond",Font.PLAIN,12));
        setrikaCheckBox.setForeground(hijau);
        antarCheckBox.setFont(new Font("Garamond",Font.PLAIN,12));
        antarCheckBox.setForeground(hijau);
        createNotaButton.setFont(font3);
        createNotaButton.setForeground(hijau);
        backButton.setFont(font3);
        backButton.setForeground(hijau);

        //setLayout

        gbc.gridx=0;
        gbc.gridy=0;
        // gbc.weightx = 11;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        add(paketLabel,gbc);

        gbc.gridx=4;
        add(paketComboBox,gbc);

        gbc.gridx=5;
        add(showPaketButton,gbc);

        gbc.gridx=0;
        gbc.gridy=2;
        add(beratLabel,gbc);

        gbc.gridx=4;
        gbc.weightx=1;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        add(beratTextField,gbc);

        gbc.gridx=0;
        gbc.gridy=4;
        add(setrikaCheckBox,gbc);

        gbc.gridx=0;
        gbc.gridy=6;
        add(antarCheckBox,gbc);

        gbc.gridx=0;
        gbc.gridy=8;
        gbc.gridwidth=6;
        add(createNotaButton,gbc);

        gbc.gridy=10;
        add(backButton,gbc);

        //menambahkan event handler sesuai button tersebut

        showPaketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPaket();
            }
        });

        createNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNota();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });

        paketComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paket = (String) paketComboBox.getSelectedItem();
            }
        });

    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        // Mengambil input user
        Member member = memberSystemGUI.getLoggedInMember();
        int berat=0;
        String beratString=beratTextField.getText(); 
        boolean valid=false;
        paket = (String) paketComboBox.getSelectedItem();

        //mencoba validasi berat
        try{
            berat=Integer.parseInt(beratString);
            if(berat<1){
                JOptionPane.showMessageDialog(this,"Berat tidak boleh negatif!", "Info", JOptionPane.ERROR_MESSAGE);
                resetPanel();
            }else{
                valid=true;
                berat=Integer.parseInt(beratString);
            }
        //jika string atau float akan meminta input ulang
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Berat harus dalam bentuk Angka!", "Error", JOptionPane.ERROR_MESSAGE);
            resetPanel();
        }
        
        //jika valid maka nota bisa dibuat
        if(valid){
            if(berat<2){
                berat=2;
                JOptionPane.showMessageDialog(this,"Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg\n", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            // Mengambil input customer
            boolean setrika = setrikaCheckBox.isSelected();
            boolean antar = antarCheckBox.isSelected();
            String tanggal = fmt.format(cal.getTime());
            Nota nota = new Nota(member, berat, paket, tanggal);
            if(setrika){
                nota.addService(new SetrikaService());
            }
            if(antar){
                nota.addService(new AntarService());
            }
            NotaManager.addNota(nota);
            member.addNota(nota);
            JOptionPane.showMessageDialog(this, "Nota berhasil dibuat.", "Success", JOptionPane.INFORMATION_MESSAGE);
            resetPanel();
        }
    }

    // saya membuat method reset panel untuk mengosongkan semua field yang ada
    public void resetPanel(){
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
        paketComboBox.setSelectedIndex(0);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
        resetPanel();
    }
}
