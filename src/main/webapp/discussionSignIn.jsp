<%-- 
    Document   : discussionSignIn
    Created on : Mar 7, 2015, 4:06:16 PM
    Author     : Sam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Discussion Page</title>
        <script src="js/jquery-1.11.2.min.js"></script>
        <script src="js/angular.min.js"></script>
        <script src="js/angular-route.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/app.css">
        <link rel="stylesheet" type="text/css" href="css/hover.css">
    </head>
    <body class="container">
        <h1>Log In</h1>
        <form action="DiscussionLogin" method="POST">
            <input type="text" name="username" placeholder="username">    
            <input type="text" name="password" placeholder="password">    
            <input type="submit" value="Submit">    
        </form>
    </body>
</html>
