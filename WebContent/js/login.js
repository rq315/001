//回调方法
$(document).ready(function () {
    localStorage.removeItem("user");
    

    $(".loginButton").click(function () {
        var user_name = $(".user_name").val();
        var user_student_id = $(".user_student_id").val();
        var param = "user_name=" + user_name + "&user_student_id=" + user_student_id;
        $.ajax({
            url: "http://localhost:8080/Community/login",
            data: param,
            type: "POST",//post安全 get不安全
            //服务器响应前端  response就是响应的消息
            success: function (response) {
                //response 类型是String  --》对象   
                var obj = JSON.parse(response);
                //判断用户登录成功
                if (obj.flag == "success") {
                    var user = obj.data;
                    //缓存用户信息  js--对象  浏览器不用对象--》String
                    localStorage.setItem("user", JSON.stringify(user));
                    jump(user);
                }else {
                    $(".alertInfo").text(obj.data);
                }
            }
        });


    });

})

function jump(user) {
    if (user.user_role == 1) {
        //员工
        location.href = "searchCI.html";
    } else {
        //管理员
        location.href = "enterCI.html";
    }
}