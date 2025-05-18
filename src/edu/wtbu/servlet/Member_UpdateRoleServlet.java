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
 * Servlet implementation class UpdateRoleMServlet
 */
@WebServlet("/updateRole")
public class Member_UpdateRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Member_UpdateRoleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
        int userId=0;
		try {
			userId=Integer.parseInt(request.getParameter("user_id"));
		} catch (Exception e) {
			// TODO: handle exception
			userId=0;
		}
		int roleId=0;
		try {
			roleId=Integer.parseInt(request.getParameter("user_role"));
		} catch (Exception e) {
			// TODO: handle exception
			roleId=0;
		}
		Result result = MemberService.updateRoleMember(userId, roleId);
		response.getWriter().append(JSON.toJSONString(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
