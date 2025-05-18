// 分页状态
let pagination = {
    currentPage: 1,
    pageSize: 10,
    totalPages: 1,
    totalItems: 0
};

$(document).ready(function() {
    // 初始化分页控件
    initPagination();

    // 加载第一页数据
    loadActivityData();

    console.log("文档加载完成"); // 确认jQuery已加载
    
    $(".searchBtn").on('click', function() {
        console.log("搜索按钮被点击了"); // 确认点击事件触发
        pagination.currentPage = 1;
        // performSearch();
        loadActivityData();
    });

    // 分页大小变化事件
    $("#pageSize").change(function() {
        pagination.pageSize = parseInt($(this).val());
        pagination.currentPage = 1;
        loadActivityData();
    });
    
    // 分页按钮事件
    $("#firstPage").click(() => goToPage(1));
    $("#prevPage").click(() => goToPage(pagination.currentPage - 1));
    $("#nextPage").click(() => goToPage(pagination.currentPage + 1));
    $("#lastPage").click(() => goToPage(pagination.totalPages));
});

function loadActivityData() {
    const activityName = $("#activity_name").val().trim();
    const activityType = $("#activity_type").val().trim();
    
    $.ajax({
        url: 'http://localhost:8080/Community/searchAI',
        type: "POST",
        data: {
            activity_name: activityName,
            activity_type: activityType,
            page: pagination.currentPage,
            size: pagination.pageSize
        },
        success: function(response) {
            try {
                const result = typeof response === 'string' ? JSON.parse(response) : response;
                
                if (result.flag === "success") {
                    // 更新分页信息
                    pagination.currentPage = result.data.currentPage;
                    pagination.totalPages = result.data.totalPages;
                    pagination.totalItems = result.data.totalItems;
                    
                    // 渲染表格和分页控件
                    renderActivityTable(result.data.items);
                    updatePaginationControls();
                    updatePageInfo();
                } else {
                    showError(result.message || "搜索失败");
                }
            } catch (e) {
                showError("数据解析错误");
                console.error(e);
            }
        },
        error: function(xhr) {
            showError("请求失败: " + xhr.statusText);
        }
    });
}

function goToPage(page) {
    if (page < 1 || page > pagination.totalPages || page === pagination.currentPage) return;
    
    pagination.currentPage = page;
    loadActivityData();
}

function initPagination() {
    // 初始化分页大小选择器
    $("#pageSize").val(pagination.pageSize);
    updatePaginationControls();
}

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
    
    $("#pageNumbers").html(pageNumbersHtml);
    
    // 绑定页码点击事件
    $(".page-number").click(function() {
        const page = parseInt($(this).data("page"));
        goToPage(page);
    });
}

function updatePageInfo() {
    const start = (pagination.currentPage - 1) * pagination.pageSize + 1;
    const end = Math.min(pagination.currentPage * pagination.pageSize, pagination.totalItems);
    $("#pageInfo").text(`显示 ${start}-${end} 条，共 ${pagination.totalItems} 条`);
}

window.renderActivityTable = function(activity){
    // 清空表格
    $('.table1 tbody').empty();
    
    // 检查数据是否存在
    if (!activity || activity.length === 0) {
        $('.table1 tbody').append(
            '<tr><td colspan="7" class="no-data">未找到匹配的活动</td></tr>'
        );
        return;
    }
    // console.log(members)

    // 遍历成员数据
    activity.forEach(function(activity) {
        
        const format = val => val === null || val === undefined || val === '' ? '-' : val;
        const formatDate = dateStr => {
            if (!dateStr) return '-';
            try {
                const date = new Date(dateStr);
                return isNaN(date.getTime()) ? '-' : date.toLocaleDateString();
            } catch (e) {
                return '-';
            }
        };

        // 创建表格行
        var trHtml = '<tr>' +
                    '<td>' + format(activity.activity_id) + '</td>' +
                    '<td>' + format(activity.activity_name) + '</td>' +
                    '<td>' + format(activity.activity_type) + '</td>' +
                    '<td>' + formatDate(activity.activity_date) + '</td>' +
                    '<td>' + format(activity.activity_location) + '</td>' +
                    '<td>' + format(activity.expected_activity_number) + '</td>' +
                    '<td>' + format(activity.activity_description) + '</td>' +
                    '<td>' + format(activity.community_name) + '</td>' +
                    '</tr>';
                $('.table1 tbody').append(trHtml);
                $(".table1 tbody tr").addClass("tdcolor");
                
    });
}

function showError(message) {
    $('.table1 tbody').html(`<tr><td colspan="8" class="error">${message}</td></tr>`);
    $("#pageInfo").text("显示 0-0 条，共 0 条");
    pagination.totalPages = 1;
    updatePaginationControls();
}