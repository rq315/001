$(document).ready(function(){
    $(".logout").click(function(){
        //清空缓存信息
        localStorage.removeItem("user");
        //返回登录
        location.href="login.html";
    })

    $(".returnA").click(function(){
        //返回登录
        location.href="activity.html";
    })

    $(".returnF").click(function(){
        //返回登录
        location.href="finance.html";
    })

    $(".returnM").click(function(){
        //返回登录
        location.href="member.html";
    })
    
})