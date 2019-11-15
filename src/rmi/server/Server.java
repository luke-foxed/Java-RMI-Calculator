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
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements Interface {


    private static final long serialVersionUID = -3363284187844134696L;

    public Server() throws RemoteException {
        super();
    }

    public String add(int val1, int val2) {
        String result = String.valueOf(val1 + val2);
        return result;
    }

    public String minus(int val1, int val2) throws RemoteException {
    String result = String.valueOf(val1 - val2);
    return result;
    }

    public static void main(String args[]) {
        try {

            Server server = new Server();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("HelloWorld", server);
            System.out.println("Server bound in registry");
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.exit(0);
              }
          });
    }
}
