package com.neu.system.mapper;

import java.util.List;
import com.neu.system.domain.AcwAlertLog;

/**
 * 预警记录Mapper接口
 * 
 * @author neu
 * @date 2025-03-25
 */
public interface AcwAlertLogMapper 
{
    /**
     * 查询预警记录
     * 
     * @param id 预警记录ID
     * @return 预警记录
     */
    public AcwAlertLog selectAcwAlertLogById(Long id);

    /**
     * 查询预警记录列表
     * 
     * @param acwAlertLog 预警记录
     * @return 预警记录集合
     */
    public List<AcwAlertLog> selectAcwAlertLogList(AcwAlertLog acwAlertLog);

    /**
     * 新增预警记录
     * 
     * @param acwAlertLog 预警记录
     * @return 结果
     */
    public int insertAcwAlertLog(AcwAlertLog acwAlertLog);

    /**
     * 修改预警记录
     * 
     * @param acwAlertLog 预警记录
     * @return 结果
     */
    public int updateAcwAlertLog(AcwAlertLog acwAlertLog);

    /**
     * 删除预警记录
     * 
     * @param id 预警记录ID
     * @return 结果
     */
    public int deleteAcwAlertLogById(Long id);

    /**
     * 批量删除预警记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcwAlertLogByIds(Long[] ids);
}
