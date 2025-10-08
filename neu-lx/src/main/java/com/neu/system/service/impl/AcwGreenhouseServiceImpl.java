package com.neu.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwGreenhouseMapper;
import com.neu.system.domain.AcwGreenhouse;
import com.neu.system.service.IAcwGreenhouseService;

/**
 * 大棚信息Service业务层处理
 * 
 * @author neu
 * @date 2025-03-26
 */
@Service
public class AcwGreenhouseServiceImpl implements IAcwGreenhouseService 
{
    @Autowired
    private AcwGreenhouseMapper acwGreenhouseMapper;

    /**
     * 查询大棚信息
     * 
     * @param id 大棚信息ID
     * @return 大棚信息
     */
    @Override
    public AcwGreenhouse selectAcwGreenhouseById(Long id)
    {
        return acwGreenhouseMapper.selectAcwGreenhouseById(id);
    }

    /**
     * 查询大棚信息列表
     * 
     * @param acwGreenhouse 大棚信息
     * @return 大棚信息
     */
    @Override
    public List<AcwGreenhouse> selectAcwGreenhouseList(AcwGreenhouse acwGreenhouse)
    {
        return acwGreenhouseMapper.selectAcwGreenhouseList(acwGreenhouse);
    }

    /**
     * 新增大棚信息
     * 
     * @param acwGreenhouse 大棚信息
     * @return 结果
     */
    @Override
    public int insertAcwGreenhouse(AcwGreenhouse acwGreenhouse)
    {
        return acwGreenhouseMapper.insertAcwGreenhouse(acwGreenhouse);
    }

    /**
     * 修改大棚信息
     * 
     * @param acwGreenhouse 大棚信息
     * @return 结果
     */
    @Override
    public int updateAcwGreenhouse(AcwGreenhouse acwGreenhouse)
    {
        return acwGreenhouseMapper.updateAcwGreenhouse(acwGreenhouse);
    }

    /**
     * 批量删除大棚信息
     * 
     * @param ids 需要删除的大棚信息ID
     * @return 结果
     */
    @Override
    public int deleteAcwGreenhouseByIds(Long[] ids)
    {
        return acwGreenhouseMapper.deleteAcwGreenhouseByIds(ids);
    }

    /**
     * 删除大棚信息信息
     * 
     * @param id 大棚信息ID
     * @return 结果
     */
    @Override
    public int deleteAcwGreenhouseById(Long id)
    {
        return acwGreenhouseMapper.deleteAcwGreenhouseById(id);
    }
}
