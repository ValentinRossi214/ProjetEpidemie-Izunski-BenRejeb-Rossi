import java.util.*;

public class Main {
    public static void main(String[] args) {
        Sensibilite sensibilite1 = Sensibilite.Sensible;
        Sensibilite sensibilite2 = Sensibilite.Sensible;
        Sensibilite sensibilite3 = Sensibilite.Sensible;

        Set<Comportement> comportements1 = new HashSet<>();
        comportements1.add(Comportement.DistanciationSociale);
        comportements1.add(Comportement.Protection);

        Set<Comportement> comportements2 = new HashSet<>();

        Set<Comportement> comportements3 = new HashSet<>();

        Set<Comportement> comportements4 = new HashSet<>();
        comportements4.add(Comportement.Protection);

        Set<Comportement> comportements5 = new HashSet<>();

        Personne personne1 = new Personne("Dupont", "Jean", sensibilite1, 12, comportements1);
        Personne personne2 = new Personne("Martin", "Sophie", sensibilite2, 8, comportements2);
        Personne personne3 = new Personne("Durand", "Alice", sensibilite3, 15, comportements3);
        Personne personne4 = new Personne("Bernard", "Luc", sensibilite1, 10, comportements4);
        Personne personne5 = new Personne("Morel", "Emma", sensibilite2, 5, comportements5);

        List<Personne> personneList = new ArrayList<>();
        personneList.add(personne1);
        personneList.add(personne2);
        personneList.add(personne3);
        personneList.add(personne4);
        personneList.add(personne5);

        EnumMap<Sensibilite, Float> facteursTransmission = new EnumMap<>(Sensibilite.class);
        facteursTransmission.put(Sensibilite.Sensible, 1.0F);
        facteursTransmission.put(Sensibilite.Neutre, 0.9F);
        facteursTransmission.put(Sensibilite.Resistant, 0.8F);

        Variant grippe = new Variant("Grippe", 2, 0.4f, 0.0f, facteursTransmission, 10, 0.5f);

        Vaccin vaccin = new Vaccin("Vaccin Anti Grippe", TypeVaccin.Unidose, grippe);


        EspaceCyclique espaceCyclique = new EspaceCyclique(10, 10, personneList, grippe);

        espaceCyclique.initialiserEspace();

        personne1.seFaireVacciner(vaccin);
        personne2.seFaireVacciner(vaccin);
        espaceCyclique.nouveauCycle();
        espaceCyclique.nouveauCycle();
        espaceCyclique.nouveauCycle();
        espaceCyclique.nouveauCycle();
        espaceCyclique.nouveauCycle();

    }
}
