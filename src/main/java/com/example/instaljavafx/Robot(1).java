package fr.uphf.sae201;

public class Robot implements Couleurs {
    private int id;
    private int stock;
    private int limite;
    private int capacite;
    private Minerais type;

    private Secteur secteur;

    public Robot(int id, int limite, int capacite, Minerais type, Secteur secteur) {
        this.id = id;
        this.stock = 0;
        this.limite = limite;
        this.capacite = capacite;
        this.type = type;
        this.secteur = secteur;
    }

    /*Dans le cas ou l'objet est une instance de Mine la méthode qui permet au robot de récolter des minerais, la quantité
     * récupéré sera stocker dans le stock du robot.
     * Dans le cas ou l'objet est une instance de la classe Entrepot, cette méthode permet de déposer son stock
     * actuel dans l'entrepot.*/
    public boolean action() {
        Batiment batiment = this.secteur.obtenirBatiment();
        if (batiment != null && this.secteur == batiment.obtenirSecteur() && batiment.obtenirType() == this.type) {
            int quantite = 0;
            if (batiment instanceof Mine) {
                if (batiment.obtenirStock() >= capacite) {
                    quantite = capacite;
                } else {
                    quantite = batiment.obtenirStock();
                }
                if (quantite > limite) {
                    quantite = limite;
                }
                if (batiment.action(quantite)) {
                    stock += quantite;
                    return true;

                }
            }
            if (batiment instanceof Entrepot) {
                if (quantite >= capacite) {
                    quantite = capacite;
                } else {
                    quantite = stock;
                }
                if (batiment.action(quantite)) {
                    stock -= quantite;
                    return true;
                }

            }
        }
        return false;
    }

    public int obtenirId() {
        return this.id;
    }

    /* Méthode qui permet de récupérer le stock actuel du robot */
    public int obtenirStock() {
        return stock;
    }

    /* Méthode qui permet de récupérer la limite actuel du robot */
    public int obtenirLimite() {
        return limite;
    }

    /* Méthode qui permet de récupérer le type de minerais associé au robot actuel. */
    public Minerais obtenirType() {
        return type;
    }

    /* Méthode qui permet de récupérer le secteur actuel du robot */
    public Secteur obtenirSecteur() {
        return secteur;
    }

    /* Méthode qui permet de déplacer le robot dans 4 directions : "N" , "E" , "S" , "O". A chaque tour. */
    public boolean deplacer(char direction) {

        int nx = 0, ny = 0;

        if (direction == 'N') {
            nx = this.secteur.obtenirX() + 1;
            ny = this.secteur.obtenirY();

        } else if (direction == 'S') {
            nx = this.secteur.obtenirX() - 1;
            ny = this.secteur.obtenirY();
        } else if (direction == 'W') {
            nx = this.secteur.obtenirX();
            ny = this.secteur.obtenirY() - 1;
        } else if (direction == 'E') {
            nx = this.secteur.obtenirX();
            ny = this.secteur.obtenirY() + 1;
        } else {
            return false;
        }

        if ((nx >= 0 && nx < 10) && (ny >= 0 && ny < 10)) {
            if (!secteur.obtenirMonde().obtenirSecteur(nx, ny).occuper(this)) {
                if (secteur.obtenirMonde().obtenirSecteur(nx, ny).ajoutRobot(this)) {
                    if (secteur.retierRobot(this)) {
                        secteur = secteur.obtenirMonde().obtenirSecteur(nx, ny);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public String affichageGrille() {
        return (type == Minerais.OR ? ANSI_YELLOW_BACKGROUND : ANSI_WHITE_BACKGROUND) + "R" + (id < 10 ? "0" + id : id) + ANSI_RESET;
    }


    public String affichageInformations() {
        return "M" + this.id + "\t" + secteur.obtenirX() + " " + secteur.obtenirY() + "\t" + (this.type == Minerais.NICKEL ? "NI" : "OR") + "\t" + this.stock + "/" + limite;
    }

}
