var roleId = '[[${data.id}]]';
document.body.style.display = "block";
var setting = {
    check: {
        enable: true,
        chkStyle: "checkbox",
        chkboxType:  { "Y" : "p", "N" : "s" }
    }, data: {
        simpleData: {
            enable: true
        }
    }
};
var tree;
$(document).ready(function () {
    var permissionIds = [[${data.permissionIds}]];

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
    if (1 == roleId) {
        tree.checkAllNodes(true);
        return;
    }

    var nodes = tree.transformToArray(tree.getNodes());
    for (var i = 0; i < nodes.length; i++) {
        for (var j = 0; j < permissionIds.length; j++) {
            if (nodes[i].id == permissionIds[j]) {
                console.log(nodes[i].id)
                tree.checkNode(nodes[i], true, true);
            }
        }
    }

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
    console.log(arr)
    var data = {
        "id": roleId,
        "roleName": $(".role-name").val(),
        "sort": $(".role_sort").val(),
        "remark": $(".role_remark").val(),
        "ids": arr
    };

    $.ajax({
        type: "POST",
        url: '/role/updateRole',
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
            layer.msg("失败");
        }
    });
});
