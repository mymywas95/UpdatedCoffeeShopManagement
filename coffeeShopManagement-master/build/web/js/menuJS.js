var s = {
    "menu": []
};
function saveToLocalStorage(selectedMenuContentId) {
    localStorage.setItem("myMenu", JSON.stringify(s));
    var menuStored = localStorage.getItem("myMenu");
    if (typeof (menuStored) == "undefined" || menuStored == null) {
        console.log("fail");
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
    document.getElementById("menu").innerHTML = JSON.stringify(s);
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
                            if(s.menu[i].productList.length == 0){
                                s.menu.splice(i,1);
                            }
                            break;
                        }
                    }
                }

            }
        }
    }
    document.getElementById("menu").innerHTML = JSON.stringify(s);
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