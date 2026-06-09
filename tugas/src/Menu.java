import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Mengelola semua item menu restoran menggunakan ArrayList.
 */
public class Menu {
    private ArrayList<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        items.add(item);
    }

    public int getJumlahItem() {
        return items.size();
    }

    public MenuItem getItem(int indeks) throws MenuItemTidakDitemukanException {
        if (indeks < 0 || indeks >= items.size()) {
            throw new MenuItemTidakDitemukanException(
                    "Indeks menu tidak valid: " + (indeks + 1));
        }
        return items.get(indeks);
    }

    public MenuItem cariBerdasarkanNama(String nama) throws MenuItemTidakDitemukanException {
        int i = 0;
        while ( i < items.size()) {
            if (items.get(i).getNama().equalsIgnoreCase(nama.trim())) {
                return items.get(i);
            }
            i++;
        }
        throw new MenuItemTidakDitemukanException("Menu tidak ditemukan: " + nama);
    }

    /** Tampilkan semua item; polymorphism via tampilMenu(). */
    public void tampilkanSemuaMenu() {
        if (items.isEmpty()) {
            System.out.println("Menu masih kosong.");
            return;
        }
        System.out.println();
        System.out.println("--- DAFTAR MENU RESTORAN ---");
        int i = 0;
        while (i < items.size()) {
            System.out.printf("%2d. %s%n", i + 1, items.get(i).tampilMenu());
            i++;
        }
    }

    public void tampilkanMenuMakanan() {
        System.out.println();
        System.out.println("--- MAKANAN ---");
        int i = 0;
        while (i < items.size()) {
            MenuItem item = items.get(i);
            if (item instanceof Makanan) {
                System.out.println(item.tampilMenu());
            }
            i++;
        }
    }

    public void tampilkanMenuMinuman() {
        System.out.println();
        System.out.println("--- MINUMAN ---");
        int i = 0;
        while (i < items.size()) {
            MenuItem item = items.get(i);
            if (item instanceof Minuman) {
                System.out.println(item.tampilMenu());
            }
            i++;
        }
    }

    public void tampilkanMenuDiskon() {
        System.out.println();
        System.out.println("--- PENAWARAN DISKON ---");
        int i = 0;
        while (i < items.size()) {
            MenuItem item = items.get(i);
            if (item instanceof Diskon) {
                System.out.println(item.tampilMenu());
            }
            i++;
        }
    }

    public void simpanKeFile(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int i = 0;
        while (i < items.size()) {
            writer.write(items.get(i).keBarisFile());
            writer.newLine();
            i++;
        }
        writer.close();
    }

    public void muatDariFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File menu tidak ditemukan: " + path);
        }
        items.clear();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String baris;
        while ((baris = reader.readLine()) != null) {
            baris = baris.trim();
            if (baris.isEmpty()) {
                continue;
            }
            items.add(parseBarisMenu(baris));
        }
        reader.close();
    }

    private MenuItem parseBarisMenu(String baris) {
        String[] bagian = baris.split("\\|");
        String tipe = bagian[0].trim();
        if (tipe.equals("MAKANAN")) {
            return new Makanan(bagian[1].trim(), Double.parseDouble(bagian[2].trim()), bagian[3].trim());
        }
        if (tipe.equals("MINUMAN")) {
            return new Minuman(bagian[1].trim(), Double.parseDouble(bagian[2].trim()), bagian[3].trim());
        }
        if (tipe.equals("DISKON")) {
            return new Diskon(bagian[1].trim(), Double.parseDouble(bagian[2].trim()));
        }
        throw new IllegalArgumentException("Tipe menu tidak dikenal: " + tipe);
    }

    public void isiMenuDefault() {
        items.clear();
        items.add(new Makanan("Nasi Padang", 25000, "Nasi"));
        items.add(new Makanan("Mie Goreng", 18000, "Mie"));
        items.add(new Makanan("Ayam Geprek", 22000, "Ayam"));
        items.add(new Makanan("Nasi Goreng", 20000, "Nasi"));
        items.add(new Minuman("Es Teh Manis", 5000, "Teh"));
        items.add(new Minuman("Kopi Susu", 12000, "Kopi"));
        items.add(new Minuman("Jus Alpukat", 15000, "Jus"));
        items.add(new Minuman("Jus Jeruk", 12000, "Jus"));
        items.add(new Diskon("Diskon Member 10%", 0.10));
        items.add(new Diskon("Diskon Happy Hour 5%", 0.05));
    }
}
