package com.webcheckers.Model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("com.webcheckers.Model tier")
public class PieceTest {

    private Piece red;
    private Piece white;
    private Piece king;


    @BeforeEach
    public void setup(){
        red = new Piece(Color.RED);
        white = new Piece(Color.WHITE);
        king = new Piece(Color.RED);
        king.kingMe();

    }

    @Test
    public void test_red(){
        assertEquals(Color.RED,red.getColor());
    }

    @Test
    public void test_white(){
        assertEquals(Color.WHITE,white.getColor());
    }

    @Test
    public void test_kinged(){
        assertEquals(PieceType.KING, king.getType());
    }



}

