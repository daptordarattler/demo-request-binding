package com.sbdemo

import javax.servlet.http.*
import javax.servlet.*
import java.io.*

class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedBody

    CachedBodyHttpServletRequest(HttpServletRequest request, String body) {
        super(request)
        this.cachedBody = body.getBytes("UTF-8")
    }

    @Override
    ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody)
        return new ServletInputStream() {
            @Override
            boolean isFinished() { return byteArrayInputStream.available() == 0 }

            @Override
            boolean isReady() { return true }

            @Override
            void setReadListener(ReadListener readListener) { }

            @Override
            int read() throws IOException {
                return byteArrayInputStream.read()
            }
        }
    }

    @Override
    BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.inputStream))
    }
}