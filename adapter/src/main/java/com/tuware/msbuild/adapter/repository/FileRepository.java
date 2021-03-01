package com.tuware.msbuild.adapter.repository;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRepository implements Repository<Path, String> {

    @Override
    public void save(Path identifier, String content) throws AdapterException {
        try {
            Files.write(identifier, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new AdapterException("Failed to save file:" + identifier, e);
        }
    }
}
