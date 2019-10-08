package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.Model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  private final TemplateEngine templateEngine;

  private final PlayerLobby playerLobby;

  static final String TITLE_ATTR = "title";

  static final String NEW_PLAYER_ATTR = "newPlayer";
  static final String PLAYER_LIST_ATTR = "playerList";
  static final String CUR_PLAYER_ATTR = "currentUser";
  static final String NUM_PLAYERS_ATTR = "numPlayers";

  static final String SIGNEDIN = "signedIn";

  static final String VIEW_NAME = "home.ftl";

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
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
    //
    Map<String, Object> vm = new HashMap<>();
    Session curSession = request.session();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);

    // show the number of signed in players
    vm.put(NUM_PLAYERS_ATTR, playerLobby.getNumPlayers());

    vm.put(PLAYER_LIST_ATTR, playerLobby.getPlayers());

    if(curSession.attribute(SIGNEDIN) == null || !(Boolean)curSession.attribute(SIGNEDIN)){
      curSession.attribute(SIGNEDIN, Boolean.FALSE);
      return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }else{
      vm.put(PLAYER_LIST_ATTR, playerLobby.getPlayers());
      vm.put(CUR_PLAYER_ATTR, playerLobby.getCurrentPlayer(PostSigninRoute.USERNAME_PARAM));

      vm.put("message", Message.info("Signin successful")); //temporary placeholder
      return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
  }
}
