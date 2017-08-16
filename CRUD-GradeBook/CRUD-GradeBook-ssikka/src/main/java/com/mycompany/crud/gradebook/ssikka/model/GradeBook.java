/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.gradebook.ssikka.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author SAHIL
 */
@XmlRootElement
@XmlType(propOrder={
    "taskId",    
    "studentName",
    "workItem",
    "grades",
    "feedBack",
    "appeal"})
public class GradeBook {
    
    int taskId;
    String studentName;
    String workItem;
    String grades;
    String feedBack;
    boolean appeal;

    public int getTaskId() {
        return taskId;
    }
    @XmlAttribute
    public void setTaskId(int studentId) {
        this.taskId = studentId;
    }
    
    public String getStudentName(){
        return studentName;
    }
    @XmlElement
    public void setStudentName(String studentName){
        this.studentName=studentName;
    }
    public String getworkItem() {
        return workItem;
    }
    
    @XmlElement
    public void setAppeal(Boolean appeal){
        this.appeal=appeal;
    }
    public Boolean getAppeal() {
        return appeal;
    }
    @XmlElement
    public void setworkItem(String workItem) {
        this.workItem = workItem;
    }

    public String getGrades() {
        return grades;
    }
    @XmlElement
    public void setGrades(String grades) {
        this.grades = grades;
    }
    
     public String getfeedBack() {
        return feedBack;
    }
    @XmlElement
    public void setfeedBack(String feedBack) {
        this.feedBack = feedBack;
    }
    
}
