package com.webcheckers.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

@Tag("Model-tier")
public class BoardTest {
    private Board CuT;

    //mocked dependencies
    private ArrayList<Row> list;
    private Row row;
    private Space space;
    private Position position;
    private Iterator<Row> iterator;
    private Board board;

    @BeforeEach
    public void setup(){
        list = mock(ArrayList.class);
        row = mock(Row.class);
        space = mock(Space.class);
        position = mock(Position.class);
        iterator = mock(Iterator.class);
        board = mock(Board.class);

        when(list.get(any(Integer.class))).thenReturn(row);
        CuT = new Board(list);
    }

    @Test
    public void multi_jump_mode(){
        System.setProperty("mode", "multi-jump");
        new Board(new ArrayList<>());
    }

    @Test
    public void kinging_mode(){
        System.setProperty("mode", "kinging");
        new Board(new ArrayList<>());
    }

    @Test
    public void get_at_position_row_col(){
        when(row.getSpace(any(Integer.class))).thenReturn(space);

        Space result = CuT.getAtPosition(0, 0);

        assertEquals(result, space);
    }

    @Test
    public void get_at_position(){
        when(position.getRow()).thenReturn(0);
        when(position.getRow()).thenReturn(0);
        when(row.getSpace(any(Integer.class))).thenReturn(space);

        Space result = CuT.getAtPosition(position);

        assertEquals(result, space);
    }

    @Test
    public void get_iterator(){
        when(list.iterator()).thenReturn(iterator);

        Iterator<Row> result = CuT.iterator();

        assertEquals(result, iterator);
    }

    @Test
    public void board_clone(){
        when(list.get(any(Integer.class))).thenReturn(row);
        try {
            when(row.clone()).thenReturn(row);
            Board result = (Board)CuT.clone();
            assertNotNull(result);
        }catch(CloneNotSupportedException e){
            System.err.println("wut 2 electric boogaloo");
        }
    }

    @Test
    public void equals_not_board_instance(){
        boolean result = CuT.equals(row);

        assertFalse(result);
    }

    @Test
    public void equals_true(){
        when(list.size()).thenReturn(0);

        boolean result = CuT.equals(board);

        assertTrue(result);
    }

    @Test
    public void to_string(){
        when(list.get(anyInt())).thenReturn(row);
        when(row.toString()).thenReturn("");

        String result = CuT.toString();

        assertEquals(result, "\n\n\n\n\n\n\n\n");
    }

}
