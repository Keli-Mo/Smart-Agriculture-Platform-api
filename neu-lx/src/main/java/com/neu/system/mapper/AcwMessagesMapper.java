package com.neu.system.mapper;

import java.util.List;
import com.neu.system.domain.AcwMessages;

/**
 * 消息Mapper接口
 * 
 * @author neu
 * @date 2025-05-13
 */
public interface AcwMessagesMapper 
{
    /**
     * 查询消息
     * 
     * @param id 消息ID
     * @return 消息
     */
    public AcwMessages selectAcwMessagesById(Long id);

    /**
     * 查询消息列表
     * 
     * @param acwMessages 消息
     * @return 消息集合
     */
    public List<AcwMessages> selectAcwMessagesList(AcwMessages acwMessages);

    /**
     * 新增消息
     * 
     * @param acwMessages 消息
     * @return 结果
     */
    public int insertAcwMessages(AcwMessages acwMessages);

    /**
     * 修改消息
     * 
     * @param acwMessages 消息
     * @return 结果
     */
    public int updateAcwMessages(AcwMessages acwMessages);

    /**
     * 删除消息
     * 
     * @param id 消息ID
     * @return 结果
     */
    public int deleteAcwMessagesById(Long id);

    /**
     * 批量删除消息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcwMessagesByIds(Long[] ids);
}
