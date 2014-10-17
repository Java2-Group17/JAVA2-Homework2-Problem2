//Group 17

//Jonathan Cuellar
//Navinthira Raman
//Luis Gardea
//Ephraim Girmay
//Gerardo Gonzalez-Inzunza

import java.io.File;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.IllegalFormatException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileMatch
{
  private Scanner inputOldMaster;
  private Scanner inputTransaction;
  private Formatter outputNewMaster;
  private Formatter logFile;
  private TransactionRecord transaction;
  private AccountRecord account;

  public static void main(String[] args)
  {
    FileMatch application = new FileMatch();

    application.openFiles();
    application.processFiles();
    application.closeFiles();
  } // end main

  public FileMatch()
  {
    transaction = new TransactionRecord();
    account = new AccountRecord();
  }

  public void openFiles()
  {
    try
    {
      // file stream for input and output files
      inputOldMaster = new Scanner(new File("oldmast.txt"));
      inputTransaction = new Scanner(new File("trans.txt"));
      outputNewMaster = new Formatter("newmast.txt");
      logFile = new Formatter("log.txt");
    } // end try
    catch (Exception e)
    {
      System.err.println("Error opening the files.");
    } // end catch

  } // end openFiles

  public void processFiles()
  {
    int transactionAccountNumber;
    int accountNumber;

    try
    {
      // get a transaction record and its account number
      transaction = getTransactionRecord();

      //if the transaction is null, we are done
      if (transaction == null)
        return;

      transactionAccountNumber = transaction.getAccount();

      // get an account record and its account number
      account = getAccountRecord();

      if (account == null)
        return;

      accountNumber = account.getAccount();

      while (accountNumber != 0)
      {
        while (accountNumber < transactionAccountNumber)
        {
          // there is no transaction for this account
          outputNewMaster.format("%d %s %s %.2f\r\n",
            account.getAccount(), account.getFirstName(),
            account.getLastName(), account.getBalance() );

          account = getAccountRecord(); // get a new account

          if (account == null)
           return;

          accountNumber = account.getAccount();
        } // end inner while

        // if there is a transaction for this account
        if (accountNumber == transactionAccountNumber)
        {
          account.combine( transaction );

          // write to the master file
          outputNewMaster.format("%d %s %s %.2f\r\n",
            account.getAccount(), account.getFirstName(),
            account.getLastName(), account.getBalance());

          // get a new transaction
          transaction = getTransactionRecord();

          if (transaction == null)
            return ;

          transactionAccountNumber = transaction.getAccount();

          // get a new account
          account = getAccountRecord();

          if (account == null)
            return;

          accountNumber = account.getAccount();
        } // end outer if

        while (transactionAccountNumber < accountNumber)
        {
          // there is no account for this transaction
          logFile.format("%s %d\r\n",
            "Unmatched transaction record for account number",
              transactionAccountNumber);

          // get a new transaction
          transaction = getTransactionRecord();

          if (transaction == null)
            return;

          transactionAccountNumber = transaction.getAccount();
        } // end inner while

      } // end outer while

    } // end try
    catch (FormatterClosedException fce)
    {
      System.err.printf("\n%s", "Error writing to file");
      System.exit(1);
    } // end catch
    catch (IllegalFormatException ife)
    {
      System.err.printf("\n%s", "Error with output");
      System.exit(1);
    } // end catch
  } // end processFiles

  public void closeFiles()
  {
    try
    {
      if (inputTransaction != null)
        inputTransaction.close();

      if (outputNewMaster != null)
        outputNewMaster.close();

      if (inputOldMaster != null)
        inputOldMaster.close();

      if (logFile != null)
        logFile.close();
    } // end try
    catch (Exception e)
    {
      System.err.printf("\n%s", "Error closing the files");
      System.exit(1);
    } // end catch
  } // end closeFiles

  private TransactionRecord getTransactionRecord()
  {
    // try to read the record
    try
    {
      if (inputTransaction.hasNext())
      {
        transaction.setAccount(inputTransaction.nextInt());
        transaction.setAmount(inputTransaction.nextDouble());

        return transaction;
      } // end if
      else // we have hit the end of transaction file
      {
        //these remaining accounts have
        while (inputOldMaster.hasNext())
        {
          account.setAccount(inputOldMaster.nextInt());
          account.setFirstName(inputOldMaster.next());
          account.setLastName(inputOldMaster.next());
          account.setBalance(inputOldMaster.nextDouble());

          // store in new master
          outputNewMaster.format("%d %s %s %.2f\r\n",
            account.getAccount(), account.getFirstName(),
            account.getLastName(), account.getBalance());
        } // end while

      } // end else

    } // end try
    catch (FormatterClosedException fce)
    {
      System.err.printf("\n%s", "Error writing to file");
      System.exit(1);
    } // end catch
    catch (IllegalFormatException ife)
    {
      System.err.printf("\n%s", "Error with output");
      System.exit(1);
    } // end catch
    catch (NoSuchElementException nse)
    {
      System.err.printf("\n%s", "Invalid input from file");
    } // end catch

    return null;
  } // end getTransactionRecord

  private AccountRecord getAccountRecord()
  {
    try // try to read an account record
    {
      if(inputOldMaster.hasNext())
      {
        account.setAccount(inputOldMaster.nextInt() );
        account.setFirstName(inputOldMaster.next() );
        account.setLastName(inputOldMaster.next() );
        account.setBalance(inputOldMaster.nextDouble() );

        return account;
      } // end if
      else // we have hit end of old master file
      {
        logFile.format("%s %d\n",
          "Umatched transaction record for account number",
          transaction.getAccount());

        // these records are transactions without accounts
        while (inputTransaction.hasNext())
        {
          transaction.setAccount(inputTransaction.nextInt());
          transaction.setAmount(inputTransaction.nextDouble());
        } // end while

      } // end else

    } // end try
    catch (FormatterClosedException fce)
    {
      System.err.printf("\n%s", "Error writing to file");
      System.exit(1);
    } // end catch
    catch (IllegalFormatException ife)
    {
      System.err.printf("\n%s", "Error with output");
      System.exit(1);
    } // end catch
    catch (NoSuchElementException nse)
    {
      System.err.printf("\n%s", "Invalid input from file");
    } // end catch

    return null;
  } // end getAccountRecord()

} // end class
