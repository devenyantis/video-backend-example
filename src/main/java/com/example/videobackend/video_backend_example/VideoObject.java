package com.example.videobackend.video_backend_example;

import lombok.Getter;
import lombok.Setter;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class VideoObject {

    @Getter private final List<String> base64Frames = new ArrayList<>();
    @Getter int videoLength;
    @Getter int framesPerSecond;
    @Getter int numOfGeneratedFrames = 0;

    @Setter private boolean saveFramesToFolder = false;

    static {
        nu.pattern.OpenCV.loadLocally();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public boolean initVideoObject(String videoPath) {

        if(extractFrames(videoPath)) {
            System.out.println("Video process successful");
            return true;
        }

        System.out.println("Video process fail");
        return false;
    }

    private boolean extractFrames(String videoPath) {
        VideoCapture cap = new VideoCapture();
        cap.open(videoPath);

        videoLength = (int) cap.get(Videoio.CAP_PROP_FRAME_COUNT);
        framesPerSecond = (int) cap.get(Videoio.CAP_PROP_FPS);

        if(cap.isOpened()) {

            System.out.println("Video is opened");
            System.out.println("Number of Frames: " + videoLength);
            System.out.println(framesPerSecond + " Frames per Second");
            System.out.println("Converting Video...");

            Mat frame = new Mat();
            int generatedFrames = 0;
            while(cap.read(frame)) //the last frame of the movie will be invalid. check for it !
            {
                base64Frames.add(base64FromBufferedImage(toBufferedImage(frame)));
                if(saveFramesToFolder) {
                    String output = getOutputFolder(videoPath);
                    Imgcodecs.imwrite(output + "/" + generatedFrames +".jpg", frame);
                }

                generatedFrames++;
            }
            numOfGeneratedFrames = generatedFrames + 1; // Because frame index start at 0
            cap.release();
            return true;
        }

        System.out.println("Fail");
        return false;
    }

    private String getOutputFolder(String videoPath) {
        String parentDir = Paths.get(videoPath).toFile().getAbsoluteFile().getParentFile().toString();
        File saveOutputPath = Paths.get(parentDir, "outputs").toFile();

        if(!saveOutputPath.exists()) {
            saveOutputPath.mkdir();
        }

//        System.out.println("Saving output to " + saveOutputPath);

        return saveOutputPath.toString();
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
            ImageIO.write(img, "JPG", out);
            String base64bytes = Base64.getEncoder().encodeToString(out.toByteArray());

            return "data:image/png;base64," + base64bytes;
        }
        catch (Exception e)
        {
            System.out.println("Fail to convert");
            return null;
        }
    }

}
