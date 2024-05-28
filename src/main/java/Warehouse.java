package adrien.sae_201;

public class Warehouse {
    private static int idCounter = 1;
    private int id;
    private int x;
    private int y;
    private String type;
    private int nbM;

    public Warehouse(int id, int x, int y, String type) {
        this.id = idCounter++;
        this.x = x;
        this.y = y;
        this.type = type;
        this.nbM = 0;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public int getNbM() {
        return nbM;
    }

    public void setNbM(int nbM) {
        this.nbM = nbM;
    }
}
