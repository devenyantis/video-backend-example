package com.example.videobackend.video_backend_example;

import com.zandero.rest.reader.ValueReader;
import com.zandero.utils.StringUtils;
import com.zandero.utils.extra.JsonUtils;
import io.vertx.core.json.jackson.DatabindCodec;

public class BodyReader implements ValueReader<VideoEndpointBody> {

    @Override
    public VideoEndpointBody read(String value, Class<VideoEndpointBody> type) {
        if(StringUtils.isNullOrEmptyTrimmed(value)) {
            return null;
        }

        return JsonUtils.fromJson(value, type, DatabindCodec.mapper());
    }
}