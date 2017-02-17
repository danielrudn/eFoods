package ctrl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ItemBean;
import model.ItemUtils;
import model.dao.CategoryDAO;
import model.dao.ItemDAO;

/**
 * Servlet implementation class Category
 */
@WebServlet("/category/*")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Category() {
        super();
    }
    
    public void init() {
    	ItemDAO itemDAO = new ItemDAO();
    	CategoryDAO categoryDAO = new CategoryDAO();
    	this.getServletContext().setAttribute("categoryDAO", categoryDAO);
    	this.getServletContext().setAttribute("itemDAO", itemDAO);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryDAO categoryDAO = (CategoryDAO) this.getServletContext().getAttribute("categoryDAO");
		ItemDAO itemDAO = (ItemDAO) this.getServletContext().getAttribute("itemDAO");
		String path = request.getPathInfo();
		if(path.matches("/\\d+$")) {
			int categoryId = Integer.parseInt(path.substring(path.lastIndexOf("/")+1, path.length()));
			try {
				request.setAttribute("category", categoryDAO.getCategoryById(categoryId));
				List<ItemBean> items = itemDAO.getItemsInCategory(categoryId);
				request.setAttribute("minItemPrice", ItemUtils.getMinPrice(items));
				request.setAttribute("maxItemPrice", ItemUtils.getMaxPrice(items));
				if(request.getQueryString() != null) {
					request.setAttribute("items", ItemUtils.applyFilter(items,
							request.getParameter("keywords"), request.getParameter("maxPrice"), request.getParameter("sort")));
				} else {
					request.setAttribute("items", items);
				}
			} catch (Exception e) {
				request.getRequestDispatcher("/").forward(request, response);
			}
		}
		request.getRequestDispatcher("/Category.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
