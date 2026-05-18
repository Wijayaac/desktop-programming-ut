import java.util.Scanner;

/**
 * Aplikasi CLI restoran — Tugas Praktik 1 (revisi).
 * Array menu + array pesanan; loop untuk tampilan/input/hitung; if/switch untuk promo & struk.
 */
public class Main {

    private static final double PAJAK_PERSEN = 10.0;
    private static final int BIAYA_PELAYANAN = 20_000;
    private static final int BATAS_DISKON_PERSEN = 100_000;
    private static final int BATAS_PROMO_MINUMAN = 50_000;
    private static final double DISKON_PERSEN = 10.0;

    private static final int KAPASITAS_MENU = 40;
    private static final int MAKS_PESANAN = 20;

    private static Menu[] daftarMenu = new Menu[KAPASITAS_MENU];
    private static int jumlahMenu = 0;

    private static Menu[] pesananItem = new Menu[MAKS_PESANAN];
    private static int[] pesananJumlah = new int[MAKS_PESANAN];
    private static int jumlahPesanan = 0;

    public static void main(String[] args) {
        inisialisasiMenuAwal();
        Scanner sc = new Scanner(System.in);
        boolean jalan = true;

        System.out.println("========== APLIKASI RESTORAN ==========");

        while (jalan) {
            System.out.println();
            System.out.println("--- Menu Utama ---");
            System.out.println("1. Lihat daftar menu");
            System.out.println("2. Input pesanan");
            System.out.println("3. Lihat ringkasan pesanan");
            System.out.println("4. Hapus baris pesanan");
            System.out.println("5. Kelola menu (tambah / ubah harga)");
            System.out.println("6. Cetak struk & hitung total");
            System.out.println("0. Keluar");
            System.out.print("Pilih [0-6]: ");

            String pilihan = sc.nextLine().trim();
            if (pilihan.equals("1")) {
                tampilkanDaftarMenu();
            } else if (pilihan.equals("2")) {
                inputPesanan(sc);
            } else if (pilihan.equals("3")) {
                tampilkanRingkasanPesanan();
            } else if (pilihan.equals("4")) {
                hapusBarisPesanan(sc);
            } else if (pilihan.equals("5")) {
                kelolaMenu(sc);
            } else if (pilihan.equals("6")) {
                prosesStruk();
            } else if (pilihan.equals("0")) {
                jalan = false;
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        System.out.println("Terima kasih.");
        sc.close();
    }

    private static void inisialisasiMenuAwal() {
        tambahMenuKeDaftar(new Menu("Nasi Padang", 25_000, "MAKANAN", false));
        tambahMenuKeDaftar(new Menu("Mie Goreng", 18_000, "MAKANAN", false));
        tambahMenuKeDaftar(new Menu("Ayam Geprek", 22_000, "MAKANAN", false));
        tambahMenuKeDaftar(new Menu("Nasi Goreng", 20_000, "MAKANAN", false));
        tambahMenuKeDaftar(new Menu("Es Teh Manis", 5_000, "MINUMAN", false));
        tambahMenuKeDaftar(new Menu("Kopi Susu", 12_000, "MINUMAN", false));
        tambahMenuKeDaftar(new Menu("Jus Alpukat", 15_000, "MINUMAN", true));
        tambahMenuKeDaftar(new Menu("Jus Jeruk", 12_000, "MINUMAN", true));
    }

    private static void tambahMenuKeDaftar(Menu menu) {
        if (jumlahMenu < KAPASITAS_MENU) {
            menu.id = String.format("%02d", jumlahMenu + 1);
            daftarMenu[jumlahMenu] = menu;
            jumlahMenu++;
        }
    }

    private static void tampilkanDaftarMenu() {
        tampilkanMenuMakanan();
        tampilkanMenuMinuman();
        System.out.println("(Promo jus: beli 1 gratis 1 jika subtotal pesanan > Rp "
                + BATAS_PROMO_MINUMAN + ",-)");
    }

    private static void tampilkanMenuMakanan() {
        System.out.println();
        System.out.println("--- MAKANAN ---");
        int i = 0;
        while (i < jumlahMenu) {
            if (daftarMenu[i].kategori.equals("MAKANAN")) {
                System.out.println(formatBarisMenu(daftarMenu[i]));
            }
            i++;
        }
    }

    private static void tampilkanMenuMinuman() {
        System.out.println();
        System.out.println("--- MINUMAN ---");
        int i = 0;
        while (i < jumlahMenu) {
            if (daftarMenu[i].kategori.equals("MINUMAN")) {
                System.out.println(formatBarisMenu(daftarMenu[i]));
            }
            i++;
        }
    }

    private static String formatBarisMenu(Menu m) {
        return String.format("[%s] %-15s | Rp %,d | %-8s%s", m.id, m.nama, m.harga, m.kategori,
                m.promoMinumanJus ? " [promo jus]" : "");
    }

    private static void inputPesanan(Scanner sc) {
        tampilkanDaftarMenu();
        System.out.println();
        System.out.println("Input pesanan (maks. " + MAKS_PESANAN + " baris).");
        System.out.println("Format: ID = jumlah  (contoh: 01 = 2)  — atau nama menu = jumlah");
        System.out.println("Kosongkan baris untuk selesai menambah pesanan pada sesi ini.");
        System.out.println();

        int nomor = 1;
        while (nomor <= MAKS_PESANAN) {
            System.out.print("Pesanan " + nomor + ": ");
            String baris = sc.nextLine();
            if (baris == null) {
                baris = "";
            }
            baris = baris.trim();
            if (baris.isEmpty()) {
                break;
            }

            String kodeAtauNama = ambilNama(baris);
            int qty = ambilJumlah(baris);
            Menu item = cariMenu(kodeAtauNama);

            if (item == null) {
                System.err.println("Menu tidak dikenal (ID/nama salah), baris diabaikan.");
            } else if (qty <= 0) {
                System.err.println("Jumlah tidak valid, baris diabaikan.");
            } else if (jumlahPesanan >= MAKS_PESANAN) {
                System.err.println("Pesanan penuh (maks. " + MAKS_PESANAN + " baris).");
                break;
            } else {
                pesananItem[jumlahPesanan] = item;
                pesananJumlah[jumlahPesanan] = qty;
                jumlahPesanan++;
                System.out.println("  -> ditambahkan: [" + item.id + "] " + item.nama + " x" + qty);
            }
            nomor++;
        }
    }

    private static void tampilkanRingkasanPesanan() {
        if (jumlahPesanan == 0) {
            System.out.println("Belum ada pesanan.");
            return;
        }
        System.out.println();
        System.out.println("--- Ringkasan Pesanan ---");
        int i = 0;
        while (i < jumlahPesanan) {
            Menu m = pesananItem[i];
            int q = pesananJumlah[i];
            System.out.printf("%d. [%s] %-14s x%-2d = Rp %,d%n", i + 1, m.id, m.nama, q, m.harga * q);
            i++;
        }
        System.out.printf("Subtotal sementara: Rp %,d%n", hitungSubtotalPesanan());
    }

    private static void hapusBarisPesanan(Scanner sc) {
        if (jumlahPesanan == 0) {
            System.out.println("Tidak ada pesanan untuk dihapus.");
            return;
        }
        tampilkanRingkasanPesanan();
        System.out.print("Nomor baris yang dihapus (1-" + jumlahPesanan + "), 0 = batal: ");
        int nomor = bacaAngka(sc);
        if (nomor == 0) {
            return;
        }
        if (nomor < 1 || nomor > jumlahPesanan) {
            System.out.println("Nomor tidak valid.");
            return;
        }
        int indeks = nomor - 1;
        System.out.println("Dihapus: " + pesananItem[indeks].nama + " x" + pesananJumlah[indeks]);

        int j = indeks;
        while (j < jumlahPesanan - 1) {
            pesananItem[j] = pesananItem[j + 1];
            pesananJumlah[j] = pesananJumlah[j + 1];
            j++;
        }
        pesananItem[jumlahPesanan - 1] = null;
        pesananJumlah[jumlahPesanan - 1] = 0;
        jumlahPesanan--;
    }

    private static void kelolaMenu(Scanner sc) {
        System.out.println();
        System.out.println("--- Kelola Menu ---");
        System.out.println("1. Tambah menu baru (bisa beberapa sekaligus)");
        System.out.println("2. Ubah harga menu");
        System.out.println("0. Kembali");
        System.out.print("Pilih: ");
        String p = sc.nextLine().trim();
        if (p.equals("1")) {
            tambahMenuBaru(sc);
        } else if (p.equals("2")) {
            ubahHargaMenu(sc);
        }
    }

    private static void tambahMenuBaru(Scanner sc) {
        System.out.print("Berapa menu baru yang ingin ditambahkan? ");
        int banyak = bacaAngka(sc);
        if (banyak <= 0) {
            System.out.println("Jumlah tidak valid.");
            return;
        }
        if (jumlahMenu + banyak > KAPASITAS_MENU) {
            System.out.println("Kapasitas menu tidak cukup (sisa slot: " + (KAPASITAS_MENU - jumlahMenu) + ").");
            return;
        }

        int ke = 1;
        while (ke <= banyak) {
            System.out.println();
            System.out.println("--- Menu baru " + ke + " dari " + banyak + " ---");
            System.out.print("Nama menu: ");
            String nama = sc.nextLine().trim();
            if (nama.isEmpty()) {
                System.out.println("Nama kosong, baris dilewati.");
                ke++;
                continue;
            }
            if (cariMenu(nama) != null) {
                System.out.println("Nama sudah ada, baris dilewati.");
                ke++;
                continue;
            }

            System.out.print("Harga (angka): ");
            int harga = bacaAngka(sc);
            if (harga <= 0) {
                System.out.println("Harga tidak valid, baris dilewati.");
                ke++;
                continue;
            }

            System.out.print("Kategori (MAKANAN/MINUMAN): ");
            String kat = sc.nextLine().trim().toUpperCase();
            if (!kat.equals("MAKANAN") && !kat.equals("MINUMAN")) {
                System.out.println("Kategori harus MAKANAN atau MINUMAN, baris dilewati.");
                ke++;
                continue;
            }

            boolean promoJus = false;
            if (kat.equals("MINUMAN")) {
                System.out.print("Ikut promo jus? (y/n): ");
                String j = sc.nextLine().trim().toLowerCase();
                promoJus = j.equals("y") || j.equals("ya");
            }

            tambahMenuKeDaftar(new Menu(nama, harga, kat, promoJus));
            System.out.println("Menu \"" + nama + "\" berhasil ditambahkan.");
            ke++;
        }
    }

    private static void ubahHargaMenu(Scanner sc) {
        tampilkanDaftarMenu();
        System.out.print("ID atau nama menu yang harganya diubah: ");
        String kodeAtauNama = sc.nextLine().trim();
        Menu m = cariMenu(kodeAtauNama);
        if (m == null) {
            System.out.println("Menu tidak ditemukan.");
            return;
        }
        System.out.printf("Harga lama %s: Rp %,d%n", m.nama, m.harga);
        System.out.print("Harga baru: ");
        int hargaBaru = bacaAngka(sc);
        if (hargaBaru <= 0) {
            System.out.println("Harga tidak valid.");
            return;
        }
        m.harga = hargaBaru;
        System.out.printf("Harga %s diubah menjadi Rp %,d%n", m.nama, m.harga);
    }

    private static void prosesStruk() {
        int subtotal = hitungSubtotalPesanan();
        if (subtotal <= 0) {
            System.out.println("Tidak ada pesanan valid untuk dicetak.");
            return;
        }
        cetakStruk(subtotal);
    }

    private static int hitungSubtotalPesanan() {
        int total = 0;
        int i = 0;
        while (i < jumlahPesanan) {
            total += pesananItem[i].harga * pesananJumlah[i];
            i++;
        }
        return total;
    }

    private static int hitungPotonganPromoMinuman(int subtotalAwal) {
        int potongan = 0;
        if (subtotalAwal > BATAS_PROMO_MINUMAN) {
            int i = 0;
            while (i < jumlahPesanan) {
                Menu m = pesananItem[i];
                int q = pesananJumlah[i];
                if (m.promoMinumanJus && q > 1) {
                    potongan += (q / 2) * m.harga;
                }
                i++;
            }
        }
        return potongan;
    }

    private static void cetakStruk(int subtotalAwal) {
        System.out.println();
        System.out.println("================ STRUK PEMBAYARAN ================");

        int i = 0;
        while (i < jumlahPesanan) {
            Menu m = pesananItem[i];
            int q = pesananJumlah[i];
            System.out.printf("[%s] %-13s x%-2d @ Rp %,d  =  Rp %,d%n", m.id, m.nama, q, m.harga, m.harga * q);
            i++;
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Subtotal item                    Rp %,d%n", subtotalAwal);

        int potonganPromoMinuman = hitungPotonganPromoMinuman(subtotalAwal);
        boolean pakaiPromoMinuman = potonganPromoMinuman > 0;
        int setelahPromoMinuman = subtotalAwal - potonganPromoMinuman;

        if (pakaiPromoMinuman) {
            System.out.printf("Promo jus (beli 1 gratis 1)           - Rp %,d%n", potonganPromoMinuman);
            System.out.printf("Setelah promo jus                     Rp %,d%n", setelahPromoMinuman);
        } else {
            if (subtotalAwal > BATAS_PROMO_MINUMAN) {
                System.out.println("Promo jus: belum memenuhi (min. 2 jus promo per jenis, atau tidak ada jus promo).");
            } else {
                System.out.println("Promo jus: subtotal belum > Rp " + BATAS_PROMO_MINUMAN + ",-");
            }
        }

        int setelahDiskon10 = setelahPromoMinuman;
        double nilaiDiskon10 = 0;
        boolean syaratDiskon10 = subtotalAwal > BATAS_DISKON_PERSEN;

        if (syaratDiskon10) {
            nilaiDiskon10 = Math.round(setelahPromoMinuman * (DISKON_PERSEN / 100.0));
            setelahDiskon10 = (int) Math.round(setelahPromoMinuman - nilaiDiskon10);
            System.out.printf("Diskon 10%% (subtotal > Rp %,d)         - Rp %,.0f%n", BATAS_DISKON_PERSEN, nilaiDiskon10);
            System.out.printf("Setelah diskon                        Rp %,d%n", setelahDiskon10);
        } else if (subtotalAwal == BATAS_DISKON_PERSEN) {
            System.out.println("Diskon 10%: subtotal tepat batas, tidak mendapat diskon.");
        } else {
            System.out.println("Diskon 10%: subtotal belum > Rp " + BATAS_DISKON_PERSEN + ",-");
        }

        double pajak = Math.round(setelahDiskon10 * (PAJAK_PERSEN / 100.0));
        System.out.printf("Pajak 10%% atas Rp %,d                 Rp %,.0f%n", setelahDiskon10, pajak);

        double grand = setelahDiskon10 + pajak + BIAYA_PELAYANAN;

        int kodePromo = kodeRingkasanPromo(pakaiPromoMinuman, syaratDiskon10);
        switch (kodePromo) {
            case 0:
                System.out.println("Skenario: tanpa promo jus dan tanpa diskon 10%.");
                break;
            case 1:
                System.out.println("Skenario: hanya promo jus.");
                break;
            case 2:
                System.out.println("Skenario: hanya diskon 10%.");
                break;
            case 3:
                System.out.println("Skenario: promo jus + diskon 10%.");
                break;
            default:
                System.out.println("Skenario: tidak terklasifikasi.");
                break;
        }

        System.out.printf("Biaya pelayanan                       Rp %,d%n", BIAYA_PELAYANAN);
        System.out.println("--------------------------------------------------");
        System.out.printf("TOTAL BAYAR                           Rp %,.0f%n", grand);
        System.out.println("==================================================");

        nestedIfRingkasanSkenario(subtotalAwal, pakaiPromoMinuman, syaratDiskon10);
    }

    private static int kodeRingkasanPromo(boolean adaPromoJus, boolean adaDiskon10) {
        if (adaPromoJus) {
            if (adaDiskon10) {
                return 3;
            }
            return 1;
        }
        if (adaDiskon10) {
            return 2;
        }
        return 0;
    }

    private static void nestedIfRingkasanSkenario(int subAwal, boolean adaPromoJus, boolean syaratDiskon10) {
        System.out.println();
        System.out.println("---- Note ----");
        if (subAwal > BATAS_PROMO_MINUMAN) {
            if (adaPromoJus) {
                System.out.println("Subtotal > batas promo jus; promo jus dipakai.");
            } else {
                System.out.println("Subtotal > batas promo jus, tetapi potongan promo tidak ada.");
            }
        } else {
            if (subAwal > 0) {
                System.out.println("Subtotal belum > Rp " + BATAS_PROMO_MINUMAN + ",- (batas promo jus).");
            }
        }
        if (syaratDiskon10) {
            if (subAwal > BATAS_DISKON_PERSEN) {
                System.out.println("Subtotal > Rp " + BATAS_DISKON_PERSEN + ",- sehingga diskon 10% berlaku.");
            }
        }
    }

    /** Cari menu berdasarkan ID (01 / 1) atau nama (tidak peka huruf besar-kecil). */
    private static Menu cariMenu(String kodeAtauNama) {
        if (kodeAtauNama == null || kodeAtauNama.isEmpty()) {
            return null;
        }
        String input = kodeAtauNama.trim();

        int i = 0;
        while (i < jumlahMenu) {
            if (daftarMenu[i].id.equalsIgnoreCase(input)) {
                return daftarMenu[i];
            }
            i++;
        }

        try {
            int nomor = Integer.parseInt(input);
            if (nomor >= 1 && nomor <= jumlahMenu) {
                return daftarMenu[nomor - 1];
            }
        } catch (NumberFormatException e) {
            // bukan angka, lanjut cari by nama
        }

        i = 0;
        while (i < jumlahMenu) {
            if (daftarMenu[i].nama.equalsIgnoreCase(input)) {
                return daftarMenu[i];
            }
            i++;
        }
        return null;
    }

    private static String ambilNama(String baris) {
        int i = baris.indexOf('=');
        if (i <= 0) {
            return baris.trim();
        }
        return baris.substring(0, i).trim();
    }

    private static int ambilJumlah(String baris) {
        int i = baris.indexOf('=');
        if (i < 0 || i >= baris.length() - 1) {
            return 0;
        }
        String angka = baris.substring(i + 1).trim();
        switch (angka.length()) {
            case 0:
                return 0;
            default:
                try {
                    return Integer.parseInt(angka);
                } catch (NumberFormatException e) {
                    return 0;
                }
        }
    }

    private static int bacaAngka(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
