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
    private int score;

    public MonJeu() {
        this(12,8, 8, 25);
    }

    public MonJeu(int height, int width, int colours, int initial) {
        this.height = height;
        this.width = width;
        this.initial = initial;
        this.color = colours;
    }

    // A vers B
    @Override
    public List<Point> deplace(int y0, int x0, int y1, int x1) {
        List<Point> v = new ArrayList<>();
        if(y0 < 0 || y0 >= height || x0 < 0 || x0 >= width || y1 < 0 || y1 >= height || x1 < 0 || x1 >= width)
            return v;
        if(y0 == y1 && x0 == x1)
            return v;
        if(getCouleur(y0, x0) == IJeuDesBilles.VIDE)
            return v;
        if(getCouleur(y1, x1) != IJeuDesBilles.VIDE)
            return v;
        var path = new PathFinding(width, height);
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                path.setNode(x, y, getCouleur(y, x) == IJeuDesBilles.VIDE ? PathFinding.NodeTypes.EMPTY : PathFinding.NodeTypes.WALL);
        path.setStart(x0, y0);
        path.setEnd(x1, y1);
        var p = path.getPath();
        for(int x = 0; x < width; x++) {
            System.out.print("O");
            for (int y = 0; y < height; y++) {
                PathFinding.Node n = null;
                for (PathFinding.Node node : p) {
                    if (node.x == x && node.y == y) {
                        n = node;
                        break;
                    }
                }
                if (n != null) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("O");
        }
        if(p.length == 0)
            return v;

        grille[y1][x1] = getCouleur(y0, x0);
        grille[y0][x0] = IJeuDesBilles.VIDE;
        v.add(new Point(x1, y1));
        v.add(new Point(x0, y0));

        var aligned = false;
        var li = getAligned();
        while (!li.isEmpty()) {
            v.addAll(li);
            aligned = true;
            li = getAligned();
        }
        if(!aligned)
            v.addAll(poseBillesAjoutees());
        for(Point point : v)
            System.out.println("Point: " + point);
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++)
                System.out.print(grille[y][x] == IJeuDesBilles.VIDE ? " " : grille[y][x]);
            System.out.println();
        }
        return v;
    }

    public List<Point> poseBillesAjoutees() {
        List<Point> li = new ArrayList<>();
        for(int i = 0; i < getNbBillesAjoutees(); i++) {
            List<Point> v = getVides();
            Point p = v.get(nextInt(0, v.size()));
            grille[p.y][p.x] = getNouvellesCouleurs()[i];
            li.add(p);
        }
        setNouvellesCouleurs();
        return li;
    }

    public List<Point> getAligned() {
        List<Point> v = new ArrayList<>();
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++) {
                int c = getCouleur(y, x);
                if(c == IJeuDesBilles.VIDE)
                    continue;
                var l = 1;
                for(int i = x + 1; i < width; i++)
                    if(getCouleur(y, i) == c)
                        l++;
                    else
                        break;
                if(l >= getNbBillesAlignees()) {
                    for(int i = x; i < x + l; i++) {
                        grille[y][i] = IJeuDesBilles.VIDE;
                        v.add(new Point(x, y));
                    }
                    score += l;
                    return v;
                }
                l = 1;
                for(int i = y + 1; i < height; i++)
                    if(getCouleur(i, x) == c)
                        l++;
                    else
                        break;
                if(l >= getNbBillesAlignees()) {
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
        return width;
    }

    @Override
    public int getNbBillesAjoutees() {
        return nextcolors.length;
    }

    public int getNbBillesAlignees() {
        return 5;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getNbLignes() {
        return height;
    }

    @Override
    public int getNbCouleurs() {
        return color;
    }

    private int[] nextcolors = new int[3];
    @Override
    public int[] getNouvellesCouleurs() {
        return nextcolors;
    }

    public void setNouvellesCouleurs() {
        nextcolors = new int[getNbBillesAjoutees()];
        for(int i = 0; i < getNbBillesAjoutees(); i++)
            nextcolors[i] = nextInt(0, color);
    }

    @Override
    public boolean partieFinie() {
        return getVides().isEmpty();
    }

    @Override
    public int getCouleur(int y, int x) {
        if(grille == null)
            return IJeuDesBilles.VIDE;
        return grille[y][x];
    }

    @Override
    public void reinit() {
        score = 0;
        grille = new int[height][width];
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                grille[y][x] = IJeuDesBilles.VIDE;
        for(int i = 0; i < initial; i++) {
            List<Point> v = getVides();
            Point p = v.get(nextInt(0, v.size()));
            grille[p.y][p.x] = nextInt(0, color);
        }
        setNouvellesCouleurs();
    }
}
