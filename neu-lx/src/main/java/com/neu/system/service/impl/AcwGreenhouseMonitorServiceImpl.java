package com.neu.system.service.impl;

import java.util.List;

import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwGreenhouseMonitorMapper;
import com.neu.system.domain.AcwGreenhouseMonitor;
import com.neu.system.service.IAcwGreenhouseMonitorService;

/**
 * 大棚监测Service业务层处理
 * 
 * @author neu
 * @date 2025-03-26
 */
@Service
public class AcwGreenhouseMonitorServiceImpl implements IAcwGreenhouseMonitorService 
{
    @Autowired
    private AcwGreenhouseMonitorMapper acwGreenhouseMonitorMapper;

    /**
     * 查询大棚监测
     * 
     * @param id 大棚监测ID
     * @return 大棚监测
     */
    @Override
    public AcwGreenhouseMonitor selectAcwGreenhouseMonitorById(Long id)
    {
        return acwGreenhouseMonitorMapper.selectAcwGreenhouseMonitorById(id);
    }

    /**
     * 查询大棚监测列表
     * 
     * @param acwGreenhouseMonitor 大棚监测
     * @return 大棚监测
     */
    @Override
    public List<AcwGreenhouseMonitor> selectAcwGreenhouseMonitorList(AcwGreenhouseMonitor acwGreenhouseMonitor)
    {
        return acwGreenhouseMonitorMapper.selectAcwGreenhouseMonitorList(acwGreenhouseMonitor);
    }

    /**
     * 新增大棚监测
     * 
     * @param acwGreenhouseMonitor 大棚监测
     * @return 结果
     */
    @Override
    public int insertAcwGreenhouseMonitor(AcwGreenhouseMonitor acwGreenhouseMonitor)
    {
        acwGreenhouseMonitor.setDataTime(DateUtils.getNowDate());
        return acwGreenhouseMonitorMapper.insertAcwGreenhouseMonitor(acwGreenhouseMonitor);
    }

    /**
     * 修改大棚监测
     * 
     * @param acwGreenhouseMonitor 大棚监测
     * @return 结果
     */
    @Override
    public int updateAcwGreenhouseMonitor(AcwGreenhouseMonitor acwGreenhouseMonitor)
    {
        return acwGreenhouseMonitorMapper.updateAcwGreenhouseMonitor(acwGreenhouseMonitor);
    }

    /**
     * 批量删除大棚监测
     * 
     * @param ids 需要删除的大棚监测ID
     * @return 结果
     */
    @Override
    public int deleteAcwGreenhouseMonitorByIds(Long[] ids)
    {
        return acwGreenhouseMonitorMapper.deleteAcwGreenhouseMonitorByIds(ids);
    }

    /**
     * 删除大棚监测信息
     * 
     * @param id 大棚监测ID
     * @return 结果
     */
    @Override
    public int deleteAcwGreenhouseMonitorById(Long id)
    {
        return acwGreenhouseMonitorMapper.deleteAcwGreenhouseMonitorById(id);
    }
}
