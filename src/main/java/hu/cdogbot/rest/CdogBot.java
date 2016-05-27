package hu.cdogbot.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/cdogbot")
public class CdogBot {

	@GET
	public String dummy() {
		return "dummy";
	}
}
