package com.company.service.impl;

import com.company.Commons.ServerResponse;
import com.company.dao.CategoryMapper;
import com.company.dao.pojo.Category;
import com.company.service.iservice.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {
    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    @Qualifier("categoryMapper")
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createErrorMessageResponse("添加品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createSuccessMessageResponse("添加品类成功");
        } else {
            return ServerResponse.createErrorMessageResponse("添加品类失败");
        }

    }

    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createErrorMessageResponse("添加品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.createSuccessMessageResponse("更改品类名称成功");
        } else {
            return ServerResponse.createErrorMessageResponse("添加品类失败");
        }
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到子类");
        }
        return ServerResponse.createSuccessResponse(categoryList);
    }

    /**
     * 查询本节点的id和子节点的id
     *
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndDeepChildrenCategory(Integer categoryId) {
        //1-guava缓存工具的sets集合对象
        Set<Category> categorySet = Sets.newHashSet();
        //2-递归实现排重 -> pojo类重写hashcode和equals方法更好实现排重
        findChildCategory(categorySet, categoryId);
        //3-guava缓存工具的lists集合对象
        List<Integer> categoryListId = Lists.newArrayList();
        if (categoryId != null) {
            for (Category categoryItem : categorySet) {
                categoryListId.add(categoryItem.getId());
            }
        }
        return ServerResponse.createSuccessResponse(categoryListId);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
