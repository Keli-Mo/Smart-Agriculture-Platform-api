package com.neu.system.service;

import java.util.List;
import com.neu.system.domain.AcwChatMessages;

/**
 * 聊天记录Service接口
 * 
 * @author neu
 * @date 2025-05-14
 */
public interface IAcwChatMessagesService 
{
    /**
     * 查询聊天记录
     * 
     * @param id 聊天记录ID
     * @return 聊天记录
     */
    public AcwChatMessages selectAcwChatMessagesById(Long id);

    /**
     * 查询聊天记录列表
     * 
     * @param acwChatMessages 聊天记录
     * @return 聊天记录集合
     */
    public List<AcwChatMessages> selectAcwChatMessagesList(AcwChatMessages acwChatMessages);

    /**
     * 新增聊天记录
     * 
     * @param acwChatMessages 聊天记录
     * @return 结果
     */
    public int insertAcwChatMessages(AcwChatMessages acwChatMessages);

    /**
     * 修改聊天记录
     * 
     * @param acwChatMessages 聊天记录
     * @return 结果
     */
    public int updateAcwChatMessages(AcwChatMessages acwChatMessages);

    /**
     * 批量删除聊天记录
     * 
     * @param ids 需要删除的聊天记录ID
     * @return 结果
     */
    public int deleteAcwChatMessagesByIds(Long[] ids);

    /**
     * 删除聊天记录信息
     * 
     * @param id 聊天记录ID
     * @return 结果
     */
    public int deleteAcwChatMessagesById(Long id);
}
