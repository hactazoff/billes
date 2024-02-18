public enum Couleurs {
    VERT,
    ORANGE,
    CYAN,
    BLEU,
    ROSE,
    ROUGE,
    JAUNE,
    VIOLET;

    public static Couleurs fromValue(int value) {
        for(Couleurs c : values())
            if(c.ordinal() == value)
                return c;
        return null;
    }

    public static int getNbCouleurs() {
        return Couleurs.values().length;
    }

    public static Couleurs getCouleurAleatoire() {
        return Couleurs.values()[(int) (Math.random() * getNbCouleurs())];
    }
}
