document.body.style.display = "block";
var $table = $('#role_table')
$(document).ready(function () {
    $table.bootstrapTable({
        url: '/role/pageRole',
        pagination: true,
        sortable: false,
        locale: 'zh-CN',
        virtualScroll: true,
        totalField: 'total',
        dataField: 'list',
        sidePagination: 'server',
        search: false,
        pageSize: 5,
        pageList: [5, 10, 20, 50, 100],
        buttonsAlign: 'left',
        idField: 'id',
        showRefresh: false,
        // height: 500,
        columns: [{radio: true}, {
            field: 'roleName',
            title: '角色名'
        }, {
            field: 'description',
            title: '描述'
        }, {
            field: 'deleted',
            title: '状态',
            formatter: function accountFormatter(value, row) {
                return value ? '失效' : '有效';
            }
        }, {
            field: 'createTime',
            title: '创建时间'
        }, {
            field: 'updateTime',
            title: '修改时间'
        }]
    });
});


$(".role_add").click(function () {
    layuiIframe("新增角色", "/role/addRole");
});

$(".role_edit").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    layuiIframe("查看", "/role/updateRole?id=" + row[0].id);
});

$(".role_del").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    $.ajax({
        type: 'GET',
        url: '/role/delRole?id=' + row[0].id,
        success: function (res) {
            if (1 == res.code) {
                window.location.reload();
            }
        }, error: function () {
            alert('失败')
        }
    });
});

function edit(id) {
    layuiIframe("查看", "/role/updateRole?id=" + id);
}
