import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;

public class test extends Thread implements ActionListener {
    private int port;
    private String ip;
    private String guessNumber = "";
    private int currentRound;

    private JPanel inputJPanel;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JButton Submitbutton;
    private JTextField textField;
    private JLabel nameLabel;
    private JButton Guessbutton;

    private JPanel resultJPanel;
    private JLabel postDigitCorrecting;

    private JFrame frame;

    public test(int port, int roundForGuess) {
        this.port = port;
        this.currentRound = roundForGuess;
        initializeUI();
    }

    public test() {
        this(5555, 5);
    }

    private void initializeUI() {
        frame = new JFrame("Guess the Number Game");
        frame.setSize(600, 400);

        ipLabel = new JLabel("ip: ");
        portLabel = new JLabel("Port: ");
        textField = new JTextField(10);
        nameLabel = new JLabel("Name: ");
        Submitbutton = new JButton("start");
        Guessbutton = new JButton("guess!");

        postDigitCorrecting = new JLabel("a");

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            this.ip = inetAddress.getHostAddress();
            ipLabel.setText("IP: " + ip);
            portLabel.setText("port: " + this.port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        inputJPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputJPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        inputJPanel.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputJPanel.add(ipLabel, gbc);

        gbc.gridx = 1;
        inputJPanel.add(portLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputJPanel.add(Submitbutton, gbc);

        gbc.gridy = 3;
        inputJPanel.add(Guessbutton, gbc);
        Guessbutton.setVisible(false);

        gbc.gridy = 4;
        inputJPanel.add(postDigitCorrecting, gbc);

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
        if (e.getActionCommand().equals("start")) {
            southPanel();
            textField.setText(null);

            Submitbutton.setVisible(false);
            Guessbutton.setVisible(true);
        }

        if (e.getActionCommand().equals("guess!")) {
            this.guessNumber = textField.getText();
            postDigitCorrecting.setText("asd");
        }
    }

    public String getGuessNumber() {
        return this.guessNumber;
    }

    public void resetGuessNumber() {
        this.guessNumber = "";
    }

    public static void main(String[] args) {
        new UIClient();
    }

    private void southPanel() {
        JPanel southPanel = (JPanel) frame.getContentPane().getComponent(1);
        String name = textField.getText();
        nameLabel.setText("Name: " + name);
        southPanel.add(nameLabel);
        southPanel.add(ipLabel);
        southPanel.add(portLabel);
        southPanel.revalidate();
        southPanel.repaint();
    }

    public void setCurrentRound() {
        this.currentRound -= 1;
    }
}
