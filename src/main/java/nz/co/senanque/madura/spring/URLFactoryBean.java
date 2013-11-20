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

import java.net.URL;
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;

/**
 * Creates a URL from the wired source.
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.5 $
 */
public class URLFactoryBean implements FactoryBean
{
    private String m_name;
    private Properties m_properties;

    public Object getObject() throws Exception
    {
    	String urlString = (String)System.getProperties().get(m_name);
    	if(urlString!=null){
    		return new URL(urlString);
    	}
        return new URL(m_properties.get(m_name).toString());
    }

    public Class getObjectType()
    {
        return URL.class;
    }

    public boolean isSingleton()
    {
        return true;
    }

	public String getName() {
		return m_name;
	}

	public void setName(String m_name) {
		this.m_name = m_name;
	}

	public Properties getEnvironmentProperties() {
		return m_properties;
	}

	public void setEnvironmentProperties(Properties m_properties) {
		this.m_properties = m_properties;
	}
    
   

}
