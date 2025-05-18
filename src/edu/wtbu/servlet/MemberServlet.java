package edu.wtbu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.pojo.Result;
import edu.wtbu.service.MemberService;

/**
 * Servlet implementation class MemberServlet
 */
@WebServlet("/member")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		// 获取分页参数
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            page = 1;
        }
        
        int pageSize = 10;
        try {
            pageSize = Integer.parseInt(request.getParameter("size"));
        } catch (Exception e) {
            pageSize = 10;
        }
        // 获取查询条件
        String userName = request.getParameter("user_name");
        String roleId = request.getParameter("user_role");
        
        Result result = MemberService.getMemberList(page, pageSize, userName, roleId);
        response.getWriter().write(JSON.toJSONString(result));
        
        
        
//		 List<HashMap<String, Object>> result;
//         if (roleId > 0) {
//             // 按角色和姓名搜索
//             result = UsersService.findUserListByRoleAndName(userName, roleId);
//         } else {
//             // 仅按姓名搜索
//             result = UsersService.findUserListByName(userName);
//         }
//        Result res = new Result();
//		res.setData(result);
//		res.setFlag("success");
//		response.getWriter().append(JSON.toJSONString(res));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
