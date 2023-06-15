package com.example.netty.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgHeader {
    private Integer version;
    private Integer cmdId;
}
