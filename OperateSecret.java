import java.util.ArrayList;
import java.util.Random;

public class OperateSecret extends Thread {
    ArrayList<String> setOfDigits = initArray();
    ArrayList<String> secret = new ArrayList<>();
    String secretString = "";
    int digit = 0;
    int position = 0;

    public void run() {
        createSecret();
    }

    // setup array for random
    private ArrayList<String> initArray() {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("1");
        lst.add("2");
        lst.add("3");
        lst.add("4");
        lst.add("5");
        lst.add("6");
        lst.add("7");
        lst.add("8");
        lst.add("9");
        lst.add("0");
        return lst;
    }

    // create secret number
    private void createSecret() {
        Random ran = new Random();

        for (int i = 0; i < 5; i++) {
            int index = ran.nextInt(setOfDigits.size());
            secret.add(setOfDigits.get(index));
            setOfDigits.remove(index);
        }

        for (String i : secret) {
            this.secretString += i;
        }
    }

    public String getSecret() {
        return secretString;
    }

    // check and update input correcting
    public boolean checkResult(String uesrGuess) {
        position = 0;
        digit = 0;
        for (int i = 0; i < uesrGuess.length(); i++) {
            if (uesrGuess.charAt(i) == secretString.charAt(i)) {
                position += 1;
            }
            if (secretString.indexOf(uesrGuess.charAt(i)) != -1) {
                digit += 1;
            }
        }
        // System.out.println("position: " + position + "\tdigit: " + digit + "\n");

        return position == 5;
    }

    public int getCurrentDigit(){
        return this.digit;
    }
    public int getCurrentPosition(){
        return this.position;
    }
}
