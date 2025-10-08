package com.neu.system.mapper;

import java.util.List;
import com.neu.system.domain.AcwExhaustSchedule;

/**
 * 排风计划Mapper接口
 * 
 * @author neu
 * @date 2025-05-13
 */
public interface AcwExhaustScheduleMapper 
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
     * 删除排风计划
     * 
     * @param id 排风计划ID
     * @return 结果
     */
    public int deleteAcwExhaustScheduleById(Long id);

    /**
     * 批量删除排风计划
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcwExhaustScheduleByIds(Long[] ids);
}
