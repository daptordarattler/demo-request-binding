package com.sbdemo

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class BodyCaptureInterceptorSpec extends Specification implements InterceptorUnitTest<BodyCaptureInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test bodyCapture interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"bodyCapture")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
