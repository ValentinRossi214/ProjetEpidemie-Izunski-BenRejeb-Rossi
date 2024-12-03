import java.util.List;

public class Espace {
    private int longeur;
    private int largeur;
    private List<Personne> personnes;

    public Espace(int longeur, int largeur, List<Personne> personnes) {
        this.longeur = longeur;
        this.largeur = largeur;
        this.personnes = personnes;
    }
}
