package br.unisinos.kibutzzz;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

  private static final int port = 1099;
  public static final String ACCOUNT_URL = "//localhost:" + port + "/Account";

  public static void main(String[] args) {
    System.out.println("Initializing Server...");

    try {
      final var account = new Account();

      LocateRegistry.createRegistry(port);
      Naming.rebind(ACCOUNT_URL, account);

    } catch (RemoteException | MalformedURLException e) {
      System.err.println(e);
    }

  }
}
