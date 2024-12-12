import java.util.EnumMap;

public abstract class Maladie {
    private String nom;
    private float periodeIncubation;
    private float probabiliteGuerison;
    private float probabiliteDeces;
    private EnumMap<Sensibilite, Float> tauxTransmission;
    private int distanceMaxTransmission;

    public Maladie(String nom,
                   float periodeIncubation,
                   float probabiliteGuerison,
                   float probabiliteDeces,
                   EnumMap<Sensibilite, Float> tauxTransmission,
                   int distanceMaxTransmission) {
        this.nom = nom;
        this.periodeIncubation = periodeIncubation;
        this.probabiliteGuerison = probabiliteGuerison;
        this.probabiliteDeces = probabiliteDeces;
        this.tauxTransmission = tauxTransmission;
        this.distanceMaxTransmission = distanceMaxTransmission;
    }

    public String getNom() {
        return nom;
    }

    public float getPeriodeIncubation() {
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
}
