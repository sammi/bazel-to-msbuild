package com.tuware.msbuild.cli;

import com.tuware.msbuild.feature.CppProjectFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.tuware.msbuild.contract",
    "com.tuware.msbuild.feature",
    "com.tuware.msbuild.adapter",
})
public class BazelToMsbuildApplication implements CommandLineRunner {

    @Autowired
    private CppProjectFeature cppProjectFeature;

    public static void main(String[] args) {
        SpringApplication.run(BazelToMsbuildApplication.class, args);
    }

    @Override
    public void run(String... args) {
        assert(cppProjectFeature != null);
    }

}
