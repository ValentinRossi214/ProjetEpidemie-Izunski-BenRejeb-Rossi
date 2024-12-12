import java.util.EnumMap;

public class Variant extends Maladie {
    public Variant(String nom,
                   int periodeIncubation,
                   float probabiliteGuerison,
                   float probabiliteDeces,
                   EnumMap<Sensibilite, Float> tauxTransmission,
                   int distanceMaxTransmission,
                   float tauxTransmissionInitial) {
        super(nom, periodeIncubation, probabiliteGuerison, probabiliteDeces, tauxTransmission, distanceMaxTransmission, tauxTransmissionInitial);
    }

    public String getNom() {
        return super.getNom();
    }

    public int getPeriodeIncubation() {
        return super.getPeriodeIncubation();
    }

    public float getProbabiliteGuerison() {
        return super.getProbabiliteGuerison();
    }

    public float getProbabiliteDeces() {
        return super.getProbabiliteDeces();
    }

    public EnumMap<Sensibilite, Float> getTauxTransmission() {
        return super.getTauxTransmission();
    }

    public int getDistanceMaxTransmission() {
        return super.getDistanceMaxTransmission();
    }

    public float getTauxTransmissionInitial() {
        return super.getTauxTransmissionInitial();
    }
}
