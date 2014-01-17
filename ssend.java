
package javaapplication1;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

class ssend
{
    public static void main(String ar[])
    {
        try
        {
            Socket s = new Socket("localhost",2000);
        
            Scanner sc=new Scanner(System.in);
            DataOutputStream ots = new DataOutputStream(s.getOutputStream());
            BufferedReader ifs = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            System.out.println("Selective Repeat Sliding Window Protocol\n\nTransmitter Started");     
            
            System.out.println("Enter the Data size");
            int n=sc.nextInt();
            int data[]=new int[n];

            System.out.println("Enter the Data to be Transmitted");
            for(int i=0;i<n;i++)
            {
                data[i]=sc.nextInt();
            }

            System.out.println("Enter the Window Size.");
            int window  = sc.nextInt();
            
            int j=0;
            
            ots.writeBytes(n+"\n");
            ots.writeBytes(window+"\n");
            
            int temp[]=new int[window];
            System.arraycopy(data,0,temp,0,window);
            String sent=Arrays.toString(temp);
            System.out.println("\nSending new Frames:"+sent);
            ots.writeBytes(sent+"\n");
            ots.writeBytes(window+"\n");
            
            while(j<n)
            {
                int ACK=Integer.parseInt(ifs.readLine());
                
                switch(ACK)
                {
                    
                    case 0:
                        timeOut();
                        int y=Integer.parseInt(ifs.readLine());
                        System.out.println("\nNEGATIVE ACK received for frame no "+y);
                        sent="["+data[y]+"]";
                        System.out.println("Sending "+sent+" again");
                        ots.writeBytes(sent+"\n");
                        ots.writeBytes(1+"\n");
                    break;
                        
                    case 1:
                        timeOut();
                        if(j+window<n)
                        {
                            j+=window;
                            if(n-j<window)
                            {
                                window=n-j;  
                                temp=new int[window];  
                            }
                            System.arraycopy(data,j,temp,0,window);
                            sent=Arrays.toString(temp);
                            System.out.println("Positive ack received \nSending Next Frames "+sent);
                            ots.writeBytes(sent+"\n");
                            ots.writeBytes(window+"\n");
                        }
                        else
                        {
                            j=n;
                            System.out.println("\nEntire Data has been Transmitted.");
                            s.close();
                        }
                    break;
                    
                    case 2:
                    case 3:
                        timeOut();
                        System.out.println("\nTimer Time out\nSo, the initial ACK might been dropped OR\nThe DATA might have dropped");
                        System.out.println("And Resending the Data : "+sent);
                        ots.writeBytes(sent+"\n");
                        ots.writeBytes(window+"\n");
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
