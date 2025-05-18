$(document).ready(function() {

    // 提交表单
    $('#enterCIForm').submit(function(e) {
        e.preventDefault();// 阻止默认提交
        console.log("提交按钮被点击了"); // 确认点击事件触发
        enterCI();
    });
});

function enterCI(){
    var param = {
        community_name: $(".community_name").val(),
        community_type: $(".community_type").val(),
        founder_name: $(".founder_name").val(),
        founder_id: $(".founder_id").val(),
        create_date: $(".create_date").val(),
        community_introduction: $(".community_introduction").val(),
        community_status: $("community_status").val()
    };
    
    $.ajax({
        url: 'http://localhost:8080/Community/enterCI',
        data: param,
        type: "POST",
        success: function(response) {

            var obj = JSON.parse(response);
            console.log("得到的响应："+obj);
            console.log(obj.flag);
            console.log(param);

            var user = obj.data;
            localStorage.setItem("user", JSON.stringify(user));
            if(obj.flag=="success"){
                alert('社团信息录入成功！');
                window.location.href = 'enterCI.html';
            }else{
                alert("录入失败！");
            }
         
        },
        error: function() {
            alert('提交失败，请重试！');
        }
    });

}
    