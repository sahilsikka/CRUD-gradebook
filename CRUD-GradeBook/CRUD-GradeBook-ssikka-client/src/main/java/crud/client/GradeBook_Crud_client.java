/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author SAHIL
 */
public class GradeBook_Crud_client {
    
     private static final Logger LOG = LoggerFactory.getLogger(GradeBook_Crud_client.class);
    
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/CRUD-GradeBook-ssikka/webresources";

    public GradeBook_Crud_client() {        
        //LOG.info("Creating a Appointment_CRUD REST client");

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("GradeBook");
        LOG.debug("webResource = {}", webResource.getURI());
    }
    
    public ClientResponse createGrades(Object requestEntity) throws UniformInterfaceException {
        LOG.info("Initiating a Create request");
        LOG.debug("XML String = {}", (String) requestEntity);
        ClientResponse resp=webResource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);;
        LOG.debug("rsponse after server ",resp.getLocation());
        return resp;
    }

    
    public <T> T readGrades(Class<T> responseType, String id,String workItem) throws UniformInterfaceException {
        LOG.info("Initiating a Retrieve request");
        LOG.debug("responseType = {}", responseType.getClass());
        LOG.debug("Id = {}", id);        
        return webResource.path(id+"/"+workItem).accept(MediaType.APPLICATION_XML).get(responseType);
    }
    public ClientResponse updateGrades(Object requestEntity, String id,String workItem) throws UniformInterfaceException {
        LOG.info("Initiating an Update request");
        LOG.debug("XML String = {}", (String) requestEntity);
        LOG.debug("Id = {}", id);
        
        return webResource.path(id+"/"+workItem).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }
    
    public ClientResponse updateAppeal(Object requestEntity, String id,String workItem) throws UniformInterfaceException {
        LOG.info("Initiating an Update request");
        LOG.debug("XML String = {}", (String) requestEntity);
        LOG.debug("Id = {}", id);
        
        return webResource.path(id+"/"+workItem).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }
    
    
    public ClientResponse deleteGrades(String id,String workItem) throws UniformInterfaceException {
        LOG.info("Initiating a Delete request");
        LOG.debug("Id = {}", id);
        
        return webResource.path(id+"/"+workItem).delete(ClientResponse.class);
    }
}
