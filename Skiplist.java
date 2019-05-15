//package hw02 ;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;
class Skiplistnode<T extends Comparable<T>,Z> {
   //declare pointers for node
       private Skiplistnode<T,Z>     up;
          private Skiplistnode<T,Z>  down;
         private Skiplistnode<T,Z>   forward;
           private Skiplistnode<T,Z> backward;
    private T data;
    private Z value;
    private int row;

    public Skiplistnode(T data, Z value, int row) {
        this.data = data;
        this.value = value;
        this.row = row;
    }

    @Override
    public String toString() {
         StringBuilder printall = new StringBuilder(); 
       Skiplistnode<T,Z> bottom = this;
        while (
                bottom.getDown() != null) {
                 bottom = bottom.getDown();
        }
   
        for (Skiplistnode<T,Z> node = bottom; node != null; node = node.getUp()) {
           
           if(node.getData()!=null)
           {
               printall.append(" ");
               printall.append(node.getData().toString());
          
           } 
          if(!printall.toString().trim().isEmpty())
          {
              printall.append(";");
              printall.append(" ");
          } 
        }
        if (bottom.forward != null) {
              printall.append('\n').append(bottom.forward.toString());
        }
        return printall.toString();
    }

    
     public void search1(T data) {
        if (forward != null) {
            int x = forward.getData().compareTo(data);

            if (x == 0) {
                System.out.print(data+ " found");
            } else if (x < 0) {
                
                        forward.search1(data);
            } else if (down != null) {
               
                        down.search1(data);
            } else {
               
             System.out.print(data+ " NOT FOUND");
            }
        } else if (down != null) {
           
                    down.search1(data);
        } else {
            
       }
    }
    public void search2(T data) {
        
        //if forward is not null
        if (forward != null) {
            int x = forward.getData().compareTo(data);
            //equals, then it will be deleted 
            if (x == 0) {
                System.out.println(data+ " deleted");
            } 
            else if (x < 0) {
                
                        forward.search2(data);
            } 
            else if (down != null) {
               
                        down.search2(data);
            } 
            else {
               
             System.out.println(data+ " integer not found - delete not successful");
             
            }
        } 
        //down is null then delete
        else if (down != null) {
            
                    down.search2(data);
        } 
        else {
            
           System.out.println(data+" integer not found - delete not successful");
       }
    }

   
// Getters and setters for node
    public T getData() { return data;}
public Z getValue() {return value;}
public void setBackward(Skiplistnode node) {   backward = node; }
public void setForward(Skiplistnode node) { forward = node; }
public void setDown(Skiplistnode node) { down = node; }
public void setUp(Skiplistnode node) { up = node; }
public Skiplistnode<T,Z> getUp() {return up;}
 public Skiplistnode<T,Z> getDown() {return down;}
public Skiplistnode<T,Z> getBackward() { return backward; }
public Skiplistnode<T,Z> getForward() {return forward; }
public int getRow() {  return row;}

 public void insertion(T data, Z value, int row,Skiplistnode<T,Z> parent) {
       // check for duplicates, if there are return
        if(search(data)!=null)
        {
            return;
            
        }
        
        if (this.row <= row && (forward == null || forward.getData().compareTo(data) > 0)) {
            
            Skiplistnode<T, Z> newNode = new Skiplistnode<T, Z>(data, value, this.row);
            
           //if foward is not null
            if (forward != null) {
                forward.setBackward(newNode);
                newNode.setForward(forward);
            }
            
            forward = newNode;
            newNode.setBackward(this);
           
            if (parent != null) {
                newNode.setUp(parent);
                parent.setDown(newNode);
            }
            if (down != null) {
                down.insertion(data, value, row, newNode);
            }
        } 
        else if (forward != null && forward.getData().compareTo(data) < 0) {
            forward.insertion(data, value, row, parent);
        } 
       
        else if (down != null) {
            down.insertion(data, value, row, parent);
        }
    }
 public Skiplistnode<T, Z> search(T data) {
        if (forward != null) {
            int x = forward.getData().compareTo(data);

            if (x == 0) {
                return forward;
            } 
            else if (x < 0) {
                return forward.search(data);
            }
             else if (down != null) {
                return down.search(data);
            } 
           
        } 
        
        else if (down != null) {
            return down.search(data);
        } 
            return null;
            
     }
    
}



 class SkipList1<T extends Comparable<T>,Z> {
    private Skiplistnode<T, Z> head = new Skiplistnode<T, Z>(null, null, 0);
    final static Random random = new Random();
           
@Override
    public String toString() {
        return head.toString();
    } 
    
    public void insertion(T data, Z value) {
        int row = 0; 
        
        //keep increasing levels if true
        while (random.nextInt() % 2 ==1) {
            row++;
        }
        while (head.getRow() < row) {
           Skiplistnode<T, Z> temp = new Skiplistnode<T, Z>(null, null, head.getRow() + 1);
            head.setUp(temp);
            temp.setDown(head);
            head = temp;
        }
        head.insertion(data, value, row, null);
    }

     public void insertion1(T data, Z value) {
        int row = 0;
        // Random random = new Random();
        //keep increasing levels if true
        while (random.nextInt() % 2 ==1) {
            row++;
        }
        while (head.getRow() < row) {
           Skiplistnode<T, Z> temp = new Skiplistnode<T, Z>(null, null, head.getRow() + 1);
            head.setUp(temp);
            temp.setDown(head);
            head = temp;
        }

        head.insertion(data, value, row, null);
    }
   
    public void delete(T data) {
        // if found then number  will be deleted
        if(true)
       {
            head.search2(data);
       }
        for (Skiplistnode<T,Z> node = head.search(data); node != null; node = node.getDown()) {
            node.getBackward().setForward(node.getForward());
            if (
                    node.getForward() != null) {
                    node.getForward().setBackward(node.getBackward());
            }
         
        }
            //delete at all levels
        while (head.getForward() == null) {
            head = head.getDown();
            head.setUp(null);
        }
        
    }
  public void search1(T data) {
         
                head.search1(data);
    }
      
    }








 class SkipList<T extends Comparable<T>,Z> {
    private Skiplistnode<T, Z> head = new Skiplistnode<T, Z>(null, null, 0);
    final static Random random = new Random(42);
           
@Override
    public String toString() {
        return head.toString();
    } 
    
    public void insertion(T data, Z value) {
        int row = 0; 
        
        //keep increasing levels if true
        while (random.nextInt() % 2 ==1) {
            row++;
        }
        while (head.getRow() < row) {
           Skiplistnode<T, Z> temp = new Skiplistnode<T, Z>(null, null, head.getRow() + 1);
            head.setUp(temp);
            temp.setDown(head);
            head = temp;
        }

        head.insertion(data, value, row, null);
    }

     public void insertion1(T data, Z value) {
        int row = 0;
         Random random = new Random();
        //keep increasing levels if true
        while (random.nextInt() % 2 ==10) {
            row++;
        }
        while (head.getRow() < row) {
           Skiplistnode<T, Z> temp = new Skiplistnode<T, Z>(null, null, head.getRow() + 1);
            head.setUp(temp);
            temp.setDown(head);
            head = temp;
        }

        head.insertion(data, value, row, null);
    }
   
    public void delete(T data) {
        // if found then number  will be deleted
        if(true)
       {
            head.search2(data);
       }
        for (Skiplistnode<T,Z> node = head.search(data); node != null; node = node.getDown()) {
            node.getBackward().setForward(node.getForward());
            if (
                    node.getForward() != null) {
                    node.getForward().setBackward(node.getBackward());
            }
         
        }
            //delete at all levels
        while (head.getForward() == null) {
            head = head.getDown();
            head.setUp(null);
        }
        
    }
  public void search1(T data) {
         
                head.search1(data);
    }
      
    }
    
    public class Hw02
    {
    public static void main(String[] args)throws FileNotFoundException, IOException {
        SkipList<Integer, Integer> myskiplist = new SkipList<Integer, Integer>();
     
          String arraystrings,firstpart= "f",secondpart;
     String filename= args[0];
     
      // First reader for file 
      FileReader fr = new FileReader(filename);
      //Second reader to print contains  
       FileReader fr2 = new FileReader(filename);
    // The two required buffered Readers
        BufferedReader br2 = new BufferedReader(fr2);
       BufferedReader br = new BufferedReader(fr);
    int number=0;
    //if(args.length==1)
    if(args.length == 1)
    {
    System.out.println("For the input file named " + filename);
                        System.out.println("With the RNG unseeded,");
   //Parsing over the txt file
    while((arraystrings = br.readLine()) != null)
    {
  int x =(arraystrings.length()!=1)?1:0; 
   
   if(x==1) {
// separate them by a space
String[] parts = arraystrings.split("\\s");
    
  // the two parts separated 1 and 2
firstpart = parts[0]; 
secondpart = parts[1]; 
 number = Integer.parseInt(secondpart);

    }


else if(x==0)
  {
      // if only one character in string then part 1 is the whole string
      firstpart =arraystrings;
   }


switch(firstpart){
case "i":
            myskiplist.insertion(number, number);
              continue;
            
case "s":
            
              
             myskiplist.search1(number);
             System.out.println();
            continue;
case "d":
            
           
           myskiplist.delete(number);
           
           continue;
case "p":
   
    System.out.println("the current Skip List is shown below: ");
                        //System.out.print("---infinity");
  String s = "---infinity";  
    System.out.print(s.trim());
                       
                        System.out.print(myskiplist);
                        System.out.println("\n+++infinity");
                        System.out.println("---End of Skip List---");
          ;
            continue;
case"q":
          
        System.exit(0);
          
}
} 
br.close();
    }
    
    else if(args.length == 2)
    {  
        SkipList1<Integer, Integer> myskiplist1 = new SkipList1<Integer, Integer>();
    System.out.println("For the input file named " + filename);
                        System.out.println("With the RNG unseeded,");
   //Parsing over the txt file
    while((arraystrings = br.readLine()) != null)
    {
  int x =(arraystrings.length()!=1)?1:0; 
   
   if(x==1) {
// separate them by a space
String[] parts = arraystrings.split("\\s");
    
  // the two parts separated 1 and 2
firstpart = parts[0]; 
secondpart = parts[1]; 
 number = Integer.parseInt(secondpart);

    }


else if(x==0)
  {
      // if only one character in string then part 1 is the whole string
      firstpart =arraystrings;
   }


switch(firstpart){
case "i":
            myskiplist1.insertion1(number, number);
              continue;
            
case "s":
            
              
             myskiplist1.search1(number);
             System.out.println();
            continue;
case "d":
            
           
           myskiplist1.delete(number);
           
           continue;
case "p":
   
    System.out.println("the current Skip List is shown below: ");
                       
  String s = "---infinity";  
    System.out.print(s.trim());
                       
                        System.out.print(myskiplist1);
                        System.out.println("\n+++infinity");
                        System.out.println("---End of Skip List---");
          ;
            continue;
case"q":
           
        System.exit(0);
          
}
} 
br.close();
    
    }
      }
      
  
       
}
    
    
    
    
    
    
    
    
    
    

