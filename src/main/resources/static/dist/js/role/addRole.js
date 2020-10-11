document.body.style.display = "block";
var treeChecked;
var setting = {
    check: {
        enable: true,
        chkStyle: "checkbox",
        chkboxType: {"Y": "s", "N": "s"}
    }, data: {
        simpleData: {
            enable: true
        }
    }
};
var tree;
$(document).ready(function () {
    var data = ["D", "M", "B"];
    var treeData = null;
    $.ajax({
        type: "POST",
        url: "/menu/getZtreeMenu",
        async: false,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (res) {
            treeData = res;
        }
    });
    tree = $.fn.zTree.init($("#menuPermission"), setting, treeData);
    tree.expandAll(true);

});

$(".formCommit").click(function () {
    treeChecked = tree.getCheckedNodes();
    if (treeChecked.length == 0) {
        layer.msg("请选择归属关系!");
        return;
    }
    let arr = [];
    for (var i = 0; i < treeChecked.length; i++) {
        arr.push(treeChecked[i].id)
    }
    var data = {
        "roleName": $(".role-name").val(),
        "sort": $(".role_sort").val(),
        "remark": $(".role_remark").val(),
        "ids": arr
    };

    $.ajax({
        type: "POST",
        url: '/role/saveRole',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
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
