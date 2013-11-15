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
package nz.co.senanque.madura.spring;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

/**
 * Creates an XSL template from the wired source.
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.5 $
 */
public class XSLSpringFactoryBean implements FactoryBean<Templates>
{
    private Resource m_resource;
    private String m_path;

    public Templates getObject() throws Exception
    {
        return loadXSLT(getResource().getInputStream());
    }

    public Class<Templates> getObjectType()
    {
        return Templates.class;
    }

    public boolean isSingleton()
    {
        return true;
    }
    /**
     * Load the XSLT document. The documentName can be a local resource file name
     * or a url pointing to a resource.
     * @param documentName
     * @return a Templates object containing the document
     * @throws DocumentException
     */
    private Templates loadXSLT(InputStream in) 
    {
        try
        {
            Source xsltSource = new StreamSource(in);
            TransformerFactory transFact = TransformerFactory.newInstance();
            Templates templates = transFact.newTemplates(xsltSource);
            return templates;
        }
        catch (Exception e)
        {
            throw new RuntimeException("failed to parse: "+getResource().getDescription(),e);
        }
    }

    public String getPath()
    {
        return m_path;
    }

    public void setPath(String path)
    {
        m_path = path;
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
