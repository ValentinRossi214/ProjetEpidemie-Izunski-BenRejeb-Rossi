import java.util.EnumMap;

public abstract class Maladie {
    private String nom;
    private int periodeIncubation;
    private float probabiliteGuerison;
    private float probabiliteDeces;
    private EnumMap<Sensibilite, Float> tauxTransmission;
    private int distanceMaxTransmission;

    private float tauxTransmissionInitial;

    public Maladie(String nom,
                   int periodeIncubation,
                   float probabiliteGuerison,
                   float probabiliteDeces,
                   EnumMap<Sensibilite, Float> tauxTransmission,
                   int distanceMaxTransmission,
                   float tauxTransmissionInitial) {
        this.nom = nom;
        this.periodeIncubation = periodeIncubation;
        this.probabiliteGuerison = probabiliteGuerison;
        this.probabiliteDeces = probabiliteDeces;
        this.tauxTransmission = tauxTransmission;
        this.distanceMaxTransmission = distanceMaxTransmission;
        this.tauxTransmissionInitial = tauxTransmissionInitial;
    }

    public String getNom() {
        return nom;
    }

    public int getPeriodeIncubation() {
        return periodeIncubation;
    }

    public float getProbabiliteGuerison() {
        return probabiliteGuerison;
    }

    public float getProbabiliteDeces() {
        return probabiliteDeces;
    }

    public EnumMap<Sensibilite, Float> getTauxTransmission() {
        return tauxTransmission;
    }

    public int getDistanceMaxTransmission() {
        return distanceMaxTransmission;
    }

    public float getTauxTransmissionInitial() {
        return tauxTransmissionInitial;
    }
}
