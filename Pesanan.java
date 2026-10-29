import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Pesanan {
    private ArrayList<MenuItem> daftarPesanan = new ArrayList<>();

    public void tambahPesanan(MenuItem item) {
        if (item instanceof Diskon) {
            throw new IllegalArgumentException(
                "Diskon tidak dapat dipesan sebagai produk."
            );
        }

        daftarPesanan.add(item);
        System.out.println("Pesanan berhasil ditambahkan.");
    }

    public boolean isKosong() {
        return daftarPesanan.isEmpty();
    }

    public double hitungSubtotal() {
        double subtotal = 0;

        for (MenuItem item : daftarPesanan) {
            subtotal += item.getHarga();
        }

        return subtotal;
    }

    public double cariPersentaseDiskon(Menu menu) {
        double terbesar = 0;

        for (int i = 1; i <= menu.jumlahMenu(); i++) {
            MenuItem item = menu.ambilMenu(i);

            if (item instanceof Diskon) {
                Diskon d = (Diskon) item;

                if (d.getDiskon() > terbesar) {
                    terbesar = d.getDiskon();
                }
            }
        }

        return terbesar;
    }

    public double hitungTotal(Menu menu) {
        double subtotal = hitungSubtotal();
        double diskon = cariPersentaseDiskon(menu);

        return subtotal - (subtotal * diskon / 100.0);
    }

    public void tampilkanStruk(Menu menu) {
        if (isKosong()) {
            System.out.println("Belum ada pesanan.");
            return;
        }

        double subtotal = hitungSubtotal();
        double persentase = cariPersentaseDiskon(menu);
        double potongan = subtotal * persentase / 100.0;
        double total = subtotal - potongan;

        System.out.println("\n========== STRUK RESTORAN ==========");

        for (MenuItem item : daftarPesanan) {
            System.out.printf("%s - Rp%.0f%n",
                item.getNama(), item.getHarga());
        }

        System.out.println("------------------------------------");
        System.out.printf("Subtotal       : Rp%.0f%n", subtotal);
        System.out.printf("Diskon         : %.0f%%%n", persentase);
        System.out.printf("Potongan       : Rp%.0f%n", potongan);
        System.out.printf("Total           : Rp%.0f%n", total);
        System.out.println("====================================");
    }

    public void simpanStruk(String namaFile, Menu menu) {
        if (isKosong()) {
            System.out.println("Tidak ada pesanan untuk disimpan.");
            return;
        }

        double subtotal = hitungSubtotal();
        double persentase = cariPersentaseDiskon(menu);
        double potongan = subtotal * persentase / 100.0;
        double total = subtotal - potongan;

        try (BufferedWriter writer =
                 new BufferedWriter(new FileWriter(namaFile, true))) {

            writer.write("========== STRUK RESTORAN ==========");
            writer.newLine();

            for (MenuItem item : daftarPesanan) {
                writer.write(item.getNama() + " - Rp"
                    + String.format("%.0f", item.getHarga()));
                writer.newLine();
            }

            writer.write("------------------------------------");
            writer.newLine();
            writer.write(String.format("Subtotal: Rp%.0f", subtotal));
            writer.newLine();
            writer.write(String.format("Diskon: %.0f%%", persentase));
            writer.newLine();
            writer.write(String.format("Potongan: Rp%.0f", potongan));
            writer.newLine();
            writer.write(String.format("Total: Rp%.0f", total));
            writer.newLine();
            writer.write("====================================");
            writer.newLine();
            writer.newLine();

            System.out.println("Struk berhasil disimpan ke " + namaFile);

        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk: " + e.getMessage());
        }
    }

    public void bacaStruk(String namaFile) {
        try (BufferedReader reader =
                 new BufferedReader(new FileReader(namaFile))) {

            String baris;
            boolean adaIsi = false;

            System.out.println("\n===== RIWAYAT STRUK =====");

            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
                adaIsi = true;
            }

            if (!adaIsi) {
                System.out.println("File struk masih kosong.");
            }

        } catch (IOException e) {
            System.out.println("Gagal membaca struk: " + e.getMessage());
        }
    }
}
