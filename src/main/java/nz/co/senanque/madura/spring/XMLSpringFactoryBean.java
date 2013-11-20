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

import java.io.InputStream;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

/**
 * 
 * Reads the wired XML file and returns a document
 * Note that it returns a clone of the document to prevent callers from messing with the original.
  * @author Roger Parkinson
 * @version $Revision: 1.4 $
 */
public class XMLSpringFactoryBean implements FactoryBean<Document>
{
    private Resource m_resource;
    private Document m_document;

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Document getObject()
    {
        if (m_document == null)
            getSynch();
        return (Document)m_document.clone();
    }
    
    private synchronized void getSynch()
    {
        if (m_document != null) return;
        Document doc = null;
        try {
            InputStream in = getResource().getInputStream();
            SAXBuilder sax = new SAXBuilder();
            doc = sax.build(in);
        } 
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        m_document = doc;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<Document> getObjectType()
    {
        return org.jdom.Document.class;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton()
    {
        return false;
    }

    public Resource getResource()
    {
        return m_resource;
    }

    public void setResource(Resource resource)
    {
        m_resource = resource;
    }

}
