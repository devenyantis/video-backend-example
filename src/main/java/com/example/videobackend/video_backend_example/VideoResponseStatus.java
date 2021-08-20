package com.example.videobackend.video_backend_example;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface VideoResponseStatus {
    static VideoResponseStatus okWithReturnFrame(String base64Img)
    {
        return new VideoActionSucceedWithFrame(base64Img);
    }

    static VideoResponseStatus okWithInitInfo(int numOfFrames, int numOfGeneratedFrames)
    {
        return new VideoActionSucceedReturnInfo(numOfFrames, numOfGeneratedFrames);
    }

    static VideoResponseStatus failedWithMessage(String msg)
    {
        return new VideoActionFailed(msg);
    }
}

@Data
@AllArgsConstructor
class VideoActionSucceedWithFrame implements VideoResponseStatus {
    private final int message = 1;
    private String encodedImg;
}

@Data
@AllArgsConstructor
class VideoActionSucceedReturnInfo implements VideoResponseStatus {
    private final int message = 1;
    private int videoFramePerSecond;
    private int numOfGeneratedFrames;
}

@Data
@AllArgsConstructor
class VideoActionFailed implements VideoResponseStatus {
    private final int message = 0;

    private final int errorCode = 1;

    private final String errorMessage;
}
