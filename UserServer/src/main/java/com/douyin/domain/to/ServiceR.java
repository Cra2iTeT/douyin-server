package com.douyin.domain.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Cra2iTeT
 * @date 2022/10/13 17:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceR implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private Object data;

    public ServiceR(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

}