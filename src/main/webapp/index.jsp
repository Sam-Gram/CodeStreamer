<%-- 
    Document   : index
    Created on : Mar 21, 2015, 10:05:36 AM
    Author     : David, Sam, Austin
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Code Streamers</title>
        <meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
        <!--Styles-->
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.css"/>
        <link rel="stylesheet" href="css/codemirror.css"/>
        <link rel="stylesheet" href="css/editor.css"/>
        <!--End Styles -->
        
        <!--Scripts-->
        <script src="bower_components/jquery/dist/jquery.js"></script>
        <script src="bower_components/bootstrap/dist/js/bootstrap.js"></script>
        <script src="js/codemirror.js"></script>
        <script src="js/cmExt/matchbrackets.js"></script>
        <script src="js/cmExt/closebrackets.js"></script>
        <script src="js/mode/clike/clike.js"></script>
        <script src="js/editor.js"></script>
        <!--End Scripts-->
        <script>
            window.fbAsyncInit = function() {
                FB.init({
                    appId      : '1375271102793701',
                    xfbml      : true,
                    version    : 'v2.2'
                });
            };

            (function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {
                    return;
                }
                js = d.createElement(s);
                js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));
        </script>
    </head>
    <body class="container">
        <nav class="navbar navbar">
           <div class="container-fluid">
               <div class="navbar-header">
                   <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="glyphicon glyphicon-menu-hamburger"></span>
                    </button>
                   <a href="" class="navbar-brand">Code Streamers <c:if test="${sessionScope.loggedIn}"><small>Welcome ${sessionScope.username}</c:if></small></a>
               </div>
               <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                   <ul class="nav navbar-nav navbar-right">
                       <c:choose>
                           <c:when test="${sessionScope.loggedIn}">
                               <li><a href="SignOut">Logout</a></li>
                            </c:when>
                               
                            <c:otherwise>
                                <li><a href="SignIn">Login with Facebook</a></li>
                            </c:otherwise>
                       </c:choose>
                       <li class="dropdown">
                           <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">About <span class="caret"></span></a>
                           <ul class="dropdown-menu" role="menu">
                               <li><a href="https://github.com/Samuel-Graham" target="_blank">Sam Graham</a></li>
                               <li><a href="https://github.com/davidaj3" target="_blank">David Jones <span class="glyphicon glyphicon-music"></span></a></li>
                               <li><a href="https://github.com/austinmoore-" target="_blank">Austin Moore</a></li>                     
                           </ul>
                       </li>
                   </ul>
               </div><!-- /.navbar-collapse -->
           </div>
        </nav>
        <div class="row">
            <div class="col-xs-6">
                <h3>Welcome to Code Streamers</h3>
                <p>Here at Code Streamers, you can broadcast your coding to others! Click the "New Stream" button to
                get started!</p>
                <a href="new" class="btn btn-default">New Stream</a>
            </div>
            <div class="col-xs-6">
                <h3>Streams</h3>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <c:forEach items="${keys}" var="current">
                            <a href="stream/${current}">${current}</a><br>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

