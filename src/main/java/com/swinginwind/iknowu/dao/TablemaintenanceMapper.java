package com.swinginwind.iknowu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.swinginwind.iknowu.model.Field;
import com.swinginwind.iknowu.model.Tablemaintenance;


public interface TablemaintenanceMapper {

	public List<Tablemaintenance> query(Tablemaintenance tablemaintenance);
	
	/**
	 * 查询所有配置表
	 * 描述：TODO
	 * @return
	 */
	//查询所有配置表，目前用于外键。之前是只查询物理表，不区分视图。后改为查询所有视图，这样做的目的是可以通过视图控制【外键选择表格】的显示字段数目
	public List<Tablemaintenance> getAllTable();
	
	public int count(Tablemaintenance tablemaintenance);
	
	public void save(Tablemaintenance tablemaintenance);
	public void save(Map<String, Object> tablemaintenance);
	
	public void delete(Tablemaintenance tablemaintenance);
	
	public void update(Tablemaintenance tablemaintenance);
	public void update(Map<String, Object> tablemaintenance);
	
	//删除表数据
	public void deletedata(Tablemaintenance tablemaintenance);
	
	//删除表字段
	public void deletefield(Map<String,Object> map);
	
	//删除表数据
	public void deletetable(Tablemaintenance tablemaintenance);
	
	//新增表
	public void inserttable(Map<String,Object> map);
	
	//新增表字段
	public void insertfield(Map<String,Object> map);
	
	//为字段添加注释
	public void commentForField(Map<String,Object> map);
	
	//修改表字段
	public void updatefield(Map<String,Object> map);
	
	//查询用户所有物理表
	public List<String> queryTable(@Param("names") String[] names);
	
	/**
	 * 查询物理表是否存在
	 * 描述：TODO
	 * @param tableName
	 * @return
	 */
	public int queryTableExists(String tableName);
	
	//查询已有表的主键-主键别名
	public List<String> getPrimaryKey(Map<String,Object> map);
	
	//查询已有表的主键-字段名
	public List<String> getPrimaryKey_COL(Map<String,Object> map);
	
	//给已有表添加主键
	public void addPrimaryKey(Map<String,Object> map);
	
	//删除已有表的主键
	public void deletePrimayKey(Map<String,Object> map);
	
	//查询已有表的唯一性约束
	public List<String> getUniqueConstraintName(Map<String,Object> map);
	
	//给已有表某个字段添加唯一性约束
	public void addUniqueConstraint(Map<String,Object> map);
	
	//删除已有表唯一性约束 
	public void deleteUniqueConstraint(Map<String,Object> map);
	
	/**
	 * 获取最大的viewcode
	 * 描述：TODO
	 * @param tableName
	 * @return
	 */
	public List<String> queryMaxViewCode(String tableName);
	
	/**
     * 更新动态表头信息
     * @param tablemaintenance
     */
    public int updateDynamicTitle(Tablemaintenance tablemaintenance);
    
    /**
     * 得到系统表对应该信息
     * @param tablemaintenance
     * @return
     */
    public List<Tablemaintenance> getSysTables(Tablemaintenance tablemaintenance);
    
    /**
     * 复制指定表中的数据
     * @param tablename     表名
     * @param fields        表中所有的字段
     * @param condition     条件，待复制的数据
     * @param primaryVal    是主键字段，可定义sql表达式，为空时为UUID
     * @param fixedFields   固定值(字段:值)，代表该字段复制出的值是指定的常量，如pid等。 该参数可为空
     * 
     */
    public int insertCopyData(@Param("tablename") String tablename,
    		@Param("fields") List<Field> fields,
    		@Param("condition") String condition,
    		@Param("primaryVal") String primaryVal,
    		@Param("fixedFields") Map<String,Object> fixedFields,
		    @Param("fixedFieldSql") Map<String,Object> fixedFieldSql);
    
}
