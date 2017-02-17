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
import model.dao.ItemDAO;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
    }

    public void init() {
    	ItemDAO itemDAO = new ItemDAO();
    	this.getServletContext().setAttribute("itemDAO", itemDAO);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("query") != null) {
			ItemDAO itemDAO = (ItemDAO) request.getServletContext().getAttribute("itemDAO");
			try {
				List<ItemBean> searchResults = itemDAO.getItemsByNameMatching(request.getParameter("query"));
				request.setAttribute("minItemPrice", ItemUtils.getMinPrice(searchResults));
				request.setAttribute("maxItemPrice", ItemUtils.getMaxPrice(searchResults));
				if(request.getQueryString() != null) {
					request.setAttribute("searchResults", ItemUtils.applyFilter(searchResults,
							request.getParameter("keywords"), request.getParameter("maxPrice"), request.getParameter("sort")));
				} else {
					request.setAttribute("searchResults", searchResults);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("/Search.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
