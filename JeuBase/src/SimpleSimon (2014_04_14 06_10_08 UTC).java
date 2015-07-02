/**
 * extends Jeu
 * Created by Nicholas on 23/12/13.
 */
public class SimpleSimon extends Jeu {

    /**
     * Initialisation, 1 type de pile, 1 deck
     */
    public SimpleSimon() {
        int nombre_decks = 1;
        int[] distributionInitial = new int[]{8, 8, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] regleTypes = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


        PileProperties[] pilePropertieses = new PileProperties[distributionInitial.length];
        for (int j = 0; j < distributionInitial.length; j++) {
            pilePropertieses[j] = new PileProperties(distributionInitial[j], regleTypes[j]);
        }
        boolean shuffle = true;
        initialise(nombre_decks, shuffle, pilePropertieses);

    }

    /**
     * bouge soit une carte, soit une groupe de cartes d'une pile a une autre
     * @param numPile1
     * @param numPile2
     * @param premiereCarte
     * @return
     */
    public int[] bouge(int numPile1, int numPile2, int premiereCarte) {
        if (jeuPiles.estTransferPossible(numPile1, numPile2, premiereCarte)) {
            //if ( typePile1 != 2 && typePile2 != 2 ) {
            //  ArrayList<Carte> pile1 = jeuPiles.get(typePile1).get(numPile1);
            //  ArrayList<Carte> pile2 = jeuPiles.get(typePile2).get(numPile2);
          /*  if ( pile2.size() == 0 || (pile2.get(pile2.size()-1).valeur - 1 == pile1.get(premiereCarte).valeur)  ) {
                if (!jeuPiles.estSequence(typePile1, numPile1, premiereCarte, pile1.size(), false, false )) return false;
                jeuPiles.transfer(typePile1,numPile1,typePile2,numPile2,premiereCarte);
                return true;
            }
          */
            if ((0 == jeuPiles.getPileCartes(numPile2).size() ||
                    jeuPiles.estCombinationPossible(numPile1, numPile2, premiereCarte, false, false)) &&
                    jeuPiles.estSequence(numPile1, premiereCarte, jeuPiles.getPileCartes(numPile1).size(),
                            true, false)
                    ) {
                jeuPiles.transfer(numPile1, numPile2, premiereCarte);
                return new int[]{numPile1, numPile2};
            }
            //}
        }
        return new int[]{};
    }

    /**
     * possibilite de retirer les cartes (peut-etre pas implemente graphiquement
     * @param typePile1
     * @param numPile1
     * @param premiereCarte
     * @return
     */
    public boolean retirer(int typePile1, int numPile1, int premiereCarte) {
        PileCartes pile1 = jeuPiles.getPileCartes(numPile1);
        if (
                jeuPiles.estTransferPossible(numPile1, premiereCarte) &&
                        pile1.get(premiereCarte).valeur == NomDeCarte.ROI.getValeurDefault() &&
                        pile1.get(pile1.size() - 1).valeur == NomDeCarte.AS.getValeurDefault() &&
                        jeuPiles.estSequence(numPile1, premiereCarte, pile1.size() - 1, true, false)) {
            /*
            for ( int i = premiereCarte + 1; i < pile1.size(); i++ ) {
                if ( pile1.get(i-1).valeur + 1 != pile1.get(i).valeur &&
                        pile1.get(i-1).couleur == pile1.get(i).couleur ) {
                    return false;
                }
                return true;
            }
            */
            pile1.removeAll();
        }
        return false;
    }

    /**
     * gagne si toute pile est vide
     * @return
     */
    public boolean gagne() {
        gagne = jeuPiles.touteVide();
        return gagne;
    }

    /*
        public ArrayList<ArrayList<PileCartes>> getDisplayGroups() {
            ArrayList<ArrayList<PileCartes>> outList = new ArrayList<ArrayList<PileCartes>>();
            outList.add( this.getJeuPiles() );
            return outList;
        }
        */

    /**
     * return les display groupes
     * @return
     */
    public int[][] getDisplayGroups() {
        int[][] groups = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}};
        return groups;
    }

    /** return les display directions des piles
     *
     */
    public void setDisplayDirections() {
        for (PileCartes pile : jeuPiles) {
            pile.setPileDisplayDirection(PileDisplayDirection.VERTICAL);
        }
        return;
    }

    /** determiner le max pile size
     *
     */
    public void setMaxPileSize() {
        for (PileCartes pile : jeuPiles) {
            pile.setMaxPileSize(16);
        }
        return;
    }


}
