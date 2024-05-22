import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.util.Scanner;

public class AppClient {
    String name;
    static int roundForGuess;
    static String str;

    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 8080);
        // input data
        dataInput = new DataInputStream(s.getInputStream());
        // output data
        dataOutput = new DataOutputStream(s.getOutputStream());

        setUp();

        int rounds = 1;
        while (rounds <= roundForGuess) {
            String userTyping = userInput(rounds);
            dataOutput.writeUTF(userTyping);
            dataOutput.flush();

            str = dataInput.readUTF();
            System.out.println("server say: " + str);

            while (str.equals("wrong data input.")) {
                userTyping = userInput(rounds);
                dataOutput.writeUTF(userTyping);
                dataOutput.flush();

                str = dataInput.readUTF();
                System.out.println("server say: " + str);
            }

            if (str.equals("client found the secret.")) {
                break;
            }
            if (str.equals("server was reject close.")) {
                break;
            }
            setCurrentDigitPost();
            System.out.println("corrected position: " + getCurrentPosition());
            System.out.println("corrected digit: " + getCurrentDigit());
            rounds += 1;
        }
        dataOutput.close();
        s.close();

    }

    private static String userInput(int times) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nenter your guess number #" + times + ": ");
        String data = sc.nextLine();
        return data;
    }

    private static void setUp() {
        try {
            // synchronize round for guess
            roundForGuess = dataInput.readInt();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static String[] currentDigitPost;
    private static void setCurrentDigitPost() {
        String[] lstDataServer = str.split("_");

        // Step 1: Remove the brackets
        String trimmedData = lstDataServer[1].substring(1, lstDataServer[1].length() - 1);
        System.out.println("correct history: " + trimmedData);
        // Step 2: Split the string by comma
        String[] parts = trimmedData.split(",\\s*"); // Split by comma and optional space
        // Handle colon-separated pairs
        currentDigitPost = parts[parts.length - 1].split(":");
    }
    
    private static String getCurrentPosition(){
        return currentDigitPost[0];
    }
    private static String getCurrentDigit(){
        return currentDigitPost[1];
    }

}
