import java.util.*;

public class EspaceCyclique {
    private int longeur;
    private int largeur;
    private List<Personne> personnes;
    private int nbPersonnes = 0;
    private Variant maladie;
    private List<Personne> infectes;
    private Map<Personne, List<Personne>> voisins;
    private int numCycle;
    private Map<Personne, Integer> personnesEnRemission;

    public EspaceCyclique(int longeur, int largeur, List<Personne> personnes, Variant maladie) {
        this.longeur = longeur;
        this.largeur = largeur;
        this.personnes = personnes;
        this.nbPersonnes = personnes.size();
        this.maladie = maladie;
        infectes = new ArrayList<>();
        voisins = new HashMap<>();
        numCycle = 0;
        personnesEnRemission = new HashMap<>();
    }

    public void initialiserEspace() {
        Random rand = new Random();
        for (Personne p : personnes) {
            p.setAbcisse(rand.nextInt(largeur));
            p.setOrdonnee(rand.nextInt(longeur));
        }

        patientZero();
        detecterVoisins();

        System.out.println("\n### Voisins\n");
        for (Personne p : voisins.keySet()) {
            System.out.println(p);
            for (Personne pp : voisins.get(p)){
                System.out.println(" -> " + pp);
            }
        }

        System.out.println("\n### Patient 0\n");

        for (Personne p : infectes) {
            System.out.println(p);
        }
    }

    private void patientZero() {
        Random rand = new Random();
        Personne personneInfectee = personnes.get(rand.nextInt(personnes.size()));
        infectes.add(personneInfectee);
    }

    private void detecterVoisins() {
        for (Personne p : personnes) {
            voisins.put(p, new ArrayList<>());
        }

        for (int i = 0 ; i < personnes.size() ; i++) {
            Personne personneCourante = personnes.get(i);
            System.out.println(personneCourante);
            for (int j = i + 1 ; j < personnes.size() ; j++) {
                Personne personneAComparer = personnes.get(j);
                System.out.println(personneCourante.getDistance(personneAComparer) + " -> " + personneAComparer);
                if (personneCourante.getDistance(personneAComparer) <= maladie.getDistanceMaxTransmission()){
                    voisins.get(personneCourante).add(personneAComparer);
                    voisins.get(personneAComparer).add(personneCourante);
                }
            }
        }
    }

    public void nouveauCycle() {
        System.out.println("\n##########################################");
        System.out.println("NOUVEAU CYCLE\n");
        numCycle++;
        cycleTransmission();
        cycleGuerison();
        cycleDeces();
        cycleSortieRemission();

        System.out.println("\nToutes les personnes malades : \n");
        for(Personne p : infectes) {
            System.out.println(p);
        }
        System.out.println("\nToutes les personnes saines : \n");
        for(Personne p : personnes) {
            if (!infectes.contains(p))
                System.out.println(p);
        }
    }

    private void cycleGuerison() {
        System.out.println("\n### Personnes guéries\n");

        Random rand = new Random();
        List<Personne> personnesGueries = new ArrayList<>();

        for (Personne p : infectes) {
            if(rand.nextFloat() <= maladie.getProbabiliteGuerison()) {
                System.out.println(p);
                personnesGueries.add(p);
            }
        }
        infectes.removeAll(personnesGueries);
        EntrerEnRemission(personnesGueries);
    }

    private void cycleTransmission() {
        System.out.println("\n### Personnes infectées\n");

        List<Personne> personnesInfectees = new ArrayList<>();
        boolean infectee;


        for (Personne pInfectee : infectes) {
            for (Personne pVoisin : voisins.get(pInfectee)) {
                infectee = tenterInfecterVoisin(pInfectee, pVoisin);
                if (infectee)
                    personnesInfectees.add(pVoisin);
            }
        }

        infectes.addAll(personnesInfectees);
    }

    private void cycleDeces() {
        System.out.println("\n### Personnes Mortes\n");

        Random rand = new Random();
        List<Personne> personnesMortes = new ArrayList<>();

        for (Personne p : infectes) {
            if(rand.nextFloat() <= maladie.getProbabiliteDeces()) {
                System.out.println(p);
                personnesMortes.add(p);
            }
        }

        for (Personne p : personnesMortes) {
            decesPersonne(p);
        }
    }

    private void cycleSortieRemission() {
        List<Personne> personnesSortantRemission = new ArrayList<>();

        for (Personne p : personnesEnRemission.keySet()) {
            if(numCycle - personnesEnRemission.get(p) >= p.getDureeRemission()) {
                personnesSortantRemission.add(p);
            }
        }

        for (Personne p : personnesSortantRemission) {
            personnesEnRemission.remove(p);
        }
    }

    private void decesPersonne(Personne personneDecede) {
        infectes.remove(personneDecede);
        personnes.remove(personneDecede);
        voisins.remove(personneDecede);
        for (Personne p : voisins.keySet()){
            voisins.get(p).remove(personneDecede);
        }
    }

    private boolean tenterInfecterVoisin(Personne pInfectee, Personne pVoisin) {
        Random rand = new Random();

        float probaInfection = 1 - ((float) pInfectee.getDistance(pVoisin) / maladie.getDistanceMaxTransmission());

        if(infectes.contains(pVoisin))
            return false;

        if(pVoisin.getComportements().contains(Comportement.Protection))
            probaInfection = (maladie.getTauxTransmissionInitial() /2) * probaInfection;
        else
            probaInfection = maladie.getTauxTransmissionInitial() * probaInfection;


        if (pVoisin.getSensibilite() == Sensibilite.Resistant || personnesEnRemission.containsKey(pVoisin)) {
            if (rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Resistant)) {
                System.out.println(pVoisin);
                return true;
            }
        } else if (pVoisin.getSensibilite() == Sensibilite.Neutre) {
            if (rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Neutre)) {
                System.out.println(pVoisin);
                return true;
            }
        } else if (pVoisin.getSensibilite() == Sensibilite.Sensible) {
            if (rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Sensible)) {
                System.out.println(pVoisin);
                return true;
            }
        }

        return false;
    }

    private void EntrerEnRemission(List<Personne> personnesGueries) {
        for (Personne p : personnesGueries) {
            personnesEnRemission.put(p, numCycle);
        }
    }

    public int getNbPersonnes(){
        return this.nbPersonnes;
    }

    //Définition d'une méthode permettant d'ajouter une nouvelle personne à l'espace,
    //tout en mettant à jour le nombre d'individus suite à l'ajout
    public void ajoutPersonne(Personne p){
        personnes.add(p);
        this.nbPersonnes++;
    }

    //Définition de la méthode permettant d'accéder aux vaccination(s) d'une personne
    public Set<Vaccination> getVaccinations_Personne(Personne personne) throws IllegalArgumentException{
        //Vérifier si la liste de personnes n'est pas vide
        if(!personnes.isEmpty()){
            boolean testExistencePersonne= false;
            for(Personne p : personnes){
                if (p.equals(personne)) {
                    testExistencePersonne = true;
                    break;
                }
            }
            if (!testExistencePersonne) {
                throw new IllegalArgumentException("Cette personne ne fait pas partie de cette espace");
            }
            for(Personne p : personnes){
                if (p.equals(personne)){
                    return p.getVaccinations();
                }
            }
        }
        return null;
    }

    //Définition d'une méthode vérifiant si une personne a effectué une vaccination par un vaccin spécifique
    public boolean verifExistenceVaccin(Vaccin vaccin, Vaccination vaccination){
        return vaccination.getVaccin().equals(vaccin);
    }

    //Définition d'une méthode permettant d'accéder à une vaccination d'une personne donnée, en faisant recours aux méthodes préalablement définies
    public Vaccination getVaccination_Personne(Personne personne, Vaccin vaccin) throws IllegalArgumentException{
        Set<Vaccination> vaccinations= getVaccinations_Personne(personne);
        if(vaccinations != null){
            boolean verifierExistenceVaccin=false;
            for(Vaccination v : vaccinations){
                verifierExistenceVaccin= verifExistenceVaccin(vaccin, v);
                if (verifierExistenceVaccin) {
                    break;
                }
            }
            if (!verifierExistenceVaccin) {
                throw new IllegalArgumentException("Ce vaccin n'est pas utilisé par cette personne");
            }

            for(Vaccination v : vaccinations){
                if (v.getVaccin().equals(vaccin)) {
                    return v;
                }
            }
        }
        return null;
    }

    //Définition d'une méthode permettant de savoir le nombre de doses effectués pour une vaccination spécifique d'une personne, en faisant recours à la méthode getVaccination_Personne()
    public int getNbDosesPrises_Personne(Personne p, Vaccin v){
        Vaccination vaccination= getVaccination_Personne(p, v);
        if (vaccination == null) {
            throw new IllegalArgumentException("Il n'existe pas de vaccination pour la combinaison d'arguments donnés");
        }
        return vaccination.getNbDosesPrises();
    }

}
