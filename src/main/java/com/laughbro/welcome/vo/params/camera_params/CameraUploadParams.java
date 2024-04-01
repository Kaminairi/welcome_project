package com.laughbro.welcome.vo.params.camera_params;

import lombok.Data;

@Data
public class CameraUploadParams {
   private String base64Image;
   private String cameratoken;
}
