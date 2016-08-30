package eu.erbs.kik.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erbs.kik.rest.model.Message;

@Path("/")
public class KikRestServer {
	
	private final static Logger logger = LoggerFactory.getLogger(KikRestServer.class);

	@POST
	@Path("message")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveMessages(Collection<Message> messages)
	{
		logger.info("Received: " + messages.size() + " messages");
		
		return Response
				.status(Status.OK).build();
	}

}