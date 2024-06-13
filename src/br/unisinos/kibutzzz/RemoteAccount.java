package br.unisinos.kibutzzz;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteAccount extends Remote {
  void withdraw(BigDecimal amount) throws RemoteException;

  void deposit(BigDecimal amount) throws RemoteException;

  BigDecimal readBalance() throws RemoteException;

}
