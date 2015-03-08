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
        <script src="js/jquery-1.11.2.min.js"></script>
        <script src="js/angular.min.js"></script>
        <script src="js/angular-route.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/app.css">
        <link rel="stylesheet" type="text/css" href="css/hover.css">
    </head>
    <body class="container">
        <h1>Add new post</h1>
        <form action="AddDiscussionPost" method="POST">
            <input type="text" name="author" placeholder="author"><br>
            <input type="text" name="comment" placeholder="comment"><br>
            <input type="submit" value="Submit">    
        </form>
    </body>
</html>
