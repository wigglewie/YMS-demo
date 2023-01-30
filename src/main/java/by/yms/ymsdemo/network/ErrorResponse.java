package by.yms.ymsdemo.network;

import lombok.Data;

@Data
public class ErrorResponse {

    public String timestamp;

    public int status;

    public String error;

    public String message;

    public String path;

}
