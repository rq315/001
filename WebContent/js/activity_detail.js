$(document).ready(function() {
    // 从URL获取活动ID
    const urlParams = new URLSearchParams(window.location.search);
    const activityId = urlParams.get('activity_id');
    
    // 加载活动数据
    $.ajax({
        url: `http://localhost:8080/Community/activityDetail?activity_id=${activityId}`,
        type: "GET",
        dataType: "json",
        success: function(response) {
            console.log(response);
            console.log(response.flag);
            
            if(response.flag === "success") {
                const activity = response.data;
                // 填充数据
                $('#activity-id').text(activity.activity_id || '--');
                $('#activity-name').text(activity.activity_name || '--');
                $('#activity-type').text(activity.activity_type || '--');
                $('#activity-date').text(new Date(activity.activity_date).toLocaleDateString() || '--');
                $('#activity-location').text(activity.activity_location || '--');
                $('#expected-number').text(activity.expected_activity_number || '--');
                $('#activity-description').text(activity.activity_description || '--');
                $('#community-name').text(activity.community_name || '--');
                $('#activity-summary').val(activity.activity_summary || '');
            }else {
                alert('加载活动详情失败');
                // window.location.href = 'activity.html';
            }
        }
    });
    
    // 保存总结
    $('#save-summary').on('click', function() {
        const summary = $('#activity-summary').val().trim();
        console.log('准备保存的数据:', {  // 调试日志
            activity_id: activityId,
            summary: summary
        });
        
        $.ajax({
            url: 'http://localhost:8080/Community/saveSummary',
            type: "POST",
            data: {
                activity_id: activityId,
                summary: summary
            },
            dataType: "json",
            success: function(response) {
                console.log('保存响应:', response);  // 调试日志
                alert(response.flag === "success" ? '保存成功' : '保存失败: ' + response.data);
            }
        });
    });


});