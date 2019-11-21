<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />
    <#if isSignedIn == false>
      <h3>Number of Players</h3>
      <p>${numPlayers}</p>
    <#elseif (numPlayers > 1)>
        <div class = "SignIn">
                <a href="/spectator">Players In Game</a>
                <br>
                <a href="/replay">Saved replays</a>
        </div>
        <h3>Player List</h3>
            <form action="/game" method="post">
            <#list playerList as player>
                <ul style="list-style-type:none">
                    <li>
                        <button type="submit" name="opponent" value="${player}">${player}</button>
                    </li>
                </ul>
            </#list>
            </form>

    </#if>


    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
