document.body.style.display = "block";
const E = window.wangEditor;
const editor = new E('#editor');
editor.config.placeholder = '开始创作';
// 配置字体
editor.config.fontNames = [
    '黑体',
    '仿宋',
    '楷体',
    '标楷体',
    '华文仿宋',
    '华文楷体',
    '宋体',
    '微软雅黑',
    'Arial',
    'Tahoma',
    'Verdana',
    'Times New Roman',
    'Courier New',
    "JetBrains Mono"
];
editor.config.uploadFileName = 'file';
editor.config.uploadImgServer = '/resource/uploadResource';
editor.config.uploadImgMaxSize = 5 * 1024 * 1024;
editor.config.uploadImgMaxLength = 3;
editor.config.customAlert = function (s) {
};
editor.config.showLinkImg = false;
var index;
editor.config.uploadImgHooks = {
    before: function (xhr) {
        index = layer.load(2, {shade: false});
    },
    success: function (xhr) {
        layer.close(index);
    },
    fail: function (xhr, editor, resData) {
        layer.msg(resData.message);
        layer.close(index);
    },
    error: function (xhr, editor, resData) {
        layer.close(index);
    },
    timeout: function (xhr) {
        layer.close(index);
        layer.msg("上传超时！");
    }
};
editor.create();


$(".publish").click(function () {
    var menuData = {
        "title": $(".title").val(),
        "content": editor.txt.html()

    };
    $.ajax({
        type: "POST",
        url: '/article/saveArticle',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(menuData),
        success: function (res) {
            if (res.code == 1) {
                layer.msg("成功");
                window.location.href='/article/articleListPage?menuName=文章列表'

            } else {
                layer.msg(res.message);
            }
        },
        error: function (res) {

        }
    });
});
