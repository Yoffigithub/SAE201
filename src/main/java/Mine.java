package adrien.sae_201;

import java.util.Random;

public class Mine {
    private static int idCounter = 1;
    private int id;
    private int x;
    private int y;
    private String type;
    private int nbM;
    private int initNbM;

    Random random = new Random();
    public Mine(int id, int x, int y, String type) {
        this.id = idCounter++;
        this.x = x;
        this.y = y;
        this.type = type;
        this.nbM = random.nextInt(51) + 50;
        this.initNbM = nbM;
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

    public int getInitNbM() {
        return initNbM;
    }

    public void setNbM(int nbM) {
        this.nbM = nbM;
    }

    public int generateRandomNbM() {
        Random random = new Random();
        //genere un nb entre 50 et 100
        return random.nextInt(51) + 50;
    }
}

