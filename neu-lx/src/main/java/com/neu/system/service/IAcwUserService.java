package com.neu.system.service;

import java.util.List;
import com.neu.system.domain.AcwUser;

/**
 * 用户信息Service接口
 * 
 * @author lx
 * @date 2025-02-11
 */
public interface IAcwUserService 
{
    /**
     * 查询用户信息
     * 
     * @param id 用户信息ID
     * @return 用户信息
     */
    public AcwUser selectAcwUserById(Long id);

    /**
     * 查询用户信息列表
     * 
     * @param acwUser 用户信息
     * @return 用户信息集合
     */
    public List<AcwUser> selectAcwUserList(AcwUser acwUser);

    /**
     * 新增用户信息
     * 
     * @param acwUser 用户信息
     * @return 结果
     */
    public int insertAcwUser(AcwUser acwUser);

    /**
     * 修改用户信息
     * 
     * @param acwUser 用户信息
     * @return 结果
     */
    public int updateAcwUser(AcwUser acwUser);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的用户信息ID
     * @return 结果
     */
    public int deleteAcwUserByIds(Long[] ids);

    /**
     * 删除用户信息信息
     * 
     * @param id 用户信息ID
     * @return 结果
     */
    public int deleteAcwUserById(Long id);
}
