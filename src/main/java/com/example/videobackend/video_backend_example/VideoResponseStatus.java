package com.example.videobackend.video_backend_example;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface VideoResponseStatus {
    static VideoResponseStatus ok(String base64Img)
    {
        return new VideoActionSucceed(base64Img);
    }

    static VideoResponseStatus failedWithMessage(String msg)
    {
        return new VideoActionFailed(msg);
    }
}

@Data
@AllArgsConstructor
class VideoActionSucceed implements VideoResponseStatus {
    private final int message = 1;
    private String encodedImg;
}

@Data
@AllArgsConstructor
class VideoActionFailed implements VideoResponseStatus {
    private final int message = 0;

    private final int errorCode = 1;

    private final String errorMessage;
}
