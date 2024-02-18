public class Case {
    public final int x;
    public final int y;
    private Couleurs val;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.val = null;
    }

    public Couleurs getValeur() {
        return val;
    }

    public void setValeur(Couleurs c) {
        this.val = c;
    }

    public String toString() {
        return "Case(" + x + ", " + y + ")";
    }
}
