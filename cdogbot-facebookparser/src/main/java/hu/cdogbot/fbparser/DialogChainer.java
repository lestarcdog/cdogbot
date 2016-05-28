package hu.cdogbot.fbparser;

import java.util.ArrayList;
import java.util.Iterator;
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

	public void persistRequestReply() {
		Iterator<FbMessage> it = thread.iterator();
		
		List<FbMessage> partner = new ArrayList<>();
		List<FbMessage> me = new ArrayList<>();
		
		//find first who is not me
		while(it.hasNext() && partner.isEmpty()) {
			FbMessage msg = it.next();
			if(!msg.getSender().equals(Config.ME)) {
				msg.setId(seq.next());
				partner.add(msg);
			}
		}
		
		//continue with response search or group message from the partner
		while(it.hasNext()) {
			FbMessage msg = it.next();
			//i said something
			if(msg.getSender().equals(Config.ME)) {
				nextMessageCurrentGroup(me,partner,msg);
			} else {
				//they said something
				nextMessageCurrentGroup(partner,me,msg);
			}
		}
	}
	
	private void nextMessageCurrentGroup(List<FbMessage> currentGroup,List<FbMessage> otherGroup,FbMessage currentMessage) {
		//they said something
		if(!currentGroup.isEmpty()) {
			currentMessage.setId(currentGroup.get(0).getId());
			currentGroup.add(currentMessage);
		} else {
			//dialog change
			FbMessage grouped = groupMessages(otherGroup);
			
			chainMessages(grouped, currentMessage);
			currentGroup.add(currentMessage);
			db.save(grouped);
			otherGroup.clear();

		}
	}
	
	private void chainMessages(FbMessage prev, FbMessage next) {
		//set up ids
		next.setId(seq.next());
		prev.setNextMessageId(next.getId());
	}
	
	private FbMessage groupMessages(List<FbMessage> groupedMessages) {
		StringBuilder builder = groupedMessages.stream().collect(StringBuilder::new, (bldr, str) -> bldr.append(str.getMessage()+" "), (b1,b2) -> b1.append(b2.toString()));
		FbMessage grouped = new FbMessage(groupedMessages.get(0).getSender(), groupedMessages.get(0).getTimestamp(), builder.toString());
		grouped.setId(groupedMessages.get(0).getId());
		
		return grouped;
	}

}
