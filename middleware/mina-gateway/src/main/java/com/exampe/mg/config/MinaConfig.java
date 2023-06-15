package com.exampe.mg.config;

import lombok.Data;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.integration.beans.InetSocketAddressEditor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyEditor;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "mina")
@EnableConfigurationProperties
@Data
public class MinaConfig {
    private int port;

    @Bean
    public TcpCodecFactory tcpCodecFactory() {
        // 设置encode & decode
        return new TcpCodecFactory();
    }

    @Bean
    public TcpIoHandler tcpIoHandler() {
        return new TcpIoHandler();
    }

    @Bean
    public ProtocolCodecFilter codecFilter() {
        return new ProtocolCodecFilter(tcpCodecFactory());
    }

    @Bean
    public ExecutorFilter executorFilter() {
        return new ExecutorFilter(20,200);
    }

    @Bean
    public MdcInjectionFilter mdcInjectionFilter() {
        return new MdcInjectionFilter();
    }

    @Bean
    public DefaultIoFilterChainBuilder filterChainBuilder() {
        DefaultIoFilterChainBuilder filterChainBuilder = new DefaultIoFilterChainBuilder();
        Map<String, IoFilter> filterMap = new LinkedHashMap<>();
        filterMap.put("codecFilter", codecFilter());
        filterMap.put("executor", executorFilter());
        filterMap.put("mdcInjectionFilter", mdcInjectionFilter());
        filterChainBuilder.setFilters(filterMap);
        return filterChainBuilder;
    }


    // spring配置文件中的字符串转换成相应的对象进行注入
    @Bean
    public static CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        Map<Class<?>, Class<? extends PropertyEditor>> editors = new HashMap<>();
        editors.put(SocketAddress.class, InetSocketAddressEditor.class);
        customEditorConfigurer.setCustomEditors(editors);
        return customEditorConfigurer;
    }

    @Bean(initMethod = "bind", destroyMethod = "unbind")
    public NioSocketAcceptor nioSocketAcceptor() {
        NioSocketAcceptor nioSocketAcceptor = new NioSocketAcceptor();
        nioSocketAcceptor.setDefaultLocalAddresses(new InetSocketAddress(port));
        nioSocketAcceptor.setReuseAddress(true);
        nioSocketAcceptor.setFilterChainBuilder(filterChainBuilder());
        nioSocketAcceptor.setHandler(tcpIoHandler());
        return nioSocketAcceptor;
    }

}
