/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class PerishableProduct extends Product {
    private LocalDate tanggalKedaluwarsa;

    public PerishableProduct(String id, String nama, double harga, LocalDate tanggalKedaluwarsa) {
        super(id, nama, harga);
        this.tanggalKedaluwarsa = tanggalKedaluwarsa;
    }

    public LocalDate getTanggalKedaluwarsa() {
        return tanggalKedaluwarsa;
    }

    @Override
    public String getJenis() {
        return "Kedaluwarsa";
    }
}
