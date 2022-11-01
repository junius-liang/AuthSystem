package com.junius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junius.model.system.SysRole;
import org.springframework.stereotype.Repository;

/**
 * @author junius
 * @date 2022/10/25 17:22
 **/
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole>  {

     IPage<SysRole> selectPage(Page<SysRole> sysRolePage, String roleName);

    IPage<SysRole> findAllByPageHandler(Page<SysRole> pageTool);
}
