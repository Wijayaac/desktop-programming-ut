/**
 * Representasi satu item menu restoran (makanan atau minuman).
 */
public class Menu {
    /** Kode pesanan, mis. 01, 02 (diisi saat menu ditambahkan ke daftar). */
    public String id;
    public String nama;
    public int harga;
    /** MAKANAN atau MINUMAN */
    public String kategori;
    /** True jika minuman ikut promo jus (beli 1 gratis 1). */
    public boolean promoMinumanJus;

    public Menu(String nama, int harga, String kategori, boolean promoMinumanJus) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.promoMinumanJus = promoMinumanJus;
    }
}
