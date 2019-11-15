<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
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

    <h3>Click a Player to Spectate Their Game</h3>
    <form action="/game" method="post">
    <#list playerList as player>
        <ul style="list-style-type:none">
            <li>
                <button type="submit" name="spectating" value="${player}">${player}</button>
            </li>
        </ul>
    </#list>
    </form>

  </div>
</div>
</body>

</html>