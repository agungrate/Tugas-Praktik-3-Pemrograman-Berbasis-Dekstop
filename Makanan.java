public class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String kategori,
                   String jenisMakanan) {
        super(nama, harga, kategori);

        if (jenisMakanan == null || jenisMakanan.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Jenis makanan tidak boleh kosong."
            );
        }

        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(
            "Makanan: %s | Harga: Rp%.0f | Kategori: %s | Jenis: %s%n",
            getNama(), getHarga(), getKategori(), jenisMakanan
        );
    }
}
