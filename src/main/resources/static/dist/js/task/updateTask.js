document.body.style.display = "block";
var taskId = '[[${task.id}]]';
$(".formCommit").click(function () {
    var data = {
        "id": taskId,
        "taskName": $(".task-name").val(),
        "jobGroup": $(".task-group").val(),
        "description": $(".task-description").val(),
        "cronExpression": $(".cron-expression").val(),
        "beanClass": $(".class-name").val(),
        "jobStatus": $("input[name='taskStatus']:checked").val(),
    };
    $.ajax({
        type: "POST",
        url: "/task/updateTask",
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: function (res) {
            if (1 == res.code) {
                window.parent.location.reload();
                return;
            }
            layer.msg('失败');
        },
        error: function () {
            layer.msg('失败');
        }
    });
});
