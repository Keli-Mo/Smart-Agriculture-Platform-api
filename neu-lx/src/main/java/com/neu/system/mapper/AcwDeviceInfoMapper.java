package com.neu.system.mapper;

import java.util.List;
import com.neu.system.domain.AcwDeviceInfo;

/**
 * 设备管理Mapper接口
 * 
 * @author lx
 * @date 2025-02-06
 */
public interface AcwDeviceInfoMapper 
{
    /**
     * 查询设备管理
     * 
     * @param id 设备管理ID
     * @return 设备管理
     */
    public AcwDeviceInfo selectAcwDeviceInfoById(Long id);

    /**
     * 查询设备管理列表
     * 
     * @param acwDeviceInfo 设备管理
     * @return 设备管理集合
     */
    public List<AcwDeviceInfo> selectAcwDeviceInfoList(AcwDeviceInfo acwDeviceInfo);

    /**
     * 新增设备管理
     * 
     * @param acwDeviceInfo 设备管理
     * @return 结果
     */
    public int insertAcwDeviceInfo(AcwDeviceInfo acwDeviceInfo);

    /**
     * 修改设备管理
     * 
     * @param acwDeviceInfo 设备管理
     * @return 结果
     */
    public int updateAcwDeviceInfo(AcwDeviceInfo acwDeviceInfo);

    /**
     * 删除设备管理
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    public int deleteAcwDeviceInfoById(Long id);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcwDeviceInfoByIds(Long[] ids);
}
