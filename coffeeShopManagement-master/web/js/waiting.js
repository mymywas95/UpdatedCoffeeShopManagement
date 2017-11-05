
function indexController() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            if (this.responseText == "success") {
                document.getElementById("goIndexForm").submit();
            } 
        } 
    };
    xhttp.open("POST", "/coffeeShopManagement/IndexServlet");
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttp.send();
}
