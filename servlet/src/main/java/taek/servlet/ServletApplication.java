package taek.servlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) throws Exception {

		MyBeanFactoryByConstructor factory = new MyBeanFactoryByConstructor("/Users/taek/Documents/Spring/4th-Server-Wontaek/servlet/src/main/java/taek/servlet/beans.xml");

		MemberService memberService = (MemberService) factory.getBean("memberService");

		memberService.register();
	}


}







