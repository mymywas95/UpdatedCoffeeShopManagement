<%-- 
    Document   : menu
    Created on : Oct 19, 2017, 4:56:50 PM
    Author     : MYNVTSE61526
--%>

<%@page contentType="text/html; charset=UTF-8" %> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/coffeeShopStyle.css" rel="stylesheet" type="text/css"/>
        <title>PhêCàPhê</title>
    </head>
    <body onload="indexController()">
        <div class="waiting-block">
            <div class="waiting-block-item">
                <div class="waiting-block-content">
                    <div>
                        Hệ thống đang xử lý
                        <br>
                        Vui lòng đợi trông giây lát....
                    </div>
                </div>
            </div>
        </div>
        <form action="ProcessServlet" method="POST" id="goIndexForm">
            <button type="submit" name="btnAction" value="goIndex">goIndex</button>
        </form>
    </body>
</html>
<script src="js/waiting.js" type="text/javascript"></script>
