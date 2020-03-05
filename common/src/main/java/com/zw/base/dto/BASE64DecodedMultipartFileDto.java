package com.zw.entity.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BASE64DecodedMultipartFileDto implements MultipartFile {
    private final byte[] content;
    private final String name;
    private final InputStream inputStream;
    public BASE64DecodedMultipartFileDto(final byte[] content, final String name, final InputStream inputStream) {
        this.content = content;
        this.name = name;
        this.inputStream = inputStream;
    }

    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return this.name+ ".xlsx";
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return this.name+ ".xlsx";
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return "xlsx";
    }

    @Override
    public boolean isEmpty() {
        return content==null&& name==null;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes()  {
        return content;
    }

    @Override
    public InputStream getInputStream()  {
        return inputStream;
    }

    @Override
    public void transferTo(final File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(content);
    }
}
