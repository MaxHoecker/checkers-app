package com.webcheckers.Model;

import com.sun.org.apache.regexp.internal.REDebugCompiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("Model-Tier")
public class GameTest {

    private Game CuT;

    //mocked dependencies
    private Player red;
    private Player white;
    private Player player;
    private Board board;
    private Move move;
    private Space space;
    private Piece piece;
    private Position position;


    @BeforeEach
    public void setup() {
        red = mock(Player.class);
        white = mock(Player.class);
        player = mock(Player.class);
        board = mock(Board.class);
        move = mock(Move.class);
        space = mock(Space.class);
        piece = mock(Piece.class);
        position = mock(Position.class);

        CuT = new Game(red, white, board);
    }

    @Test
    public void game_id(){
        int id = CuT.getGameID();

        assertTrue(id >= 0);
    }

    @Test
    public void clear_move_list(){
        CuT.clearMoveList();
    }

    @Test
    public void add_move(){
        CuT.addMove(move);
    }

    @Test
    public void get_move_list(){
        ArrayList<Move> list = CuT.getMoveList();

        assertNotNull(list);
    }

    @Test
    public void get_board(){
        Board board = CuT.getBoard();

        assertNotNull(board);
    }

    @Test
    public void get_player_white(){
        Player white = CuT.getPlayer(Color.WHITE);

        assertNotNull(white);
        assertEquals(this.white, white);
    }

    @Test
    public void get_player_red(){
        Player red = CuT.getPlayer(Color.RED);

        assertNotNull(red);
        assertEquals(this.red, red);
    }

    @Test
    public void set_current_player_color_white(){
        CuT.setCurrentPlayerColor(Color.RED);

        assertEquals(CuT.getCurrentPlayerColor(), Color.RED);
    }

    @Test
    public void set_current_player_color_red(){
        CuT.setCurrentPlayerColor(Color.WHITE);

        assertEquals(CuT.getCurrentPlayerColor(), Color.WHITE);
    }

    @Test
    public void get_current_player_color_red(){
        CuT.setCurrentPlayerColor(Color.RED);
        Color color = CuT.getCurrentPlayerColor();

        assertEquals(color, Color.RED);
    }

    @Test
    public void get_current_player_color_red_null(){
        CuT.setCurrentPlayerColor(null);
        Color color = CuT.getCurrentPlayerColor();

        assertEquals(color, Color.RED);
    }

    @Test
    public void get_current_player_color_white(){
        CuT.setCurrentPlayerColor(Color.WHITE);
        Color color = CuT.getCurrentPlayerColor();

        assertEquals(color, Color.WHITE);
    }

    @Test
    public void get_opponent_of_white(){
        when(player.getColor()).thenReturn(Color.WHITE);
        Player red = CuT.getOpponent(player);

        assertEquals(this.red, red);
    }

    @Test
    public void get_opponent_of_red(){
        when(player.getColor()).thenReturn(Color.RED);
        Player white = CuT.getOpponent(player);

        assertEquals(this.white, white);
    }

    @Test
    public void get_opponent_null(){
        when(player.getColor()).thenReturn(null);
        Player op = CuT.getOpponent(player);

        assertNull(op);
    }

    @Test
    public void set_player_red(){
        assertEquals(CuT.getPlayer(Color.RED), this.red);

        CuT.setPlayer(Color.RED, player);

        assertEquals(CuT.getPlayer(Color.RED), player);
    }

    @Test
    public void set_player_white(){
        assertEquals(CuT.getPlayer(Color.WHITE), this.white);

        CuT.setPlayer(Color.WHITE, player);

        assertEquals(CuT.getPlayer(Color.WHITE), player);
    }

    @Test
    public void set_player_null(){
        assertEquals(CuT.getPlayer(Color.WHITE), this.white);

        CuT.setPlayer(null, player);

        assertEquals(CuT.getPlayer(Color.WHITE), this.white);
    }

    @Test
    public void get_num_red_pieces(){
        int reds = CuT.numRedPieces();

        assertTrue(reds >= 0);
    }

    @Test
    public void get_num_white_pieces(){
        int whites = CuT.numWhitePieces();

        assertTrue(whites >= 0);
    }

    @Test
    public void cloning(){
        try {
            when(board.clone()).thenReturn(board);
            when(red.clone()).thenReturn(red);
            when(white.clone()).thenReturn(white);

            Game g = (Game)CuT.clone();
            assertNotNull(g);


        }catch(CloneNotSupportedException e){
            System.err.println("wut?");
        }
    }

    @Test
    public void make_move_simple_move(){
        when(move.getStart()).thenReturn(position);
        when(move.getEnd()).thenReturn(position);
        when(board.getAtPosition(position)).thenReturn(space);
        when(space.removeOccupant()).thenReturn(piece);
        when(move.getDistance()).thenReturn(1);

        CuT.makeMove(move, false);
    }

    @Test
    public void make_single_jump_over_red(){
        int reds = CuT.numRedPieces();

        when(move.getStart()).thenReturn(position);
        when(move.getEnd()).thenReturn(position);
        when(position.getRow()).thenReturn(1);
        when(position.getCell()).thenReturn(1);
        when(board.getAtPosition(position)).thenReturn(space);
        when(board.getAtPosition(1,1)).thenReturn(space);
        when(space.removeOccupant()).thenReturn(piece);
        when(move.getDistance()).thenReturn(2);
        when(piece.getColor()).thenReturn(Color.RED);

        CuT.makeMove(move, false);
        assertEquals(reds - 1, CuT.numRedPieces());
    }

    @Test
    public void make_single_jump_over_white(){
        int whites = CuT.numWhitePieces();

        when(move.getStart()).thenReturn(position);
        when(move.getEnd()).thenReturn(position);
        when(position.getRow()).thenReturn(1);
        when(position.getCell()).thenReturn(1);
        when(board.getAtPosition(position)).thenReturn(space);
        when(board.getAtPosition(1,1)).thenReturn(space);
        when(space.removeOccupant()).thenReturn(piece);
        when(move.getDistance()).thenReturn(2);
        when(piece.getColor()).thenReturn(Color.WHITE);

        CuT.makeMove(move, false);
        assertEquals(whites - 1, CuT.numWhitePieces());
    }

    @Test
    public void king_red(){
        when(move.getStart()).thenReturn(position);
        when(move.getEnd()).thenReturn(position);
        when(board.getAtPosition(position)).thenReturn(space);
        when(space.removeOccupant()).thenReturn(piece);
        when(move.getDistance()).thenReturn(1);
        CuT.setCurrentPlayerColor(Color.RED);
        when(position.getRow()).thenReturn(7);

        CuT.makeMove(move, false);
    }

    @Test
    public void king_white(){
        when(move.getStart()).thenReturn(position);
        when(move.getEnd()).thenReturn(position);
        when(board.getAtPosition(position)).thenReturn(space);
        when(space.removeOccupant()).thenReturn(piece);
        when(move.getDistance()).thenReturn(1);
        CuT.setCurrentPlayerColor(Color.WHITE);
        when(position.getRow()).thenReturn(0);

        CuT.makeMove(move, false);
    }

    @Test
    public void multi_jump_as_red(){
        CuT.setCurrentPlayerColor(Color.RED);
        when(move.getStart()).thenReturn(position);
        when(move.getEnd()).thenReturn(position);
        when(position.getRow()).thenReturn(1);
        when(position.getCell()).thenReturn(1);
        when(board.getAtPosition(position)).thenReturn(space);
        when(board.getAtPosition(1,1)).thenReturn(space);
        when(space.removeOccupant()).thenReturn(piece);
        when(move.getDistance()).thenReturn(2);
        when(piece.getColor()).thenReturn(Color.WHITE);

        CuT.makeMove(move, true);

        assertEquals(CuT.getCurrentPlayerColor(), Color.RED);
    }

    @Test
    public void multi_jump_as_white(){
        CuT.setCurrentPlayerColor(Color.WHITE);
        when(move.getStart()).thenReturn(position);
        when(move.getEnd()).thenReturn(position);
        when(position.getRow()).thenReturn(1);
        when(position.getCell()).thenReturn(1);
        when(board.getAtPosition(position)).thenReturn(space);
        when(board.getAtPosition(1,1)).thenReturn(space);
        when(space.removeOccupant()).thenReturn(piece);
        when(move.getDistance()).thenReturn(2);
        when(piece.getColor()).thenReturn(Color.RED);

        CuT.makeMove(move, true);

        assertEquals(CuT.getCurrentPlayerColor(), Color.WHITE);
    }
}
