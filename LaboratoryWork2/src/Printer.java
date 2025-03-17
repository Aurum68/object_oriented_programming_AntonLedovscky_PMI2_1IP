import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class Printer implements AutoCloseable{

    private final String COLOR_CODE;
    private final int SIZE;
    private final boolean correctSize;
    private final List<Integer> position;
    private final String fileName;
    private final char symbol;

    /**
     * @param size can be 7 or 5
     * @param symbolOpt unnecessary char
     * */
    public Printer(Colors colorName, Integer[] position, int size, Character... symbolOpt) throws Exception {
        if (position.length != 2){
            throw new IllegalArgumentException("Incorrect array size. It must be 2. " + position.length + " got");
        }
        this.position = Arrays.asList(position);

        this.correctSize = checkCorrectSize(size);
        if (!this.correctSize){
            this.close();
        }
        this.SIZE = size;

        this.fileName = "src/fonts/" + size + ".json";
        this.symbol = symbolOpt.length > 0 ? symbolOpt[0] : 'x';
        this.COLOR_CODE = colorName.getColorCode();
    }

    private static boolean checkCorrectSize(int size){
        File folder = new File("src/fonts");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().equals(size + ".json")){
                    return true;
                }
            }
        }
        return false;
    }

    private static TreeMap<Character, Integer> messageToMap(String message){
        TreeMap<Character, Integer> charNumber = new TreeMap<>();
        for (char c : message.toCharArray()){
            charNumber.put(c, Character.getNumericValue(c) - Character.getNumericValue('A'));
        }
        return charNumber;
    }

    private static Character checkCorrectMessage(String message){
        for (char c : message.toCharArray()){
            if (!('A' <= c && c <= 'Z')){
                return c;
            }
        }
        return null;
    }

    private static String[] parseJsonFile(String fileName, Character character) throws Exception {
        Object o = new JSONParser().parse(new FileReader(fileName));
        JSONObject jsonObject = (JSONObject) o;
        String key = character.toString();
        String value = jsonObject.get(key).toString();
        return value.split("\n");
    }

    private static TreeMap<Character, String[]> createFontForMessage(String message, String fileName) throws Exception {
        TreeMap<Character, Integer> charNumber = messageToMap(message);

        TreeMap<Character, String[]> font = new TreeMap<>();
        for (Character c : charNumber.keySet()){
            font.put(c, parseJsonFile(fileName, c));
        }

        return font;
    }

    private static String[] replace(String[] array, char symbol){
        for (int i = 0; i < array.length; i++){
            array[i] = array[i].replace('x', symbol);
        }
        return array;
    }

    public void print(String message) throws Exception {
        message = message.toUpperCase();
        Character illegalSymbol = checkCorrectMessage(message);
        if (illegalSymbol != null){
            this.close();
            throw new IllegalArgumentException("Invalid message. Use only English letters. '" + illegalSymbol + "' got.");
        }
        TreeMap<Character, Integer> charNumber = messageToMap(message);

        for (int i = 0; i < this.position.getLast(); i++){
            System.out.println();
        }

        TreeMap<Character, String[]> font = createFontForMessage(message, this.fileName);

        if (this.symbol != 'x'){
            for (Character c : charNumber.keySet()){
                font.put(c, replace(font.get(c), this.symbol));
            }
        }

        for (int j =0 ; j < this.SIZE; j++) {
            System.out.print(" ".repeat(this.position.getFirst()));
            for (int i = 0; i < message.length(); i++) {
                String lineFromFont = font.get(message.charAt(i))[j];
                if (lineFromFont.length() < this.SIZE * 2 + 2){
                    lineFromFont += " ".repeat(this.SIZE * 2 - lineFromFont.length() + 2);
                }
                System.out.print(COLOR_CODE + lineFromFont);
            }
            System.out.println();
        }

        System.out.print(Colors.RESET.getColorCode());
    }

    public static void print(String message, Colors color, Integer[] position, int size, Character... symbOpt) throws Exception {
        try(Printer printer = new Printer(color, position, size, symbOpt)) {
            printer.print(message);
        }
    }

    @Override
    public void close() throws Exception {
        if (!correctSize){
            throw new IllegalArgumentException("Invalid size.");
        }
        System.out.print(Colors.RESET.getColorCode());
    }
}
