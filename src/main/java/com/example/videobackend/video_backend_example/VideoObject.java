package com.example.videobackend.video_backend_example;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class VideoObject {

    @Getter
    private final List<String> base64Frames = new ArrayList<>();
    @Setter
    private boolean saveFrames = false;

    static {
        nu.pattern.OpenCV.loadLocally();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public boolean initVideoObject(String videoPath) {

        if(extractFrames(videoPath)) {
            log.info("Video process successful");
            return true;
        }

        log.info("Video process fail");
        return false;
    }

    private boolean extractFrames(String videoPath) {
        Mat frame = new Mat();
        int frame_number = 0;
        VideoCapture cap = new VideoCapture();
        cap.open(videoPath);

        int video_length = (int) cap.get(Videoio.CAP_PROP_FRAME_COUNT);
        int frames_per_second = (int) cap.get(Videoio.CAP_PROP_FPS);

        if(cap.isOpened()) {

            System.out.println("Video is opened");
            System.out.println("Number of Frames: " + video_length);
            System.out.println(frames_per_second + " Frames per Second");
            System.out.println("Converting Video...");
            while(cap.read(frame)) //the last frame of the movie will be invalid. check for it !
            {
                base64Frames.add(base64FromBufferedImage(toBufferedImage(frame)));
                if(saveFrames) {
                    String output = getOutputFolder(videoPath);
                    Imgcodecs.imwrite(output + "/" + frame_number +".jpg", frame);
                }

                frame_number++;
            }
            cap.release();

            return true;
        }

        System.out.println("Fail");
        return false;
    }

    private String getOutputFolder(String videoPath) {
        String parentDir = Paths.get(videoPath).toFile().getAbsoluteFile().getParentFile().getName();
        return Paths.get(parentDir, "outputs").toString();
    }

    public BufferedImage toBufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);

        return image;
    }

    private static String base64FromBufferedImage(BufferedImage img) {
        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(img, "PNG", out);
            byte[] bytes = out.toByteArray();
            String base64bytes = Base64.getEncoder().encodeToString(bytes);

            return "data:image/png;base64," + base64bytes;
        }
        catch (Exception e)
        {
            log.info("Fail to convert");
            return "";
        }
    }

}
