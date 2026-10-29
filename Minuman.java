public class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String kategori,
                   String jenisMinuman) {
        super(nama, harga, kategori);

        if (jenisMinuman == null || jenisMinuman.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Jenis minuman tidak boleh kosong."
            );
        }

        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(
            "Minuman: %s | Harga: Rp%.0f | Kategori: %s | Jenis: %s%n",
            getNama(), getHarga(), getKategori(), jenisMinuman
        );
    }
}
