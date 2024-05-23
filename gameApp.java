import java.util.HashSet;
import java.util.Scanner;

public class gameApp extends Thread {
    OperateSecret secret;
    String userTyping;
    int times = 1;
    Scanner sc = new Scanner(System.in);

    public void run() {
        secret = new OperateSecret();
        secret.start();
        try {
            secret.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    // test function ไม่ได้ใช้
    public void checkCorrecting(String userTyping){
        while (checkInput(userTyping) != 5 || userTyping.length() > 5) {
            System.out.println("\nyour text input is wrong!");
            sc = new Scanner(System.in);
            System.out.print("enter your guess number #" + times + ": ");
            userTyping = sc.nextLine();
        }
    }

    public int checkInput(String data) {
        // นับจำนวน element ที่ไม่ซ้ำกัน
        // HashSet is a collection of items where every item is unique
        HashSet<Character> setElement = new HashSet<>();

        for (int i = 0; i < data.length(); i++) {
            // lst.add(data.charAt(i));
            setElement.add(data.charAt(i));
        }
        return setElement.size();
    }
}
