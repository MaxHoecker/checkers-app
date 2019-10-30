package com.webcheckers.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.utils.Assert;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Model-tier")
public class SpaceTest {

    private Space CuT;

    private int VALIDID1 = 0;
    private int VALIDID2 = 7;

    private Piece piece;

    @BeforeEach
    public void setup(){

        piece = mock(Piece.class);
    }

    @Test
    public void test_is_valid_space(){
        CuT = new Space(false, 0, null);
        assertFalse(CuT.isValid());
        assertNull(CuT.getOccupant());
        CuT.setOccupant(piece);
        assertNull(CuT.getOccupant());

        CuT = new Space(true, 0, null);
        assertTrue(CuT.isValid());
        assertNull(CuT.getOccupant());
        CuT.setOccupant(piece);
        assertNotNull(CuT.getOccupant());

    }

    @Test
    public void test_correct_Idx(){
        CuT = new Space(false, VALIDID1, null);
        assertEquals(1, CuT.getCellIdx());

        CuT = new Space(false, VALIDID2, null);
        assertEquals(7, CuT.getCellIdx());


    }

    @Test
    public void test_occupy_space(){
        CuT = new Space(true, 0, null);
        assertNull(CuT.getOccupant());

        CuT.setOccupant(piece);

        assertNotNull(CuT.getOccupant());

    }

    @Test
    public void test_remove_occupant(){
        CuT = new Space(true, 0, piece);
        assertNotNull(CuT.getOccupant());

        CuT.removeOccupant();

        assertNull(CuT.getOccupant());
    }

    //test to make sure that when constructed its getters don't return null and don't
    /**
     * getOccupant
     * isValid
     * getCellIdx
     * moveTo
     * removeOccupant
     */
}
