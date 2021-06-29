package com.bjpowernode.crm.settings.workbench.domain;

import lombok.Data;

@Data
public class Stage {
    private String type;//展示阶段图标的样式
    private String stage;//展示交易阶段的内容
    private String possibility;//展示阶段的可能性
    private String index;//当前阶段图标在整个图标中的索引位置
}
