package fr.uphf.sae201;

public class Mine extends Batiment {

    public Mine(int id, int stock, Minerais type, Secteur secteur) {
        super(id, stock, type, secteur);
    }

    /* Cette méthode renvoie true lorsque le stock de la mine est superieur à la quantité demander. Si oui la quantité est soustrait au stock de la mine. Sinon elle renvoie False */
    public boolean action(int quantite) {
        if (super.stock >= quantite) {
            super.stock = super.stock - quantite;
            return true;
        }
        return false;
    }

