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

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractRefreshableApplicationContext;

/**
 * 
 * Listens for the reload event and, when it is completed, reloads the Spring beans.
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.2 $
 */
public class ReloadListener implements ConfigurationListener,ApplicationContextAware
{
    private ApplicationContext m_applicationContext;

    public void configurationChanged(ConfigurationEvent event)
    {
        if (!event.isBeforeUpdate() && event.getType() == AbstractFileConfiguration.EVENT_RELOAD)
        {
            if (m_applicationContext instanceof AbstractRefreshableApplicationContext)
            {
                ((AbstractRefreshableApplicationContext)m_applicationContext).refresh();
            }
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        m_applicationContext = applicationContext;
    }

}
