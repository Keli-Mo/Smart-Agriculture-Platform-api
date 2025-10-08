package com.neu.system.service.impl;

import java.util.List;
import com.neu.common.utils.DateUtils;
import com.neu.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwAlertLogMapper;
import com.neu.system.domain.AcwAlertLog;
import com.neu.system.service.IAcwAlertLogService;

/**
 * 预警记录Service业务层处理
 * 
 * @author neu
 * @date 2025-03-25
 */
@Service
public class AcwAlertLogServiceImpl implements IAcwAlertLogService 
{
    @Autowired
    private AcwAlertLogMapper acwAlertLogMapper;

    /**
     * 查询预警记录
     * 
     * @param id 预警记录ID
     * @return 预警记录
     */
    @Override
    public AcwAlertLog selectAcwAlertLogById(Long id)
    {
        return acwAlertLogMapper.selectAcwAlertLogById(id);
    }

    /**
     * 查询预警记录列表
     * 
     * @param acwAlertLog 预警记录
     * @return 预警记录
     */
    @Override
    public List<AcwAlertLog> selectAcwAlertLogList(AcwAlertLog acwAlertLog)
    {
        return acwAlertLogMapper.selectAcwAlertLogList(acwAlertLog);
    }

    /**
     * 新增预警记录
     * 
     * @param acwAlertLog 预警记录
     * @return 结果
     */
    @Override
    public int insertAcwAlertLog(AcwAlertLog acwAlertLog)
    {
        acwAlertLog.setCreateTime(DateUtils.getNowDate());
        acwAlertLog.setCreateBy(SecurityUtils.getUsername());
        return acwAlertLogMapper.insertAcwAlertLog(acwAlertLog);
    }

    /**
     * 修改预警记录
     * 
     * @param acwAlertLog 预警记录
     * @return 结果
     */
    @Override
    public int updateAcwAlertLog(AcwAlertLog acwAlertLog)
    {
        return acwAlertLogMapper.updateAcwAlertLog(acwAlertLog);
    }

    /**
     * 批量删除预警记录
     * 
     * @param ids 需要删除的预警记录ID
     * @return 结果
     */
    @Override
    public int deleteAcwAlertLogByIds(Long[] ids)
    {
        return acwAlertLogMapper.deleteAcwAlertLogByIds(ids);
    }

    /**
     * 删除预警记录信息
     * 
     * @param id 预警记录ID
     * @return 结果
     */
    @Override
    public int deleteAcwAlertLogById(Long id)
    {
        return acwAlertLogMapper.deleteAcwAlertLogById(id);
    }
}
