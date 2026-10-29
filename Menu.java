import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Menu {
    private ArrayList<MenuItem> daftarMenu = new ArrayList<>();

    public void tambahMenu(MenuItem item) {
        daftarMenu.add(item);
        System.out.println("Menu berhasil ditambahkan.");
    }

    public void tampilkanMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu restoran masih kosong.");
            return;
        }

        System.out.println("\n===== DAFTAR MENU RESTORAN =====");

        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.print((i + 1) + ". ");
            daftarMenu.get(i).tampilMenu();
        }
    }

    public MenuItem ambilMenu(int nomor) {
        if (nomor < 1 || nomor > daftarMenu.size()) {
            throw new IllegalArgumentException(
                "Nomor menu tidak tersedia."
            );
        }

        return daftarMenu.get(nomor - 1);
    }

    public int jumlahMenu() {
        return daftarMenu.size();
    }

    public void simpanKeFile(String namaFile) {
        try (BufferedWriter writer =
                 new BufferedWriter(new FileWriter(namaFile))) {

            for (MenuItem item : daftarMenu) {
                String baris;

                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    baris = "MAKANAN|" + m.getNama()
                        + "|" + m.getHarga()
                        + "|" + m.getKategori()
                        + "|" + m.getJenisMakanan();

                } else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    baris = "MINUMAN|" + m.getNama()
                        + "|" + m.getHarga()
                        + "|" + m.getKategori()
                        + "|" + m.getJenisMinuman();

                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    baris = "DISKON|" + d.getNama()
                        + "|" + d.getHarga()
                        + "|" + d.getKategori()
                        + "|" + d.getDiskon();

                } else {
                    continue;
                }

                writer.write(baris);
                writer.newLine();
            }

            System.out.println("Daftar menu berhasil disimpan.");

        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }
    }

    public void muatDariFile(String namaFile) {
        ArrayList<MenuItem> sementara = new ArrayList<>();

        try (BufferedReader reader =
                 new BufferedReader(new FileReader(namaFile))) {

            String baris;
            int nomorBaris = 0;

            while ((baris = reader.readLine()) != null) {
                nomorBaris++;

                if (baris.trim().isEmpty()) {
                    continue;
                }

                String[] data = baris.split("\\|", -1);

                try {
                    switch (data[0]) {
                        case "MAKANAN":
                            if (data.length != 5) {
                                throw new IllegalArgumentException(
                                    "Format makanan salah."
                                );
                            }
                            sementara.add(new Makanan(
                                data[1],
                                Double.parseDouble(data[2]),
                                data[3],
                                data[4]
                            ));
                            break;

                        case "MINUMAN":
                            if (data.length != 5) {
                                throw new IllegalArgumentException(
                                    "Format minuman salah."
                                );
                            }
                            sementara.add(new Minuman(
                                data[1],
                                Double.parseDouble(data[2]),
                                data[3],
                                data[4]
                            ));
                            break;

                        case "DISKON":
                            if (data.length != 5) {
                                throw new IllegalArgumentException(
                                    "Format diskon salah."
                                );
                            }
                            sementara.add(new Diskon(
                                data[1],
                                Double.parseDouble(data[2]),
                                data[3],
                                Double.parseDouble(data[4])
                            ));
                            break;

                        default:
                            throw new IllegalArgumentException(
                                "Jenis item tidak dikenal."
                            );
                    }
                } catch (RuntimeException e) {
                    throw new IOException(
                        "Data salah pada baris " + nomorBaris
                        + ": " + e.getMessage()
                    );
                }
            }

            daftarMenu.clear();
            daftarMenu.addAll(sementara);
            System.out.println("Daftar menu berhasil dimuat.");

        } catch (IOException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }
    }
}
