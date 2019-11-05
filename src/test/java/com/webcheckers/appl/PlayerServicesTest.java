package com.webcheckers.appl;

import com.google.gson.Gson;
import com.webcheckers.Model.Color;
import com.webcheckers.Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Application-tier")
public class PlayerServicesTest {

    private PlayerServices CuT;

    private PlayerLobby playerLobby;
    private Gson gson = new Gson();
    private String VALID = "h";
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
        opponentInGame.setColor(Color.RED);
        playerLobby = mock(PlayerLobby.class);
        CuT = new PlayerServices(playerLobby, gson);
    }

    @Test
    public void test_constructor(){
        new PlayerServices(playerLobby, gson);
    }

    @Test
    public void test_initialize_player_valid_name(){
        when(playerLobby.addPlayer(any(String.class), any(Player.class))).thenReturn(Boolean.TRUE);
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
    /*
    @Test
    public void test_nullify_player(){
        when(playerLobby.addPlayer(any(String.class), any(Player.class))).thenReturn(Boolean.TRUE);
        Player opponent = null;
        CuT.setCurPlayer(VALID);
        assertNotNull(CuT.curPlayer());
        if(CuT.curPlayer().game().getOpponent(CuT.curPlayer()) != null){
            opponent = CuT.curPlayer().game().getOpponent(CuT.curPlayer());
        }

        CuT.setCurPlayer(null);

        if(opponent != null) {
            assertNull(opponent.game().getOpponent(opponent));
        }
        assertNull(CuT.curPlayer());
    }

    @Test
    public void test_set_up_game_opponent_not_in_game(){
        when(playerLobby.getPlayer(VALID2)).thenReturn(opponentNotInGame);
        when(playerLobby.addPlayer(any(String.class), any(Player.class))).thenReturn(Boolean.TRUE);
        CuT.setCurPlayer(VALID);
        assertNull(CuT.curPlayer().game().getBoard());
        assertNull(CuT.curPlayer().getColor());

        boolean result = CuT.setUpGame(VALID2);

        assertNotNull(CuT.curPlayer().getColor());
        assertNotNull(CuT.curPlayer().game().getOpponent(CuT.curPlayer()));
        assertNotNull(CuT.curPlayer().game().getOpponent(CuT.curPlayer()).getColor());
        assertNotNull(CuT.curPlayer().game().getBoard());
        assertNotNull(CuT.curPlayer().game().getOpponent(CuT.curPlayer()).game().getBoard());
        assertEquals(CuT.curPlayer().game().getBoard(), CuT.curPlayer().game().getOpponent(CuT.curPlayer()).game().getBoard());
        assertTrue(result);
    }

    @Test
    public void test_set_up_game_opponent_in_game(){
        when(playerLobby.getPlayer(VALID2)).thenReturn(opponentInGame);
        when(playerLobby.addPlayer(any(String.class), any(Player.class))).thenReturn(Boolean.TRUE);
        CuT.setCurPlayer(VALID);
        assertNull(CuT.curPlayer().game().getBoard());
        assertNull(CuT.curPlayer().getColor());
        assertNull(CuT.curPlayer().game().getOpponent(CuT.curPlayer()));

        boolean result = CuT.setUpGame(VALID2);

        assertNull(CuT.curPlayer().game().getOpponent(CuT.curPlayer()));
        assertNull(CuT.curPlayer().getColor());
        assertNull(CuT.curPlayer().game().getOpponent(CuT.curPlayer()));
        assertFalse(result);
    }
    */
}

