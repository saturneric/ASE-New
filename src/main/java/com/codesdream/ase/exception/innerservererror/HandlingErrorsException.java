package com.codesdream.ase.exception.innerservererror;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// 处理错误对应的异常类
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class HandlingErrorsException extends RuntimeException {
    public HandlingErrorsException(String msg){
        super(msg);
    }
}
