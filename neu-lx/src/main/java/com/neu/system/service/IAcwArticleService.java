package com.neu.system.service;

import java.util.List;
import com.neu.system.domain.AcwArticle;

/**
 * 文章管理Service接口
 * 
 * @author neu
 * @date 2025-03-14
 */
public interface IAcwArticleService 
{
    /**
     * 查询文章管理
     * 
     * @param id 文章管理ID
     * @return 文章管理
     */
    public AcwArticle selectAcwArticleById(Long id);

    /**
     * 查询文章管理列表
     * 
     * @param acwArticle 文章管理
     * @return 文章管理集合
     */
    public List<AcwArticle> selectAcwArticleList(AcwArticle acwArticle);

    /**
     * 新增文章管理
     * 
     * @param acwArticle 文章管理
     * @return 结果
     */
    public int insertAcwArticle(AcwArticle acwArticle);

    /**
     * 修改文章管理
     * 
     * @param acwArticle 文章管理
     * @return 结果
     */
    public int updateAcwArticle(AcwArticle acwArticle);

    /**
     * 批量删除文章管理
     * 
     * @param ids 需要删除的文章管理ID
     * @return 结果
     */
    public int deleteAcwArticleByIds(Long[] ids);

    /**
     * 删除文章管理信息
     * 
     * @param id 文章管理ID
     * @return 结果
     */
    public int deleteAcwArticleById(Long id);
}
