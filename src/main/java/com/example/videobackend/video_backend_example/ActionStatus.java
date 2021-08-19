package com.example.videobackend.video_backend_example;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface ActionStatus {
    static ActionStatus ok()
    {
        return new ActionSucceed();
    }

    static ActionStatus failedWithMessage(String msg)
    {
        return new ActionFailed(msg);
    }
}

@Data
class ActionSucceed implements ActionStatus {
    private final int message = 1;
}

@Data
@AllArgsConstructor
class ActionFailed implements ActionStatus {
    private final int message = 0;

    private final int errorCode = 1;

    private final String errorMessage;
}
