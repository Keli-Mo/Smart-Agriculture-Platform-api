package com.neu.system.service.impl;

import java.util.List;
import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwChatMessagesMapper;
import com.neu.system.domain.AcwChatMessages;
import com.neu.system.service.IAcwChatMessagesService;

/**
 * 聊天记录Service业务层处理
 * 
 * @author neu
 * @date 2025-05-14
 */
@Service
public class AcwChatMessagesServiceImpl implements IAcwChatMessagesService 
{
    @Autowired
    private AcwChatMessagesMapper acwChatMessagesMapper;

    /**
     * 查询聊天记录
     * 
     * @param id 聊天记录ID
     * @return 聊天记录
     */
    @Override
    public AcwChatMessages selectAcwChatMessagesById(Long id)
    {
        return acwChatMessagesMapper.selectAcwChatMessagesById(id);
    }

    /**
     * 查询聊天记录列表
     * 
     * @param acwChatMessages 聊天记录
     * @return 聊天记录
     */
    @Override
    public List<AcwChatMessages> selectAcwChatMessagesList(AcwChatMessages acwChatMessages)
    {
        return acwChatMessagesMapper.selectAcwChatMessagesList(acwChatMessages);
    }

    /**
     * 新增聊天记录
     * 
     * @param acwChatMessages 聊天记录
     * @return 结果
     */
    @Override
    public int insertAcwChatMessages(AcwChatMessages acwChatMessages)
    {
        acwChatMessages.setCreateTime(DateUtils.getNowDate());
        return acwChatMessagesMapper.insertAcwChatMessages(acwChatMessages);
    }

    /**
     * 修改聊天记录
     * 
     * @param acwChatMessages 聊天记录
     * @return 结果
     */
    @Override
    public int updateAcwChatMessages(AcwChatMessages acwChatMessages)
    {
        return acwChatMessagesMapper.updateAcwChatMessages(acwChatMessages);
    }

    /**
     * 批量删除聊天记录
     * 
     * @param ids 需要删除的聊天记录ID
     * @return 结果
     */
    @Override
    public int deleteAcwChatMessagesByIds(Long[] ids)
    {
        return acwChatMessagesMapper.deleteAcwChatMessagesByIds(ids);
    }

    /**
     * 删除聊天记录信息
     * 
     * @param id 聊天记录ID
     * @return 结果
     */
    @Override
    public int deleteAcwChatMessagesById(Long id)
    {
        return acwChatMessagesMapper.deleteAcwChatMessagesById(id);
    }
}
