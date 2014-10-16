/* Write a program to create data for testing the program.
Use the sample account data. Run the program to create the files
trans.txt and oldmast.txt, to be used by file-matching program
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.IllegalFormatException;

public class CreateData
{
  public static void main(String[] args)
  {
    CreateData application = new CreateData();

    application.openFile();
    application.addRecords();
    application.closeFile();
  }
    private Formatter outputMaster;
    private Formatter outputTransaction;


    // create oldmast.txt and trans.txt
    public void openFile()
    {
      try
      {
        // file stream for output Master file
        outputMaster = new Formatter("oldmast.txt");

        // file stream for output Transaction file
        outputTransaction = new Formatter( "Trans.txt");
      } // end try

      catch(SecurityException se)
      {
        System.err.println("You do not have write access to this file");
        System.exit(1);
      } // end catch
      catch (FileNotFoundException fnfe)
      {
        System.err.println("Error opening or creating the file");
        System.exit(1);
      } // end catch

    } // end openFile


    // add records to Master and Transaction
    public void addRecords()
    {

      AccountRecord accounts[] = new AccountRecord[4];
      TransactionRecord transactions[] = new TransactionRecord[4];

      try
      {
        // create account records
        accounts[0] = new AccountRecord(100, "Alan", "Jones", 348.17);
        accounts[1] = new AccountRecord(300, "Mary", "Smith", 27.19);
        accounts[2] = new AccountRecord(500, "Sam", "Sharp", 0.00);
        accounts[3] = new AccountRecord(700, "Suzy", "Green", -14.22);

        // create transactions
        transactions[0] = new TransactionRecord(100, 27.14);
        transactions[1] = new TransactionRecord(300, 62.11);
        transactions[2] = new TransactionRecord(400, 100.56);
        transactions[3] = new TransactionRecord(900, 82.17);


        for (int i = 0; i < accounts.length; i++)
        {
          outputMaster.format("%d %s %s %.2f\r\n",
            accounts[i].getAccount(), accounts[i].getFirstName(),
            accounts[i].getLastName(), accounts[i].getBalance() );
        } // end for



        for ( int i = 0; i < transactions.length; i++)
        {
          outputTransaction.format( "%d %.2f\r\n",
            transactions[i].getAccount(),
            transactions[i].getAmount() );
          } // end for

      } // end try

      catch(FormatterClosedException fce)
      {
        System.err.println("Error writing to file");
        System.exit(1);
      } // end catch
      catch(IllegalFormatException ife)
      {
        System.err.println("Invalid with output.");
        System.exit(1);
      } // end catch

    } // end addRecords


    // close files
    public void closeFile()
    {
      if(outputMaster != null)
        outputMaster.close();

      if(outputTransaction != null)
      outputTransaction.close();
    } // end closeFile


} // end class
