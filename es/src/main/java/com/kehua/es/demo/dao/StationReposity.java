package com.kehua.es.demo.dao;

import com.kehua.es.demo.entity.NeStation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StationReposity extends ElasticsearchRepository<NeStation, Long> {
}
