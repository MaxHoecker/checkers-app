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
                    <ul style="list-style-type:none">
                                <li>
                                    <button type="submit" name="answer" value="true">yes</button>
                                </li>
                                <li>
                                    <button type="submit" name="answer" value="false">no</button>
                                </li>
                     </ul>
                 </form>
        </div>
    </div>
</body>