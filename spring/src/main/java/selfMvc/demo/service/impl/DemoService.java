package selfMvc.demo.service.impl;


import selfMvc.demo.service.IDemoService;
import selfMvc.mvcframework.annotation.GPService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name;
	}

}
