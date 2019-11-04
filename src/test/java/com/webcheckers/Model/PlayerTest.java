package com.webcheckers.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Tag;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;


@Tag("Model-tier")
public class PlayerTest {
    private Player CuT;
    private Player p2;

    @BeforeEach
    public void setup() {
        CuT = new Player("Player1");
        CuT.setColor(Color.RED);
        p2.setColor(Color.WHITE);
        Game game = new Game(CuT,p2);
        CuT.setGame(game);
        p2.setGame(game);
        game.setCurrentPlayerColor(Color.RED);
    }


    @Test
    public void is_my_turn(){
        assertTrue(CuT.isMyTurn());
    }

    @Test
    public void is_not_my_turn(){

    }

    


}