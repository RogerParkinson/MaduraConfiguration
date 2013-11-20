/*******************************************************************************
 * Copyright (c)2013 Prometheus Consulting
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package nz.co.senanque.madura.configuration;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.DefaultBeanFactory;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.apache.commons.configuration.tree.ConfigurationNode;

/**
 * 
 * This is a factory for apache.commons.config.
 * You can configure it to return any class that takes zero or more constructors like this:<br/>
 * <code>
 *     &lt;myurl2 config-class="java.net.URL" config-factory="nz.co.senanque.madura.configuration.ConstuctorBeanFactory"
 *    constructor-arg="http://localhost:8080/hello"/&gt;
 * </code>
 * <br/>
 * Multiple constructor arguments just have numbers eg constructor-arg0, constructor-arg1, constructor-arg2 ...
 * Only String constructor arguments will work and no other setters are called.
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.3 $
 */
public class ConstructorBeanFactory extends DefaultBeanFactory
{
    /** A map for the so far created instances.*/
    private Map<String,Object> beans;
    
    public ConstructorBeanFactory()
    {
        super();
        beans = new HashMap<String,Object>();
    }
    
    // Creates the bean. Checks if already an instance exists.
    public synchronized Object createBean(Class beanClass, BeanDeclaration decl,
        Object param) throws Exception
    {
        Map m = decl.getBeanProperties();
        XMLBeanDeclaration xmlDecl = (XMLBeanDeclaration)decl;
        ConfigurationNode n = xmlDecl.getNode();
        String nodeName = n.getName();
        n = n.getParentNode();
        while (n != null)
        {
            
            if (n.getName() != null)
                nodeName = n.getName()+"/"+nodeName;
            n = n.getParentNode();
        }
        
        Object bean = beans.get(nodeName);
        if (bean != null)
        {
            // Yes, there is already an instance
            return bean;
        }
        else
        {
            // No, create it now (done by the super class)
            List<Class> constructorClasses = new ArrayList<Class>();
            List<Object> constructorArguments = new ArrayList<Object>();
            Object o = m.get("constructor-arg");
            if (o != null)
            {
                constructorClasses.add(o.getClass());
                constructorArguments.add(o);
            }
            o = m.get("constructor-arg0");
            if (o != null)
            {
                constructorClasses.add(o.getClass());
                constructorArguments.add(o);
            }
            for (int i=1;i<10;i++)
            {
                o = m.get("constructor-arg"+i);
                if (o==null) break;
                constructorClasses.add(o.getClass());
                constructorArguments.add(o);
            }
            Constructor constructor = beanClass.getDeclaredConstructor(constructorClasses.toArray(new Class[constructorClasses.size()]));
            bean = constructor.newInstance(constructorArguments.toArray(new Object[constructorArguments.size()]));
            // Store it in map
            beans.put(nodeName, bean);
            return bean;
        }
    }
}
