package com.studi.testblob;

import com.studi.testblob.BlobStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController()
public class BlobStorageController {

    @Autowired
    BlobStorageService blobStorageService;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file")MultipartFile file) {
        URI uri = blobStorageService.upload(file);
        return ResponseEntity.ok(uri);
    }

    @GetMapping("blobs")
    public ResponseEntity getAllBlobs(@RequestParam String containerName) {
        List uris = blobStorageService.listBlobs(containerName);
        return ResponseEntity.ok(uris);
    }

    @PostMapping("/container")
    public ResponseEntity createContainer(@RequestBody String containerName) {
        boolean created = blobStorageService.createContainer(containerName);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/blob")
    public ResponseEntity deleteBlob(@RequestParam String containerName, @RequestParam String blobName) {
        blobStorageService.deleteBlob(containerName, blobName);
        return ResponseEntity.ok().build();
    }
}
