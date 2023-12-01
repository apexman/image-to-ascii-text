package com.maksimov.imgtoascii;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

//TODO  impl
public class VideoConverter implements Converter<Object, Object> {
//    private final double SECONDS_BETWEEN_FRAMES = 0.1;
//
//    private final String outputFileName = "convertedVideo.mp4";
//
//    // The video stream index, used to ensure we display frames from one and
//    // only one video stream from the media container.
//    private static int mVideoStreamIndex = -1;
//
//    // Time of last frame write
//    private static long mLastPtsWrite = Global.NO_PTS;
//
//    private long startTime = System.nanoTime();
//
//    private final long MICRO_SECONDS_BETWEEN_FRAMES =
//            (long)(Global.DEFAULT_PTS_PER_SECOND);
//
//    private final ImageConverter imageConverter = new ImageConverter();

    @Override
    public Object convert(Object filePath) {
        if (true) {
            throw new UnsupportedOperationException();
        }
//
//        IMediaReader mediaReader = ToolFactory.makeReader(filePath);
//
//        // stipulate that we want BufferedImages created in BGR 24bit color space
//        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
//
//        mediaReader.addListener(new ImageSnapListener());
//
//        // read out the contents of the media file and
//        // dispatch events to the attached listener
//        while (mediaReader.readPacket() == null) ;
        return null;
    }

//    private class ImageSnapListener extends MediaListenerAdapter {
//        private IMediaWriter writer = ToolFactory.makeWriter(outputFileName);
//
//        public ImageSnapListener() {
//
//            // We tell it we're going to add one video stream, with id 0,
//            // at position 0, and that it will have a fixed frame rate of FRAME_RATE.
//            writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4,
//                    1920, 1080);
//        }
//
//        public void onVideoPicture(IVideoPictureEvent event) {
//
//            if (event.getStreamIndex() != mVideoStreamIndex) {
//                // if the selected video stream id is not yet set, go ahead an
//                // select this lucky video stream
//                if (mVideoStreamIndex == -1)
//                    mVideoStreamIndex = event.getStreamIndex();
//                    // no need to show frames from this video stream
//                else
//                    return;
//            }
//
//            // if uninitialized, back date mLastPtsWrite to get the very first frame
//            if (mLastPtsWrite == Global.NO_PTS)
//                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
//
//            // if it's time to write the next frame
//            if (event.getTimeStamp() - mLastPtsWrite >=
//                    MICRO_SECONDS_BETWEEN_FRAMES) {
//
//                BufferedImage convertedImage = imageConverter.convertToImageASCII(event.getImage());
//
//                saveToVideo(convertedImage);
//
//                // update last write time
//                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
//            }
//        }
//
//        @Override
//        public void onClose(ICloseEvent event) {
//            super.onClose(event);
//            writer.close();
//        }
//
//        private void saveToVideo(BufferedImage convertedImage) {
//            // We tell it we're going to add one video stream, with id 0,
//            // at position 0, and that it will have a fixed frame rate of FRAME_RATE.
////            writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4,
////                    convertedImage.getWidth(), convertedImage.getHeight());
//
//            // convert to the right image type
//            BufferedImage bgrScreen = convertToType(convertedImage,
//                    BufferedImage.TYPE_3BYTE_BGR);
//
//            // encode the image to stream #0
//            writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime,
//                    TimeUnit.NANOSECONDS);
//
//        }
//
//        private BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
//
//            BufferedImage image;
//
//            // if the source image is already the target type, return the source image
//            if (sourceImage.getType() == targetType) {
//                image = sourceImage;
//            }
//            // otherwise create a new image of the target type and draw the new image
//            else {
//                image = new BufferedImage(sourceImage.getWidth(),
//                        sourceImage.getHeight(), targetType);
//                image.getGraphics().drawImage(sourceImage, 0, 0, null);
//            }
//
//            return image;
//
//        }
//
//
//    }

}
