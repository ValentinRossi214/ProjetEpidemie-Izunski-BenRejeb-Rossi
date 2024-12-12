import java.util.HashSet;
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
        abcisse = 0;
        ordonnee = 0;
        vaccinations = new HashSet<>();
    }

    public int getDistance(Personne personne) {
        int distance = Math.abs(this.abcisse - personne.getAbcisse()) + Math.abs(this.ordonnee - personne.getOrdonnee());

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

    public int getAbcisse() {
        return abcisse;
    }

    public void setAbcisse(int abcisse) {
        this.abcisse = abcisse;
    }

    public int getOrdonnee() {
        return ordonnee;
    }

    public void setOrdonnee(int ordonnee) {
        this.ordonnee = ordonnee;
    }

    public Set<Comportement> getComportements() {
        return comportements;
    }

    public Sensibilite getSensibilite() {
        return sensibilite;
    }
}
