package com.neu.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwMessagesMapper;
import com.neu.system.domain.AcwMessages;
import com.neu.system.service.IAcwMessagesService;

/**
 * 消息Service业务层处理
 * 
 * @author neu
 * @date 2025-05-13
 */
@Service
public class AcwMessagesServiceImpl implements IAcwMessagesService 
{
    @Autowired
    private AcwMessagesMapper acwMessagesMapper;

    /**
     * 查询消息
     * 
     * @param id 消息ID
     * @return 消息
     */
    @Override
    public AcwMessages selectAcwMessagesById(Long id)
    {
        return acwMessagesMapper.selectAcwMessagesById(id);
    }

    /**
     * 查询消息列表
     * 
     * @param acwMessages 消息
     * @return 消息
     */
    @Override
    public List<AcwMessages> selectAcwMessagesList(AcwMessages acwMessages)
    {
        return acwMessagesMapper.selectAcwMessagesList(acwMessages);
    }

    /**
     * 新增消息
     * 
     * @param acwMessages 消息
     * @return 结果
     */
    @Override
    public int insertAcwMessages(AcwMessages acwMessages)
    {
        return acwMessagesMapper.insertAcwMessages(acwMessages);
    }

    /**
     * 修改消息
     * 
     * @param acwMessages 消息
     * @return 结果
     */
    @Override
    public int updateAcwMessages(AcwMessages acwMessages)
    {
        return acwMessagesMapper.updateAcwMessages(acwMessages);
    }

    /**
     * 批量删除消息
     * 
     * @param ids 需要删除的消息ID
     * @return 结果
     */
    @Override
    public int deleteAcwMessagesByIds(Long[] ids)
    {
        return acwMessagesMapper.deleteAcwMessagesByIds(ids);
    }

    /**
     * 删除消息信息
     * 
     * @param id 消息ID
     * @return 结果
     */
    @Override
    public int deleteAcwMessagesById(Long id)
    {
        return acwMessagesMapper.deleteAcwMessagesById(id);
    }
}
