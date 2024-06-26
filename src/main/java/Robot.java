package adrien.sae_201;

import java.util.List;

public class Robot {
    private static int idCounter = 1;
    private int id;
    private int x;
    private int y;
    private String type;
    private int storageCapacity;
    private int extractionCapacity;
    private int currentLoad;

    public Robot(int id, int x, int y, String type, int storageCapacity, int extractionCapacity) {
        this.id = idCounter++;
        this.x = x;
        this.y = y;
        this.type = type;
        this.storageCapacity = storageCapacity;
        this.extractionCapacity = extractionCapacity;
        this.currentLoad = 0;
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

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public int getExtractionCapacity() {
        return extractionCapacity;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean canMoveNorth(String[][] grid) {
        return x > 0 && !grid[x - 1][y].equals("X") && !grid[x - 1][y].equals("R");
    }

    public boolean canMoveEast(String[][] grid) {
        return y < grid[0].length - 2 && !grid[x][y + 2].equals("X") && !grid[x][y + 2].equals("R");
    }

    public boolean canMoveSouth(String[][] grid) {
        return x < grid.length - 2 && !grid[x + 1][y].equals("X") && !grid[x + 1][y].equals("R");
    }

    public boolean canMoveWest(String[][] grid) {
        return y > 0 && !grid[x][y - 1].equals("X") && !grid[x][y - 1].equals("R");
    }

    public boolean moveNorth(String[][] grid) {
        if (x > 1 && !grid[x - 2][y].equals("X") && !grid[x - 2][y].equals("R")) {
            x -= 2;
            return true;
        } else {
            return false;
        }
    }

    public boolean moveEast(String[][] grid) {
        if (y < grid[0].length - 2 && !grid[x][y + 2].equals("X") && !grid[x][y + 2].equals("R")) {
            y += 2;
            return true;
        } else {
            return false;
        }
    }

    public boolean moveSouth(String[][] grid) {
        if (x < grid.length - 2 && !grid[x + 2][y].equals("X") && !grid[x + 2][y].equals("R")) {
            x += 2;
            return true;
        } else {
            return false;
        }
    }

    public boolean moveWest(String[][] grid) {
        if (y > 1 && !grid[x][y - 2].equals("X") && !grid[x][y - 2].equals("R")) {
            y -= 2;
            return true;
        } else {
            return false;
        }
    }

    public void extract(Mine mine) {
        if (mine.getType().equals(this.type) && currentLoad < storageCapacity) {
            int extractable = Math.min(extractionCapacity, mine.getNbM());
            extractable = Math.min(extractable, storageCapacity - currentLoad);
            mine.setNbM(mine.getNbM() - extractable);
            currentLoad += extractable;
            System.out.println("Le robot" + this.id + " a extrait " + extractable + " minerais.");
        } else {
            System.out.println("Le robot" + this.id + " ne peut pas extraire de minerais ici.");
        }
    }

    public void deposit(Warehouse warehouse) {
        if (warehouse.getType().equals(this.type) && currentLoad > 0) {
            warehouse.setNbM(warehouse.getNbM() + currentLoad);
            currentLoad = 0;
        }
    }

    // ajout des 2 methodes
    public void followPath(String[][] grid, List<int[]> path) {
        for (int[] step : path) {
            int nextX = step[0];
            int nextY = step[1];
            if (canMoveTo(grid, nextX, nextY)) {
                grid[this.x][this.y] = " ";
                grid[this.x][this.y + 1] = " ";
                this.x = nextX;
                this.y = nextY;
                grid[this.x][this.y] = "R";
                grid[this.x][this.y + 1] = Integer.toString(this.id);
            }
        }
    }

    private boolean canMoveTo(String[][] grid, int x, int y) {
        return !grid[x][y].equals("X") && !grid[x][y].equals("R") && (x % 2 == 1 && y % 2 == 0);
    }
}
