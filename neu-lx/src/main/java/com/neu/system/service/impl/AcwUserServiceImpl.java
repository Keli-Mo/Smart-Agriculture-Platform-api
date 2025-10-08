package com.neu.system.service.impl;

import java.util.List;
import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwUserMapper;
import com.neu.system.domain.AcwUser;
import com.neu.system.service.IAcwUserService;

/**
 * 用户信息Service业务层处理
 * 
 * @author lx
 * @date 2025-02-11
 */
@Service
public class AcwUserServiceImpl implements IAcwUserService 
{
    @Autowired
    private AcwUserMapper acwUserMapper;

    /**
     * 查询用户信息
     * 
     * @param id 用户信息ID
     * @return 用户信息
     */
    @Override
    public AcwUser selectAcwUserById(Long id)
    {
        return acwUserMapper.selectAcwUserById(id);
    }

    /**
     * 查询用户信息列表
     * 
     * @param acwUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<AcwUser> selectAcwUserList(AcwUser acwUser)
    {
        return acwUserMapper.selectAcwUserList(acwUser);
    }

    /**
     * 新增用户信息
     * 
     * @param acwUser 用户信息
     * @return 结果
     */
    @Override
    public int insertAcwUser(AcwUser acwUser)
    {
        acwUser.setCreateTime(DateUtils.getNowDate());
        return acwUserMapper.insertAcwUser(acwUser);
    }

    /**
     * 修改用户信息
     * 
     * @param acwUser 用户信息
     * @return 结果
     */
    @Override
    public int updateAcwUser(AcwUser acwUser)
    {
        acwUser.setUpdateTime(DateUtils.getNowDate());
        return acwUserMapper.updateAcwUser(acwUser);
    }

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的用户信息ID
     * @return 结果
     */
    @Override
    public int deleteAcwUserByIds(Long[] ids)
    {
        return acwUserMapper.deleteAcwUserByIds(ids);
    }

    /**
     * 删除用户信息信息
     * 
     * @param id 用户信息ID
     * @return 结果
     */
    @Override
    public int deleteAcwUserById(Long id)
    {
        return acwUserMapper.deleteAcwUserById(id);
    }
}
