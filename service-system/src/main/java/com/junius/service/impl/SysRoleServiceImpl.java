package com.junius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junius.mapper.SysRoleMapper;
import com.junius.mapper.SysUserRoleMapper;
import com.junius.model.system.SysRole;
import com.junius.model.system.SysUserRole;
import com.junius.model.vo.AssginRoleVo;
import com.junius.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author junius
 * @date 2022/10/25 18:14
 **/
@Service
public class SysRoleServiceImpl
        extends ServiceImpl<SysRoleMapper , SysRole> implements SysRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> sysRolePage, String roleName) {
        IPage<SysRole> pageModel =  baseMapper.selectPage(sysRolePage, roleName);
        return pageModel;
    }

    @Override
    public IPage<SysRole> findAllByPageHandler(Page<SysRole> pageTool) {
        return baseMapper.findAllByPageHandler(pageTool);
    }

    @Override
    public Map<String, Object> getRoleByUserId(String userId) {
        //所有角色
        List<SysRole> sysRoles = baseMapper.selectList(null);
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        QueryWrapper<SysUserRole> wrapper = sysUserRoleQueryWrapper.eq("user_id", userId);
        //当前用户角色
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(wrapper);
        List<String> ids = new ArrayList<>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            ids.add(sysUserRole.getRoleId());
        }
        Map<String,Object> result = new HashMap<>();
        result.put("all",sysRoles);
        result.put("new",sysUserRoles);
        result.put("one",ids);
        return result;
    }

    @Override
    public void doAssign(AssginRoleVo vo) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<SysUserRole>().eq("user_id", vo.getUserId());
        sysUserRoleMapper.delete(wrapper);

        List<String> roleIdList = vo.getRoleIdList();
        for (String roleId : roleIdList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(vo.getUserId());
            sysUserRoleMapper.insert(sysUserRole);
        }
    }
}
