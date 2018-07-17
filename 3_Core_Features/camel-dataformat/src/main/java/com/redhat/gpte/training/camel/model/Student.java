package com.redhat.gpte.training.camel.model;

import com.redhat.gpte.training.camel.MyBean;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.util.Date;

@CsvRecord( separator = "," )
public class Student {

    @DataField(pos = 1, position = 1) private String name;
    @DataField(pos = 2, position = 2) private String course;
    @DataField(pos = 3, position = 3) private Integer code;
    @DataField(pos = 5, required = false, position = 5) private String status;
    @DataField(pos = 6, required = false, position = 6) private String room;
    @DataField(pos = 4, required = false, position = 4, pattern = "dd-MM-yyyy") private Date creationDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
