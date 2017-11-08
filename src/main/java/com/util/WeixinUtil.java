package com.util;

import com.adcc.utility.log.Log;
import com.model.accesstoken.AccessToken;
import com.model.menu.Button;
import com.model.menu.ClickButton;
import com.model.menu.Menu;
import com.model.menu.ViewButton;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Created by Administrator on 2016/11/7.
 */
public class WeixinUtil {

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            Log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            Log.error("https request error:{}", e);
        }
        return jsonObject;
    }

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    // 菜单创建（POST） 限1000（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 获取access_token
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setAccess_token(jsonObject.getString("access_token"));
                accessToken.setExpires_in(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                Log.error("获取token失败 errcode:{} errmsg:{}"+ jsonObject.getInt("errcode")+ jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }

    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton button11 = new ClickButton();
        button11.setName("监控项一");
        button11.setType("click");
        button11.setKey("11");

        ClickButton button12 = new ClickButton();
        button12.setName("监控项二");
        button12.setType("click");
        button12.setKey("12");

        ClickButton button13 = new ClickButton();
        button13.setName("监控项三");
        button13.setType("click");
        button13.setKey("13");

        ClickButton button14 = new ClickButton();
        button14.setName("监控项四");
        button14.setType("click");
        button14.setKey("14");

        ClickButton button21 = new ClickButton();
        button21.setName("指南一");
        button21.setType("click");
        button21.setKey("21");

        ClickButton button22 = new ClickButton();
        button22.setName("指南二");
        button22.setType("click");
        button22.setKey("22");

        ClickButton button23 = new ClickButton();
        button23.setName("指南三");
        button23.setType("click");
        button23.setKey("23");

        ClickButton button24 = new ClickButton();
        button24.setName("指南四");
        button24.setType("click");
        button24.setKey("24");

        ClickButton button25 = new ClickButton();
        button25.setName("指南五");
        button25.setType("click");
        button25.setKey("25");

        ViewButton button31 = new ViewButton();
        button31.setName("登录");
        button31.setType("view");
        button31.setUrl("http://weixinzp.ngrok.xiaomiqiu.cn/loginPage");

        ViewButton button32 = new ViewButton();
        button32.setName("APP下载");
        button32.setType("view");
        button32.setUrl("http://www.baidu.com");

        ViewButton button33 = new ViewButton();
        button33.setName("用户信息");
        button33.setType("view");
        button33.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx83dad0407e617c02&redirect_uri=http://weixinzp.ngrok.xiaomiqiu.cn/userInfo.do&response_type=code&scope=snsapi_userinfo&state=STAT#wechat_redirect");

        ClickButton button34 = new ClickButton();
        button34.setName("扫码事件");
        button34.setType("scancode_push");
        button34.setKey("34");

        ClickButton button35 = new ClickButton();
        button35.setName("地理位置");
        button35.setType("location_select");
        button35.setKey("35");

        Button button1 = new Button();
        button1.setName("网关监控");
        button1.setSub_button(new Button[]{button11,button12,button13,button14});

        Button button2 = new Button();
        button2.setName("办理指南");
        button2.setSub_button(new Button[]{button21,button22,button23,button24,button25});

        Button button3 = new Button();
        button3.setName("个人中心");
        button3.setSub_button(new Button[]{button31,button32,button33,button34,button35});

        menu.setButton(new Button[]{button1,button2,button3});
        return menu;
    }

    public static int createMenu(Menu menu, String accessToken){
        int result = 0;
        try {
            // 拼装创建菜单的url
            String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
            // 将菜单对象转换成json字符串
            String jsonMenu = JSONObject.fromObject(menu).toString();
            // 调用接口创建菜单
            JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu);
            if (null != jsonObject) {
                if (0 != jsonObject.getInt("errcode")) {
                    result = jsonObject.getInt("errcode");
                    Log.error("创建菜单失败 errcode:{} errmsg:{}"+ jsonObject.getInt("errcode")+ jsonObject.getString("errmsg"));
                    Log.error("****"+jsonMenu+"****");
                }
            }
        } catch (Exception e) {
            Log.error("createMenu method error", e);
        }
        return result;
    }

}