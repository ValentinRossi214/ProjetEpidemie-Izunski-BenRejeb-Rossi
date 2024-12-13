public class Vaccination {
    private int nbDosesPrises;
    private final Vaccin vaccin;
    private final Personne personne;

    public Vaccination(Vaccin vaccin, Personne personne) {
        nbDosesPrises = 1;
        this.vaccin = vaccin;
        this.personne = personne;
        if (vaccin.getType() == TypeVaccin.Unidose) {
            personne.setSensibilite();
        }
    }

    //Méthode permettant d'effectuer une deuxième dose à une personne, si c'est possible
    public void faireDeuxiemeDose(){
        this.nbDosesPrises++;
    }

    //Définition des Getters
    //Retourner le nombre de doses prises par une personne lors du ou des vaccinations
    public int getNbDosesPrises() {
        return nbDosesPrises;
    }
    //Retourner le vaccin associé à une vaccination
    public Vaccin getVaccin() {
        return vaccin;
    }
    //Retourner la personne à laquelle est appliquée la vaccination
    public Personne getPersonne(){
        return this.personne;
    }
}
