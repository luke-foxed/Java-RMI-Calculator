/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server;

/**
 *
 * @author lukef
 */
import java.awt.BorderLayout;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Server extends UnicastRemoteObject implements Interface {

    private static final long serialVersionUID = -3363284187844134696L;
    private Registry registry = null;

    private JFrame jFrame = new JFrame("Server");
    JTextArea serverLog = new JTextArea();

    public Server() throws RemoteException {
        super();
        constructFrame();

        try {
            registry = LocateRegistry.createRegistry(1099);
            registry.rebind("HelloWorld", this);
            System.out.println("Server bound in registry");
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void constructFrame() {

        JPanel panel = new JPanel();
        JPanel controlsPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        JButton clearLogsBtn = new JButton("CLEAR");
        JButton stopServerBtn = new JButton("STOP");
        JLabel header = new JLabel("SERVER LOGS");
        JScrollPane scrollPane = new JScrollPane(serverLog);
        
        scrollPane.setBounds(3, 3, 600, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.setLayout(boxlayout);
        panel.add(header);
        panel.add(scrollPane);
        
        controlsPanel.add(clearLogsBtn);
        controlsPanel.add(stopServerBtn);

        header.setAlignmentX(CENTER_ALIGNMENT);
        clearLogsBtn.setAlignmentX(CENTER_ALIGNMENT);
        clearLogsBtn.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                serverLog.setText("");
            }
        });

        stopServerBtn.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deregister(registry);
                } catch (NoSuchObjectException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jFrame.setResizable(false);
        jFrame.setTitle("Server");
        jFrame.setSize(700, 450);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(panel, BorderLayout.CENTER);
        jFrame.add(controlsPanel, BorderLayout.SOUTH);
        jFrame.setVisible(true);
    }

    public void deregister(Registry registry) throws NoSuchObjectException {
        UnicastRemoteObject.unexportObject(registry, true);
        System.exit(0);
    }

    public String startup() {
        try {
            serverLog.append("New Client at: " + getClientHost());
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Client connection established";
    }

    public void writeToServer(String operation, int val1, int val2, String result) {
        try {
            serverLog.append("\nRequest from: " + getClientHost()
                    + ", Data: [" + val1 + "," + val2 + "], Operation: "
                    + "\'" + operation + "\' | Response: " + result);
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] add(int val1, int val2) {
        String[] response = new String[2];
        response[0] = "Returning add operation from server";
        response[1] = String.valueOf(val1 + val2);
        writeToServer("+", val1, val2, response[1]);
        return response;
    }

    public String[] minus(int val1, int val2) throws RemoteException {
        String[] response = new String[2];
        response[0] = "Returning subtract operation from server";
        response[1] = String.valueOf(val1 - val2);
        writeToServer("-", val1, val2, response[1]);
        return response;
    }

    public String[] divide(int val1, int val2) {
        String[] response = new String[2];
        response[0] = "Returning divide operation from server";
        response[1] = String.valueOf(val1 / val2);
        writeToServer("/", val1, val2, response[1]);
        return response;
    }

    public String[] multiply(int val1, int val2) throws RemoteException {
        String[] response = new String[2];
        response[0] = "Returning multiply operation from server";
        response[1] = String.valueOf(val1 * val2);
        writeToServer("*", val1, val2, response[1]);
        return response;
    }

    public static void main(String args[]) throws RemoteException, ServerNotActiveException {
        new Server();
    }
}
