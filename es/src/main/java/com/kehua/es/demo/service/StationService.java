package com.kehua.es.demo.service;

import com.kehua.es.demo.dao.StationReposity;
import com.kehua.es.demo.entity.NeStation;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {

    @Resource
    private StationReposity elasticRepository;
    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;



    public void add(){
        elasticRepository.save(new NeStation().setId(System.currentTimeMillis()).setName("测试电站666888").setCompanyId(4L));
    }


    public void delete(){
        elasticRepository.deleteById(1L);
    }


    public List<NeStation> list(){
        Iterable<NeStation> stations = elasticRepository.findAll();
        List<NeStation> res = new ArrayList<>();
        stations.forEach(res::add);

        return res;
    }


    public List<NeStation> listPage(Integer pageNum, Integer pageSize){
        PageRequest request = PageRequest.of(pageNum, pageSize);


        Iterable<NeStation> stations = elasticRepository.findAll(request);
        List<NeStation> res = new ArrayList<>();
        stations.forEach(res::add);

        return res;
    }


    public List<NeStation> nativeQuery(Integer pageNum, Integer pageSize){

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery("name", "666888"));

        //注：Pageable类中 pageNum需要减1,如果是第一页 数值为0
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        nativeSearchQueryBuilder.withPageable(pageable);

        SearchHits<NeStation> searchHitsResult = elasticsearchTemplate.search(nativeSearchQueryBuilder.build(), NeStation.class);
        //7.获取分页数据
        SearchPage<NeStation> searchPageResult = SearchHitSupport.searchPageFor(searchHitsResult, pageable);

        List<NeStation> res = new ArrayList<>();
        searchPageResult.getContent().forEach(x -> res.add(x.getContent()));

        return res;
    }


}
