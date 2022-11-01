package com.junius.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author junius
 * @date 2022/10/27 15:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVo {
    private String userName;

    private String pwd;
}
