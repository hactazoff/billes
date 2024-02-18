import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import guilines.IJeuDesBilles;

import java.util.Random;

public class a implements IJeuDesBilles {
    public static final int NB_BILLES_ALIGNES = 3;
    public static final int VIDE = -1;
    private int[][] grille;
    private int hauteur;
    private int largeur;
    private int couleur;
    private int initial;
    private int score;

    public a() {
        this.hauteur = 16;
        this.largeur = 8;
        this.couleur = 8;
        this.initial = 5;
        reinit(); // Appelez reinit() pour initialiser la grille avec des valeurs par défaut
    }

    public a(int hauteur, int largeur, int couleur, int initial) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.couleur = couleur;
        this.initial = initial;
    }

    public List<Point> deplace(int ligD, int colD, int ligA, int colA) {
        List<Point> casesModifiees = new ArrayList<>();

        if(ligD < 0 || ligD >= hauteur || colD < 0 || colD >= largeur || ligA < 0 || ligA >= hauteur || colA < 0 || colA >= largeur)
            return casesModifiees;
        if(getCouleur(ligD, colD) == IJeuDesBilles.VIDE || getCouleur(ligA, colA) != IJeuDesBilles.VIDE)
            return casesModifiees;

        grille[ligA][colA] = grille[ligD][colD];
        grille[ligD][colD] = VIDE;

        if(getAligned().isEmpty()) {
            for(int clr : getNouvellesCouleurs())
            {
                var v = getVides();
                if(v.isEmpty())
                    break;
                var p = v.get(nextInt(0, v.size() - 1));
                grille[p.y][p.x] = clr;
                casesModifiees.add(new Point(p.x, p.y));
            }
        }

        casesModifiees.add(new Point(ligD, colD));
        casesModifiees.add(new Point(ligA, colA));
        return casesModifiees;
    }

    public List<Point> getAligned() {
        List<Point> v = new ArrayList<>();
        for(int y = 0; y < hauteur; y++)
            for(int x = 0; x < largeur; x++) {
                int c = getCouleur(y, x);
                if(c == IJeuDesBilles.VIDE)
                    continue;
                var l = 1;
                for(int i = x + 1; i < largeur; i++)
                    if(getCouleur(y, i) == c)
                        l++;
                    else
                        break;
                if(l >= NB_BILLES_ALIGNES) {
                    for(int i = x; i < x + l; i++) {
                        grille[y][i] = IJeuDesBilles.VIDE;
                        v.add(new Point(x, y));
                    }
                    score += l;
                    return v;
                }
                l = 1;
                for(int i = y + 1; i < hauteur; i++)
                    if(getCouleur(i, x) == c)
                        l++;
                    else
                        break;
                if(l >= NB_BILLES_ALIGNES) {
                    for(int i = y; i < y + l; i++) {
                        grille[i][x] = IJeuDesBilles.VIDE;
                        v.add(new Point(x, y));
                    }
                    score += l;
                    return v;
                }
            }
        return v;
    }

    public int nextInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public int getCouleur(int lig, int col) {
        return grille[lig][col]; // Accès à la grille avec les indices corrects
    }

    @Override
    public boolean partieFinie() {
        return false;
    }

    @Override
    public void reinit() {
        grille = new int[hauteur][largeur];
        for(int y = 0; y < hauteur; y++)
            for(int x = 0; x < largeur; x++)
                grille[y][x] = IJeuDesBilles.VIDE;
        for(int i = 0; i < initial; i++) {
            var v = getVides();
            if(v.isEmpty())
                break;
            var p = v.get(nextInt(0, v.size() - 1));
            grille[p.y][p.x] = nextInt(0, couleur - 1);
        }
        score = 0;
    }

    private List<Point> getVides() {
        List<Point> v = new ArrayList<>();
        for(int y = 0; y < hauteur; y++)
            for(int x = 0; x < largeur; x++)
                if(getCouleur(y, x) == IJeuDesBilles.VIDE)
                    v.add(new Point(x, y));
        return v;
    }

    public int getNbColonnes() {
        return largeur;
    }

    @Override
    public int getNbBillesAjoutees() {
        return 3;
    }

    public int getScore() {
        return 0;
    }

    public int getNbLignes() {
        return hauteur;
    }

    public int getNbCouleurs() {
        return couleur;
    }

    public int[] getNouvellesCouleurs() {
        return new int[]{
                nextInt(0, couleur), // Utilisez couleur + 1 comme borne supérieure
                nextInt(0, couleur),
                nextInt(0, couleur)
        };
    }
}