import java.util.Scanner;
import java.io.File;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final Menu menu = new Menu();
    private static final Pesanan pesanan = new Pesanan();

    private static final String FILE_MENU = "daftar_menu.txt";
    private static final String FILE_STRUK = "struk_pesanan.txt";

    public static void main(String[] args) {
        File fileMenu = new File(FILE_MENU);

        if (fileMenu.exists()) {
            menu.muatDariFile(FILE_MENU);
        }

        int pilihan = -1;

        do {
            tampilkanMenuUtama();
            System.out.print("Pilih menu: ");

            String teks = input.nextLine();

            try {
                pilihan = Integer.parseInt(teks);

                switch (pilihan) {
                    case 1:
                        tambahMakanan();
                        break;
                    case 2:
                        tambahMinuman();
                        break;
                    case 3:
                        tambahDiskon();
                        break;
                    case 4:
                        menu.tampilkanMenu();
                        break;
                    case 5:
                        buatPesanan();
                        break;
                    case 6:
                        pesanan.tampilkanStruk(menu);
                        break;
                    case 7:
                        menu.simpanKeFile(FILE_MENU);
                        break;
                    case 8:
                        menu.muatDariFile(FILE_MENU);
                        break;
                    case 9:
                        pesanan.simpanStruk(FILE_STRUK, menu);
                        break;
                    case 10:
                        pesanan.bacaStruk(FILE_STRUK);
                        break;
                    case 0:
                        System.out.println("Terima kasih telah menggunakan program.");
                        break;
                    default:
                        System.out.println("Pilihan tidak tersedia.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            } catch (IllegalArgumentException e) {
                System.out.println("Kesalahan: " + e.getMessage());
            }

        } while (pilihan != 0);

        input.close();
    }

    private static void tampilkanMenuUtama() {
        System.out.println("\n===== MANAJEMEN RESTORAN =====");
        System.out.println("1. Tambah makanan");
        System.out.println("2. Tambah minuman");
        System.out.println("3. Tambah diskon");
        System.out.println("4. Tampilkan daftar menu");
        System.out.println("5. Buat pesanan");
        System.out.println("6. Tampilkan struk");
        System.out.println("7. Simpan daftar menu ke file");
        System.out.println("8. Muat daftar menu dari file");
        System.out.println("9. Simpan struk ke file");
        System.out.println("10. Baca riwayat struk dari file");
        System.out.println("0. Keluar");
    }

    private static void tambahMakanan() {
        System.out.println("\n--- TAMBAH MAKANAN ---");

        String nama = bacaTeks("Nama makanan: ");
        double harga = bacaAngka("Harga makanan: ");
        String kategori = bacaTeks("Kategori: ");
        String jenis = bacaTeks("Jenis makanan: ");

        menu.tambahMenu(
            new Makanan(nama, harga, kategori, jenis)
        );
    }

    private static void tambahMinuman() {
        System.out.println("\n--- TAMBAH MINUMAN ---");

        String nama = bacaTeks("Nama minuman: ");
        double harga = bacaAngka("Harga minuman: ");
        String kategori = bacaTeks("Kategori: ");
        String jenis = bacaTeks("Jenis minuman: ");

        menu.tambahMenu(
            new Minuman(nama, harga, kategori, jenis)
        );
    }

    private static void tambahDiskon() {
        System.out.println("\n--- TAMBAH DISKON ---");

        String nama = bacaTeks("Nama diskon: ");
        double diskon = bacaAngka("Persentase diskon (0-100): ");

        menu.tambahMenu(
            new Diskon(nama, 0, "Diskon", diskon)
        );
    }

    private static void buatPesanan() {
        if (menu.jumlahMenu() == 0) {
            System.out.println("Belum ada menu yang tersedia.");
            return;
        }

        menu.tampilkanMenu();

        System.out.println("\nMasukkan nomor menu yang ingin dipesan.");
        System.out.println("Masukkan 0 jika selesai memesan.");

        while (true) {
            System.out.print("Nomor menu: ");
            String teks = input.nextLine();

            try {
                int nomor = Integer.parseInt(teks);

                if (nomor == 0) {
                    break;
                }

                MenuItem item = menu.ambilMenu(nomor);
                pesanan.tambahPesanan(item);

            } catch (NumberFormatException e) {
                System.out.println("Nomor harus berupa angka.");
            } catch (IllegalArgumentException e) {
                System.out.println("Kesalahan: " + e.getMessage());
            }
        }

        if (!pesanan.isKosong()) {
            System.out.println("Pesanan selesai dicatat.");
            pesanan.tampilkanStruk(menu);
        }
    }

    private static String bacaTeks(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine().trim();

            if (teks.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
            } else if (teks.contains("|")) {
                System.out.println("Karakter | tidak diperbolehkan.");
            } else {
                return teks;
            }
        }
    }

    private static double bacaAngka(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine();

            try {
                double angka = Double.parseDouble(teks);

                if (!Double.isFinite(angka) || angka < 0) {
                    System.out.println(
                        "Masukkan angka yang valid dan tidak negatif."
                    );
                } else {
                    return angka;
                }

            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }
}
