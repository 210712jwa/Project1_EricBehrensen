package com.revature.service;

import java.util.List;

import com.revature.dao.ReimbursementDAO;
import com.revature.model.Reimbursement;

public class ReimbursementService {
	private ReimbursementDAO reimbursementDao;
	
	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
	}
	
	public List<Reimbursement> getAllReimbursementsFromUserId(String userId) {
		int id = Integer.parseInt(userId);
		
		List<Reimbursement> reimbursements = reimbursementDao.getAllReimbursementsFromUserId(id);
		
		return reimbursements;
	}
}
