document.body.style.display = "block";
var $table = $('#user_table')
$(document).ready(function () {
    $table.bootstrapTable({
        url: '/article/pageArticle',
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
            }, {
                field: 'articleName',
                title: '标题'
            }, {
                field: 'createTime',
                title: '创建时间'
            }, {
                field: 'updateTime',
                title: '修改时间',
            }, {
                field: 'create_by',
                title: '创建人',
            }, {
                field: 'updateBy',
                title: '修改人'
            }],
        onDblClickRow: function (row) {
            window.location.href = '/article/articleDetail?articleName=' + row.articleName + '&id=' + row.id
        }
    });
});

$(".user_add").click(function () {
    layuiIframe("新增", "/userInfo/userAdd");
});

$(".article_edit").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    // layuiIframe("编辑", "/article/updateDetail?id=" + row[0].id);
    window.location.href = '/article/updateDetail?menuName=修改文章' + '&id=' + row[0].id
});

$(".article_del").click(function () {
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0) {
        layer.msg('请选择一条记录！');
        return;
    }
    $.ajax({
        type: 'GET',
        url: '/article/delArticle?id=' + row[0].id,
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
