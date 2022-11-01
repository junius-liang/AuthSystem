package com.junius.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junius.mapper.SysRoleMapper;
import com.junius.model.system.SysRole;
import com.junius.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * @author junius
 * @date 2022/10/25 18:14
 **/
@Service
public class SysRoleServiceImpl
        extends ServiceImpl<SysRoleMapper , SysRole> implements SysRoleService {


    @Override
    public IPage<SysRole> selectPage(Page<SysRole> sysRolePage, String roleName) {
        IPage<SysRole> pageModel =  baseMapper.selectPage(sysRolePage, roleName);
        return pageModel;
    }

    @Override
    public IPage<SysRole> findAllByPageHandler(Page<SysRole> pageTool) {
        return baseMapper.findAllByPageHandler(pageTool);
    }
}
