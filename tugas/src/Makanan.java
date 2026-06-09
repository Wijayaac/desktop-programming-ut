/**
 * Subkelas MenuItem untuk makanan.
 */
public class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "MAKANAN");
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public String tampilMenu() {
        return String.format("[MAKANAN] %-18s | Rp %,.0f | jenis: %s",
                getNama(), getHarga(), jenisMakanan);
    }

    @Override
    public String keBarisFile() {
        return "MAKANAN|" + getNama() + "|" + getHarga() + "|" + jenisMakanan;
    }
}
