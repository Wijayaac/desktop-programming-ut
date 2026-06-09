/**
 * Subkelas MenuItem untuk minuman.
 */
public class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "MINUMAN");
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    public void setJenisMinuman(String jenisMinuman) {
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public String tampilMenu() {
        return String.format("[MINUMAN] %-18s | Rp %,.0f | jenis: %s",
                getNama(), getHarga(), jenisMinuman);
    }

    @Override
    public String keBarisFile() {
        return "MINUMAN|" + getNama() + "|" + getHarga() + "|" + jenisMinuman;
    }
}
