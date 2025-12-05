package com.book.bookcommon.result;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response<T> {
    private Boolean success;  // 是否成功：false
    private Integer code;     // 业务错误码
    private String message;   // 错误信息
    private Long timestamp;   // 时间戳
    private String path;      // 请求路径（可选）
    private T data;

    public Response(Integer code, String message, String path, T data) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.path = path;
        this.data = data;
    }

    // Getters
    public Boolean getSuccess() { return success; }
    public Integer getCode() { return code; }
    public String getMessage() { return message; }
    public Long getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public T getData() { return data; }

    public static <T> Response<T> success(T data) {
        Response<T> res = new Response<>();
        res.setSuccess(Boolean.TRUE);
        res.setCode(200);
        res.setMessage("成功");
        res.setData(data);
        return res;
    }

}
