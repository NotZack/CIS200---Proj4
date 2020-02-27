import java.util.*;
import java.util.stream.Collectors;

public class Proj4 {

    // RANDOM DEAL
    //int[] value = new int[5];
    //int[] suit = new int[5];

    // Royal Flush
    // int[] value = {10, 12, 14, 13, 11};
    // int[] suit = {1,1,1,1,1};

    // Straight Flush
    // int[] value = {9, 7, 8, 6, 5};
    // int[] suit = {1,1,1,1,1};

    // 4 of kind
    // int[] value = {9, 7, 9, 9, 9};
    // int[] suit = {1,1,3,4,2};

    // Full House
    // int[] value = {9, 7, 9, 7, 9};
    // int[] suit = {1,2,3,4,2};

    // Flush
    // int[] value = {9, 10, 8, 6, 5};
    // int[] suit = {1,1,1,1,1};

    // Straight
     int[] value = {9, 7, 8, 6, 5};
     int[] suit = {1,2,4,3,1};

    // 3 of kind
    // int[] value = {9, 7, 9, 2, 9};
    // int[] suit = {1,2,3,4,2};

    // Two Pair
    // int[] value = {9, 7, 9, 2, 7};
    // int[] suit = {1,2,3,4,1};

    // One Pair
    // int[] value = {9, 7, 8, 2, 7};
    // int[] suit = {1,2,3,4,1};

    // High Card (Ace)
    // int[] value = {9, 7, 8, 14, 11};
    // int[] suit = {1,2,3,4,1};

    private Scanner scnr = new Scanner(System.in);
    private Random random = new Random();
    private boolean redeal = false;
    private boolean playing = true;

    public void start() {
        //validateCredentials();

        while (playing) {
            dealCards();
            sortHand();
            checkHand();
            checkContinue();
        }
    }

    private void checkContinue() {
        System.out.println("Play Again (Y or N)?");
        String response = scnr.nextLine();

        while (response.toLowerCase().charAt(0) == 'y' || response.toLowerCase().charAt(0) == 'n') {
            System.out.println("Please enter a 'Y' or 'N' only");
            System.out.println("Play Again (Y or N)?");
            response = scnr.nextLine();
        }

        if (response.toLowerCase().charAt(0) == 'y') redeal = true;
        else playing = false;
    }

    private void checkHand() {
        String result = "";


        // High card
        int highest = 0;
        for (int item : value) if (item > highest) highest = item;
        result = "High card is a(n) " + cardValueLookup(highest) + ".";

        // Pair/Two pair
        outerLoop:
        for (int i = 0; i < value.length; i++) {
            for (int j = i; j < value.length; j++) {
                if (i != j) {
                    if (value[i] == value[j] && result.equals("You were dealt a pair.")) {
                        result = "You were dealt two pair.";
                        break outerLoop;
                    }
                    else if (value[i] == value[j]) result = "You were dealt a pair.";
                }
            }
        }

        // 3 of a kind
        int matched = 0;
        for (int i = 0; i < value.length; i++) {
            for (int j = i; j < value.length; j++) {
                if (i != j) {
                    if (value[i] == value[j]) {
                        matched++;
                        if (matched == 3) {
                            result = "You were dealt 3 of a kind.";
                        }
                    }
                }
            }
        }

        // Straight
        boolean broken = false;
        for (int i = 0; i < value.length; i++) {
            broken = true;
            for (int j = 0; j < value.length; j++) {
                if (i != j) {
                    if (value[i] + 1 == value[j]) broken = false;
                }
            }
        }
        if (!broken) result = "You were dealt a Straight";

        System.out.println(result);
    }

    private void sortHand() {
        for (int j = 0; j < value.length; j++) {
            for (int i = 0; i < value.length - 1; i++) {
                final int tempValue;
                final int tempSuit;
                if (value[i] < value[i + 1]) {
                    tempValue = value[i];
                    tempSuit = suit[i];
                    value[i] = value[i + 1];
                    suit[i] = suit[i + 1];
                    value[i + 1] = tempValue;
                    suit[i + 1] = tempSuit;
                }
            }
        }
    }

    private void dealCards() {
        System.out.println("\nShuffling cards ...");
        if (redeal || value[0] == 0) shuffleCards();

        System.out.println("Dealing cards ...\n");
        System.out.println("Here are your five cards ...");

        for (int i = 0; i < value.length; i++) {
            String actualValue = Integer.toString(value[i]);
            String actualSuit = Integer.toString(suit[i]);

            actualValue = cardValueLookup(value[i]);

            switch(suit[i]) {
                case 1: actualSuit = "Spades"; break;
                case 2: actualSuit = "Clubs"; break;
                case 3: actualSuit = "Hearts"; break;
                case 4: actualSuit = "Diamonds"; break;
            }

            System.out.println("\t" + actualValue + " of " + actualSuit);
        }
    }

    private void shuffleCards() {
        for (int i = 0; i < value.length; i++) {
            value[i] = random.nextInt(13) + 2;
            suit[i] = random.nextInt(4) + 1;
        }

        for (int j = 0; j < value.length; j++) {
            for (int k = 0; k < value.length - 1; k++) {
                if (k + 1 != j) {
                    if (value[j] == value[k + 1] && suit[j] == suit[k + 1]) shuffleCards();
                }
            }
        }
    }

    private void validateCredentials() {
        final String LAST_NAME = "Nichol";
        final int WID = 821027744;
        final String PASSWORD = "CIS200$Spr20";
        final String username = LAST_NAME.substring(0, 4) + (WID % 10000);

        System.out.println("Username generated: " + username + "\n");

        int attempts = 0;
        boolean credentialValid = false;
        while (!credentialValid) {

            if (attempts == 3) {
                System.out.println("Invalid Username and/or Password entered 3 times...EXITING");
                System.exit(0);
            }

            System.out.print("Please " + ((attempts > 0) ? "re-" : "") + "enter your Username: ");
            credentialValid = scnr.nextLine().equals(username);

            System.out.print("Please " + ((attempts > 0) ? "re-" : "") + "enter your Password: ");
            credentialValid = scnr.nextLine().equals(PASSWORD) && credentialValid;

            if (!credentialValid) {
                attempts++;
                System.out.println("User and/or Password is invalid\n");
            }
        }
    }

    private String cardValueLookup(int value) {
        switch(value) {
            case 11: return "Jack";
            case 12: return  "Queen";
            case 13: return  "King";
            case 14: return  "Ace";
        }
        return Integer.toString(value);
    }

    public static void main(String[] args) {
        Proj4 main = new Proj4();
        main.start();
    }
}
