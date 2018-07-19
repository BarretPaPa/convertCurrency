/**  

* @Title: CurrentExchangeController.java

* @Package demo.mine.currentExch.util

* @Description: TODO(用一句话描述该文件做什么)

* @author Huang Barret

* @date 2018年7月19日 上午9:37:39

* @version V1.0  

*/
package demo.mine.currentExch.util;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**

* @ClassName: CurrentExchangeController

* @Description: TODO(这里用一句话描述这个类的作用)

* @author Huang Barret

* @date 2018年7月19日 上午9:37:39

*


*/
@RestController
public class CurrentExchangeController {
	
	private final Map<String,Double> change = new HashMap<String,Double>() {
		/**
		
		* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
		
		*/
		private static final long serialVersionUID = 1L;

		{
			put("USD-CHY",6.732100);
			put("CHY-USD",0.148591);
			put("HKD-EUR",0.109364);
			put("EUR-HKD",9.143789);
			put("GBP-HKD",10.256303);
			put("HKD-GBP",0.097501);
		}
	};
	
	@RequestMapping(value="/convert/{from}/{to}",method=RequestMethod.GET)
	public void convertAll(HttpServletResponse response, @PathVariable String from, @PathVariable String to,@RequestParam(value = "amount", defaultValue = "0") double amount) {
		double rate=-9999999;
		String re=null;
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00000");
		if(change.containsKey(from+"-"+to)) {
			try {
				rate = change.get(from+"-"+to);
				re = decimalFormat.format(rate*amount);
			}catch (Exception e) {
				rate=0;
				re=decimalFormat.format(amount);
			}
			
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("amount", re);
		result.put("rate", rate);
		result.put("from", from);
		result.put("to", to);
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					response.getOutputStream(), "UTF-8"));
			out.println(result);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
