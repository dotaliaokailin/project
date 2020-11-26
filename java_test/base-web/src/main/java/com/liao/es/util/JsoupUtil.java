package com.liao.es.util;

import com.liao.es.pojo.Goods;
import org.apache.poi.ss.formula.functions.T;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * jsoup爬取网页资源工具
 */
@Component
public class JsoupUtil {

    /**
     * 解析京东网页内容
     * @param searchName 查询名称
     * @return
     */
    public List<Goods> parse(String searchName){
        List<Goods> goods = new ArrayList<>();
        try{
            String url = "https://search.jd.com/Search?keyword="+searchName;
            Document parse = Jsoup.parse(new URL(url), 30000);
            Element element = parse.getElementById("J_goodsList");
            Elements lis = element.getElementsByTag("li");
            lis.forEach(li -> {
                //图片
                String img = li.getElementsByTag("img").get(0).attr("data-lazy-img");
                //标题
                String title = li.getElementsByClass("p-name").get(0).text();
                //价格
                String price = li.getElementsByClass("p-price").get(0).text();
                Goods good = new Goods(title, img, price);
                goods.add(good);
            });
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            return goods;
        }
    }
}
