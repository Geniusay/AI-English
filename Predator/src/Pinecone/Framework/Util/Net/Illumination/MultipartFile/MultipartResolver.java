package Pinecone.Framework.Util.Net.Illumination.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface MultipartResolver {
    boolean isMultipart(javax.servlet.http.HttpServletRequest hHttpServletRequest);

    MultipartHttpServletRequest resolveMultipart(HttpServletRequest hHttpServletRequest) throws MultipartException;

    void cleanupMultipart(MultipartHttpServletRequest hMultipartHttpServletRequest);
}
