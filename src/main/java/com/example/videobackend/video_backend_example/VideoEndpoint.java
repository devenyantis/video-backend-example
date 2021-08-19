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
    public ActionStatus initVideoProject() {
        videoObject = new VideoObject();
        if(videoObject.initVideoObject("C:\\Users\\Deven.Yantis\\Documents\\example_videos\\vid2.mp4")) {
            return ActionStatus.ok();
        }

        return ActionStatus.failedWithMessage("Fail to init video");
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
        return VideoResponseStatus.ok(frame);
    }
}
