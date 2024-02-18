import guilines.IJeuDesBilles;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonJeu implements IJeuDesBilles {
    public final int NB_BILLES_A_AJOUTER = 15;
    public final int WIDTH = 8;
    public final int HEIGHT = 16;
    public final Grille grille;
    private final Suggestions suggestions;
    public int score = 0;

    public MonJeu() {
        grille = new Grille(WIDTH, HEIGHT);
        suggestions = new Suggestions();
    }

    public int getNbLignes() {
        return HEIGHT;
    }

    public int getNbColonnes() {
        return WIDTH;
    }

    public int getNbBillesAjoutees() {
        return Suggestions.NB_SUGGESTIONS;
    }

    public int getScore() {
        return score;
    }

    public int getNbCouleurs() {
        return Couleurs.getNbCouleurs();
    }

    public int getCouleur(int y, int x) {
        var c = grille.getValeur(y, x);
        return c == null ? VIDE : c.ordinal();
    }

    public boolean partieFinie() {
        return grille.getVides().length == 0;
    }

    public void reinit() {
        grille.generate(WIDTH, HEIGHT);
        grille.ajouteValeursAleatoires(NB_BILLES_A_AJOUTER);
        suggestions.setNouvelles();
        score = 0;
    }

    public List<Point> deplace(int yA, int xA, int yB, int xB) {
        List<Point> v = new ArrayList<>();
        if (yA < 0 || yA >= HEIGHT || xA < 0 || xA >= WIDTH || yB < 0 || yB >= HEIGHT || xB < 0 || xB >= WIDTH || grille.getValeur(yA, xA) == null || grille.getValeur(yB, xB) != null)
            return v;
        var p = new PathFinding(grille, new Point(xA, yA), new Point(xB, yB)).getPath();
        if (p.length == 0)
            return v;
        grille.setValeur(yB, xB, grille.getValeur(yA, xA));
        grille.setValeur(yA, xA, null);

        var al = grille.getAlignee();
        for(var a : al) {
            System.out.println(a.toString());
        }
        if (al.length > 0) {
            System.out.println("Alignee");
            score += al.length;
            for (var a : al) {
                grille.setValeur(a.y, a.x, null);
                v.add(new Point(a.x, a.y));
            }
        } else {
            System.out.println("Pas alignee");
            for (var a : suggestions.getNouvelles())
                if (a != null) {
                    var c = grille.ajouteValeursAleatoires(1, a)[0];
                    v.add(new Point(c.x, c.y));
                }
        }
        return v;
    }

    public int[] getNouvellesCouleurs() {
        var i = new int[Suggestions.NB_SUGGESTIONS];
        var c = suggestions.getNouvelles();
        for (int j = 0; j < Suggestions.NB_SUGGESTIONS; j++)
            i[j] = c[j] == null ? VIDE : c[j].ordinal();
        System.out.print("Nouvelles couleurs");
        for (var a : i)
            System.out.print(" " + a);
        System.out.println();
        return i;
    }
}
