/*
   Name: Xiaoyu Pang
   User Name: XIAPANG
   Student Number: 1224627
*/
package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class ClientHandler extends Thread {
    private Socket socket;
    private Dictionary dictionary;
    private ServerGUI serverGUI; 

    public ClientHandler(Socket socket, Dictionary dictionary, ServerGUI serverGUI) {
        this.socket = socket;
        this.dictionary = dictionary;
        this.serverGUI = serverGUI; 
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] parts = inputLine.split("[&%]", 3);
                String command = parts[0];
                String word = parts.length > 1 ? parts[1] : "";
                String meaning = parts.length > 2 ? parts[2] : "";
                String response;

                switch (command) {
                    case "s":
                        response = dictionary.searchWord(word);
                        serverGUI.appendText("New client operation excuted: Search");
                        break;
                    case "a":
                        response = dictionary.addWord(word, meaning);
                        serverGUI.appendText("New client operation excuted: Add");
                        break;
                    case "r":
                        response = dictionary.removeWord(word);
                        serverGUI.appendText("New client operation excuted: Remove");
                        break;
                    case "u":
                        response = dictionary.updateWord(word, meaning);
                        serverGUI.appendText("New client operation excuted: Update");
                        break;
                    default:
                        response = "E&Invalid command";
                        break;
                }
                out.println(response);
            }
        } catch (IOException e) {
            serverGUI.appendText("Exception in ClientHandler: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                serverGUI.appendText("Error closing the client socket.");
            }
        }
    }
}



