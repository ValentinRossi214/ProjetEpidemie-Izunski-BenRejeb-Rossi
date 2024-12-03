import java.util.EnumMap;

public abstract class Maladie {
    private String nom;
    private int periodeIncubation;
    private int probabiliteGuerison;
    private int probabiliteDeces;
    private EnumMap<Sensibilite, Float> tauxTransmission;

    public Maladie(String nom, int periodeIncubation, int probabiliteGuerison, int probabiliteDeces, EnumMap<Sensibilite, Float> tauxTransmission) {
        this.nom = nom;
        this.periodeIncubation = periodeIncubation;
        this.probabiliteGuerison = probabiliteGuerison;
        this.probabiliteDeces = probabiliteDeces;
        this.tauxTransmission = tauxTransmission;
    }
}
