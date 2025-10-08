package com.neu.system.service.impl;

import java.util.List;
import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwExhaustRecordMapper;
import com.neu.system.domain.AcwExhaustRecord;
import com.neu.system.service.IAcwExhaustRecordService;

/**
 * 排风记录Service业务层处理
 * 
 * @author neu
 * @date 2025-05-14
 */
@Service
public class AcwExhaustRecordServiceImpl implements IAcwExhaustRecordService 
{
    @Autowired
    private AcwExhaustRecordMapper acwExhaustRecordMapper;

    /**
     * 查询排风记录
     * 
     * @param id 排风记录ID
     * @return 排风记录
     */
    @Override
    public AcwExhaustRecord selectAcwExhaustRecordById(Long id)
    {
        return acwExhaustRecordMapper.selectAcwExhaustRecordById(id);
    }

    /**
     * 查询排风记录列表
     * 
     * @param acwExhaustRecord 排风记录
     * @return 排风记录
     */
    @Override
    public List<AcwExhaustRecord> selectAcwExhaustRecordList(AcwExhaustRecord acwExhaustRecord)
    {
        return acwExhaustRecordMapper.selectAcwExhaustRecordList(acwExhaustRecord);
    }

    /**
     * 新增排风记录
     * 
     * @param acwExhaustRecord 排风记录
     * @return 结果
     */
    @Override
    public int insertAcwExhaustRecord(AcwExhaustRecord acwExhaustRecord)
    {
        acwExhaustRecord.setCreateTime(DateUtils.getNowDate());
        return acwExhaustRecordMapper.insertAcwExhaustRecord(acwExhaustRecord);
    }

    /**
     * 修改排风记录
     * 
     * @param acwExhaustRecord 排风记录
     * @return 结果
     */
    @Override
    public int updateAcwExhaustRecord(AcwExhaustRecord acwExhaustRecord)
    {
        return acwExhaustRecordMapper.updateAcwExhaustRecord(acwExhaustRecord);
    }

    /**
     * 批量删除排风记录
     * 
     * @param ids 需要删除的排风记录ID
     * @return 结果
     */
    @Override
    public int deleteAcwExhaustRecordByIds(Long[] ids)
    {
        return acwExhaustRecordMapper.deleteAcwExhaustRecordByIds(ids);
    }

    /**
     * 删除排风记录信息
     * 
     * @param id 排风记录ID
     * @return 结果
     */
    @Override
    public int deleteAcwExhaustRecordById(Long id)
    {
        return acwExhaustRecordMapper.deleteAcwExhaustRecordById(id);
    }
}
