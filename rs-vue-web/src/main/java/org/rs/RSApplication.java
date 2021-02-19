package org.rs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.boot.Banner;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class RSApplication {

	public static void main(String[] args) throws Exception {

		SpringApplication app = new SpringApplication(RSApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setLogStartupInfo(true);
		ConfigurableApplicationContext ctx = app.run(args);
		ConfigurableEnvironment env = ctx.getBean(ConfigurableEnvironment.class);
		printInfo(env, app.getMainApplicationClass());
    }

	/**
	 * Print Banner
	 */
	private static void printInfo(ConfigurableEnvironment env, Class<?> main) throws UnknownHostException {
		
		Resource resource = new ClassPathResource("banner.txt");
		if (resource.exists()) {
			String host = InetAddress.getLocalHost().getHostAddress();
			Banner banner = new ResourceBanner(resource);
			env.getSystemProperties().put("host", host);
			env.getSystemProperties().put("javaVersion", JavaVersion.getJavaVersion().toString());
			banner.printBanner(env, main, System.out);
		}
	}
}
