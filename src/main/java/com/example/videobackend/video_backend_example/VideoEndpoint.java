package com.example.videobackend.video_backend_example;

import com.zandero.rest.annotation.RequestReader;

import javax.ws.rs.*;

@Path("video")
public class VideoEndpoint {

    private VideoObject videoObject;

    @GET()
    @Produces("application/json")
    public ActionStatus checkHealth() {
        return ActionStatus.ok();
    }

    @POST()
    @Path("/init")
    @Produces("application/json")
    @RequestReader(BodyReader.class)
    public VideoResponseStatus initVideoProject(VideoEndpointBody body) {
        videoObject = new VideoObject();
        videoObject.setSaveFramesToFolder(body.saveFrameToFolder);
        if(videoObject.initVideoObject(body.videoPath)) {
            return VideoResponseStatus.okWithInitInfo(videoObject.getFramesPerSecond(), videoObject.getNumOfGeneratedFrames());
        }

        return VideoResponseStatus.failedWithMessage("Fail to init video");
    }

    @GET()
    @Path("frame/{frameIdx}")
    @Produces("application/json")
    public VideoResponseStatus extractFrame(
            @PathParam("frameIdx") int frameIdx
    ) {

        String frame = videoObject.getBase64Frames().get(frameIdx);
        if(frame == null) {
            return VideoResponseStatus.failedWithMessage("Fail to get frame");
        }
        return VideoResponseStatus.okWithReturnFrame(frame);
    }
}
