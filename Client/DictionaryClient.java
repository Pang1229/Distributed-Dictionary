/*
   Name: Xiaoyu Pang
   User Name: XIAPANG
   Student Number: 1224627
*/
package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class DictionaryClient {

	static ClientGUI clientWindow;

    public String searchAction(String word, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            // initialize PrintWriter and BufferedReader
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send request to server
            out.println("s&" + word);

            // read the response
            String inMessage = in.readLine();
            if (inMessage == null) throw new Exception("E&Server sent an empty response.");          
            return inMessage;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection Error: " + e.getMessage(), "Client failed to connect to Server.", JOptionPane.ERROR_MESSAGE);
            return "E&Connection failed: " + e.getMessage();
        }
    }
    
    public String addAction(String word, String meaning, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
        	// initialize PrintWriter and BufferedReader
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send request to server
            out.println("a&" + word + "%" + meaning);

            // read the response
            String inMessage = in.readLine();
            if (inMessage == null) throw new Exception("E&Server sent an empty response.");
            return inMessage;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection Error: " + e.getMessage(), "Client failed to connect to Server.", JOptionPane.ERROR_MESSAGE);
            return "E&Connection failed: " + e.getMessage();
        }
    }
    
    
    public String removeAction(String word, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send request to server
            out.println("r&" + word);

            // read the response
            String response = in.readLine();
            return response != null ? response : "E&Server sent an empty response.";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection Error: " + e.getMessage(), "Client failed to connect to Server.", JOptionPane.ERROR_MESSAGE);
            return "E&Connection failed: " + e.getMessage();
        }
    }
    
    public String updateAction(String word, String newMeaning, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send request to server
            out.println("u&" + word + "%" + newMeaning);

            // read the response
            String response = in.readLine();
            return response != null ? response : "E&Server sent an empty response.";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection Error: " + e.getMessage(), "Client failed to connect to Server.", JOptionPane.ERROR_MESSAGE);
            return "E&Connection failed: " + e.getMessage();
        }
    }
    
    
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar DictionaryClient.jar <server-address> <server-port>");
            System.exit(1);
        }
        try {
            String hostname = args[0];
            int port = Integer.parseInt(args[1]);
            clientWindow = new ClientGUI(hostname, port);
            clientWindow.run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter the 'Server address' followed by the 'Server port'.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



