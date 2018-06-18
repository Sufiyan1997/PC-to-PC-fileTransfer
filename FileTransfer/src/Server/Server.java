/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
/**
 *
 * @author ganis
 */
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            InetAddress myIP = InetAddress.getLocalHost();
            InetSocketAddress address = new InetSocketAddress(myIP,5000);
            int count = 0;
            serverSocket.bind(address);
            while(true){
                System.out.println("Waiting for some client to get connected...");
                Socket client = serverSocket.accept();
                System.out.println("Someone connected....");
                //dispatch a new thread
                TransferRequestHandler handler = new TransferRequestHandler(count, client);
                count++;
                //writing OK
                /*OutputStream clientOutputStream = client.getOutputStream();
                BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(clientOutputStream));
                clientWriter.write("OK");
                System.out.println("OK Sent....");*/
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
