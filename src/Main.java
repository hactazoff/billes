import guilines.IJeuDesBilles;
import guilines.Lines;

public class Main {
    public static void main(String[] args) {
        IJeuDesBilles monJeu = new MonJeu();
        new Lines("LILines", monJeu);
    }
}