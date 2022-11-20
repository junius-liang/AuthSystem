package com.junius.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.junius.model.system.SysRole;
import com.junius.model.vo.AssginRoleVo;
import com.junius.model.vo.SysRoleQueryVo;

import java.util.Map;

/**
 * @author junius
 * @date 2022/10/25 18:13
 **/
public interface SysRoleService extends IService<SysRole>{

    IPage<SysRole> selectPage(Page<SysRole> sysRolePage, String roleName);

    IPage<SysRole> findAllByPageHandler(Page<SysRole> pageTool);

    Map<String, Object> getRoleByUserId(String userId);

    void doAssign(AssginRoleVo vo);
}
