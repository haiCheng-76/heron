document.body.style.display = "block";
var treeChecked;
var url = "/menu/getZtreeMenu";
var setting = {
    check: {
        enable: true,
        chkStyle: "radio",
        radioType: "all"
    }, data: {
        simpleData: {
            enable: true
        }
    }
};
var tree;
$(document).ready(function () {
    var data = ["D", "M"];
    var treeData = null;
    $.ajax({
        type: "POST",
        url: url,
        async: false,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (res) {
            treeData = res;
        }
    });
    tree = $.fn.zTree.init($("#treeDemo"), setting, treeData);
    tree.expandAll(true);

});

$('input[type=radio][name=type]').change(function () {
    var val = this.value;
    if (val == 'D') {
        $(".menuUrl").hide();
        $(".menuPermission").hide();
    }
    if (val == 'M') {
        $(".menuUrl").show();
        $(".menuPermission").hide();
        $(".menuPermission").show();
    }
    if (val == 'B') {
        $(".menuUrl").hide();
        $(".menuPermission").show();
    }
});

$(".formCommit").click(function () {
    treeChecked = tree.getCheckedNodes();
    if (treeChecked.length == 0) {
        layer.msg("请选择归属关系!");
        return;
    }
    var menuData = {
        "parentId": treeChecked[0].id,
        "menuName": $(".menuName").val(),
        "url": $(".url").val(),
        "isDelete": $("input[name='menuStatus']:checked").val(),
        "sort": $(".menuSort").val(),
        "type": $("input[name='type']:checked").val(),
        "permission": $(".menuPer").val()

    };
    $.ajax({
        type: "POST",
        url: '/menu/saveMenu',
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
