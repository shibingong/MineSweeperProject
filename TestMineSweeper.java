import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestMineSweeper {
    private MineSweeper game;

    @Before
    public void setUp() {
        game = new MineSweeper(3, 3, 0); // 3x3 grid, no mines
        game.initializeBoard();
    }

    @Test
    public void testCountAdjacentMines() {
        game.setMineAt(0, 0, true);
        assertEquals(1, game.countAdjacentMines(0, 1));
        assertEquals(1, game.countAdjacentMines(1, 1));
        assertEquals(0, game.countAdjacentMines(2, 2));
    }

    @Test
    public void testRevealNoMine() {
        game.updateBoard();
        game.revealCell(2, 2);
        assertTrue(game.isWin()); // All cells safe = win after reveal
    }

    @Test
    public void testRevealWithMine() {
        game.setMineAt(1, 1, true);
        game.updateBoard();
        game.revealCell(1, 1);
        assertTrue(game.isGameOver());
    }
}
