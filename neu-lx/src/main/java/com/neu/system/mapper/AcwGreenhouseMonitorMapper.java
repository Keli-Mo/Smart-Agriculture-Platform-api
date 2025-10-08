package com.neu.system.mapper;

import java.util.List;
import com.neu.system.domain.AcwGreenhouseMonitor;

/**
 * 大棚监测Mapper接口
 * 
 * @author neu
 * @date 2025-03-26
 */
public interface AcwGreenhouseMonitorMapper 
{
    /**
     * 查询大棚监测
     * 
     * @param id 大棚监测ID
     * @return 大棚监测
     */
    public AcwGreenhouseMonitor selectAcwGreenhouseMonitorById(Long id);

    /**
     * 查询大棚监测列表
     * 
     * @param acwGreenhouseMonitor 大棚监测
     * @return 大棚监测集合
     */
    public List<AcwGreenhouseMonitor> selectAcwGreenhouseMonitorList(AcwGreenhouseMonitor acwGreenhouseMonitor);

    /**
     * 新增大棚监测
     * 
     * @param acwGreenhouseMonitor 大棚监测
     * @return 结果
     */
    public int insertAcwGreenhouseMonitor(AcwGreenhouseMonitor acwGreenhouseMonitor);

    /**
     * 修改大棚监测
     * 
     * @param acwGreenhouseMonitor 大棚监测
     * @return 结果
     */
    public int updateAcwGreenhouseMonitor(AcwGreenhouseMonitor acwGreenhouseMonitor);

    /**
     * 删除大棚监测
     * 
     * @param id 大棚监测ID
     * @return 结果
     */
    public int deleteAcwGreenhouseMonitorById(Long id);

    /**
     * 批量删除大棚监测
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcwGreenhouseMonitorByIds(Long[] ids);
}
