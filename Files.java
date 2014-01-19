package javaapplication1;

import java.io.*;

class Files
{
   public static void main(String args[])
   {
       try 
       {
           //FileWriter fw=new FileWriter("xyz.txt",false);   //append is false
           FileReader fr=new FileReader("abc.txt");
           BufferedReader br=new BufferedReader(fr);
           String s="";
           
           while((s=br.readLine())!=null)
           {
              //fw.write(s+"\n");
               System.out.println(s);
           }
           
           fr.close();
           //fw.close();
           
           System.out.println("EXE");
       }
       catch (FileNotFoundException ex) 
       {
           System.out.println("FNE");
       } 
       catch (IOException ex) 
       {
           System.out.println("IOE");
       }
   }
}
