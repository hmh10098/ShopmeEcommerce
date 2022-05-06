package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.user.repository.RoleRepository;
import com.shopme.common.model.Role;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRole() {
		Role roleSalesperson = new Role("SalePerson", "manage product price, customer, shipping, orders and sales reporte");
		
		Role roleEditor = new Role("Editor", "manage categories, brands, products, articles and menus");
		
		Role roleShipper = new Role("Shipper", "view product, view products, view orders and update orders status ");
		
		Role roleAssistant = new Role("Assistant", "manage questions and reviews");

		repo.saveAll(List.of(roleAssistant, roleEditor, roleSalesperson, roleShipper));
	}
	
}
