/**
 * Carte: porte un couleur, un nom de carte  (e.g. AS, DEUX etc.)
 * et un valeur (actuellement determine purement par son nomDeCarte)
 * @author  Nicholas Parslow on 23/12/13.
 */
public class Carte {
    final Couleur couleur;
    final NomDeCarte nom;
    int valeur;

    public Carte(NomDeCarte nom, Couleur couleur) {
        this.nom = nom;
        this.couleur = couleur;
        this.valeur = nom.getValeurDefault();
    }
    // public Carte( NomDeCarte nom, Couleur couleur, HashMap<NomDeCarte, Int> valeur_map ) {}

    /**
     * toString
     *
     * @return une chaine du nomDeCarte et son couleur
     */
    public String toString() {
        return nom.toString() + couleur.toString();
    }
}
