/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.gradebook.ssikka.resources;

import com.mycompany.crud.gradebook.ssikka.converter.Converter;
import com.mycompany.crud.gradebook.ssikka.model.GradeBook;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author SAHIL
 */
@Path("GradeBook")
public class GradeBookResource {
     private static final Logger LOG = LoggerFactory.getLogger(GradeBookResource.class);
    
    //private  GradeBook gradebook;
    public  static ArrayList<GradeBook> studentList=new ArrayList<>();
    
    @Context
    public UriInfo context;
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response createGrades(String content) throws JAXBException {
    
        int found=0;
        Response response;
        GradeBook gradebook=null;                  
            try {
                gradebook = (GradeBook) Converter.convertFromXmlToObject(content, GradeBook.class);
                if(gradebook.getStudentName().isEmpty()==true || gradebook.getworkItem().isEmpty()==true)
                    response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
                else{
                for(int i=0;i<studentList.size();i++){
                    if(studentList.get(i).getStudentName().equalsIgnoreCase(gradebook.getStudentName()) &&
                    studentList.get(i).getworkItem().equalsIgnoreCase(gradebook.getworkItem()) ){
                        found=1;
                        break;
                    }
                }
                
                if(found==0){
   
                int randomId=0;
                Random r=new Random();
                int taskId = Math.abs(r.nextInt(1000));
                while(randomId==0){
                    for(int i=0;i<studentList.size();i++){
                        if(studentList.get(i).getTaskId()==taskId){
                           taskId = Math.abs(r.nextInt(1000));                           
                        }
                    }
                    randomId=1;
                }
                gradebook.setTaskId(taskId);
                studentList.add(gradebook);
                
                String xmlString = Converter.convertFromObjectToXml(gradebook, GradeBook.class);
                
                URI locationURI = URI.create(context.getAbsolutePath()+"/"+Integer.toString(gradebook.getTaskId())+"/"+gradebook.getworkItem());
                response = Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
                }
                else
                {
                    response = Response.status(Response.Status.CONFLICT).entity(content).build();
                }
            }}
            catch (JAXBException e) {                                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            } catch (RuntimeException e) {
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
               
        
        return response;
    }
     
    @GET
    @Path("{id}/{workItem}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getResource(@PathParam("id") String id,@PathParam("workItem") String workItem) {
        
        Response response;
        GradeBook gradebook=null;
        for(int i=0;i<studentList.size();i++){
            if(studentList.get(i).getTaskId()==Integer.parseInt(id) && studentList.get(i).getworkItem().equalsIgnoreCase(workItem))
                gradebook=studentList.get(i);
        }
        
        if (studentList.size() == 0){           
            response = Response.status(Response.Status.GONE).entity("No Resource available").build();
        } else {            
            if (gradebook!=null && gradebook.getTaskId()== Integer.parseInt(id)){
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                
                String xmlString = Converter.convertFromObjectToXml(gradebook, GradeBook.class);
                
                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                
                response = Response.status(Response.Status.NOT_FOUND).entity("Resource not found").build();
            }
        }        
        return response;
    }


@PUT
    @Path("{id}/{workItem}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateResource(@PathParam("id") String id, @PathParam("workItem") String workItem,String content) {
       
        Response response;
       GradeBook gradebook=null;
        for(int i=0;i<studentList.size();i++){
            if(studentList.get(i).getTaskId()==Integer.parseInt(id) && studentList.get(i).getworkItem().equalsIgnoreCase(workItem))
            {
                gradebook=studentList.get(i);
                break;
            }
        }
        if (gradebook != null){                    
            try {
                
                GradeBook gradebook1 = (GradeBook) Converter.convertFromXmlToObject(content, GradeBook.class);
                GradeBook gradebook2=gradebook;
                
                if(gradebook1.getStudentName().isEmpty() || gradebook1.getworkItem().isEmpty())
                {response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
                return response;
                }
                    if(gradebook1.getAppeal() && !gradebook.getAppeal()){
                        gradebook.setAppeal(true);
                        String xmlString = Converter.convertFromObjectToXml(gradebook, GradeBook.class);
                        response = Response.status(Response.Status.OK).entity(xmlString).build();                
                        return response;
                    }
                    else if(gradebook1.getAppeal() && gradebook.getAppeal()){
                        String xmlString = Converter.convertFromObjectToXml(gradebook, GradeBook.class);
                        response = Response.status(Response.Status.OK).entity(xmlString).build();                
                        return response;
                    }
                    else if(!gradebook1.getAppeal() && gradebook.getAppeal()){
                        gradebook1.setAppeal(true);
                        studentList.remove(gradebook);
                        studentList.add(gradebook1);
                        String xmlString = Converter.convertFromObjectToXml(gradebook1, GradeBook.class);
                        response = Response.status(Response.Status.OK).entity(xmlString).build();                
                        return response;
                    }
                    
                    studentList.remove(gradebook);
                    studentList.add(gradebook1);
                    String xmlString = Converter.convertFromObjectToXml(gradebook1, GradeBook.class);
                    response = Response.status(Response.Status.OK).entity(xmlString).build();                
//                    return response;}
//                    else{
//                      if(gradebook.getAppeal()==false && gradebook.getAppeal()==true){
//                          gradebook.setAppeal(true);
//                        studentList.remove(gradebook2);
//                        studentList.add(gradebook);
//                        String xmlString = Converter.convertFromObjectToXml(gradebook, GradeBook.class);
//                    response = Response.status(Response.Status.OK).entity(xmlString).build();
//                    return response;
                            
//                    }
//                    }
//                
//                
//                      
//                String xmlString = Converter.convertFromObjectToXml(gradebook1, GradeBook.class);
//                response = Response.status(Response.Status.OK).entity(content).build();
                
            } catch (JAXBException e) {
                //LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());                                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            } catch (RuntimeException e) {                                
                
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        } else {
            
            response = Response.status(Response.Status.CONFLICT).entity(content).build();
        }

        return response;
    }   
    
    @DELETE
    @Path("{id}/{workItem}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteResource(@PathParam("id") String id,@PathParam("workItem") String workItem) {

        LOG.debug("DELETE request");
        LOG.debug("PathParam id = {}", id);
        GradeBook gradebook=null;
        Response response;
        for(int i=0;i<studentList.size();i++){
            
            if(studentList.get(i).getTaskId()==Integer.parseInt(id) && studentList.get(i).getworkItem().equalsIgnoreCase(workItem))
            {
                gradebook=studentList.get(i);
                break;
            }
        }
        
        if (studentList==null || studentList.size() == 0){            
            response = Response.status(Response.Status.GONE).build();
        } else {
            if (gradebook!=null && gradebook.getTaskId() == Integer.parseInt(id)&& gradebook.getworkItem().equalsIgnoreCase(workItem)){                
                studentList.remove(gradebook);                
                response = Response.status(Response.Status.NO_CONTENT).build();
            } else {                
                response = Response.status(Response.Status.NOT_FOUND).build();
            }
        }        
                
        return response;
    }
}
