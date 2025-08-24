import java.util.Scanner;


class BankAccount {
    double balance;

    // Constructor
    BankAccount(double startingBalance) {
        balance = startingBalance;
    }

    // Deposit method
    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Enter a valid amount to deposit.");
        }
    }

    // Withdraw method
    void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Not enough balance.");
        } else if (amount <= 0) {
            System.out.println("Enter a valid amount to withdraw.");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        }
    }

    // Check balance
    void checkBalance() {
        System.out.println("Your balance is: $" + balance);
    }
}



public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount userAccount = new BankAccount(1000); // Starting balance

        while (true) {
          
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

           
            if (choice == 1) {
                userAccount.checkBalance();

            } else if (choice == 2) {
                System.out.print("Enter deposit amount: ");
                double amount = scanner.nextDouble();
                userAccount.deposit(amount);

            } else if (choice == 3) {
                System.out.print("Enter withdrawal amount: ");
                double amount = scanner.nextDouble();
                userAccount.withdraw(amount);

            } else if (choice == 4) {
                System.out.println("Thank you for using the ATM. Goodbye!");
                break;

            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
