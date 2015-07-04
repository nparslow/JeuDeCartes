import java.util.ArrayList;

/**
 * Un groupe de piles avec leur visibilite comme parametre ajoute
 * Created by Nicholas on 28/12/13.
 */
public class GroupePiles extends ArrayList<PileCartes> {
    private PileCarteVisibilite pileCarteVisibilite;

    public GroupePiles() {
        super();
        pileCarteVisibilite = PileCarteVisibilite.SHOWALL;
    }

    public GroupePiles(PileCarteVisibilite pileCarteVisibilite) {
        super();
        this.pileCarteVisibilite = pileCarteVisibilite;
    }

    public PileCarteVisibilite getPileCarteVisibilite() {
        return pileCarteVisibilite;
    }

    protected void setPileCarteVisibilite(PileCarteVisibilite pileCarteVisibilite) {
        this.pileCarteVisibilite = pileCarteVisibilite;
    }
}
