package com.example.goobee_yuer.testcamera.utils;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by 羊柯 on 2017/5/19.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int PREVIEW_WIDTH = 1280;
    private int PREVIEW_HEIGHT = 720;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            return;
        }
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setPreviewFormat(ImageFormat.NV21);

            List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
            for (Camera.Size previewSize : previewSizes) {
                if (previewSize.width == PREVIEW_WIDTH && previewSize.height == PREVIEW_HEIGHT) {
                    parameters.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
                    break;
                }
            }

            List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
            Camera.Size fs = null;
            for (int i = 0; i < pictureSizes.size(); i++) {
                Camera.Size psize = pictureSizes.get(i);
                if (fs == null && psize.width >= 1280) {
                    fs = psize;
                }
            }
            parameters.setPictureSize(fs.width, fs.height);
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        //解决java.lang.RuntimeException: Camera is being used after Camera.release() was called异常
        holder.removeCallback(this);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

    }
}
