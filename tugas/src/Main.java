import java.io.IOException;
import java.util.Scanner;

/**
 * Aplikasi manajemen restoran — Tugas Praktik 3 (OOP).
 */
public class Main {

    private static final String FILE_MENU = "data/menu.txt";
    private static final String FILE_STRUK = "data/struk/struk.txt";

    private static Menu menuRestoran;
    private static Pesanan pesananAktif;

    public static void main(String[] args) {
        menuRestoran = new Menu();
        pesananAktif = new Pesanan();
        muatMenuAwal();

        Scanner sc = new Scanner(System.in);
        boolean jalan = true;

        System.out.println("========== MANAJEMEN RESTORAN (PRAKTIK 3) ==========");

        while (jalan) {
            tampilkanMenuUtama();
            System.out.print("Pilih [0-8]: ");
            String pilihan = sc.nextLine().trim();

            switch (pilihan) {
                case "1":
                    tambahItemKeMenu(sc);
                    break;
                case "2":
                    menuRestoran.tampilkanSemuaMenu();
                    break;
                case "3":
                    terimaPesanan(sc);
                    break;
                case "4":
                    pesananAktif.tampilkanRingkasan();
                    break;
                case "5":
                    hapusBarisPesanan(sc);
                    break;
                case "6":
                    tampilkanStrukDanTotal();
                    break;
                case "7":
                    kelolaFileMenu(sc);
                    break;
                case "8":
                    kelolaFileStruk(sc);
                    break;
                case "0":
                    jalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }

        System.out.println("Program selesai. Terima kasih.");
        sc.close();
    }

    private static void muatMenuAwal() {
        try {
            menuRestoran.muatDariFile(FILE_MENU);
            System.out.println("Menu dimuat dari " + FILE_MENU);
        } catch (IOException e) {
            System.out.println("File menu belum ada, memakai menu default.");
            menuRestoran.isiMenuDefault();
        }
    }

    private static void tampilkanMenuUtama() {
        System.out.println();
        System.out.println("--- Menu Utama ---");
        System.out.println("1. Tambah item ke menu (makanan / minuman / diskon)");
        System.out.println("2. Tampilkan menu restoran");
        System.out.println("3. Terima pesanan pelanggan");
        System.out.println("4. Lihat ringkasan pesanan");
        System.out.println("5. Hapus baris pesanan");
        System.out.println("6. Hitung total & tampilkan struk");
        System.out.println("7. Simpan / muat daftar menu (file)");
        System.out.println("8. Simpan / muat struk pesanan (file)");
        System.out.println("0. Keluar");
    }

    private static void tambahItemKeMenu(Scanner sc) {
        System.out.println();
        System.out.println("--- Tambah Item ---");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        System.out.println("4. Tambah beberapa menu sekaligus");
        System.out.print("Pilih jenis: ");
        String jenis = sc.nextLine().trim();

        if (jenis.equals("4")) {
            tambahBeberapaMenu(sc);
            return;
        }

        try {
            if (jenis.equals("1")) {
                tambahSatuMakanan(sc);
            } else if (jenis.equals("2")) {
                tambahSatuMinuman(sc);
            } else if (jenis.equals("3")) {
                tambahSatuDiskon(sc);
            } else {
                System.out.println("Pilihan jenis tidak valid.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Input angka tidak valid: " + e.getMessage());
        }
    }

    private static void tambahBeberapaMenu(Scanner sc) {
        System.out.print("Berapa item yang ingin ditambahkan? ");
        int banyak = bacaAngkaPositif(sc);
        if (banyak <= 0) {
            System.out.println("Jumlah tidak valid.");
            return;
        }
        int ke = 1;
        while (ke <= banyak) {
            System.out.println();
            System.out.println("--- Item " + ke + " dari " + banyak + " ---");
            System.out.println("Jenis (1=Makanan, 2=Minuman, 3=Diskon): ");
            String jenis = sc.nextLine().trim();
            try {
                if (jenis.equals("1")) {
                    tambahSatuMakanan(sc);
                } else if (jenis.equals("2")) {
                    tambahSatuMinuman(sc);
                } else if (jenis.equals("3")) {
                    tambahSatuDiskon(sc);
                } else {
                    System.out.println("Jenis dilewati.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input angka tidak valid, item dilewati.");
            }
            ke++;
        }
    }

    private static void tambahSatuMakanan(Scanner sc) {
        System.out.print("Nama makanan: ");
        String nama = sc.nextLine().trim();
        System.out.print("Harga: ");
        double harga = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Jenis makanan: ");
        String jenis = sc.nextLine().trim();
        menuRestoran.tambahItem(new Makanan(nama, harga, jenis));
        System.out.println("Makanan \"" + nama + "\" ditambahkan.");
    }

    private static void tambahSatuMinuman(Scanner sc) {
        System.out.print("Nama minuman: ");
        String nama = sc.nextLine().trim();
        System.out.print("Harga: ");
        double harga = Double.parseDouble(sc.nextLine().trim());
        System.out.print("Jenis minuman: ");
        String jenis = sc.nextLine().trim();
        menuRestoran.tambahItem(new Minuman(nama, harga, jenis));
        System.out.println("Minuman \"" + nama + "\" ditambahkan.");
    }

    private static void tambahSatuDiskon(Scanner sc) {
        System.out.print("Nama penawaran diskon: ");
        String nama = sc.nextLine().trim();
        System.out.print("Persen diskon (contoh 10 untuk 10%): ");
        double persen = Double.parseDouble(sc.nextLine().trim());
        menuRestoran.tambahItem(new Diskon(nama, persen / 100.0));
        System.out.println("Diskon \"" + nama + "\" ditambahkan.");
    }

    private static void terimaPesanan(Scanner sc) {
        menuRestoran.tampilkanSemuaMenu();
        System.out.println();
        System.out.println("Format: nomor = jumlah  (contoh: 1 = 2)");
        System.out.println("Kosongkan baris untuk selesai.");

        int baris = 1;
        while (baris <= 20) {
            System.out.print("Pesanan " + baris + ": ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                break;
            }

            try {
                String kode = ambilSebelumEquals(input);
                int qty = ambilSetelahEquals(input);
                if (qty <= 0) {
                    System.out.println("Jumlah harus > 0.");
                    continue;
                }

                MenuItem item = cariItemInput(kode);
                pesananAktif.tambahItem(item, qty);
                System.out.println("  -> ditambahkan: " + item.getNama() + " x" + qty);
            } catch (MenuItemTidakDitemukanException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Format jumlah tidak valid.");
            }
            baris++;
        }
    }

    private static MenuItem cariItemInput(String kode) throws MenuItemTidakDitemukanException {
        try {
            int nomor = Integer.parseInt(kode.trim());
            return menuRestoran.getItem(nomor - 1);
        } catch (NumberFormatException e) {
            return menuRestoran.cariBerdasarkanNama(kode);
        }
    }

    private static void hapusBarisPesanan(Scanner sc) {
        if (pesananAktif.kosong()) {
            System.out.println("Tidak ada pesanan.");
            return;
        }
        pesananAktif.tampilkanRingkasan();
        System.out.print("Nomor baris yang dihapus (0 = batal): ");
        int nomor = bacaAngkaPositif(sc);
        if (nomor == 0) {
            return;
        }
        try {
            pesananAktif.hapusBaris(nomor - 1);
            System.out.println("Baris pesanan dihapus.");
        } catch (MenuItemTidakDitemukanException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void tampilkanStrukDanTotal() {
        if (pesananAktif.kosong()) {
            System.out.println("Belum ada pesanan untuk dicetak.");
            return;
        }
        pesananAktif.cetakStruk();
        nestedIfRingkasanDiskon();
    }

    /** Demo struktur keputusan nested if pada skenario diskon. */
    private static void nestedIfRingkasanDiskon() {
        double subtotal = pesananAktif.hitungSubtotalMakananMinuman();
        double potongan = pesananAktif.hitungTotalPotonganDiskon();
        System.out.println();
        System.out.println("---- Note ----");
        if (subtotal > 0) {
            if (potongan > 0) {
                System.out.println("Pesanan memiliki item diskon; potongan diterapkan.");
            } else {
                System.out.println("Belum ada item Diskon pada pesanan.");
            }
        } else {
            System.out.println("Subtotal nol; tidak ada yang dihitung.");
        }
    }

    private static void kelolaFileMenu(Scanner sc) {
        System.out.println();
        System.out.println("1. Simpan menu ke file");
        System.out.println("2. Muat menu dari file");
        System.out.print("Pilih: ");
        String p = sc.nextLine().trim();
        if (p.equals("1")) {
            try {
                menuRestoran.simpanKeFile(FILE_MENU);
                System.out.println("Menu disimpan ke " + FILE_MENU);
            } catch (IOException e) {
                System.out.println("Gagal menyimpan: " + e.getMessage());
            }
        } else if (p.equals("2")) {
            try {
                menuRestoran.muatDariFile(FILE_MENU);
                System.out.println("Menu dimuat dari " + FILE_MENU);
            } catch (IOException e) {
                System.out.println("Gagal memuat: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Format file tidak valid: " + e.getMessage());
            }
        }
    }

    private static void kelolaFileStruk(Scanner sc) {
        System.out.println();
        System.out.println("1. Simpan struk ke file");
        System.out.println("2. Muat struk dari file");
        System.out.print("Pilih: ");
        String p = sc.nextLine().trim();
        if (p.equals("1")) {
            if (pesananAktif.kosong()) {
                System.out.println("Belum ada pesanan untuk disimpan.");
                return;
            }
            try {
                pesananAktif.simpanStrukKeFile(FILE_STRUK);
                System.out.println("Struk disimpan ke " + FILE_STRUK);
            } catch (IOException e) {
                System.out.println("Gagal menyimpan struk: " + e.getMessage());
            }
        } else if (p.equals("2")) {
            try {
                pesananAktif.muatStrukDariFile(FILE_STRUK);
            } catch (IOException e) {
                System.out.println("Gagal memuat struk: " + e.getMessage());
            }
        }
    }

    private static String ambilSebelumEquals(String baris) {
        int i = baris.indexOf('=');
        if (i <= 0) {
            return baris.trim();
        }
        return baris.substring(0, i).trim();
    }

    private static int ambilSetelahEquals(String baris) {
        int i = baris.indexOf('=');
        if (i < 0 || i >= baris.length() - 1) {
            throw new NumberFormatException("Format harus: nomor = jumlah");
        }
        return Integer.parseInt(baris.substring(i + 1).trim());
    }

    private static int bacaAngkaPositif(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
