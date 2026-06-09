/**
 * Pengecualian saat item menu tidak ditemukan.
 */
public class MenuItemTidakDitemukanException extends Exception {
    public MenuItemTidakDitemukanException(String pesan) {
        super(pesan);
    }
}
