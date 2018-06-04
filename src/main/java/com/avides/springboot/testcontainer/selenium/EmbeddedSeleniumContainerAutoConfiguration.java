package com.avides.springboot.testcontainer.selenium;

import static com.avides.springboot.testcontainer.selenium.SeleniumProperties.BEAN_NAME;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import com.avides.springboot.testcontainer.common.container.EmbeddedContainer;

@Configuration
@ConditionalOnProperty(name = "embedded.container.selenium.enabled", matchIfMissing = true)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(SeleniumProperties.class)
public class EmbeddedSeleniumContainerAutoConfiguration
{
    @ConditionalOnMissingBean(DefaultSeleniumContainer.class)
    @Bean(name = BEAN_NAME)
    public EmbeddedContainer seleniumContainer(ConfigurableEnvironment environment, SeleniumProperties properties)
    {
        if (StringUtils.isEmpty(properties.getDockerImage()))
        {
            properties.setDockerImage(SeleniumBrowserType.getByProperty(properties.getBrowserName()).getDockerImage());
        }

        return new DefaultSeleniumContainer("selenium", environment, properties);
    }
}