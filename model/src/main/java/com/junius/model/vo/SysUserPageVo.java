package com.junius.model.vo;

import lombok.Data;

/**
 * @author junius
 * @date 2022/10/31 16:00
 **/
@Data
public class SysUserPageVo {
    private Long page;

    private Long size;

    private String keyword;
}
