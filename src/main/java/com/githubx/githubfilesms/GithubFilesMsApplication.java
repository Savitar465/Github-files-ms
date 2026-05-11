package com.githubx.githubfilesms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.githubx.githubfilesms",
    "com.smithy.g.files.server.files",
    "org.openapitools.configuration"
})
public class GithubFilesMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubFilesMsApplication.class, args);
    }

}
