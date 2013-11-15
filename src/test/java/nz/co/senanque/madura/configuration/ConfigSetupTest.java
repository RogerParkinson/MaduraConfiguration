/*******************************************************************************
 * Copyright 2010 Prometheus Consulting
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package nz.co.senanque.madura.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConfigSetupTest{
    
    protected String[] getConfigLocations() {       
        return  new String[] { "/nz/co/senanque/madura/configuration/Config-spring.xml" };
     }

    @Test
    public void testSpringConfig() throws Exception{

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(getConfigLocations());
        Object hs1 = applicationContext.getBean("component.sourcedir");
        assertTrue("mysourcedir".equals(hs1.toString()));
        Object hs2 = applicationContext.getBean("component1.sourcedir");
        System.out.println(hs2.toString());
        assertTrue("mysourcedir".equals(hs2.toString()));

        URL url = (URL)applicationContext.getBean("myurl");
        assertTrue("http://localhost:8080/jjj".equals(url.toString()));
        System.out.println(url.toString());
        
        Configuration configuration = (Configuration)applicationContext.getBean("configuration");
        Object doc = applicationContext.getBean("test1");
        MyTestBean myTestBean = (MyTestBean)applicationContext.getBean("test2");
        assertTrue(myTestBean.getA().equals("XYZ"));
        myTestBean.setA("12345");
        assertTrue(myTestBean.getA().equals("12345"));
        List sampleList = (List)applicationContext.getBean("sampleList");
        assertEquals(2,sampleList.size());
        applicationContext.refresh();
        
//        ManagedReloadingStrategy reloadingStrategy = (ManagedReloadingStrategy)applicationContext.getBean("reloadingStrategy");
//        reloadingStrategy.refresh();
        MyTestBean myTestBean1 = (MyTestBean)applicationContext.getBean("test2");
        assertTrue(myTestBean1.getA().equals("XYZ"));
        
        
    }

}
