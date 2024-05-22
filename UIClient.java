import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.swing.*;

public class UIClient extends Thread implements ActionListener {
    private int port;
    private String ip;
    private String guessNumber = "";
    private int remainRound;

    private JPanel inputJPanel;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JButton Submitbutton;
    private JTextField textField;
    private JLabel nameLabel;
    private JButton Guessbutton;
    
    
    private JLabel postDigitCorrecting;

    private JFrame frame;

    // private JLabel numberLabel;
    // private JLabel historyLabel;
    // private JTextField GuessNumbertextField;

    public UIClient() {
        this(5555, 5);
    }
    public UIClient(int port, int roundForGuess) {
        this.port = port;
        this.remainRound = roundForGuess;
        initializeUI();
    }


    private void initializeUI() {

        // ตัวแปรทั้งหมดที่ใช้
        frame = new JFrame("Guess the Number Game");
        frame.setSize(600, 400);

        ipLabel = new JLabel("ip: ");
        portLabel = new JLabel("Port: ");
        textField = new JTextField(10);
        nameLabel = new JLabel("Name: ");
        Submitbutton = new JButton("start");
        Guessbutton = new JButton("guess!");

        
        postDigitCorrecting = new JLabel("");

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            this.ip = inetAddress.getHostAddress();
            ipLabel.setText("IP: " + ip);
            portLabel.setText("port: " + this.port);

        } catch (UnknownHostException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        // start page
        inputJPanel = new JPanel();
        inputJPanel.add(nameLabel);
        inputJPanel.add(textField);
        inputJPanel.add(ipLabel);
        inputJPanel.add(portLabel);
        inputJPanel.add(Submitbutton);

        
        inputJPanel.add(postDigitCorrecting);



        Submitbutton.addActionListener(this);
        Guessbutton.addActionListener(this);

        frame.add(inputJPanel, BorderLayout.CENTER);
        

        JPanel southPanel = new JPanel();
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // หลังจากการกด submit หลังจากกรอกข้อมูลในหน้าแรก
        if (e.getActionCommand().equals("start")) {
            southPanel();
            textField.setText(null);
            inputJPanel.remove(Submitbutton);
            inputJPanel.add(Guessbutton);
            // เด้ง pop up เพื่อรอให้อีกฝ่ายเข้ามาพร้อมกัน
            // JOptionPane.showMessageDialog(null,
            // "Hello, " + name + " wait for another player on" + " ip: " + ip + " port:" +
            // port);
        }

        if (e.getActionCommand().equals("guess!")) {
            this.guessNumber = textField.getText();
            if (remainRound <= 0) {
                inputJPanel.remove(Guessbutton);
                
            }
            postDigitCorrecting.setText("cr: " + remainRound);
        }
    }

    public String getGuessNumber(){
        return this.guessNumber;
    }
    public void resetGuessNumber(){
        this.guessNumber = "";
    }
    // code ที่เธอเขียน
    public static void main(String[] args) {
        new UIClient();
    }

    private void southPanel() {
        JPanel southPanel = (JPanel) frame.getContentPane().getComponent(1);
        // แสดงชื่อที่ได้จากการกรอก เอาไว้อยู่ด้านล่างของจอ
        String name = textField.getText();
        nameLabel.setText("Name: " + name);
        southPanel.add(nameLabel);
        southPanel.add(ipLabel);
        southPanel.add(portLabel);
        southPanel.revalidate();
        southPanel.repaint();
    }

    public void setRemainRound(int r){
        this.remainRound = r;
    }

}
