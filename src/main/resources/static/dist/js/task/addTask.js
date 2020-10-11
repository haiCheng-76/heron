document.body.style.display = "block";
$(".formCommit").click(function () {
    var data = {
        "taskName": $(".task-name").val(),
        "description": $(".task-description").val(),
        "cronExpression": $(".cron-expression").val(),
        "beanClass": $(".class-name").val(),
        "jobStatus": $("input[name='taskStatus']:checked").val(),
        "jobGroup": $(".task-group").val()
    };

    $.ajax({
        type: "POST",
        url: '/task/addTask',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (res) {
            if (res.code == 1) {
                window.parent.location.reload();
                return;
            } else {
                layer.msg(res.message);
            }
        },
        error: function (res) {
            layer.msg("失败");
        }
    });
});
