document.body.style.display = "block";
$(".formCommit").click(function () {
    var pwd1 = $(".pwd1").val();
    var pwd2 = $(".pwd2").val();
    if (pwd1 != null && pwd2 != null) {
        if (pwd1 != pwd2) {
            layer.msg('两次输入的密码不同');
            return;
        }
    }
    var data = {
        "userName": $(".username").val(),
        "account": $(".account").val(),
        "password": pwd1,
        "mail": $(".mail").val(),
        "roleId": $("input[name='role']:checked").val()
    };
    $.ajax({
        type: 'POST',
        url: '/userInfo/addUserInfo',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: function (res) {
            if (1 == res.code) {
                window.parent.location.reload();
            } else {
                layer.msg('添加失败');
            }
        }, error: function () {
            layer.msg('添加失败');
        }
    });
});
