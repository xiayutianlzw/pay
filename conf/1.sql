create table order(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
sid BIGINT Not NULL
COMMENT '商户id',
service VARCHAR(255) NOT NULL
COMMENT '服务名称',
out_trade_no VARCHAR(255) NOT NULL
COMMENT '商户订单号',
trade_state INT NOT NULL
COMMENT '交易状态',
trade_mode INT NOT NULL
COMMENT '交易方式',
fee FLOAT NOT NULL
COMMENT '交易金额',
timestamp BIGINT NOT NULL
COMMENT '交易创建时间戳',
trade_no VARCHAR(255) NOT NULL
COMMENT '第三方支付产生的交易号'
)
ENGINE = InnoDB, DEFAULT CHARSET = utf8, AUTO_INCREMENT = 0;