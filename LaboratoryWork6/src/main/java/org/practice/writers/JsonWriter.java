package org.practice.writers;

import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.practice.Configuration;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Getter
public class JsonWriter{

    private static final String FILENAME = Configuration.RESOURCES_DIRECTORY + Configuration.SHORTCUT_FILE;

    private final JSONObject jsonObject;

    public JsonWriter() {
        jsonObject = (JSONObject) createJSONObject();
    }

    private Object createJSONObject(){
        Object o;
        try {
            o = new JSONParser().parse(new FileReader(FILENAME));
        }catch (IOException | ParseException e){
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return o;
    }

    public void write(Map<String, String> map){
        jsonObject.putAll(map);
        saveToFile();
    }


    private void saveToFile() {
        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            fileWriter.write(jsonObject.toJSONString()); // Записываем содержимое в виде JSON-строки
            fileWriter.flush(); // Явно очищаем буфер
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
