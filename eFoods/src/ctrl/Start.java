package ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.dao.ItemDAO;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Start", "/Startup/*"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
	}
	
	public void init() throws ServletException {
		ItemDAO itemDAO = new ItemDAO();
		CategoryDAO categoryDAO = new CategoryDAO();
		this.getServletContext().setAttribute("categoryDAO", categoryDAO);
		this.getServletContext().setAttribute("itemDAO", itemDAO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException	{
		ItemDAO itemDAO = (ItemDAO) this.getServletContext().getAttribute("itemDAO");
		CategoryDAO categoryDAO = (CategoryDAO) this.getServletContext().getAttribute("categoryDAO");
		try {
			request.setAttribute("categories", categoryDAO.getCategories());
			request.setAttribute("items", itemDAO.getRandomItems());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		request.getRequestDispatcher("/UI.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
