<%-- 
    Document   : home
    Created on : Mar 21, 2015, 10:50:31 AM
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Facebook API Test</title>
    </head>
    <body>
        <h1>Welcome, ${sessionScope.username}</h1>
        <p><a href="ReadNews">Click here</a> to access your news feed!</p>
        <form action="PostStuff" method="post">
            <p>Post some stuff to your news feed:</p>
            <textarea name = "postContent" rows="20" cols="60"></textarea>
            <input type="submit" value="Post" />
        </form>
    </body>
</html>
