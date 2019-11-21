package com.webcheckers.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class BoardTest {

    @BeforeEach
    public void setup(){

    }
    @Test
    public void test_multi() throws CloneNotSupportedException {
        Board boarding = new Board();
        Board b2 = new Board();
        boarding.toString();
        boarding.getAtPosition(new Position(0,0));
        boarding.getAtPosition(0,0);
        boarding.clone();
        boarding.equals(b2);
        boarding.iterator();

    }
    //size is 0 before
    //size is zero after
    //make sure the rows arent null
}
