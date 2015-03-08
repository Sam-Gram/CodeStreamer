<%-- 
    Document   : discussion
    Created on : Mar 7, 2015, 7:10:23 PM
    Author     : Sam
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
        <script src="js/jquery-1.11.2.min.js"></script>
        <script src="js/angular.min.js"></script>
        <script src="js/angular-route.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/app.css">
        <link rel="stylesheet" type="text/css" href="css/hover.css">
        <style>
            .author {
                background-color: black;
                color: white;
            }
        </style>
    </head>
    <body class="container">
        <h1>Discussion</h1>
       <c:forEach var="post" items="${posts}">
           <p><span class="author">${post.author}</span> ${post.comment}</p>
       </c:forEach>
   
       <a href="sendDiscussionPost.jsp">Add Post</a>
    </body>
</html>
