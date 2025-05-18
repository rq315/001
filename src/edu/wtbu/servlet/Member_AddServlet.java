package edu.wtbu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.StuId;
import edu.wtbu.pojo.Result;
import edu.wtbu.service.MemberService;

/**
 * Servlet implementation class AddMemberServlet
 */
@WebServlet("/addMember")
public class Member_AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Member_AddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		int clubId=0;
		try {
			clubId=Integer.parseInt(request.getParameter("user_belong_community_number"));
		} catch (Exception e) {
			// TODO: handle exception
			clubId=0;
		}
		int user_role=0;
		try {
			user_role=Integer.parseInt(request.getParameter("user_role"));
		} catch (Exception e) {
			// TODO: handle exception
			user_role=0;
		}
		String user_name = request.getParameter("user_name");
        String user_phone = request.getParameter("user_phone");
        String user_join_date = request.getParameter("user_join_date");
        // 验证日期格式
//	    if (user_join_date == null || !user_join_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
//	        response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid date format. Expected format: YYYY-MM-DD\"}");
//	        return;
//	    }
        // 生成学号
        String studentId = StuId.generateStudentId();
        Result result=MemberService.addMember(user_name, studentId, user_join_date, user_role, user_phone, clubId);
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
