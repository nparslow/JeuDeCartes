/**
 * Created by Nicholas on 28/12/13.
 */
public class PileProperties {
    private int numCartesInitials; // <0  = unlimited
    private int regleType;
    private PileCarteVisibilite pileCarteVisibilite;
    private boolean pileTailleVisibilite;
    private int joueur;
    private boolean pileCarteVisibiliteNaturel;

    public PileProperties(int numCartesInitials, int regleType, PileCarteVisibilite pileCarteVisibilite,
                          boolean pileCarteVisibiliteNaturel,
                          boolean pileTailleVisibilite, int joueur) {
        this.numCartesInitials = numCartesInitials;
        this.regleType = regleType;
        this.pileCarteVisibilite = pileCarteVisibilite;
        this.pileCarteVisibiliteNaturel = pileCarteVisibiliteNaturel;
        this.pileTailleVisibilite = pileTailleVisibilite;
        this.joueur = joueur;
    }

    public PileProperties(int numCartesInitials, int regleType, PileCarteVisibilite pileCarteVisibilite,
                          boolean pileCarteVisibiliteNaturel, boolean pileTailleVisibilite) {
        this(numCartesInitials, regleType, pileCarteVisibilite, pileCarteVisibiliteNaturel, pileTailleVisibilite, 0);
    }

    public PileProperties(int numCartesInitials, int regleType, PileCarteVisibilite pileCarteVisibilite,
                          boolean pileCarteVisibiliteNaturel) {
        this(numCartesInitials, regleType, pileCarteVisibilite, pileCarteVisibiliteNaturel, true);
    }

    public PileProperties(int numCartesInitials, int regleType) {
        this(numCartesInitials, regleType, PileCarteVisibilite.SHOWALL, true);
    }

    public PileProperties(int numCartesInitials) {
        this(numCartesInitials, 0);
    }

    public PileProperties() {
        this(-1);
    }

    public int getNumCartesInitials() {
        return numCartesInitials;
    }

    public PileCarteVisibilite getPileCarteVisibilite() {
        return pileCarteVisibilite;
    }

    public boolean getPileTailleVisibilite() {
        return pileTailleVisibilite;
    }

    public int getRegleType() {
        return regleType;
    }

    public int getJoueur() {
        return joueur;
    }

    public boolean getPileCarteVisibiliteNaturel() {
        return pileCarteVisibiliteNaturel;
    }
}
