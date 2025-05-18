$(document).ready(function(){
    //加载社团选项
    loadClub();

    // 提交表单
    $('#memberForm').submit(function(e) {
        e.preventDefault();// 阻止默认提交
        console.log("添加按钮被点击了"); // 确认点击事件触发
        addMember();
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
            // 确保选择正确的选择器 - 修改这里
            // $('select[name="user_belong_community_number"]').html(options);
            $('.community_id').html(options);
        },
        error: function(xhr) {
            console.error("加载社团列表失败:", xhr.responseText);
        }
    });
}


//添加新成员后刷新列表
function addMember(){
    var param = {
        user_name: $(".user_SelectName").val().trim(),
        user_join_date: $(".user_join_date").val().trim(),
        user_phone: $(".user_phone").val().trim(),
        user_role: $(".user_SelectRole").val(),
        user_belong_community_number: $("select[name='user_belong_community_number']").val() || ""
    };
    console.log("提交的参数:",param);

        // 确保值不为undefined
        if(typeof user_belong_community_number === "undefined") {
            user_belong_community_number = "";
        }
        
    // 显示加载状态
    $('.submit-btn').prop('disabled', true).text('提交中...');

    $.ajax({
        url: 'http://localhost:8080/Community/addMember',
        data: param,
        type: "POST",
        dataType:"json",
        success: function(response) {
            console.log("得到的响应："+response);
            console.log(response.flag);

            // 调试输出
            console.log("关联社团选择值:", $("select[name='user_belong_community_number']").val());
            console.log("关联社团选择元素:", $("select[name='user_belong_community_number']"));


            var user = param;
            console.log(user);

            localStorage.setItem("user", JSON.stringify(user));

            if(response.flag=="success"){
                alert('添加成功！学号: ' + (response.data.studentId || ''));
                // searchMembers("0","");// 刷新列表
                // loadMembers(); // 刷新列表
                $('#memberForm')[0].reset(); // 清空表单

            }else{
                alert("添加失败！");
            }

            
        },
        error: function() {
            alert('添加失败，请重试！');
            console.error("添加失败:", xhr.responseText);
        },
        complete: function() {
            $('.submit-btn').prop('disabled', false).text('添加');
        }
    });
}