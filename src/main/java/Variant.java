import java.util.EnumMap;

public class Variant extends Maladie {
    public Variant(String nom,
                   int periodeIncubation,
                   int probabiliteGuerison,
                   int probabiliteDeces,
                   EnumMap<Sensibilite, Float> tauxTransmission) {
        super(nom, periodeIncubation, probabiliteGuerison, probabiliteDeces, tauxTransmission);
    }
}
