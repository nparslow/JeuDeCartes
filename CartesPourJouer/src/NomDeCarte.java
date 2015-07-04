/**
 * NomDeCarte: enum, contient le nom de la carte et son valeur de defaut
 *@author  Nicholas on 23/12/13.
 */
public enum NomDeCarte {
    /**
     * les constructeurs prennent un valeur de defaut
     * et un string pour le display
     */
    AS(1, "A"),
    DEUX(2, "2"),
    TROIS(3, "3"),
    QUATRE(4, "4"),
    CINQ(5, "5"),
    SIX(6, "6"),
    SEPT(7, "7"),
    HUIT(8, "8"),
    NEUF(9, "9"),
    DIX(10, "10"),
    VALET(11, "V"),
    DAME(12, "D"),
    ROI(13, "R");

    private final int valeur_default;
    private final String representation;

    NomDeCarte(int valeur_default, String representation) {
        this.valeur_default = valeur_default;
        this.representation = representation;
    }

    public int getValeurDefault() {
        return valeur_default;
    }

    /**
     * toString
     *
     * @return la representation du nom en chaine de caracteres
     */
    public String toString() {
        return this.representation;
    }
}
