import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.net.ServerSocket;
import java.net.Socket;

public class AppServer {
    static final int PORT = 8080;

    static String guess;
    static gameApp game = new gameApp();
    static ArrayList<String> lastGuess = new ArrayList<>();
    static ArrayList<String> lastDigitPosition = new ArrayList<>();

    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    private static final int roundForGuess = 5;

    static ServerSocket server;

    static UIServer uiServer;

    public static void main(String[] args) throws Exception {
        uiServer = new UIServer(PORT);
        
        while (!uiServer.isStart) {
            System.out.println("server not open yet.");
            Thread.sleep(1000);
        }

        // open UI server
        uiServer.startServer();

        // connect client
        connectSocket(PORT);

        startGame(); // generate secret number
        setUp(); // set secret number in server and send round to client

        int rounds = 1;
        while (rounds <= roundForGuess) {
            guess = dataInput.readUTF();
            System.out.println("client guess number: " + guess);

            while (game.checkInput(guess) != 5 || guess.length() > 5) {
                System.out.println("\nyour text input is wrong!");
                dataOutput.writeUTF("wrong data input.");
                dataOutput.flush();
                guess = dataInput.readUTF();
                System.out.println("client guess number: " + guess);
            }

            rounds += 1;
            if (game.secret.checkResult(guess)) {
                System.out.println("\nclient found the secret number! --> (" + game.secret.getSecret() + ")");
                dataOutput.writeUTF("client found the secret.");
                dataOutput.flush();
                break;
            }
            lastGuess.add(guess);
            lastDigitPosition.add(game.secret.position + ":" + game.secret.digit);
            dataOutput.writeUTF(lastGuess + "_" + lastDigitPosition);
            dataOutput.flush();

            // check status server
            if (!uiServer.isStart) {
                dataOutput.writeUTF("server was reject close.");
                dataOutput.flush();
                break;
            }
        }
        if (game.secret.position != 5) {
            System.out.println("client lose! The secret number is " + game.secret.secretString);
        }

        server.close();
        uiServer.closeServer();

        System.out.println("\nServer auto close.");

    }

    private static void connectSocket(int port) throws IOException {
        server = new ServerSocket(port); // socket bind lisen
        // accept client
        System.out.println("wating for client connect.");
        Socket serve1 = server.accept();
        System.out.println("client was join.");

        // get input from Streaming
        dataInput = new DataInputStream(serve1.getInputStream());
        // get output from streaming
        dataOutput = new DataOutputStream(serve1.getOutputStream());
    }

    private static void startGame() {
        game.start();
        try {
            game.join();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("the secret is " + game.secret.getSecret());

    }

    private static void setUp() {
        try {
            // set secret
            dataOutput.writeUTF(game.secret.getSecret());
            dataOutput.flush();
            dataInput.readUTF();
             // synchronize round for guess
            dataOutput.writeInt(roundForGuess);
            dataOutput.flush();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
