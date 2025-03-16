import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;

public class Printer implements AutoCloseable{

    private final String COLOR_CODE;
    private boolean correctColorName;
    private final int SIZE;
    private boolean correctSize;
    private final List<String> font;

    /**
     * @param colorName : BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE in any registers.
     * @param size can be 7 or 5
     * @param symbolOpt unnecessary char
     * */
    public Printer(String colorName, int size, Character... symbolOpt) throws Exception {
        colorName = colorName.toUpperCase();
        if (!checkCorrectOfColorName(colorName)){
            this.close();
        }
        this.COLOR_CODE = Colors.valueOf(colorName).getColorCode();
        if (!checkCorrectSize(size)){
            this.close();
        }
        this.SIZE = size;

        String fileName = "src/fonts/" + size + ".txt";
        this.font = Files.readAllLines(Paths.get(fileName));
        char symbol = symbolOpt.length > 0 ? symbolOpt[0] : 'x';
        if (symbol != 'x'){
            font.replaceAll(s -> s.replace('x', symbol));
        }
    }

    private boolean checkCorrectOfColorName(String colorName){
        for (Colors color : Colors.values()){
            if (color.name().equals(colorName)){
                correctColorName = true;
                return true;
            }
        }
        correctColorName = false;
        return false;
    }

    private boolean checkCorrectSize(int size){
        File folder = new File("src/fonts");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().equals(size + ".txt")){
                    correctSize = true;
                    return true;
                }
            }
        }
        correctSize = false;
        return false;
    }

    private TreeMap<Character, Integer> messageToMap(String message){
        TreeMap<Character, Integer> charNumber = new TreeMap<>();
        for (char c : message.toCharArray()){
            charNumber.put(c, Character.getNumericValue(c) - Character.getNumericValue('A'));
        }
        return charNumber;
    }

    private Character checkCorrectMessage(String message){
        for (char c : message.toCharArray()){
            if (!('A' <= c && c <= 'Z')){
                return c;
            }
        }
        return null;
    }

    public void print(String message) throws Exception {
        message = message.toUpperCase();
        Character illegalSymbol = checkCorrectMessage(message);
        if (illegalSymbol != null){
            this.close();
            throw new IllegalArgumentException("Invalid message. Use only English letters. '" + illegalSymbol + "' got.");
        }
        TreeMap<Character, Integer> charNumber = messageToMap(message);

        for (int j =0 ; j < this.SIZE; j++) {
            for (int i = 0; i < message.length(); i++) {
                int index = charNumber.get(message.charAt(i));
                String lineFromFont = font.get((this.SIZE + 1) * index + j);
                if (lineFromFont.length() < this.SIZE * 2 + 2){
                    lineFromFont += " ".repeat(this.SIZE * 2 - lineFromFont.length() + 2);
                }
                System.out.print(COLOR_CODE + lineFromFont);
            }
            System.out.println();
        }

        this.close();
    }

    @Override
    public void close() throws Exception {
        if (!correctColorName){
            throw new IllegalArgumentException("Invalid color name.");
        }
        if (!correctSize){
            throw new IllegalArgumentException("Invalid size.");
        }
        System.out.print(Colors.RESET.getColorCode());
    }
}
