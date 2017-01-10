package com.redhat.gpe.training.camel;

import java.util.HashMap;
import java.util.List;

import org.apache.camel.Body;
import com.redhat.gpe.training.camel.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBean {

    private static final Logger logger = LoggerFactory.getLogger(MyBean.class);

    public Student process(@Body List<?> list) throws Exception {

        // List<?> list = (List<?>) exchange.getIn().getBody();
        HashMap<String, Object> map = (HashMap<String, Object>) list.get(0);
        String modelKey = Student.class.getName();
        Student student = (Student) map.get(modelKey);

 //       student.setStatus("true");
        student.setRoom("jboss-fuse-0123");

        return student;

        // exchange.getIn().setBody(student);
    }

}
