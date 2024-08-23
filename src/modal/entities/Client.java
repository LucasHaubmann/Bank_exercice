package modal.entities;

import java.time.LocalDate;

public class Client {
    private String name;
    private LocalDate birthDate;
    private String profession;
    private Integer accountNumber;
    private String CPF;

    public Client(){

    }

    public Client(String name, LocalDate birthDate, String profession, Integer accountNumber, String CPF) {
        this.birthDate = birthDate;
        this.name = name;
        this.profession = profession;
        this.accountNumber = accountNumber;
        this.CPF = CPF;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        birthDate = birthDate;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCPF() {
        return CPF;
    }
}
