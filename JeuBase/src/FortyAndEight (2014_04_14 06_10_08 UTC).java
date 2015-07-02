/**
 * extension de la classe abstrait jeu
 * 1 joueur
 * Created by Nicholas on 23/12/13.
 */
public class FortyAndEight extends Jeu {

    int tourCounter;

    /**
     * Initialisation: 4 types de piles
     */
    public FortyAndEight() {
        int nombre_decks = 2;
        tourCounter = 0;
        // piles = 0, bases = 1, pot = 2, talon = 3
        int[] distributionInitial = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1};
        int[] regleTypes = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3};

        PileProperties[] pilePropertieses = new PileProperties[distributionInitial.length];
        for (int j = 0; j < distributionInitial.length; j++) {
            PileCarteVisibilite pileCarteVisibilite = PileCarteVisibilite.SHOWALL;
            boolean pileCarteVisibiliteNatural = true;
            boolean pileTailleShow = true;
            if (regleTypes[j] == 1) {
                pileCarteVisibilite = PileCarteVisibilite.SHOWTOPONLY;
                pileTailleShow = false;
            }
            if (regleTypes[j] == 2) {
                pileCarteVisibilite = PileCarteVisibilite.SHOWTOPONLY;
                pileTailleShow = false;
            }
            if (regleTypes[j] == 3) {
                pileCarteVisibilite = PileCarteVisibilite.SHOWNONE;
                //pileCarteVisibiliteNatural = false;  // pas encore implemente
                pileTailleShow = false;
            }
            pilePropertieses[j] = new PileProperties(distributionInitial[j], regleTypes[j],
                    pileCarteVisibilite, pileCarteVisibiliteNatural, pileTailleShow);
        }
        boolean shuffle = true;
        initialise(nombre_decks, shuffle, pilePropertieses);
    }
/*
    public boolean bouge( int typePile1, int numPile1, int typePile2, int numPile2, int premiereCarte ){
        if ( jeuPiles.estTransferPossible(typePile1,numPile1,typePile2,numPile2,premiereCarte) ) {
            ArrayList<Carte> pile1 = jeuPiles.get(typePile1).get(numPile1);
            ArrayList<Carte> pile2 = jeuPiles.get(typePile2).get(numPile2);
            if ( typePile1 < 3 ) {
                if ( typePile2 == 0 ) {
                    if ( premiereCarte == jeuPiles.get(typePile1).size() ) {
                        if ( pile2.size() == 0 ||
                             (pile2.get(pile2.size()-1).valeur + 1 == pile1.get(premiereCarte).valeur &&
                              pile2.get(pile2.size()-1).couleur == pile1.get(premiereCarte).couleur      )
                        ) {
                            for ( int i = premiereCarte + 1; i < pile1.size(); i++ ) {
                                if ( pile1.get(i).valeur - 1 != pile1.get(i-1).valeur ||
                                        pile1.get(i).couleur != pile1.get(i-1).couleur) {
                                    return false;
                                }
                            }
                            jeuPiles.transfer(typePile1,numPile1,typePile2,numPile2,premiereCarte);
                            return true;
                        }
                    }
                } else if ( typePile2 == 1 ) {
                    if ( premiereCarte == jeuPiles.get(typePile1).size() ) {
                        if ( (pile2.size() == 0 && pile1.get(premiereCarte).valeur == 1) ||
                                (pile2.get(pile2.size()-1).valeur + 1 == pile1.get(premiereCarte).valeur &&
                                        pile2.get(pile2.size()-1).couleur == pile1.get(premiereCarte).couleur      )
                                ) {
                            for ( int i = premiereCarte + 1; i < pile1.size(); i++ ) {
                                if ( pile1.get(i).valeur - 1 != pile1.get(i-1).valeur ||
                                        pile1.get(i).couleur != pile1.get(i-1).couleur) {
                                    return false;
                                }
                            }
                            jeuPiles.transfer(typePile1,numPile1,typePile2,numPile2,premiereCarte);
                            return true;
                        }
                }
            } else if ( typePile1 == 3 ) {

            }
        }
        return false;
    }
    */

    /**
     * bouger une carte seule d'une pile a une autre
     * @param numPile1
     * @param numPile2
     * @param premiereCarte
     * @return
     */
    public int[] bouge(int numPile1, int numPile2, int premiereCarte) {
        return bouge(numPile1, numPile2, premiereCarte, false);
    }

    /**
     * bouger avec ou non la possibilite de bouger un groupe de cartes
     * @param numPile1
     * @param numPile2
     * @param premiereCarte
     * @param groupePossible
     * @return
     */
    public int[] bouge(int numPile1, int numPile2, int premiereCarte, boolean groupePossible) {
        if (jeuPiles.estTransferPossible(numPile1, numPile2, premiereCarte)) {
            PileCartes pile1 = jeuPiles.getPileCartes(numPile1);
            PileCartes pile2 = jeuPiles.getPileCartes(numPile2);
            int typePile1 = pile1.getRegleType();
            int typePile2 = pile2.getRegleType();
            if (typePile1 < 3) {
                //System.out.println("test0");
                if (typePile1 == 2 && typePile2 == 3 && pile2.size() == 0 && tourCounter == 0) {
                    jeuPiles.transferRenverse(numPile1, numPile2, 0);
                    tourCounter++;
                    return new int[]{numPile1, numPile2};
                } else if (groupePossible || premiereCarte == pile1.size() - 1) {
                    if (typePile2 == 0) {  // situation des piles, alors descendant
                        //System.out.println("test2");
                        if (pile2.size() == 0 ||
                                jeuPiles.estCombinationPossible(numPile1, numPile2, premiereCarte,
                                        true, false)
                                ) {
                            jeuPiles.transfer(numPile1, numPile2, premiereCarte);
                            return new int[]{numPile1, numPile2};
                        }
                    } else if (typePile2 == 1) { // situation des bases, alors ascendant
                        if ((pile2.size() == 0 && pile1.get(premiereCarte).valeur == 1) ||
                                (pile2.size() != 0 && jeuPiles.estCombinationPossible(numPile1, numPile2, premiereCarte,
                                        true, true))
                                ) {
                            jeuPiles.transfer(numPile1, numPile2, premiereCarte);
                            return new int[]{numPile1, numPile2};
                        }
                    }
                }
            } else if (typePile1 == 3 && typePile2 == 2 && premiereCarte == (pile1.size() - 1)) {
                jeuPiles.transfer(numPile1, numPile2, premiereCarte);
                return new int[]{numPile1, numPile2};
            }
            //System.out.println( pile1.size()-1) ;

        }
        return new int[]{};
    }

    /**
     * retirer ne joue pas un role, un error serait meilleur
     * @param typePile1
     * @param numPile1
     * @param premiereCarte
     * @return
     */
    public boolean retirer(int typePile1, int numPile1, int premiereCarte) {
        return true;
    }

    /**
     * si les cartes sont tous en ordre dans les bons piles, c'est gagne
      */
    public boolean gagne() {
        for (int i = 0; i < jeuPiles.size(); i++) {
            if (!(jeuPiles.getPileCartes(i).size() == NomDeCarte.values().length) ||
                    !jeuPiles.estSequence(i, 0, jeuPiles.getPileCartes(i).size(), true, true)
                    ) {
                gagne = false;
                return gagne;
            }
        }
        gagne = true;
        return gagne;
    }

    /*
        public ArrayList<ArrayList<PileCartes>> getDisplayGroups() {
            ArrayList<ArrayList<PileCartes>> outList = new ArrayList<ArrayList<PileCartes>>();
            outList.add(getJeuPiles().toutDeType(1));
            outList.add( getJeuPiles().toutDeType(0));
            ArrayList<PileCartes> finalPiles= getJeuPiles().toutDeType(2);
            finalPiles.addAll(getJeuPiles().toutDeType(3));
            outList.add( finalPiles );
            return outList;
        }
        */

    /**
     * pour forty and seven (et forty and eight) les display groups sont 2 lignes + les 2 autres piles
     * @return
     */
    public int[][] getDisplayGroups() {
        int[][] groups;
        /* // debug mode ne marche pas avec le GUI pour 40 & 8 /7 pour le moment
        if ( debugMode ) {
            // pour avoir assez de place:
            groups = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7}, {8, 9, 10, 11, 12, 13, 14, 15}, {16}, {17}};
        } else {
        */
            groups = new int[][]{{0, 1, 2, 3, 4, 5, 6, 7}, {8, 9, 10, 11, 12, 13, 14, 15}, {16, 17}};
        //}
        return groups;
    }

    /**
     * un melange de horizontale and verticale
     */
    public void setDisplayDirections() {
        for (PileCartes pile : jeuPiles) {
            if (pile.getRegleType() < 2) {
                pile.setPileDisplayDirection(PileDisplayDirection.VERTICAL);
            } else {
                pile.setPileDisplayDirection(PileDisplayDirection.HORIZONTAL);
                //if ( pile.getRegleType() == 3 ) { pile.setPileDisplayDirectionNatural( false ); } // pas encore implemente
            }
        }
    }

    /** determiner la taille max des piles
     *
     */
    public void setMaxPileSize() {
        for (PileCartes pile : jeuPiles) {
            switch (pile.getRegleType()) {
                case 0:
                    pile.setMaxPileSize(16);
                    break;
                case 1:
                    pile.setMaxPileSize(13);
                    break;
                default:
                    pile.setMaxPileSize(72);
                    break;
            }
        }
    }
}

