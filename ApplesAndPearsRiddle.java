import java.util.Random;
import java.util.Scanner;

public class ApplesAndPearsRiddle {
    public static void main(String[] args) {

        // Section 1: Insert Data
        System.out.println("False label says: (A, P, A+P)");
        String[] basketFalseLabel = new String[3];
        basketFalseLabel[0] = insertLabel();
        basketFalseLabel[1] = insertLabel(basketFalseLabel[0]);
        basketFalseLabel[2] = insertLabel(basketFalseLabel[0], basketFalseLabel[1]);

        Random randomBasket = new Random();
        int chosenBasket = randomBasket.nextInt(3);

        Random randomFruit = new Random();
        String pickedFruit = "P";
        if (randomFruit.nextInt(2) == 1) {
            pickedFruit = "A";
        }

        displayMessageForChosenBasketAndFruitsInIt(chosenBasket, pickedFruit);

        System.out.println("What is the true content in each basket?");
        String[] receivedAnswer = new String[3];
        receivedAnswer[0] = insertLabel();
        receivedAnswer[1] = insertLabel(receivedAnswer[0]);
        receivedAnswer[2] = insertLabel(receivedAnswer[0], receivedAnswer[1]);

        // Section 2: Solution
        String[][] basketTrueContent = new String[2][3];

        // True content of the selected basket
        if (basketFalseLabel[chosenBasket].equals(pickedFruit)) {
            basketTrueContent[0][chosenBasket] = "A+P";
        } else if (basketFalseLabel[chosenBasket].contains(pickedFruit)) {
            basketTrueContent[0][chosenBasket] = pickedFruit;
        } else {
            // Answers can be more than one
            basketTrueContent[0][chosenBasket] = pickedFruit;
            basketTrueContent[1][chosenBasket] = "A+P";
        }

        // True content of the other two baskets, if answer can be only one
        for (int i = 0; i < 3; i++) {
            if (basketTrueContent[0][chosenBasket].equals(basketFalseLabel[i])) {
                basketTrueContent[0][i] = basketFalseLabel[3 - chosenBasket - i];
                basketTrueContent[0][3 - chosenBasket - i] = basketFalseLabel[chosenBasket];
                break;
            }
        }

        // True content of the other two baskets, if answers can be more than one
        if (isTheAnswerExactlyOne(basketFalseLabel[chosenBasket], pickedFruit) == false) {
            for (int i = 0; i < 3; i++) {
                if (basketTrueContent[1][chosenBasket].equals(basketFalseLabel[i])) {
                    basketTrueContent[1][i] = basketFalseLabel[3 - chosenBasket - i];
                    basketTrueContent[1][3 - chosenBasket - i] = basketFalseLabel[chosenBasket];
                    break;
                }
            }
        }

        // Section 3: Display results in dynamic way
        if (isTheAnswerExactlyOne(basketFalseLabel[chosenBasket], pickedFruit)) {
            displayResults(receivedAnswer, basketTrueContent[0]);
        } else {
            displayResults(receivedAnswer, basketTrueContent[0], basketTrueContent[1]);
        }
    }

    private static String insertLabel() {
        Scanner console = new Scanner(System.in);

        String falseLabel = "";

        while (true){
            System.out.println("1st basket:");
            falseLabel = console.nextLine().toUpperCase();

            if(checkTheInputCorrectness(falseLabel)) {
                break;
            } else {
                System.out.println("Incorrect Input! Try Again.");
            }
        }

        return falseLabel;
    }

    // Override
    private static String insertLabel(String firstFalseLabel) {
        Scanner console = new Scanner(System.in);

        String falseLabel = "";

        while (true){
            System.out.println("2nd basket:");
            falseLabel = console.nextLine().toUpperCase();

            if(checkTheInputCorrectness(falseLabel) && checkTheInputCorrectness(falseLabel, firstFalseLabel)) {
                break;
            } else {
                System.out.println("Incorrect Input! Try Again.");
            }
        }

        return falseLabel;
    }

    // Override
    private static String insertLabel(String firstFalseLabel, String secondFalseLabel) {
        Scanner console = new Scanner(System.in);
        String falseLabel = "";

        while (true){
            System.out.println("3rd basket:");
            falseLabel = console.nextLine().toUpperCase();

            if(checkTheInputCorrectness(falseLabel) && checkTheInputCorrectness(falseLabel, firstFalseLabel) && checkTheInputCorrectness(falseLabel, secondFalseLabel)) {
                break;
            } else {
                System.out.println("Incorrect Input! Try Again.");
            }
        }

        return falseLabel;
    }

    private static boolean checkTheInputCorrectness(String input) {
        boolean isCorrect = false;

        if (input.equals("A") || input.equals("P") || input.equals("A+P")) {
            isCorrect = true;
        }

        return isCorrect;
    }

    // Override
    private static boolean checkTheInputCorrectness(String input, String labelAlreadyExists) {
        boolean isCorrect = true;

        if (input.equals(labelAlreadyExists)) {
            isCorrect = false;
        }

        return isCorrect;
    }

    private static void displayMessageForChosenBasketAndFruitsInIt(int chosenBasket, String pickedFruit) {
        String basket = "";
        if (chosenBasket + 1 == 3) {
            basket = "third basket";
        } else if (chosenBasket + 1 == 2) {
            basket = "second basket";
        } else {
            basket = "first basket";
        }

        String fruit = "";
        if (pickedFruit.equals("A")) {
            fruit = "apples";
        } else {
            fruit = "pears";
        }

        System.out.printf("%nWell, I can tell you that they are %s in the %s.%n%n"
                , fruit, basket);
    }

    private static boolean isTheAnswerExactlyOne(String falseBasketLabel, String pickedFruit) {
        boolean isExact = false;

        if (falseBasketLabel.contains(pickedFruit)) {
            isExact = true;
        }

        return isExact;
    }

    private static boolean compareReceivedAnswer(String[] receivedAnswer, String[] correctAnswer) {
        boolean isCorrect = false;

        for (int i = 0; i < 3; i++) {
            if (receivedAnswer[i].equals(correctAnswer[i])) {
                isCorrect = true;
            } else {
                isCorrect = false;
                break;
            }
        }

        return isCorrect;
    }

    private static void displayResults(String[] receivedAnswer, String[] correctAnswer) {
        Scanner console = new Scanner(System.in);

        while (true) {
            boolean isAnswerCorrect = compareReceivedAnswer(receivedAnswer, correctAnswer);

            if (isAnswerCorrect) {
                System.out.println("Well done!");
                drawPicture(15, correctAnswer[0], correctAnswer[1], correctAnswer[2]);
                break;
            }

            System.out.println("I don't think so. Would you like to answer again? (Y/N)");
            String tryAgain = console.nextLine().toUpperCase();

            if (tryAgain.equals("N")) {
                drawPicture(15, correctAnswer[0], correctAnswer[1], correctAnswer[2]);
                break;
            }

            receivedAnswer[0] = insertLabel();
            receivedAnswer[1] = insertLabel(receivedAnswer[0]);
            receivedAnswer[2] = insertLabel(receivedAnswer[0], receivedAnswer[1]);
        }
    }

    // Override
    private static void displayResults(String[] receivedAnswer, String[] firstCorrectAnswer, String[] secondCorrectAnswer) {
        Scanner console = new Scanner(System.in);

        while (true) {
            boolean isAnswerCorrect = compareReceivedAnswer(receivedAnswer, firstCorrectAnswer)
                    || compareReceivedAnswer(receivedAnswer, secondCorrectAnswer);

            if (isAnswerCorrect) {
                System.out.println("Well done!");
                System.out.println("First possible answer:");
                drawPicture(15, firstCorrectAnswer[0], firstCorrectAnswer[1], firstCorrectAnswer[2]);
                System.out.println("Second possible answer:");
                drawPicture(15, secondCorrectAnswer[0], secondCorrectAnswer[1], secondCorrectAnswer[2]);
                break;
            }

            System.out.println("I don't think so. Would you like to answer again? (Y/N)");
            String tryAgain = console.nextLine();

            if (tryAgain.equals("N")) {
                System.out.println("First possible answer:");
                drawPicture(15, firstCorrectAnswer[0], firstCorrectAnswer[1], firstCorrectAnswer[2]);
                System.out.println("Second possible answer:");
                drawPicture(15, secondCorrectAnswer[0], secondCorrectAnswer[1], secondCorrectAnswer[2]);
                break;
            }

            receivedAnswer[0] = insertLabel();
            receivedAnswer[1] = insertLabel(receivedAnswer[0]);
            receivedAnswer[2] = insertLabel(receivedAnswer[0], receivedAnswer[1]);
        }
    }

    private static void drawPicture(int n, String firstBasketContent, String secondBasketContent, String thirdBasketContent) {
        // Top Section
        drawStaticRow(n);
        drawDynamicRow(n,"Basket #1", "Basket #2", "Basket #3");
        drawStaticRow(n);

        // Middle Section
        drawSection(n);
        drawDynamicRow(n,firstBasketContent, secondBasketContent, thirdBasketContent);

        // Bottom Section
        drawSection(n);
        drawStaticRow(n);
    }

    private static String drawLineOfSymbols(int length, char symbol){
        StringBuilder lineOfSymbols = new StringBuilder();

        for (int i = 0; i < length; i++) {
            lineOfSymbols.append(symbol);
        }

        return lineOfSymbols.toString();
    }

    private static void drawStaticRow(int n) {
        System.out.print(drawLineOfSymbols(n, '|'));
        System.out.print(drawLineOfSymbols(n / 3, ' '));
        System.out.print(drawLineOfSymbols(n, '|'));
        System.out.print(drawLineOfSymbols(n / 3, ' '));
        System.out.print(drawLineOfSymbols(n, '|'));
        System.out.println();
    }

    private static void drawDynamicRow(int n, String firstString, String secondString, String thirdString){
        System.out.print("|");
        System.out.print(drawLineOfSymbols((n - 2 - firstString.length()) / 2, ' '));
        System.out.print(firstString);
        System.out.print(drawLineOfSymbols((n - 2 - firstString.length()) / 2, ' '));
        System.out.print("|");
        System.out.print(drawLineOfSymbols(n / 3, ' '));
        System.out.print("|");
        System.out.print(drawLineOfSymbols((n - 2 - secondString.length()) / 2, ' '));
        System.out.print(secondString);
        System.out.print(drawLineOfSymbols((n - 2 - secondString.length()) / 2, ' '));
        System.out.print("|");
        System.out.print(drawLineOfSymbols(n / 3, ' '));
        System.out.print("|");
        System.out.print(drawLineOfSymbols((n - 2 - thirdString.length()) / 2, ' '));
        System.out.print(thirdString);
        System.out.print(drawLineOfSymbols((n - 2 - thirdString.length()) / 2, ' '));
        System.out.print("|");
        System.out.println();
    }

    private static void drawSection(int n) {
        for (int i = 0; i < n / 5; i++) {
            if (i == 0 || i == 3) {
                System.out.print("|");
                System.out.print(drawLineOfSymbols(n - 2, ' '));
                System.out.print("|");
                System.out.print(drawLineOfSymbols(n / 3, ' '));
                System.out.print("|");
                System.out.print(drawLineOfSymbols(n - 2, ' '));
                System.out.print("|");
                System.out.print(drawLineOfSymbols(n / 3, ' '));
                System.out.print("|");
                System.out.print(drawLineOfSymbols(n - 2, ' '));
                System.out.print("|");
                System.out.println();
            }
        }
    }
}
