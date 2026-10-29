public class Diskon extends MenuItem {
    private double diskon;

    public Diskon(String nama, double harga, String kategori,
                  double diskon) {
        super(nama, harga, kategori);

        if (!Double.isFinite(diskon) || diskon < 0 || diskon > 100) {
            throw new IllegalArgumentException(
                "Diskon harus antara 0 sampai 100 persen."
            );
        }

        this.diskon = diskon;
    }

    public double getDiskon() {
        return diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(
            "Diskon: %s | Potongan: %.0f%%%n",
            getNama(), diskon
        );
    }
}
