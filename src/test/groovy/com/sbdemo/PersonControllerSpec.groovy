package com.sbdemo

import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.mixin.integration.Integration
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification

@Integration
class PersonControllerSpec extends Specification implements ControllerUnitTest<PersonController> {

    @Value('${local.server.port}')
    Integer port

    def setup() {
    }

    def cleanup() {
    }

    void "test data binding still works after BufferedRequestWrapper is used in interceptor"() {
        given:
        def client = HttpClients.createDefault()
        def post = new HttpPost("http://localhost:${port}/person")
        post.setHeader("Content-Type", "application/json")
        post.setEntity(new StringEntity('{"name":"Daniella","role":"Founder"}', "UTF-8"))

        when:
        def response = client.execute(post)
        def responseText = EntityUtils.toString(response.entity)

        then:
        response.statusLine.statusCode == 200
        responseText.contains('"name":"Daniella"')
        responseText.contains('"role":"Founder"')
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
