import java.util.EnumMap;

public class Variant extends Maladie {
    public Variant(String nom,
                   int periodeIncubation,
                   int probabiliteGuerison,
                   int probabiliteDeces,
                   EnumMap<Sensibilite, Float> tauxTransmission,
                   int distanceMaxTransmission) {
        super(nom, periodeIncubation, probabiliteGuerison, probabiliteDeces, tauxTransmission, distanceMaxTransmission);
    }

    public String getNom() {
        return super.getNom();
    }

    public float getPeriodeIncubation() {
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
}
