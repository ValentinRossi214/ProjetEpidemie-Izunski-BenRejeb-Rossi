public class Vaccin {
    private String nom;
    private TypeVaccin type;
    private Variant variantTraite;

    public Vaccin(String nom, TypeVaccin type, Variant variantTraite) {
        this.nom = nom;
        this.type = type;
        this.variantTraite = variantTraite;
    }

    public TypeVaccin getType() {
        return type;
    }

}
