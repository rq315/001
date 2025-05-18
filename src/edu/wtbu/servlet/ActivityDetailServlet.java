package edu.wtbu.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.pojo.Result;
import edu.wtbu.service.ActivityService;

/**
 * Servlet implementation class ActivityDetailServlet
 */
@WebServlet("/activityDetail")
public class ActivityDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		int activity_id=0;
		try {
			activity_id=Integer.parseInt(request.getParameter("activity_id"));
		} catch (Exception e) {
			// TODO: handle exception
			activity_id=0;
		}
		HashMap<String, Object> result = ActivityService.getActivityDetailList(activity_id);
		Result res = new Result();
		res.setData(result);
		res.setFlag("success");
		response.getWriter().append(JSON.toJSONString(res));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
