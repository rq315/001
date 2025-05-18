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
 * Servlet implementation class SearchAServlet
 */
@WebServlet("/searchAI")
public class SearchAIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAIServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
//		//获取参数
//		String activity_name=request.getParameter("activity_name");
//		String activity_type=request.getParameter("activity_type");
//		
//		 List<HashMap<String, Object>> result;
//         if (activity_type!=null) {
//             // 按名称和类型搜索
//             result = UsersService.findActivityListByTypeAndName(activity_name, activity_type);
//         } else {
//             // 仅按名称搜索
//             result = UsersService.findActivityListByName(activity_name);
//         }
//        Result res = new Result();
//		res.setData(result);
//		res.setFlag("success");
//		response.getWriter().append(JSON.toJSONString(res));
		
		int page =0;
		try {
			page =Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			// TODO: handle exception
			page =0;
		}
		int pageSize =0;
		try {
			pageSize =Integer.parseInt(request.getParameter("size"));
		} catch (Exception e) {
			// TODO: handle exception
			pageSize =0;
		}
		String activityName = request.getParameter("activity_name");
        String activityType = request.getParameter("activity_type");
        Result result = ActivityService.searchAI(activityName, activityType, page, pageSize);
        response.getWriter().write(JSON.toJSONString(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
