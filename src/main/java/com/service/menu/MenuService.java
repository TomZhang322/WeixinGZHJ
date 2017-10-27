package com.service.menu;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 菜单操作接口
 */
public interface MenuService {

    public JSONObject getMenu(String accessToken);

    public int createMenu(Map<String, Object> menu, String accessToken);

    public int deleteMenu(String accessToken);
}
