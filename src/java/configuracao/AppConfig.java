package configuracao;

import java.io.Serializable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "controle", "dao", "delegate", "entidades", "service"})
public class AppConfig implements Serializable {

    @Bean @Scope(value="singleton")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPersistenceUnitName("PU");
        return bean;
    }

    @Bean @Scope(value="singleton")
    public SharedEntityManagerBean jpaTemplate() {
        SharedEntityManagerBean bean = new SharedEntityManagerBean();
        bean.setEntityManagerFactory(entityManagerFactory().getObject());
        return bean;
    }

    @Bean @Scope(value="singleton")
    public JtaTransactionManager transactionManager() {
        JtaTransactionManager bean = new JtaTransactionManager();
        return bean;
    }

    @Bean @Scope(value="singleton")
    public JndiObjectFactoryBean fonteDeDados() {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/fonteDeDados");
        //bean.setJndiName("java:openejb/Resource/jdbc/fonteDeDados");
       //bean.setJndiName("java:jdbc/fonteDeDados");
        return bean;
    }
}