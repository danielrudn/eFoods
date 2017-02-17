package analytics;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import model.ShoppingCart;

/**
 * Application Lifecycle Listener implementation class CheckoutTime
 *
 */
@WebListener
public class CheckoutTime implements HttpSessionAttributeListener, HttpSessionListener {

	private List<Long> checkoutTimes;
	
    public CheckoutTime() {
    	checkoutTimes = new ArrayList<Long>();
    }
    
    public void sessionCreated(HttpSessionEvent se)  { 
    	long creationTime = System.currentTimeMillis();
    	se.getSession().setAttribute("creationTime", creationTime);
    }

    public void sessionDestroyed(HttpSessionEvent se)  { }

    public void attributeAdded(HttpSessionBindingEvent se)  { }

    public void attributeRemoved(HttpSessionBindingEvent se)  { }

    public void attributeReplaced(HttpSessionBindingEvent se)  {
    	if(se.getName().equals("cart")) {
    		// if cart is replaced with an empty one, a checkout has completed
    		ShoppingCart newCart = (ShoppingCart) se.getSession().getAttribute("cart");
    		ShoppingCart originalCart = (ShoppingCart) se.getValue();
    		if(originalCart.getItemCount() > 0 && newCart.getItemCount() == 0) {
    			long checkoutTime = System.currentTimeMillis();
        		long creationTime = Long.parseLong(se.getSession().getAttribute("creationTime").toString());
        		checkoutTimes.add(checkoutTime - creationTime);
        		se.getSession().getServletContext().setAttribute("checkoutTimes", checkoutTimes.stream().mapToLong(Long::longValue).average().getAsDouble());
    		}
    	}
    }
	
}
