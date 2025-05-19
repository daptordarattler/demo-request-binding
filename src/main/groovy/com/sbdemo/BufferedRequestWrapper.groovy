package com.sbdemo

import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body

    BufferedRequestWrapper(HttpServletRequest request) {
        super(request)
        body = request.inputStream.bytes
    }

    @Override
    ServletInputStream getInputStream() {
        def bais = new ByteArrayInputStream(body)
        return new ServletInputStream() {
            @Override
            boolean isFinished() { bais.available() == 0 }

            @Override
            boolean isReady() { true }

            @Override
            void setReadListener(ReadListener listener) {}

            @Override
            int read() throws IOException {
                return bais.read()
            }
        }
    }

    @Override
    BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream(), characterEncoding ?: "UTF-8"))
    }

    String getBodyText() {
        return new String(body, characterEncoding ?: "UTF-8")
    }
}
