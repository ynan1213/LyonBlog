CREATE TABLE `stk_shelf_locked`
(
    `id`               bigint                                  NOT NULL AUTO_INCREMENT,
    `sku_id`           bigint                                  NOT NULL COMMENT '货品id',
    `lot_id`           bigint                                  NOT NULL COMMENT '批次ID',
    `loc_id`           bigint                                  NOT NULL COMMENT '库位ID',
    `lpn_no`           varchar(50) COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'LPN号',
    `qty_locked`       decimal(20, 3)                                   DEFAULT NULL COMMENT '锁定库存',
    `attach_info`      varchar(256) COLLATE utf8mb4_general_ci          DEFAULT NULL,
    `warehouse_id`     bigint                                  NOT NULL COMMENT '仓库ID',
    `version`          int                                     NOT NULL DEFAULT '0' COMMENT '版本号',
    `create_time`      timestamp                               NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`        varchar(60) COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'sys' COMMENT '创建人',
    `update_time`      timestamp                               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`        varchar(60) COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'sys' COMMENT '更新人',
    `is_deleted`       bigint                                  NOT NULL DEFAULT '0',
    `corporation_code` varchar(32) COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '1014' COMMENT '主体编码',
    `corporation_name` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '上海雨生百谷食品有限公司' COMMENT '主体名称',
    `tenant_code`      varchar(32) COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'DDGY' COMMENT '租户编码',
    PRIMARY KEY (`id`) USING BTREE,
    KEY                `idx_sku_lot_loc_lpn` (`sku_id`,`lot_id`,`loc_id`,`lpn_no`,`warehouse_id`) USING BTREE,
    KEY                `idx_update_time` (`update_time`) USING BTREE,
    KEY                `idc_warehouse_loc_id` (`warehouse_id`,`loc_id`,`lpn_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1172489696060674049 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='锁定库存'