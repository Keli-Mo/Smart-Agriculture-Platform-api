package com.neu.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwDeviceInfoMapper;
import com.neu.system.domain.AcwDeviceInfo;
import com.neu.system.service.IAcwDeviceInfoService;

/**
 * 设备管理Service业务层处理
 * 
 * @author lx
 * @date 2025-02-06
 */
@Service
public class AcwDeviceInfoServiceImpl implements IAcwDeviceInfoService 
{
    @Autowired
    private AcwDeviceInfoMapper acwDeviceInfoMapper;

    /**
     * 查询设备管理
     * 
     * @param id 设备管理ID
     * @return 设备管理
     */
    @Override
    public AcwDeviceInfo selectAcwDeviceInfoById(Long id)
    {
        return acwDeviceInfoMapper.selectAcwDeviceInfoById(id);
    }

    /**
     * 查询设备管理列表
     * 
     * @param acwDeviceInfo 设备管理
     * @return 设备管理
     */
    @Override
    public List<AcwDeviceInfo> selectAcwDeviceInfoList(AcwDeviceInfo acwDeviceInfo)
    {
        return acwDeviceInfoMapper.selectAcwDeviceInfoList(acwDeviceInfo);
    }

    /**
     * 新增设备管理
     * 
     * @param acwDeviceInfo 设备管理
     * @return 结果
     */
    @Override
    public int insertAcwDeviceInfo(AcwDeviceInfo acwDeviceInfo)
    {
        return acwDeviceInfoMapper.insertAcwDeviceInfo(acwDeviceInfo);
    }

    /**
     * 修改设备管理
     * 
     * @param acwDeviceInfo 设备管理
     * @return 结果
     */
    @Override
    public int updateAcwDeviceInfo(AcwDeviceInfo acwDeviceInfo)
    {
        return acwDeviceInfoMapper.updateAcwDeviceInfo(acwDeviceInfo);
    }

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的设备管理ID
     * @return 结果
     */
    @Override
    public int deleteAcwDeviceInfoByIds(Long[] ids)
    {
        return acwDeviceInfoMapper.deleteAcwDeviceInfoByIds(ids);
    }

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理ID
     * @return 结果
     */
    @Override
    public int deleteAcwDeviceInfoById(Long id)
    {
        return acwDeviceInfoMapper.deleteAcwDeviceInfoById(id);
    }
}
