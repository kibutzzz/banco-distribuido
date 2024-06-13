package br.unisinos.kibutzzz;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static br.unisinos.kibutzzz.Server.ACCOUNT_URL;

public class Client {

  public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
    final var account = (RemoteAccount) Naming.lookup(ACCOUNT_URL);
    final var scanner = new Scanner(System.in);
    var action = "";
    while (!"exit".equals(action.trim())) {
      try {
        System.out.println("""
            Options:
            - deposit - add money to account
            - withdraw - remove money from account
            - balance - show balance
            - exit - close the client
            """);


        action = scanner.nextLine();

        final ThrowingConsumer<RemoteAccount> actionConsumer = switch (action.trim()) {
          case "exit" -> (RemoteAccount acc) -> {
          };
          case "deposit" -> makeDepositAction(scanner);
          case "balance" -> makeBalanceAction();
          case "withdraw" -> makeWithdrawAction(scanner);
          default -> makeDefaultOption(action);
        };

        actionConsumer.apply(account);

        System.out.println("--- ".repeat(10));

      } catch (Exception e) {
        System.err.println(e);
      }
    }
  }

  private static ThrowingConsumer<RemoteAccount> makeDefaultOption(String action) {
    return account -> System.out.printf("Invalid option '%s', please try again%n", action);
  }

  private static ThrowingConsumer<RemoteAccount> makeDepositAction(Scanner scanner) {
    return account -> {
      System.out.println("How much would you like to deposit?");
      final var value = BigDecimal.valueOf(scanner.nextDouble());
      scanner.nextLine();
      account.deposit(value);
    };
  }

  private static ThrowingConsumer<RemoteAccount> makeBalanceAction() {
    return account -> System.out.printf("The account currently has $%s%n", account.readBalance());
  }

  private static ThrowingConsumer<RemoteAccount> makeWithdrawAction(Scanner scanner) {
    return account -> {
      System.out.println("How much would you like to withdraw?");
      final var value = BigDecimal.valueOf(scanner.nextDouble());
      scanner.nextLine();
      account.withdraw(value);
    };
  }


  @FunctionalInterface
  private interface ThrowingConsumer<T> {
    void apply(T data) throws Exception;
  }

}
