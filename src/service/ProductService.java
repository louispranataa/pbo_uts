/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import com.toedter.calendar.JDateChooser;
import model.Product;
import model.PerishableProduct;
import model.NonPerishableProduct;
import model.DigitalProduct;
import model.BundleProduct;
import model.ProdukModel;
import database.koneksi;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.net.URL;
import javax.swing.*;

public class ProductService {
    public static String idOtomatis() {
        try (Connection conn = koneksi.getConnection();
             Statement st = conn.createStatement()) {

            String sql = "SELECT id FROM produk ORDER BY id DESC LIMIT 1";
            ResultSet rs = st.executeQuery(sql);

            if (!rs.next()) return "PDC0001";

            String lastId = rs.getString("id");

            if (!lastId.startsWith("PDC") || lastId.length() < 7) {
                return "PDC0001";
            }

            int lastNumber = Integer.parseInt(lastId.substring(3));
            String newId = String.format("PDC%04d", lastNumber + 1);
            return newId;

        } catch (SQLException e) {
            e.printStackTrace();
            return "PDC0001"; // fallback safe default
        }
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection conn = koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM produk")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nama = rs.getString("nama");
                double harga = rs.getDouble("harga");
                String jenis = rs.getString("jenis_produk");

                switch (jenis) {
                    case "Tidak Kedaluwarsa":
                        products.add(new NonPerishableProduct(id, nama, harga));
                        break;

                    case "Kedaluwarsa":
                        java.sql.Date tanggal = rs.getDate("tanggal_kedaluwarsa");
                        LocalDate expired = tanggal.toLocalDate();
                        products.add(new PerishableProduct(id, nama, harga, expired));
                        break;

                    case "Digital":
                        URL url = new URL(rs.getString("url"));
                        String vendor = rs.getString("nama_vendor");
                        products.add(new DigitalProduct(id, nama, harga, url, vendor));
                        break;

                    case "Paket":
                        List<Product> isiPaket = getIsiPaket(id, conn);
                        Double diskon = rs.getDouble("diskon");
                        products.add(new BundleProduct(id, nama, harga, diskon, isiPaket));
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    private static List<Product> getIsiPaket(String idPaket, Connection conn) throws SQLException {
        List<Product> isi = new ArrayList<>();

        String sql = "SELECT p.* FROM isi_paket ip JOIN produk p ON ip.id_produk = p.id WHERE ip.id_paket = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPaket);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String nama = rs.getString("nama");
                    double harga = rs.getDouble("harga");
                    String jenis = rs.getString("jenis_produk");

                    switch (jenis) {
                        case "tidak_kedaluwarsa":
                            isi.add(new NonPerishableProduct(id, nama, harga));
                            break;
                        case "kedaluwarsa":
                            java.sql.Date tanggal = rs.getDate("tanggal_kedaluwarsa");
                            LocalDate expired = tanggal.toLocalDate();
                            isi.add(new PerishableProduct(id, nama, harga, tanggal.toLocalDate()));
                            break;
                        case "digital":
                            try {
                                URL url = new URL(rs.getString("url"));
                                String vendor = rs.getString("nama_vendor");
                                isi.add(new DigitalProduct(id, nama, harga, url, vendor));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        }

        return isi;
    }
    
    public static boolean simpanProduk(String ID, String nama, double harga, String jenis, String tanggal, String urlProduk, String vendor) {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "INSERT INTO produk (id, nama, harga, jenis_produk, tanggal_kedaluwarsa, url, nama_vendor) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, ID);
            stmt.setString(2, nama);
            stmt.setDouble(3, harga);
            stmt.setString(4, jenis);

            if ("Kedaluwarsa".equals(jenis)) {
                stmt.setString(5, tanggal);
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            if ("Digital".equals(jenis)) {
                stmt.setString(6, urlProduk);
                stmt.setString(7, vendor);
            } else {
                stmt.setNull(6, java.sql.Types.VARCHAR);
                stmt.setNull(7, java.sql.Types.VARCHAR);
            }

            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static boolean saveBundle(BundleProduct bundle) {
        Connection conn = null;
        PreparedStatement insertProdukStmt = null;
        PreparedStatement insertIsiStmt = null;

        try {
            conn = koneksi.getConnection();
            conn.setAutoCommit(false); // supaya bisa rollback kalau gagal

            // 1. Simpan ke tabel produk
            String sqlProduk = "INSERT INTO produk (id, nama, harga, jenis_produk, diskon) VALUES (?, ?, ?, 'paket', ?)";
            insertProdukStmt = conn.prepareStatement(sqlProduk);
            insertProdukStmt.setString(1, bundle.getId());
            insertProdukStmt.setString(2, bundle.getNama());
            insertProdukStmt.setDouble(3, bundle.getHarga());
            insertProdukStmt.setDouble(4, bundle.getDiskon());
            insertProdukStmt.executeUpdate();

            // 2. Simpan ke tabel isi_paket
            String sqlIsi = "INSERT INTO isi_paket (id_paket, id_produk) VALUES (?, ?)";
            insertIsiStmt = conn.prepareStatement(sqlIsi);

            for (Product p : bundle.getIsiPaket()) {
                insertIsiStmt.setString(1, bundle.getId());
                insertIsiStmt.setString(2, p.getId());
                insertIsiStmt.addBatch();
            }
            insertIsiStmt.executeBatch();
            LogService.tambahLog("Tambah Produk", "Menambahkan Produk dengan ID :"+bundle.getId()+", Nama :"+bundle.getNama()+", Harga:"+bundle.getHarga()+", Jenis :Paket, Diskon :"+bundle.getDiskon());
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // rollback kalau error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;

        } finally {
            try {
                if (insertProdukStmt != null) insertProdukStmt.close();
                if (insertIsiStmt != null) insertIsiStmt.close();
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

//    public static boolean simpanProdukPaket(String nama, double harga, String jenis, double diskon, List isiPaket) {
//        try (Connection conn = koneksi.getConnection()) {
//            String sql = "INSERT INTO produk (nama, harga, jenis_produk, tanggal_kedaluwarsa, nama_vendor, url, diskon) VALUES (?, ?, ?, ?, ?, ?)";
//            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//            stmt.setString(1, nama);
//            stmt.setDouble(2, harga);
//            stmt.setString(3, jenis);
//            stmt.setDouble(3, diskon);
//
//            // === Eksekusi insert produk ===
//            stmt.executeUpdate();
//
//            // === Ambil ID produk yang baru dimasukkan ===
//            ResultSet rs = stmt.getGeneratedKeys();
//            int idProdukBaru = -1;
//            if (rs.next()) {
//                idProdukBaru = rs.getInt(1);
//            }
//
//            // === Jika jenis paket, masukkan ke tabel isi_paket ===
//            if ("paket".equals(jenis)) {
//                String sqlIsi = "INSERT INTO isi_paket (id_paket, id_produk) VALUES (?, ?)";
//                PreparedStatement stmtIsi = conn.prepareStatement(sqlIsi);
//
//                for (Product p : isiPaket.getSelectedValuesList()) {
//                    stmtIsi.setInt(1, idProdukBaru);
//                    stmtIsi.setInt(2, p.getId());
//                    stmtIsi.addBatch();
//                }
//
//                stmtIsi.executeBatch();
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
    
   public static ProdukModel getProduk(String ID) {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT * FROM produk WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String nama = rs.getString("nama");
                double harga = rs.getDouble("harga");
                String jenis = rs.getString("jenis_produk");

                LocalDate tanggalKedaluwarsa = null;
                String url = null;
                String vendor = null;

                if ("Kedaluwarsa".equals(jenis)) {
                    java.sql.Date sqlDate = rs.getDate("tanggal_kedaluwarsa");
                    if (sqlDate != null) {
                        tanggalKedaluwarsa = sqlDate.toLocalDate();
                    }
                }

                if ("Digital".equals(jenis)) {
                    url = rs.getString("url");
                    vendor = rs.getString("nama_vendor");
                }

                return new ProdukModel(id, nama, harga, jenis, tanggalKedaluwarsa, url, vendor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Tidak ditemukan
    }
    
    public static boolean updateProduk(String ID, String nama, double harga, String jenis, String tanggal, String urlProduk, String vendor) {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "UPDATE produk SET nama = ?, harga = ?, jenis_produk = ?, tanggal_kedaluwarsa = ?, url = ?, nama_vendor = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nama);
            stmt.setDouble(2, harga);
            stmt.setString(3, jenis);

            if ("Kedaluwarsa".equals(jenis)) {
                stmt.setString(4, tanggal); // pastikan formatnya cocok dengan DATE
            } else {
                stmt.setNull(4, java.sql.Types.DATE);
            }

            if ("Digital".equals(jenis)) {
                stmt.setString(5, urlProduk);
                stmt.setString(6, vendor);
            } else {
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }

            stmt.setString(7, ID); // WHERE id = ?

            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteProduk(String id) {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "DELETE FROM produk WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
