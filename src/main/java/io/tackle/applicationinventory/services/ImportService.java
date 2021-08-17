package io.tackle.applicationinventory.services;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.tackle.applicationinventory.MultipartImportBody;
import io.tackle.applicationinventory.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static javax.transaction.Transactional.TxType.REQUIRED;

@Path("/file")
@ApplicationScoped
public class ImportService {

    public static final String COMPLETED_STATUS = "Completed";
    public static final String IN_PROGRESS_STATUS = "In Progress";
    public static final String FAILED_STATUS = "Failed";



    @Inject
    @RestClient
    TagService tagService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional(REQUIRED)
    public Response importFile(@MultipartForm MultipartImportBody data) {

        try {

            Set<Tag> tags = tagService.getListOfTags(0, 1000);
            if (tags == null)
            {
                String msg = "Unable to retrieve Tags from remote resource";
                throw new Exception(msg);
            }

            tags.forEach(tag -> System.out.println("tag: " + tag.name + ", tagType: " + tag.tagType.name));




        } catch (Exception e) {

            e.printStackTrace();
            return Response.serverError().build();

        }
            return Response.ok().build();



    }







}
