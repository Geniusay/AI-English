package Pinecone.Framework.Util.Net.Illumination.http;

import Pinecone.Framework.Util.JSON.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface HttpEntityParser {
    Object valueSafety( Object value );

    JSONObject parseQueryString  (String szQueryString, boolean bSafe );

    JSONObject parseFormData     (HttpServletRequest request, boolean bSafe );

    JSONObject requestMapJsonify ( HttpServletRequest request, boolean bSafe );

    JSONObject siftPostFromParameterMap( HttpServletRequest request, boolean bSafe );
}
