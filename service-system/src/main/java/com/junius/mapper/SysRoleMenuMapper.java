package com.junius.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junius.model.system.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author junius
 * @date 2022/11/26 15:33
 **/
@Repository
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
