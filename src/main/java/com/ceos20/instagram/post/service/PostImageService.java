package com.ceos20.instagram.post.service;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import com.ceos20.instagram.post.dto.PostImageDto;
import com.ceos20.instagram.post.repository.PostImageRepository;
import com.ceos20.instagram.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostImageService {
    private PostRepository postRepository;
    private PostImageRepository postImageRepository;


    public void uploadImage(Post post, List<MultipartFile> images) {
        try {
            String uploadsDir = "src/main/resources/static/uploads/images/";

            for (MultipartFile image : images) {
                String dbFilePath = saveImage(image, uploadsDir);

                PostImage postImage = PostImage.builder()
                        .post(post)
                        .path(dbFilePath)
                        .build();

                postImageRepository.save(postImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String saveImage(MultipartFile image, String uploadsDir) throws IOException {
        String imageName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();

        String imagePath = uploadsDir + imageName;

        String dbFilePath = "uploads/images/" + imageName;

        Path path = Paths.get(imagePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        return dbFilePath;
    }
}
