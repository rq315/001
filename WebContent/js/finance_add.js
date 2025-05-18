$(document).ready(function(){
    // 加载活动选项
    loadActivities();

    // 提交表单
    $('#financeForm').submit(function(e) {
        e.preventDefault();
        addFinanceRecord();
    });

})
// 加载活动选项
function loadActivities() {
    console.log("正在加载活动选项...");
    
    $.ajax({
        url: 'http://localhost:8080/Community/searchAI',
        type: "POST",
        dataType: "json",
        success: function(response) {
            console.log("活动API响应:", response);
            
            // 检查响应是否有效
            if(!response) {
                console.error("无效的响应");
                $('.activity-id').html('<option value="">-- 加载失败 --</option>');
                return;
            }
            
            // 检查响应状态
            if(response.flag !== "success") {
                console.error("响应状态异常:", response.flag, response.data);
                $('.activity-id').html(`<option value="">-- ${response.data || '加载失败'} --</option>`);
                return;
            }
            
            // 检查数据是否存在
            if(!response.data.items || !Array.isArray(response.data.items)) {
                console.error("无效的活动数据");
                $('.activity-id').html('<option value="">-- 无可用活动 --</option>');
                return;
            }
            
            // 构建选项
            var options = '<option value="">-- 无关联活动 --</option>';
            var validActivities = 0;
            
            response.data.items.forEach(function(activity) {
                if(activity && activity.activity_id && activity.activity_name) {
                    options += `<option value="${activity.activity_id}">${activity.activity_name}</option>`;
                    validActivities++;
                }
            });
            
            $('.activity-id').html(options);
            console.log(`成功加载 ${validActivities}/${response.data.items.length} 个活动选项`);
        },
        error: function(xhr, status, error) {
            console.error("加载活动失败:", status, error);
            $('.activity-id').html('<option value="">-- 加载失败，请刷新 --</option>');
            
            // 显示错误提示
            alert("加载活动列表失败，请检查网络连接后刷新页面");
        }
    });
}


//记录新收支
function addFinanceRecord() {
    // 获取表单值
    var fundAmount = parseFloat($('.fund-amount').val());
    var fundType = $('.fund-type').val();

    console.log(fundType);

    // 根据收支类型验证金额
    if (fundType === "支出" && fundAmount >= 0) {
        alert("支出金额必须小于0,请输入负数");
        return;
    }
    
    if (fundType === "收入" && fundAmount <= 0) {
        alert("收入金额必须大于0,请输入正数");
        return;
    }

    var param = {
        fund_amount: fundAmount,
        fund_type: fundType,
        fund_record_date: $('.fund-date').val(),
        fund_description: $('.fund-desc').val(),
        associated_activity_number: $('.activity-id').val() || 0
    };

    $.ajax({
        url: 'http://localhost:8080/Community/addFinance',
        type: "POST",
        data: param,
        dataType:"json",
        success: function(response) {
            console.log("得到的响应："+response);
            console.log(response.flag);

            var user = param;
            console.log(user);

            localStorage.setItem("user", JSON.stringify(user));

            if(response.flag=="success"){
                alert('记录添加成功');

                // totalSum += fundAmount; // 更新总费用
                // $(".money").text(totalSum.toFixed(2));

                // loadFinances(); // 刷新列表
                $('#financeForm')[0].reset(); // 清空表单
            
            }else{
                alert("记录添加失败"||response.data);
            }   
        },
        error: function(xhr) {
            alert('添加失败，请重试！');
            console.error("更新失败:", xhr.responseText);
        }
    });
}
