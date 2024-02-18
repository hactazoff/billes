import java.util.*;

public class Grille {
    public final int NB_BILLES_ALIGNES = 5;
    private Case[][] grille;
    private int width;
    private int height;

    Grille(int width, int height) {
        generate(width, height);
    }

    void generate(int width, int height) {
        this.width = width;
        this.height = height;
        grille = new Case[height][width];
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                grille[y][x] = new Case(x, y);
    }

    Case[] ajouteValeursAleatoires(int n) {
        return ajouteValeursAleatoires(n, null);
    }

    Case[] ajouteValeursAleatoires(int n, Couleurs c) {
        Random r = new Random();
        List<Case> s = new ArrayList<>();
        List<Case> v = Arrays.asList(getVides());
        for(int i = 0; i < n; i++) {
            if(v.isEmpty())
                break;
            Case p = v.get(r.nextInt(v.size()));
            p.setValeur(c == null ? Couleurs.getCouleurAleatoire() : c);
            s.add(p);
        }
        return s.toArray(new Case[0]);
    }


    Couleurs getValeur(int y, int x) {
        if(y < 0 || y >= height || x < 0 || x >= width)
            return null;
        return grille[y][x].getValeur();
    }

    boolean setValeur(int y, int x, Couleurs valeur) {
        if(y < 0 || y >= height || x < 0 || x >= width)
            return false;
        grille[y][x].setValeur(valeur);
        return true;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    Case[] getVides() {
        List<Case> vides = new ArrayList<>();
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                if(grille[y][x].getValeur() == null)
                    vides.add(grille[y][x]);
        return vides.toArray(new Case[0]);
    }

    Case[] getAlignee() {
        List<Case> a = new ArrayList<>();
        for(int y = 0; y < height - NB_BILLES_ALIGNES + 1; y++)
            for(int x = 0; x < width - NB_BILLES_ALIGNES + 1; x++) {
                Couleurs c = getValeur(y, x);
                if(c == null) continue;
                List<Case> l = new ArrayList<>();
                for(int i = 0; i < NB_BILLES_ALIGNES; i++)
                    if(getValeur(y, x + i) == c && !l.contains(grille[y][x + i]))
                        l.add(grille[y][x + i]);
                    else break;
                if(l.size() == NB_BILLES_ALIGNES) {
                    System.out.println("Alignee H" + l.size());
                    a.addAll(l);
                }
                l.clear();
                for(int i = 0; i < NB_BILLES_ALIGNES; i++)
                    if(getValeur(y + i, x) == c && !l.contains(grille[y + i][x]))
                        l.add(grille[y + i][x]);
                    else break;
                if(l.size() == NB_BILLES_ALIGNES) {
                    System.out.println("Alignee V " + l.size());
                    a.addAll(l);
                }
            }
        return a.toArray(new Case[0]);
    }
}
