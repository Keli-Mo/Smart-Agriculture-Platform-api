package com.neu.system.service.impl;

import java.util.List;
import com.neu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neu.system.mapper.AcwArticleMapper;
import com.neu.system.domain.AcwArticle;
import com.neu.system.service.IAcwArticleService;

/**
 * 文章管理Service业务层处理
 * 
 * @author neu
 * @date 2025-03-14
 */
@Service
public class AcwArticleServiceImpl implements IAcwArticleService 
{
    @Autowired
    private AcwArticleMapper acwArticleMapper;

    /**
     * 查询文章管理
     * 
     * @param id 文章管理ID
     * @return 文章管理
     */
    @Override
    public AcwArticle selectAcwArticleById(Long id)
    {
        return acwArticleMapper.selectAcwArticleById(id);
    }

    /**
     * 查询文章管理列表
     * 
     * @param acwArticle 文章管理
     * @return 文章管理
     */
    @Override
    public List<AcwArticle> selectAcwArticleList(AcwArticle acwArticle)
    {
        return acwArticleMapper.selectAcwArticleList(acwArticle);
    }

    /**
     * 新增文章管理
     * 
     * @param acwArticle 文章管理
     * @return 结果
     */
    @Override
    public int insertAcwArticle(AcwArticle acwArticle)
    {
        acwArticle.setCreateTime(DateUtils.getNowDate());
        return acwArticleMapper.insertAcwArticle(acwArticle);
    }

    /**
     * 修改文章管理
     * 
     * @param acwArticle 文章管理
     * @return 结果
     */
    @Override
    public int updateAcwArticle(AcwArticle acwArticle)
    {
        return acwArticleMapper.updateAcwArticle(acwArticle);
    }

    /**
     * 批量删除文章管理
     * 
     * @param ids 需要删除的文章管理ID
     * @return 结果
     */
    @Override
    public int deleteAcwArticleByIds(Long[] ids)
    {
        return acwArticleMapper.deleteAcwArticleByIds(ids);
    }

    /**
     * 删除文章管理信息
     * 
     * @param id 文章管理ID
     * @return 结果
     */
    @Override
    public int deleteAcwArticleById(Long id)
    {
        return acwArticleMapper.deleteAcwArticleById(id);
    }
}
