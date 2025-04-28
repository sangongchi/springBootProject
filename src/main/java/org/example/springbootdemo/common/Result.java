package org.example.springbootdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;
    private long timestamp=System.currentTimeMillis();
    private Long total;
    private Long limit;
    private Long page;
}
