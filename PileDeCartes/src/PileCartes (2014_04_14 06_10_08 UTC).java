import java.util.ArrayList;

/**
 * Created by Nicholas Parslow on 28/12/13.
 */
public class PileCartes extends ArrayList<Carte> {
    private PileCarteVisibilite pileCarteVisibilite;
    private boolean montreTailleDePile;
    private PileDisplayDirection pileDisplayDirection;
    private boolean pileDisplayDirectionNatural; // true = top -> bottom or left -> right, false = opposite
    private int regleType;
    private int joueur;
    private int pileIdentite;
    private int maxPileSize;

    public PileCartes(PileProperties pileProperties) {
        this(pileProperties.getRegleType(), pileProperties.getPileCarteVisibilite(),
                pileProperties.getPileCarteVisibiliteNaturel(),
                pileProperties.getPileTailleVisibilite(), pileProperties.getJoueur());
    }

    public PileCartes(int regleType, PileCarteVisibilite pileCarteVisibilite, boolean pileDisplayDirectionNatural,
                      boolean montreTailleDePile, int joueur) {
        this.regleType = regleType;
        this.pileCarteVisibilite = pileCarteVisibilite;
        this.pileDisplayDirectionNatural = pileDisplayDirectionNatural;
        this.montreTailleDePile = montreTailleDePile;
        this.joueur = joueur;

        // default value, should incorporate better:
        pileDisplayDirection = PileDisplayDirection.VERTICAL;
        maxPileSize = 13;
    }

    public PileCartes(int regleType, PileCarteVisibilite pileCarteVisibilite, boolean pileDisplayDirectionNatural,
                      boolean montreTailleDePile) {
        this(regleType, pileCarteVisibilite, montreTailleDePile, pileDisplayDirectionNatural, 0);
    }
    public PileCartes(int regleType, PileCarteVisibilite pileCarteVisibilite, boolean pileDisplayDirectionNatural) {
        this(regleType, pileCarteVisibilite, pileDisplayDirectionNatural, false);
    }

    public PileCartes(int regleType, PileCarteVisibilite pileCarteVisibilite ) {
        this(regleType, pileCarteVisibilite, true);
    }

    public PileCartes(int regleType) {
        this(regleType, PileCarteVisibilite.SHOWALL, true);
    }

    public PileCarteVisibilite getPileCarteVisibilite() {
        return pileCarteVisibilite;
    }

    protected void setPileCarteVisibilite(PileCarteVisibilite pileCarteVisibilite) {
        this.pileCarteVisibilite = pileCarteVisibilite;
    }

    public int getRegleType() {
        return regleType;
    }

    public int getJoueur() {
        return joueur;
    }

    public void removeAll() {
        removeRange(0, size());
    }

    public void setPileDisplayDirection(PileDisplayDirection pileDisplayDirection) {
        this.pileDisplayDirection = pileDisplayDirection;
    }

    public PileDisplayDirection getPileDisplayDirection() {
        return pileDisplayDirection;
    }

    public boolean getMontreTailleDePile() {
        return montreTailleDePile;
    }
    public void setMontreTailleDePile( boolean montreTailleDePile ) {
        this.montreTailleDePile = montreTailleDePile;
    }

    public void setMaxPileSize(int maxPileSize) {
        this.maxPileSize = maxPileSize;
    }

    public int getMaxPileSize() {
        return maxPileSize;
    }

    public void setPileDisplayDirectionNatural(boolean pileDisplayDirectionNatural) {
        this.pileDisplayDirectionNatural = pileDisplayDirectionNatural;
    }

    public boolean getPileDisplayDirectionNatural() {
        return pileDisplayDirectionNatural;
    }
}
