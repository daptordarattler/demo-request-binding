package com.sbdemo


class BodyCaptureInterceptor {

    BodyCaptureInterceptor() {
        match(controller: "person")
    }

    boolean before() {
        return true
    }

    boolean after() {

        if (request.contentType?.contains("application/json")) {
            // Log parsed JSON (doesn't break data binding)
            log.info "Request JSON: ${request.JSON}"
        } else if (request.contentType?.contains("application/x-www-form-urlencoded")) {
            log.info "Request params: ${params}"
        }
        true
    }

    void afterView() {
        // no-op
    }
}
