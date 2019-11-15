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
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interface extends Remote {

    String startup() throws RemoteException;

    String[] add(int val1, int val2) throws RemoteException;

    String[] minus(int val1, int val2) throws RemoteException;

    String[] divide(int val1, int val2) throws RemoteException;

    String[] multiply(int val1, int val2) throws RemoteException;
}
