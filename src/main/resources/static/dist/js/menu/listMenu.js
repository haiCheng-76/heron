document.body.style.display = "block";
var $table = $('#user_table')
$(function () {
    $table.bootstrapTable({
        url: '/menu/getTreeTable',
        idField: 'id',
        locale: 'zh-CN',
        treeEnable: true,
        treeShowField: 'title',
        parentIdField: 'pid',
        columns: [{radio: true},
            {
                field: 'title',
                title: '名称'
            },
            {
                field: 'type',
                title: '类型',
                formatter: function formatter(value, row) {
                    if (value == 'D') {
                        return '<a class="btn btn-primary btn-pill btn-sm" style="cursor: default;color: #ffffff">目录</a>';
                    }
                    if (value == 'M') {
                        return '<a class="btn btn-success btn-pill btn-sm" style="cursor: default;color: #ffffff">菜单</a>';
                    }
                    if (value == 'B') {
                        return '<a class="btn btn-info btn-pill btn-sm" style="cursor: default;color: #ffffff">按钮</a>';
                    }
                }
            },
            {
                field: 'url',
                title: '地址',
            },
            {
                field: 'permission',
                title: '权限'
            },
            {
                field: 'isDelete',
                title: '状态',
                formatter: function formatter(value, row) {
                    if (1 == value) {
                        return "冻结";
                    }
                    if (0 == value) {
                        return "启用";
                    }
                }
            }
        ],
        onPostBody: function () {
            var columns = $table.bootstrapTable('getOptions').columns

            if (columns && columns[0][1].visible) {
                $table.treegrid({
                    treeColumn: 1,
                    onChange: function () {
                        $table.bootstrapTable('resetView')
                    }
                })
            }
        }
    });

})

function edit(id, pid, type) {
    layuiIframe("编辑", "/menu/menuUpdate?parentId=" + pid + "&id=" + id + "&type=" + type);
}

$(".menu_add").click(function () {
    layuiIframe("新增", "/menu/addMenu");
});

$(".menu_edit").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    layuiIframe("编辑", "/menu/menuUpdate?parentId=" + row[0].pid + "&id=" + row[0].id + "&type=" + row[0].type);
});

$(".menu_del").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    if (delMenu(row[0].id)) {
        layer.msg("请先删除子菜单");
        return;
    }
    ;
    window.location.reload();
});
