// 分页状态对象
let memberPagination = {
    currentPage: 1,
    pageSize: 10,
    totalPages: 1,
    totalItems: 0
};
$(document).ready(function() {

    console.log("文档加载完成"); // 确认jQuery已加载

    // 初始加载成员数据
    loadMembers();
    
    
    // 搜索按钮点击事件
    $(".searchBtn").on('click', function() {
        memberPagination.currentPage = 1;
        loadMembers();
    });

    // 分页按钮事件
    $('#firstPage').click(() => goToPage(1));
    $('#prevPage').click(() => goToPage(memberPagination.currentPage - 1));
    $('#nextPage').click(() => goToPage(memberPagination.currentPage + 1));
    $('#lastPage').click(() => goToPage(memberPagination.totalPages));
    
    // 每页显示数量变化事件
    $('#pageSize').change(function() {
        memberPagination.pageSize = parseInt($(this).val());
        memberPagination.currentPage = 1;
        loadMembers();
    });

    //添加成员按钮
    $(".btn_publish").click(function(){
        console.log("发布按钮被点击");
        location.href="member_add.html";
    })
    
});

// 加载成员数据
function loadMembers() {
    $('.table1 tbody').html('<tr><td colspan="7">加载中...</td></tr>');
    
    var userName = $(".user_name").val().trim();
    var roleId = $(".user_role").val();
    
    $.ajax({
        url: 'http://localhost:8080/Community/member',
        type: "POST",
        data: { 
            user_name: userName,
            user_role: roleId,
            page: memberPagination.currentPage,
            size: memberPagination.pageSize
        },
        dataType: "json",
        success: function(response) {
            try {
                if (response.flag === "success") {
                    // 更新分页信息
                    memberPagination.currentPage = response.data.currentPage || 1;
                    memberPagination.totalPages = response.data.totalPages || 1;
                    memberPagination.totalItems = response.data.totalItems || 0;
                    
                    // 渲染表格
                    if (response.data.list && response.data.list.length > 0) {
                        renderMemberTable(response.data.list);
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
            showErrorMessage("网络请求失败...请刷新");
        }
    });
}

// 更新分页控件
function updatePaginationControls() {
    // 更新按钮状态
    $("#firstPage").prop("disabled", memberPagination.currentPage === 1);
    $("#prevPage").prop("disabled", memberPagination.currentPage === 1);
    $("#nextPage").prop("disabled", memberPagination.currentPage === memberPagination.totalPages);
    $("#lastPage").prop("disabled", memberPagination.currentPage === memberPagination.totalPages);
 
    // 更新页码显示
    let pageNumbersHtml = "";
    const startPage = Math.max(1, memberPagination.currentPage - 2);
    const endPage = Math.min(memberPagination.totalPages, memberPagination.currentPage + 2);
    
    for (let i = startPage; i <= endPage; i++) {
        const activeClass = i === memberPagination.currentPage ? 'active' : '';
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
    if (page < 1 || page > memberPagination.totalPages || page === memberPagination.currentPage) return;
    
    memberPagination.currentPage = page;
    loadMembers();
}

// 更新分页信息
function updatePageInfo() {
    const start = (memberPagination.currentPage - 1) * memberPagination.pageSize + 1;
    const end = Math.min(memberPagination.currentPage * memberPagination.pageSize, memberPagination.totalItems);
    $('#pageInfo').text(`显示 ${start}-${end} 条，共 ${memberPagination.totalItems} 条`);
}

function showNoDataMessage() {
    $('.table1 tbody').html(
        '<tr><td colspan="7" class="no-data">未找到匹配的成员...请刷新</td></tr>'
    );
}

function showErrorMessage() {
    $('.table1 tbody').html(
        '<tr><td colspan="7" class="error">数据加载失败...请刷新</td></tr>'
    );
}


//渲染表格数据
window.renderMemberTable = function(members){
    // 清空表格
    $('.table1 tbody').empty();
    
    // 检查数据是否存在
    if (!members || members.length === 0) {
        $('.table1 tbody').append(
            '<tr><td colspan="7" class="no-data">未找到匹配的成员...请刷新</td></tr>'
        );
        return;
    }
    console.log(members)

     // 统一处理空值的工具函数
     function formatValue(value) {
        return (value === null || value === undefined || value === '') ? '-' : value;
    }

    // 处理日期的工具函数
    function formatDate(dateString) {
        if (!dateString) return '-';
        try {
            const dateObj = new Date(dateString);
            return isNaN(dateObj.getTime()) ? '-' : dateObj.toLocaleDateString();
        } catch (e) {
            console.error("日期解析错误:", e);
            return '-';
        }
    }

    // 遍历成员数据
    members.forEach(function(member) {
        // 转换角色ID为文本
        var roleText = member.user_role == 1 ? "User" : "Administrator";
           
        // 创建表格行
        var trHtml = '<tr>' +
        '<td>' + formatValue(member.user_name) + '</td>' +
        '<td>' + formatValue(member.user_student_id) + '</td>' +
        '<td>' + formatDate(member.user_join_date)+ '</td>' +
        '<td>' + roleText + '</td>' +
        '<td>' + formatValue(member.user_phone)+ '</td>' +
        '<td>' + formatValue(member.community_name) + '</td>' +
        '<td>' +
                '<button class="signup-btn" data-member-id="' + member.user_id + '">退出</button>' +
                '<button class="summary-btn" data-member-id="' + member.user_id + '">权限分配</button>' +
        '</td>' +
        '</tr>';
            // console.log(trHtml);
        
        $('.table1 tbody').append(trHtml);
        $(".table1 tbody tr").addClass("tdcolor1");
        
    });

    // 绑定按钮事件
    $('.signup-btn').on('click', function() {
        const memberId = $(this).data("memberId");
        console.log("user_id="+memberId);
        console.log("删除按钮被点击了");
        if(confirm('确定要删除此成员吗？')) {
            deleteMember(memberId);
        }
    });
    
    $('.summary-btn').on('click', function() {
        const memberId = $(this).data("memberId");
        console.log("分配按钮被点击了");
        console.log("user_id="+memberId);
        updateMemberRole(memberId);
    });

}
// 删除成员成功后刷新列表
function deleteMember(memberId) {
    // if(confirm('确定要删除此成员吗？')) {
        $.ajax({
            url: 'http://localhost:8080/Community/deleteMember?',
            type: "POST",
            dataType:"json",
            data: { user_id: memberId },
            success: function(response) {
                if(response.flag === "success") {
                    alert('删除成功');
                    loadMembers(); // 刷新列表
                } else {
                    alert('删除失败: ' + (response.message || ''));
                }
            },
            error: function() {
                alert('请求失败，请重试');
            }
        });
    // }
}


// 更新权限成功后刷新列表
function updateMemberRole(memberId) {
    const newRole = prompt('请输入新角色(1-User, 2-Administrator):');
    if(newRole && (newRole === '1' || newRole === '2')) {
        $.ajax({
            url: 'http://localhost:8080/Community/updateRole',
            type: "POST",
            dataType: "json",
            data: { 
                user_id: memberId,
                user_role: newRole 
            },
            success: function(response) {
            console.log("更新的成员id："+memberId+" ,roleid："+newRole);
            console.log("数据响应："+response);
            console.log(response.flag);

                if(response.flag === "success") {
                    alert('权限更新成功');
                    loadMembers(); // 刷新列表
                } else {
                    alert('更新失败: ' + (response.message || ''));
                }
            },
            error: function() {
                alert('请求失败，请重试');
            }
        });
    } else if(newRole !== null) {
        alert('请输入有效的角色值(1或2)');
    }
}
