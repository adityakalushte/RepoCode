
package javaapplication1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

class srec
{
    public static void main(String[] args) throws Exception 
    {
        try
        {
        Random r=new Random();
        ServerSocket ss = new ServerSocket(2000);
        
        System.out.println("Receiver Waiting for Connection.");
        
        Socket s = ss.accept();
        
        System.out.println("\nConnection Establihed.");
        System.out.println("\nWaiting for DATA.");
        
        Scanner sc=new Scanner(System.in);
        DataOutputStream otc = new DataOutputStream(s.getOutputStream());
        BufferedReader ifc = new BufferedReader(new InputStreamReader(s.getInputStream()));
        
        int n=Integer.parseInt(ifc.readLine());
        int winsize=Integer.parseInt(ifc.readLine());
        int j=0;

        while(j<n)
        {
            String rec=ifc.readLine();
            winsize=Integer.parseInt(ifc.readLine());
            System.out.println("\nThe Received Frames are : "+rec);
            
            switch(r.nextInt(4))
            {

               case 0: 
                    otc.writeBytes("0\n");
                    int x=j+r.nextInt(winsize);
                    System.out.println("Frame Damaged NEGATIVE ACK sent for frame no."+x);
                    otc.writeBytes(x+"\n");
               break;
                  
                case 1:
                    otc.writeBytes("1\n");
                    System.out.println("\nPOSITIVE ACK sent");
                    j+=winsize;
                    break;
                    
                case 2:
                    otc.writeBytes("2\n");
                    System.out.println("ACK Dropped signal sent");
                    break;
                    
                case 3:
                    otc.writeBytes("3\n");
                    System.out.println("Packet Dropped signal sent");
                    break;

            }  
        
        }
        
        System.out.println("\nEntire Data has been received.");
        s.close();
        }
        catch(Exception e)
        {
            System.out.print("Exception...............");
        }
    }
}
