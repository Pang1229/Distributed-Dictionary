/*
   Name: Xiaoyu Pang
   User Name: XIAPANG
   Student Number: 1224627
*/
package Client;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ClientGUI implements Runnable{
    private JFrame clientInterface;
    private JTextField wordTextField;
    private JTextArea resultTextArea;
    private static DictionaryClient client = new DictionaryClient();
    private String address;
    private int port;
    private JTextField meaningTextField;

    
    @Override
	public void run() {
    	EventQueue.invokeLater(() -> {
            try {
                ClientGUI window = new ClientGUI(address,port);
                window.clientInterface.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

    public ClientGUI(String host,int port_num) {
    	GUI();
    	address = host;
    	port = port_num;
    }

    private void GUI() {
        clientInterface = new JFrame();
        clientInterface.setTitle("Dictionary service");
        clientInterface.setBounds(100, 100, 450, 400);
        clientInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientInterface.getContentPane().setLayout(null);

        wordTextField = new JTextField();
        wordTextField.setBounds(108, 34, 257, 26);
        clientInterface.getContentPane().add(wordTextField);
        wordTextField.setColumns(10);
        wordTextField.setForeground(Color.GRAY);
        wordTextField.setText("Type your word here");
        
        wordTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (wordTextField.getText().equals("Type your word here")) {
					wordTextField.setEditable(true);
					wordTextField.setText("");
					wordTextField.setForeground(Color.BLACK);
				}
			}
		});

        JLabel lblWord = new JLabel("Word");
        lblWord.setBounds(36, 38, 32, 16);
        clientInterface.getContentPane().add(lblWord);

        JLabel lblMeaning = new JLabel("Meaning");
        lblMeaning.setBounds(36, 93, 53, 16);
        clientInterface.getContentPane().add(lblMeaning);

        JLabel lblResult = new JLabel("Result");
        lblResult.setBounds(36, 172, 39, 16);
        clientInterface.getContentPane().add(lblResult);
        

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(82, 243, 117, 29);
        clientInterface.getContentPane().add(btnSearch);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(270, 243, 117, 29);
        clientInterface.getContentPane().add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(82, 305, 117, 29);
        clientInterface.getContentPane().add(btnUpdate);

        JButton btnRemove = new JButton("Remove");
        btnRemove.setBounds(270, 305, 117, 29);
        clientInterface.getContentPane().add(btnRemove);
        
                resultTextArea = new JTextArea();
                resultTextArea.setBounds(108, 156, 257, 71);
//                clientInterface.getContentPane().add(resultTextArea);
                resultTextArea.setEditable(false);
                JScrollPane sp = new JScrollPane(resultTextArea);
        		sp.setBounds(108, 156, 257, 71);
        		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        		clientInterface.getContentPane().add(sp);
                
                meaningTextField = new JTextField();
                meaningTextField.setBounds(108, 70, 257, 63);
                clientInterface.getContentPane().add(meaningTextField);
                meaningTextField.setColumns(10);
                meaningTextField.setForeground(Color.GRAY);
                meaningTextField.setText("Type the meaning of word here");
                
                meaningTextField.addMouseListener(new MouseAdapter() {
        			@Override
        			public void mouseClicked(MouseEvent e) {
        				if (meaningTextField.getText().equals("Type the meaning of word here")) {
        					meaningTextField.setEditable(true);
        					meaningTextField.setText("");
        					meaningTextField.setForeground(Color.BLACK);
        				}
        			}
        		});

        // Add action listeners for buttons
        btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
				//add event for Search button
				String word = wordTextField.getText();
				if(word.isEmpty())
				{
					resultTextArea.setText("Please type a word.");
					return;
				}
				
				String displayText = client.searchAction(word,address,port);
				if(displayText.startsWith("E&"))
				{
					resultTextArea.setText(displayText.substring(2));
					wordTextField.setText("");
				}
				else
				{
					wordTextField.setText("");
					resultTextArea.setText(displayText);
				}
			}
			
		});
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String word = wordTextField.getText().trim();
                String meaning = meaningTextField.getText().trim();
                if(word.isEmpty() || meaning.isEmpty()) {
                    resultTextArea.setText("Please enter both the word and its meaning.");
                    return;
                }

                String displayText = client.addAction(word, meaning, address, port);
                if(displayText.startsWith("S&")) {
                    resultTextArea.setText("Word added successfully.");
                } else if(displayText.startsWith("E&")) {
                    resultTextArea.setText(displayText.substring(2));
                } else {
                    resultTextArea.setText("Unexpected error.");
                }
                wordTextField.setText("");
                meaningTextField.setText("");
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String word = wordTextField.getText().trim();
                String newMeaning = meaningTextField.getText().trim();
                if(word.isEmpty() || newMeaning.isEmpty()) {
                    resultTextArea.setText("Please enter both the word and its new meaning.");
                    return;
                }

                String displayText = client.updateAction(word, newMeaning, address, port);
                if(displayText.startsWith("S&")) {
                    resultTextArea.setText("Word updated successfully.");
                } else if(displayText.startsWith("E&")) {
                    resultTextArea.setText(displayText.substring(2));
                } else {
                    resultTextArea.setText("Unexpected error.");
                }
                wordTextField.setText("");
                meaningTextField.setText("");
            }
        });
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String word = wordTextField.getText().trim();
                if(word.isEmpty()) {
                    resultTextArea.setText("Please enter the word you want to remove.");
                    return;
                }

                String displayText = client.removeAction(word, address, port);
                if(displayText.startsWith("S&")) {
                    resultTextArea.setText("Word removed successfully.");
                } else if(displayText.startsWith("E&")) {
                    resultTextArea.setText(displayText.substring(2));
                } else {
                    resultTextArea.setText("Unexpected error.");
                }
                wordTextField.setText("");
                meaningTextField.setText("");
            }
        });
    }
}


