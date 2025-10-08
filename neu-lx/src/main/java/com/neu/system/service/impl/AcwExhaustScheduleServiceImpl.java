package com.neu.system.service.impl;

import java.util.List;
import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwExhaustScheduleMapper;
import com.neu.system.domain.AcwExhaustSchedule;
import com.neu.system.service.IAcwExhaustScheduleService;

/**
 * 排风计划Service业务层处理
 * 
 * @author neu
 * @date 2025-05-13
 */
@Service
public class AcwExhaustScheduleServiceImpl implements IAcwExhaustScheduleService 
{
    @Autowired
    private AcwExhaustScheduleMapper acwExhaustScheduleMapper;

    /**
     * 查询排风计划
     * 
     * @param id 排风计划ID
     * @return 排风计划
     */
    @Override
    public AcwExhaustSchedule selectAcwExhaustScheduleById(Long id)
    {
        return acwExhaustScheduleMapper.selectAcwExhaustScheduleById(id);
    }

    /**
     * 查询排风计划列表
     * 
     * @param acwExhaustSchedule 排风计划
     * @return 排风计划
     */
    @Override
    public List<AcwExhaustSchedule> selectAcwExhaustScheduleList(AcwExhaustSchedule acwExhaustSchedule)
    {
        return acwExhaustScheduleMapper.selectAcwExhaustScheduleList(acwExhaustSchedule);
    }

    /**
     * 新增排风计划
     * 
     * @param acwExhaustSchedule 排风计划
     * @return 结果
     */
    @Override
    public int insertAcwExhaustSchedule(AcwExhaustSchedule acwExhaustSchedule)
    {
        acwExhaustSchedule.setCreateTime(DateUtils.getNowDate());
        return acwExhaustScheduleMapper.insertAcwExhaustSchedule(acwExhaustSchedule);
    }

    /**
     * 修改排风计划
     * 
     * @param acwExhaustSchedule 排风计划
     * @return 结果
     */
    @Override
    public int updateAcwExhaustSchedule(AcwExhaustSchedule acwExhaustSchedule)
    {
        return acwExhaustScheduleMapper.updateAcwExhaustSchedule(acwExhaustSchedule);
    }

    /**
     * 批量删除排风计划
     * 
     * @param ids 需要删除的排风计划ID
     * @return 结果
     */
    @Override
    public int deleteAcwExhaustScheduleByIds(Long[] ids)
    {
        return acwExhaustScheduleMapper.deleteAcwExhaustScheduleByIds(ids);
    }

    /**
     * 删除排风计划信息
     * 
     * @param id 排风计划ID
     * @return 结果
     */
    @Override
    public int deleteAcwExhaustScheduleById(Long id)
    {
        return acwExhaustScheduleMapper.deleteAcwExhaustScheduleById(id);
    }
}
