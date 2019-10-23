package com.webcheckers.appl;

import com.webcheckers.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Application-tier")
public class PlayerServicesTest {

    private PlayerServices CuT;

    private PlayerLobby playerLobby;
    private String VALID = "username";
    private String VALID2 = "username1";
    private String INVALID1 = ";";
    private String INVALID2 = "\"David\"";

    //friendly
    private Player player;
    private Player opponentNotInGame;
    private Player opponentInGame;

    @BeforeEach
    public void setup(){
        player = new Player(VALID);
        opponentNotInGame = new Player(VALID2);
        opponentInGame = new Player(VALID2);
        opponentInGame.setColor(Player.Color.RED);
        playerLobby = mock(PlayerLobby.class);
        CuT = new PlayerServices(playerLobby);
    }

    @Test
    public void test_constructor(){
        new PlayerServices(playerLobby);
    }

    @Test
    public void test_initialize_player_valid_name(){
        assertNull(CuT.curPlayer());

        CuT.setCurPlayer(VALID);

        assertNotNull(CuT.curPlayer());
        assertEquals(CuT.curPlayer().getName(), VALID);
        assertNull(CuT.curPlayer().getColor());
    }

    @Test
    public void test_initialize_player_invalid_name1(){
        assertNull(CuT.curPlayer());

        String result = CuT.setCurPlayer(INVALID1);

        assertEquals(result, PlayerServices.INVALID_NAME_MSG);
        assertNull(CuT.curPlayer());
    }

    @Test
    public void test_initialize_player_invalid_name2(){
        assertNull(CuT.curPlayer());

        String result = CuT.setCurPlayer(INVALID2);

        assertEquals(result, PlayerServices.INVALID_NAME_MSG);
        assertNull(CuT.curPlayer());
    }

    @Test
    public void test_name_in_use(){
        when(playerLobby.addPlayer(VALID, player)).thenReturn(Boolean.FALSE);

        assertNull(CuT.curPlayer());

        String result = CuT.setCurPlayer(VALID);

        assertEquals(result, PlayerServices.NAME_TAKEN_MSG);
        assertNull(CuT.curPlayer());
    }

    @Test
    public void test_nullify_player(){
        Player opponent = null;
        assertNotNull(CuT.curPlayer());
        if(CuT.curPlayer().getOpponent() != null){
            opponent = CuT.curPlayer().getOpponent();
        }

        CuT.setCurPlayer(null);

        assertNull(opponent.getOpponent());
        assertNull(CuT.curPlayer());
    }

    @Test
    public void test_set_up_game_opponent_not_in_game(){
        when(playerLobby.getPlayer(VALID2)).thenReturn(opponentNotInGame);
        assertNull(CuT.curPlayer().getBoard());
        assertNull(CuT.curPlayer().getColor());

        boolean result = CuT.setUpGame(VALID2);

        assertNotNull(CuT.curPlayer().getColor());
        assertNotNull(CuT.curPlayer().getOpponent());
        assertNotNull(CuT.curPlayer().getOpponent().getColor());
        assertNotNull(CuT.curPlayer().getBoard());
        assertNotNull(CuT.curPlayer().getOpponent().getBoard());
        assertEquals(CuT.curPlayer().getBoard(), CuT.curPlayer().getOpponent().getBoard());
        assertTrue(result);
    }

    @Test
    public void test_set_up_game_opponent_in_game(){
        when(playerLobby.getPlayer(VALID2)).thenReturn(opponentInGame);
        assertNull(CuT.curPlayer().getBoard());
        assertNull(CuT.curPlayer().getColor());
        assertNull(CuT.curPlayer().getOpponent());

        boolean result = CuT.setUpGame(VALID2);

        assertNull(CuT.curPlayer().getOpponent());
        assertNull(CuT.curPlayer().getColor());
        assertNull(CuT.curPlayer().getOpponent());
        assertFalse(result);
    }
}
