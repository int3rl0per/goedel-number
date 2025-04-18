import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Goedel {

    public static void main(String[] args) {
        List<Transition> input = getInput();
        String output = calculateGoedel(input);
        System.out.println("Final Gödel Number: ");
        System.out.print(output);
    }

    private static List<Transition> getInput() {
        Scanner sc = new Scanner(System.in);
        List<Transition> transitions = new ArrayList<>();
        boolean addAnother = true;

        do {
            System.out.println("New transition:");

            // Initial State
            System.out.print("Initial State (n): ");
            while (!sc.hasNextInt()) {
                System.out.print("Please enter a Number: ");
                sc.next();
            }
            int initialState = sc.nextInt();

            // Read Tape Symbol
            System.out.print("Read Tape Symbol (0/1/_): ");
            while (!sc.hasNext("[01_]")) {
                System.out.print("Please enter a valid character (0/1/_): ");
                sc.next();
            }
            char readTapeSymbol = sc.next().charAt(0);

            // Next State
            System.out.print("Next State (n): ");
            while (!sc.hasNextInt()) {
                System.out.print("Please enter a Number: ");
                sc.next();
            }
            int nextState = sc.nextInt();

            // Write Tape Symbol
            System.out.print("Write Tape Symbol (0/1/_): ");
            while (!sc.hasNext("[01_]")) {
                System.out.print("Please enter a valid character (0/1/_): ");
                sc.next();
            }
            char writeTapeSymbol = sc.next().charAt(0);

            // Move Direction
            System.out.print("Move Direction (L/R): ");
            while (!sc.hasNext("[LRlr]")) {
                System.out.print("Please enter a valid character (L/R): ");
                sc.next();
            }
            char moveDirection = sc.next().charAt(0);

            transitions.add(new Transition(initialState, readTapeSymbol, nextState, writeTapeSymbol, moveDirection));

            // Add another?
            System.out.print("Do you want to add another transition? (Y/N) ");
            char addAnotherChar = sc.next().charAt(0);
            if (addAnotherChar == 'Y' || addAnotherChar == 'y') {
                addAnother = true;
            } else {
                addAnother = false;
            }

        } while (addAnother);

        return transitions;
    }

    private static String calculateGoedel(List<Transition> transitions) {
        StringBuilder stringBuilder = new StringBuilder("1");

        for (Transition transition : transitions) {
            // Initial State
            stringBuilder.append("0".repeat(transition.initialState));
            stringBuilder.append('1');

            // Read Tape Symbol
            switch (transition.readTapeSymbol) {
                case '0' -> stringBuilder.append('0');
                case '1' -> stringBuilder.append("00");
                case '_' -> stringBuilder.append("000");
            }
            stringBuilder.append('1');

            // Next State
            stringBuilder.append("0".repeat(transition.nextState));
            stringBuilder.append('1');

            // Write Tape Symbol
            switch (transition.writeTapeSymbol) {
                case '0' -> stringBuilder.append('0');
                case '1' -> stringBuilder.append("00");
                case '_' -> stringBuilder.append("000");
            }
            stringBuilder.append('1');

            // Move Direction
            switch (transition.moveDirection) {
                case 'L', 'l' -> stringBuilder.append('0');
                case 'R', 'r' -> stringBuilder.append("00");
            }

            // Transition Separation
            if (!transition.equals(transitions.getLast())) {
                stringBuilder.append("11");
            }
        }
        return stringBuilder.toString();
    }

    public record Transition(
            int initialState,
            char readTapeSymbol,
            int nextState,
            char writeTapeSymbol,
            char moveDirection
    ) {}
}
