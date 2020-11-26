package com.liao.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.liao.es.pojo.Goods;
import com.liao.es.service.GoodsService;
import com.liao.es.util.JsoupUtil;
import com.liao.util.ElasticSearchConst;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 商品实现类
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private JsoupUtil jsoupUtil;
    /**
     * 添加商品到elasticsearch
     *
     * @param keyword
     */
    @Override
    public Boolean parse(String keyword) {
        try {
            //构建批量请求
            BulkRequest bulkRequest = new BulkRequest();
            //设置超时时间120s
            bulkRequest.timeout(new TimeValue(2, TimeUnit.MINUTES));
            //爬取京东数据
            List<Goods> goodList = jsoupUtil.parse(keyword);
            goodList.forEach(good -> {
                bulkRequest.add(
                        new IndexRequest(ElasticSearchConst.IMG_INDEX)
                        .source(JSON.toJSONString(good), XContentType.JSON)
                );
            });
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return !bulkResponse.hasFailures();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 分页查询商品
     *
     * @param keyword
     * @param pageIndex
     * @param pageSize
     */
    @Override
    public Map<String, Object> search(String keyword, Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> goods = new ArrayList<>();//商品集合
        long total = 0;//总数
        try {
            if(null == pageIndex || pageIndex < 1){
                pageIndex = 1;
            }
            if(null == pageSize || pageSize < 1){
                pageSize = 10;
            }
            //构建查询请求
            SearchRequest searchRequest = new SearchRequest(ElasticSearchConst.IMG_INDEX);
            //查询source构造器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //起始页
            searchSourceBuilder.from(pageIndex - 1);
            //页码容量
            searchSourceBuilder.size(pageSize);
            //设置高亮
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title");
            highlightBuilder.preTags("<span style='color:red'>");
            highlightBuilder.postTags("</span>");
            highlightBuilder.requireFieldMatch(false);//如果要多个字段高亮,这项要为false
            searchSourceBuilder.highlighter(highlightBuilder);
            //查询构造器，精准匹配
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
            //设置查询构造器
            searchSourceBuilder.query(termQueryBuilder);
            //设置超时间30秒
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //执行搜索
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(searchResponse));
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //高亮字段
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField title = highlightFields.get("title");
                String text = "";
                if(null != title){
                    Text[] fragments = title.getFragments();
                    for (Text fragment : fragments) {
                        text += fragment;
                    }
                    //替换之前的字段
                    sourceAsMap.put("title",text);
                }
                goods.add(sourceAsMap);
            }
            //查询总数
            CountRequest countRequest = new CountRequest(ElasticSearchConst.IMG_INDEX);
            countRequest.query(termQueryBuilder);
            CountResponse countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
            total = countResponse.getCount();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            map.put("goods", goods);
            map.put("total", total);
            return map;
        }
    }
}
