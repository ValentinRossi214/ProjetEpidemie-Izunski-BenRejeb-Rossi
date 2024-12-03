import java.util.Set;

public class Personne {
    private String nom;
    private String prenom;
    private Sensibilite sensibilite;
    private int abcisse;
    private int ordonnee;
    private int dureeRemission;
    private Set<Comportement> comportements;
    private Set<Vaccination> vaccinations;

    public Personne(String nom,
                    String prenom,
                    Sensibilite sensibilite,
                    int dureeRemission,
                    Set<Comportement> comportements) {
        this.nom = nom;
        this.prenom = prenom;
        this.sensibilite = sensibilite;
        this.dureeRemission = dureeRemission;
        this.comportements = comportements;
    }

    public int getDistance(Personne personne) {
        int distance = Math.abs(this.abcisse - personne.abcisse) + Math.abs(this.ordonnee - personne.ordonnee);

        if (comportements.contains(Comportement.DistanciationSociale)) {
            return distance * 2;
        } else {
            return distance;
        }
    }

    public boolean seFaireVacciner(Vaccin vaccin) {

        for (Vaccination vaccination : vaccinations) {
            if (vaccination.getVaccin().equals(vaccin)) {

                if (vaccin.getType() == TypeVaccin.Unidose) {
                    throw new IllegalArgumentException("Ce vaccin ne nécessite qu'une seule dose.");
                } else if (vaccination.getNbDosesPrises() >= 2) {
                    throw new IllegalArgumentException("Cette personne à déjà reçu les deux doses de ce vaccin.");
                } else {
                    vaccination.faireDeuxiemeDose();
                    return true;
                }
            }
        }

        vaccinations.add(new Vaccination(vaccin));
        return true;
    }

    public Set<Vaccination> getVaccinations() {
        return vaccinations;
    }
    ;
}
