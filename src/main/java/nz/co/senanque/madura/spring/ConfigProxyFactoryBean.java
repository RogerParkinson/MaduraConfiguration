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
package nz.co.senanque.madura.spring;

import nz.co.senanque.madura.configuration.MaduraConfiguration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.springframework.beans.factory.FactoryBean;


/**
 * 
 * This factory bean acts as a proxy so that we can wire config values into Spring beans
 * If the inner config is an XMLConfiguration then we support beans, otherwise we just return Strings.
 * 
 * example:
 * <br/>
 * <code>
 *  &lt;bean id="myurl2" class="nz.co.senanque.madura.spring.ConfigProxyFactoryBean"&gt;<br/>
 *       &lt;property name="configuration" ref="myconfiguration"/&gt;<br/>
 *       &lt;property name="key" value="test.myurl2"/&gt;<br/>
 *   &lt;/bean&gt;<br/>
 * </code>
 * <br/>
 * The above will deliver whatever object was defined in the configuration at that key.
 * It is usually a string but it might be a URL or any object.
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.3 $
 */
public class ConfigProxyFactoryBean implements FactoryBean
{
    private MaduraConfiguration m_configuration;
    private String m_key;

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Object getObject() throws Exception
    {
        Configuration config = m_configuration.getConfiguration();
        if (config instanceof XMLConfiguration)
        {
            BeanDeclaration decl = new XMLBeanDeclaration((XMLConfiguration)config, getKey());
            Object o = decl.getBeanClassName();
            if (o == null)
            {
                String value = config.getString(getKey());
                return value;
            }
            return BeanHelper.createBean(decl);
        }
        else
        {
            return config.getString(getKey());
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class getObjectType()
    {
        if (m_configuration == null)
        {
            return null;
        }
        Configuration config = m_configuration.getConfiguration();
        if (config instanceof XMLConfiguration)
        {
            try
            {
                BeanDeclaration decl = new XMLBeanDeclaration((XMLConfiguration)config, getKey());
                String n = decl.getBeanClassName();
                if (n == null)
                {
                    return String.class;
                }
                return Class.forName(decl.getBeanClassName());
            }
            catch (ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            return String.class;
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton()
    {
        return true;
    }

    public MaduraConfiguration getConfiguration()
    {
        return m_configuration;
    }

    public void setConfiguration(MaduraConfiguration configuration)
    {
        m_configuration = configuration;
    }

    public String getKey()
    {
        return m_key;
    }

    public void setKey(String key)
    {
        m_key = key;
    }

}
