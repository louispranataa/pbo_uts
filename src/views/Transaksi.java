/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;
import model.Product;
import model.BundleProduct;
import model.PurchaseTransaction;
import service.TransactionService;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import javax.swing.ImageIcon;
import model.JenisTransaksi;

public class Transaksi extends javax.swing.JFrame {

    DefaultTableModel model;
    private PurchaseTransaction transaksi;
    
    public static Integer setIDRow;
    public static Integer setHarga;
    public static Integer setJumlah;
    public static Integer setDiskon;
    /**
     * Creates new form Penjualan
     */
    public Transaksi() {
        initComponents();
        URL iconURL = getClass().getResource("/images/icon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        tanggal.setText(dateFormat.format(cal.getTime()));
        reset();
    }
    
    private void BtnEnabled(boolean x){
        bHapus.setEnabled(x);
    }
    
    public void reset() {
        jenis.removeAllItems();
        jenis.addItem("pembelian");
        jenis.addItem("retur");
        String newId = TransactionService.idOtomatis();
        idTransaksi.setText(newId);
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        id.setText("");
        diskon.setText("--");
        nama.setText("--");
        harga.setText("");
        jumlah.setText("");
        bayar.setText("");
        totalDiskon.setText("0");
        total.setText("0");
        kembalian.setText("0");
        id.requestFocus();
        BtnEnabled(false);
    }
    
    private void getFromTable(){
        setIDRow = table.getSelectedRow();
        String dkn = (table.getModel().getValueAt(setIDRow, 2).toString());
        String hrg = (table.getModel().getValueAt(setIDRow, 3).toString());
        String jml = (table.getModel().getValueAt(setIDRow, 4).toString());
        setHarga = (int) Double.parseDouble(hrg);
        setJumlah = (int) Integer.parseInt(jml);
        setDiskon = (int) Double.parseDouble(dkn);
        BtnEnabled(true);
    }
    
    private void kembalian() {
        int ttl = Integer.parseInt(total.getText());
        int byr = 0;
        String bayarText = bayar.getText().trim();
        if(!bayarText.isEmpty()){
            byr = Integer.parseInt(bayar.getText());
        }
        int kmb = byr - ttl;
        
        kembalian.setText(Integer.toString(kmb));
    }
    
//    private boolean insert() {
//    JenisTransaksi selectedJenis = JenisTransaksi.PEMBELIAN; // default
//    if (jenis.getSelectedItem() != null) {
//        String selected = jenis.getSelectedItem().toString().toUpperCase();
//        if (selected.equals("RETUR")) {
//            selectedJenis = JenisTransaksi.RETUR;
//        }
//    }
//
//    // Buat transaksi baru
//    transaksi = new PurchaseTransaction(idTransaksi.getText(), selectedJenis);
//        transaksi.processTransaction();
//            TransactionService trx = new TransactionService();
//            boolean hasil = trx.simpanTransaksi(transaksi);
//            if (hasil) {
//                reset();
//                JOptionPane.showMessageDialog(this, "Data Produk berhasil ditambahkan");
//                return true;
//            } else {
//                JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi.");
//                return false;
//            }
//    }
    
    public void insert() {
        // Ambil nilai dari comboBox jenis
        JenisTransaksi selectedJenis = JenisTransaksi.PEMBELIAN; // default
        if (jenis.getSelectedItem() != null) {
            String selected = jenis.getSelectedItem().toString();
            if (selected.equals("retur")) {
                selectedJenis = JenisTransaksi.RETUR;
            }
        }

        // Buat transaksi baru
        transaksi = new PurchaseTransaction(idTransaksi.getText(), selectedJenis);

        // Tambahkan item-item dari tabel (contoh logika pengisian item)
        for (int i = 0; i < table.getRowCount(); i++) {
            String id = table.getValueAt(i, 0).toString();
            int qty = Integer.parseInt(table.getValueAt(i, 4).toString());
            Product produk = TransactionService.getProduk(id);
            if (produk != null) {
                transaksi.addItem(produk, qty);
            }
        }

        // Set pembayaran
        transaksi.setBayar(Double.parseDouble(bayar.getText()));

        // Simpan transaksi ke database
        boolean success = new TransactionService().simpanTransaksi(transaksi);
        if (success) {
            JOptionPane.showMessageDialog(null, "Transaksi berhasil disimpan!");
            reset(); // reset form setelah simpan
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan transaksi.");
        }
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        bTambah = new javax.swing.JButton();
        bBatal = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        diskon = new javax.swing.JLabel();
        jumlah = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        nama = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        harga = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        idTransaksi = new javax.swing.JTextField();
        tanggal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bSimpan = new javax.swing.JButton();
        bayar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        kembalian = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        totalDiskon = new javax.swing.JLabel();
        jenis = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Penjualan-WANT Laptop");

        jLabel1.setBackground(new java.awt.Color(0, 153, 255));
        jLabel1.setFont(new java.awt.Font("Californian FB", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Transaksi");
        jLabel1.setOpaque(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Produk", "Nama Produk", "Diskon", "Harga", "Jumlah"
            }
        ));
        table.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tableFocusGained(evt);
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        bTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        bTambah.setText("Tambah");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });

        bBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cencel.png"))); // NOI18N
        bBatal.setText("Batal");
        bBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatalActionPerformed(evt);
            }
        });

        bHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        bHapus.setText("Hapus yang dipilh");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        jLabel2.setText("ID Produk");

        id.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                idInputMethodTextChanged(evt);
            }
        });
        id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idKeyReleased(evt);
            }
        });

        jLabel3.setText("Nama Produk :");

        diskon.setText("--");

        jLabel8.setText("Jumlah");

        nama.setText("--");

        jLabel10.setText("Harga");

        harga.setEnabled(false);

        jLabel13.setText("Diskon :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bHapus)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nama)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(diskon)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bTambah)
                    .addComponent(jLabel2)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(nama)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(diskon)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bHapus)
                    .addComponent(bBatal))
                .addContainerGap())
        );

        jLabel5.setText("ID Transaksi");

        idTransaksi.setEnabled(false);

        tanggal.setEnabled(false);
        tanggal.setFocusable(false);

        jLabel7.setText("Tanggal");

        total.setBackground(new java.awt.Color(255, 255, 255));
        total.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        total.setForeground(new java.awt.Color(0, 153, 255));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 255));
        jLabel4.setText("Total Bayar    :");

        bSimpan.setBackground(new java.awt.Color(0, 153, 255));
        bSimpan.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bSimpan.setText("Simpan");
        bSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        bayar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bayar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "*Ketik jumlah lalu enter", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Tahoma", 1, 8))); // NOI18N
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });
        bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bayarKeyPressed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setText("Kembalian      :");

        kembalian.setBackground(new java.awt.Color(255, 255, 255));
        kembalian.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel12.setText("Jumlah Bayar :");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 51));
        jLabel6.setText("Diskon            :");

        totalDiskon.setBackground(new java.awt.Color(255, 255, 255));
        totalDiskon.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        totalDiskon.setForeground(new java.awt.Color(0, 204, 51));

        jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jenis.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jenisItemStateChanged(evt);
            }
        });

        jLabel9.setText("Jenis Transaksi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel5)
                        .addGap(26, 26, 26)
                        .addComponent(idTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(530, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bSimpan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(kembalian))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(totalDiskon, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(total))))
                                .addGap(6, 6, 6)))))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(idTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(totalDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(total))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(kembalian))
                .addGap(18, 18, 18)
                .addComponent(bSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        getFromTable();
    }//GEN-LAST:event_tableMouseClicked

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        // TODO add your handling code here:
        String kode = id.getText();
            Integer same_kode = 0;
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                if(kode.equals(table.getModel().getValueAt(i, 0).toString())){
                    same_kode = 1;
                    break;
                }
            }
            
            if(same_kode == 0){
                String ID = id.getText().toUpperCase();
                String Nama = nama.getText();
                String Diskon = diskon.getText();
                String Harga = harga.getText();
                String Jumlah = jumlah.getText().trim();
                String Total = total.getText();
                String TotalDiskon = totalDiskon.getText();
                if(!(ID.equals("")) && !(Nama.equals("")) && !(Diskon.equals("")) && !(Harga.equals("")) && !(Jumlah.equals(""))) {
                    int hrg = (int) Double.parseDouble(Harga);
                    int jml;
                    try {
                        jml = Integer.parseInt(Jumlah);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Jumlah harus angka!");
                        return;
                    }
                    int ttl = Integer.parseInt(Total);
                    int dkn = (int) Double.parseDouble(Diskon);
                    int ttlDiskon = Integer.parseInt(TotalDiskon);
                        Object[] row = { ID, Nama, Diskon, Harga, Jumlah };
//                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.addRow(row);
                        ttl += (hrg*jml);
                        ttlDiskon += (dkn*jml);
                        id.setText("");
                        nama.setText("--");
                        diskon.setText("--");
                        harga.setText("");
                        jumlah.setText("");
                        total.setText(Integer.toString(ttl));
                        totalDiskon.setText(Integer.toString(ttlDiskon));
                        if(!bayar.getText().trim().isEmpty()){
                            kembalian(); 
                        }
                        id.requestFocus();
//                        Product produk = TransactionService.getProduk(ID);
//                        int jumlah = Integer.parseInt(Jumlah);
//                        transaksi.addItem(produk, jumlah);
                }else{
                    JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Kode barang sudah pernah ditambah.");
                id.setText("");
                nama.setText("--");
                diskon.setText("--");
                harga.setText("");
                jumlah.setText("");
                id.requestFocus();
            }
    }//GEN-LAST:event_bTambahActionPerformed

    private void bBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatalActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_bBatalActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus baris ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(ok==0) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(setIDRow);
            int ttl = Integer.parseInt(total.getText());
            ttl -= (setHarga*setJumlah);
            total.setText(Integer.toString(ttl));
            int ttlDiskon = Integer.parseInt(totalDiskon.getText());
            ttlDiskon -= (setDiskon*setJumlah);
            totalDiskon.setText(Integer.toString(ttlDiskon));
            BtnEnabled(false);
            kembalian();
        }
    }//GEN-LAST:event_bHapusActionPerformed

    private void idInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_idInputMethodTextChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_idInputMethodTextChanged

    private void idKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idKeyReleased
        // TODO add your handling code here:
        Product produk = TransactionService.getProduk(id.getText());
        if (produk != null) {
            id.setText(produk.getId());
            nama.setText(produk.getNama());
            harga.setText(String.valueOf(produk.getHarga()));
            if (produk instanceof BundleProduct) {
                diskon.setText(String.valueOf(((BundleProduct) produk).getDiskon()));
            } else {
                diskon.setText("0.0");
            }
            }else {
                        nama.setText("--");
                        harga.setText("--");
                        diskon.setText("0.0");
                    }
    }//GEN-LAST:event_idKeyReleased

    private void tableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableFocusGained
        // TODO add your handling code here:
        getFromTable();
    }//GEN-LAST:event_tableFocusGained

    private void bSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSimpanActionPerformed
        // TODO add your handling code here:
        int rowCount = model.getRowCount();
            if(!total.getText().trim().equals("0") &&
                    !bayar.getText().trim().isEmpty() &&
                    !idTransaksi.getText().trim().isEmpty() &&
                    !tanggal.getText().trim().isEmpty() &&
                    rowCount > 0) {
                insert();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Lengkapi form terlebih dahulu!",
                    "Notifikasi", JOptionPane.WARNING_MESSAGE);
            }
    }//GEN-LAST:event_bSimpanActionPerformed

    private void bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bayarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            kembalian();
        }
    }//GEN-LAST:event_bayarKeyPressed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bayarActionPerformed

    private void jenisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jenisItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jenisItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBatal;
    private javax.swing.JButton bHapus;
    private static javax.swing.JButton bSimpan;
    private javax.swing.JButton bTambah;
    private javax.swing.JTextField bayar;
    private javax.swing.JLabel diskon;
    private javax.swing.JTextField harga;
    private javax.swing.JTextField id;
    private javax.swing.JTextField idTransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jenis;
    private javax.swing.JTextField jumlah;
    private javax.swing.JLabel kembalian;
    private javax.swing.JLabel nama;
    private javax.swing.JTable table;
    private javax.swing.JTextField tanggal;
    private javax.swing.JLabel total;
    private javax.swing.JLabel totalDiskon;
    // End of variables declaration//GEN-END:variables
}
