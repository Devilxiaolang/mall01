package com.company.service.iservice;

import com.company.Commons.ServerResponse;
import com.company.dao.pojo.Category;

import java.util.List;

public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndDeepChildrenCategory(Integer categoryId);
}
