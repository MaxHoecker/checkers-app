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
        assertNull(CuT.getOpponent());
        assertNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }

    @Test
    public void Functions_test(){
        CuT.setBoard(new Board());
        CuT.setColor(Color.RED);
        CuT.setOpponent(new Player("Josh"));
        assertNotNull(CuT.getBoard());
        assertNotNull(CuT.getOpponent());
        assertNotNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }
}