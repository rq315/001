package edu.wtbu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.pojo.Result;
import edu.wtbu.service.ActivityService;

/**
 * Servlet implementation class PublishA
 */
@WebServlet("/publishA")
public class Activity_PublishServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Activity_PublishServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		int assocoated_community_number=0;
		try {
			assocoated_community_number=Integer.parseInt(request.getParameter("assocoated_community_number"));
		} catch (Exception e) {
			// TODO: handle exception
			assocoated_community_number=0;
		}
		String activity_name=request.getParameter("activity_name");
		String activity_type=request.getParameter("activity_type");
		String activity_date=request.getParameter("activity_date");
		String activity_location=request.getParameter("activity_location");
		String expected_activity_number=request.getParameter("expected_activity_number");
		String activity_description=request.getParameter("activity_description");
		System.out.println("Establishment Date: " + activity_date);
		// 验证日期格式
	    if (activity_date == null || !activity_date.matches("\\d{4}-\\d{2}-\\d{2}")) {
	        response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid date format. Expected format: YYYY-MM-DD\"}");
	        return;
	    }
		Result result=ActivityService.publishA(activity_name, activity_type, activity_date,
				activity_location, expected_activity_number, activity_description, assocoated_community_number);
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
