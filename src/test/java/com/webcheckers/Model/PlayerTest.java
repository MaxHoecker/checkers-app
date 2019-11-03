package com.webcheckers.Model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import com.webcheckers.Model.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class PlayerTest {
    private Player CuT;
    private Game game;

    @BeforeEach
    public void setup() {
        CuT = new Player("Player1");
        game = mock(Game.class);
    }

    @Test
    public void Constructor_Test(){
        assertNull(CuT.game());
        assertNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }

    @Test
    public void Functions_test(){
        CuT.setGame(game);
        CuT.setColor(Color.RED);
        assertNotNull(CuT.game());
        assertNotNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }
}