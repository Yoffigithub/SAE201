package adrien.s201;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class World {
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

        // Contrôle des robots par l'utilisateur
        String command = "";
        robotLoop:
        while (true) {
            // Itère sur chaque robot
            for (Robot robot : robots) {
                boolean moveSuccessful = false;
                while (!moveSuccessful) {
                    System.out.println("\nEntrez une commande pour déplacer le robot " + robot.getId() + " (N: Nord, E: Est, S: Sud, O: Ouest, M: Miner, D: Deposer Q: Quitter):");
                    command = scanner.nextLine().toUpperCase();

                    // Vérifie si l'utilisateur souhaite quitter
                    if (command.equals("Q")) {
                        moveSuccessful = true; // Permet de sortir de la boucle interne
                        break;
                    }

                    // Efface les anciennes positions du robot
                    grid[robot.getX()][robot.getY()] = " ";
                    grid[robot.getX()][robot.getY() + 1] = " ";

                    // Déplace le robot en fonction de la commande
                    //boolean moveSuccessful = false;
                    boolean actionSuccessful = false;

                    switch (command) {
                        case "N":
                            moveSuccessful = robot.moveNorth(grid);
                            if (!moveSuccessful) {
                                System.out.println("Erreur, déplacement impossible vers le nord. Réessayez.");
                            }
                            break;
                        case "E":
                            moveSuccessful = robot.moveEast(grid);
                            if (!moveSuccessful) {
                                System.out.println("Erreur, déplacement impossible vers l'est. Réessayez.");
                            }
                            break;
                        case "S":
                            moveSuccessful = robot.moveSouth(grid);
                            if (!moveSuccessful) {
                                System.out.println("Erreur, déplacement impossible vers le sud. Réessayez.");
                            }
                            break;
                        case "O":
                            moveSuccessful = robot.moveWest(grid);
                            if (!moveSuccessful) {
                                System.out.println("Erreur, déplacement impossible vers l'ouest. Réessayez.");
                            }
                            break;
                        case "M": // Extraction
                            for (Mine mine : mines) {
                                if (robot.getX() - 1 == mine.getX() && robot.getY() == mine.getY()) {
                                    if (robot.getCurrentLoad() >= robot.getStorageCapacity()) {
                                        System.out.println("Le robot ne peut pas extraire plus de minerais. Capacité maximale atteinte.");
                                    }
                                    if (mine.getNbM() == 0) {
                                        System.out.println("La mine est vide. Aucun minerai ne peut être extrait.");
                                        continue robotLoop; // Redemande une action au même robot
                                    }
                                    else {
                                        robot.extract(mine);
                                        actionSuccessful = true;
                                        moveSuccessful = true;
                                    }
                                    break;
                                }
                            }
                            if (!actionSuccessful) {
                                System.out.println("Erreur, le robot ne peut pas effectuer d'action ici.");
                            }
                            break;
                        case "D":
                            for (Warehouse warehouse : warehouses) {
                                if (robot.getX() - 1 == warehouse.getX() && robot.getY() == warehouse.getY()) {
                                    if (robot.getCurrentLoad() == 0) {
                                        System.out.println("Le robot n'a pas de minerais à déposer.");
                                    }
                                    else {
                                        robot.deposit(warehouse);
                                        actionSuccessful = true;
                                        moveSuccessful = true;
                                    }
                                    break;
                                }
                            }
                            if (!actionSuccessful) {
                                System.out.println("Erreur, le robot ne peut pas effectuer d'action ici.");
                            }
                            break;
                        default:
                            System.out.println("Commande invalide. Veuillez entrer N, E, S, O, M ou D.");
                            break;
                    }

                    // Si une action ou un déplacement est réussi, passez au robot suivant
                    if (moveSuccessful || actionSuccessful) {
                        continue;
                    }

                    // Si le déplacement n'est pas réussi, réaffiche les anciennes positions du robot
                    if (!moveSuccessful) {
                        grid[robot.getX()][robot.getY()] = "R";
                        grid[robot.getX()][robot.getY() + 1] = Integer.toString(robot.getId());
                    }
                }

                // Met à jour la nouvelle position du robot dans la grille
                if (moveSuccessful && !command.equals("Q")) {
                    grid[robot.getX()][robot.getY()] = "R";
                    grid[robot.getX()][robot.getY() + 1] = Integer.toString(robot.getId());
                }

                // Affiche la grille mise à jour avec le déplacement du robot
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

                // Vérifie si l'utilisateur a demandé à quitter
                if (command.equals("Q")) {
                    scanner.close();
                    break;
                }
            }
        }
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