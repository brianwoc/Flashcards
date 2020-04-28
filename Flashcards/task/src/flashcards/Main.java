package flashcards;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static LinkedHashMap<String, String> definitionsAnswer = new LinkedHashMap<>();
    static List<String> logger = new ArrayList<>();
    static List<DefinionAnswer> listCard = new ArrayList<>();


    public static void add() {
        logger.add("add");
        System.out.println("The card:");
        logger.add("The card:");
        String card = scanner.nextLine();
        logger.add(card);
        for (DefinionAnswer definionAnswer : listCard) {
            if (definionAnswer.getCard().equals(card)) {
                System.out.println("The card \"" + card + "\" already exists.\n");
                logger.add("The card \"" + card + "\" already exists.\n");
                return;
            }
        }

        System.out.println("The definition of the card:");
        String definition = scanner.nextLine();
        for (DefinionAnswer definionAnswer : listCard) {
            if (definionAnswer.getDefinition().equals(card)) {
                System.out.println("The definition \"" + definition + "\" already exists.\n");
                logger.add("The definition \"" + definition + "\" already exists.\n");
                return;
            }
        }
        DefinionAnswer definionAnswer = new DefinionAnswer(card, definition);
        listCard.add(definionAnswer);
        System.out.println("The pair (\"" + card + "\":\"" + definition + "\") has been added");
        System.out.println();

        logger.add("The pair (\"" + card + "\":\"" + definition + "\") has been added");
        logger.add("The definition of the card:");
        logger.add(definition);
    }


    public static void remove() {
        System.out.println("The card:");
        String card = scanner.nextLine();
        logger.add("remove");
        logger.add("The card:");
        logger.add(card);


        for (int i = 0; i < listCard.size(); i++) {
            if (listCard.get(i).getCard().equals(card)) {
                listCard.remove(i);
                System.out.println("The card has been removed");
                System.out.println();
                logger.add("The card has been removed");
                return;
            }
        }
        System.out.println("Can't remove \"" + card + "\": there is no such card");
        System.out.println();
        logger.add("Can't remove \"" + card + "\": there is no such card");
    }

    public static void log() {
        System.out.println("File name:");
        String fileName = scanner.nextLine();
        logger.add("File name:");
        logger.add(fileName);

//        String path = "C:\\dev\\Proj\\Flashcards\\Flashcards\\task\\src\\flashcards\\";


        File file = new File(fileName);

        try (FileWriter writer = new FileWriter(file)) {
            for (String item : logger) {
                writer.write(item);
                writer.write("\n");
            }
            writer.write("The log has been saved.");
            System.out.println("The log has been saved.");
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }

    }


    public static void hardestCard() {
        int max = 0;
        String middle = "";
        String begin = "The hardest cards ";

        String begin2 = "are \"";
        String end2 = "them.";


        List<String> listOfHardestCard = new ArrayList<>();
        for (DefinionAnswer definitionAnswer : listCard) {
            if (definitionAnswer.getMistakes() > max) {
                max = definitionAnswer.getMistakes();
            }
        }
        for (DefinionAnswer definitionAnswer : listCard) {
            if (definitionAnswer.getMistakes() == max) {
                listOfHardestCard.add(definitionAnswer.getCard());
            }
        }
        if (max == 0) {
            System.out.println("There are no cards with errors.");
            logger.add("There are no cards with errors.");

        } else {
            if (listOfHardestCard.size() == 1) {
                begin2 = begin2.replace("are", "is");
                end2 = end2.replace("them.", "it.");
                begin = begin.replace("cards", "card");
            }
            for (int i = 0; i < listOfHardestCard.size(); i++) {
                if (i < listOfHardestCard.size() - 1) {
                    middle = middle.concat(listOfHardestCard.get(i) + "\", \"");
                } else {
                    middle = middle.concat(listOfHardestCard.get(i) + "\".");
                }

            }


            String end = " You have " + max + " errors answering ";
            System.out.println(begin + begin2 + middle + end + end2);
            logger.add(begin + begin2 + middle + end + end2);
        }

    }

    private static void resetStats() {

        for (int i = 0; i < listCard.size(); i++) {
            listCard.get(i).setMistakes(0);

        }
        System.out.println("Card statistics has been reset.");
        logger.add("Card statistics has been reset.");
    }


    public static void import_(String fileFromCmd) {
        String path = "C:\\dev\\Proj\\Flashcards\\Flashcards\\task\\src\\flashcards\\";
        File file;
        if (fileFromCmd.length() > 0) {
            file = new File(path + fileFromCmd);
        } else {
            System.out.println("File name");
            String fileName = scanner.nextLine();
            file = new File(path + fileName);
            logger.add("File name");
            logger.add(fileName);
            logger.add("import");
        }
        int licznik = 0;

        boolean flag = true;

        try (Scanner scanner1 = new Scanner(file)) {

            while (scanner1.hasNext()) {
                String newLine = scanner1.nextLine();
                String[] lista = newLine.split(":");
                flag = true;
                for (int i = 0; i < listCard.size(); i++) {
                    if (listCard.get(i).getCard().equals(lista[0]) || listCard.get(i).getDefinition().equals(lista[1])) {
                        flag = false;
                        listCard.get(i).setMistakes(Integer.parseInt(lista[2]));
                    }

                }
                if (flag) {
                    DefinionAnswer definionAnswer = new DefinionAnswer(lista[0], lista[1]);
                    definionAnswer.setMistakes(Integer.parseInt(lista[2]));
                    listCard.add(definionAnswer);
                    licznik++;
                }
            }
            System.out.println(licznik  + " cards have been loaded");
            logger.add(licznik + " cards have been loaded");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            logger.add("File not found");
        }

    }

    public static void export(String exportFileCmd) {
        String path = "C:\\dev\\Proj\\Flashcards\\Flashcards\\task\\src\\flashcards\\";
        File file;

        if (exportFileCmd.length() > 0) {
            file = new File(path + exportFileCmd);
        } else {
            System.out.println("File name");
            String fileName = scanner.nextLine();
            file = new File(path + fileName);
            logger.add("export");
            logger.add("File name");
            logger.add(fileName);
        }


        try (FileWriter writer = new FileWriter(file)) {
            for (DefinionAnswer definionAnswer : listCard) {
                writer.write(definionAnswer.getCard());
                writer.write(":");
                writer.write(definionAnswer.getDefinition());
                writer.write(":");
                writer.write(String.valueOf(definionAnswer.getMistakes()));
                writer.write("\n");

            }


            System.out.println(listCard.size() + " cards have been saved.");
            logger.add(listCard.size() + " cards have been saved.");
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
            String message = e.getMessage();
            logger.add("An exception occurs " + message);
        }
    }


    public static void ask() {
        System.out.println("How many times to ask?");
        String timesString = scanner.nextLine();
        int times = Integer.parseInt(timesString);

        logger.add("ask");
        logger.add("How many times to ask?");

        List<String> listAnswers = getListAnswersName(listCard);

        int counter = -1;
        for (int i = 0; i < times; i++) {
            if (i < listCard.size()) {
                counter++;
            } else if (i >= listCard.size()) {
                counter = i % listCard.size();
            }

            System.out.println("Print the definition of \"" + listCard.get(counter).getCard() + "\":");
            logger.add("Print the definition of \"" + listCard.get(counter).getCard() + "\":");
            String answer = scanner.nextLine();
            logger.add(answer);
            if (listAnswers.contains(answer)) {
                if (answer.equals(listCard.get(counter).getDefinition())) {
                    System.out.println("Correct answer.");
                    logger.add("Correct answer.");

                } else {
                    for (int j = 0; j < listCard.size(); j++) {
                        if (answer.equals(listCard.get(j).getDefinition())) {
                            System.out.println("Wrong answer. The correct one is \"" + listCard.get(counter).getDefinition() + "\", you've just written the definition of \"" + listCard.get(j).getCard() + "\"");
                            logger.add("Wrong answer. The correct one is \"" + listCard.get(j).getDefinition() + "\", you've just written the definition of \"" + listCard.get(j).getCard() + "\"");
                            listCard.get(counter).setMistakes(listCard.get(counter).getMistakes() + 1);
                        }
                    }
                }

            } else {
                System.out.println("Wrong answer. The correct one is \"" + listCard.get(counter).getDefinition() + "\".");
                logger.add("Wrong answer. The correct one is \"" + listCard.get(counter).getDefinition() + "\".");
                listCard.get(counter).setMistakes(listCard.get(counter).getMistakes() + 1);
            }
        }
    }


    public static void main(String[] args) {
        String exportFile = "";
        if (args.length > 0 && args.length<3) {

            if (args.length > 0 && args[0].equals("-import")) {
                import_(args[1]);
            }

            if (args.length > 0 && args[0].equals("-export")) {
                exportFile = args[1];
            }
        }
        if (args.length>2){

            if (args.length > 0 && args[0].equals("-import")) {
                import_(args[1]);
                exportFile = args[3];

            }else {
                import_(args[3]);
                exportFile = args[1];
            }

        }


        System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        logger.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        String userChoice = scanner.nextLine();
//        DefinionAnswer definionAnswer1 = new DefinionAnswer("Francja", "Paryż");
//        DefinionAnswer definionAnswer2 = new DefinionAnswer("Polska", "Warszawa");
//        DefinionAnswer definionAnswer3 = new DefinionAnswer("UK", "Londyn");
//        listCard.add(definionAnswer1);
//        listCard.add(definionAnswer2);
//        listCard.add(definionAnswer3);


        while (!userChoice.equals("exit")) {
            switch (userChoice) {
                case "add":
                    add();
                    userChoice = getEntryText();
                    break;
                case "remove":
                    remove();
                    userChoice = getEntryText();
                    break;
                case "ask":
                    ask();
                    userChoice = getEntryText();
                    break;
                case "import":
                    import_("");
                    userChoice = getEntryText();
                    break;
                case "export":
                    export("");
                    userChoice = getEntryText();
                    break;
                case "log":
                    log();
                    userChoice = getEntryText();
                    break;
                case "hardest card":
                    hardestCard();
                    userChoice = getEntryText();
                    break;
                case "reset stats":
                    resetStats();
                    userChoice = getEntryText();
                    break;

            }
        }
        if (exportFile.length() > 0) {
            System.out.println("Bye Bye!");
            export(exportFile);
        }
        else {
            System.out.println("Bye Bye!");
        }
    }

    public static String getEntryText() {
        System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        logger.add("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        String userChoice = scanner.nextLine();
        logger.add(userChoice);
        return userChoice;
    }

    public static List<String> getListAnswersName(List<DefinionAnswer> listCard) {
        List<String> listAnswers = new ArrayList<>();
        for (int i = 0; i < listCard.size(); i++) {
            listAnswers.add(listCard.get(i).getDefinition());
        }
        return listAnswers;
    }


}

class DefinionAnswer {
    private String Card;
    private String Definition;
    private int mistakes;

    public DefinionAnswer(String card, String definition) {
        Card = card;
        Definition = definition;
        this.mistakes = 0;
    }

    public String getCard() {
        return Card;
    }

    public void setCard(String card) {
        Card = card;
    }

    public String getDefinition() {
        return Definition;
    }

    public void setDefinition(String definition) {
        Definition = definition;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }


}

