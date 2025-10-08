package com.neu.system.service;

import java.util.List;
import com.neu.system.domain.AcwExhaustRecord;

/**
 * 排风记录Service接口
 * 
 * @author neu
 * @date 2025-05-14
 */
public interface IAcwExhaustRecordService 
{
    /**
     * 查询排风记录
     * 
     * @param id 排风记录ID
     * @return 排风记录
     */
    public AcwExhaustRecord selectAcwExhaustRecordById(Long id);

    /**
     * 查询排风记录列表
     * 
     * @param acwExhaustRecord 排风记录
     * @return 排风记录集合
     */
    public List<AcwExhaustRecord> selectAcwExhaustRecordList(AcwExhaustRecord acwExhaustRecord);

    /**
     * 新增排风记录
     * 
     * @param acwExhaustRecord 排风记录
     * @return 结果
     */
    public int insertAcwExhaustRecord(AcwExhaustRecord acwExhaustRecord);

    /**
     * 修改排风记录
     * 
     * @param acwExhaustRecord 排风记录
     * @return 结果
     */
    public int updateAcwExhaustRecord(AcwExhaustRecord acwExhaustRecord);

    /**
     * 批量删除排风记录
     * 
     * @param ids 需要删除的排风记录ID
     * @return 结果
     */
    public int deleteAcwExhaustRecordByIds(Long[] ids);

    /**
     * 删除排风记录信息
     * 
     * @param id 排风记录ID
     * @return 结果
     */
    public int deleteAcwExhaustRecordById(Long id);
}
