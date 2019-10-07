<!DOCTYPE html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>Web Checkers | ${title}</title>
</head>
<body>
    <div class="page">
        <h1>Sign In</h1>
        <div class = "SignIn">
                <#include "message.ftl" />
                <form action=${page} method="post">
                    Username: <input type="text" name="signinField" >
                    <br>
                    <button type=submit class="player">
                        Sign-in
                    </button>
                </form>
        </div>
    </div>
</body>