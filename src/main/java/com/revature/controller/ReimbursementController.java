package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.dto.MessageDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ReimbursementController implements Controller {
	private ReimbursementService reimbursementService;
	
	public ReimbursementController() {
		this.reimbursementService = new ReimbursementService();
	}
	
	private Handler getAllReimbursementsBelongingToSpecificUser = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		
		if (session.getAttribute("currentUser") == null) {
			ctx.json(new MessageDTO("You need to be logged in to perform this action"));
		} else {
			User currentUser = (User) session.getAttribute("currentUser");
			
			String userId = ctx.pathParam("userid");
			
			if (currentUser.getId() == Integer.parseInt(userId)) {
				List<Reimbursement> reimbursements = reimbursementService.getAllReimbursementsFromUserId(userId);
				
				ctx.json(reimbursements);
				ctx.status(200);
			} else {
				ctx.json(new MessageDTO("You are not the user that you want to retrieve all reimbursements from"));
				ctx.status(401);
			}
		}
	};
	
	private Handler addReimbursement = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		
		if (session.getAttribute("currentUser") == null) {
			ctx.json(new MessageDTO("You need to be logged in to perform this action"));
		} else {
			User currentUser = (User) session.getAttribute("currentUser");
			
			String userId = ctx.pathParam("userid");
			
			if (currentUser.getId() == Integer.parseInt(userId)) {
				List<Reimbursement> reimbursements = reimbursementService.addReimbursement(userId);
				
				ctx.json(reimbursements);
				ctx.status(200);
			} else {
				ctx.json(new MessageDTO("You are not the user that you want to add a reimbursement to"));
				ctx.status(401);
			}
		}
	};
	
	private Handler approveOrDenyReimbursement = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		
		if (session.getAttribute("currentUser") == null) {
			ctx.json(new MessageDTO("You need to be logged in to perform this action"));
		} else {
			User currentUser = (User) session.getAttribute("currentUser");
			
			String userId = ctx.pathParam("userid");
			String reimbursementId = ctx.pathParam("reimbursementid");
			
			if (currentUser.getId() == Integer.parseInt(userId)) {
				List<Reimbursement> reimbursements = reimbursementService.approveOrDenyReimbursement(userId, reimbursementId);
				
				ctx.json(reimbursements);
				ctx.status(200);
			} else {
				ctx.json(new MessageDTO("You are not the user that you want to approve or deny a reimbursement with"));
				ctx.status(401);
			}
		}
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/user/:userid/reimbursement", getAllReimbursementsBelongingToSpecificUser);
		app.post("/user/:userid/reimbursement", addReimbursement);
		app.put("/user/:userid/reimbursement/:reimbursementid", approveOrDenyReimbursement);
	}

}
