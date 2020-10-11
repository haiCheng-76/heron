document.body.style.display = "block";
var $table = $('#user_table')
$(document).ready(function () {
    $table.bootstrapTable({
        url: '/userInfo/pageUserInfo',
        pagination: true,
        sortable: false,
        locale: 'zh-CN',
        virtualScroll: true,
        totalField: 'total',
        dataField: 'list',
        sidePagination: 'server',
        pageSize: 5,
        pageList: [5, 10, 20, 50, 100],
        search: false,
        idField: 'id',
        showRefresh: false,
        columns: [{radio: true}, {
            field: 'userName',
            title: '用户名'
        }, {
            field: 'account',
            title: '账户名'
        }, {
            field: 'isDelete',
            title: '是否删除',
            formatter: function statusFormatter(value, row) {
                return value ? '失效' : '有效';
            }
        }, {
            field: 'enable',
            title: '账户状态',
            formatter: function accountFormatter(value, row) {
                if (1 == value) {
                    return "冻结";
                }
                if (0 == value) {
                    return "启用";
                }
            }
        }, {
            field: 'createTime',
            title: '创建时间'
        }, {
            field: 'updateTime',
            title: '修改时间'
        }],
        onDblClickRow: function (row) {
            layuiIframe("查看", "/userInfo/getUserInfo?id=" + row.id);
        }
    });
});

$(".user_add").click(function () {
    layuiIframe("新增", "/userInfo/userAdd");
});

$(".user_edit").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    layuiIframe("编辑", "/userInfo/updateUserInfo?id=" + row[0].id);
});

$(".user_del").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    $.ajax({
        type: 'GET',
        url: '/userInfo/deleteUser?id=' + row[0].id,
        success: function (res) {
            if (1 == res.code) {
                window.location.reload();
                return;
            }
        }, error: function () {
            layer.msg('失败');
        }
    });
});
