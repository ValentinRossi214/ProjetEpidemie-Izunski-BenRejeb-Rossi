import java.util.*;

public class Espace {
    //TODO : implémenter le temps de rémission
    private int longeur;
    private int largeur;
    private List<Personne> personnes;
    private Variant maladie;
    private List<Personne> infectes;
    private Map<Personne, List<Personne>> voisins;
    private int numCycle;

    public Espace(int longeur, int largeur, List<Personne> personnes, Variant maladie) {
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
            for (int j = i + 1 ; j < personnes.size() - i ; j++) {
                Personne personneAComparer = personnes.get(j);
                if (personneCourante.getDistance(personneAComparer) <= maladie.getDistanceMaxTransmission()){
                    voisins.get(personneCourante).add(personneAComparer);
                    voisins.get(personneAComparer).add(personneCourante);
                }
            }
        }
    }

    public void nouveauCycle() {
        numCycle++;
        cycleRemission();
        cycleTransmission();
        cycleDeces();
    }

    public void cycleRemission() {
        Random rand = new Random();
        for (Personne p : infectes) {
            if(rand.nextFloat() <= maladie.getProbabiliteGuerison()) {
                infectes.remove(p);
            }
        }
    }

    public void cycleTransmission() {
        Random rand = new Random();
        for (Personne pInfectee : infectes) {
            for (Personne pVoisin : voisins.get(pInfectee))
                if(pVoisin.getComportements().contains(Comportement.Protection)) {
                    switch (pVoisin.getSensibilite()) {
                        case Sensible :
                            if(rand.nextFloat() <= maladie.getTauxTransmission().get(Sensibilite.Sensible) / 2)
                                infectes.add(pVoisin);
                            break;
                        case Neutre :
                            if(rand.nextFloat() <= maladie.getTauxTransmission().get(Sensibilite.Neutre) / 2)
                                infectes.add(pVoisin);
                            break;
                        case Resistant :
                            if(rand.nextFloat() <= maladie.getTauxTransmission().get(Sensibilite.Resistant) / 2)
                                infectes.add(pVoisin);
                            break;
                    }
                } else {
                    switch (pVoisin.getSensibilite()) {
                        case Sensible :
                            if(rand.nextFloat() <= maladie.getTauxTransmission().get(Sensibilite.Sensible))
                                infectes.add(pVoisin);
                            break;
                        case Neutre :
                            if(rand.nextFloat() <= maladie.getTauxTransmission().get(Sensibilite.Neutre))
                                infectes.add(pVoisin);
                            break;
                        case Resistant :
                            if(rand.nextFloat() <= maladie.getTauxTransmission().get(Sensibilite.Resistant))
                                infectes.add(pVoisin);
                            break;
                }
            }
        }
    }

    public void cycleDeces() {
        Random rand = new Random();
        for (Personne p : infectes) {
            if(rand.nextFloat() <= maladie.getProbabiliteDeces()) {
                decesPersonne(p);
            }
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

}
