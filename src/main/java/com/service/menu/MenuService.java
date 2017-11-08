package com.service.menu;

import com.model.menu.Menu;
import net.sf.json.JSONObject;

/**
 * 菜单操作接口
 */
public interface MenuService {

    public JSONObject getMenu(String accessToken);

    public int createMenu(Menu menu, String accessToken);

    public int deleteMenu(String accessToken);
}
