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
 * You can configure it to return an XML document. It does not cache, but Spring does.
 * <br/>
 * <code>
 *    &lt;test1 config-class="org.jdom.Document"<br/>
 *           config-factory="nz.co.senanque.madura.configuration.XMLBeanFactory"&gt;<br/>
 *         &lt;A&gt;XYZ&lt;/A&gt;<br/>
 *         &lt;B&gt;ABC&lt;/B&gt;<br/>
 *     &lt;/test1&gt;<br/>
 * </code>
 *  <br/>
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.1 $
 */
public class ListBeanFactory extends DefaultBeanFactory
{
    /** A map for the so far created instances.*/
    private Map<String,Object> beans;
    
    public ListBeanFactory()
    {
        super();
        beans = new HashMap<String,Object>();
    }
    
    // Creates the bean. Checks if already an instance exists.
    public synchronized Object createBean(Class beanClass, BeanDeclaration decl,
            Object param) throws Exception
        {
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
            final List<String> list = new ArrayList<String>();
            n = xmlDecl.getNode();
            List<ConfigurationNode> children = n.getChildren();
            for (ConfigurationNode node: children)
            {
                list.add(node.getValue().toString());
            }
            // Store it in map
            bean = list;
            beans.put(nodeName, bean);
            return bean;
        }

}
