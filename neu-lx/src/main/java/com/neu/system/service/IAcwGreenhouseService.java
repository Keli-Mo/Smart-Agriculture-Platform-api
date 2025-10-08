package com.neu.system.service;

import java.util.List;
import com.neu.system.domain.AcwGreenhouse;

/**
 * 大棚信息Service接口
 * 
 * @author neu
 * @date 2025-03-26
 */
public interface IAcwGreenhouseService 
{
    /**
     * 查询大棚信息
     * 
     * @param id 大棚信息ID
     * @return 大棚信息
     */
    public AcwGreenhouse selectAcwGreenhouseById(Long id);

    /**
     * 查询大棚信息列表
     * 
     * @param acwGreenhouse 大棚信息
     * @return 大棚信息集合
     */
    public List<AcwGreenhouse> selectAcwGreenhouseList(AcwGreenhouse acwGreenhouse);

    /**
     * 新增大棚信息
     * 
     * @param acwGreenhouse 大棚信息
     * @return 结果
     */
    public int insertAcwGreenhouse(AcwGreenhouse acwGreenhouse);

    /**
     * 修改大棚信息
     * 
     * @param acwGreenhouse 大棚信息
     * @return 结果
     */
    public int updateAcwGreenhouse(AcwGreenhouse acwGreenhouse);

    /**
     * 批量删除大棚信息
     * 
     * @param ids 需要删除的大棚信息ID
     * @return 结果
     */
    public int deleteAcwGreenhouseByIds(Long[] ids);

    /**
     * 删除大棚信息信息
     * 
     * @param id 大棚信息ID
     * @return 结果
     */
    public int deleteAcwGreenhouseById(Long id);
}
