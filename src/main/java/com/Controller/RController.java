package com.Controller;

import com.Service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RController {
    @GetMapping("/")
    public Set<String> listUploadedFiles() throws IOException {
        return StorageService.listFilesUsingJavaIO("c:\\FilesForJava");
    }

    @GetMapping("/{filename}")
    private byte[] displayFileContent(@PathVariable("filename") String filename) throws Exception 
        {
            byte[] buffer = new byte[0];
            try(FileInputStream fin=new FileInputStream("c:\\FilesForJava\\" + filename))
                {
                    buffer = new byte[fin.available()];
                    fin.read(buffer, 0, buffer.length);
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
            return buffer;
        }
    @GetMapping("/write/{filename}")
    private ResponseEntity<?> AddInformationToFile(@PathVariable("filename") String filename, @RequestParam("text") String text) throws Exception
    {
        try(FileOutputStream fos=new FileOutputStream("c:\\FilesForJava\\" + filename))
        {
            byte[] buffer = text.getBytes();

            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        boolean wr=true;
        return wr
                ?new ResponseEntity<>(HttpStatus.OK)
                :new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }
}
