package com.liao;

import com.alibaba.fastjson.JSON;
import com.liao.pojo.User;
import com.liao.util.EsConst;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EsApiApplicationTests {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //创建索引
    @Test
    void createIndex(){
        try {
            //创建索引请求
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(EsConst.LIAO_INDEX);
            //执行创建索引请求
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询索引是否存在
    @Test
    void existIndex(){
        try {
            //获取索引链接
            GetIndexRequest getIndexRequest = new GetIndexRequest(EsConst.LIAO_INDEX);
            //执行索引是否存在
            boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            System.out.println(exists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除索引
    @Test
    void deleteIndex(){
        try {
            //获取删除所有链接
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(EsConst.LIAO_INDEX);
            //执行删除所有操作
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            System.out.println(acknowledgedResponse.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //添加文档  /liao_index/_doc/1
    @Test
    void addDocument(){
        try {
            IndexRequest indexRequest = new IndexRequest(EsConst.LIAO_INDEX);
            indexRequest.id("1");//主键
            indexRequest.timeout(TimeValue.timeValueSeconds(1));//设置超时时间
            //把对象传入进去
            indexRequest.source(JSON.toJSONString(new User("凯林", 12)),XContentType.JSON);
            //执行
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            //toString
            System.out.println(indexResponse.toString());
            //返回状态
            System.out.println(indexResponse.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取文档，判断文档是否存在
    @Test
    void existDocument(){
        try {
            GetRequest getRequest = new GetRequest(EsConst.LIAO_INDEX, "1");
            //不返回source的上下文
            getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
            getRequest.storedFields("_none_");
            //
            boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
            System.out.println(exists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取文档内容
    @Test
    void getDocument() throws IOException {
        GetRequest getRequest = new GetRequest(EsConst.LIAO_INDEX, "1");
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(response.getSourceAsString());
    }

    //更新文档内容
    @Test
    void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(EsConst.LIAO_INDEX, "1");
        updateRequest.timeout("1s");//超时时间
        updateRequest.doc(JSON.toJSONString(new User("爱你", 111)), XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(update);
        System.out.println(update.status());
    }

    //删除文档
    @Test
    void deleteDocument() throws IOException{
        DeleteRequest request = new DeleteRequest(EsConst.LIAO_INDEX, "1");
        request.timeout("1s");//设置超时时间
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response);
    }

    //批量插入
    @Test
    void bulkDocument() throws  IOException{
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        List<User> userList = new ArrayList<User>(){
            {
                add(new User("xiao1", 2));
                add(new User("xiao2", 3));
                add(new User("xiao3", 4));
            }
        };
        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(
                    new IndexRequest(EsConst.LIAO_INDEX)
                            //.id(i+"")
                            .source(JSON.toJSONString(userList.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures());//返回false标识执行成功
    }

    //查询
    @Test
    void searchDocument() throws IOException{
        //构建查询请求
        SearchRequest searchRequest = new SearchRequest();
        //查询资源构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置超时时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //精确查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "xiao1");
        //MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        searchSourceBuilder.from(0);//起始页
        searchSourceBuilder.size(10);//最大容量
        //添加查询条件
        searchSourceBuilder.query(termQueryBuilder);
        //添加查询条件
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(search.getHits()));
        for (SearchHit hit : search.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
