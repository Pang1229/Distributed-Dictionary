/*
   Name: Xiaoyu Pang
   User Name: XIAPANG
   Student Number: 1224627
*/
package Server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Dictionary {
    private Map<String, String> words = new HashMap<>();

    // load the default dictionary from file
    public void loadFromFile(String filepath) {
        try (Scanner scanner = new Scanner(new File(filepath))) {
            while (scanner.hasNextLine()) {
                String[] entry = scanner.nextLine().split(":", 2);
                if (entry.length == 2) {
                    words.put(entry[0], entry[1]);
                }
            }
            System.out.println("Dictionary loaded successfully.");
            
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found: " + filepath);
        }
    }
    
    // save dictionary data to the file
    public void saveToFile(String filepath) {
        try (PrintWriter writer = new PrintWriter(new File(filepath))) {
            for (Map.Entry<String, String> entry : words.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
            System.out.println("Dictionary saved successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error saving dictionary: " + e.getMessage());
        }
    }

    // add a word
    public synchronized String addWord(String word, String meaning) {
        if (words.containsKey(word)) {
            return "E&Word already exists";
        } else {
            words.put(word, meaning);
            return "S&Word added successfully";
        }
    }

    // delete a word
    public synchronized String removeWord(String word) {
        if (words.containsKey(word)) {
            words.remove(word);
            return "S&Word removed successfully";
        } else {
            return "E&Word not found";
        }
    }

    // update a word
    public synchronized String updateWord(String word, String newMeaning) {
        if (words.containsKey(word)) {
            words.put(word, newMeaning);
            return "S&Word updated successfully";
        } else {
            return "E&Word not found";
        }
    }

    // search a word
    public synchronized String searchWord(String word) {
        if (words.containsKey(word)) {
            return words.get(word);
        } else {
            return "E&Word not found";
        }
    }
    
    
}
