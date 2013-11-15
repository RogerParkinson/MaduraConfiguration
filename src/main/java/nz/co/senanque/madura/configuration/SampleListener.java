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

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.springframework.stereotype.Component;

/**
 * 
 * Demonstrates configuration listener behaviour.
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.4 $
 */
@Component
public class SampleListener implements ConfigurationListener
{
    private String m_identifier;

    /* (non-Javadoc)
     * @see org.apache.commons.configuration.event.ConfigurationListener#configurationChanged(org.apache.commons.configuration.event.ConfigurationEvent)
     */
    public void configurationChanged(ConfigurationEvent arg0)
    {
        System.out.println("identifier: "+m_identifier);
        Configuration configuration = (Configuration)arg0.getPropertyValue();
        String s = configuration.getString(m_identifier);
        s.toString();
    }

    public String getIdentifier()
    {
        return m_identifier;
    }

    public void setIdentifier(String identifier)
    {
        m_identifier = identifier;
    }

}
