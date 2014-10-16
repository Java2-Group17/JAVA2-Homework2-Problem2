
public class AccountRecord
{
  private int account;
  private String firstName;
  private String lastName;
  private double balance;

  public AccountRecord()
  {
    this(0, "", "", 0.0);
  }

  public AccountRecord( int acct, String first, String last, double bal)
  {
    setAccount(acct);
    setFirstName(first);
    setLastName(last);
    setBalance(bal);
  }

  public void combine(TransactionRecord transaction)
  {
    balance = balance + transaction.getAmount();
  }

  // get methods
  public int getAccount()
  {
    return account;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public double getBalance()
  {
    return balance;
  }

  // set methods
  public void setAccount(int acct)
  {
    account = acct;
  }

  public void setFirstName(String first)
  {
    firstName = first;
  }

  public void setLastName(String last)
  {
    lastName = last;
  }

  public void setBalance(double bal)
  {
    balance = bal;
  }
} // end class
