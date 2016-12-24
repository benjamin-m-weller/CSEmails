
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ParseCSJobEmails 
{
    public static void main(String[] args) 
    {
        String fileName=JOptionPane.showInputDialog("Please enter the file you would like to parse.");
        String output=FileInput.parseFile(fileName);
        ArrayList<String> getRidOfFullTime=FileInput.splitMe(output, "End Date:");
        
        //Just a short bit of code to remove all the fullTime only offers.
        int innitialSize=getRidOfFullTime.size();
        ArrayList<String> removeString=new ArrayList();
        
        for (String s:getRidOfFullTime)
        {
            if (s.contains("Full"))
            {
                removeString.add(s);
            }
        }
        
        for (String s: removeString)
        {
            getRidOfFullTime.remove(s);
        }
        
        //Now I need to get rid of any HTML.
        int counter=0;
        innitialSize=getRidOfFullTime.size();
        while(counter<innitialSize)
        {
            String cleanMe=getRidOfFullTime.remove(0);
            cleanMe=FileInput.removeCharactersBetween(cleanMe, '<', '>');
            getRidOfFullTime.add(cleanMe);
            counter++;
        }
        
        //This will print out all strings that are in the remaining arraylist.
        for (String s: getRidOfFullTime)
        {
            System.out.println(s+"\n");
        }
    }
    
}
