package br.unisinos.kibutzzz;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Account extends UnicastRemoteObject implements RemoteAccount {

  private BigDecimal balance = BigDecimal.ZERO;

  protected Account() throws RemoteException {
    super();
  }

  @Override
  public void withdraw(BigDecimal amount) throws RemoteException {
    if (amount.compareTo(balance) > 0) {
      throw new IllegalArgumentException("amount should not be greater than balance");
    }
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("amount should be positive");
    }

    balance = balance.subtract(amount);
  }

  @Override
  public void deposit(BigDecimal amount) throws RemoteException {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("amount should be positive");
    }

    balance = balance.add(amount);
  }

  @Override
  public BigDecimal readBalance() throws RemoteException {
    return balance;
  }
}
