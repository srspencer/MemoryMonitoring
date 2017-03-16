package gov.uspto.ESSD.Monitoring;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LinuxLoad
 */
@WebServlet("/LinuxLoad")
public class LinuxLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final double loadThreshold = 1;
	OperatingSystemMXBean osBean;
	String message;
	double threshold;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinuxLoad() {
        super();
        osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String newThreshold = request.getParameter("threshold");
		if(newThreshold != null){
			try{
				threshold = new Double(newThreshold).doubleValue();
			}catch(java.lang.NumberFormatException e){
				threshold = loadThreshold;
			}
		}else{
			threshold = loadThreshold;
		}
		message = new String("Load=OK");
		out.println("<pre>");
		double l = osBean.getSystemLoadAverage();
		out.println(l);
		if(l > threshold){
			message = ("Load=excess");
		}
		out.println(message);
		out.println("</pre>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
