package com.tuware.msbuild.adapter.repository;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRepository implements Repository<Path, String> {

    @Override
    public void save(Path absoluteFilePath, String utf8Text) throws AdapterException {
        try {
            Files.write(absoluteFilePath, utf8Text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new AdapterException("Failed to save file:" + absoluteFilePath, e);
        }
    }
}
