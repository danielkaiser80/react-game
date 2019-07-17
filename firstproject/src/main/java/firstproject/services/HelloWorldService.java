package firstproject.services;

import org.springframework.stereotype.Service;

/**
 * Hello World service class from the Spring tutorial
 */
@Service("helloWorldService")
public class HelloWorldService {

	/**
	 * the name of the person
	 */
	private String name;


	/**
	 * Set a new name
	 * 
	 * @param name the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	protected String getName() {
		return name;
	}

	/**
	 * Say hello to the person
	 * @return the greeting
	 */
	public String sayHello() {
		return "Hello! " + name;
	}
}