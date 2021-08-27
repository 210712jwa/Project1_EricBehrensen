package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.model.User;
import com.revature.model.UserRole;

public class PopulateDataInDatabase {

	public static void main(String[] args) {
		populateReimbursementStatus_ReimbursementType_UserRole();
		addSampleUsers();
		addReimbursements();
	}
	
	private static void populateReimbursementStatus_ReimbursementType_UserRole() {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		UserRole admin = new UserRole("admin");
		UserRole user = new UserRole("user");
		session.persist(admin);
		session.persist(user);
		
		ReimbursementStatus pending = new ReimbursementStatus("pending");
		ReimbursementStatus approved = new ReimbursementStatus("approved");
		ReimbursementStatus denied = new ReimbursementStatus("denied");
		session.persist(pending);
		session.persist(approved);
		session.persist(denied);
		
		ReimbursementType lodging = new ReimbursementType("lodging");
		ReimbursementType travel = new ReimbursementType("travel");
		ReimbursementType food = new ReimbursementType("food");
		ReimbursementType other = new ReimbursementType("other");
		session.persist(lodging);
		session.persist(travel);
		session.persist(food);
		session.persist(other);
		
		tx.commit();
		session.close();
	}

	private static void addSampleUsers() {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		User adminUser1 = new User("Eric", "Behrensen", "eric.behrensen@revature.net", "eric", "12345");
		UserRole admin = (UserRole) session.createQuery("FROM UserRole ur WHERE ur.role = 'admin'").getSingleResult();
		adminUser1.setUserRole(admin);
		session.persist(adminUser1);
		
		UserRole user = (UserRole) session.createQuery("FROM UserRole ur WHERE ur.role = 'user'").getSingleResult();
		User regularUser1 = new User("Test1", "testing", "test@test.com", "test12345", "12345");
		regularUser1.setUserRole(user);
		User regularUser2 = new User("John", "Doe", "johndoe@email.com", "johnny123", "password123");
		regularUser2.setUserRole(user);
		
		session.persist(regularUser1);
		session.persist(regularUser2);
		
		tx.commit();
		session.close();
	}
	
	private static void addReimbursements() {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		User eric = (User) session.createQuery("FROM User u WHERE u.username = 'eric'").getSingleResult();
		ReimbursementStatus pending = (ReimbursementStatus) session.createQuery("FROM ReimbursementStatus r WHERE r.status = 'pending'").getSingleResult();
		
		ReimbursementType lodging = (ReimbursementType) session.createQuery("FROM ReimbursementType r WHERE r.type = 'lodging'").getSingleResult();
		ReimbursementType travel = (ReimbursementType) session.createQuery("FROM ReimbursementType r WHERE r.type = 'travel'").getSingleResult();
		ReimbursementType food = (ReimbursementType) session.createQuery("FROM ReimbursementType r WHERE r.type = 'food'").getSingleResult();
		ReimbursementType other = (ReimbursementType) session.createQuery("FROM ReimbursementType r WHERE r.type = 'other'").getSingleResult();
		
		Reimbursement reimbursement1 = new Reimbursement();
		reimbursement1.setOwner(eric);
		reimbursement1.setStatus(pending);
		reimbursement1.setType(lodging);
		reimbursement1.setAmount(13454);
		
		Reimbursement reimbursement2 = new Reimbursement();
		reimbursement2.setOwner(eric);
		reimbursement2.setStatus(pending);
		reimbursement2.setType(travel);
		reimbursement2.setAmount(9472);
		
		Reimbursement reimbursement3 = new Reimbursement();
		reimbursement3.setOwner(eric);
		reimbursement3.setStatus(pending);
		reimbursement3.setType(food);
		reimbursement3.setAmount(910);
		
		Reimbursement reimbursement4 = new Reimbursement();
		reimbursement4.setOwner(eric);
		reimbursement4.setStatus(pending);
		reimbursement4.setType(other);
		reimbursement4.setAmount(9127);
		
		session.persist(reimbursement1);
		session.persist(reimbursement2);
		session.persist(reimbursement3);
		session.persist(reimbursement4);
		
		tx.commit();
		session.close();
	}
}
