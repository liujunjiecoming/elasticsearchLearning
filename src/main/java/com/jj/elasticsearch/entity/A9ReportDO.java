package com.jj.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName A9ReportDO
 * @Description TODO
 * @Author JJLiu
 * @Date 19-7-15 下午1:48
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class A9ReportDO {

    private Long clicks;

    private Long searches;

    private Double sales;

    private Double salesVolume;

}
