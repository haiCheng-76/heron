document.body.style.display = "block";
var userid = '[[${user.id}]]';
// 头像上传
$('.avatarUpload').click(function () {
    $('.avatarChoose').click();
});
// 保存
$('.formCommit').click(function () {
    var data = {
        'id': userid,
        'userName': $(".username").val(),
        'account': $(".account").val(),
        'password': $(".pwd").val(),
        'isDelete': $("input[name='userDel']:checked").val(),
        'enable': $("input[name='userStatus']:checked").val(),
        // 'avatar': '',
        'eMail': $(".mail").val()
    }
    alert(JSON.stringify(data))
    $.ajax({
        type: 'POST',
        url: '/userInfo/updateUer',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: function (res) {
            if (1 == res.code) {
                window.parent.location.reload();
            }
        }, error: function () {
            layer.msg('失败');
        }
    });
});
