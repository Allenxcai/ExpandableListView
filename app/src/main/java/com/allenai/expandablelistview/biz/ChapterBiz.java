package com.allenai.expandablelistview.biz;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.allenai.expandablelistview.dao.ChapterDao;
import com.allenai.expandablelistview.entiy.Chapter;
import com.allenai.expandablelistview.entiy.ChapterItem;
import com.allenai.expandablelistview.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChapterBiz {

    private static final String TAG = "Allen ChapterBiz";
    private ChapterDao mChapterDao = new ChapterDao();

    public ChapterBiz() {
    }

    public void loadDatas(final Context context, final CallBack callBack,
                          final boolean useCashe) {

        AsyncTask<Boolean, Void, List<Chapter>> asyncTask
                = new AsyncTask<Boolean, Void, List<Chapter>>() {

            private Exception ex;

            //做前
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //做中
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            //后台运行
            @Override
            protected List<Chapter> doInBackground(Boolean... booleans) {
                final List<Chapter> chapters = new ArrayList<>();

                try {
                    // 从缓存中取
                    if (booleans[0]) {
                        //从数据库取出
                        chapters.addAll(mChapterDao.loadFromDb(context));
                        Log.d(TAG, "doInBackground: loadFromDb->"+chapters);
                    }

                    if (chapters.isEmpty()) {
                        //从网络获取
                        final List<Chapter> chaptersFromNet = loadFromNet(context);
                        chapters.addAll(chaptersFromNet);
                        // 缓存到数据库
                        mChapterDao.insertToDb(context,chaptersFromNet);

                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    ex = e;
                    return null;
                }

                return chapters;
            }

            //做后
            @Override
            protected void onPostExecute(List<Chapter> chapters) {
                if (ex != null) {
                    callBack.loadFailed(ex);
                } else {
                    callBack.loadSuccess(chapters);
                }

            }
        };
        asyncTask.execute(useCashe);

    }

    private List<Chapter> loadFromNet(Context context) {
        final String        url      = "http://www.wanandroid.com/tools/mockapi/2/mooc-expandablelistview";
        //

       // String              content  = HttpUtils.doGet(url);

        //
        String content="{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"PullToRefresh\",\n" +
                "          \"pid\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"name\": \"Android 8.0通知栏解决方案\",\n" +
                "          \"pid\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 4,\n" +
                "          \"name\": \"Android 与WebView的js交互12\",\n" +
                "          \"pid\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 8,\n" +
                "          \"name\": \"Android UiAutomator 2.0 入门实战\",\n" +
                "          \"pid\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 10,\n" +
                "          \"name\": \"移动端音频视频入门\",\n" +
                "          \"pid\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Android\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"id\": 11,\n" +
                "          \"name\": \"iOS开发之LeanCloud\",\n" +
                "          \"pid\": 2\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 12,\n" +
                "          \"name\": \"iOS开发之传感器\",\n" +
                "          \"pid\": 2\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 13,\n" +
                "          \"name\": \"iOS开发之网络协议\",\n" +
                "          \"pid\": 2\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 14,\n" +
                "          \"name\": \"iOS之分享集成\",\n" +
                "          \"pid\": 2\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 15,\n" +
                "          \"name\": \"iOS之FTP上传\",\n" +
                "          \"pid\": 2\n" +
                "        }\n" +
                "      ],\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"IOS\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"id\": 16,\n" +
                "          \"name\": \"Unity 3D 翻牌游戏开发\",\n" +
                "          \"pid\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 17,\n" +
                "          \"name\": \"Unity 3D基础之变体Transform\",\n" +
                "          \"pid\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 20,\n" +
                "          \"name\": \"带你开发类似Pokemon Go的AR游戏\",\n" +
                "          \"pid\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 21,\n" +
                "          \"name\": \"Unity 3D游戏开发之脚本系统\",\n" +
                "          \"pid\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 22,\n" +
                "          \"name\": \"Unity 3D地形编辑器\",\n" +
                "          \"pid\": 3\n" +
                "        }\n" +
                "      ],\n" +
                "      \"id\": 3,\n" +
                "      \"name\": \"Unity 3D\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"id\": 25,\n" +
                "          \"name\": \"Cocos2d-x游戏之七夕女神抓捕计划\",\n" +
                "          \"pid\": 4\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 26,\n" +
                "          \"name\": \"Cocos2d-x游戏开发初体验-C++篇\",\n" +
                "          \"pid\": 4\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 27,\n" +
                "          \"name\": \"Cocos2d-x全民俄罗斯\",\n" +
                "          \"pid\": 4\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 28,\n" +
                "          \"name\": \"Cocos2d-x坦克大战\",\n" +
                "          \"pid\": 4\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 30,\n" +
                "          \"name\": \"新春特辑-Cocos抢红包\",\n" +
                "          \"pid\": 4\n" +
                "        }\n" +
                "      ],\n" +
                "      \"id\": 4,\n" +
                "      \"name\": \"Cocos2d-x\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"errorCode\": 0,\n" +
                "  \"errorMsg\": \"\"\n" +
                "}";
        final List<Chapter> chapters = parseContent(content);

        return chapters;

    }


    private List<Chapter> parseContent(String content) {
        List<Chapter> chapters = new ArrayList<>();
        int           errCode;
        String        errMsg;

        try {
            JSONObject jsonObject = new JSONObject(content);

            errCode = jsonObject.getInt("errorCode");
            if (errCode == 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject chapterJson = jsonArray.getJSONObject(i);

                    int     id      = chapterJson.getInt("id");
                    String  name    = chapterJson.getString("name");
                    Chapter chapter = new Chapter(id, name);

                    chapters.add(chapter);

                    JSONArray jsonArrayChild = chapterJson.getJSONArray("children");

                    for (int j = 0; j < jsonArrayChild.length(); j++) {

                        JSONObject chapterChildrenJson = jsonArrayChild.getJSONObject(j);
                        id = chapterChildrenJson.getInt("id");
                        name = chapterChildrenJson.getString("name");
                        ChapterItem chapterItem = new ChapterItem(id, name);
                        chapter.addChild(chapterItem);

                    }
                }

            } else {
                errMsg = jsonObject.getString("errorMsg");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return chapters;
    }


    public static interface CallBack {

        void loadSuccess(List<Chapter> chapters);

        void loadFailed(Exception ex);
    }
}
