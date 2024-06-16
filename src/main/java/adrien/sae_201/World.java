package adrien.sae_201;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class World {
    private static List<Mine> mines = new ArrayList<>();
    public static void main(String[] args) {
        int sectors = 10;
        int totalSectors = sectors * sectors;
        // utile pour afficher les lettres et chiffres pour les mines ou les plans d'eau
        String[][] grid = new String[sectors * 2][sectors * 2];
        // nbre de plans d'eau
        int waterSectors = 10;
        // nbre de tours
        int tour = 1;
        // nbre de terrain (au moins 90)
        int terrainSectors = totalSectors - waterSectors;
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        // initialise la grille avec des terrains
        for (int i = 0; i < sectors * 2; i++) {
            for (int j = 0; j < sectors * 2; j++) {
                grid[i][j] = " ";
            }
        }

        // test
        //grid[0][1] = "01";
        //grid[1][1] = "11";
        //grid[0][0] = "00";
        //grid[0][3] = "03";

        // Crée une liste pour stocker les mines
        List<Mine> mines = new ArrayList<>();
        // Crée une liste pour stocker les entrepots
        List<Warehouse> warehouses = new ArrayList<>();
        // Crée une liste pour stocker les robots
        List<Robot> robots = new ArrayList<>();

        // Génère aléatoirement le nombre de mines de nickel et d'or entre 1 et 2
        int nickelMinesCount = random.nextInt(2) + 1;
        int goldMinesCount = random.nextInt(2) + 1;

        // Place les mines de nickel
        for (int i = 0; i < nickelMinesCount; i++) {
            int x, y;
            do {
                x = random.nextInt(sectors) * 2;
                y = random.nextInt(sectors) * 2; // Assure que y est toujours pair
                //System.out.println("ici");
            } while (!isEmpty(grid, x, y) || !isEmpty(grid, x + 1, y)); // Vérifie si c'est vide
            mines.add(new Mine(mines.size() + 1, x, y, "NI"));
        }

        // Place les mines d'or
        for (int i = 0; i < goldMinesCount; i++) {
            int x, y;
            do {
                x = random.nextInt(sectors) * 2;
                y = random.nextInt(sectors) * 2; // Assure que y est toujours pair
            } while (!isEmpty(grid, x, y) || !isEmpty(grid, x + 1, y)); // Vérifie si c'est vide
            mines.add(new Mine(mines.size() + 1, x, y, "OR"));
        }

        // Place les mines sur la grille
        for (Mine mine : mines) {
            grid[mine.getX()][mine.getY()] = "M";
            // Convertit l'id de la mine en string et l'affiche a coté du M
            grid[mine.getX()][mine.getY() + 1] = Integer.toString(mine.getId());
        }

        // Placement des plans d'eau
        // nbWater = entre 1 et 10 plans d'eau
        int nbWater = random.nextInt(10) + 1;
        for (int i = 0; i < nbWater; i++) {
            int x, y;
            do {
                x = random.nextInt(sectors) * 2;
                y = random.nextInt(sectors) * 2;
                //System.out.println("ici");
            } while (!isEmpty(grid, x, y) || !isEmpty(grid, x + 1, y) || !isEmpty(grid, x, y + 1) || !isEmpty(grid, x + 1, y + 1)); // Vérifie si le secteur est vide
            grid[x][y] = "X";
            grid[x + 1][y] = "X";
            grid[x][y + 1] = "X";
            grid[x + 1][y + 1] = "X";
        }

        // Place les entrepots
        // utilse pour un entrepot nickel et un or
        List<String> tmp = new ArrayList<>();
        tmp.add("NI");
        tmp.add("OR");
        int k = 0;
        for (int i = 0; i < 2; i++) {
            int x, y;
            do {
                x = random.nextInt(sectors) * 2;
                y = random.nextInt(sectors) * 2; // Assure que y est toujours pair
                //System.out.println("ici");
            } while (!isEmpty(grid, x, y) || !isEmpty(grid, x + 1, y)); // Vérifie si c'est vide
            warehouses.add(new Warehouse(warehouses.size() + 1, x, y, tmp.get(k)));
            k++;
        }

        // Place les entrepots sur la grille
        for (Warehouse warehouse : warehouses) {
            grid[warehouse.getX()][warehouse.getY()] = "E";
            // Convertit l'id de la mine en string et l'affiche a coté du M
            grid[warehouse.getX()][warehouse.getY() + 1] = Integer.toString(warehouse.getId());
        }

        // Ajout de robots
        int robotCount = random.nextInt(4) + 2;
        for (int i = 0; i < robotCount; i++) {
            int x, y;
            do {
                x = random.nextInt(sectors) * 2 + 1; // Assure que x est toujours impair
                y = random.nextInt(sectors) * 2; // Assure que y est toujours pair
            } while (!isEmpty(grid, x, y)); // Vérifie si c'est vide
            String type = (i == 0) ? "NI" : "OR";
            robots.add(new Robot(robots.size() + 1, x, y, type, random.nextInt(5) + 5, random.nextInt(3) + 1));
        }

        // Affiche les robots
        for (Robot robot : robots) {
            grid[robot.getX()][robot.getY()] = "R";
            // Convertit l'id de la mine en string et l'affiche a coté du M
            grid[robot.getX()][robot.getY() + 1] = Integer.toString(robot.getId());
        }
        printGrid(grid, sectors);

        // affiche au premier tour les infos
        System.out.println("\nTour " + tour);

        System.out.println("\nInformations des mines :");
        for (Mine mine : mines) {
            System.out.println("M" + mine.getId() + " : (" + mine.getX() / 2 + ", " + mine.getY() / 2 + ")" + " | Type : " + mine.getType() + " | " + mine.getNbM() + " / " + mine.getInitNbM());
        }

        System.out.println("\nInformations des entrepots :");
        for (Warehouse warehouse : warehouses) {
            System.out.println("E" + warehouse.getId() + " : (" + warehouse.getX() / 2 + ", " + warehouse.getY() / 2 + ")" + " | Type : " + warehouse.getType() + " | " + warehouse.getNbM());
        }

        System.out.println("\nInformations des robots :");
        for (Robot robotInfo : robots) {
            System.out.println("R" + robotInfo.getId() + " : (" + robotInfo.getX() / 2 + ", " + robotInfo.getY() / 2 + ")" + " | Type : " + robotInfo.getType() + " | " + robotInfo.getCurrentLoad() + " / " + robotInfo.getStorageCapacity());
        }

        // Affiche les informations après chaque tour
        while (true) {
            for (Robot robot : robots) {
                if (robot.getCurrentLoad() == 0) {
                    // Trouver le chemin vers la mine la plus proche
                    Mine closestMine = findClosestMine(robot, mines);
                    if (closestMine != null) {
                        // Réserver la mine
                        closestMine.setReserved(true);
                        List<int[]> pathToMine = Dijkstra.findPath(grid, robot.getX(), robot.getY(), closestMine.getX() - 1, closestMine.getY());
                        if (pathToMine != null) {
                            robot.followPath(grid, pathToMine);
                            robot.extract(closestMine);

                            // Après avoir extrait, déplacer le robot en dessous de la mine
                            grid[robot.getX() + 2][robot.getY()] = "R";
                            grid[robot.getX() + 2][robot.getY() + 1] = Integer.toString(robot.getId());

                            // Réinitialiser la position précédente du robot
                            grid[robot.getX()][robot.getY()] = " ";
                            grid[robot.getX()][robot.getY() + 1] = " ";
                            robot.setX(robot.getX() + 2); // Mettre à jour les coordonnées du robot
                        } else {
                            System.out.println("Chemin non trouvé vers la mine " + closestMine.getId() + ". Le robot reste sur place.");
                        }
                        // Libérer la mine après extraction
                        closestMine.setReserved(false);
                    }
                } else {
                    // Trouver le chemin vers l'entrepôt le plus proche
                    Warehouse closestWarehouse = findClosestWarehouse(robot, warehouses);
                    if (closestWarehouse != null) {
                        List<int[]> pathToWarehouse = Dijkstra.findPath(grid, robot.getX(), robot.getY(), closestWarehouse.getX() - 1, closestWarehouse.getY());
                        if (pathToWarehouse != null) {
                            robot.followPath(grid, pathToWarehouse);
                            robot.deposit(closestWarehouse);
                        } else {
                            System.out.println("Chemin non trouvé vers l'entrepôt " + closestWarehouse.getId() + ". Le robot reste sur place.");
                        }
                    }
                }
            }

            printGrid(grid, sectors);
            // Affiche les informations après chaque tour
            System.out.println("\nTour " + ++tour);

            System.out.println("\nInformations des mines :");
            for (Mine mine : mines) {
                System.out.println("M" + mine.getId() + " : (" + mine.getX() / 2 + ", " + mine.getY() / 2 + ")" + " | Type : " + mine.getType() + " | " + mine.getNbM() + " / " + mine.getInitNbM());
            }

            System.out.println("\nInformations des entrepots :");
            for (Warehouse warehouse : warehouses) {
                System.out.println("E" + warehouse.getId() + " : (" + warehouse.getX() / 2 + ", " + warehouse.getY() / 2 + ")" + " | Type : " + warehouse.getType() + " | " + warehouse.getNbM());
            }

            System.out.println("\nInformations des robots :");
            for (Robot robotInfo : robots) {
                System.out.println("R" + robotInfo.getId() + " : (" + robotInfo.getX() / 2 + ", " + robotInfo.getY() / 2 + ")" + " | Type : " + robotInfo.getType() + " | " + robotInfo.getCurrentLoad() + " / " + robotInfo.getStorageCapacity());
            }
            try {
                Thread.sleep(1000); // Attendre 1 seconde entre chaque déplacement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Mine> getAvailableMinesOfType(String type) {
        List<Mine> minesOfType = new ArrayList<>();
        for (Mine mine : mines) {
            if (mine.getType().equals(type)) {
                minesOfType.add(mine);
            }
        }
        return minesOfType;
    }

    private static Mine findClosestMine(Robot robot, List<Mine> mines) {
        Mine closestMine = null;
        int shortestDistance = Integer.MAX_VALUE;
        for (Mine mine : mines) {
            if (mine.getType().equals(robot.getType()) && mine.getNbM() > 0) {
                int distance = Math.abs(robot.getX() - mine.getX()) + Math.abs(robot.getY() - mine.getY());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    closestMine = mine;
                }
            }
        }
        return closestMine;
    }

    private static Warehouse findClosestWarehouse(Robot robot, List<Warehouse> warehouses) {
        Warehouse closestWarehouse = null;
        int shortestDistance = Integer.MAX_VALUE;
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getType().equals(robot.getType())) {
                int distance = Math.abs(robot.getX() - warehouse.getX()) + Math.abs(robot.getY() - warehouse.getY());
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    closestWarehouse = warehouse;
                }
            }
        }
        return closestWarehouse;
    }

    private static void printGrid(String[][] grid, int sectors) {
        System.out.print("        ");
        for (int j = 0; j < sectors; j++) {
            System.out.print(j);
            if (j < 9)
                System.out.print("      ");
            else
                System.out.print("     ");
        }
        System.out.println();

        for (int i = 0; i < sectors; i++) {
            System.out.print("    ");
            for (int j = 0; j < sectors; j++) {
                System.out.print("+-----+");
            }
            System.out.println();
            System.out.print("    ");
            for (int j = 0; j < sectors; j++) {
                System.out.print("| " + grid[i * 2][j * 2] + " " + grid[i * 2][j * 2 + 1] + " |");
            }
            System.out.println();
            System.out.print("    ");
            for (int j = 0; j < sectors; j++) {
                System.out.print("| " + grid[i * 2 + 1][j * 2] + " " + grid[i * 2 + 1][j * 2 + 1] + " |");
            }
            System.out.println();
        }
        System.out.print("    ");
        for (int j = 0; j < sectors; j++) {
            System.out.print("+-----+");
        }
        System.out.println();
    }

    // Vérifie si une case de la grille est vide
    private static boolean isEmpty(String[][] grid, int x, int y) {
        return grid[x][y].equals(" ");
    }
}