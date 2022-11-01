package com.junius.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junius.Result;
import com.junius.mapper.SysUserMapper;
import com.junius.model.system.SysUser;
import com.junius.model.vo.SysUserPageVo;
import com.junius.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author junius
 * @date 2022/10/27 15:34
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public Result selectPage(SysUserPageVo sysUserPageVo) {
        Page<SysUser> sysUserPage = new Page<>(sysUserPageVo.getPage(), sysUserPageVo.getSize());
        IPage<SysUser> sysUserIPage = baseMapper.selectPage(sysUserPage, sysUserPageVo.getKeyword());
        return Result.ok(sysUserIPage);
    }
}
