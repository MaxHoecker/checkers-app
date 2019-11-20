<!DOCTYPE html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>Web Checkers | ${title}</title>
</head>
<body>
    <div class="page">
        <h1>Replay</h1>
        <div>
                <#include "message.ftl" />
                <form action="/replay/prompt/response" method="post">
                        <button type="submit" name="answer" value="true">yes</button>
                        <button type="submit" name="answer" value="false">no</button>
                 </form>
        </div>
    </div>
</body>