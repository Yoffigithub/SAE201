package adrien.sae_201;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RobotTest {
    private Robot robot;
    private String[][] grid;

    @BeforeEach
    void setUp() {
        robot = new Robot(1, 4, 4, "NI", 10, 3);
        grid = new String[10][10];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = " ";
            }
        }
    }

    @Test
    void testMoveNorthSuccess() {
        assertTrue(robot.moveNorth(grid));
        assertEquals(2, robot.getX());
        assertEquals(4, robot.getY());
    }

    @Test
    void testMoveNorthFail() {
        grid[2][4] = "X";
        assertFalse(robot.moveNorth(grid));
        assertEquals(1, robot.getX());
        assertEquals(4, robot.getY());
    }

    @Test
    void testExtractSuccess() {
        Mine mine = new Mine(1, 2, 4, "NI");
        mine.setNbM(5);

        robot.moveNorth(grid);
        robot.moveNorth(grid);

        robot.extract(mine);
        assertEquals(3, robot.getCurrentLoad());
        assertEquals(2, mine.getNbM());
    }

    @Test
    void testExtractFailWrongType() {
        Mine mine = new Mine(1, 2, 4, "OR");
        mine.setNbM(5);

        robot.moveNorth(grid);
        robot.moveNorth(grid);

        robot.extract(mine);
        assertEquals(0, robot.getCurrentLoad());
        assertEquals(5, mine.getNbM());
    }

    @Test
    void testExtractFailFullLoad() {
        Mine mine = new Mine(1, 2, 4, "NI");
        mine.setNbM(5);

        robot.moveNorth(grid);
        robot.moveNorth(grid);

        robot.extract(mine);
        robot.extract(mine);

        assertEquals(6, robot.getCurrentLoad());
        assertEquals(0, mine.getNbM());
    }
}
