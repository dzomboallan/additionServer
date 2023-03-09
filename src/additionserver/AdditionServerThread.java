package additionserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class AdditionServerThread extends Thread{
    private int id;
    private static int totalConnections;
    private final int port = 8901;
    
    // declare a "general" socket
    private final Socket connection;
    
    // declare low level and high level objects for input
    private InputStream inStream;
    private DataInputStream inDataStream;
    
    // declare low level and high level objects for output
    private OutputStream outStream;
    private DataOutputStream outDataStream;
    
    // declare other variables
    private String client;
    private int first, second, sum;
    private boolean connected;
    
    public AdditionServerThread(Socket socketIn){
        connection = socketIn;
    }
    
   @Override
    public void run(){
        try{
            connected = true;
            System.out.println("Connection established");
            totalConnections++;
            id = totalConnections;
            
            // create an input stream from client
            inStream = connection.getInputStream();
            inDataStream = new DataInputStream(inStream);
            
            // create an outpout stream to the client
            outStream = connection.getOutputStream();
            outDataStream = new DataOutputStream(outStream);
            
            // wait for a string from the client
            client = inDataStream.readUTF();
            System.out.println("Address of client: "+client);
            
            while(connected){
                // read an integer from the client
                first = inDataStream.readInt();
                System.out.println("First number received from connection "+id+": "+first);
                
                // read an integer from the client
                second = inDataStream.readInt();
                System.out.println("Second number received from connection "+id+": "+second);
                
                sum = first + second;
                System.out.println("Sum returned to connection "+id+": "+sum);
                
                // send the sum to the client
                outDataStream.write(sum); 
            }
        }catch(IOException e){
            connected = false;
        }
    }   
}
