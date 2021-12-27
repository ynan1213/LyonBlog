/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.spring.autoconfigure;

import javax.annotation.PostConstruct;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.MQAdmin;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(RocketMQProperties.class)
@ConditionalOnClass({MQAdmin.class})
@ConditionalOnProperty(prefix = "rocketmq", value = "name-server", matchIfMissing = true)
@Import({
    // 消息转换器
    MessageConverterConfiguration.class,
    // 解析 @RocketMQMessageListener 注解的 bean
    ListenerContainerConfiguration.class,
    ExtProducerResetConfiguration.class,
    ExtConsumerResetConfiguration.class,
    // 为RocketMQTemplate 设置事务 TransactionListener
    RocketMQTransactionConfiguration.class
})
@AutoConfigureAfter({MessageConverterConfiguration.class})
@AutoConfigureBefore({RocketMQTransactionConfiguration.class})
public class RocketMQAutoConfiguration implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(RocketMQAutoConfiguration.class);

    public static final String ROCKETMQ_TEMPLATE_DEFAULT_GLOBAL_NAME = "rocketMQTemplate";
    public static final String PRODUCER_BEAN_NAME = "defaultMQProducer";
    public static final String CONSUMER_BEAN_NAME = "defaultLitePullConsumer";

    @Autowired
    private Environment environment;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void checkProperties() {
        String nameServer = environment.getProperty("rocketmq.name-server", String.class);
        log.debug("rocketmq.nameServer = {}", nameServer);
        if (nameServer == null) {
            log.warn("The necessary spring property 'rocketmq.name-server' is not defined, all rockertmq beans creation are skipped!");
        }
    }

    @Bean(PRODUCER_BEAN_NAME) // defaultMQProducer
    @ConditionalOnMissingBean(DefaultMQProducer.class)
    @ConditionalOnProperty(prefix = "rocketmq", value = {"name-server", "producer.group"})
    public DefaultMQProducer defaultMQProducer(RocketMQProperties rocketMQProperties) {

        RocketMQProperties.Producer producerConfig = rocketMQProperties.getProducer();
        String nameServer = rocketMQProperties.getNameServer();
        String groupName = producerConfig.getGroup();
        Assert.hasText(nameServer, "[rocketmq.name-server] must not be null");
        Assert.hasText(groupName, "[rocketmq.producer.group] must not be null");

        String accessChannel = rocketMQProperties.getAccessChannel();

        String ak = rocketMQProperties.getProducer().getAccessKey();
        String sk = rocketMQProperties.getProducer().getSecretKey();

        // 消息轨迹开关，默认开启
        boolean isEnableMsgTrace = rocketMQProperties.getProducer().isEnableMsgTrace();
        // 消息轨迹默认主题：RMQ_SYS_TRACE_TOPIC
        String customizedTraceTopic = rocketMQProperties.getProducer().getCustomizedTraceTopic();

        // 默认的类型：TransactionMQProducer
        DefaultMQProducer producer = RocketMQUtil.createDefaultMQProducer(groupName, ak, sk, isEnableMsgTrace, customizedTraceTopic);

        producer.setNamesrvAddr(nameServer);
        if (!StringUtils.isEmpty(accessChannel)) {
            producer.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }
        producer.setSendMsgTimeout(producerConfig.getSendMessageTimeout());
        producer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(producerConfig.getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMessageBodyThreshold());
        producer.setRetryAnotherBrokerWhenNotStoreOK(producerConfig.isRetryNextServer());
        return producer;
    }

    @Bean(CONSUMER_BEAN_NAME)// defaultLitePullConsumer
    @ConditionalOnMissingBean(DefaultLitePullConsumer.class)
    @ConditionalOnProperty(prefix = "rocketmq", value = {"name-server", "consumer.group", "consumer.topic"})
    public DefaultLitePullConsumer defaultLitePullConsumer(RocketMQProperties rocketMQProperties) throws MQClientException {
        RocketMQProperties.Consumer consumerConfig = rocketMQProperties.getConsumer();
        String nameServer = rocketMQProperties.getNameServer();
        String groupName = consumerConfig.getGroup();
        String topicName = consumerConfig.getTopic();
        Assert.hasText(nameServer, "[rocketmq.name-server] must not be null");
        Assert.hasText(groupName, "[rocketmq.consumer.group] must not be null");
        Assert.hasText(topicName, "[rocketmq.consumer.topic] must not be null");

        String accessChannel = rocketMQProperties.getAccessChannel();

        // BROADCASTING or CLUSTERING
        MessageModel messageModel = MessageModel.valueOf(consumerConfig.getMessageModel());
        // TAG or SQL92
        SelectorType selectorType = SelectorType.valueOf(consumerConfig.getSelectorType());
        // 过滤表达式
        String selectorExpression = consumerConfig.getSelectorExpression();
        String ak = consumerConfig.getAccessKey();
        String sk = consumerConfig.getSecretKey();

        // 单词拉取消息条数
        int pullBatchSize = consumerConfig.getPullBatchSize();

        DefaultLitePullConsumer litePullConsumer = RocketMQUtil.createDefaultLitePullConsumer(nameServer, accessChannel,
            groupName, topicName, messageModel, selectorType, selectorExpression, ak, sk, pullBatchSize);
        return litePullConsumer;
    }

    @Bean(destroyMethod = "destroy")
    // 容器中只要任一存在 DefaultMQProducer 和 DefaultLitePullConsumer
    @Conditional(ProducerOrConsumerPropertyCondition.class)
    // 保证容器中只会存在一个 name=rocketMQTemplate 的 bean
    @ConditionalOnMissingBean(name = ROCKETMQ_TEMPLATE_DEFAULT_GLOBAL_NAME)
    public RocketMQTemplate rocketMQTemplate(RocketMQMessageConverter rocketMQMessageConverter) {
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        if (applicationContext.containsBean(PRODUCER_BEAN_NAME)) {
            rocketMQTemplate.setProducer((DefaultMQProducer) applicationContext.getBean(PRODUCER_BEAN_NAME));
        }
        if (applicationContext.containsBean(CONSUMER_BEAN_NAME)) {
            rocketMQTemplate.setConsumer((DefaultLitePullConsumer) applicationContext.getBean(CONSUMER_BEAN_NAME));
        }
        rocketMQTemplate.setMessageConverter(rocketMQMessageConverter.getMessageConverter());
        return rocketMQTemplate;
    }

    /**
     * AnyNestedCondition：内部带有@ConditionalOnBean的方法任意一个生效就生效
     * AllNestedConditions：内部所有的都要生效才生效
     */
    static class ProducerOrConsumerPropertyCondition extends AnyNestedCondition {

        public ProducerOrConsumerPropertyCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnBean(DefaultMQProducer.class)
        static class DefaultMQProducerExistsCondition {

        }

        @ConditionalOnBean(DefaultLitePullConsumer.class)
        static class DefaultLitePullConsumerExistsCondition {

        }
    }
}
