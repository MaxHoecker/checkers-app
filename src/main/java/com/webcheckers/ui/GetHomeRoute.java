package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;

  private final PlayerLobby playerLobby;

  private final Gson gson;

  static final String TITLE_ATTR = "title";

  static final String PLAYER_LIST_ATTR = "playerList";
  static final String CUR_PLAYER_ATTR = "currentUser";
  static final String NUM_PLAYERS_ATTR = "numPlayers";
  static final String IS_SIGNED_IN = "isSignedIn";

  static final String PLAYER_SERVICE_ATTR = "playerServices";



  static final String VIEW_NAME = "home.ftl";

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine, final Gson gson) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    this.gson = Objects.requireNonNull(gson);
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    LOG.info("entering getHomeRoute");
    //
    Map<String, Object> vm = new HashMap<>();
    Session curSession = request.session();

    if(curSession.attribute(PLAYER_SERVICE_ATTR) == null){
      curSession.attribute(PLAYER_SERVICE_ATTR, new PlayerServices(playerLobby, gson));
    }
    PlayerServices playerServices = curSession.attribute(PLAYER_SERVICE_ATTR);

    vm.put(TITLE_ATTR, "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    // show the number of signed in players
    vm.put(NUM_PLAYERS_ATTR, playerLobby.getNumPlayers());

    vm.put(PLAYER_LIST_ATTR, Message.info("no players yet!"));

    if(playerServices.signedIn() == null) {
      vm.put(IS_SIGNED_IN, false);
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }else if(!playerServices.signedIn()){
      vm.put(IS_SIGNED_IN, false);
      vm.put("message", Message.info("Sign-out Successful!"));
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }

    else{
      vm.put("message", Message.info("Sign-in successful")); //temporary placeholder

      //ToDo
      //if(playerServices.getViewMode() == "Spectator" || playerServices.getViewMode() == "Replay"){

      if (playerServices.getWonGame() == null){ }

      else{
        if (playerServices.getResigned() == null){}
        else if (playerServices.getResigned()){
          vm.put("message", playerServices.getGameEndMessage());
          playerServices.setWonGame(null, playerServices.getGameEndMessage());
        }
        else if (!playerServices.getResigned()){
          vm.put("message", playerServices.getGameEndMessage());
          playerServices.removeFromGame();
          playerServices.setWonGame(null, playerServices.getGameEndMessage());
        }
        vm.put("message", playerServices.getGameEndMessage());
        playerServices.setWonGame(null, playerServices.getGameEndMessage());
          playerServices.removeFromGame();
      }


      //}
       if(playerServices.curPlayerColor() != null){ //handles player getting clicked on by another player
        response.redirect(WebServer.GAME_URL);
        return null;
      }
      vm.put(IS_SIGNED_IN, true);
      if(playerLobby.getNumPlayers() > 1){
        Set<String> x = playerLobby.playersNotInGame();
        x.remove(playerServices.curPlayerName());
        vm.put(PLAYER_LIST_ATTR, x);
      }
      else{
        vm.put(PLAYER_LIST_ATTR, Message.info("no players yet!"));
      }
      vm.put(CUR_PLAYER_ATTR, playerServices.curPlayer());

      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
  }
}
