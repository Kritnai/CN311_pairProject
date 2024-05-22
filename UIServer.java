import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UIServer extends JFrame implements Runnable {
    private JLabel ipLabel;
    private JLabel portLabel;
    private int port;
    private Thread uiThread;

    JPanel buttonPanel;
    JButton startButton;
    JButton closeButton;

    public boolean isStart;

    public UIServer(int port) {
        this.port = port;
        initializeUI();
    }

    public UIServer() {
        this(5555);
    }

    public void initializeUI() {

        super.setTitle("Server GUI");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // สร้าง JPanel สำหรับแสดงข้อมูล IP และ Port
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        ipLabel = new JLabel("IP: ");
        portLabel = new JLabel("Port: ");
        infoPanel.add(ipLabel);
        infoPanel.add(portLabel);

        // กำหนดให้ JLabel อยู่ตรงกลางของ JPanel
        ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // สร้าง JPanel สำหรับปุ่ม Start Server และ Close Server
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton = new JButton("Start Server");
        closeButton = new JButton("Close Server");
        buttonPanel.add(startButton);

        // นำ JPanel ทั้งสองมาต่อกัน
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // กำหนด ActionListener สำหรับปุ่ม Start Server
        startButton.addActionListener(e -> startServer());
        
        // กำหนด ActionListener สำหรับปุ่ม Close Server
        closeButton.addActionListener(e -> closeServer());

        // แสดง GUI ตรงกลางของหน้าจอ
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void run() {
        while (!isStart) {
        }
        // ทำสิ่งที่ต้องการเมื่อคลิกปุ่ม "Start Server"
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            ipLabel.setText("IP: " + ip);
            portLabel.setText("Port: " + port); // กำหนดพอร์ตเฉยๆ ให้เป็น 1234 (สามารถแก้ไขได้ตามต้องการ)

            // Simulate server work (e.g., listening for connections)
            while (!Thread.currentThread().isInterrupted()) {
                // System.out.println("Server running on IP: " + ip + " Port: " + port);
                Thread.sleep(1000);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    // เริ่มการทำงานของเซิร์ฟเวอร์
    public void startServer() {
        isStart = true;
        System.out.println("starting UI.");
        if (uiThread == null || !uiThread.isAlive()) {
            uiThread = new Thread(this);
            uiThread.start();
        }
        buttonPanel.removeAll();
        buttonPanel.add(closeButton);

        // setIsStart(true);

    }

    // ปิดการทำงานของเซิร์ฟเวอร์
    public void closeServer() {
        isStart = false;
        if (uiThread != null && uiThread.isAlive()) {
            uiThread.interrupt();
        }
        // ทำสิ่งที่ต้องการเมื่อคลิกปุ่ม "Close Server"
        dispose(); // ปิดหน้าต่าง GUI
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UIServer());

    }

}
