import guilines.Lines;
import guilines.IJeuDesBilles;
public class Main  {
    public static void main(String[] args) {
        IJeuDesBilles monJeu = new a();
        System.out.println("Main"+monJeu.getNbLignes()+"x"+monJeu.getNbColonnes());
        Lines fenetre = new Lines("LILines", monJeu);
    }
}