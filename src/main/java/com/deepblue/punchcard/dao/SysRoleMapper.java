package com.deepblue.punchcard.dao;
import com.deepblue.punchcard.entity.SysRole;
import java.util.List;
import java.util.Map;

/**
*@Description: SysRoledao接口
* @author 向一
*@date 2019/10/30 22:04
*/
public interface SysRoleMapper {

		public SysRole getSysRoleById(Long id)throws Exception;

		public List<SysRole>	getSysRoleListByMap(Map<String,Object> param)throws Exception;

		public Integer getSysRoleCountByMap(Map<String,Object> param)throws Exception;

		public Integer insertSysRole(SysRole sysRole)throws Exception;

		public Integer updateSysRole(SysRole sysRole)throws Exception;

		public Integer deleteSysRoleById(Long id)throws Exception;
}
