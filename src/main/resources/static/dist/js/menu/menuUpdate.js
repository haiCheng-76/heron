document.body.style.display = "block";
$(".formCommit").click(function () {
    var menuData = {
        "id": menuId,
        "parentId": ztree.getCheckedNodes()[0].id,
        "menuName": $(".menu_name").val(),
        "url": $(".url").val(),
        "permission": $(".menu_permission").val(),
        "isDelete": $("input[name='menuStatus']:checked").val(),
        "sort": $(".menu_sort").val()
    };
    $.ajax({
        type: "POST",
        url: '/menu/updateMenu',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(menuData),
        success: function (res) {
            if (res.code == 1) {
                window.parent.location.reload();

            } else {
                layer.msg(res.message);
            }
        },
        error: function (res) {

        }
    });
});
