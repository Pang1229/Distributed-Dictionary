/*
   Name: Xiaoyu Pang
   User Name: XIAPANG
   Student Number: 1224627
*/
package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class DictionaryServer {
    static int port;
    static String filepath;
    static Dictionary dictionary = new Dictionary(); 
    private static ServerSocket serverSocket;
    private static boolean isStopped = false;
    private static ServerGUI serverGUI;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java DictionaryServer <port> <dictionary-file>");
            System.exit(1);
        }

        port = Integer.parseInt(args[0]);
        filepath = args[1];
        
        // load dictionary data
        dictionary.loadFromFile(filepath);

        try {
            serverSocket = new ServerSocket(port);         
            
            // start GUI
            serverGUI = new ServerGUI(() -> stopServer());
            serverGUI.display();
            serverGUI.appendText("Server listening on port " + port);
            while (!isStopped) {
                try {
                    Socket clientSocket = serverSocket.accept();                   
                    new ClientHandler(clientSocket, dictionary, serverGUI).start();
                } catch (IOException e) {
                    if(isStopped) {
                        serverGUI.appendText("Server Stopped.");
                        break;
                    }
                    throw new RuntimeException("Error accepting client connection", e);
                }
            }
        } catch (IOException e) {           
            serverGUI.appendText("Server could not listen on port: " + port + ". " + e.getMessage());
            e.printStackTrace();
        }
    }

    // a method to stop the server side
    public static void stopServer() {
        isStopped = true;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            dictionary.saveToFile(filepath);
            serverGUI.appendText("Dictionary data stopped and saved successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}

