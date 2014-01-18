
package javaapplication1;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

class osend
{
    public static void main(String ar[])throws Exception
    {
        try
        {
        Socket s = new Socket("localhost",2000);
        Scanner sc=new Scanner(System.in);
        System.out.println("One Bit Sliding Window Protocol\n\nTransmitter Started");     
        System.out.println("Enter the Data to be Transmitted");
        String input  = sc.next();
        DataOutputStream ots = new DataOutputStream(s.getOutputStream());
        BufferedReader ifs = new BufferedReader(new InputStreamReader(s.getInputStream()));
        int j=0;
        int n=input.length();
        char[] data=input.toCharArray();

        ots.writeBytes(n+"\n");
        ots.writeBytes(data[j]+"\n");
                    
        while(j<n)
        {
            int ACK=Integer.parseInt(ifs.readLine());
            
            switch(ACK)
            {

                case 0:
                        timeOut();
                        System.out.println("\nNEGATIVE ACK received for "+data[j]);
                        System.out.print("Sending "+data[j]+" again");
                        ots.writeBytes(data[j]+"\n");
                     break;

                case 1:
                        timeOut();
                        System.out.println("\nPOSITIVE ACK received for "+data[j++]);
                        if(j<n)
                        {
                            System.out.print("Sending Next bit "+data[j]);
                            ots.writeBytes(data[j]+"\n");
                        }
                        else
                        {
                            System.out.println("\nEntire Data has been Transmitted.");
                            s.close();
                        }
                    break;

                case 2:
                case 3: 
                        timeOut();   
                        System.out.println("\nTimer Time out\nSo, the initial ACK might been dropped OR\nThe DATA might have dropped");
                        System.out.println("And Resending the Data : "+data[j]);
                        ots.writeBytes(data[j]+"\n");
                    break;
            }  
            
        }
        }
        catch(Exception e)
        {
            System.out.print("Exception...............");
        }
    }
    
    
    public static void timeOut()
  	{
    	System.out.print("\nPlease Wait");
    	for(int j=0;j<5;j++)
    	{
      		System.out.print(".");
      		try
      		{
        		Thread.sleep(500);
      		}
      		catch(Exception e)
      		{}
   		}
    	System.out.println();
  	}
    
}
