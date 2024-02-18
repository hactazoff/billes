public class Suggestions {
    public static final int NB_SUGGESTIONS = 3;
    Couleurs[] suggestions;

    public Suggestions() {
        suggestions = new Couleurs[NB_SUGGESTIONS];
        for(int i = 0; i < NB_SUGGESTIONS; i++)
            suggestions[i] = null;
    }

    void setNouvelles() {
        for(int i = 0; i < NB_SUGGESTIONS; i++)
            suggestions[i] = Couleurs.getCouleurAleatoire();
    }

    public Couleurs[] getNouvelles() {
        return suggestions;
    }
}
