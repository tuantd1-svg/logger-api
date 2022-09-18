package com.example.loggerapi.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private ByteArrayOutputStream baoData;
    private HttpServletRequest request;
    private ResettableServletInputStream servletInput;

    public MyHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
        this.baoData = new ByteArrayOutputStream(1024);
        this.servletInput = new ResettableServletInputStream();

        DataInputStream in;
        try {
            in = new DataInputStream(request.getInputStream());
            byte[] buffer = new byte[4096];
            int nRead = -1;
            long totalByteRead = 0;

            while (totalByteRead < 4096 && (nRead = in.read(buffer)) != -1) {
                totalByteRead += nRead;
                baoData.write(buffer, 0, nRead);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        servletInput.stream =  new SequenceInputStream(new ByteArrayInputStream(baoData.toByteArray()), request.getInputStream());
        return servletInput;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (baoData.size() == 0) {
            DataInputStream in = new DataInputStream(request.getInputStream());
            byte[] buffer = new byte[4096];
            int nRead = -1;
            long totalByteRead = 0;

            while (totalByteRead < 4096 && (nRead = in.read(buffer)) != -1) {
                baoData.write(buffer, 0, nRead);
            }
        }
        servletInput.stream = new SequenceInputStream(new ByteArrayInputStream(baoData.toByteArray()), request.getInputStream());
        return new BufferedReader(new InputStreamReader(servletInput));
    }

    public byte[] getAllByteInRequest() {
        return baoData.toByteArray();
    }

    private class ResettableServletInputStream extends ServletInputStream {

        private InputStream stream;

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }
    }
}