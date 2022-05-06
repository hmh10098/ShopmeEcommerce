package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.user.repository.UserRepository;
import com.shopme.common.model.Role;
import com.shopme.common.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
//	@Test
//	public void testCreateNewUserWithOneRole() {
//		Role roleAdmin = testEntityManager.find(Role.class, 1);
//		User userHieuhm = new User("hieu@gmail.com", "hieu123", "Hieu", "Ha Minh");
//		userHieuhm.addRole(roleAdmin);
//		
//		User savedUser = repo.save(userHieuhm);
//		
//		assertThat(savedUser.getId()).isGreaterThan(0);
//		
//	}
//	
//	@Test
//	public void testCreateNewUserWithTwoRole() {
//		User userDuong = new User("duong@gmail.com", "duong12", "Duong", "Nguyen Sy");
//		
//		Role roleEditor = new Role(3);
//		Role roleAssistiant = new Role(5);
//		
//		userDuong.addRole(roleAssistiant);
//		userDuong.addRole(roleEditor);
//		
//		User savedUser = repo.save(userDuong);
//		
//		assertThat(savedUser.getId()).isGreaterThan(0);
//		
//	}
//	
//	@Test
//	public void testListAllUsers() {
//		Iterable<User> listUsers = repo.findAll();
//		listUsers.forEach(user->System.out.print(user));
//		//assertThat(listUsers.)
//		
//	}
//	
//	@Test
//	public void testGetUserByEmail() {
//		String email="hieu@gmail.com";
//		User user = repo.getUserByEmail(email);
//		
//		assertThat(user).isNotNull();
//	}
//	
//	@Test
//	public void testCountById() {
//		Long id = 1L;
//		Long countById = repo.countById(id);
//		
//		assertThat(countById).isGreaterThan(0);
//	}
//	
	@Test
	public void testDisableUser() {
		Long id = 3L;
		
		repo.updateEnabledStatus(id, true);
		}
	
	
}
