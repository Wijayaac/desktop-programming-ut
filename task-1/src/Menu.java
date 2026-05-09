/**
 * Representasi satu item menu restoran (makanan atau minuman).
 */
public class Menu {
    /** Nama yang dipakai pemesan di input, misal: {@code Nasi Padang}. */
    public final String nama;
    /** Harga satuan dalam Rupiah. */
    public final int harga;
    /** Kategori besar: MAKANAN atau MINUMAN (untuk pengelompokan tampilan). */
    public final String kategori;
    /**
     * True jika item minuman ikut promo khusus (mis. kelompok jus: beli satu gratis satu).
     */
    public final boolean promoMinumanJus;

    public Menu(String nama, int harga, String kategori, boolean promoMinumanJus) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.promoMinumanJus = promoMinumanJus;
    }
}
