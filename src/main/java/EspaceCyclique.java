import java.util.*;

public class EspaceCyclique {
    //TODO : implémenter le temps de rémission
    private int longeur;
    private int largeur;
    private List<Personne> personnes;
    private Variant maladie;
    private List<Personne> infectes;
    private Map<Personne, List<Personne>> voisins;
    private int numCycle;

    public EspaceCyclique(int longeur, int largeur, List<Personne> personnes, Variant maladie) {
        this.longeur = longeur;
        this.largeur = largeur;
        this.personnes = personnes;
        this.maladie = maladie;
        infectes = new ArrayList<>();
        voisins = new HashMap<>();
        numCycle = 0;
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
        cycleRemission();
        cycleDeces();

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

    private void cycleRemission() {
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
        probaInfection = maladie.getTauxTransmissionInitial() * probaInfection;

        if(infectes.contains(pVoisin))
            return false;

        if(pVoisin.getComportements().contains(Comportement.Protection)) {
            switch (pVoisin.getSensibilite()) {
                case Sensible :
                    if(rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Sensible) / 2) {
                        System.out.println(pVoisin);
                        return true;
                    }
                    break;
                case Neutre :
                    if(rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Neutre) / 2) {
                        System.out.println(pVoisin);
                        return true;
                    }
                    break;
                case Resistant :
                    if(rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Resistant) / 2) {
                        System.out.println(pVoisin);
                        return true;
                    }
                    break;
            }
        } else {
            switch (pVoisin.getSensibilite()) {
                case Sensible :
                    if(rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Sensible)) {
                        System.out.println(pVoisin);
                        return true;
                    }
                    break;
                case Neutre :
                    if(rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Neutre)) {
                        System.out.println(pVoisin);
                        return true;
                    }
                    break;
                case Resistant :
                    if(rand.nextFloat() <= probaInfection * maladie.getTauxTransmission().get(Sensibilite.Resistant)) {
                        System.out.println(pVoisin);
                        return true;
                    }
                    break;
            }
        }
        return false;
    }
}
