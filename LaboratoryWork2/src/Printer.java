import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class Printer implements AutoCloseable{

    public static class Position{
        private int x;
        private int y;
        public Position(int x, int y){
            setX(x);
            setY(y);
        }

        private void setX(int x){
            if (x < 0){
                throw new IllegalArgumentException();
            }
            this.x = x;
        }

        private void setY(int y){
            if (y < 0){
                throw new IllegalArgumentException();
            }
            this.y = y;
        }

        public int getX(){return this.x;}
        public int getY(){return this.y;}
    }

    private static final char DEFAULT_SYMBOL = 'x';
    private static final String DIRECTORY = "src/fonts";
    private final String extension;
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
    public Printer(Colors colorName, Position position, int size, Character... symbolOpt) throws Exception {
        this.extension = ".json";
        this.position = new ArrayList<>();
        this.position.add(position.getX());
        this.position.add(position.getY());

        this.correctSize = checkCorrectSize(size, this.extension);
        if (!this.correctSize){
            this.close();
        }
        this.SIZE = size;

        this.fileName = DIRECTORY + "/" + size + extension;
        this.symbol = symbolOpt.length > 0 ? symbolOpt[0] : DEFAULT_SYMBOL;
        this.COLOR_CODE = colorName.getColorCode();
    }

    private static boolean checkCorrectSize(int size, String extension){
        File folder;
        try {
            folder = new File(DIRECTORY);
        }catch (NullPointerException e){
            throw new NullPointerException();
        }
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().equals(size + extension)){
                    return true;
                }
            }
        }
        return false;
    }

    private static Character checkCorrectMessage(String message){
        for (char c : message.toCharArray()){
            if ('A' > c || c > 'Z'){
                return c;
            }
        }
        return null;
    }

    private static TreeSet<Character> messageToSet(String message){
        TreeSet<Character> set = new TreeSet<>();
        for (char c : message.toCharArray()){
            set.add(c);
        }
        return set;
    }

    private static String[] parseJsonFile(String fileName, Character character) {
        Object o;
        try {
            o = new JSONParser().parse(new FileReader(fileName));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = (JSONObject) o;
        String key = character.toString();
        String value = jsonObject.get(key).toString();
        return value.split("\n");
    }

    private static TreeMap<Character, String[]> createFontForMessage(String message, String fileName){
        TreeSet<Character> set = messageToSet(message);

        TreeMap<Character, String[]> font = new TreeMap<>();
        for (Character c : set){
            font.put(c, parseJsonFile(fileName, c));

        }

        return font;
    }

    private static String[] replace(String[] array, char symbol){
        for (int i = 0; i < array.length; i++){
            array[i] = array[i].replace(DEFAULT_SYMBOL, symbol);
        }
        return array;
    }

    public void print(String message) throws Exception {
        if (message == null) throw new NullPointerException();

        message = message.toUpperCase();
        Character illegalSymbol = checkCorrectMessage(message);
        if (illegalSymbol != null){
            this.close();
            throw new IllegalArgumentException("Invalid message. Use only English letters. '" + illegalSymbol + "' got.");
        }

        for (int i = 0; i < this.position.getLast(); i++){
            System.out.println();
        }

        TreeSet<Character> charSet = messageToSet(message);
        TreeMap<Character, String[]> font = createFontForMessage(message, this.fileName);

        if (this.symbol != DEFAULT_SYMBOL){
            for (Character c : charSet){
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

    public static void print(String message, Colors color, Position position, int size, Character... symbOpt) throws Exception {
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
