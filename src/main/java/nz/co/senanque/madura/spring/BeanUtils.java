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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The place to hold various utility methods for manipulating beans.
 * 
 * @author Roger Parkinson
 * @version $Revision: 1.4 $
 */
public class BeanUtils
{
    /**
     * Sets a property on the bean using a setter method.
     * 
     * @param target
     * @param name
     * @param value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setProperty(Object target, String name, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        String methodName = "set"+name;
        Class clazz = target.getClass();
        Method[] methods = clazz.getMethods();
        for (int i=0;i<methods.length;i++)
        {
            if (methodName.equalsIgnoreCase(methods[i].getName()))
            {
                Class params[] = methods[i].getParameterTypes();
                if (params.length == 1 && params[0].equals(value.getClass()))
                {
                    // we have one parameter of the right class.
                    methods[i].invoke(target, new Object[]{value});
                    break;
                }
            }
        }
    }

}
