package com.redhat.gpe.training.camel;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.camel.Body;
import com.redhat.gpe.training.camel.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBean {

    private static final Logger logger = LoggerFactory.getLogger(MyBean.class);

    public Student process(@Body List<?> list) throws Exception {
        HashMap<String, Object> map = (HashMap<String, Object>) list.get(0);
        String modelKey = Student.class.getName();
        Student student = (Student) map.get(modelKey);

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
