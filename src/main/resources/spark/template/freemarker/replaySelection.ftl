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

    <#if hasSaved == true>
        <h3>Click the replay you would like to watch</h3>
        <form action="/replay/game" method="post">
            <#list replays as replay>
                <ul style="list-style-type:none">
                    <li>
                        <button type="submit" name="replay" value="${replay}">${replay}</button>
                    </li>
                </ul>
            </#list>
        </form>

    <#else>
        <h3>No saved replays</h3>
    </#if>




  </div>
</div>
</body>

</html>