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
            if (this.responseText == "success") {
                window.location.replace("http://localhost:8084/coffeeShopManagement/GetMenuServlet");
            } else {
                openModal("announceModal", "Cập nhập thất bại");
            }
        } else {
            if (!hasClass(el, 'show')) {
                openModal("announceModal", "Việc cập nhập thông tin sản phẩm sẽ mất nhiều thời gian, vui lòng đợi trong giây lát");
            }
        }
    };
    xhttp.open("POST", "/coffeeShopManagement/ParseDataFromCompetitorWeb");
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttp.send();
}