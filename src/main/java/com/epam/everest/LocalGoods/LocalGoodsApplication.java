package com.epam.everest.LocalGoods;

import com.epam.everest.LocalGoods.config.RsaConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(RsaConfigurationProperties.class)
public class LocalGoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalGoodsApplication.class, args);
	}

}
