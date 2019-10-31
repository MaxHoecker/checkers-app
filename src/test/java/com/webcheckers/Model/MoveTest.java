package com.webcheckers.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class MoveTest {

    private Move dist1;
    private Move dist2;
    private Move dist3;
    private Move vertical;
    private Move horizontal;


    @BeforeEach
    public void setup(){
        dist1 = new Move(new Position(7, 0), new Position(6,1));
        dist2 = new Move(new Position(7, 0), new Position(5, 2));
        dist3 = new Move(new Position(7, 0), new Position(4, 3));
        vertical = new Move(new Position(7, 0), new Position(5, 0));
        horizontal = new Move(new Position(7, 0), new Position(7, 2));
    }

    @Test
    public void test_get_start(){
        assertEquals(7, dist1.start.getRow());
        assertEquals(0, dist1.start.getCell());
    }

    @Test
    public void test_get_end(){
        assertEquals(6, dist1.end.getRow());
        assertEquals(1, dist1.end.getCell());
    }

    @Test
    public void test_distance_1(){
        int dist = dist1.getDistance();
        assertEquals(1, dist);
    }

    @Test
    public void test_distance_2(){
        int dist = dist2.getDistance();
        assertEquals(2, dist);
    }

    @Test
    public void test_distance_3(){
        int dist = dist3.getDistance();
        assertEquals(3, dist);
    }

    @Test
    public void test_vertical(){
        int dist = vertical.getDistance();
        assertEquals(0, dist);
    }

    @Test
    public void test_horizontal_3(){
        int dist = horizontal.getDistance();
        assertEquals(0, dist);
    }
}
