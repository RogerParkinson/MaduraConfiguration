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
package nz.co.senanque.madura.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

/**
 * 
 * Used to deliver JNDI resources from application servers such as Glassfish.
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.4 $
 */
public class StringResourceFactory implements ObjectFactory
{

    /**
     * @see javax.naming.spi.ObjectFactory#getObjectInstance(java.lang.Object,
     *      javax.naming.Name, javax.naming.Context, java.util.Hashtable)
     */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
            Hashtable<?, ?> environment) throws Exception
    {
        Reference ref = (Reference) obj;
        RefAddr addr = ref.get("content");
        if (addr == null)
        {
            throw new NamingException("Missing parameter with key 'content'");
        }

        String content = (String) addr.getContent();
        if (content == null)
        {
            throw new NamingException("content attribute not set.");
        }
        return content;
    }

}
