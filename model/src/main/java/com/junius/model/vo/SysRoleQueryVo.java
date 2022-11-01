//
//
package com.junius.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色查询实体
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Data
public class SysRoleQueryVo implements Serializable {

	private String roleName;

	private  Long page;

	private Long limit;

}

