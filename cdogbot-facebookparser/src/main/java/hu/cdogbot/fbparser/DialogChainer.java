package hu.cdogbot.fbparser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.cdogbot.fbparser.db.PostgresDb;
import hu.cdogbot.fbparser.model.FbMessage;
import hu.cdogbot.fbparser.model.FbThread;
import hu.cdogbot.fbparser.model.IdSequence;

public class DialogChainer {
	
	private static final Logger log = LoggerFactory.getLogger(DialogChainer.class);
	
	private final FbThread thread;
	private final PostgresDb db;
	private final IdSequence seq;
	
	public DialogChainer(IdSequence seq, FbThread thread,PostgresDb db) {
		this.thread = thread;
		this.db = db;
		this.seq = seq;
		log.info("Parsing thread {}", thread.getParties());
		
	}

	public void persistRequestReply() throws SQLException {
		Iterator<FbMessage> it = thread.iterator();
		
		List<FbMessage> groupedMessages = groupMessages(it);
		if(!groupedMessages.isEmpty()) {
			List<FbMessage> chainedMessages = chainMessages(groupedMessages);
			//reverse insert chain messages because of foreign key constraints
			for (int i = chainedMessages.size() - 1; i>=0; i--) {
				db.save(chainedMessages.get(i));
			}
		}		
	}
	
	private List<FbMessage> chainMessages(List<FbMessage> messages) {
		Iterator<FbMessage> it = messages.iterator();
		FbMessage prev = it.next();
		prev.setId(seq.next());
		
		while(it.hasNext()) {
			FbMessage current = it.next();
			current.setId(seq.next());
			prev.setNextMessageId(current.getId());
			
			prev = current;
		}
		
		return messages;
	}
	
	private List<FbMessage> groupMessages(Iterator<FbMessage> it) {
		List<FbMessage> partner = new ArrayList<>();
		List<FbMessage> me = new ArrayList<>();
		List<FbMessage> grouped = new LinkedList<>();
		
		//find first who is not me
		while(it.hasNext() && partner.isEmpty()) {
			FbMessage msg = it.next();
			if(!msg.getSender().equals(Config.ME)) {
				partner.add(msg);
			}
		}
		
		//continue with response search or group message from the partner
		while(it.hasNext()) {
			FbMessage msg = it.next();
			//i said something
			if(msg.getSender().equals(Config.ME)) {
				if(!me.isEmpty()) {
					me.add(msg);
				} else {
					grouped.add(groupMessages(partner));
					partner.clear();
					me.add(msg);
				}
			} else {
				//they said something
				if(!partner.isEmpty()) {
					partner.add(msg);
				} else {
					grouped.add(groupMessages(me));
					me.clear();
					partner.add(msg);
				}	
			}
		}
		
		//save my last grouped without next message
		if(!me.isEmpty()) {
			grouped.add(groupMessages(me));
		}
		
		return grouped;
	}

	
	private FbMessage groupMessages(List<FbMessage> groupedMessages) {
		StringBuilder builder = groupedMessages.stream().collect(StringBuilder::new, (bldr, str) -> bldr.append(str.getMessage()+" "), (b1,b2) -> b1.append(b2.toString()));
		FbMessage grouped = new FbMessage(groupedMessages.get(0).getSender(), groupedMessages.get(0).getTimestamp(), builder.toString());
		grouped.setId(groupedMessages.get(0).getId());
		
		return grouped;
	}
	
	private boolean isMessageValid(FbMessage message) {
		return message != null && !message.getMessage().isEmpty();
	}
	

}
