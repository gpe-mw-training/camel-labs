package com.redhat.gpe.training.camel;

import java.util.Random;

import org.apache.camel.Body;

import com.redhat.gpe.training.camel.model.Student;

public class MyBean {

    public Student process(@Body Student student) throws Exception {
        student.setStatus(Status.getRandomStatus().toString());
        student.setRoom("jboss-fuse-0123");
        return student;
    }

    enum Status {
        Registered,
        Follow,
        Certified,
        Canceled;

        public static Status getRandomStatus() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }

    }

}
