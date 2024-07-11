/*
   Name: Xiaoyu Pang
   User Name: XIAPANG
   Student Number: 1224627
*/
package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerGUI {
    private JFrame frame;
    private JLabel timeLabel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Runnable stopServer;
    private JScrollPane scrollPane;
    private JTextArea textArea;

    // be used to stop the server
    public ServerGUI(Runnable stopServer) {
        this.stopServer = stopServer;
    }

    /**
     * @wbp.parser.entryPoint
     */
    public void initialize() {
        frame = new JFrame("Dictionary Server");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        timeLabel = new JLabel("Time: " + dateFormat.format(new Date()), SwingConstants.LEFT);
        Timer timer = new Timer(1000, (e) -> timeLabel.setText("Time: " + dateFormat.format(new Date())));
        timer.start();

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(timeLabel, BorderLayout.WEST);

        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        
                JButton stopButton = new JButton("Stop Server");
                bottomPanel.add(stopButton, BorderLayout.CENTER);
                
                scrollPane = new JScrollPane();
                
                frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                
                textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setBounds(108, 156, 257, 71);
                JScrollPane sp = new JScrollPane(textArea);
        		sp.setBounds(108, 156, 257, 71);
        		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        		frame.getContentPane().add(sp);
        		
        		
                stopButton.addActionListener((ActionEvent event) -> {
                    stopServer.run();           
                    JOptionPane.showMessageDialog(frame, "Server stopped successfully.");
                    frame.dispose(); // Close the GUI after clicking the button
                });
    }

    public void display() {
        SwingUtilities.invokeLater(() -> {
            initialize();
            frame.setVisible(true);
        });
    }
    
	 // add text to textArea
    public void appendText(String text) {
    	SwingUtilities.invokeLater(() -> {
    		textArea.append(text + "\n"); 
    	});
 }

}
