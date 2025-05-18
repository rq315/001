package edu.wtbu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StuId {
	//����ݿ�ͷ��10λ���ѧ������
    public static String generateStudentId() {
    	
    	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(new Date());
         
        // ����6λ�����
        Random random = new Random();
        int randomNum = random.nextInt(900000) + 100000;
         
        return year + randomNum;
    }
}