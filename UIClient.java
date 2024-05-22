import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Label;
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
    private String position = "";
    private String digit = "";
    private String secertNumber = "";

    private JPanel panel;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JButton Submitbutton;
    private JTextField textField;
    private JLabel nameLabel;
    private JButton Guessbutton;

    private JLabel resultGuess;
    private JLabel roundRemaining;

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
        System.out.println("r: " + remainRound);
        // ตัวแปรทั้งหมดที่ใช้
        frame = new JFrame("Guess the Number Game");
        frame.setSize(600, 400);
        frame.setMaximumSize(new Dimension(600, 400));

        ipLabel = new JLabel("ip: ");
        portLabel = new JLabel("Port: ");
        textField = new JTextField(10);
        nameLabel = new JLabel("Name: ");
        Submitbutton = new JButton("start");
        Guessbutton = new JButton("guess!");

        resultGuess = new JLabel("");
        roundRemaining = new JLabel("");

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
        // JPanel southPanel = new JPanel(new GridLayout(2,2));

        panel = new JPanel();
        panel.add(ipLabel);
        panel.add(portLabel);
        panel.add(nameLabel);
        panel.add(textField);
        panel.add(Submitbutton);

        panel.add(roundRemaining);
        panel.add(resultGuess);

        Submitbutton.addActionListener(this);
        Guessbutton.addActionListener(this);

        
        frame.add(panel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridLayout(2,2));
        
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
            panel.remove(Submitbutton);
            panel.add(Guessbutton);
            roundRemaining.setText("remaining round: " + (remainRound));

            // เด้ง pop up เพื่อรอให้อีกฝ่ายเข้ามาพร้อมกัน
            // JOptionPane.showMessageDialog(null,
            // "Hello, " + name + " wait for another player on" + " ip: " + ip + " port:" +
            // port);
        }

        if (e.getActionCommand().equals("guess!")) {
            roundRemaining.setText("remaining round: " + (remainRound - 1));

            this.guessNumber = textField.getText();
            try {
                Thread.sleep(500);
            } catch (InterruptedException err) {
                Thread.currentThread().interrupt();
            }

            if (remainRound <= 0) {
                resultGuess.setText("you lose the secrer number is " + secertNumber);
                panel.remove(Guessbutton);
                panel.remove(textField);

            } else {
                if (position.isEmpty() || digit.isEmpty()) {
                    resultGuess.setText("wrong input. try again");
                } else {
                    resultGuess.setText("correctly position: " + position + "\ncorrectly digit: " + digit);
                }

                if (position.equals("5") && digit.equals("5")) {
                    resultGuess.setText("Win you found the secret.");

                }
            }

        }
    }

    public String getGuessNumber() {
        return this.guessNumber;
    }

    public void resetGuessNumber() {
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
        southPanel.add(new Label());
        southPanel.add(ipLabel);
        southPanel.add(portLabel);
        southPanel.revalidate();
        southPanel.repaint();
    }

    public void setRemainRound(int r) {
        this.remainRound = r;
    }

    public void setPosition(String p) {
        this.position = p;
    }

    public void setDigit(String d) {
        this.digit = d;
    }

    public void setSecretNumber(String secertNumber) {
        this.secertNumber = secertNumber;
    }

}
