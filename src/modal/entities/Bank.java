package modal.entities;

import exceptions.BankExeption;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bank {

    // Lista de clientes registrados no banco
    private ArrayList<Client> register = new ArrayList<>();

    // Mapa que associa cada cliente ao seu saldo
    private Map<Client, Double> clientBalances = new HashMap<>();

    // Mapa que associa o número da conta à senha do cliente
    private HashMap<Integer, String> accountPasswords = new HashMap<>();

    // Variáveis de usuário e senha
    private String user;
    private String password;

    // Formato de data para exibição
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor padrão
    public Bank() {
    }

    // Construtor que inicializa o banco com uma senha
    public Bank(String password) {
        Client client = new Client();
        this.user = client.getName();
        this.password = password;
    }

    // Método para obter o saldo de um cliente
    public Double getBalance(Client client) {
        return clientBalances.getOrDefault(client, 0.0);
    }

    // Getter e Setter para a senha
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    // Método para obter a lista de clientes
    public ArrayList<Client> getClients() {
        return register;
    }

    // Método para verificar a idade
    public void verifyAge(LocalDate date){
        int currentYear = LocalDate.now().getYear();
        if (date.getYear() <= 1900 || date.getYear() > currentYear){
            throw new BankExeption("Error: Invalid date");
        }
    }

    // Método para realizar saque de um cliente
    public void withdraw(Client client, double amount) {
        Double balance = getBalance(client);

        // Verificação de valores inválidos
        if (amount <= 0) {
            throw new BankExeption("Error: The amount cannot be zero or negative numbers");
        }

        // Verificação de saldo insuficiente
        if (balance == 0) {
            throw new BankExeption("Error: The selected account has no money");
        }
        if (balance < amount) {
            throw new BankExeption("Error: The selected account does not have the money for the amount suggested");
        }

        // Verificação se há clientes cadastrados
        if (getClients().isEmpty()) {
            throw new BankExeption("No clients registered");
        }

        // Realiza o saque
        clientBalances.put(client, balance - amount);
    }

    // Método para realizar depósito de um cliente
    public void deposit(Client client, double amount) {
        Double currentBalance = getBalance(client);

        // Verificação de valores inválidos
        if (amount <= 0) {
            throw new BankExeption("Error: The amount cannot be zero or negative numbers");
        }

        // Verificação se há clientes cadastrados
        if (getClients().isEmpty()) {
            throw new BankExeption("No clients registered");
        }

        // Realiza o depósito
        clientBalances.put(client, currentBalance + amount);
    }

    // Método para adicionar um novo cliente ao banco
    public void addClient(Client client) {
        register.add(client);
        clientBalances.put(client, 0.0);
        accountPasswords.put(client.getAccountNumber(), password);
    }

    // Método para listar os clientes registrados
    public void listClients() {
        if (register.size() <= 0) {
            throw new BankExeption("Error: There are no clients registered");
        }

        System.out.println("\033[1mClients:\033[0m");
        for (Client client : register) {
            System.out.println("Name: " + client.getName());
            System.out.println("Birthdate: " + client.getBirthDate().format(fmt));
            System.out.println("Profession: " + client.getProfession());
            System.out.println("Account number: " + client.getAccountNumber());
            System.out.println("CPF: " + client.getCPF());
            System.out.println("Balance: " + String.format("R$%.2f", getBalance(client)));
            System.out.println();
        }
    }

    // Método para selecionar um cliente com base no número da conta
    public Client selectClientByAccountNumber(Scanner sc, Bank bank) {
        if (register.size() == 0) {
            throw new BankExeption("There are no clients registered");
        }

        System.out.print("Enter the account number of the client: ");
        int accountNumber = sc.nextInt();
        sc.nextLine();

        Client client = bank.findClientByAccountNumber(accountNumber);
        if (client == null) {
            throw new BankExeption("Client with account number " + accountNumber + " not found");
        } else {
            System.out.println();
            System.out.println("Client selected: ");
            System.out.println("Name: " + client.getName());
            System.out.println("CPF: " + client.getCPF());
            System.out.println("Balance: " + String.format("R$%.2f", getBalance(client)));
        }
        return client;
    }

    // Método para encontrar um cliente com base no número da conta
    public Client findClientByAccountNumber(int accountNumber) {
        if (register.size() <= 0) {
            throw new BankExeption("Error: There are no clients registered");
        }
        for (Client client : register) {
            if (client.getAccountNumber() == accountNumber) {
                return client;
            }
        }
        return null;
    }

    // Método para alterar as informações de um cliente
    public void changeInfo(Scanner sc, Bank bank) {
        if (register.size() == 0) {
            throw new BankExeption("There are no clients registered");
        }

        System.out.print("Enter the client's account number: ");
        int accountNumber = sc.nextInt();
        Client client = bank.findClientByAccountNumber(accountNumber);
        sc.nextLine();

        if (client == null) {
            throw new BankExeption("Client with account number " + accountNumber + " not found");
        }

        System.out.print("Type " + client.getName() + "'s password to continue: ");
        String password = sc.nextLine();

        if (!bank.verifyPassword(accountNumber, password)) {
            throw new BankExeption("Error: Password is incorrect");
        } else {
            System.out.print("Name: ");
            String name = sc.nextLine();
            client.setName(name);
            System.out.print("Birthdate: ");
            LocalDate birthdate = LocalDate.parse(sc.next(), fmt);
            client.setBirthDate(birthdate);
            sc.nextLine();
            System.out.print("Profession: ");
            String profession = sc.nextLine();
            client.setProfession(profession);

            System.out.println("Information updated successfully.");
        }
    }

    // Método para verificar a senha de um cliente
    public boolean verifyPassword(int accountNumber, String password) {
        return password.equals(accountPasswords.get(accountNumber));
    }
}
