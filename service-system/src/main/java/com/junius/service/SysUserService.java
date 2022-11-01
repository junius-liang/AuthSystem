package com.junius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junius.Result;
import com.junius.model.system.SysUser;
import com.junius.model.vo.SysUserPageVo;

/**
 * @author junius
 * @date 2022/10/27 15:33
 **/
public interface SysUserService extends IService<SysUser> {
    Result selectPage(SysUserPageVo sysUserPageVo);
}
