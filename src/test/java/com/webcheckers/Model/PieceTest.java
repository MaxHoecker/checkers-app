package com.webcheckers.Model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("com.webcheckers.Model tier")
public class PieceTest {

    private static final Color W = Color.WHITE;
    private static final Color R = Color.RED;

    @Test
    public void ctor_testW(){
        final Piece CuT = new Piece(Color.WHITE);
        assertSame("W" , CuT.toString());
    }

    @Test
    public void ctor_testR(){
        final Piece Cut = new Piece(Color.RED);
        assertSame("R" , Cut.toString());

    }



}

