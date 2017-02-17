package adhoc;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ItemBean;
import model.ShoppingCart;

/**
 * Servlet Filter implementation class CrossSell
 */
@WebFilter(dispatcherTypes = {DispatcherType.FORWARD }
					, urlPatterns = { "/Cart.jspx" })
public class CrossSell implements Filter {

    /**
     * Default constructor. 
     */
    public CrossSell() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ShoppingCart cart = (ShoppingCart) ((HttpServletRequest)request).getSession().getAttribute("cart");
		boolean present = false;
		if(cart != null) {
			Optional<ItemBean> itemInCart = cart.getItems().keySet().stream()
				.filter(item -> item.getItemNumber().equals("1409S413"))
				.findAny();
			present = itemInCart.isPresent();
		}
		if(present) {
			MyResponse resp = new MyResponse((HttpServletResponse)response);
			chain.doFilter(request, resp);
			try {
				ItemBean crossSell = cart.getItemDAO().getItemByNumber("2002H712");
				String html = "<h3 style=\"margin-top: 16px;\"><span class=\"glyphicon glyphicon-shopping-cart\"></span> Related Items</h3><hr/>";
				html += "<form method=\"get\">";
				html += "<div class=\"item col-lg-12 col-md-12 col-sm-12 col-xs-12\">";
				html += "<img class=\"col-md-2 col-sm-2 col-xs-2\" style=\"background: " + crossSell.getHexColor() + ";\"/>";
				html += "<div class=\"col-md-10 col-sm-10 col-xs-10\">";
				html += "<div class=\"item-name\">" + crossSell.getName() + "</div>";
				html += "<div class=\"item-number\">" + crossSell.getItemNumber() + "</div>";
				html += "<input type=\"hidden\" value=\"" + crossSell.getItemNumber() + "\" name=\"add\"/>";
				html += String.format("<div>$%.2f %s</div>", crossSell.getPrice(), crossSell.getUnit());
				html += "<button type=\"submit\" class=\"btn btn-success\">Add to Cart</button>";
				html += "</div></div>";
				String content = resp.getContent();
				content = content.replace("</a></div></form>", "</a></div></form>" + html);
				response.getWriter().write(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
