package com.APES.UI;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.APES.Algorithm.Algorithm;
import com.APES.Model.Point;
import com.APES.Model.Trace;
import com.APES.Utils.DBUtils;
import com.APES.Utils.JDBCUtils;

public class CalculateSimilarityServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] choices=request.getParameterValues("choices");
		double Similarity=0.0;
		if(choices==null || choices.length!=2)
		{
		  String errormessage="�����ˣ����ѡ�������켣���бȽ�<br>";
				  
		request.setAttribute("errormessage", errormessage);
		request.getRequestDispatcher("/Failure.jsp").forward(request, response);
		}
		else 
		{
			boolean isWithTime=false;
			int id1=Integer.parseInt(choices[0].substring(1));
			int id2=Integer.parseInt(choices[1].substring(1));
			if(choices[0].startsWith("Y"))
				isWithTime=true;
			Similarity=Algorithm.GetSimilarity(id1, id2,isWithTime);
			DecimalFormat df = new DecimalFormat("0.000000");  //�����������뱣��С��λ��
			String successmessage="";
			if(isWithTime)
			{
				
			    Similarity=Math.pow(0.99,Similarity)*100;
			    successmessage= "����ʱ���<font color='blue' size='20'>�켣"+id1+"</font> ��<font color='blue' size='20'>�켣"+id2+"</font> �����ƶ�Ϊ��<br>"+df.format(Similarity)+"%<br>";
			}
			else 
			{
				Similarity=Math.pow(0.8,Similarity)*100;
				successmessage= "������ʱ���<font color='blue' size='20'>�켣"+id1+"</font> ��<font color='blue' size='20'>�켣"+id2+"</font> �����ƶ�Ϊ��<br>"+df.format(Similarity)+"%<br>";
			}
			this.getServletContext().removeAttribute("traceId");
			request.setAttribute("successmessage", successmessage);
			request.getRequestDispatcher("/Success.jsp").forward(
					request, response);	
		}
	}
}
