
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

//I believe that this class will be my try at creating a standard text processing arsenal.
public class FileInput 
{
    public static ArrayList<String> skipList=null;
    public static String startSequence=null;
    public static String endSequence=null;
    
    public static String parseFile(String fileName)
    {
        String returned="";
        
        if (startSequence==null)
        {
            startSequence=JOptionPane.showInputDialog("Please enter some criteria that you would like to make start the search.");
        }
        
        if (endSequence==null)
        {
            endSequence=JOptionPane.showInputDialog("Please enter some criteria that you would like to make end the search.");
        }
        
         //Going to get the skip list....
        if (skipList==null) //If we are running multiple similar files this is useful and needed.
        {
            getSkipListPrompt(); //This will get the list of lines that in the middle of the start and end point would need to be skipped.
        }
        
        //I'll make my scanner out here
        Scanner lineInput=null;
        while (true)
        {
            try
            {
                lineInput=new Scanner(new File(fileName));
                break; //If it makes it past the first line it will be fine to break out of the loop.
            }
            catch (FileNotFoundException fnfe)
            {
                fileName=JOptionPane.showInputDialog("Looks like the file you entered doesn't exist, try again.");
            }
        }
        
        boolean collectNow=false;
        while(lineInput.hasNextLine())
        {
            String line=lineInput.nextLine();
            if (line.contains(startSequence))
            {
                returned+=line+"\n";
                collectNow=true;
                continue;
            }
            else if (line.contains(endSequence))
            {
                collectNow=false;
            }
            else if (collectNow && skipList!=null)
            {
                boolean flagForContainingSkip=false;
                for (String s: skipList) //Wondering if this will work...
                {
                    if (line.contains(s))
                    {
                        flagForContainingSkip=true;
                    }
                }
                
                if (!flagForContainingSkip)
                {
                    returned+=line+"\n";
                }
            }
            else if (collectNow && skipList==null)
            {
                returned+=line+"\n";
            }
        }
        
        return returned; 
    }
    
    /**
     * This is a helper method for the parseFile method, simply gets the skip 
     * list for lines that must be skipped between the starting of the recored lines and the ending of the recorded lines.
     */
    public static void getSkipListPrompt()
    {
        skipList=new ArrayList();
        
        String addToSkip="";
        
        while(true)
        {
            addToSkip=JOptionPane.showInputDialog("Please enter criteria that would "
                    + "make the line need to be skipped between the start and end point."
                    + "\nOr type \"Quit\" to exit.");
            if (addToSkip.equals("Quit") || addToSkip.equalsIgnoreCase("quit"))
            {
                if (skipList.size()==0)
                {
                    skipList=null; //This is used in the selection for parsing the file.
                }
                
                break; //This means that they want to be done...
            }
            else
            {
                skipList.add(addToSkip);
            }
        }
    }
    
    /**
     * In many cases you will have a file that contains multiple instances of information that you wish to recover
     * this method takes the String containing multiple instances and instead of splitting this into a string array
     * around a certain set of characters, it splits it around a certain line number.
     * @param containsMultipleInstances This is the string that will be split.
     * @param splitAfterThisLine This is the number of lines that will make up each line.
     * @return The array of all instances.
     */
    public static String[] splitMe(String containsMultipleInstances, int splitAfterThisLine)
    {
        ArrayList<String> container=new ArrayList();
        Scanner lineParse=new Scanner(containsMultipleInstances);
        String addMe="";
        int counter=0;
        while(lineParse.hasNextLine())
        {
            if (counter==splitAfterThisLine)
            {
                counter=0;
                //I don't think it's overkill to trim the string here so I'm going to do that.
                addMe=addMe.trim();
                container.add(addMe);
            }
            else
            {
                addMe+=lineParse.nextLine();
                counter++;
            }
        }
        return (String []) container.toArray();
    }
    
    /**
     * This method will split a string into separate instances if a certain line contains the end of instance sequence.
     * This split will be INCLUSIVE of the last line (AKA the line that contains the ending sequence)
     * @param containsMultipleInstances This is the string that will be split.
     * @param endOfInstance This is the sequence that will cause the string to be split.
     * @return The array of all instances.
     */
    public static ArrayList<String> splitMe(String containsMultipleInstances, String endOfInstance)
    {
        ArrayList<String> container=new ArrayList();
        Scanner lineParse=new Scanner(containsMultipleInstances);
        String addMe="";
        while(lineParse.hasNextLine())
        {
            String line=lineParse.nextLine();
            
            if (line.contains(endOfInstance))
            {
                addMe+=line;
                //I don't think it's overkill to trim the string here so I'm going to do that.
                addMe=addMe.trim();
                container.add(addMe);
                addMe=""; //This just resets the string 
            }
            else
            {
                addMe+=line +"\n";
            }
        }
        return container;
    }
    
    public static String removeCharactersBetween(String cleanMe, char startChar, char endChar)
    {
        char[] toBeCleaned=cleanMe.toCharArray();
        String cleanString="";
        boolean addMe=true;
        
        for(char c:toBeCleaned)
        {
            if (startChar==c)
            {
                addMe=false;
            }
            else if (endChar==c)
            {
                addMe=true; 
                continue;
            }
            else if (addMe) 
            {
                cleanString+=c+"";
            }
        }
        return cleanString;
    }
}
