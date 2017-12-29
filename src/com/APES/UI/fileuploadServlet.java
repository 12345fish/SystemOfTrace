package com.APES.UI;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.APES.Utils.DBUtils;
import com.APES.Utils.IOUtils;

public class fileuploadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);

		List<FileItem> list;
		try {
			list = fileUpload.parseRequest(request);
			for (FileItem item : list) {
				if (!item.isFormField()) {
					String filename = item.getName();
					if (filename == "" || !filename.endsWith("csv")) {   //�ļ�Ϊ�ջ����ļ���ʽ���ϸ�
						String errormessage = "�����ˣ��ϴ����ļ�����Ϊ���ұ���Ϊcsv�ļ�����ѡ����ȷ��csv�ļ�<br>";
						request.setAttribute("errormessage", errormessage);
						request.getRequestDispatcher("Failure.jsp").forward(
								request, response);
						
					} else {
						String filepathInDisk = this.getServletContext()
								.getRealPath("/upload/" + filename);
						InputStream in = item.getInputStream();
						OutputStream out = new FileOutputStream(filepathInDisk);
						IOUtils.In2Out(in, out);
						IOUtils.close(in, out);
						String msg=DBUtils.CSV2DB(filepathInDisk);
						if(msg.equals("ERROR"))
						{
							String errormessage="�����ˣ��ļ��ϴ�ʧ�ܣ���ѡ�����ݸ�ʽ��ȷ���ļ�<br>";
							request.setAttribute("errormessage", errormessage);
							request.getRequestDispatcher("/Failure.jsp").forward(request, response);
						}
						else {
							DBUtils.InitUpload(msg);
							msg=msg.replaceAll("_", "");
							String successmessage = "�ļ��ϴ��ɹ�!���ز鿴�ϴ�·��!<br>";
							request.setAttribute("successmessage", successmessage);
							this.getServletContext().setAttribute("traceId", msg);
							request.getRequestDispatcher("/Success.jsp").forward(
									request, response);	
						}
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

}
