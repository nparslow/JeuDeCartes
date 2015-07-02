/**
 * Couleur: contient les 4 couleurs possibles
 * @author Nicholas Parslow on 23/12/13.
 */
public enum Couleur {
    /**
     * Les constructeurs de TREFLE, CARREAU, COEUR et PIQUE
     * prennent le code ascii de leur symbole
     */
    TREFLE("\u2663"),
    CARREAU("\u2666"),
    COEUR("\u2665"),
    PIQUE("\u2660");

    private final String display;

    Couleur(String display) {
        this.display = display;
    }

    /**
     * toString
     *
     * @return le caractere correspondant au couleur
     */
    public String toString() {
        return this.display;
    }
}
