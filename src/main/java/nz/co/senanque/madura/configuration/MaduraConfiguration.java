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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.beanutils.BeanDeclaration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.beanutils.XMLBeanDeclaration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * 
 * This acts as a jacket around the apache commons configuration to make it more friendly for Spring.
 * With this we can wire in listeners which is not possible with the commons classes.
 * The configuration that is injected into this class must be a <code>XMLConfiguration</code>
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.8 $
 */
public class MaduraConfiguration implements Configuration, ApplicationContextAware
{
    private FileConfiguration m_configuration;
    @Autowired
    private List<ConfigurationListener> m_configurationListeners;
    private ApplicationContext m_applicationContext;
    
    public MaduraConfiguration()
    {
        BeanHelper.registerBeanFactory("nz.co.senanque.madura.configuration.ConstructorBeanFactory", new nz.co.senanque.madura.configuration.ConstructorBeanFactory());
        BeanHelper.registerBeanFactory("nz.co.senanque.madura.configuration.XMLBeanFactory", new XMLBeanFactory());
        BeanHelper.registerBeanFactory("nz.co.senanque.madura.configuration.SetterBeanFactory", new SetterBeanFactory());
        BeanHelper.registerBeanFactory("nz.co.senanque.madura.configuration.ListBeanFactory", new ListBeanFactory());
    }


    public Configuration getConfiguration()
    {
        return m_configuration;
    }

    public void setConfiguration(Configuration configuration)
    {
        m_configuration = (FileConfiguration)configuration;
    }

    public void addProperty(String arg0, Object arg1)
    {
        m_configuration.addProperty(arg0, arg1);
    }

    public void clear()
    {
        m_configuration.clear();        
    }

    public void clearProperty(String arg0)
    {
        m_configuration.clearProperty(arg0);       
    }

    public boolean containsKey(String arg0)
    {
        return m_configuration.containsKey(arg0);
    }

    public BigDecimal getBigDecimal(String arg0)
    {
        return m_configuration.getBigDecimal(arg0);
    }

    public BigDecimal getBigDecimal(String arg0, BigDecimal arg1)
    {
        return m_configuration.getBigDecimal(arg0,arg1);
    }

    public BigInteger getBigInteger(String arg0)
    {
        return m_configuration.getBigInteger(arg0);
    }

    public BigInteger getBigInteger(String arg0, BigInteger arg1)
    {
        return m_configuration.getBigInteger(arg0,arg1);
    }

    public boolean getBoolean(String arg0)
    {
        return m_configuration.getBoolean(arg0);
    }

    public boolean getBoolean(String arg0, boolean arg1)
    {
        return m_configuration.getBoolean(arg0,arg1);
    }

    public Boolean getBoolean(String arg0, Boolean arg1)
    {
        return m_configuration.getBoolean(arg0,arg1);
    }

    public byte getByte(String arg0)
    {
        return m_configuration.getByte(arg0);
    }

    public byte getByte(String arg0, byte arg1)
    {
        return m_configuration.getByte(arg0,arg1);
    }

    public Byte getByte(String arg0, Byte arg1)
    {
        return m_configuration.getByte(arg0,arg1);
    }

    public double getDouble(String arg0)
    {
        return m_configuration.getDouble(arg0);
    }

    public double getDouble(String arg0, double arg1)
    {
        return m_configuration.getDouble(arg0,arg1);
    }

    public Double getDouble(String arg0, Double arg1)
    {
        return m_configuration.getDouble(arg0,arg1);
    }

    public float getFloat(String arg0)
    {
        return m_configuration.getFloat(arg0);
    }

    public float getFloat(String arg0, float arg1)
    {
        return m_configuration.getFloat(arg0,arg1);
    }

    public Float getFloat(String arg0, Float arg1)
    {
        return m_configuration.getFloat(arg0,arg1);
    }

    public int getInt(String arg0)
    {
        return m_configuration.getInt(arg0);
    }

    public int getInt(String arg0, int arg1)
    {
        return m_configuration.getInt(arg0,arg1);
    }

    public Integer getInteger(String arg0, Integer arg1)
    {
        return m_configuration.getInteger(arg0,arg1);
    }

    public Iterator getKeys()
    {
        return m_configuration.getKeys();
    }

    public Iterator getKeys(String arg0)
    {
        return m_configuration.getKeys(arg0);
    }

    public List getList(String arg0)
    {
        return m_configuration.getList(arg0);
    }

    public List getList(String arg0, List arg1)
    {
        return m_configuration.getList(arg0,arg1);
    }

    public long getLong(String arg0)
    {
        return m_configuration.getLong(arg0);
    }

    public long getLong(String arg0, long arg1)
    {
        return m_configuration.getLong(arg0,arg1);
    }

    public Long getLong(String arg0, Long arg1)
    {
        return m_configuration.getLong(arg0,arg1);
    }

    public Properties getProperties(String arg0)
    {
        return m_configuration.getProperties(arg0);
    }

    public Object getProperty(String arg0)
    {
        Configuration config = m_configuration;
        if (config instanceof XMLConfiguration)
        {
            try
            {
                BeanDeclaration decl = new XMLBeanDeclaration((XMLConfiguration)config, arg0);
                return BeanHelper.createBean(decl);
            }
            catch (IllegalArgumentException e)
            {
                return null;
            }
        }
        else
        {
            return config.getString(arg0);
        }
    }

    public short getShort(String arg0)
    {
        return m_configuration.getShort(arg0);
    }

    public short getShort(String arg0, short arg1)
    {
        return m_configuration.getShort(arg0,arg1);
    }

    public Short getShort(String arg0, Short arg1)
    {
        return m_configuration.getShort(arg0,arg1);
    }

    public String getString(String arg0)
    {
        return m_configuration.getString(arg0);
    }

    public String getString(String arg0, String arg1)
    {
        return m_configuration.getString(arg0,arg1);
    }

    public String[] getStringArray(String arg0)
    {
        return m_configuration.getStringArray(arg0);
    }

    public boolean isEmpty()
    {
        return m_configuration.isEmpty();
    }

    public void setProperty(String arg0, Object arg1)
    {
        m_configuration.setProperty(arg0,arg1);       
    }

    public Configuration subset(String arg0)
    {
        return m_configuration.subset(arg0);
    }
    public void init()
    {
//        BeanHelper.registerBeanFactory("nz.co.senanque.madura.configuration.ConstructorBeanFactory", new nz.co.senanque.madura.configuration.ConstructorBeanFactory());
//        BeanHelper.registerBeanFactory("nz.co.senanque.madura.configuration.XMLBeanFactory", new XMLBeanFactory());
//        BeanHelper.registerBeanFactory("nz.co.senanque.madura.configuration.SetterBeanFactory", new SetterBeanFactory());
//        BeanHelper.registerBeanFactory("nz.co.senanque.madura.configuration.ListBeanFactory", new ListBeanFactory());
        
        XMLConfiguration apacheConfiguration = (XMLConfiguration)getConfiguration();
        
        if (apacheConfiguration.getReloadingStrategy() != null)
        {
            ReloadListener reloadListener = new ReloadListener();
            reloadListener.setApplicationContext(m_applicationContext);
            apacheConfiguration.addConfigurationListener(reloadListener);
        }
//        Map<String, ConfigurationListener> listeners = m_applicationContext.getBeansOfType(ConfigurationListener.class);
//        m_configurationListeners = new ArrayList<ConfigurationListener>();
//        m_configurationListeners.addAll(listeners.values());
    }
    public void reload()
    {
        org.apache.commons.configuration.reloading.ReloadingStrategy reloadingStrategy = m_configuration.getReloadingStrategy();
        if (reloadingStrategy != null && reloadingStrategy instanceof org.apache.commons.configuration.reloading.ManagedReloadingStrategy)
        {
            org.apache.commons.configuration.reloading.ManagedReloadingStrategy managedReloadingStrategy = (org.apache.commons.configuration.reloading.ManagedReloadingStrategy)reloadingStrategy;
            managedReloadingStrategy.refresh();
        }
       invokeListeners();
    }
    private void invokeListeners()
    {
        if (m_configurationListeners != null)
        {
            try
            {
                ConfigurationEvent event = new ConfigurationEvent(this,AbstractConfiguration.EVENT_SET_PROPERTY,null,this, false);
                for (ConfigurationListener listener:m_configurationListeners)
                {
                    listener.configurationChanged(event);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public List<ConfigurationListener> getConfigurationListeners()
    {
        return m_configurationListeners;
    }

    public void setConfigurationListeners(
            List<ConfigurationListener> configurationListeners)
    {
        m_configurationListeners = configurationListeners;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        m_applicationContext = applicationContext;        
    }
}
