package ctrl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Authenticator;
import model.CustomerBean;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Auth() {
		super();
	}

	public void init() {
		Authenticator auth = new Authenticator();
		this.getServletContext().setAttribute("auth", auth);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Authenticator auth = (Authenticator) this.getServletContext().getAttribute("auth");
		String username = request.getParameter("user");
		String fullName = request.getParameter("fullName");
		String hash = request.getParameter("hash");
		if(username != null && hash != null && fullName != null) {
			try {
				CustomerBean customer = auth.validate(username, hash, Long.parseLong(request.getSession().getAttribute("authNonce").toString()), fullName);
				request.getSession().removeAttribute("authNonce");
				request.getSession().setAttribute("authorizedUser", customer);
				String back = "/";
				if(request.getSession().getAttribute("authBack") != null) {
					back = request.getSession().getAttribute("authBack").toString();
					request.getSession().removeAttribute("authBack");
				}
				response.sendRedirect(back);
			} catch(Exception ex) {
				ex.printStackTrace();
				response.sendRedirect("/");
			}
		} else {
			long secret = auth.getNextSecret();
			response.sendRedirect("https://www.eecs.yorku.ca/~danielru/auth/auth.cgi?secret=" + secret
					+ "&back=" + request.getRequestURL());
			request.getSession().setAttribute("authNonce", secret);
			if(request.getParameter("back") != null) {
				request.getSession().setAttribute("authBack", request.getParameter("back"));
			} else {
				request.getSession().setAttribute("authBack", request.getHeader("referer"));
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
