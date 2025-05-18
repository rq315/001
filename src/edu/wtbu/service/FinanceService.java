package edu.wtbu.service;

import java.util.HashMap;
import java.util.List;

import edu.wtbu.dao.FinanceDao;
import edu.wtbu.pojo.Result;

public class FinanceService {
	//���ѱ���ҳ�������ܷ��ã�
	public static Result getFinanceList(int page, int pageSize) {
        Result result = new Result();
        try {
            // ��ȡ�ܼ�¼��
            int totalItems = FinanceDao.getFinanceCount();
            
            // ������ҳ��
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            
            // ��ȡ��ǰҳ����
            List<HashMap<String, Object>> list = FinanceDao.getFinanceList(page, pageSize);
            
            // ��ȡ�ܷ���
            double totalSum = FinanceDao.getTotalFundAmount();
            
            // �������ؽ��
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("list", list);
            data.put("currentPage", page);
            data.put("totalPages", totalPages);
            data.put("totalItems", totalItems);
            data.put("totalSum", totalSum); // ����ܷ���
            
            result.setFlag("success");
            result.setData(data);
        } catch (Exception e) {
            result.setFlag("fail");
            result.setData("��ѯ�����б�ʧ��: " + e.getMessage()+"...��ˢ��");
        }
        return result;
    }
	
	
	//��Ӿ�����Ϣ
		public static Result addFinance(int fund_amount,String fund_type, String fund_record_date, String fund_description,
				int associated_activity_number) {
		Result result = new Result("fail", null, null);
		int rows = FinanceDao.addFinance(fund_amount, fund_type, fund_record_date, fund_description, associated_activity_number);
		if (rows > 0) {
			result.setFlag("success");
			result.setData("��ӳɹ�");
		} else {
			result.setData("���ʧ��");
		}
		return result;
	}
		
//	//������Ϣ
//	public static List<HashMap<String, Object>> getFinanceList() {
//        return UsersDao.getFinanceList();
//    }
	
}
