$(document).ready(function(){
    //加载社团选项
    loadClub();

    // 提交表单
    $('#activityForm').submit(function(e) {
        e.preventDefault();// 阻止默认提交
        console.log("发布按钮被点击了"); // 确认点击事件触发
        performSearch();
    });
    
})
// 加载社团选项
function loadClub() {
    $.ajax({
        url: 'http://localhost:8080/Community/getCIList',
        type: "POST",
        dataType: "json", // 添加数据类型
        success: function(response) {
            // 直接使用response，因为设置了dataType:json
            console.log("社团列表:", response);
            
            var options = '<option value="">-- 无关联社团 --</option>';
            if(response.data && response.data.length > 0) {
                response.data.forEach(function(club) {
                    options += `<option value="${club.community_id}">${club.community_name}</option>`;
                });
            }
            $('.community_id').html(options);
        },
        error: function(xhr) {
            console.error("加载社团列表失败:", xhr.responseText);
        }
    });
}

//活动发布
function performSearch() {
    
    var param = {
        activity_name: $(".activity_name").val(),
        activity_type: $(".activity_type").val(),
        activity_date: $(".activity_date").val(),
        activity_location: $(".activity_location").val(),
        expected_activity_number: $(".expected_activity_number").val(),
        activity_description: $(".activity_description").val(),
        assocoated_community_number: $("select[name='assocoated_community_number']").val() || ""
    };

        // 确保值不为undefined
        if(typeof assocoated_community_number === "undefined") {
            assocoated_community_number = "";
        }
    
    $.ajax({
        url: 'http://localhost:8080/Community/publishA',
        data: param,
        type: "POST",
        dataType:"json",
        success: function(response) {
            console.log("得到的响应："+response);
            console.log(response.flag);

            var user = param;
            console.log(user);

            localStorage.setItem("user", JSON.stringify(user));

            if(response.flag=="success"){
                alert('活动发布成功！');
                // loadActivity(); // 刷新列表
                $('#activityForm')[0].reset(); // 清空表单
            }else{
                alert("活动发布失败！");
            }

            
        },
        error: function() {
            alert('发布失败，请重试！');
            console.error("更新失败:", xhr.responseText);
        }
    });
}
