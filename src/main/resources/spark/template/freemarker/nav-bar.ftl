 <div class="navigation">
  <#if currentUser??>
    <a href="/">my home</a> |
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();">sign out [${currentUser.name}]</a>
    </form> |
    <a href="/replay">replays</a>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
