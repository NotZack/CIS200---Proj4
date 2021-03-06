import java.util.*;

/**
 * Proj4.java
 * Zackary Nichol / Friday 3:30PM lab session
 *
 * After the user logs in with their username and password, deals 5 cards depending on which value and suit array is
 * not commented. Then tells the user what hand that they got and asks the user if they want to play again.
 */

public class Proj4 {

    // RANDOM DEAL
     int[] value = new int[5];
     int[] suit = new int[5];

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
    // int[] value = {9, 7, 8, 6, 5};
    // int[] suit = {1,2,4,3,1};

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
        validateCredentials();

        while (playing) {
            dealCards();
            sortHand();
            checkHand();
            checkContinue();
        }
    }

    private void checkContinue() {
        System.out.print("Play Again (Y or N)?");
        String response = scnr.nextLine();

        while (response.toLowerCase().charAt(0) != 'y' && response.toLowerCase().charAt(0) != 'n') {
            System.out.println("Please enter a 'Y' or 'N' only");
            System.out.print("Play Again (Y or N)?");
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
        for (int k = 0; k < value.length; k++) {
            for (int l = k; l < value.length; l++) {
                if (k != l) {
                    if (value[k] == value[l]) {
                        matched++;
                        if (matched == 3) {
                            result = "You were dealt 3 of a kind.";
                        }
                    }
                }
            }
        }

        // Straight
        boolean straightBroken = false;
        for (int i = 0; i < value.length - 1; i++) {
            if (value[i] - 1 != value[i + 1]) {
                straightBroken = true;
                break;
            }
        }
        if (!straightBroken) result = "You were dealt a Straight";

        // Flush
        int matchSuit = suit[0];
        boolean flushBroken = false;
        for (int singleSuit : suit) {
            if (singleSuit != matchSuit) {
                flushBroken = true;
                break;
            }
        }
        if (!flushBroken) result = "You were dealt a flush";

        // Full House
        int fullMatched = 0;
        for (int i = 0; i < value.length; i++) {
            for (int j = i; j < value.length; j++) {
                if (j != i) {
                    if (value[i] == value[j]) fullMatched++;
                    if (fullMatched == 4) result = "You were dealt a Full House.";
                }
            }
        }

        // 4 of a kind
        int fourMatches = 0;
        for (int i = 0; i < value.length; i++) {
            for (int j = i; j < value.length; j++) {
                if (value[i] == value[j]) {
                    fourMatches++;
                    if (fourMatches == 4) {
                        result = "You were dealt 4 of a kind.";
                    }
                }
            }
            fourMatches = 0;
        }

        // Straight Flush
        boolean straightFlushBroken = false;
        for (int i = 0; i < value.length - 1; i++) {
            if (value[i] - 1 != value[i + 1] || suit[i] != suit[i + 1]) {
                straightFlushBroken = true;
                break;
            }
        }
        if (!straightFlushBroken) result = "You were dealt a Straight Flush";

        // Royal Flush
        boolean royalFlushBroken = false;
        for (int item : value) {
            royalFlushBroken = true;
            for (int j = 10; j < 15; j++) {
                if (item == j) {
                    royalFlushBroken = false;
                    break;
                }
            }

            if (royalFlushBroken) break;
        }
        if (!royalFlushBroken) result = "You were dealt a Royal Flush";

        System.out.println("\n" + result + "\n");
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

        System.out.println("\n** Welcome to the 2020 Las Vegas Poker Festival! **\n" +
                " (Application developed by Zackary Nichol) \n");
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
