package com.blog;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run( String... args ) throws Exception {
//		System.out.println(passwordEncoder.encode("peter@123"));
		Role admin = new Role();
		admin.setId(AppConstants.ADMIN_USER);
		admin.setName("ROLE_ADMIN");

		Role normal = new Role();
		normal.setId(AppConstants.NORMAL_USER);
		normal.setName("ROLE_NORMAL");

		List <Role> roles = List.of(admin, normal);
		roleRepository.saveAll(roles);
	}
}
//	aditya@123 : $2a$10$ng3Y8v9ua7L8s5u7Zu.WjOMz9lZQ2vEnw7QTLHVSo1mCsxpX4yAb6
//	john@123   : $2a$10$m7w2twPA7f6w6DPlnZQCE.ftIFxRhheQGnOZ6qcYrb9eMZeHh0Fn2
//	peter@123  : $2a$10$2TJ7fnLDXa6Jxga83iAIP.PAFCv4oZIKvAGaCedgeN37PTmssZn7m