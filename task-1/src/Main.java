import java.util.Scanner;

/**
 * Aplikasi CLI pemesanan restoran — Tugas Praktik 1.
 * Tidak memakai for/while sesuai petunjuk; array + struktur keputusan.
 */
public class Main {

    private static final double PAJAK_PERSEN = 10.0;
    private static final int BIAYA_PELAYANAN = 20_000;
    private static final int BATAS_DISKON_PERSEN = 100_000;
    /** Subtotal item harus lebih besar dari nilai ini agar promo minuman bisa dihitung. */
    private static final int BATAS_PROMO_MINUMAN = 50_000;
    private static final double DISKON_PERSEN = 10.0;

    private static final Menu[] DAFTAR_MENU = new Menu[] {
        new Menu("Nasi Padang", 25_000, "MAKANAN", false),
        new Menu("Mie Goreng", 18_000, "MAKANAN", false),
        new Menu("Ayam Geprek", 22_000, "MAKANAN", false),
        new Menu("Nasi Goreng", 20_000, "MAKANAN", false),
        new Menu("Es Teh Manis", 5_000, "MINUMAN", false),
        new Menu("Kopi Susu", 12_000, "MINUMAN", false),
        new Menu("Jus Alpukat", 15_000, "MINUMAN", true),
        new Menu("Jus Jeruk", 12_000, "MINUMAN", true),
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("========== RESTORAN PRAKTIK 1 ==========");
        tampilkanMenuMakanan();
        tampilkanMenuMinuman();
        System.out.println();
        System.out.println("Masukkan pesanan (maks. 4 baris). Format: Nama Menu = jumlah");
        System.out.println("Contoh: Nasi Padang = 2");
        System.out.println("Kosongkan baris jika tidak dipakai.");
        System.out.println();

        String baris1 = bacaBaris(sc, "Pesanan 1: ");
        String baris2 = bacaBaris(sc, "Pesanan 2: ");
        String baris3 = bacaBaris(sc, "Pesanan 3: ");
        String baris4 = bacaBaris(sc, "Pesanan 4: ");

        Menu p1 = null;
        Menu p2 = null;
        Menu p3 = null;
        Menu p4 = null;
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;

        if (!baris1.isEmpty()) {
            String nama1 = ambilNama(baris1);
            int q = ambilJumlah(baris1);
            p1 = cariMenu(nama1);
            if (p1 != null && q > 0) {
                q1 = q;
            } else if (p1 == null) {
                System.err.println("(Abaikan baris 1: menu tidak dikenal)");
            }
        }
        if (!baris2.isEmpty()) {
            String nama2 = ambilNama(baris2);
            int q = ambilJumlah(baris2);
            p2 = cariMenu(nama2);
            if (p2 != null && q > 0) {
                q2 = q;
            } else if (p2 == null) {
                System.err.println("(Abaikan baris 2: menu tidak dikenal)");
            }
        }
        if (!baris3.isEmpty()) {
            String nama3 = ambilNama(baris3);
            int q = ambilJumlah(baris3);
            p3 = cariMenu(nama3);
            if (p3 != null && q > 0) {
                q3 = q;
            } else if (p3 == null) {
                System.err.println("(Abaikan baris 3: menu tidak dikenal)");
            }
        }
        if (!baris4.isEmpty()) {
            String nama4 = ambilNama(baris4);
            int q = ambilJumlah(baris4);
            p4 = cariMenu(nama4);
            if (p4 != null && q > 0) {
                q4 = q;
            } else if (p4 == null) {
                System.err.println("(Abaikan baris 4: menu tidak dikenal)");
            }
        }

        int subtotalPesanan = hitungSubtotalBaris(p1, q1, p2, q2, p3, q3, p4, q4);

        // Keputusan: ada isi pesanan atau tidak (nested if)
        if (subtotalPesanan <= 0) {
            System.out.println("Tidak ada pesanan valid. Program selesai.");
        } else {
            cetakStruk(p1, q1, p2, q2, p3, q3, p4, q4, subtotalPesanan);
        }
        sc.close();
    }

    private static String bacaBaris(Scanner sc, String label) {
        System.out.print(label);
        String s = sc.nextLine();
        return s != null ? s.trim() : "";
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

    /** Pencarian menu: rantai if-else if untuk setiap elemen array (tanpa perulangan). */
    private static Menu cariMenu(String nama) {
        if (nama.equals(DAFTAR_MENU[0].nama)) {
            return DAFTAR_MENU[0];
        } else if (nama.equals(DAFTAR_MENU[1].nama)) {
            return DAFTAR_MENU[1];
        } else if (nama.equals(DAFTAR_MENU[2].nama)) {
            return DAFTAR_MENU[2];
        } else if (nama.equals(DAFTAR_MENU[3].nama)) {
            return DAFTAR_MENU[3];
        } else if (nama.equals(DAFTAR_MENU[4].nama)) {
            return DAFTAR_MENU[4];
        } else if (nama.equals(DAFTAR_MENU[5].nama)) {
            return DAFTAR_MENU[5];
        } else if (nama.equals(DAFTAR_MENU[6].nama)) {
            return DAFTAR_MENU[6];
        } else if (nama.equals(DAFTAR_MENU[7].nama)) {
            return DAFTAR_MENU[7];
        } else {
            return null;
        }
    }

    private static void tampilkanMenuMakanan() {
        System.out.println();
        System.out.println("--- MAKANAN ---");
        if (DAFTAR_MENU[0].kategori.equals("MAKANAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[0]));
        }
        if (DAFTAR_MENU[1].kategori.equals("MAKANAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[1]));
        }
        if (DAFTAR_MENU[2].kategori.equals("MAKANAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[2]));
        }
        if (DAFTAR_MENU[3].kategori.equals("MAKANAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[3]));
        }
        if (DAFTAR_MENU[4].kategori.equals("MAKANAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[4]));
        }
        if (DAFTAR_MENU[5].kategori.equals("MAKANAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[5]));
        }
        if (DAFTAR_MENU[6].kategori.equals("MAKANAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[6]));
        }
        if (DAFTAR_MENU[7].kategori.equals("MAKANAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[7]));
        }
    }

    private static void tampilkanMenuMinuman() {
        System.out.println();
        System.out.println("--- MINUMAN ---");
        if (DAFTAR_MENU[0].kategori.equals("MINUMAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[0]));
        }
        if (DAFTAR_MENU[1].kategori.equals("MINUMAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[1]));
        }
        if (DAFTAR_MENU[2].kategori.equals("MINUMAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[2]));
        }
        if (DAFTAR_MENU[3].kategori.equals("MINUMAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[3]));
        }
        if (DAFTAR_MENU[4].kategori.equals("MINUMAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[4]));
        }
        if (DAFTAR_MENU[5].kategori.equals("MINUMAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[5]));
        }
        if (DAFTAR_MENU[6].kategori.equals("MINUMAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[6]));
        }
        if (DAFTAR_MENU[7].kategori.equals("MINUMAN")) {
            System.out.println(formatBarisMenu(DAFTAR_MENU[7]));
        }
        System.out.println("(Promo beli 1 gratis 1 — kelompok jus — jika subtotal pesanan > Rp "
                + BATAS_PROMO_MINUMAN + ",-)");
    }

    private static String formatBarisMenu(Menu m) {
        return String.format("%-15s | Rp %,d/kategori %-8s%s", m.nama, m.harga, m.kategori,
                m.promoMinumanJus ? " [promo jus]" : "");
    }

    private static int hitungSubtotalBaris(Menu p1, int q1, Menu p2, int q2, Menu p3, int q3, Menu p4, int q4) {
        int t = 0;
        if (p1 != null && q1 > 0) {
            t += p1.harga * q1;
        }
        if (p2 != null && q2 > 0) {
            t += p2.harga * q2;
        }
        if (p3 != null && q3 > 0) {
            t += p3.harga * q3;
        }
        if (p4 != null && q4 > 0) {
            t += p4.harga * q4;
        }
        return t;
    }

    /**
     * Promo beli satu gratis satu untuk kelompok jus: per baris, potongan = floor(qty/2) * harga.
     * Hanya jika {@code subtotalAwal} &gt; {@link #BATAS_PROMO_MINUMAN}.
     */
    private static int hitungPotonganPromoMinuman(Menu p1, int q1, Menu p2, int q2, Menu p3, int q3, Menu p4, int q4,
            int subtotalAwal) {
        int potongan = 0;
        if (subtotalAwal > BATAS_PROMO_MINUMAN) {
            if (p1 != null && q1 > 1 && p1.promoMinumanJus) {
                potongan += (q1 / 2) * p1.harga;
            }
            if (p2 != null && q2 > 1 && p2.promoMinumanJus) {
                potongan += (q2 / 2) * p2.harga;
            }
            if (p3 != null && q3 > 1 && p3.promoMinumanJus) {
                potongan += (q3 / 2) * p3.harga;
            }
            if (p4 != null && q4 > 1 && p4.promoMinumanJus) {
                potongan += (q4 / 2) * p4.harga;
            }
        }
        return potongan;
    }

    private static void cetakStruk(Menu p1, int q1, Menu p2, int q2, Menu p3, int q3, Menu p4, int q4,
            int subtotalAwal) {
        System.out.println();
        System.out.println("================ STRUK PEMBAYARAN ================");

        if (p1 != null && q1 > 0) {
            System.out.printf("%-16s x%-2d @ Rp %,d  =  Rp %,d%n", p1.nama, q1, p1.harga, p1.harga * q1);
        }
        if (p2 != null && q2 > 0) {
            System.out.printf("%-16s x%-2d @ Rp %,d  =  Rp %,d%n", p2.nama, q2, p2.harga, p2.harga * q2);
        }
        if (p3 != null && q3 > 0) {
            System.out.printf("%-16s x%-2d @ Rp %,d  =  Rp %,d%n", p3.nama, q3, p3.harga, p3.harga * q3);
        }
        if (p4 != null && q4 > 0) {
            System.out.printf("%-16s x%-2d @ Rp %,d  =  Rp %,d%n", p4.nama, q4, p4.harga, p4.harga * q4);
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Subtotal item                    Rp %,d%n", subtotalAwal);

        int potonganPromoMinuman = hitungPotonganPromoMinuman(p1, q1, p2, q2, p3, q3, p4, q4, subtotalAwal);
        boolean pakaiPromoMinuman = potonganPromoMinuman > 0;

        int setelahPromoMinuman = subtotalAwal - potonganPromoMinuman;
        if (pakaiPromoMinuman) {
            System.out.printf("Promo minuman (beli 1 gratis 1, jus)  - Rp %,d%n", potonganPromoMinuman);
            System.out.printf("Setelah promo minuman                 Rp %,d%n", setelahPromoMinuman);
        } else {
            if (subtotalAwal > BATAS_PROMO_MINUMAN) {
                System.out.println("Promo minuman belum dipakai "
                        + "(perlu min. 2 jus promo per jenis yang dipesan, atau tidak ada jus promo).");
            } else {
                System.out.println("Promo minuman: subtotal belum > Rp " + BATAS_PROMO_MINUMAN + ",-");
            }
        }

        int setelahDiskon10 = setelahPromoMinuman;
        double nilaiDiskon10 = 0;
        // Syarat soal: "total biaya keseluruhan pesanan" di atas batas; di sini pakai subtotal item.
        boolean syaratDiskon10 = subtotalAwal > BATAS_DISKON_PERSEN;
        if (syaratDiskon10) {
            nilaiDiskon10 = Math.round(setelahPromoMinuman * (DISKON_PERSEN / 100.0));
            setelahDiskon10 = (int) Math.round(setelahPromoMinuman - nilaiDiskon10);
            System.out.printf("Diskon 10%% (subtotal item > Rp %,d) - Rp %,.0f%n", BATAS_DISKON_PERSEN, nilaiDiskon10);
            System.out.printf("Setelah diskon                  Rp %,d%n", setelahDiskon10);
        } else if (subtotalAwal == BATAS_DISKON_PERSEN) {
            System.out.println("Diskon 10%: subtotal item tepat batas, tidak mendapat diskon.");
        } else {
            System.out.println("Diskon 10%: subtotal item belum > Rp " + BATAS_DISKON_PERSEN + ",-");
        }

        double pajak = Math.round(setelahDiskon10 * (PAJAK_PERSEN / 100.0));
        System.out.printf("Pajak %s%% atas Rp %,d            Rp %,.0f%n", "10", setelahDiskon10, pajak);

        double grand = setelahDiskon10 + pajak + BIAYA_PELAYANAN;

        int kodePromo = kodeRingkasanPromo(pakaiPromoMinuman, syaratDiskon10);
        switch (kodePromo) {
            case 0:
                System.out.println("Ringkasan: tanpa promo minuman dan tanpa diskon 10%.");
                break;
            case 1:
                System.out.println("Ringkasan: hanya promo minuman (jus).");
                break;
            case 2:
                System.out.println("Ringkasan: hanya diskon 10%.");
                break;
            case 3:
                System.out.println("Ringkasan: promo minuman (jus) + diskon 10%.");
                break;
            default:
                System.out.println("Ringkasan: tidak terklasifikasi.");
                break;
        }

        System.out.printf("Biaya pelayanan                 Rp %,d%n", BIAYA_PELAYANAN);

        System.out.println("--------------------------------------------------");
        System.out.printf("TOTAL BAYAR                     Rp %,.0f%n", grand);
        System.out.println("==================================================");

        nestedIfRingkasanSkenario(subtotalAwal, pakaiPromoMinuman, syaratDiskon10);
    }

    /** Kode 0–3 untuk {@code switch} pada struk. */
    private static int kodeRingkasanPromo(boolean adaPromoMinuman, boolean adaDiskon10) {
        if (adaPromoMinuman) {
            if (adaDiskon10) {
                return 3;
            } else {
                return 1;
            }
        } else {
            if (adaDiskon10) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    /** Nested if untuk demo struktur keputusan (alur promo vs batas nominal). */
    private static void nestedIfRingkasanSkenario(int subAwal, boolean adaPromoMinuman, boolean syaratDiskon10) {
        System.out.println();
        System.out.println("--- Ringkasan struktur nested if ---");
        if (subAwal > BATAS_PROMO_MINUMAN) {
            if (adaPromoMinuman) {
                System.out.println("Subtotal menembus batas promo minuman; promo jus dipakai.");
            } else {
                System.out.println("Subtotal menembus batas promo minuman, namun tidak ada potongan jus.");
            }
        } else {
            if (subAwal > 0) {
                System.out.println("Subtotal belum menembus batas promo minuman (Rp " + BATAS_PROMO_MINUMAN + ",-).");
            }
        }
        if (syaratDiskon10) {
            if (subAwal > BATAS_DISKON_PERSEN) {
                System.out.println("Subtotal item melewati Rp "
                        + BATAS_DISKON_PERSEN
                        + ",- sehingga diskon 10% (dari nominal setelah promo minuman) berlaku.");
            }
        }
    }
}
