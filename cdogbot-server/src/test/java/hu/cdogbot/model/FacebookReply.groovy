package hu.cdogbot.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

/**
 * Created by cdog on 2016.06.09..
 */
class FacebookReplySpec extends Specification {

    def "can be serialized"() {
        def m = new ObjectMapper()
        given:
        def obj = new FacebookReply("id", "anyadat mukodj")
        when:
        def json = m.writeValueAsString(obj)
        then:
        !json.isEmpty()
    }
}
