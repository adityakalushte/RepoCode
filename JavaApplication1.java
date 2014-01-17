
package javaapplication1;

import java.util.Random;
import java.util.Scanner;

/**
 *@author Akhil Saokar
 * SAP : 60004100045
 * ID  : RM-2010045
 */

class Distance_Vector_Routing_1 
{
    static Scanner sc=new Scanner(System.in);
            
    public static void main(String[] args)
    {
        // TODO code application logic here
        System.out.print("Enter the number of routers : ");
        int n=sc.nextInt();
        Network obj=new Network(n);
        obj.addNetwork();
        System.out.print("Enter Router for which new table needs to be made : ");
        char source =sc.next().charAt(0);
        obj.DVR(source);
    }
}
class Network
{
    static Scanner sc=new Scanner(System.in);
    boolean adjacency[][];
    int neigh[],distance[],previous[],delaySource[],routingtable[][];
    Random generateNumber;
    public Network(int n) 
    {
        adjacency=new boolean[n][n];
        distance=new int[n];
        previous=new int [n];
        generateNumber=new Random();
    }
    public void addNetwork()
    {
        char n;
        do
        {
            System.out.print("Enter a new path between two routers : ");
            String path=sc.next().toUpperCase();
            int src=path.charAt(0)-65;
            int dest=path.charAt(1)-65;
            adjacency[src][dest]=true;
            adjacency[dest][src]=true;
            System.out.print("Enter 'y' or 'Y' to add a new path : ");
            n=sc.next().charAt(0);
        }while(n=='y' || n=='Y');
    }
    public void DVR(int source)
    {
        source=Character.toUpperCase(source)-65;

        int neighbourCount= getNeighbourCount(source);
        
        routingtable=new int[adjacency.length][neighbourCount];
        int temp[]=new int[neighbourCount];
        delaySource=new int[neighbourCount];
        neigh=new int[neighbourCount];
        
        int z=0;
        for(int i=0;i<adjacency.length;i++)
            if(adjacency[source][i])
            {
                neigh[z]=i;
                z++;
            }

        generateRoutingTable(neighbourCount);
        
        generateDelayTable(adjacency.length,source);
        
       for(int i=0;i<adjacency.length;i++)
        {
            if(i==source)
            {
                previous[i]=-20;  //for blank space (ascii value of '-' :45[65-20] )
                distance[i]=0;
            }
            else
            { 
                for(int j=0;j<neighbourCount;j++)
                {
                    temp[j]=delaySource[j]+routingtable[i][j];
                }
                int min=temp[0];
                int index=0;
                 for(int k=1;k<temp.length;k++)
                {
                   if(min>temp[k])
                    {
                        min=temp[k];
                        index=k;
                    }
                }
                previous[i]=neigh[index];
                distance[i]=min;
            }
        }
        
        displayRoutingTable(neighbourCount,source); 
        System.out.println("DELAY from "+(char)(source+65)+" : ");
        for(int i=0;i<neighbourCount;i++)
        {
            System.out.print(delaySource[i]+" ");
        }
        System.out.println();
        
        System.out.println("New routing table for "+(char)(source+65)+" : ");
        System.out.println("To \tDelay \tvia");
        for(int i=0;i<adjacency.length;i++)
        {

            System.out.println((char)(i+65)+"\t"+distance[i]+"\t"+(char)(previous[i]+65));
        }
    }
    public int getNeighbourCount(int source)
    {
        int count=0;
        for(int i=0;i<adjacency.length;i++)
        {
            if(adjacency[source][i])
            {
                count++;
            }
        }
        return count;
    }
    public void generateRoutingTable(int count)
    {
        for(int i=0;i<adjacency.length;i++)
        {
            for(int j=0;j<count;j++)
            {
                if(i==neigh[j])
                    routingtable[i][j]=0;
                else
                    routingtable[i][j]=generateNumber.nextInt(50);
            }
        }
    }
    public void generateDelayTable(int n,int source)
    {
        int i=0;
        for(int j=0;j<n;j++)
        {
            if(adjacency[source][j])
            {
                System.out.print("Enter delay for "+(char)(source+65)+" to "+(char)(j+65)+" : ");
                delaySource[i]=sc.nextInt();
                i++;
            }
        }
    }
    
    public void displayRoutingTable(int count,int source)
    {
        System.out.println("ROUTING TABLE: ");
        for(int i=0;i<adjacency.length;i++)
        {
            if(adjacency[source][i])
            {
                System.out.print("\t"+(char)(i+65));
            }
        }
        System.out.println();
        for(int i=0;i<adjacency.length;i++)
        {
            System.out.print((char)(i+65));
            for(int j=0;j<count;j++)
            {
                    System.out.print("\t"+routingtable[i][j]);
            }
            System.out.println();
        }
    }
            
}
/*
 *  Output: 

Enter the number of routers : 12
Enter a new path between two routers : ab
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : aj
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : ae
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : bc
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : bg
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : cd
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : ce
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : dh
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : ef
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : fg
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : gh
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : jh
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : ei
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : hl
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : ij
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : jk
Enter 'y' or 'Y' to add a new path : y
Enter a new path between two routers : kl
Enter 'y' or 'Y' to add a new path : n
Enter Router for which new table needs to be made : j
Enter delay for J to A : 8
Enter delay for J to H : 12
Enter delay for J to I : 10
Enter delay for J to K : 6
ROUTING TABLE: 
    A   H   I   K
A   0   8   26  41
B   22  15  38  48
C   9   12  24  30
D   8   12  2   0
E   15  14  18  29
F   23  48  4   9
G   28  13  18  7
H   35  0   38  33
I   11  28  0   23
J   25  0   24  49
K   7   49  24  0
L   31  11  29  34
DELAY from J : 
8 12 10 6 
New routing table for J : 
To  Delay   via
A   8   A
B   27  H
C   17  A
D   6   K
E   23  A
F   14  I
G   13  K
H   12  H
I   10  I
J   0   -
K   6   K
L   23  H
 */