document.body.style.display = "block";
var $table = $('#user_table')
$(document).ready(function () {
    $table.bootstrapTable({
        url: '/task/pageTask',
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
        columns: [
            {
                radio: true
            },
            {
                field: 'jobName',
                title: '任务名称'
            }, {
                field: 'cronExpression',
                title: '表达式'
            }, {
                field: 'jobStatus',
                title: '任务状态',
                formatter: function statusFormatter(value, row) {
                    if (0 == value) {
                        return "运行";
                    }
                    if (1 == value) {
                        return "停止";
                    }
                    if (2 == value) {
                        return "暂停";
                    }
                }
            }, {
                field: 'createTime',
                title: '创建时间'
            }],
        onDblClickRow: function (row) {
            layuiIframe("查看", "/task/taskDetail?id=" + row.id);
        }
    });
});

// 新增
$(".task_add").click(function () {
    layuiIframe("新增", "/task/addTask");
});
// 编辑
$(".task_edit").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    layuiIframe("编辑", "/task/updateTask?id=" + row[0].id);
});

// 删除
$(".task_del").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    $.ajax({
        type: 'GET',
        url: '/task/delTask?id=' + row[0].id,
        success: function (res) {
            if (1 == res.code) {
                window.location.reload();
                return;
            }
            layer.msg('失败');
        }, error: function () {
            layer.msg('失败');
        }
    });
});
// 暂停
$(".task_pause").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    $.ajax({
        type: 'GET',
        url: '/task/pauseTask?id=' + row[0].id,
        success: function (res) {
            if (1 == res.code) {
                window.location.reload();
                return;
            }
            layer.msg('失败');
        }, error: function () {
            layer.msg('失败');
        }
    });
});
//  恢复
$(".task_resume").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }

    $.ajax({
        type: 'GET',
        url: '/task/resumeTask?id=' + row[0].id,
        success: function (res) {
            if (1 == res.code) {
                window.location.reload();
                return;
            }
            layer.msg(res.message);
        }, error: function () {
            layer.msg('失败');
        }
    });
});
