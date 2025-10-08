package com.neu.system.service;

import java.util.List;
import com.neu.system.domain.AcwGreenhouseMonitor;

/**
 * 大棚监测Service接口
 * 
 * @author neu
 * @date 2025-03-26
 */
public interface IAcwGreenhouseMonitorService 
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
     * 批量删除大棚监测
     * 
     * @param ids 需要删除的大棚监测ID
     * @return 结果
     */
    public int deleteAcwGreenhouseMonitorByIds(Long[] ids);

    /**
     * 删除大棚监测信息
     * 
     * @param id 大棚监测ID
     * @return 结果
     */
    public int deleteAcwGreenhouseMonitorById(Long id);
}
