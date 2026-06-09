import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Mencatat pesanan pelanggan menggunakan ArrayList.
 */
public class Pesanan {
    private ArrayList<BarisPesanan> barisPesanan;

    public Pesanan() {
        barisPesanan = new ArrayList<>();
    }

    public void tambahItem(MenuItem item, int jumlah) {
        barisPesanan.add(new BarisPesanan(item, jumlah));
    }

    public int getJumlahBaris() {
        return barisPesanan.size();
    }

    public boolean kosong() {
        return barisPesanan.isEmpty();
    }

    public void hapusBaris(int indeks) throws MenuItemTidakDitemukanException {
        if (indeks < 0 || indeks >= barisPesanan.size()) {
            throw new MenuItemTidakDitemukanException(
                    "Baris pesanan tidak valid: " + (indeks + 1));
        }
        barisPesanan.remove(indeks);
    }

    public double hitungSubtotalMakananMinuman() {
        double total = 0;
        int i = 0;
        while (i < barisPesanan.size()) {
            total += barisPesanan.get(i).hitungSubtotalBaris();
            i++;
        }
        return total;
    }

    /** Terapkan semua item Diskon pada pesanan (polymorphism + instanceof). */
    public double hitungTotalSetelahDiskon() {
        double subtotal = hitungSubtotalMakananMinuman();
        double total = subtotal;
        int i = 0;
        while (i < barisPesanan.size()) {
            MenuItem item = barisPesanan.get(i).getItem();
            if (item instanceof Diskon diskon) {
                total = total * (1 - diskon.getDiskon());
            }
            i++;
        }
        return total;
    }

    public double hitungTotalPotonganDiskon() {
        double subtotal = hitungSubtotalMakananMinuman();
        return subtotal - hitungTotalSetelahDiskon();
    }

    public String buatTeksStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("================ STRUK PEMBAYARAN ================\n");

        int i = 0;
        while (i < barisPesanan.size()) {
            BarisPesanan baris = barisPesanan.get(i);
            MenuItem item = baris.getItem();
            int q = baris.getJumlah();
            if (item instanceof Diskon diskon) {
                sb.append(String.format("%-20s (potongan %.0f%%)\n",
                        item.getNama(), diskon.getDiskon() * 100));
            } else {
                sb.append(String.format("%-16s x%-2d @ Rp %,.0f = Rp %,.0f\n",
                        item.getNama(), q, item.getHarga(), item.getHarga() * q));
            }
            i++;
        }

        double subtotal = hitungSubtotalMakananMinuman();
        double potongan = hitungTotalPotonganDiskon();
        double grand = hitungTotalSetelahDiskon();

        sb.append("--------------------------------------------------\n");
        sb.append(String.format("Subtotal makanan/minuman     Rp %,.0f\n", subtotal));
        if (potongan > 0) {
            sb.append(String.format("Total potongan diskon        Rp %,.0f\n", potongan));
        } else {
            sb.append("Diskon: tidak ada item diskon pada pesanan.\n");
        }
        sb.append(String.format("TOTAL BAYAR                  Rp %,.0f\n", grand));
        sb.append("==================================================\n");
        return sb.toString();
    }

    public void cetakStruk() {
        System.out.println();
        System.out.print(buatTeksStruk());
    }

    public void simpanStrukKeFile(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(buatTeksStruk());
        writer.close();
    }

    /** Muat isi struk dari file teks dan tampilkan ke layar. */
    public void muatStrukDariFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File struk tidak ditemukan: " + path);
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String baris;
        System.out.println();
        System.out.println("--- Struk dari file: " + path + " ---");
        while ((baris = reader.readLine()) != null) {
            System.out.println(baris);
        }
        reader.close();
    }

    public void tampilkanRingkasan() {
        if (kosong()) {
            System.out.println("Belum ada pesanan.");
            return;
        }
        System.out.println();
        System.out.println("--- Ringkasan Pesanan ---");
        int i = 0;
        while (i < barisPesanan.size()) {
            BarisPesanan baris = barisPesanan.get(i);
            MenuItem item = baris.getItem();
            System.out.printf("%d. %s x%d\n", i + 1, item.getNama(), baris.getJumlah());
            i++;
        }
        System.out.printf("Subtotal: Rp %,.0f | Perkiraan total: Rp %,.0f%n",
                hitungSubtotalMakananMinuman(), hitungTotalSetelahDiskon());
    }
}
