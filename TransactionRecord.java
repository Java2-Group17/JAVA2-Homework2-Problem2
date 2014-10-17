//Group 17

//Jonathan Cuellar
//Navinthira Raman
//Luis Gardea
//Ephraim Girmay
//Gerardo Gonzalez-Inzunza

public class TransactionRecord
{
  private int account;
  private double amount;

  public TransactionRecord()
  {
    this(0, 0.0);
  }

  public TransactionRecord(int acct, double amt)
  {
    setAccount(acct);
    setAmount(amt);
  }

  // get methods
  public int getAccount()
  {
    return account;
  }

  public double getAmount()
  {
    return amount;
  }

  // set methods
  public void setAccount(int acct)
  {
    account = acct;
  }

  public void setAmount(double amt)
  {
    amount = amt;
  }

}
