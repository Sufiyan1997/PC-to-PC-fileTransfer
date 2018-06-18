/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author ganis
 */
public class ClientRequestHandler implements Runnable{
    private Thread t;
    private String filename;
    public ClientRequestHandler(int count,String filename) {
        this.filename = filename;
        t = new Thread(this,String.valueOf(count));
        t.start();
    }
    
    @Override
    public void run() {
        try {
            String host = "192.168.0.106";
            int port = 5000;
            byte buffer[] = new byte[1024];
            
            FileInputStream fileInputStream = new FileInputStream(filename);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInputStream);
            
            //send filename
            Socket server = new Socket(host, port);
            OutputStream outToServer = server.getOutputStream();
            BufferedOutputStream bufferedOutputToServer = new BufferedOutputStream(outToServer);
            File tmp = new File(filename);
            bufferedOutputToServer.write((tmp.getName()+"\n").getBytes());
            System.out.print("sent");
            
            //Send actual file
            while(bufferedInput.available()>0){
                bufferedInput.read(buffer);
                bufferedOutputToServer.write(buffer);
            }
            
            bufferedInput.close();
            bufferedOutputToServer.close();
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
