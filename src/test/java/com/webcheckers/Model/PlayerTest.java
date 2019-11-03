package com.webcheckers.Model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

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
        assertNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }

    @Test
    public void Functions_test(){
        CuT.setColor(Color.RED);
        assertNotNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }
}