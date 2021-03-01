package com.tuware.msbuild.adapter.repository

import com.google.common.jimfs.Jimfs
import spock.lang.Specification

import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path

class FileRepositorySpec extends Specification {

    def "test save text to a file"() {

        given:
        FileRepository fileRepository = new FileRepository()

        FileSystem fileSystem = Jimfs.newFileSystem()
        Path path = fileSystem.getPath("somePath")
        String utf8Text = "abc你好"

        when:
        fileRepository.save(path, utf8Text)

        then:
        String.join("", Files.readAllLines(path)) == utf8Text

    }
}
