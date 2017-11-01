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
    <body>
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
            <div class="all-product-list-block">
                <div class="all-product-list-block-top">
                    <div class="all-product-list-title">
                        <div class="all-product-list-title-item">Danh sách sản phẩm</div>
                    </div>
                    <div class="all-product-list-action">
                        <a class="btn btn-normal" onclick="saveToLocalStorage(menu)">Cập nhập menu</a>
                        <a class="btn" href="/coffeeShopManagement/">Quay lại</a>
                        <a class="btn" href="/coffeeShopManagement/ParseDataFromCompetitorWeb">Cập nhập danh sách sản phẩm</a>
                    </div>
                </div>
                <div class="all-product-list-content">
                    <ul class="all-product-list-content-block">
                        <c:forEach var="AllMenuItem" items="${ListAllMenuItem}" >
                            <li class="all-product-list-content-item">
                                <div class="all-product-list-content-item-block">
                                    <div class="all-product-category-block">
                                        <div class="all-product-category-block-title">
                                            ${AllMenuItem.categoryName}
                                        </div>
                                    </div>
                                    <div class="all-product-block title">
                                        <ul>
                                            <li>Tên Sản Phẩm</li>
                                            <li>Không bán</li>
                                            <li>Giá <br>Dù vàng</li>
                                            <li>Giá <br>Unispace</li>
                                            <li>Định giá</li>
                                        </ul>
                                    </div>
                                    <div class="all-product-block">
                                        <c:forEach var="products" items="${AllMenuItem.listProduct}" >
                                            <ul>
                                                <li>${products.name}</li>
                                                <li onclick="removeItemToMenu(this, '${AllMenuItem.categoryName}', '${products.name}')"><input type="radio" name="'${products.id}'"></li>
                                                    <c:forEach var="productItem" items="${products.listProductItemDto}" >
                                                    <li onclick="addItemToMenu(this, '${AllMenuItem.categoryName}', '${products.id}', '${products.name}', '${productItem.price}')">${productItem.price}<input type="radio" name="'${products.id}'"></li>
                                                    </c:forEach>
                                                <li onclick="valuation(this, '${AllMenuItem.categoryName}', '${products.id}', '${products.name}')"><input type="radio" name="'${products.id}'"><input type="number" onclick="valuationProductPrice(this, '${AllMenuItem.categoryName}', '${products.id}', '${products.name}', '${productItem.price}')"></li>
                                            </ul>
                                        </c:forEach>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div id="menu"></div>
    </body>

</html>
<script src="js/commonJS.js" type="text/javascript"></script>
<script src="js/menuJS.js" type="text/javascript"></script>
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
