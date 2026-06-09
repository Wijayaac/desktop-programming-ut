/**
 * Subkelas MenuItem untuk penawaran diskon khusus.
 */
public class Diskon extends MenuItem {
    /** Nilai diskon desimal, mis. 0.10 = 10%. */
    private double diskon;

    public Diskon(String nama, double diskon) {
        super(nama, 0, "DISKON");
        this.diskon = diskon;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    @Override
    public String tampilMenu() {
        return String.format("[DISKON]  %-18s | potongan: %.0f%%",
                getNama(), diskon * 100);
    }

    @Override
    public String keBarisFile() {
        return "DISKON|" + getNama() + "|" + diskon;
    }
}
