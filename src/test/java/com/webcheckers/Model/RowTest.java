package com.webcheckers.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.utils.Assert;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Tag("Model-tier")
public class RowTest {

    private int INDEX1 = 2;
    private int INDEX2 = 1;

    private Row CuT;

    @BeforeEach
    public void setup(){
        CuT = new Row(INDEX1);
        CuT.addSpacePiece(true, INDEX2, Color.RED);


    }
/*
    @Test
    public void test_add_space_piece(){
        assertTrue(CuT.getSpace(INDEX2).isValid());








    }
*/
    @Test
    public void test_add_empty_space(){

    }
}
