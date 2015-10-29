package org.avajadi.finance.tracker.service;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.avajadi.finance.tracker.Transaction;
import org.avajadi.finance.tracker.repository.TransactionRepository;

@Stateless
@ApplicationPath("/")
public class TransactionService {

	@Inject
	private TransactionRepository transactionRepository;
	@GET
	@Path("/transaction/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get( @PathParam("id") int id ) {
		Transaction transaction = transactionRepository.get(id);
		return Response.ok(transaction).build();
	}
	
	@GET
	@Path("/transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<Transaction> transactions = transactionRepository.getAll();
		return Response.ok(transactions).build();
	}
}
