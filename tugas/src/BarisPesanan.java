/**
 * Satu baris pesanan: item menu + jumlah.
 */
public class BarisPesanan {
    private MenuItem item;
    private int jumlah;

    public BarisPesanan(MenuItem item, int jumlah) {
        this.item = item;
        this.jumlah = jumlah;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double hitungSubtotalBaris() {
        if (item instanceof Diskon) {
            return 0;
        }
        return item.getHarga() * jumlah;
    }
}
