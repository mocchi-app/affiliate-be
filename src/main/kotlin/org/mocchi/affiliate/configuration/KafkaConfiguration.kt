package org.mocchi.affiliate.configuration

import com.github.daniel.shuy.kafka.protobuf.serde.KafkaProtobufDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.mocchi.product.v1beta1.ProductOuterClass
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer

@Configuration
class KafkaConfiguration {

    @Bean
    fun kafkaListenerContainerFactory(
            consumerFactory: ConsumerFactory<String, ProductOuterClass.Product>
    ): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ProductOuterClass.Product>> =
            ConcurrentKafkaListenerContainerFactory<String, ProductOuterClass.Product>()
                    .also {
                        it.consumerFactory = consumerFactory
                    }

    @Bean
    fun consumerFactory(kafkaProperties: KafkaProperties): ConsumerFactory<String, ProductOuterClass.Product> =
            DefaultKafkaConsumerFactory(
                    kafkaProperties.buildConsumerProperties(),
                    StringDeserializer(),
                    ErrorHandlingDeserializer(KafkaProtobufDeserializer(ProductOuterClass.Product.parser()))
            )
}