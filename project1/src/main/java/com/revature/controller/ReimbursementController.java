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
				ctx.json(new MessageDTO("You are not the user that you want to retrieve all ships from"));
				ctx.status(401);
			}
		}
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/user/:userid/reimbursement", getAllReimbursementsBelongingToSpecificUser);
	}

}
