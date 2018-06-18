/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ganis
 */
public class TransferRequestHandler implements Runnable{
    private Thread t;
    private Socket client;
    private String filename;
    private BufferedReader clientReader;
    private FileOutputStream fileOutput;
    private byte[] buffer = new byte[1024];
    public TransferRequestHandler(int requestNo,Socket client) {
        this.client = client;
        t = new Thread(this, String.valueOf(requestNo));
        t.start();
    }
    
    @Override
    public void run() {
        InputStream clientInputStream = null;
        try {
            //Reading filename
            clientInputStream = client.getInputStream();
            clientReader = new BufferedReader(new InputStreamReader(clientInputStream));
            filename = clientReader.readLine();
            System.out.println("It wants to transfer "+filename+"...");
            
            //Reading actual file
            fileOutput = new FileOutputStream("./received/"+filename);
            BufferedOutputStream bufferedOutputToFile = new BufferedOutputStream(fileOutput); 
            BufferedInputStream bufferedInput = new BufferedInputStream(clientInputStream); 
            while(bufferedInput.available()>0){
                bufferedInput.read(buffer);
                bufferedOutputToFile.write(buffer);
            }
            fileOutput.close();
            System.out.println("File Read SUCCESSFULLY!!...");
                
            //closing client input
            clientInputStream.close();
            //clientOutputStream.close();
            System.out.println("Connection closed...");
        } catch (IOException ex) {
            Logger.getLogger(TransferRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                clientInputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(TransferRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
