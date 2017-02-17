package tags;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DigitNames extends SimpleTagSupport {

	private final Map<String, String> digitToWord;
	private int max = 0;
	
	public DigitNames() {
		digitToWord = new HashMap<String, String>();
		digitToWord.put("0", "zero");
		digitToWord.put("1", "one");
		digitToWord.put("2", "two");
		digitToWord.put("3", "three");
		digitToWord.put("4", "four");
		digitToWord.put("5", "five");
		digitToWord.put("6", "six");
		digitToWord.put("7", "seven");
		digitToWord.put("8", "eight");
		digitToWord.put("9", "nine");
	}
	
	public void doTag() throws JspException, IOException {
		JspWriter os = this.getJspContext().getOut();
		StringWriter sw = new StringWriter();
		getJspBody().invoke(sw);
		String amount = sw.toString();
		amount = amount.replaceAll(",", "");
		if(amount.contains(".")) {
			amount = amount.substring(0, amount.indexOf("."));	
		}
		if(max <= 0 || max <= Double.parseDouble(amount)) {
			os.write(sw.toString());
			return;
		}
		String[] nums = amount.split("");
		sw.append(" [");
		for(int x = 0; x < nums.length; x++) {
			String num = digitToWord.get(nums[x]);
			if(x < nums.length-1) {
				num += "-";
			}
			sw.append(num);
		}
		os.write(sw.toString() + "]");
	}
	
	public void setMax(int max) {
		this.max = max;
	}
}
