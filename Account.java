
import java.util.*;
import java.io.*;
public class Account implements Serializable
{
	
	private String Fname;
	private String Mname;
	private String Lname;
	private String securityQuestion;
	private String securityQuestionAnswer;
	private String IDnumber;
	
	private int AccountNumber;
	private String password;
	
	private double balance;
	private double interestRate;
	
	
	public void setPersonID(String ID)
	{
		IDnumber= ID;
	}
	
	public String getPersonID()
	{
		return IDnumber;
	}
	
	public void setSecurityQuestion(String SecurityQ)
	{
		securityQuestion= SecurityQ;
	}
	
	public String getSecurityQuestion()
	{
		return securityQuestion;
	}
	
	public String getSecurityQuestionAnswer()
	{
		return securityQuestionAnswer;
	}
	
	public void setSecurityQuestionAnswer(String SecurityQA)
	{
		securityQuestionAnswer= SecurityQA;
	}
	
	public void setAccountNo (int accNo )
	{
		AccountNumber= accNo;
	}
	
	
	public int getAccountNo()
	{
		return AccountNumber;
	}
	
	public void setFname(String fn)
	{
		try {
			Fname=fn;
		}
		catch(InputMismatchException e)
		{
			System.err.println("Fuck off, dummy! Input a string name");
		}
	}
	
	public void setMname(String mn)
	{
		try {
			Mname=mn;
		}
		catch(InputMismatchException e)
		{
			System.err.println("Fuck off, dummy! Input a string name");
		}
	}
	
	public void setLname(String ln)
	{
		try {
			Lname=ln;
		}
		catch(InputMismatchException e)
		{
			System.err.println("Fuck off, dummy! Input a string name");
		}
	}
	
	public void setPassword(String passwd)
	{
		password= passwd;
	}
	
	public String getPassword()
	{
		return password;
	}
	
  public String getFname()
  {
	  return Fname;
  }
  public String getMname()
  {
	  return Mname;
  }
  
  public String getLname()
  {
	  return Lname;
  }
  
  public double getBalance()
  {
	  return balance;
  }
  public void setInterestRate( double iR)
  {
	  if(iR<1.00 && iR>0)
		  interestRate=iR;
	  else
	  interestRate=0.009; // de facto interest
  }
  
  public boolean Deposit(double amt)
  {
	  if(amt<=0)
		  return false;
	  balance+= amt*interestRate + amt;
	  return true;
  }
  
  public boolean Withdrawal( double amt)
  {
	  if(amt<=0)
		  return false;
	  else if(amt>(balance-25))
		  return false;
	  else
		  balance-=amt;
	  return true;
  }
  public void setPassword( char[] p)
  {
	  for(char c: p)
		  password+=c;
  }
  
  private void Rebate()
  {
	  if(balance>999999)
		  interestRate=0.9;
	  
	  if(balance>499999)
		  interestRate=0.7;
	  
	  if(balance>99999)
		  interestRate=0.6;
	  
	  if(balance>49999)
		  interestRate=0.5;
	  
	  if(balance>24999)
		  interestRate=0.4;
  }
  
  public boolean Transfer(Account a, double amt)
  {
	  if(amt<=0)
		  return false;
	  else if(amt>(balance-25))
		  return false;
	  else {
		  balance-=amt;
		  a.Deposit(amt);
	  }
	  return true;
  }
  public String toString()
  {
	  return (getFname()+" "+getMname()+" "+getLname()+" with account number "+getAccountNo()+" currently has "+getBalance()+" on the account\n");
  }
}
