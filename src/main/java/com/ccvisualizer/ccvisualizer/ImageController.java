package com.ccvisualizer.ccvisualizer;

import com.ccvisualizer.ccvisualizer.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.ccvisualizer.ccvisualizer.ImageUtility.OUTPUT_SVG_FILE_PATH;

@RestController
public class ImageController {

        @Autowired
         ImageService imageService;

        @GetMapping("/{userName}")
        public String getImage(@PathVariable("userName") String userName) {
                imageService.getImage(userName);
                String outPutUrl = OUTPUT_SVG_FILE_PATH + userName +".svg";
                return outPutUrl;
            }
}
