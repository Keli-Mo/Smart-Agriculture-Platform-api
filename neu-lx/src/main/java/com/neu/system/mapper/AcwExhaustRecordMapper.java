package com.neu.system.mapper;

import java.util.List;
import com.neu.system.domain.AcwExhaustRecord;

/**
 * 排风记录Mapper接口
 * 
 * @author neu
 * @date 2025-05-14
 */
public interface AcwExhaustRecordMapper 
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
     * 删除排风记录
     * 
     * @param id 排风记录ID
     * @return 结果
     */
    public int deleteAcwExhaustRecordById(Long id);

    /**
     * 批量删除排风记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcwExhaustRecordByIds(Long[] ids);
}
