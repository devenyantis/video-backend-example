package com.example.videobackend.video_backend_example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestVideoObject {
    @Test
    void testVideoObjectCreation() {
        VideoObject videoObject = new VideoObject();
        assertTrue(videoObject.initVideoObject("C:\\Users\\Deven.Yantis\\Documents\\example_videos\\vid2.mp4"));
    }

    @Test
    void testVideoObjectGenerateFramesAndSave() {
        VideoObject videoObject = new VideoObject();
        videoObject.setSaveFramesToFolder(true);
        assertTrue(videoObject.initVideoObject("C:\\Users\\Deven.Yantis\\Documents\\example_videos\\vid2.mp4"));
    }
}
