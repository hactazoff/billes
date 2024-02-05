import guilines.IJeuDesBilles;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonJeu implements IJeuDesBilles {
    private int[][] grille;
    private int height;
    private int width;
    private int color;
    private int initial;

    public MonJeu() {
        this(12,8, 8, 5);
    }

    public MonJeu(int height, int width, int colours, int initial) {
        this.height = height;
        this.width = width;
        this.initial = initial;
        this.color = colours;
        reinit();
    }

    // A vers B
    @Override
    public List<Point> deplace(int y0, int x0, int y1, int x1) {
        System.out.println("deplace");
        List<Point> v = new ArrayList<>();
        if(y0 < 0 || y1 < 0 || x0 < 0 || x1 < 0 || y0 >= height || y1 >= height || x0 >= width || x1 >= width)
            return v;
        if(grille[y0][x0] == IJeuDesBilles.VIDE || grille[y1][x1] != IJeuDesBilles.VIDE)
            return v;
        int c = grille[y0][x0];
        grille[y0][x0] = IJeuDesBilles.VIDE;
        grille[y1][x1] = c;
        v.add(new Point(x0, y0));
        v.add(new Point(x1, y1));
        return v;
    }

    public List<Point> getVides() {
        List<Point> v = new ArrayList<>();
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                if(getCouleur(y, x) == IJeuDesBilles.VIDE)
                    v.add(new Point(x, y));
        return v;
    }

    public int nextInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    @Override
    public int getNbColonnes() {
        System.out.println("getNbColonnes " + width);
        return width;
    }

    @Override
    public int getNbBallesAjoutees() {
        return 3;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int getNbLignes() {
        System.out.println("getNbLignes " + height);
        return height;
    }

    @Override
    public int getNbCouleurs() {
        System.out.println("getNbCouleurs " + color);
        return color;
    }

    @Override
    public int[] getNouvellesCouleurs() {
        System.out.println("getNouvellesCouleurs");
        return new int[] {
            nextInt(0, color),
            nextInt(0, color),
            nextInt(0, color)
        };
    }

    @Override
    public boolean partieFinie() {
        return false;
    }

    @Override
    public int getCouleur(int y, int x) {
        // System.out.println("getCouleur " + y + " " + x + " " + grille[y][x]);
        return grille[y][x];
    }

    @Override
    public void reinit() {
        System.out.println("reinit 0");
        grille = new int[height][width];
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                grille[y][x] = IJeuDesBilles.VIDE;
        System.out.println("reinit 1");

        for(int i = 0; i < initial; i++) {
            List<Point> v = getVides();
            Point p = v.get(nextInt(0, v.size()));
            grille[p.y][p.x] = nextInt(0, color);
        }
        System.out.println("reinit 2");
    }
}
