package com.junius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.junius.model.system.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author junius
 * @date 2022/10/27 15:32
 **/
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    IPage<SysUser> selectPage(Page<SysUser> page,String keyword);
}
