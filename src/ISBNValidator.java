import java.util.ArrayList;
import javax.swing.*;
import java.io.File;
import java.util.Scanner;


/**
 * Validates ISBN numbers from an imported file that the user chooses
 * @author Kyngston Gaddy
 * @version 1.23.2024
 */
public class ISBNValidator {
    private String[] validNums;
    private String[] invalidNums;
    private ArrayList<String> importedNums;
    private JFrame chooserFrame;
    private String filename;
    // simple constructor; initializes arrays
    public ISBNValidator() {
        importedNums = new ArrayList<>();
        chooserFrame = new JFrame();
        chooserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseFile();
        chooserFrame.dispose();
    }

    private void chooseFile()   {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(new File("./isbn_files").getPath()));
        int result = chooser.showOpenDialog(chooserFrame);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            filename = file.toString().substring(file.toString().lastIndexOf("/") + 1);
        }
    }

    /**
     * imports .dat file, calls isValidISBN method and stores Strings into corresponding arrays
     */
    public void importData() {
        try {
            Scanner in = new Scanner(new File("isbn_files/" + filename));
            while(in.hasNext()) {
                importedNums.add(in.nextLine().trim());
            }
            in.close();
            validNums = new String[importedNums.size()];
            invalidNums = new String[importedNums.size()];
            for(int i = 0;  i < importedNums.size(); i++) {
                if(isValidISBN(importedNums.get(i)))
                    validNums[i] = importedNums.get(i);
                else
                    invalidNums[i] = importedNums.get(i);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * determines validity of supplied ISBN number; called inside importData
     * @param isbn, the string for the isbn
     * @return whether the isbn number is valid
     */
    public boolean isValidISBN(String isbn) {
        int prefix = Integer.parseInt(isbn.split("-")[0]);
        if (prefix != 978 && prefix != 979)
            return false;
        else {
            for(int i = 0; i < isbn.length(); i++) {
                String temp = isbn.replaceAll("-", "");
                if((temp.charAt(0) + (3 * temp.charAt(1)) + temp.charAt(2) + (3 * temp.charAt(3)) + temp.charAt(4) + (3 * temp.charAt(5)) + temp.charAt(6) + (3 * temp.charAt(7)) + temp.charAt(8) + (3 * temp.charAt(9)) + temp.charAt(10) + (3 * temp.charAt(11)) + temp.charAt(12)) % 10 == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * output the user-picked ISBN list or quit the application
     */
    public void runProgram() {
        while(true) {
            System.out.println("All ISBN data has been imported and validated. Would you like to: ");
            System.out.println("\t 1) View all valid ISBN numbers");
            System.out.println("\t 2) View all invalid ISBN numbers ");
            System.out.println("\t 3) Quit");
            System.out.print("Your selection: ");
            try{
                Scanner in = new Scanner(System.in);
                int choice = in.nextInt();

                if(choice == 1) {
                    for(String isbn : validNums) {
                        if(isbn != null)
                            System.out.println(isbn);
                    }
                }
                else if (choice == 2) {
                    for(String isbn : invalidNums) {
                        if(isbn != null)
                            System.out.println(isbn);
                    }
                }
                else if (choice == 3)
                    break;
                else
                    System.out.println("Range of choices 1-3, try again.");

            }
            catch(Exception e) {
                System.out.println("User input error, see details here: ");
                System.out.println(e.toString());
            }

        }
    }

    /**
     * Main method for program
     * @param args, command line arguments if needed
     */
    public static void main(String[] args) {
        ISBNValidator app = new ISBNValidator();
        System.out.println("* ISBN Validator Program *");
        System.out.println("...Importing data...");
        app.importData(); // imports data from the text file
        app.runProgram(); // runs using a while loop; see examples
        System.out.println("* End of Program *");
    }
}

