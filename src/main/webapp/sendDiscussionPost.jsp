<%-- 
    Document   : sendDisscussionPost
    Created on : Mar 7, 2015, 10:38:31 PM
    Author     : Sam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="AddDiscussionPost" method="POST">
            <input type="text" name="author" placeholder="author"><br>
            <input type="text" name="comment" placeholder="comment"><br>
            <input type="submit" value="Submit">    
        </form>
    </body>
</html>
