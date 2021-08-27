package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.revature.model.Reimbursement;
import com.revature.util.SessionFactorySingleton;

public class ReimbursementDAO {
	public List<Reimbursement> getAllReimbursementsFromUserId(int id) {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		// Get reimbursements by owner id
		List<Reimbursement> reimbursement = session.createQuery("SELECT r FROM Reimbursement r JOIN r.owner u WHERE u.id = :userid").setParameter("userid", id).getResultList();
	
		return reimbursement;
	}
	
	public List<Reimbursement> addReimbursement(int id) {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		// Add reimbursement by owner id
		session.createQuery("INSERT INTO Reimbursement (...) VALUES (?)").executeUpdate();
		
		List<Reimbursement> reimbursement = session.createQuery("SELECT r FROM Reimbursement r JOIN r.owner u WHERE u.id = :userid").setParameter("userid", id).getResultList();
	
		return reimbursement;
	}
	
	public List<Reimbursement> approveOrDenyReimbursement(int userid, int reimbursementid) {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		// Approve or deny reimbursement by resolver id
		session.createQuery("UPDATE ReimbursementStatus SET status = (?) WHERE id = (?)").executeUpdate();
		
		List<Reimbursement> reimbursement = session.createQuery("SELECT r FROM Reimbursement r JOIN r.owner u WHERE u.id = :userid").setParameter("userid", userid).getResultList();
	
		return reimbursement;
	}
}
