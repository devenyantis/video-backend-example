package com.example.videobackend.video_backend_example;

import lombok.Data;

@Data
public class VideoEndpointBody {
    String videoPath;
    boolean saveFrameToFolder;
}
