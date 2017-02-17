package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ShoppingCart;
import model.dao.ItemDAO;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
    }
    
	public void init() throws ServletException {
		ItemDAO itemDAO = new ItemDAO();
		this.getServletContext().setAttribute("itemDAO", itemDAO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("add") != null) {
			addItemToCart(request, (ShoppingCart)request.getSession().getAttribute("cart"));
		} 
		request.getRequestDispatcher("/Cart.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
		if(request.getParameter("add") != null) {
			addItemToCart(request, cart);
		} else if(request.getParameterMap() != null && !request.getParameterMap().isEmpty()) {
			cart.updateItems(request.getParameterMap());
			request.getSession().setAttribute("cart", cart);
			doGet(request, response);
		}
	}
	
	private void addItemToCart(HttpServletRequest request, ShoppingCart cart) {
		if(cart == null) {
			cart = new ShoppingCart((ItemDAO)this.getServletContext().getAttribute("itemDAO"), this.getServletContext().getInitParameter("HST"),
					this.getServletContext().getInitParameter("SHIPPING"),
					this.getServletContext().getInitParameter("SHIPPING_FREE_CUTOFF"));
		}
		String itemNumber = request.getParameter("add");
		cart.addItem(itemNumber);
		request.getSession().setAttribute("cart", cart);
	}

}
