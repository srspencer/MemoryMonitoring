package gov.uspto.ESSD.Monitoring;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HeapMonitor
 */
@WebServlet("/HeapMonitor")
public class HeapMonitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final float initThreshold = 50; 
    String message;
    Runtime runtime;
    float threshold;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeapMonitor() {
        super();
        runtime= Runtime.getRuntime();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String newThreshold = request.getParameter("threshold");
		if(newThreshold != null){
			try{
				threshold = new Float(newThreshold).floatValue();
			}catch(java.lang.NumberFormatException e){
				threshold = initThreshold;
			}
		}else{
			threshold = initThreshold;
		}
		
		out.println("<pre>");
		
		float p = (float)(runtime.totalMemory() - runtime.freeMemory())/(float)runtime.maxMemory()*100 ;
		out.print(p);
		out.println( " percent used");
		message = new String("Memory=OK");
		if(p > threshold){
			message = new String("Memory=problem");
		}
		out.println(message);
		out.println("##### Heap utilization statistics [MB] #####");
		out.println("Used:" + (runtime.totalMemory() - runtime.freeMemory()));
		out.println("Free:" + runtime.freeMemory());
		out.println("Total: "+ runtime.totalMemory());
		out.println("Max: " + runtime.maxMemory());
		out.println("</pre>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
