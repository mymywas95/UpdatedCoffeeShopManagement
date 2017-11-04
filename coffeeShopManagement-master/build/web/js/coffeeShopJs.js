var s = {
    "menu": []
};
var o = {};
function onloadFunction() {
    var menuStored = localStorage.getItem("myMenu");

    if (typeof (menuStored) == "undefined" || menuStored == null) {
        window.location.replace("http://localhost:8084/coffeeShopManagement/GetMenuServlet");
    } else {
        setMenuContent();
        var orderStored = localStorage.getItem("order");
        if (typeof (orderStored) == "undefined" || orderStored == null) {
            addNewTable();
        } else {
            setTableList();
        }

    }
}

function openMenuModal(id, tableId) {

    var el = document.getElementById(id);
    o = {};
    document.getElementById("tableId").value = tableId;
    var tableExist = false;
    if (hasClass(el, 'show')) {
        setTableList();
        removeClass(el, 'show');
        o = {};
    } else {
        var orderStored = localStorage.getItem("order");
        if (orderStored !== null && typeof (orderStored) !== "undefined") {
            var orderStoredParsed = JSON.parse(orderStored);
            var currentTable;
            for (var i = 0; i < orderStoredParsed.order.length; i++) {
                if (orderStoredParsed.order[i].tableId == tableId) {
                    currentTable = orderStoredParsed.order[i];
                    setOrderContent(currentTable, 1);
                    o = currentTable;
                    tableExist = true;
                    break;
                }
            }
            if (tableExist == false) {
                var myNode = document.getElementsByClassName("order-item-block");
                var myNodeTotal = document.getElementsByClassName("order-total");
                var myNodeSave = document.getElementsByClassName("btn-save-order");
                var myNodePay = document.getElementsByClassName("btn-pay-order");
                if (typeof (myNode[0]) !== "undefined") {
                    while (myNode[0]) {
                        myNode[0].parentNode.removeChild(myNode[0]);
                    }
                }
                if (typeof (myNodeTotal[0]) !== "undefined") {
                    while (myNodeTotal[0]) {
                        myNodeTotal[0].parentNode.removeChild(myNodeTotal[0]);
                    }
                }
                if (typeof (myNodeSave[0]) !== "undefined") {
                    while (myNodeSave[0]) {
                        myNodeSave[0].parentNode.removeChild(myNodeSave[0]);
                    }
                }
                if (typeof (myNodePay[0]) !== "undefined") {
                    while (myNodePay[0]) {
                        myNodePay[0].parentNode.removeChild(myNodePay[0]);
                    }
                }

            }
        }
        addClass(el, 'show');
    }
}
function ChangeTableItem(id, name, price, quantity, btn) {
    var orderBlock = document.getElementsByClassName("order-block")[0];
    var tableId = document.getElementById("tableId").value;

    if (typeof (o.productList) == "undefined") {
        addTableToOrder(tableId, price, o);
        addProductToOrder(id, name, price, quantity, o.productList);
    } else {
        var existTable = false;
        var existProduct = false;
        for (var j = 0; j < o.productList.length; j++) {
            if (name == o.productList[j].name) {
                if (o.productList[j].quantity > 0) {
                    o.productList[j].quantity = o.productList[j].quantity + (quantity);
                    o.tableTotal = o.tableTotal + (price);
                    if (o.productList[j].quantity == 0) {
                        o.productList.splice(j, 1);
                    }
                }
                existProduct = true;
                break;
            }
        }
        if (existProduct == false) {
            existTable = true;
            if (quantity > 0) {
                addProductToOrder(id, name, price, quantity, o.productList);
                o.tableTotal = o.tableTotal + (price);
            } else {
                openModal("announceModal", "Không có sản phẩm này trong order");
                setTimeout(function () {
                    openModal("announceModal", "");
                }, 1600);
            }
        }
    }
    setOrderContent(o, 0);
}
function addTableToOrder(tableId, total, arr) {
    arr.tableId = tableId;
    arr.tableStatus = "Chưa thanh toán";
    arr.tableTotal = total;
    arr.productList = [];
}
function addProductToOrder(id, name, price, quantity, arr) {
    arr.push({
        "id": id,
        "name": name,
        "price": price,
        "quantity": quantity
    })
}
function addNewTableToOrder(tableId, total, arr) {
    arr.push({
        "tableId": tableId,
        "tableStatus": "Chưa thanh toán",
        "tableTotal": 0,
        "productList": []
    })
}
function setMenuContent() {
    var menuContentStored = localStorage.getItem("myMenu");
    var menuContent = JSON.parse(menuContentStored);
    var MenuBlock = document.getElementsByClassName("menu-block")[0];
    var tableBlock = document.createElement("input");
    tableBlock.setAttribute('value', '1');
    tableBlock.setAttribute('id', 'tableId');
    MenuBlock.appendChild(tableBlock);
    for (var i = 0; i < menuContent.menu.length; i++) {
        var categoryBlock = document.createElement("div");
        categoryBlock.className += "category-block";
        var category = document.createElement("div");
        category.className += "category-title";
        var categoryContent = category.appendChild(document.createTextNode(menuContent.menu[i].CategoryName));
        var productList = document.createElement("div");
        productList.className += "product-list-block";
        for (var j = 0; j < menuContent.menu[i].productList.length; j++) {
            var productId = menuContent.menu[i].productList[j].id;
            var productName = menuContent.menu[i].productList[j].name;
            var productPrice = menuContent.menu[i].productList[j].price + "";
            var productItemBlock = document.createElement("ul");
            var productPriceSplited = productPrice.split(".");
            var productPriceFormated = productPriceSplited[0] + "k";
            var productItemNameBlock = document.createElement("li");
            productItemNameBlock.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + productPrice + ", 1, this)");
            productItemBlock.appendChild(productItemNameBlock).appendChild(document.createTextNode(productName));
            var productItemPriceBlock = document.createElement("li");
            productItemPriceBlock.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + productPrice + ", 1, this)");
            productItemBlock.appendChild(productItemPriceBlock).appendChild(document.createTextNode(productPriceFormated));
//            productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productPriceFormated));
            var lastChild = document.createElement("li");
            var btnAdd = document.createElement("a");
            btnAdd.className += "btn btn-normal btn-add";
            btnAdd.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + productPrice + ", 1, this)");
//            var btnAddText = document.createElement("i");
//            btnAddText.className += "fa fa-plus";
            var btnAddText = document.createTextNode("chọn");
            btnAdd.appendChild(btnAddText);
            lastChild.appendChild(btnAdd);

//            var btnMinus = document.createElement("a");
//            btnMinus.className += "btn btn-minus";
//            btnMinus.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + (-productPrice) + ", -1, this)");
//            var btnMinusText = document.createElement("i");
//            btnMinusText.className += "fa fa-minus";
//            btnMinus.appendChild(btnMinusText);
//            lastChild.appendChild(btnMinus);
            productItemBlock.appendChild(lastChild);
            productList.appendChild(productItemBlock);
        }
        categoryBlock.appendChild(category);
        categoryBlock.appendChild(productList);
        MenuBlock.appendChild(categoryBlock);
    }
    document.getElementsByClassName("modal-body")[0].appendChild(MenuBlock);
}
function setTableList() {
    var myNodeListTable = document.getElementsByClassName("table-management-item");
    if (typeof (myNodeListTable[0]) !== "undefined") {
        while (myNodeListTable[0]) {
            myNodeListTable[0].parentNode.removeChild(myNodeListTable[0]);
        }
    }
    var orderStored = localStorage.getItem("order");
    var orderContent = JSON.parse(orderStored);
    var listTableBlock = document.getElementById("listTable");
    for (var i = 0; i < orderContent.order.length; i++) {
        var tableBlock = document.createElement("li");
        tableBlock.className += "table-management-item";
        var tableItemBlock = document.createElement("a");
        tableItemBlock.className += "table-item-block";
        tableItemBlock.setAttribute("onclick", "openMenuModal('MenuModal'," + orderContent.order[i].tableId + ")")

        var tableItem = document.createElement("div");
        tableItem.className += "table-item";
        var tableName = document.createElement("div");
        tableName.className += "table-name";
        var tableProductItem = document.createElement("div");
        tableProductItem.className += "table-product-item";

        var tableTotal = document.createElement("div");
        tableTotal.className += "table-total";
        var tableTotalTitle = document.createElement("span");
        var tableTotalDetail = document.createElement("span");
        tableTotalDetail.className += "table-total-detail";
        var payBtn = document.createElement("a");
        payBtn.className += "btn btn-normal btn-payment";


        if (orderContent.order[i].productList.length <= 0) {
            tableItemBlock.className += " table-unresolve";
            tableTotalTitle.appendChild(document.createTextNode("Tổng : "));
            tableTotalDetail.appendChild(document.createTextNode("0"));
            tableTotal.appendChild(tableTotalTitle);
            tableTotal.appendChild(tableTotalDetail);
        } else {
            for (var j = 0; j < orderContent.order[i].productList.length; j++) {
                var productBlock = document.createElement("ul");
                var productName = document.createElement("li");
                productName.appendChild(document.createTextNode(orderContent.order[i].productList[j].name));
                var productQuantity = document.createElement("li");
                productQuantity.appendChild(document.createTextNode("x " + orderContent.order[i].productList[j].quantity));
                productBlock.appendChild(productName);
                productBlock.appendChild(productQuantity);
                tableProductItem.appendChild(productBlock);
            }
            tableTotalTitle.appendChild(document.createTextNode("Tổng : "));
            tableTotalDetail.appendChild(document.createTextNode(" " + orderContent.order[i].tableTotal + "k"));
            payBtn.appendChild(document.createTextNode("Thanh toán"));
            payBtn.setAttribute("onclick", "solveOrder('" + orderContent.order[i].tableId + "')");
            tableTotal.appendChild(tableTotalTitle);
            tableTotal.appendChild(tableTotalDetail);
            tableTotal.appendChild(payBtn);
        }
        tableName.appendChild(document.createTextNode("Bàn " + orderContent.order[i].tableId));
        tableItem.appendChild(tableName);
        tableItem.appendChild(tableProductItem);
        tableItem.appendChild(tableTotal);
        tableItemBlock.appendChild(tableItem);
        tableBlock.appendChild(tableItemBlock);
        listTableBlock.insertBefore(tableBlock, listTableBlock[0]);
    }
}
//link = 1 is show pay, 0 is not
function setOrderContent(orderContent, link) {

    var myNode = document.getElementsByClassName("order-item-block");
    var myNodeTotal = document.getElementsByClassName("order-total");
//    var myNodeSave = document.getElementsByClassName("btn-save-order");
//    var myNodePay = document.getElementsByClassName("btn-pay-order");
    var myNodePay = document.getElementsByClassName("btn-save-block");
    if (typeof (myNode[0]) !== "undefined") {
        while (myNode[0]) {
            myNode[0].parentNode.removeChild(myNode[0]);
        }
    }
    if (typeof (myNodeTotal[0]) !== "undefined") {
        while (myNodeTotal[0]) {
            myNodeTotal[0].parentNode.removeChild(myNodeTotal[0]);
        }
    }
//    if (typeof (myNodeSave[0]) !== "undefined") {
//        while (myNodeSave[0]) {
//            myNodeSave[0].parentNode.removeChild(myNodeSave[0]);
//        }
//    }
    if (typeof (myNodePay[0]) !== "undefined") {
        while (myNodePay[0]) {
            myNodePay[0].parentNode.removeChild(myNodePay[0]);
        }
    }
    var orderBlock = document.createElement("div");
    orderBlock.className += "order-item-block";
    var productListLength = orderContent.productList.length;
    if (productListLength >= 0) {
        for (var j = 0; j < productListLength; j++) {
            var productId = orderContent.productList[j].id;
            var productName = orderContent.productList[j].name;
            var productPrice = orderContent.productList[j].price;
            var productQuabtity = orderContent.productList[j].quantity;
            var lastChild = document.createElement("div");
            var btnAdd = document.createElement("a");
            btnAdd.className += "btn btn-normal btn-add";
            btnAdd.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + productPrice + ", 1, this)");
            var btnAddText = document.createElement("i");
            btnAddText.className += "fa fa-plus";
            btnAdd.appendChild(btnAddText);
            var btnMinus = document.createElement("a");
            btnMinus.className += "btn btn-minus";
            btnMinus.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + (-productPrice) + ", -1, this)");
            var btnMinusText = document.createElement("i");
            btnMinusText.className += "fa fa-minus";
            btnMinus.appendChild(btnMinusText);
            lastChild.appendChild(btnAdd);
            lastChild.appendChild(btnMinus)
            var productItemBlock = document.createElement("ul");
            var productNameItemBlock = document.createElement("li");
            var deleteSelectedItem = document.createElement("i");
            deleteSelectedItem.setAttribute("onclick", "ChangeTableItem(" + productId + ",'" + productName + "'," + (-(productPrice * productQuabtity)) + "," + (-productQuabtity) + ", this)");
            deleteSelectedItem.className += "fa fa-times-circle";
            productNameItemBlock.appendChild(deleteSelectedItem);
            productNameItemBlock.appendChild(document.createTextNode(productName))
            productItemBlock.appendChild(productNameItemBlock);
            productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productQuabtity));
            productItemBlock.appendChild(document.createElement("li")).appendChild(document.createTextNode(productPrice + "k"));
            productItemBlock.appendChild(document.createElement("li")).appendChild(lastChild);
            orderBlock.appendChild(productItemBlock);
        }
    }

    var orderToalBlock = document.createElement("div");
    orderToalBlock.appendChild(document.createTextNode("Tổng : "));
    orderToalBlock.className += "order-total";
    var orderToalDetail = document.createElement("span");
    orderToalDetail.appendChild(document.createTextNode(orderContent.tableTotal + "k"));
    orderToalBlock.appendChild(orderToalDetail);
    var orderSaveBlock = document.createElement("div");
    orderSaveBlock.className += "btn-save-block";
    var btnSaveOrder = document.createElement("a");
    btnSaveOrder.className += "btn btn-save-order";
    btnSaveOrder.setAttribute("onclick", "saveOrderToLocalStorage()");
    btnSaveOrder.appendChild(document.createTextNode("Xác nhận"));

    document.getElementsByClassName("order-block")[0].appendChild(orderBlock);
    var btnCancelOrder = document.createElement("a");
    btnCancelOrder.className += "btn btn-danger btn-cancel-order";
    btnCancelOrder.setAttribute("onclick", "cancelOrder('" + orderContent.tableId + "')");
    btnCancelOrder.appendChild(document.createTextNode("Hủy order"));
    orderSaveBlock.appendChild(btnCancelOrder);
    orderSaveBlock.appendChild(btnSaveOrder);
    if (orderContent.productList.length > 0) {
        document.getElementsByClassName("order-block")[0].appendChild(orderToalBlock);
        document.getElementsByClassName("order-block")[0].appendChild(orderSaveBlock);
    }


}
function saveOrderToLocalStorage() {
    var existTable = false;
    var orderStored = localStorage.getItem("order");

    if (typeof (orderStored) == "undefined" || orderStored == null) {
        orderStored = {"order": []};
        orderStored.order.push(o);
        orderStored.order.sort(function (a, b) {
            return a.tableId - b.tableId;
        });
        var orderStoredParsed = JSON.stringify(orderStored);
        localStorage.setItem("order", orderStoredParsed);

    } else {
        var orderStoredParsed = JSON.parse(orderStored);
        for (var i = 0; i < orderStoredParsed.order.length; i++) {
            if (orderStoredParsed.order[i].tableId == o.tableId) {
                orderStoredParsed.order[i].tableStatus = o.tableStatus;
                orderStoredParsed.order[i].tableTotal = o.tableTotal;
                orderStoredParsed.order[i].productList = o.productList;
                existTable = true;
                orderStoredParsed.order.sort(function (a, b) {
                    return a.tableId - b.tableId;
                });
                localStorage.setItem("order", JSON.stringify(orderStoredParsed));
                break;
            }
        }
        if (existTable == false) {
            orderStoredParsed.order.push(o);
            orderStoredParsed.order.sort(function (a, b) {
                return a.tableId - b.tableId;
            });
            localStorage.setItem("order", JSON.stringify(orderStoredParsed));
        }
    }
    setOrderContent(o, 1);
    setTableList();
    var el = document.getElementById("MenuModal");
    if (hasClass(el, 'show')) {
        removeClass(el, 'show');
    }

}
function cancelOrder(tableId) {
    var orderStored = localStorage.getItem("order");
    var orderStoredParsed = JSON.parse(orderStored);
    for (var i = 0; i < orderStoredParsed.order.length; i++) {
        if (orderStoredParsed.order[i].tableId == tableId) {
            orderStoredParsed.order[i].tableStatus = "Đã Thanh toán";
            orderStoredParsed.order[i].tableTotal = 0;
            orderStoredParsed.order[i].productList = [];
            localStorage.setItem("order", JSON.stringify(orderStoredParsed));
            setOrderContent(orderStoredParsed.order[i], 1);
            var el = document.getElementById("MenuModal");
            if (hasClass(el, 'show')) {
                removeClass(el, 'show');
            }
            break;
        }
    }
    var myNodePay = document.getElementsByClassName("table-management-item");
    if (typeof (myNodePay[0]) !== "undefined") {
        while (myNodePay[0]) {
            myNodePay[0].parentNode.removeChild(myNodePay[0]);
        }
    }
    setTableList();
}
function addNewTable() {
    var orderStored = localStorage.getItem("order");
    if (typeof (orderStored) == "undefined" || orderStored == null) {
        var newTable = {"order": []};
        addNewTableToOrder(1, 0, newTable.order);
        localStorage.setItem("order", JSON.stringify(newTable));
    } else {
        var orderStoredParsed = JSON.parse(orderStored);

        var newTableId = orderStoredParsed.order[orderStoredParsed.order.length - 1].tableId + 1;
        addNewTableToOrder(newTableId, 0, orderStoredParsed.order);
        orderStoredParsed.order.sort(function (a, b) {
            return a.tableId - b.tableId;
        });
        localStorage.setItem("order", JSON.stringify(orderStoredParsed));
    }
    setTableList();
}
function printMenu() {
    var menuStored = localStorage.getItem("myMenu");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            if (this.responseText == "success") {
                window.open("/coffeeShopManagement/PrintMenuServlet", "_blank");
            } else {
                openModal("announceModal", "In menu thất bại");
            }

        }
    };
    xhttp.open("POST", "/coffeeShopManagement/CreateMenuFileServlet");
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttp.send("menu=" + menuStored);
}
function getStatisticInThisMonth() {
    var fromDate = getFromDate();
    var endDate = getEndDate();
    var dateRange = {"fromDate": fromDate, "endDate": endDate};
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            if (this.responseText == "fail") {
                openModal("announceModal", "Thống kê thất bại");
            } else {
                var statictisContent = document.getElementsByClassName("statictis-tbl");
                if (typeof (statictisContent[0]) !== "undefined") {
                    while (statictisContent[0]) {
                        statictisContent[0].parentNode.removeChild(statictisContent[0]);
                    }
                }
                document.getElementById("statictis-start").innerHTML = fromDate;
                document.getElementById("statictis-end").innerHTML = endDate;
                var statisticRs = JSON.parse(this.responseText);
                var table = document.createElement("table");
                table.className += "statictis-tbl";
                var tableHeader = document.createElement("thead");
                var tableHeaderRow = document.createElement("tr");
                var tableHeaderThDate = document.createElement("th");
                tableHeaderThDate.appendChild(document.createTextNode("Ngày"));
                var tableHeaderThTotal = document.createElement("th");
                tableHeaderThTotal.appendChild(document.createTextNode("Tổng doanh thu"));
                tableHeaderRow.appendChild(tableHeaderThDate);
                tableHeaderRow.appendChild(tableHeaderThTotal);
                tableHeader.appendChild(tableHeaderRow);
                var tableBody = document.createElement("tbody");

//                for (var i = 0; i < statisticRs.statictis.length; i++) {
//                    var tableHeaderTh = document.createElement("th");
//                    tableHeaderTh.appendChild(document.createTextNode(statisticRs.statictis[i].date));
//                    tableHeaderRow.appendChild(tableHeaderTh);
//                }

                table.appendChild(tableHeader);
                for (var i = 0; i < statisticRs.statictis.length; i++) {
                    var tableBodyRow = document.createElement("tr");
                    var tableBodyTdDate = document.createElement("td");
                    var tableBodyTdTotal = document.createElement("td");
                    tableBodyTdDate.appendChild(document.createTextNode(statisticRs.statictis[i].date));
                    tableBodyTdTotal.appendChild(document.createTextNode(statisticRs.statictis[i].total + "k"));
                    tableBodyRow.appendChild(tableBodyTdDate);
                    tableBodyRow.appendChild(tableBodyTdTotal);
                    tableBody.appendChild(tableBodyRow);
                }

                table.appendChild(tableBody);
                document.getElementById("statictisContent").appendChild(table);
                var el = document.getElementById("statictis");
                if (hasClass(el, 'show')) {
                    removeClass(el, 'show');
                } else {
                    addClass(el, 'show');
                }
            }

        }
    };
    xhttp.open("POST", "/coffeeShopManagement/StatisticServlet");
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttp.send("dateRange=" + JSON.stringify(dateRange));
}
function getFromDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!

    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    var today = yyyy + '-' + mm + '-' + '01';
    return today;
}
function getEndDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!

    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    if (mm == '04' || mm == '06' || mm == '09' || mm == '11') {
        var today = yyyy + '-' + mm + '-' + '30';
    } else {
        if (mm == '02') {
            if (yyyy % 4 == 0 && yyyy % 100 != 0) {
                var today = yyyy + '-' + mm + '-' + '29';
            } else {
                var today = yyyy + '-' + mm + '-' + '28';
            }
        } else {
            var today = yyyy + '-' + mm + '-' + '31';
        }
    }
    return today;
}
function getDataCompetitor(competitorName) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
        }
    };
    xhttp.open("GET", "http://duvangcoffee.com/api/data.php?type=product");
    xhttp.setRequestHeader("Content-Type", "text/html");
    xhttp.send();
}
