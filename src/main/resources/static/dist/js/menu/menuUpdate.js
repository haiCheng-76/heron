document.body.style.display = "block";
var menuId = '[[${menu.id}]]';
var menuPid = '[[${pid}]]';
var menuType = '[[${type}]]';
if ('D' == menuType) {
    $("#menu_url").hide();
    $("#menu_per").hide();
}
if ('B' == menuType) {
    $("#menu_url").hide();
}
var setting = {
    check: {
        enable: true,
        chkStyle: "radio",
        radioType: "all"
    },
    data: {
        simpleData: {
            enable: true
        }
    }
};

var ztree;
$(document).ready(function () {
    var treeData = null;
    $.ajax({
        type: "POST",
        url: '/menu/getZtreeMenu',
        data: JSON.stringify(["D", "M"]),
        async: false,
        dataType: 'json',
        contentType: 'application/json',
        success: function (res) {
            treeData = res;
        }
    });
    ztree = $.fn.zTree.init($("#menuTree"), setting, treeData);
    ztree.expandAll(true);
    var nodes = ztree.transformToArray(ztree.getNodes());
    if (nodes.length > 0) {
        for (var i = 0; i < nodes.length; i++) {
            if (menuPid == nodes[i].id) {
                nodes[i].checked = true;
                ztree.updateNode(nodes[i]);
                return;
            }
        }
    }
});


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
