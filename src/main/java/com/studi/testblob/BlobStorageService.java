package com.studi.testblob;

import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.studi.testblob.AzureStorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlobStorageService {

    @Autowired
    AzureStorageConfig azureStorageConfig;

    public URI upload (MultipartFile file) {
        URI uri = null;
        CloudBlockBlob blob = null;
        try {
            blob = azureStorageConfig.cloudBlobContainer().getBlockBlobReference(file.getOriginalFilename());
            blob.upload(file.getInputStream(), -1);
            uri = blob.getUri();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }

    public List listBlobs(String containerName) {
        List uris = new ArrayList<>();
        CloudBlobContainer container = null;
        try {
            container = azureStorageConfig.cloudBlobClient().getContainerReference(containerName);
            for(ListBlobItem blobItem : container.listBlobs()) {
                uris.add(blobItem.getUri());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return uris;
    }

    public void deleteBlob (String containerName, String blobName) {
        try {
            CloudBlobContainer container = azureStorageConfig.cloudBlobClient().getContainerReference(containerName);
            CloudBlockBlob blobToBeDeleted = container.getBlockBlobReference(blobName);
            blobToBeDeleted.deleteIfExists();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (StorageException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createContainer(String containerName) {
        boolean containerCreated = false;

        try {
            CloudBlobContainer container = azureStorageConfig.cloudBlobClient().getContainerReference(containerName);
            containerCreated = container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (StorageException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return containerCreated;
    }
}
