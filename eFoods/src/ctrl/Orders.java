package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Order;

/**
 * Servlet implementation class Orders
 */
@WebServlet("/orders/*")
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Orders() {
        super();
    }
    
    public void init() {
    	Order order = new Order("/home/user/ws_4413/eFoods/WebContent/res/orders/");
    	this.getServletContext().setAttribute("order", order);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Order order = (Order) this.getServletContext().getAttribute("order");
		try {
			request.setAttribute("placedOrder", order.retrieve(request.getPathInfo()));
		} catch(Exception ex) {
			ex.printStackTrace();
			request.setAttribute("error", ex.getMessage());
		}
		request.getRequestDispatcher("/Order.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
