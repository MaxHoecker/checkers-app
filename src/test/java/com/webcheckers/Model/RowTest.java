package com.webcheckers.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.utils.Assert;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Tag("Model-tier")
public class RowTest {

    private Row CuT;

    //mocked dependencies
    private ArrayList<Space> list;
    private Space space;
    private Iterator<Space> iterator;
    private Row row;

    @BeforeEach
    public void setup(){
        list = mock(ArrayList.class);
        space = mock(Space.class);
        iterator = mock(Iterator.class);
        row = mock(Row.class);

        CuT = new Row(1, list);
    }

    @Test
    public void add_space_piece_test(){
        CuT.addSpacePiece(true, 1, Color.RED);
    }

    @Test
    public void add_space_piece_king_test(){
        CuT.addSpacePiece(true, 0, Color.WHITE);
    }

    @Test
    public void add_empty_space_test(){
        CuT.addEmptySpace(true, 1);
    }

    @Test
    public void get_index_noflip(){
        int index = CuT.getIndex(false);
        assertEquals(index, 1);
    }

    @Test
    public void get_index_flip(){
        int index = CuT.getIndex(true);
        assertEquals(index, 7);
    }

    @Test
    public void get_index_test(){
        int index = CuT.getIndex();
        assertEquals(index, 1);
    }

    @Test
    public void set_index_test(){
        CuT.setIndex(0, space);
    }

    @Test
    public void get_space_test(){
        when(list.get(anyInt())).thenReturn(space);
        Space result = CuT.getSpace(0);

        assertEquals(result, space);
    }

    @Test
    public void iterator_test(){
        when(list.iterator()).thenReturn(iterator);
        Iterator<Space> result = CuT.iterator();
        assertEquals(result, iterator);
    }

    @Test
    public void clone_test(){
        when(list.size()).thenReturn(0);
        try{
            Row result = (Row)CuT.clone();
            assertNotNull(result);
        }catch (CloneNotSupportedException e){
            System.err.println("wut de nuevo");
        }
    }

    @Test
    public void equals_not_instance(){
        boolean result = CuT.equals(space);
        assertFalse(result);
    }

    @Test
    public void equals_true(){
        when(list.size()).thenReturn(0);
        boolean result = CuT.equals(row);

        assertTrue(result);
    }

    @Test
    public void to_string_test(){
        when(list.toString()).thenReturn("");
        String result = CuT.toString();

        assertEquals(result, "[1] ");
    }

}
