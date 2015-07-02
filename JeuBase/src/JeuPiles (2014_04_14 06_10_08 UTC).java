import java.util.*;

/**
 * Created by Nicholas on 23/12/13.
 */
public class JeuPiles extends ArrayList<PileCartes> {

    public JeuPiles(LinkedList<Carte> deck, PileProperties[] pilePropertieses) {
        Iterator<Carte> iter = deck.iterator();
        for (PileProperties pileProperties : pilePropertieses) {
            this.add(creerPile(pileProperties, iter));
        }
    }

    /*
        public static GroupePiles creerArray( PileProperties groupePileInstruction, Iterator<Carte> iter ) {
            GroupePiles outarray = new GroupePiles( groupePileInstruction.getPileCarteVisibilite() );
            for (int len : groupePileInstruction.getNumCartesInitials() ) {
                PileCartes tmp = new PileCartes( groupePileInstruction.getPileCarteVisibilite() ); // overkill, we can switch pilecartes back to arraylist<carte>
                int k = len;
                while ( iter.hasNext() && (len < 0 || k > 0 ) ) {
                    tmp.add( iter.next() );
                    k-- ;
                }
                outarray.add( tmp );
            }
            return outarray;
        }
        */
    public PileCartes creerPile(PileProperties pileProperties, Iterator<Carte> iter) {
        PileCartes tmp = new PileCartes(pileProperties); // overkill, we can switch pilecartes back to arraylist<carte>
        int k = pileProperties.getNumCartesInitials();
        while (iter.hasNext() && (pileProperties.getNumCartesInitials() < 0 || k > 0)) {
            tmp.add(iter.next());
            k--;
        }
        return tmp;
    }

    // need error throws
    public Carte getCarte(int pileNumber, int carteNo) {
        return getPileCartes(pileNumber).get(carteNo);
    }

    public PileCartes getPileCartes(int pileNumber) {
        return this.get(pileNumber);
    }

    public boolean estTransferPossible(int numPile1, int premiereCarte) {
        if (numPile1 >= 0 && numPile1 < this.size() &&
                premiereCarte >= 0 && premiereCarte < getPileCartes(numPile1).size()) {
            return true;
        }
        return false;
    }

    public boolean estTransferPossible(int numPile1, int numPile2, int premiereCarte) {
        //System.out.println( numPile1 + " " + numPile2 + " " +premiereCarte);
        if (numPile1 >= 0 && numPile1 < this.size() &&
                numPile2 >= 0 && numPile2 < this.size() &&
                premiereCarte >= 0 && premiereCarte < getPileCartes(numPile1).size()) {
            return true;
        }
        //System.out.println("Transfer not possible!!!");
        return false;
    }

    public void transferRenverse(int numPile1, int numPile2, int premiereCarte) {
        transferRenverse(numPile1, numPile2, premiereCarte, this.get(numPile2).size());
    }

    public void transferRenverse(int numPile1, int numPile2, int premiereCarte, int ouAjouter) {
        PileCartes pile1 = getPileCartes(numPile1);
        List<Carte> subList = pile1.subList(premiereCarte, pile1.size());
        Collections.reverse(subList);
        getPileCartes(numPile2).addAll(ouAjouter, subList);
        pile1.removeAll(subList);
    }

    public void transfer(int numPile1, int numPile2, int premiereCarte) {
        transfer(numPile1, numPile2, premiereCarte, this.get(numPile2).size());
    }

    public void transfer(int numPile1, int numPile2, int premiereCarte, int ouAjouter) {
        PileCartes pile1 = getPileCartes(numPile1);
        List<Carte> subList = pile1.subList(premiereCarte, pile1.size());
        //ArrayList<Carte> subList = (ArrayList<Carte>) pile1.subList(premiereCarte, pile1.size());
        getPileCartes(numPile2).addAll(ouAjouter, subList);
        //System.out.println(subList.size());
        //System.out.println(pile1.size());
        pile1.removeAll(subList);
    }

    public boolean touteVide() {
        for (PileCartes pile : this) {
            if (pile.size() > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean estVide(int pileNum) {
        return get(pileNum).size() == 0;
    }

    public ArrayList<PileCartes> toutDeType(int typeRegle) {
        ArrayList<PileCartes> outList = new ArrayList<PileCartes>();
        for (PileCartes pile : this) {
            if (pile.getRegleType() == typeRegle) {
                outList.add(pile);
            }
        }
        return outList;
    }

    public boolean touteVide(int typeRegle) {
        // need error throw
        for (PileCartes pile : toutDeType(typeRegle)) {
            if (pile.size() != 0) {
                return false;
            }
        }
        return true;
    }

    // pour avoir descendant, mettez 'ascendant' false , derniere est non-inclusive
    public boolean estSequence(int pileNum, int premiere, int derniere, boolean matchCouleur, boolean ascendant) {
        PileCartes pile = getPileCartes(pileNum);
        for (int i = premiere + 1; i < derniere; i++) {
            int delta = -1;
            if (ascendant) delta = 1;
            if (pile.get(i).valeur != pile.get(i - 1).valeur + delta) return false;
            if (matchCouleur && pile.get(i).couleur != pile.get(i - 1).couleur) return false;
        }
        return true;
    }

    public boolean estCombinationPossible(int pileNo1, int pileNo2, int premiere,
                                          boolean matchCouleur, boolean ascendant) {
        PileCartes pile1 = getPileCartes(pileNo1);
        PileCartes pile2 = getPileCartes(pileNo2);
        int delta = -1; // -1 ^ boolean ... possible or not?
        if (ascendant) delta = 1;
        if (pile1.get(premiere).valeur != pile2.get(pile2.size() - 1).valeur + delta) return false;
        if (matchCouleur && pile1.get(premiere).couleur != pile2.get(pile2.size() - 1).couleur) return false;
        return true;
    }

    public ArrayList<PileCartes> getSubElements(int[] elementsToGet) {
        ArrayList<PileCartes> outList = new ArrayList<PileCartes>();
        for (int i : elementsToGet) {
            outList.add(getPileCartes(i));
        }
        return outList;
    }
}

