//添加加载状态控制
var isDataLoading = false;

let totalSum = 0; // 存储总费用

// 分页状态对象
let Pagination = {
    currentPage: 1,
    pageSize: 10,
    totalPages: 1,
    totalItems: 0
};

$(document).ready(function() {
    
    // 分页按钮事件
    $('#firstPage').click(() => goToPage(1));
    $('#prevPage').click(() => goToPage(Pagination.currentPage - 1));
    $('#nextPage').click(() => goToPage(Pagination.currentPage + 1));
    $('#lastPage').click(() => goToPage(Pagination.totalPages));
    
    // 每页显示数量变化事件
    $('#pageSize').change(function() {
        Pagination.pageSize = parseInt($(this).val());
        Pagination.currentPage = 1;
        loadFinances();
    });
    
    // 初始加载经费数据
    loadFinances();

    //收支按钮
    $(".btn_publish").click(function(){
        console.log("按钮被点击");
        location.href="finance_add.html";
    })
});

// 加载经费数据 
function loadFinances() {
    if (isDataLoading) return;
    isDataLoading = true;

    $('.table1 tbody').html('<tr><td colspan="5">加载中...请刷新</td></tr>');
    

    $.ajax({
        url: 'http://localhost:8080/Community/finance',
        type: "GET",
        data: {
            page: Pagination.currentPage,
            size: Pagination.pageSize,
            needTotal: true //来计算总费用
        },
        dataType: "json",
        success: function(response) {

            if(response.flag === "success") {
                // 更新分页信息
                Pagination.currentPage = response.data.currentPage;
                Pagination.totalPages = response.data.totalPages;
                Pagination.totalItems = response.data.totalItems;
                
                // 更新总费用
                if(response.data.totalSum !== undefined) {
                    totalSum = response.data.totalSum;
                    $(".money").text(totalSum.toFixed(2));
                }

                // 渲染表格
                renderFinanceTable(response.data.list);
                
                // 更新分页控件
                updatePaginationControls();
                updatePageInfo();
            } else {
                showErrorMessage(response.data || "请求失败");
            }
          
        },
        complete: function() {
            isDataLoading = false;
        },
        error: function(xhr, status, error) {
            console.error('请求失败:', error);
            showErrorMessage("网络请求失败");
        }
    });
}



//数据渲染逻辑
function renderFinanceTable(finances) {
    $('.table1 tbody').empty();

    //小计费用
    var currentPageSum = 0;

    if (!finances || finances.length === 0) {
        showNoDataMessage();
        return;
    }
    
    finances.forEach(function(finance) {
        var trHtml = `<tr>
            <td>${finance.fund_amount}</td>
            <td>${finance.fund_type}</td>
            <td>${formatDate(finance.fund_record_date)|| '-'}</td>
            <td>${finance.fund_description || '-'}</td>
            <td>${finance.activity_name || '无关联活动'}</td>
        </tr>`;
        currentPageSum  += parseFloat(finance.fund_amount || 0);
        $('.table1 tbody').append(trHtml);
    });
    
    // 显示当前页小计
    $(".current-page-sum").text(currentPageSum.toFixed(2));

    $(".table1 tbody tr").addClass("tdcolor1");
}

// 格式化日期
function formatDate(dateStr) {
    if (!dateStr) return '-';
    try {
        return new Date(dateStr).toLocaleDateString();
    } catch (e) {
        return dateStr;
    }
}

// 更新分页控件
function updatePaginationControls() {
    // 更新按钮状态
    $("#firstPage").prop("disabled", Pagination.currentPage === 1);
    $("#prevPage").prop("disabled", Pagination.currentPage === 1);
    $("#nextPage").prop("disabled", Pagination.currentPage === Pagination.totalPages);
    $("#lastPage").prop("disabled", Pagination.currentPage === Pagination.totalPages);
 
    // 更新页码显示
    let pageNumbersHtml = "";
    const startPage = Math.max(1, Pagination.currentPage - 2);
    const endPage = Math.min(Pagination.totalPages, Pagination.currentPage + 2);
    
    for (let i = startPage; i <= endPage; i++) {
        const activeClass = i === Pagination.currentPage ? 'active' : '';
        pageNumbersHtml += `<span class="page-number ${activeClass}" data-page="${i}">${i}</span>`;
    }
    
    $('#pageNumbers').html(pageNumbersHtml);

    // 绑定页码点击事件
    $(".page-number").click(function() {
        const page = parseInt($(this).data("page"));
        goToPage(page);
    });
}

// 跳转到指定页
function goToPage(page) {
    if (page < 1 || page > Pagination.totalPages || page === Pagination.currentPage) return;
    
    Pagination.currentPage = page;
    loadFinances();
}

// 更新分页信息
function updatePageInfo() {
    const start = (Pagination.currentPage - 1) * Pagination.pageSize + 1;
    const end = Math.min(Pagination.currentPage * Pagination.pageSize, Pagination.totalItems);
    $('#pageInfo').text(`显示 ${start}-${end} 条，共 ${Pagination.totalItems} 条`);
}

// 显示无数据信息
function showNoDataMessage() {
    $('.table1 tbody').html(
        '<tr><td colspan="5" class="no-data">暂无数据...请刷新</td></tr>'
    );
    Pagination.totalItems = 0;
    Pagination.totalPages = 1;
    updatePaginationControls();
    updatePageInfo();
}

// 显示错误信息
function showErrorMessage(message) {
    $('.table1 tbody').html(
        `<tr><td colspan="5" class="error">${message || '数据加载失败...请刷新'}</td></tr>`
    );
    $('#pageInfo').text("数据加载错误...请刷新");
    Pagination.totalPages = 1;
    updatePaginationControls();
}