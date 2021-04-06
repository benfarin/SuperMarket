package Entities;

import java.util.List;

public class Supplier {
    private int id_supplier;
    private long id_company;
    private String name;
    private List<String> contacts;
    private String paymentMethod;
    private String bankAccount;

    public Supplier(int id_supplier, long id_company, String name, List<String> contacts, String paymentMethod, String bankAccount) {
        this.id_supplier = id_supplier;
        this.id_company = id_company;
        this.name = name;
        this.contacts = contacts;
        this.paymentMethod = paymentMethod;
        this.bankAccount = bankAccount;
    }

    protected int getId_supplier() {
        return id_supplier;
    }

    protected void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    protected long getId_company() {
        return id_company;
    }

    protected void setId_company(long id_company) {
        this.id_company = id_company;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected List<String> getContacts() {
        return contacts;
    }

    protected void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    protected String getPaymentMethod() {
        return paymentMethod;
    }

    protected void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    protected String getBankAccount() {
        return bankAccount;
    }

    protected void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
