package com.deepblue.punchcard.dao;
import com.deepblue.punchcard.entity.SysPerm;
import java.util.List;
import java.util.Map;

/**
*@Description: SysPermdao接口
* @author 向一
*@date 2019/10/30 22:04
*/
public interface SysPermMapper {

		public SysPerm getSysPermById(Long id)throws Exception;

		public List<SysPerm>	getSysPermListByMap(Map<String,Object> param)throws Exception;

		public Integer getSysPermCountByMap(Map<String,Object> param)throws Exception;

		public Integer insertSysPerm(SysPerm sysPerm)throws Exception;

		public Integer updateSysPerm(SysPerm sysPerm)throws Exception;

		public Integer deleteSysPermById(Long id)throws Exception;
}
