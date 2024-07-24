package com.luv2code.ecommerce.config;

import com.luv2code.ecommerce.entity.Country;
import com.luv2code.ecommerce.entity.Order;
import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;
import com.luv2code.ecommerce.entity.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String theAllowedOrigins[];

    // @Value("${spring.data.rest.base-path}")
    // private String basePath;

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager)
    {
        this.entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for Product: PUT, POST, DELETE and PATCH
        disableHttpMethods(Product.class,config, theUnsupportedActions);

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

        disableHttpMethods(Country.class, config, theUnsupportedActions);
        disableHttpMethods(State.class, config, theUnsupportedActions);

        disableHttpMethods(Order.class, config, theUnsupportedActions);

                // Call An Internal Helper Method
                exposeIds(config);

        // configure the cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions)
    {
        config.getExposureConfiguration()
        .forDomainType(theClass)
        .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
        .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }




    private void exposeIds(RepositoryRestConfiguration config)

    // First The /api/product-category Was Resulting the categories but they were not returning the id of the Category

    // Then After Doing The Changes For exposeIds It Will Also Return The Id Of The Categories
    {
        // Expose Entity Ids

        // Get A List Of All Entity Classes From The Entity Manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // Create An Array Of The Entity Types
        List<Class> entityClasses = new ArrayList<>();

        // Get The Entity Types For The Entities
        for(EntityType tempEntityType: entities)
        {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // Expose The Entity Ids For The Array Of Entity/Domain Types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);

    }
}