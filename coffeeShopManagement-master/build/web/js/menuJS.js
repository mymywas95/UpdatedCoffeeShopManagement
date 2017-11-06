var s = {
    "menu": []
};
function saveToLocalStorage() {
    localStorage.setItem("myMenu", JSON.stringify(s));
    var menuStored = localStorage.getItem("myMenu");
    if (typeof (menuStored) == "undefined" || menuStored == null) {
        openModal("announceModal", "Cập nhập menu thất bại");
    } else {
        window.location.replace("http://localhost:8084/coffeeShopManagement/");
    }
}
function addItemToMenu(checkbox, categoryName, id, name, price) {
    if (checkbox.querySelector("input") != null) {
        checkbox.querySelector("input").checked = true;
    }
    if (s.menu.length == 0) {
        addCateToMenu(categoryName, s.menu);
        addProductToMenu(id, name, price, s.menu[0].productList);
    } else {
        var existCategory = false;
        var existProduct = false;
        for (var i = 0; i < s.menu.length; i++) {
            if (categoryName == s.menu[i].CategoryName) {
                for (var j = 0; j < s.menu[i].productList.length; j++) {
                    if (name == s.menu[i].productList[j].name) {
                        s.menu[i].productList[j].price = price;
                        existProduct = true;
                        break;
                    }
                }
                if (existProduct == true) {
                    existCategory = true;
                    break;
                }
                if (existProduct == false) {
                    existCategory = true;
                    addProductToMenu(id, name, price, s.menu[i].productList);
                    break;
                }
            }
        }
        if (existCategory == false) {
            addCateToMenu(categoryName, s.menu);
            addProductToMenu(id, name, price, s.menu[s.menu.length - 1].productList);
        }
    }
}
function valuation(checkbox, categoryName, id, name) {
    checkbox.querySelector("input").checked = true;
    var valuatedPrice = checkbox.querySelector("input[type = 'number']").value;
    if (valuatedPrice > 0) {
        addItemToMenu(checkbox, categoryName, id, name, valuatedPrice)
    }
}
function valuationProductPrice(input, categoryName, id, name, price) {
    input.addEventListener("keyup", function (evt) {
        if (input.value <= 0) {
            openModal("announceModal", "Giá của sản phẩm phải lớn hơn 1k");
            setTimeout(function () {
                openModal("announceModal", "");
            }, 1600);
        } else {
            addItemToMenu(input, categoryName, id, name, input.value)
        }
    }, false);


}
function removeItemToMenu(checkbox, categoryName, name) {
    checkbox.querySelector("input").checked = true;
    if (s.menu.length != 0) {
        for (var i = 0; i < s.menu.length; i++) {
            if (categoryName == s.menu[i].CategoryName) {
                if (s.menu[i].productList.length > 0) {
                    for (var j = 0; j < s.menu[i].productList.length; j++) {
                        if (name == s.menu[i].productList[j].name) {
                            s.menu[i].productList.splice(j, 1);
                            if (s.menu[i].productList.length == 0) {
                                s.menu.splice(i, 1);
                            }
                            break;
                        }
                    }
                }

            }
        }
    }
}
function addCateToMenu(CateTitle, arr) {
    arr.push({
        "CategoryName": CateTitle,
        "productList": []
    })
}
function addProductToMenu(id, name, price, arr) {
    arr.push({
        "id": id,
        "name": name,
        "price": price
    })
}
function getProductCompetitor() {
    var el = document.getElementById("announceModal");

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            openModal("announceModal", "");
            if (this.responseText == "fail") {
                openModal("announceModal", "Cập nhập thất bại");

            } else {
                window.location.replace("http://localhost:8084/coffeeShopManagement/GetMenuServlet");
            }
        } else {
            if (!hasClass(el, 'show')) {
                openModal("announceModal", "Việc cập nhập thông tin sản phẩm sẽ mất nhiều thời gian, vui lòng không thoát khỏi màn hình hiện tại");
            }
        }
    };
    xhttp.open("POST", "/coffeeShopManagement/ParseDataFromCompetitorWeb");
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttp.send();
}
function scrollToCate(cateId) {
    var elmnt = cateId;
    elmnt.scrollIntoView();
}
window.smoothScroll = function (target) {
    var scrollContainer = target;
    do { //find scroll container
        scrollContainer = scrollContainer.parentNode;
        if (!scrollContainer)
            return;
        scrollContainer.scrollTop += 1;
    } while (scrollContainer.scrollTop == 0);

    var targetY = 0;
    do { //find the top of target relatively to the container
        if (target == scrollContainer)
            break;
        targetY += target.offsetTop - 85;
    } while (target = target.offsetParent);

    scroll = function (c, a, b, i) {
        i++;
        if (i > 30)
            return;
        c.scrollTop = a + (b - a) / 30 * i;
        setTimeout(function () {
            scroll(c, a, b, i);
        }, 20);
    }
    // start scrolling
    scroll(scrollContainer, scrollContainer.scrollTop, targetY, 0);
}
function setSelectedMenuItem() {
    var menuStored = localStorage.getItem("myMenu");
    if (typeof (menuStored) == "undefined" || menuStored == null) {
//        openModal("announceModal", "Ch");
    } else {
        var menuStoredParsed = JSON.parse(menuStored);
        s = menuStoredParsed;
        for (var i = 0; i < menuStoredParsed.menu.length; i++) {
            for (var j = 0; j < menuStoredParsed.menu[i].productList.length; j++) {
                var productId = "product" + menuStoredParsed.menu[i].productList[j].id;
                var productPriceId = "productPrice" + menuStoredParsed.menu[i].productList[j].id;
                document.getElementById(productPriceId).value = menuStoredParsed.menu[i].productList[j].price;
                document.getElementById(productId).checked = !document.getElementById(productId).checked;
            }
        }
    }
}