public class Vaccination {
    //TODO: Ajouter l'accès à un vaccin d'une personne
    private int nbDosesPrises;
    private final Vaccin vaccin;

    public Vaccination(Vaccin vaccin) {
        nbDosesPrises = 1;
        this.vaccin = vaccin;
    }

    public int getNbDosesPrises() {
        return nbDosesPrises;
    }

    public void faireDeuxiemeDose () {
        if(vaccin.getType() == TypeVaccin.Unidose) {
            throw new IllegalArgumentException("Ce vaccin ne nécessite qu'une seule dose.");
        }
        if(nbDosesPrises == 2) {
            throw new IllegalArgumentException("Cette personne à déjà reçu les deux doses de ce vaccin.");
        }

        nbDosesPrises++;
    }

    public Vaccin getVaccin() {
        return vaccin;
    }
}
