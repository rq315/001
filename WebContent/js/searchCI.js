// 分页状态变量
let currentPage = 1;//当前页
let totalPages = 1;//总页数
let totalItems = 0;//总数
let pageSize = 10;//每页数

$(document).ready(function() {

    // 初始化分页
    initPagination();

    // 初始加载第一页数据
    searchClub("", "", currentPage, pageSize);

    console.log("文档加载完成"); // 确认jQuery已加载
    
    $(".searchBtn").on('click', function() {
        console.log("搜索按钮被点击了"); // 确认点击事件触发
        currentPage = 1; // 搜索时重置到第一页
        performSearch();
    });

    // 分页大小变化事件
    $("#pageSize").change(function() {
        pageSize = parseInt($(this).val());
        currentPage = 1;
        performSearch();
    });

    // 分页按钮事件
    $("#firstPage").click(function() { goToPage(1); });
    $("#prevPage").click(function() { goToPage(currentPage - 1); });
    $("#nextPage").click(function() { goToPage(currentPage + 1); });
    $("#lastPage").click(function() { goToPage(totalPages); });
});

function initPagination() {
    // 初始化分页控件状态
    updatePaginationControls();
}

// 绑定搜索按钮点击事件
function performSearch() {
        var community_name = $("#community_name").val().trim();  
        var community_type = $("#community_type").val().trim();  
        // 3. 打印最终参数
        console.log("【修正后参数】", {
            community_name,
            community_type,
            currentPage,
            pageSize
        });
        
        // 4. 发送请求
        searchClub(community_name, community_type, currentPage, pageSize);
    }



//搜索社团
function searchClub(community_name, community_type, page, size) {
    console.log("发送搜索请求参数:", {
        community_name: community_name,
        community_type: community_type,
        page: page,
        size: size
    });

    $.ajax({
        url: 'http://localhost:8080/Community/searchCI',
        type: "POST",
        data: { 
            community_name: community_name,
            community_type: community_type,
            page: page,
            size: size
        },
        success: function(response) {
            console.log("收到响应:", response);

            try {
                 // 兼容字符串和对象两种响应格式
                var result = typeof response === 'string' ? JSON.parse(response) : response;
                console.log("解析后数据:", result.flag);
                
                if (result.flag === "success") {
                    // 更新分页信息
                    currentPage = result.data.currentPage || 1;
                    totalPages = result.data.totalPages || 1;
                    totalItems = result.data.totalItems || 0;

                    // 渲染表格和分页控件
                    renderClubTable(result.data.items);
                    updatePaginationControls();
                    updatePageInfo();


                } else {
                    console.error("【前端】错误:", response.message);
                    showNoDataMessage(result.message || "搜索失败");
                }
            } catch (e) {
                console.error('解析错误:', e);
                showErrorMessage();
            }
        },
        error: function(xhr, status, error) {
            console.error('请求失败:', error);
            showErrorMessage();
        }
    });
}

function showNoDataMessage() {
    $('.table1 tbody').html(
        '<tr><td colspan="7" class="no-data">未找到匹配的活动</td></tr>'
    );
}

function showErrorMessage() {
    $('.table1 tbody').html(
        '<tr><td colspan="7" class="error">数据加载失败</td></tr>'
    );
}

function updatePaginationControls() {
    // 更新按钮状态
    $("#firstPage").prop("disabled", currentPage === 1);
    $("#prevPage").prop("disabled", currentPage === 1);
    $("#nextPage").prop("disabled", currentPage === totalPages);
    $("#lastPage").prop("disabled", currentPage === totalPages);

    // 更新页码显示 2：左右显示的页码偏移量
    let pageNumbersHtml = "";
    const startPage = Math.max(1, currentPage - 2);
    const endPage = Math.min(totalPages, currentPage + 2);
    
    for (let i = startPage; i <= endPage; i++) {
        pageNumbersHtml += `<span class="page-number ${i === currentPage ? 'active' : ''}" data-page="${i}">${i}</span>`;
    }
    
    $("#pageNumbers").html(pageNumbersHtml);
    
    // 绑定页码点击事件
    $(".page-number").click(function() {
        const page = parseInt($(this).data("page"));
        goToPage(page);
    });
}

function goToPage(page) {
    if (page < 1 || page > totalPages || page === currentPage) return;
    currentPage = page;
    performSearch();
}

function updatePageInfo() {
    const startItem = (currentPage - 1) * pageSize + 1;
    const endItem = Math.min(currentPage * pageSize, totalItems);
    $("#pageInfo").text(`显示 ${startItem}-${endItem} 条，共 ${totalItems} 条`);
}

//渲染数据表格
window.renderClubTable = function(club){
    // 清空表格
    $('.table1 tbody').empty();
    
    // 检查数据是否存在
    if (!club || club.length === 0) {
        $('.table1 tbody').append(
            '<tr><td colspan="8" class="no-data">未找到匹配的活动</td></tr>'
        );
        return;
    }
    // 遍历成员数据
    club.forEach(function(club) {    
        // 处理空值显示
        const formatValue = (value) => value === null || value === undefined || value === '' ? '-' : value;
        const formatDate = (dateStr) => {
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
                    '<td>' + formatValue(club.community_id) + '</td>' +
                    '<td>' + formatValue(club.community_name) + '</td>' +
                    '<td>' + formatValue(club.community_type) + '</td>' +
                    '<td>' + formatValue(club.founder_name) + '</td>' +
                    '<td>' + formatValue(club.founder_id) + '</td>' +
                    '<td>' + formatDate(club.create_date) + '</td>' +
                    '<td>' + formatValue(club.community_introduction)+ '</td>' +
                    '<td>' + formatValue(club.community_status)+ '</td>' +
                    '</tr>';
                $('.table1 tbody').append(trHtml);
                $('.table1 tbody tr').addClass('tdcolor');
    });
}