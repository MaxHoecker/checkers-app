package com.webcheckers.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class GameTest {

    private Game CuT;
    private Player red;
    private Player white;
    private Board board;
    private Move move;
    private Piece redpiece;
    private Piece whitepiece;


    @BeforeEach
    public void setup(){
        red = new Player("RED");
        red.setColor(Color.RED);
        white = new Player("WHITE");
        white.setColor(Color.WHITE);
        board = new Board();
        move = new Move(new Position(7,0), new Position(6, 1));
        CuT = new Game(red, white);
        redpiece = new Piece(Color.RED);
        redpiece.kingMe();
        whitepiece = new Piece(Color.WHITE);
    }



    @Test
    public void test_init_move(){




    }

    @Test
    public void test_change_board_state(){



    }

    @Test
    public void test_kinging_red(){
        assertEquals(redpiece.getType(), PieceType.KING);

    }

    @Test
    public void test_kinging_white(){
        assertNotEquals(whitepiece.getType(), PieceType.KING);

    }

    @Test
    public void test_swap_turn_red(){
        assertTrue(CuT.getCurrentPlayerColor() == red.getColor());

    }

    @Test
    public void test_swap_turn_white(){

    }

}
