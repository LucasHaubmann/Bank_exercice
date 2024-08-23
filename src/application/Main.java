package application;

import exceptions.BankExeption;
import modal.entities.Bank;
import modal.entities.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Configuração inicial do scanner, formato de data e instância do banco
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Bank bank = new Bank();
        Client client;
        Client selectedClient;

        int option;

        // Loop principal do menu
        while (true) {
            try {
                // Exibição do título do menu
                UI.title("Menu");
                System.out.println("1. Register client");
                System.out.println("2. Show clients");
                System.out.println("3. Withdraw");
                System.out.println("4. Deposit");
                System.out.println("5. Change info");
                System.out.println("6. Exit");
                System.out.println();
                System.out.print("Type the number for the chosen option: ");
                option = sc.nextInt();
                sc.nextLine(); // Limpeza do buffer

                switch (option) {
                    case 1:
                        // Registro de um novo cliente
                        UI.clearScreen();
                        UI.title("Register");
                        System.out.println("\033[1mEnter the client's data:\033[0m");

                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Birthdate (dd/MM/yyyy): ");
                        String birthdateInput = sc.nextLine(); // Captura a linha completa, incluindo a data
                        LocalDate birthdate = LocalDate.parse(birthdateInput, fmt); // Formata a data
                        bank.verifyAge(birthdate);

                        System.out.print("Profession: ");
                        String profession = sc.nextLine();

                        System.out.print("Account number: ");
                        int accountNumber = sc.nextInt();
                        sc.nextLine(); // Limpeza do buffer

                        System.out.print("CPF: ");
                        String CPF = sc.nextLine();

                        // Criação do novo cliente
                        client = new Client(name, birthdate, profession, accountNumber, CPF);

                        System.out.print("Password: ");
                        String password = sc.nextLine();
                        bank.setPassword(password);

                        // Adiciona o cliente ao banco
                        bank.addClient(client);
                        System.out.println("Client registered successfully.");
                        break;

                    case 2:
                        // Exibição da lista de clientes
                        UI.clearScreen();
                        bank.listClients();
                        break;

                    case 3:
                        // Realização de saque
                        selectedClient = bank.selectClientByAccountNumber(sc, bank);
                        if (selectedClient == null) {
                            throw new BankExeption("Error: Selected client does not exist");
                        }
                        System.out.print("Enter the amount to withdraw: ");
                        double withdrawAmount = sc.nextDouble();
                        sc.nextLine(); // Limpeza do buffer
                        bank.withdraw(selectedClient, withdrawAmount);
                        System.out.println("Withdrawal successful.");
                        break;

                    case 4:
                        // Realização de depósito
                        selectedClient = bank.selectClientByAccountNumber(sc, bank);
                        if (selectedClient == null) {
                            throw new BankExeption("Error: Selected client does not exist");
                        }
                        System.out.print("Enter the amount to deposit: ");
                        double depositAmount = sc.nextDouble();
                        sc.nextLine(); // Limpeza do buffer
                        bank.deposit(selectedClient, depositAmount);
                        System.out.println("Deposit successful.");
                        break;

                    case 5:
                        // Alteração das informações do cliente
                        bank.changeInfo(sc, bank);
                        break;

                    case 6:
                        // Saída do sistema
                        System.out.println("Thank you for using our system!");
                        sc.close();
                        System.exit(0); // Encerra o programa
                        break;

                    default:
                        throw new BankExeption("The chosen option is invalid. Choose between 1 to 6");
                }
            } catch (BankExeption e) {
                // Tratamento de exceções específicas do banco
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                // Tratamento de exceções de tipo de entrada inválido
                System.out.println("Invalid input. Please enter the correct data type.");
                sc.nextLine(); // Limpeza do buffer
            } catch (Exception e) {
                // Tratamento genérico para quaisquer outras exceções não previstas
                System.out.println("An unexpected error occurred: " + e.getMessage());
            } finally {
                // Sempre pedir para o usuário pressionar Enter e limpar a tela
                System.out.println();
                System.out.print("Press Enter to continue...");
                sc.nextLine(); // Aguarda o usuário pressionar Enter
                UI.clearScreen();
            }
        }
    }
}
