package com.example.loggerapi.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MyHttpServletReponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream bao;
    private ResettableServletOutputStream servletOutput;

    public MyHttpServletReponseWrapper(HttpServletResponse response) {
        super(response);
        response.setCharacterEncoding("UTF8");
        bao = new ByteArrayOutputStream();
        servletOutput = new ResettableServletOutputStream();
        servletOutput.output = bao;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutput;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(bao);
    }

    public byte[] getAllByteInReponse() {
        return bao.toByteArray();
    }

    public int size() {
        return bao.size();
    }

    private class ResettableServletOutputStream extends ServletOutputStream {
        private OutputStream output;

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        @Override
        public void write(int b) throws IOException {
            output.write(b);
        }

    }

}