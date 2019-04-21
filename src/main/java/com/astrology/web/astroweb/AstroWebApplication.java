package com.astrology.web.astroweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AstroWebApplication {
	//implements ApplicationRunner {
	/*@Autowired
    private ApplicationContext context;*/


	public static void main(String[] args) {
		SpringApplication.run(AstroWebApplication.class, args);
	}

	/*@Override
    public void run(ApplicationArguments args) throws Exception {
        String[] beans = context.getBeanDefinitionNames();

        for (String bean: beans) {
            System.out.println(bean);
        }
    }*/
}
