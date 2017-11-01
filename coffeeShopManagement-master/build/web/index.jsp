<%-- 
    Document   : index
    Created on : Oct 23, 2017, 2:20:31 PM
    Author     : MYNVTSE61526
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>PhêCàPhê</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/coffeeShopStyle.css" rel="stylesheet" type="text/css"/>
    </head>
    <body onload="onloadFunction()">
        <div class="header-lock">
            <div class="header-item">
                <div class="logo-block">
                    <div class="logo-img">
                        <a href="/coffeeShopManagement/">
                            <img src="img/phecapheLogo.png" alt=""/>
                        </a>
                    </div>
                </div>
                <div class="user-information-block">
                    <div class="information-block">
                        <div class="user-infor-item user-img">
                            <img src="img/User.png" alt=""/>
                        </div>
                        <div class="user-infor-item user-action"> 
                            <span class="user-name">Mynvt</span>
                            <span class="user-action-item"><i class="fa fa-caret-down"></i></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="content-block">
            <div class="group-action-btn">
                <div class="action-btn-block">
                    <div class="action-btn-item">
                        <a class="btn" onclick="getStatisticInThisMonth()">Thống kê doanh thu theo ngày</a>
                    </div>
                    <div class="action-btn-item">
                        <!--<a class="btn" onclick="printMenu()">In menu</a>-->
                        <a class="btn" target="_blank" href="/coffeeShopManagement/PrintMenuServlet">In menu</a>
                    </div>
                    <div class="action-btn-item">
                        <a class="btn" onclick="addNewTable()">Thêm bàn</a>
                    </div>
                    <div class="action-btn-item">
                        <a class="btn" href="/coffeeShopManagement/GetMenuServlet">Cập nhập menu</a>
                    </div>
                    <div class="action-btn-item">
                        <a class="btn" onclick="getDataCompetitor('duvang')">Du vang</a>
                    </div>

                </div>
            </div>
            <div class="content-management-block">
                <div class="table-management-block">
                    <ul class="list-table" id="listTable">
                    </ul>
                </div>       
            </div>
        </div>
    </body>
</html>
<script src="js/commonJS.js" type="text/javascript"></script>
<script src="js/coffeeShopJs.js" type="text/javascript"></script>
<div id="MenuModal" class="modal" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-item">
                    <div class="modal-header-title">
                        Order
                    </div>
                </div>
                <div class="modal-header-item">
                    <div class="modal-header-btn-close">
                        <a class="btn btn-danger" onclick="openMenuModal('MenuModal', 1)"><i class="fa fa-times"></i></a>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="menu-block">

                </div>
                <div class="order-block">
                    <div class="order-item-block-title">
                        <ul>
                            <li>Tên sản phẩm</li>
                            <li>Số lượng</li>
                            <li>Giá</li>
                            <li>Thao tác</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="announceModal" class="modal" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-item">
                    <div class="modal-header-title">
                        Thông Báo
                    </div>
                </div>
                <div class="modal-header-item">
                    <div class="modal-header-btn-close">
                        <a class="btn btn-danger" onclick="openModal('announceModal', '')"><i class="fa fa-times"></i></a>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="annouce-content" id="annouceContent">

                </div>
            </div>
        </div>
    </div>
</div>
<div id="statictis" class="modal" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-item">
                    <div class="modal-header-title">
                        Thống kê doanh thu từ <span id="statictis-start"></span> đến <span id="statictis-end"></span>
                    </div>
                </div>
                <div class="modal-header-item">
                    <div class="modal-header-btn-close">
                        <a class="btn btn-danger" onclick="openModal('statictis', '')"><i class="fa fa-times"></i></a>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="statictis-content" id="statictisContent">

                </div>
            </div>
        </div>
    </div>
</div>

