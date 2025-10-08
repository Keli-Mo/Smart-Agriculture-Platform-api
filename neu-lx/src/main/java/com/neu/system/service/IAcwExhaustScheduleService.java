package com.neu.system.service;

import java.util.List;
import com.neu.system.domain.AcwExhaustSchedule;

/**
 * 排风计划Service接口
 * 
 * @author neu
 * @date 2025-05-13
 */
public interface IAcwExhaustScheduleService 
{
    /**
     * 查询排风计划
     * 
     * @param id 排风计划ID
     * @return 排风计划
     */
    public AcwExhaustSchedule selectAcwExhaustScheduleById(Long id);

    /**
     * 查询排风计划列表
     * 
     * @param acwExhaustSchedule 排风计划
     * @return 排风计划集合
     */
    public List<AcwExhaustSchedule> selectAcwExhaustScheduleList(AcwExhaustSchedule acwExhaustSchedule);

    /**
     * 新增排风计划
     * 
     * @param acwExhaustSchedule 排风计划
     * @return 结果
     */
    public int insertAcwExhaustSchedule(AcwExhaustSchedule acwExhaustSchedule);

    /**
     * 修改排风计划
     * 
     * @param acwExhaustSchedule 排风计划
     * @return 结果
     */
    public int updateAcwExhaustSchedule(AcwExhaustSchedule acwExhaustSchedule);

    /**
     * 批量删除排风计划
     * 
     * @param ids 需要删除的排风计划ID
     * @return 结果
     */
    public int deleteAcwExhaustScheduleByIds(Long[] ids);

    /**
     * 删除排风计划信息
     * 
     * @param id 排风计划ID
     * @return 结果
     */
    public int deleteAcwExhaustScheduleById(Long id);
}
