package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * http响应json数据，前后端统一约定的json格式(协议)
 * 响应状态码都是200，进入ajax的success方法来使用
 * {success: true,data: xxx}
 * {success: false,coude: xxx, message: xxx}
 *
 */
@Getter
@Setter
@ToString
public class JSONResponse {

    //业务操作是否成功
    private boolean success;
    //业务操作的消息码，一般来说，出现错误时的错误码才有意义,给开发人员定位错误用的
    private String code;
    //业务操作的错误消息，给用户看的信息
    private String message;
    //业务数据：业务操作成功时，给前端ajax success方法使用，解析响应json数据，渲染我们的网页内容
    private Object data;

}
