package com.webcheckers.Model;


import static org.junit.jupiter.api.Assertions.*;

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
    public void empty(){
        assertNull(CuT.getBoard());
        assertNull(CuT.getClass());
        assertNull(CuT.getColor());
        assertNotNull(CuT.getName());
    }
}