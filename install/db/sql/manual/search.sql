SELECT ts_rank(c.keyword,'kutya | suli'::tsquery,1) rank, c.next_message_id responseId, resp.message response, c.message message
FROM chat c, chat resp
WHERE c.is_sender_me = FALSE -- not said by me
AND c.keyword @@ 'kutya | suli'::tsquery
AND c.next_message_id IS NOT NULL -- we have a response
AND resp.id=c.next_message_id
ORDER BY rank DESC
LIMIT 1;