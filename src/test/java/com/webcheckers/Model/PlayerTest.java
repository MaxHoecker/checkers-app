package com.webcheckers.Model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import com.webcheckers.Model.Player.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class PlayerTest {
    private Player CuT;

    @BeforeEach
    public void setup() {
        CuT = new Player("Player1");
    }

    @Test
    public void Constructor_Test(){
        assertNull(CuT.getBoard());
        assertNull(CuT.getClass());
        assertNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }
     @Test
    public void Functions_test(){
        CuT.setBoard(mock(Board.class));
        CuT.setColor(mock(Color.class));
        CuT.setOpponent(mock(Player.class));
        assertNotNull(CuT.getBoard());
        assertNotNull(CuT.getClass());
        assertNotNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }
}