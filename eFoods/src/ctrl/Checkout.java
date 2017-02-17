package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CustomerBean;
import model.Order;
import model.ShoppingCart;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
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
		if(request.getSession().getAttribute("authorizedUser") == null) {
			authenticateUser(request, response);
		} else {
			request.getRequestDispatcher("/Checkout.jspx").forward(request, response);	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("authorizedUser") == null) {
			authenticateUser(request, response);
		} else {
			Order order = (Order) this.getServletContext().getAttribute("order");
			CustomerBean customer = (CustomerBean) request.getSession().getAttribute("authorizedUser");
			ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
			try {
				String orderNum = order.place(customer, cart);
				request.getSession().setAttribute("cart", new ShoppingCart(cart.getItemDAO(), this.getServletContext().getInitParameter("HST"),
						this.getServletContext().getInitParameter("SHIPPING"),
						this.getServletContext().getInitParameter("SHIPPING_FREE_CUTOFF")));
				request.setAttribute("placedOrderNumber", orderNum);
				request.getRequestDispatcher("/Placed.jspx").forward(request, response);
			} catch(Exception ex) {
				request.setAttribute("error", ex.getMessage());
				request.getRequestDispatcher("/Checkout.jspx").forward(request, response);	
			}
		}
	}
	
	private void authenticateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/auth?back=" + request.getRequestURI()).forward(request, response);
	}

}
