/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public abstract class Transaction {
    protected String transactionId;
    protected LocalDate date;
    protected JenisTransaksi jenis;
    

    public Transaction(String transactionId, JenisTransaksi jenis) {
        this.transactionId = transactionId;
        this.date = LocalDate.now();
        this.jenis = jenis;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public JenisTransaksi getJenis() {
        return jenis;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public abstract double calculateTotal();
    public abstract void processTransaction();
    public abstract String serializeTransaction();
}
