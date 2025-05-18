// 分页状态对象
let pagination = {
    currentPage: 1,
    pageSize: 10,
    totalPages: 1,
    totalItems: 0
};

$(document).ready(function() {

    // 分页按钮事件
    $('#firstPage').click(() => goToPage(1));
    $('#prevPage').click(() => goToPage(pagination.currentPage - 1));
    $('#nextPage').click(() => goToPage(pagination.currentPage + 1));
    $('#lastPage').click(() => goToPage(pagination.totalPages));
    
    // 每页显示数量变化事件
    $('#pageSize').change(function() {
        pagination.pageSize = parseInt($(this).val());
        pagination.currentPage = 1;
        loadActivity();
    });

    // 初始加载活动数据
    loadActivity();

    
    //发布活动按钮
    $(".btn_publish").click(function(){
        console.log("发布按钮被点击");
        location.href="activity_add.html";
    })
});

// 加载活动数据
function loadActivity() {
    $('.table tbody').html('<tr><td colspan="10">加载中...</td></tr>');
    
    $.ajax({
        url: 'http://localhost:8080/Community/searchAI',
        type: "GET",
        data: {
            page: pagination.currentPage,
            size: pagination.pageSize
        },
        dataType:"json",
        success: function(response) {
            try {
                console.log("完整响应:", response);
                console.log(response.flag);
                
                // 统一响应处理
                if(response.flag === "success") {
                    const data = response.data;
                    
                    // 更新分页信息
                    pagination.currentPage = data.currentPage || 1;
                    pagination.totalPages = data.totalPages || 1;
                    pagination.totalItems = data.totalItems || 0;
                    
                    // 获取数据列表 - 兼容不同字段名
                    const activities = data.list || data.items || data.data || [];
                    
                    console.log("分页信息:", pagination);
                    console.log("活动数据:", activities);
                    
                    // 渲染表格
                    if (Array.isArray(activities) && activities.length > 0) {
                        renderActivityTable(activities);
                    } else {
                        showNoDataMessage();
                    }
                    
                    // 更新分页控件
                    updatePaginationControls();
                    updatePageInfo();
                    
                } else {
                    showErrorMessage(response.message || "请求失败...请刷新");
                }
            } catch (e) {
                console.error('解析错误:', e);
                showErrorMessage("数据解析错误...请刷新");
            }
        },
        error: function(xhr) {
            console.error('请求失败:', xhr.responseText);
            showErrorMessage("网络请求失败");
        }
    });
}

// 渲染活动表格
function renderActivityTable(activities) {
    $('.table tbody').empty();

    console.log(activities);
    
    // 增强数据校验
    if (!Array.isArray(activities)) {
        console.error("数据不是数组:", activities);
        showNoDataMessage();
        return;
    }

    if (activities.length === 0) {
        console.log("收到空数据数组");
        showNoDataMessage();
        return;
    }

    // 遍历活动数据
    activities.forEach(function(activity) {
        // 确保activity是对象
        if(typeof activity !== 'object' || activity === null) {
            console.error("无效的活动条目:", activity);
            return;
        }
            var trHtml = '<tr>' +
                '<td>' + (activity.activity_id  || '-') + '</td>' +
                '<td>' + (activity.activity_name || '-')  + '</td>' +
                '<td>' + (activity.activity_type  || '-') + '</td>' +
                '<td>' + (new Date(activity.activity_date).toLocaleDateString()  || '-') + '</td>' +
                '<td>' + (activity.activity_location  || '-') + '</td>' +
                '<td>' + (activity.expected_activity_number || '-')  + '</td>' +
                '<td>' + (activity.activity_description || '-') + '</td>' +
                '<td>' + (activity.community_name || '请选择关联社团') + '</td>' +
                '<td><button class="signup-btn" data-activity-id="' + activity.activity_id + '">报名统计</button></td>' +
                '<td><button class="summary-btn" data-activity-id="' + activity.activity_id + '">活动总结</button></td>' +
                '</tr>';
            $('.table tbody').append(trHtml);
        });
        $(".table tbody tr").addClass("tdcolor");
        
        //绑定点击按钮
        bindButtonEvents();
}

// 更新分页控件
function updatePaginationControls() {
    // 更新按钮状态
    $("#firstPage").prop("disabled", pagination.currentPage === 1);
    $("#prevPage").prop("disabled", pagination.currentPage === 1);
    $("#nextPage").prop("disabled", pagination.currentPage === pagination.totalPages);
    $("#lastPage").prop("disabled", pagination.currentPage === pagination.totalPages);
 
    // 更新页码显示
    let pageNumbersHtml = "";
    const startPage = Math.max(1, pagination.currentPage - 2);
    const endPage = Math.min(pagination.totalPages, pagination.currentPage + 2);
    
    for (let i = startPage; i <= endPage; i++) {
        const activeClass = i === pagination.currentPage ? 'active' : '';
        pageNumbersHtml += `<span class="page-number ${activeClass}" data-page="${i}">${i}</span>`;
    }
    
    $('#pageNumbers').html(pageNumbersHtml);

    // 绑定页码点击事件
    $(".page-number").click(function() {
        const page = parseInt($(this).data("page"));
        goToPage(page);
    });
}

// 更新分页信息
function updatePageInfo() {
    const start = (pagination.currentPage - 1) * pagination.pageSize + 1;
    const end = Math.min(pagination.currentPage * pagination.pageSize, pagination.totalItems);
    $('#pageInfo').text(`显示 ${start}-${end} 条，共 ${pagination.totalItems} 条`);
}

// 跳转到指定页
function goToPage(page) {
    if (page < 1 || page > pagination.totalPages || page === pagination.currentPage) return;
    
    pagination.currentPage = page;
    loadActivity();
}


// 绑定表格中按钮事件
function bindButtonEvents() {
    // 报名统计按钮
    $('.signup-btn').click(function() {
        // 获取当前活动的ID
        var activityId = $(this).data('activity-id');

        // 获取当前行的预计参与人数（第6列，索引从0开始）
        var currentNumber = $(this).closest('tr').find('td:eq(5)').text();
        
        var newNumber = prompt("请输入更新后的预计参与人数:", currentNumber);
        
        if (newNumber !== null) {
            // 验证输入是否为纯数字
            if (!/^\d+$/.test(newNumber)) {
                alert("请输入有效的数字");
                return;
            }
            updateExpectedNumber(activityId, newNumber);
        }
    });
    
    // 活动总结按钮
    $('.summary-btn').click(function() {
        var activityId = $(this).data('activity-id');
        window.location.href = `activity_detail.html?activity_id=${activityId}`;
    });
}



// 更新预计参与人数
function updateExpectedNumber(activityId, newNumber) {
    $.ajax({
        url: 'http://localhost:8080/Community/updateNum',
        type: "POST",
        data: {
            activity_id: activityId,
            expected_number: newNumber
        },
        //字符串自动解析为js对象
        dataType: "json",
        success: function(response) {
            if (response.flag === "success") {
                alert('更新成功！');
                window.location.href = 'activity.html';
                console.log(obj);
            } else {
                alert(response.data || '更新失败');
            }
        },
        error: function(xhr) {
            alert('更新失败，请重试！');
            console.error("更新失败:", xhr.responseText);
        }
    });
}

// 显示无数据信息
function showNoDataMessage() {
    $('.table tbody').html(
        '<tr><td colspan="10" class="no-data">暂无数据...请刷新</td></tr>'
    );
    pagination.totalItems = 0;
    pagination.totalPages = 1;
    updatePaginationControls();
    updatePageInfo();
}

// 显示错误信息
function showErrorMessage(message) {
    $('.table tbody').html(
        `<tr><td colspan="10" class="error">${message || '数据加载失败...请刷新'}</td></tr>`
    );
    $('#pageInfo').text("数据加载错误...请刷新");
    pagination.totalPages = 1;
    updatePaginationControls();
}