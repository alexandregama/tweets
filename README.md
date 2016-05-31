# Spring Boot with Docker

### Gradle minimal dependencies

We will use gradle as dependency manager. To use spring boot with docker we must set up the *Spring Boot Plugin*

Gradle file:

```gradle
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.3.3.RELEASE")
    }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'idea'
apply plugin: 'spring-boot'

jar {
    baseName = 'gs-spring-boot-docker'
    version =  '0.1.0'
}

repositories {
    mavenCentral()
}

sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}

task wrapper(type: Wrapper) {
    gradleVersion = '2.3'
}
```

### Adding a simple REST endpoint

Now we will create a simple REST endpoint to test if our application is working.

```java
@SpringBootApplication
@RestController
public class HelloApplication {

	@RequestMapping("/hello-docker")
	public String home() {
		return "Hello Docker World";
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

}
```

You could access the REST endpoint in the following address:

```bash
http://localhost:8080/hello-docker
```

### Generating a jar by using Gradle

To generate a new jar we must use the **gradle build** command. 

Execute the following command:

```bash
$ docker build
```

This will create a jar file named **gs-spring-boot-docker.jar** in the **build/libs** directory. Note that this name is from build.gradle file.

```bash
$ build/libs/spring-boot-gradle-docker.jar
```

To execute the Application, you just need to execute the jar file and access the address:

```bash
$ java -jar build/libs/spring-boot-gradle-docker.jar 
```

### Dockerfile

```docker
FROM java

VOLUME /tmp

ADD spring-boot-gradle-docker-0.1.0.jar spring-boot-app.jar

RUN bash -c 'touch /spring-boot-app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-boot-app.jar"]

```

### Build a Docker Image with Gradle

```gradle
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.3.3.RELEASE")
        classpath('se.transmode.gradle:gradle-docker:1.2')
    }
}

group = 'alexandregama'

apply plugin: 'docker'
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'idea'
apply plugin: 'spring-boot'

jar {
    baseName = 'gs-spring-boot-docker'
    version =  '0.1.0'
}

repositories {
    mavenCentral()
}

sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}

task buildDocker(type: Docker, dependsOn: build) {
  push = true
  applicationName = jar.baseName
  dockerfile = file('docker/Dockerfile')
  doFirst {
    copy {
      from jar
      into stageDir
    }
  }
}

task wrapper(type: Wrapper) {
    gradleVersion = '2.3'
}

```

Note that we are using the **push** variable with a true value. This means that we will **push** the image that has created by the build gradle.

But where that image will be pushed? Note the another variable named **group**. This indicates the name of our remote docker repository, in my case named as **alexandregama** (https://hub.docker.com/u/alexandregama/). You just need to replace with your own account.

### Using our new Docker Image

After the **gradle buildDocker** command, we pushed our new image to Docker Hub. Now we are able to use it.

Next, we will run a new container using the new Docker Image:

```bash
$ docker run -it -p 8888:8080 alexandregama/spring-boot-gradle-docker /bin/bash
```

Note in the previous command that we are using a port **8888** to access the application.

Note also that we are running our new container and executing the command **/bin/bash** inside the container. It will be useful to see what is happen in the container log. 

Now we might access the application from browser! Just type:

```bash
$ http://192.168.99.100:8888/hello-docker
```

# Spring Configuration

### Positive Forms

Whe can see all positive forms that our application are capable to use

We may just setup ```debug=true``` on **application.properties** file on **src/main/resources** directory 

```bash
Positive matches:
-----------------

   DispatcherServletAutoConfiguration matched
      - @ConditionalOnClass classes found: org.springframework.web.servlet.DispatcherServlet (OnClassCondition)
      - found web application StandardServletEnvironment (OnWebApplicationCondition)

   DispatcherServletAutoConfiguration.DispatcherServletConfiguration matched
      - @ConditionalOnClass classes found: javax.servlet.ServletRegistration (OnClassCondition)
      - no ServletRegistrationBean found (DispatcherServletAutoConfiguration.DefaultDispatcherServletCondition)

   EmbeddedServletContainerAutoConfiguration matched
      - found web application StandardServletEnvironment (OnWebApplicationCondition)

   EmbeddedServletContainerAutoConfiguration.EmbeddedTomcat matched
      - @ConditionalOnClass classes found: javax.servlet.Servlet,org.apache.catalina.startup.Tomcat (OnClassCondition)
      - @ConditionalOnMissingBean (types: org.springframework.boot.context.embedded.EmbeddedServletContainerFactory; SearchStrategy: current) found no beans (OnBeanCondition)

   ErrorMvcAutoConfiguration matched
      - @ConditionalOnClass classes found: javax.servlet.Servlet,org.springframework.web.servlet.DispatcherServlet (OnClassCondition)
      - found web application StandardServletEnvironment (OnWebApplicationCondition)

   ErrorMvcAutoConfiguration#basicErrorController matched
      - @ConditionalOnMissingBean (types: org.springframework.boot.autoconfigure.web.ErrorController; SearchStrategy: current) found no beans (OnBeanCondition)

   ErrorMvcAutoConfiguration#errorAttributes matched
      - @ConditionalOnMissingBean (types: org.springframework.boot.autoconfigure.web.ErrorAttributes; SearchStrategy: current) found no beans (OnBeanCondition)

   ErrorMvcAutoConfiguration.WhitelabelErrorViewConfiguration matched
      - No error template view detected (ErrorMvcAutoConfiguration.ErrorTemplateMissingCondition)
      - matched (OnPropertyCondition)

   ErrorMvcAutoConfiguration.WhitelabelErrorViewConfiguration#beanNameViewResolver matched
      - @ConditionalOnMissingBean (types: org.springframework.web.servlet.view.BeanNameViewResolver; SearchStrategy: all) found no beans (OnBeanCondition)

   ErrorMvcAutoConfiguration.WhitelabelErrorViewConfiguration#defaultErrorView matched
      - @ConditionalOnMissingBean (names: error; SearchStrategy: all) found no beans (OnBeanCondition)

   GenericCacheConfiguration matched
      - Automatic cache type (CacheCondition)

   HttpEncodingAutoConfiguration matched
      - @ConditionalOnClass classes found: org.springframework.web.filter.CharacterEncodingFilter (OnClassCondition)
      - matched (OnPropertyCondition)

   HttpEncodingAutoConfiguration#characterEncodingFilter matched
      - @ConditionalOnMissingBean (types: org.springframework.web.filter.CharacterEncodingFilter; SearchStrategy: all) found no beans (OnBeanCondition)

   HttpMessageConvertersAutoConfiguration matched
      - @ConditionalOnClass classes found: org.springframework.http.converter.HttpMessageConverter (OnClassCondition)

   HttpMessageConvertersAutoConfiguration#messageConverters matched
      - @ConditionalOnMissingBean (types: org.springframework.boot.autoconfigure.web.HttpMessageConverters; SearchStrategy: all) found no beans (OnBeanCondition)

   HttpMessageConvertersAutoConfiguration.StringHttpMessageConverterConfiguration matched
      - @ConditionalOnClass classes found: org.springframework.http.converter.StringHttpMessageConverter (OnClassCondition)

   HttpMessageConvertersAutoConfiguration.StringHttpMessageConverterConfiguration#stringHttpMessageConverter matched
      - @ConditionalOnMissingBean (types: org.springframework.http.converter.StringHttpMessageConverter; SearchStrategy: all) found no beans (OnBeanCondition)

   JacksonAutoConfiguration matched
      - @ConditionalOnClass classes found: com.fasterxml.jackson.databind.ObjectMapper (OnClassCondition)

   JacksonAutoConfiguration.JacksonObjectMapperBuilderConfiguration matched
      - @ConditionalOnClass classes found: com.fasterxml.jackson.databind.ObjectMapper,org.springframework.http.converter.json.Jackson2ObjectMapperBuilder (OnClassCondition)

   JacksonAutoConfiguration.JacksonObjectMapperBuilderConfiguration#jacksonObjectMapperBuilder matched
      - @ConditionalOnMissingBean (types: org.springframework.http.converter.json.Jackson2ObjectMapperBuilder; SearchStrategy: all) found no beans (OnBeanCondition)

   JacksonAutoConfiguration.JacksonObjectMapperConfiguration matched
      - @ConditionalOnClass classes found: com.fasterxml.jackson.databind.ObjectMapper,org.springframework.http.converter.json.Jackson2ObjectMapperBuilder (OnClassCondition)

   JacksonAutoConfiguration.JacksonObjectMapperConfiguration#jacksonObjectMapper matched
      - @ConditionalOnMissingBean (types: com.fasterxml.jackson.databind.ObjectMapper; SearchStrategy: all) found no beans (OnBeanCondition)

   JacksonHttpMessageConvertersConfiguration.MappingJackson2HttpMessageConverterConfiguration matched
      - @ConditionalOnClass classes found: com.fasterxml.jackson.databind.ObjectMapper (OnClassCondition)
      - matched (OnPropertyCondition)
      - @ConditionalOnBean (types: com.fasterxml.jackson.databind.ObjectMapper; SearchStrategy: all) found the following [jacksonObjectMapper] (OnBeanCondition)

   JacksonHttpMessageConvertersConfiguration.MappingJackson2HttpMessageConverterConfiguration#mappingJackson2HttpMessageConverter matched
      - @ConditionalOnMissingBean (types: org.springframework.http.converter.json.MappingJackson2HttpMessageConverter; SearchStrategy: all) found no beans (OnBeanCondition)

   JmxAutoConfiguration matched
      - @ConditionalOnClass classes found: org.springframework.jmx.export.MBeanExporter (OnClassCondition)
      - matched (OnPropertyCondition)

   JmxAutoConfiguration#mbeanExporter matched
      - @ConditionalOnMissingBean (types: org.springframework.jmx.export.MBeanExporter; SearchStrategy: current) found no beans (OnBeanCondition)

   JmxAutoConfiguration#mbeanServer matched
      - @ConditionalOnMissingBean (types: javax.management.MBeanServer; SearchStrategy: all) found no beans (OnBeanCondition)

   JmxAutoConfiguration#objectNamingStrategy matched
      - @ConditionalOnMissingBean (types: org.springframework.jmx.export.naming.ObjectNamingStrategy; SearchStrategy: current) found no beans (OnBeanCondition)

   MultipartAutoConfiguration matched
      - @ConditionalOnClass classes found: javax.servlet.Servlet,org.springframework.web.multipart.support.StandardServletMultipartResolver,javax.servlet.MultipartConfigElement (OnClassCondition)
      - matched (OnPropertyCondition)

   MultipartAutoConfiguration#multipartConfigElement matched
      - @ConditionalOnMissingBean (types: javax.servlet.MultipartConfigElement; SearchStrategy: all) found no beans (OnBeanCondition)

   MultipartAutoConfiguration#multipartResolver matched
      - @ConditionalOnMissingBean (types: org.springframework.web.multipart.MultipartResolver; SearchStrategy: all) found no beans (OnBeanCondition)

   NoOpCacheConfiguration matched
      - Automatic cache type (CacheCondition)

   PropertyPlaceholderAutoConfiguration#propertySourcesPlaceholderConfigurer matched
      - @ConditionalOnMissingBean (types: org.springframework.context.support.PropertySourcesPlaceholderConfigurer; SearchStrategy: current) found no beans (OnBeanCondition)

   RedisCacheConfiguration matched
      - Automatic cache type (CacheCondition)

   ServerPropertiesAutoConfiguration matched
      - found web application StandardServletEnvironment (OnWebApplicationCondition)

   ServerPropertiesAutoConfiguration#serverProperties matched
      - @ConditionalOnMissingBean (types: org.springframework.boot.autoconfigure.web.ServerProperties; SearchStrategy: current) found no beans (OnBeanCondition)

   SimpleCacheConfiguration matched
      - Automatic cache type (CacheCondition)

   WebMvcAutoConfiguration matched
      - @ConditionalOnClass classes found: javax.servlet.Servlet,org.springframework.web.servlet.DispatcherServlet,org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter (OnClassCondition)
      - found web application StandardServletEnvironment (OnWebApplicationCondition)
      - @ConditionalOnMissingBean (types: org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport; SearchStrategy: all) found no beans (OnBeanCondition)

   WebMvcAutoConfiguration#hiddenHttpMethodFilter matched
      - @ConditionalOnMissingBean (types: org.springframework.web.filter.HiddenHttpMethodFilter; SearchStrategy: all) found no beans (OnBeanCondition)

   WebMvcAutoConfiguration#httpPutFormContentFilter matched
      - @ConditionalOnMissingBean (types: org.springframework.web.filter.HttpPutFormContentFilter; SearchStrategy: all) found no beans (OnBeanCondition)

   WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#beanNameViewResolver matched
      - @ConditionalOnBean (types: org.springframework.web.servlet.View; SearchStrategy: all) found the following [error] (OnBeanCondition)

   WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#defaultViewResolver matched
      - @ConditionalOnMissingBean (types: org.springframework.web.servlet.view.InternalResourceViewResolver; SearchStrategy: all) found no beans (OnBeanCondition)

   WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#requestContextFilter matched
      - @ConditionalOnMissingBean (types: org.springframework.web.context.request.RequestContextListener,org.springframework.web.filter.RequestContextFilter; SearchStrategy: all) found no beans (OnBeanCondition)

   WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#viewResolver matched
      - @ConditionalOnBean (types: org.springframework.web.servlet.ViewResolver; SearchStrategy: all) found the following [defaultViewResolver, beanNameViewResolver, mvcViewResolver] @ConditionalOnMissingBean (names: viewResolver; types: org.springframework.web.servlet.view.ContentNegotiatingViewResolver; SearchStrategy: all) found no beans (OnBeanCondition)

   WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter.FaviconConfiguration matched
      - matched (OnPropertyCondition)

   WebSocketAutoConfiguration matched
      - @ConditionalOnClass classes found: javax.servlet.Servlet,javax.websocket.server.ServerContainer (OnClassCondition)
      - found web application StandardServletEnvironment (OnWebApplicationCondition)

   WebSocketAutoConfiguration.TomcatWebSocketConfiguration matched
      - @ConditionalOnClass classes found: org.apache.catalina.startup.Tomcat,org.apache.tomcat.websocket.server.WsSci (OnClassCondition)

   WebSocketAutoConfiguration.TomcatWebSocketConfiguration#websocketContainerCustomizer matched
      - Required JVM version 1.7 or newer found 1.8 (OnJavaCondition)
      - @ConditionalOnMissingBean (names: websocketContainerCustomizer; SearchStrategy: all) found no beans (OnBeanCondition)

```

And it shows us all negative matches

```bash
Negative matches:
-----------------

   ActiveMQAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: javax.jms.ConnectionFactory,org.apache.activemq.ActiveMQConnectionFactory (OnClassCondition)

   AopAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.aspectj.lang.annotation.Aspect,org.aspectj.lang.reflect.Advice (OnClassCondition)

   ArtemisAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: javax.jms.ConnectionFactory,org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory (OnClassCondition)

   BatchAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.batch.core.launch.JobLauncher,org.springframework.jdbc.core.JdbcOperations (OnClassCondition)

   CacheAutoConfiguration did not match
      - @ConditionalOnClass classes found: org.springframework.cache.CacheManager (OnClassCondition)
      - @ConditionalOnBean (types: org.springframework.cache.interceptor.CacheAspectSupport; SearchStrategy: all) found no beans (OnBeanCondition)

   CacheAutoConfiguration.CacheManagerJpaDependencyConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean (OnClassCondition)
      - Ancestor 'org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration' did not match (ConditionEvaluationReport.AncestorsMatchedCondition)

   CassandraAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.datastax.driver.core.Cluster (OnClassCondition)

   CassandraDataAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.datastax.driver.core.Cluster,org.springframework.data.cassandra.core.CassandraAdminOperations (OnClassCondition)

   CassandraRepositoriesAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.datastax.driver.core.Session,org.springframework.data.cassandra.repository.CassandraRepository (OnClassCondition)

   CloudAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.cloud.config.java.CloudScanConfiguration (OnClassCondition)

   DataSourceAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType (OnClassCondition)

   DataSourceTransactionManagerAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.jdbc.core.JdbcTemplate,org.springframework.transaction.PlatformTransactionManager (OnClassCondition)

   DeviceDelegatingViewResolverAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver (OnClassCondition)

   DeviceResolverAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.mobile.device.DeviceResolverHandlerInterceptor,org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver (OnClassCondition)

   DispatcherServletAutoConfiguration.DispatcherServletConfiguration#multipartResolver did not match
      - @ConditionalOnBean (types: org.springframework.web.multipart.MultipartResolver; SearchStrategy: all) found no beans (OnBeanCondition)

   EhCacheCacheConfiguration did not match
      - required @ConditionalOnClass classes not found: net.sf.ehcache.Cache,org.springframework.cache.ehcache.EhCacheCacheManager (OnClassCondition)

   ElasticsearchAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.elasticsearch.client.Client,org.springframework.data.elasticsearch.client.TransportClientFactoryBean,org.springframework.data.elasticsearch.client.NodeClientFactoryBean (OnClassCondition)

   ElasticsearchDataAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.elasticsearch.client.Client,org.springframework.data.elasticsearch.core.ElasticsearchTemplate (OnClassCondition)

   ElasticsearchRepositoriesAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.elasticsearch.client.Client,org.springframework.data.elasticsearch.repository.ElasticsearchRepository (OnClassCondition)

   EmbeddedMongoAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.mongodb.Mongo,de.flapdoodle.embed.mongo.MongodStarter (OnClassCondition)

   EmbeddedServletContainerAutoConfiguration.EmbeddedJetty did not match
      - required @ConditionalOnClass classes not found: org.eclipse.jetty.server.Server,org.eclipse.jetty.util.Loader,org.eclipse.jetty.webapp.WebAppContext (OnClassCondition)

   EmbeddedServletContainerAutoConfiguration.EmbeddedUndertow did not match
      - required @ConditionalOnClass classes not found: io.undertow.Undertow,org.xnio.SslClientAuthMode (OnClassCondition)

   FacebookAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.social.config.annotation.SocialConfigurerAdapter,org.springframework.social.facebook.connect.FacebookConnectionFactory (OnClassCondition)

   FallbackWebSecurityAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.security.config.annotation.web.configuration.EnableWebSecurity (OnClassCondition)

   FlywayAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.flywaydb.core.Flyway (OnClassCondition)

   FreeMarkerAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: freemarker.template.Configuration,org.springframework.ui.freemarker.FreeMarkerConfigurationFactory (OnClassCondition)

   GroovyTemplateAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: groovy.text.markup.MarkupTemplateEngine (OnClassCondition)

   GsonAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.google.gson.Gson (OnClassCondition)

   GsonHttpMessageConvertersConfiguration did not match
      - required @ConditionalOnClass classes not found: com.google.gson.Gson (OnClassCondition)

   GuavaCacheConfiguration did not match
      - required @ConditionalOnClass classes not found: com.google.common.cache.CacheBuilder,org.springframework.cache.guava.GuavaCacheManager (OnClassCondition)

   H2ConsoleAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.h2.server.web.WebServlet (OnClassCondition)

   HazelcastAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.hazelcast.core.HazelcastInstance (OnClassCondition)

   HazelcastCacheConfiguration did not match
      - required @ConditionalOnClass classes not found: com.hazelcast.core.HazelcastInstance,com.hazelcast.spring.cache.HazelcastCacheManager (OnClassCondition)

   HazelcastJpaDependencyAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.hazelcast.core.HazelcastInstance,org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean (OnClassCondition)

   HibernateJpaAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean,org.springframework.transaction.annotation.EnableTransactionManagement,javax.persistence.EntityManager (OnClassCondition)

   HornetQAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: javax.jms.ConnectionFactory,org.hornetq.api.jms.HornetQJMSClient (OnClassCondition)

   HypermediaAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.hateoas.Resource,org.springframework.plugin.core.Plugin (OnClassCondition)

   InfinispanCacheConfiguration did not match
      - required @ConditionalOnClass classes not found: org.infinispan.spring.provider.SpringEmbeddedCacheManager (OnClassCondition)

   IntegrationAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.integration.config.EnableIntegration (OnClassCondition)

   JCacheCacheConfiguration did not match
      - required @ConditionalOnClass classes not found: javax.cache.Caching,org.springframework.cache.jcache.JCacheCacheManager (OnClassCondition)

   JacksonAutoConfiguration.JodaDateTimeJacksonConfiguration did not match
      - required @ConditionalOnClass classes not found: org.joda.time.DateTime,com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer,com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat (OnClassCondition)

   JacksonAutoConfiguration.ParameterNamesModuleConfiguration did not match
      - required @ConditionalOnClass classes not found: com.fasterxml.jackson.module.paramnames.ParameterNamesModule (OnClassCondition)

   JacksonHttpMessageConvertersConfiguration.MappingJackson2XmlHttpMessageConverterConfiguration did not match
      - required @ConditionalOnClass classes not found: com.fasterxml.jackson.dataformat.xml.XmlMapper (OnClassCondition)

   JerseyAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.glassfish.jersey.server.spring.SpringComponentProvider (OnClassCondition)

   JmsAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.jms.core.JmsTemplate (OnClassCondition)

   JndiConnectionFactoryAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.jms.core.JmsTemplate (OnClassCondition)

   JndiDataSourceAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType (OnClassCondition)

   JooqAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.jooq.DSLContext (OnClassCondition)

   JpaRepositoriesAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.data.jpa.repository.JpaRepository (OnClassCondition)

   JtaAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: javax.transaction.Transaction (OnClassCondition)

   LinkedInAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.social.config.annotation.SocialConfigurerAdapter,org.springframework.social.linkedin.connect.LinkedInConnectionFactory (OnClassCondition)

   LiquibaseAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: liquibase.integration.spring.SpringLiquibase (OnClassCondition)

   MailSenderAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: javax.mail.internet.MimeMessage (OnClassCondition)

   MailSenderValidatorAutoConfiguration did not match
      - @ConditionalOnProperty missing required properties spring.mail.test-connection  (OnPropertyCondition)

   MessageSourceAutoConfiguration did not match
      - No bundle found for spring.messages.basename: messages (MessageSourceAutoConfiguration.ResourceBundleCondition)

   MongoAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.mongodb.MongoClient (OnClassCondition)

   MongoDataAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.mongodb.Mongo,org.springframework.data.mongodb.core.MongoTemplate (OnClassCondition)

   MongoRepositoriesAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.mongodb.Mongo,org.springframework.data.mongodb.repository.MongoRepository (OnClassCondition)

   MustacheAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.samskivert.mustache.Mustache (OnClassCondition)

   OAuth2AutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.security.oauth2.common.OAuth2AccessToken (OnClassCondition)

   PersistenceExceptionTranslationAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor (OnClassCondition)

   RabbitAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.amqp.rabbit.core.RabbitTemplate,com.rabbitmq.client.Channel (OnClassCondition)

   ReactorAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: reactor.spring.context.config.EnableReactor,reactor.Environment (OnClassCondition)

   RedisAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.data.redis.connection.jedis.JedisConnection,org.springframework.data.redis.core.RedisOperations,redis.clients.jedis.Jedis (OnClassCondition)

   RepositoryRestMvcAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration (OnClassCondition)

   SecurityAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.security.authentication.AuthenticationManager,org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter (OnClassCondition)

   SecurityFilterAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer,org.springframework.security.config.http.SessionCreationPolicy (OnClassCondition)

   SendGridAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: com.sendgrid.SendGrid (OnClassCondition)

   SessionAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.session.Session (OnClassCondition)

   SitePreferenceAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.mobile.device.site.SitePreferenceHandlerInterceptor,org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver (OnClassCondition)

   SocialWebAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.social.connect.web.ConnectController,org.springframework.social.config.annotation.SocialConfigurerAdapter (OnClassCondition)

   SolrAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.apache.solr.client.solrj.impl.HttpSolrServer,org.apache.solr.client.solrj.impl.CloudSolrServer,org.apache.solr.common.cloud.HashPartitioner (OnClassCondition)

   SolrRepositoriesAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.apache.solr.client.solrj.SolrServer,org.springframework.data.solr.repository.SolrRepository (OnClassCondition)

   SpringApplicationAdminJmxAutoConfiguration did not match
      - @ConditionalOnProperty missing required properties spring.application.admin.enabled  (OnPropertyCondition)

   SpringDataWebAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.data.web.PageableHandlerMethodArgumentResolver (OnClassCondition)

   ThymeleafAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.thymeleaf.spring4.SpringTemplateEngine (OnClassCondition)

   TransactionAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.transaction.support.TransactionTemplate,org.springframework.transaction.PlatformTransactionManager (OnClassCondition)

   TwitterAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.social.config.annotation.SocialConfigurerAdapter,org.springframework.social.twitter.connect.TwitterConnectionFactory (OnClassCondition)

   VelocityAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.apache.velocity.app.VelocityEngine,org.springframework.ui.velocity.VelocityEngineFactory (OnClassCondition)

   WebMvcAutoConfiguration.ResourceChainCustomizerConfiguration did not match
      - Webjars locator (org.webjars.WebJarAssetLocator) is absent (OnEnabledResourceChainCondition)

   WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#dateFormatter did not match
      - @ConditionalOnProperty missing required properties spring.mvc.date-format  (OnPropertyCondition)

   WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter#localeResolver did not match
      - @ConditionalOnMissingBean (types: org.springframework.web.servlet.LocaleResolver; SearchStrategy: all) found no beans (OnBeanCondition)
      - @ConditionalOnProperty missing required properties spring.mvc.locale  (OnPropertyCondition)

   WebSocketAutoConfiguration.JettyWebSocketConfiguration did not match
      - required @ConditionalOnClass classes not found: org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer (OnClassCondition)

   WebSocketAutoConfiguration.UndertowWebSocketConfiguration did not match
      - required @ConditionalOnClass classes not found: io.undertow.websockets.jsr.Bootstrap (OnClassCondition)

   WebSocketMessagingAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer (OnClassCondition)

   XADataSourceAutoConfiguration did not match
      - required @ConditionalOnClass classes not found: javax.transaction.TransactionManager,org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType (OnClassCondition)
      
```bash




